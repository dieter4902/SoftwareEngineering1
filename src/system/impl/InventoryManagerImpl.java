package system.impl;

import datamodel.Article;
import datamodel.Currency;
import datamodel.Order;
import datamodel.OrderItem;
import system.DataRepository.ArticleRepository;
import system.Formatter;
import system.InventoryManager;

import java.util.*;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

class InventoryManagerImpl implements InventoryManager {
    static InventoryManager inventoryManager;
    /**
     * dependency on ArticleRepository.
     */
    private final ArticleRepository articleRepository;
    /**
     * internal data structure to manage inventory (unitsInStore) for Article-id's.
     */
    private final HashMap<String, Integer> inventory = new HashMap<>();

    public InventoryManagerImpl(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public static InventoryManager getInstance(ArticleRepository articleRepository) {
        if (inventoryManager == null) {
            inventoryManager = new InventoryManagerImpl(articleRepository);
        }
        return inventoryManager;
    }

    @Override
    public Optional<Article> findById(String id) {
        return articleRepository.findById(id);
    }

    @Override
    public Iterable<Article> findAll() {
        return articleRepository.findAll();
    }

    @Override
    public long count() {
        return articleRepository.count();
    }

    /**
     * Create new article in InventoryManager (add to internal ArticleRepository).
     *
     * @param article article to create.
     * @throws IllegalArgumentException if article is null, has no valid id or already exists.
     */
    @Override
    public Article save(Article article) {
        if (article == null)
            throw new IllegalArgumentException("illegal article: null");
        //
        String id = article.getId();
        if (id == null)
            throw new IllegalArgumentException("illegal article.id: null");
        //
        articleRepository.save(article);    // save, make sure to avoid duplicates
        //
        if (!inventory.containsKey(id)) {
            this.inventory.put(id, 0);
        }
        return article;
    }

    @Override
    public int getUnitsInStock(String id) {
        return inventory.get(id);
    }

    @Override
    public void update(String id, int updatedUnitsInStock) {
        inventory.put(id, updatedUnitsInStock);
    }


    /**
     * Test that order is fillable.
     * <p>
     * An order is fillable when all order items meet the condition:
     * {@code orderItem.unitsOrdered <= inventory(article).unitsInStock}.
     *
     * @param order to validate.
     * @return true if order is fillable from current inventory.
     * @throws IllegalArgumentException if order is null.
     */
    @Override
    public boolean isFillable(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order is null?!");
        }
        for (OrderItem e : order.getItems()) {
            if (getUnitsInStock(e.getArticle().getId()) < e.getUnitsOrdered()) {
                return false;
            }
        }
        return true;
    }


    /**
     * Fills order by deducting all order items from the inventory, if the
     * order is fillable. If the order is not fillable, inventory remains
     * unchanged (transactional behavior: all or none order item is filled).
     *
     * @param order to fill.
     * @return true if order has been filled, false otherwise.
     * @throws IllegalArgumentException if order is null.
     */
    @Override
    public boolean fill(Order order) {
        if (isFillable(order)) {
            for (OrderItem e : order.getItems()) {
                String id = e.getArticle().getId();
                update(id, getUnitsInStock(id) - e.getUnitsOrdered());
            }
            return true;
        } else {
            return false;
        }
    }


    /**
     * Print inventory as table.
     *
     * @return printed inventory (as table).
     */
    @Override
    public StringBuffer printInventory() {
        return printInventory(1, true, 9);
        //return printInventory(StreamSupport.stream(articleRepository.findAll().spliterator(), false));
    }

    private StringBuffer printInventory(Stream<Article> articleStream) {
        //
        Formatter formatter = new FormatterImpl();
        Formatter.TableFormatter tfmt = new TableFormatterImpl(formatter, new Object[][]{
                // five column table with column specs: width and alignment ('[' left, ']' right)
                {12, '['}, {32, '['}, {12, ']'}, {10, ']'}, {14, ']'}
        })
                .liner("+-+-+-+-+-+")        // print table header
                .hdr("||", "Inv.-Id", "Article / Unit", "Unit", "Units", "Value")
                .hdr("||", "", "", "Price", "in-Stock", "(in €)")
                .liner("+-+-+-+-+-+");
        //
        long totalValue = articleStream
                .map(a -> {
                    long unitsInStock = this.inventory.get(a.getId());
                    long value = a.getUnitPrice() * unitsInStock;
                    tfmt.hdr("||",
                            a.getId(),
                            a.getDescription(),
                            formatter.fmtPrice(a.getUnitPrice(), a.getCurrency()).toString(),
                            Long.toString(unitsInStock),
                            formatter.fmtPrice(value, a.getCurrency()).toString()
                    );
                    return value;
                })
                .reduce(0L, Long::sum);
        //
        String inventoryValue = formatter.fmtPrice(totalValue, Currency.EUR).toString();
        tfmt
                .liner("+-+-+-+-+-+")
                .hdr("", "", "Invent", "ory Value:", inventoryValue);
        //
        return tfmt.getFormatter().getBuffer();
    }

    /**
     * Print inventory as table with sorting and limiting criteria.
     *
     * @param sortedBy  sorting criteria 1: byPrice; 2: byValue; 3: byUnits;
     *                  4: byDescription; 5: bySKU; else: unsorted
     * @param decending true if in descending order
     * @param limit     upper boundary of articles printed after sorting
     * @return printed inventory (as table).
     */
    @Override
    public StringBuffer printInventory(int sortedBy, boolean decending, Integer... limit) {
        Stream<Article> articleStream = StreamSupport.stream(articleRepository.findAll().spliterator(), false);
        articleStream = switch (sortedBy) {
            case 1 -> articleStream.sorted(Comparator.comparingLong(Article::getUnitPrice));
            case 2 -> articleStream.sorted(Comparator.comparingLong(o -> inventory.get(o.getId()) * o.getUnitPrice()));
            case 3 -> articleStream.sorted(Comparator.comparingLong(o -> inventory.get(o.getId())));
            case 4 -> articleStream.sorted(Comparator.comparing(Article::getDescription));
            case 5 -> articleStream.sorted(Comparator.comparing(Article::getId));
            default -> StreamSupport.stream(articleRepository.findAll().spliterator(), false);
        };
        if (decending) {
            List<Article> aList = articleStream.toList();
            List<Article> newAlist = new ArrayList<>();
            for (int i = 0; i < aList.size(); i++) {
                newAlist.add(aList.get(aList.size() - 1 - i));
            }
            articleStream = newAlist.stream();
        }
        if (limit.length > 0) {
            articleStream = articleStream.limit(limit[0]);
        }
        return printInventory(articleStream);
    }
}

package system.impl;

import datamodel.Article;
import datamodel.Currency;
import datamodel.Order;
import system.DataRepository.ArticleRepository;
import system.Formatter;
import system.InventoryManager;

import java.util.HashMap;
import java.util.Optional;
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
    private final HashMap<String, Integer> inventory = new HashMap<String, Integer>();

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
        return Optional.empty();
    }

    @Override
    public Iterable<Article> findAll() {
        return null;
    }

    @Override
    public long count() {
        return 0;
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
            this.inventory.put(id, Integer.valueOf(0));
        }
        return article;
    }

    @Override
    public int getUnitsInStock(String id) {
        return 0;
    }

    @Override
    public void update(String id, int updatedUnitsInStock) {

    }

    @Override
    public boolean isFillable(Order order) {
        return false;
    }

    @Override
    public boolean fill(Order order) {
        return false;
    }


    /**
     * Print inventory as table.
     *
     * @return printed inventory (as table).
     */
    @Override
    public StringBuffer printInventory() {
        return printInventory(StreamSupport.stream(articleRepository.findAll().spliterator(), false));
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
                    long unitsInStock = this.inventory.get(a.getId()).intValue();
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
                .reduce(0L, (a, b) -> a + b);
        //
        String inventoryValue = formatter.fmtPrice(totalValue, Currency.EUR).toString();
        tfmt
                .liner("+-+-+-+-+-+")
                .hdr("", "", "Invent", "ory Value:", inventoryValue);
        //
        return tfmt.getFormatter().getBuffer();
    }


    @Override
    public StringBuffer printInventory(int sortedBy, boolean decending, Integer... limit) {
        return null;
    }
}

package datamodel;

/**
 * Class that describes an ordered item as part of an Order. Orders may have
 * multiple order items.
 *
 * @author AJ
 * @version {@value package_info#Version}
 * @since 1.0
 */
public class OrderItem {

    /**
     * ordered article, immutable, final
     */
    private final Article article;

    /**
     * number of articles ordered
     */
    private int unitsOrdered;

    /**
     * Constructor with article and units arguments.
     *
     * @param article      ordered article, throws IllegalArgumentException if                     article is null
     * @param unitsOrdered number of articles ordered
     */
    public OrderItem(Article article, int unitsOrdered) {
        if (article == null) {
            throw new IllegalArgumentException("article has to be not null");
        } else {
            this.article = article;
        }
        this.unitsOrdered = unitsOrdered;
    }

    /**
     * article getter. Attribute article cannot be changed has therefore has no
     * setter (immutable attribute).
     *
     * @return reference to the ordered article
     */
    public Article getArticle() {
        return article;
    }

    /**
     * unitsOrdered getter.
     *
     * @return number of article ordered
     */
    public int getUnitsOrdered() {
        return unitsOrdered;
    }

    /**
     * unitsOrdered setter with constraint: {@code units >= 0}, otherwise the method
     * has no effect.
     *
     * @param units updated number of articles ordered
     */
    public void setUnitsOrdered(int units) {
        if (units >= 0) {
            unitsOrdered = units;
        }
    }

    /**
     * toString method that returns the details of the order in a better string
     *
     * @return name of item, ordered amount and the price in one String
     */
    public String toString() {
        return unitsOrdered + " " + getArticle().getDescription() + " " + getArticle().getId() + ", " +
                (unitsOrdered == 1 ? "" : unitsOrdered + "x") + " " + (double) getArticle().getUnitPrice() / 100 + getArticle().getCurrency().symbol();
    }

}
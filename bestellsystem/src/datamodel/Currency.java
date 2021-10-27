package datamodel;

/**
 * Type to enumerate currencies.
 * <p>
 * Currency is the unit in which price information is expressed.
 *
 * @author AJ
 * @version {@value package_info#Version}
 * @since 1.0
 */
public enum Currency {
    /**
     * Eur currency.
     */
    EUR,
    /**
     * Usd currency.
     */
    USD,
    /**
     * Gbp currency.
     */
    GBP,
    /**
     * Yen currency.
     */
    YEN,
    /**
     * None currency.
     */
    NONE
}
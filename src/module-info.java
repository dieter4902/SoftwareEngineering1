/**
 * Module of a simple order processing application: <b>&rdquo;se1.bestellsystem&rdquo;</b>
 * <p>
 * The {@link datamodel} package exports the data model definitions.
 * </p>
 * <p>
 * Hier könnte ihre Werbung stehen!
 * </p>
 *
 * @author AJ
 * @version 0.1.2
 * @since 0.1.0
 */

module se.bestellsystem {
    requires transitive com.fasterxml.jackson.core;
    requires transitive com.fasterxml.jackson.databind;
    requires transitive com.fasterxml.jackson.annotation;
    exports datamodel;
    exports system;
}

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
 * @version 0.1.0
 */

module se1.bestellsystem {
    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.databind;
    requires com.fasterxml.jackson.annotation;
    exports datamodel;
}

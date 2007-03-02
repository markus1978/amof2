package hub.sam.mof.merge;

import cmof.Property;

/**
 * This class represent a difference in two models. A difference is based on the differing objects, the property in
 * that they differ, and the values that cause the difference, as well as a message that explains the kind of the
 * difference.
 */
public class Difference {

    private final Property property;
    private final Object objects1;
    private final Object objects2;
    private final Iterable<Object> values1;
    private final Iterable<Object> values2;
    private final String message;
    private final Difference cause;

    public Difference(
            String message, Object objects1, Object objects2, Property property, Iterable<Object> values1,
            Iterable<Object> values2, Difference cause) {
        super();
        this.message = message;
        this.objects1 = objects1;
        this.objects2 = objects2;
        this.property = property;
        this.values1 = values1;
        this.values2 = values2;
        this.cause = cause;
    }

    /**
     * Returns a message that explains the difference.
     */
    @SuppressWarnings({"OverlyLongMethod"})
    public String getMessage() {
        StringBuffer result = new StringBuffer();
        result.append(message);
        result.append("\n");
        if (objects1 != null) {
            result.append("    Object one: ");
            result.append(objects1);
            result.append("\n");
        }
        if (objects2 != null) {
            result.append("    Object two: ");
            result.append(objects2);
            result.append("\n");
        }
        if (property != null) {
            result.append("    In property: ");
            result.append(property);
            result.append("\n");
        }
        if (values1 != null) {
            result.append("    In value set of object one: ");
            for (Object value : values1) {
                result.append(value.toString());
                result.append("  ");
            }
            result.append("\n");
        }
        if (values2 != null) {
            result.append("    In value set of object two: ");
            for (Object value : values2) {
                result.append(value.toString());
                result.append("  ");
            }
            result.append("\n");
        }
        if (cause != null) {
            result.append("    Caused by: ");
            result.append(cause);
            result.append("\n");
        }
        return result.toString();
    }
}

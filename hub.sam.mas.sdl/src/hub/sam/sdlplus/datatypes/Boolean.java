package hub.sam.sdlplus.datatypes;

public class Boolean extends ReflectiveDataValue {

    public static final Boolean TRUE = new Boolean(true);
    public static final Boolean FALSE = new Boolean(false);

    private boolean value;

    public Boolean(boolean initalValue) {
        super(Boolean.class);
        this.value = initalValue;
    }

    public Boolean and(Boolean o1, Boolean o2) {
        return (o1.value && o2.value) ? TRUE : FALSE;
    }

    public Boolean or(Boolean o2) {
        return (value || o2.value) ? TRUE : FALSE;
    }

    public Boolean not() {
        return (value) ? FALSE : TRUE;
    }

    public Boolean equal(Boolean o2) {
        return (value == o2.value) ? TRUE : FALSE;
    }

    @Override
    public boolean equals(Object o2) {
        if (o2 instanceof Boolean) {
            return equal((Boolean)o2).value;
        } else {
            return false;
        }
    }
}

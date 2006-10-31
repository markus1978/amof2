package hub.sam.sdlplus.datatypes;

public class Integer extends ReflectiveDataValue {

    private int value;
    public Integer(int initalValue) {
        super(Integer.class);
        this.value = initalValue;
    }

    public Integer add(Integer o2) {
        return new Integer(value + o2.value);
    }


    public Integer sub(Integer o2) {
        return new Integer(value - o2.value);
    }

    public Integer mul(Integer o2) {
        return new Integer(value * o2.value);
    }

    public Integer neg() {
        return new Integer(value * (-1));
    }

    public Boolean greater(Integer o2) {
        return (value > o2.value) ? Boolean.TRUE : Boolean.FALSE;
    }

    public Boolean less(Integer o2) {
        return (value < o2.value) ? Boolean.TRUE : Boolean.FALSE;
    }

    public Boolean equal(Integer o2) {
        return (value == o2.value) ? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public boolean equals(Object o2) {
        if (o2 instanceof Integer) {
            return value == ((Integer)o2).value;
        } else {
            return false;
        }
    }
}

package hub.sam.util;

public class Tuple {
    private final Object o1;
    private final Object o2;

    public Tuple(Object o1, Object o2) {
        super();
        this.o1 = o1;
        this.o2 = o2;
    }

    @Override
    public int hashCode() {
        return o1.hashCode() + o2.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Tuple) {
            Tuple tupe = (Tuple)obj;
            if (o1 == null || o2 == null) {
                return ((o1 == tupe.o1) && (o2 == tupe.o2)) || ((o1 == tupe.o2) && (o2 == tupe.o1));
            } else {
                return (o1.equals(tupe.o1) && o2.equals(tupe.o2)) || (o1.equals(tupe.o2) && o2.equals(tupe.o1));
            }
        } else {
            return false;
        }
    }
}

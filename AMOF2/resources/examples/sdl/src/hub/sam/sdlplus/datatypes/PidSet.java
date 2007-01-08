package hub.sam.sdlplus.datatypes;

import SDL.PidValue;

import java.util.Vector;
import java.util.List;

public class PidSet extends ReflectiveDataValue {

    private final List<PidValue> pidSet = new Vector<PidValue>();

    public PidSet() {
        super(PidSet.class);
    }

    public Boolean contains(PidValue value) {
        Boolean result = (pidSet.contains(value)) ? Boolean.TRUE : Boolean.FALSE;
        return result;
    }

    public PidSet incl(PidValue value) {
        pidSet.add(value);
        return this;
    }

    public PidSet del(PidValue value) {
        pidSet.remove(value);
        return this;
    }
}

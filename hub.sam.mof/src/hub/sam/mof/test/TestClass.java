package hub.sam.mof.test;

import java.io.Serializable;

public class TestClass implements Serializable {
    private final int x;
    private final int y;

    public TestClass(int x, int y) {
        super();
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

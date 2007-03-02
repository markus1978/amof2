package hub.sam.tools;

import java.util.List;
import java.util.Comparator;
import java.util.Vector;

public class Util {
    /**
     * Sorts list properly when c does only induce a half order (1 for bigger; 0,-1 for not bigger).
     */
    public static <T> void sortHO(List<T> list, Comparator<? super T> c) {
        List<T> result = new Vector<T>();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            T biggest = null;
            for (T e: list) {
                if (biggest == null || c.compare(e, biggest) == 1) {
                    biggest = e;
                }
            }
            result.add(biggest);
            list.remove(biggest);
        }
        for (int i = 0; i < size; i++) {
            list.add(null);
            list.set(i, result.get(i));
        }
    }
}

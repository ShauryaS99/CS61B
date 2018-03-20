package hw3.hash;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        Map<Integer, Integer> buckets = new HashMap<>();
        int numbuckets;
        int size = oomages.size();
        for (Oomage o : oomages) {
            numbuckets = (o.hashCode() & 0x7FFFFFFF) % M;
            if (buckets.containsKey(numbuckets)) {
                int count = buckets.get(numbuckets) + 1;
                buckets.replace(numbuckets, count);
            } else {
                buckets.put(numbuckets, 1);
            }
        }
        for (int v : buckets.values()) {
            if (v < size / 50 || v > size / 2.5) {
                return false;
            }
        }
        return true;
    }
}

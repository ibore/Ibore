package me.ibore.http.map;

import java.util.Comparator;
import java.util.List;
import java.util.TreeMap;

/**
 * Created by Administrator on 2017/6/2.
 */

public class TreeMultiValuedMap<K, V> extends MultiValuedMapImp<K, V> {

    public TreeMultiValuedMap() {
        super(new TreeMap<K, List<V>>());
    }

    public TreeMultiValuedMap(Comparator<K> keyComparator) {
        super(new TreeMap<K, List<V>>(keyComparator));
    }

}

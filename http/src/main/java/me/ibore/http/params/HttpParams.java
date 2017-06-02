package me.ibore.http.params;

import java.util.List;
import java.util.Map;
import java.util.Set;

import me.ibore.http.map.TreeMultiValuedMap;

/**
 * Created by Administrator on 2017/6/2.
 */

public class HttpParams extends TreeMultiValuedMap<String, String> implements Params {

    @Override
    public void addAll(Params params) {
        if (params != null) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                add(key, params.getValues(key));
            }
        }
    }

    @Override
    public void setAll(Params params) {
        if (params != null) {
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                set(key, params.getValues(key));
            }
        }
    }

    @Override
    public Map<String, List<String>> getParams() {
        return getSource();
    }
}

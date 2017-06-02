package me.ibore.http.headers;


import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import me.ibore.http.map.TreeMultiValuedMap;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-31 19:40
 * website: ibore.me
 */

public final class HttpHeaders extends TreeMultiValuedMap<String, String> implements Headers {

    public HttpHeaders() {
        super(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Override
    public void addAll(Headers headers) {
        if (headers != null) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                add(key, headers.getValues(key));
            }
        }
    }

    @Override
    public void setAll(Headers headers) {
        if (headers != null) {
            Set<String> keySet = headers.keySet();
            for (String key : keySet) {
                set(key, headers.getValues(key));
            }
        }
    }

    @Override
    public Map<String, List<String>> getHeaders() {
        return getSource();
    }
}

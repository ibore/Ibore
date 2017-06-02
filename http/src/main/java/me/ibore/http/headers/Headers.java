package me.ibore.http.headers;

import java.util.List;
import java.util.Map;

import me.ibore.http.map.MultiValuedMap;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-06-02 00:22
 * website: ibore.me
 */

public interface Headers extends MultiValuedMap<String, String> {


    void addAll(Headers headers);

    void setAll(Headers headers);

    Map<String, List<String>> getHeaders();

}

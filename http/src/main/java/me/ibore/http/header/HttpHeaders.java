package me.ibore.http.header;


import java.util.Map;

import me.ibore.http.map.MultiValuedMapImp;

/**
 * description:
 * author: Ibore Xie
 * date: 2017-05-31 19:40
 * website: ibore.me
 */

public final class HttpHeaders extends MultiValuedMapImp<String, String> implements Headers {


    public HttpHeaders(Map source) {
        super(source);
    }
}

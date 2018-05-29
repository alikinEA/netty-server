package server;

import io.netty.handler.codec.http.HttpRequest;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Alikin E.A. on 29.05.18.
 */
public class Service {

    public static byte[] getByRequest(HttpRequest req) throws UnsupportedEncodingException {
        if (req.uri().contains("/api/test/json")) {
            return Repository.store.get(Integer.parseInt(getOneParam(req.uri())));
        } else {
            throw new RuntimeException();
        }
    }

    public static String getOneParam(String uri) throws UnsupportedEncodingException {
        return uri.substring(uri.lastIndexOf("=") + 1,uri.length());
    }
}

package sample.test;

import io.restassured.response.Response;
import io.restassured.http.Headers;

import java.util.Map;

import io.restassured.authentication.BasicAuthScheme;

public class TestRestCrud {

    /**
     * @param params  - Query parameters
     * @param headers - Request headers
     * @param auth    - Basic authentication
     * @param requestUrl - requestUrl 
     * @return Response
     */
    public static Response getCrud(Headers headers, Map<String, Object> params, BasicAuthScheme auth, String requestUrl) {
        return HttpRequestSender.executeGet(requestUrl, headers, params, auth);
    }


}

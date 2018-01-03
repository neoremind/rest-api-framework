package com.hulu.ap.apiframework;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author xu.zhang
 */
public class ServerFunctionalTest extends ServerBaseTest {

    @Test
    public void test() throws IOException, InterruptedException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(baseUrl + PORT + "/ping")
                .build();
        Response response = client.newCall(request).execute();
        String res = response.body().string();
        assertThat(res, Matchers.containsString("+Pong"));
    }

}
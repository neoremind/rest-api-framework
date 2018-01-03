package com.hulu.ap.example.simple.api.client;

import com.google.common.collect.Lists;
import com.hulu.ap.apiframework.client.OkHttpClientBuilder;
import com.hulu.ap.example.simple.server.client.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Date;
import java.util.List;

/**
 * @author xu.zhang
 */
@Ignore
public class HelloworldClientTest {

    @Test
    public void test_echo_message_with_no_response_got() {
        HelloworldApi api = new HelloworldApi();
        try {
            api.echoBlackholeMessage("hi");
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_echo_message() {
        HelloworldApi api = new HelloworldApi();
        try {
            String response = api.echoMessage("hi");
            System.out.println(response);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_echo_message_with_client_helper() {
        ApiClient client = new ApiClient()
                .setBasePath("http://localhost:8080")
                .setHttpClient(new OkHttpClientBuilder()
                        .setConnectTimeout(2000)
                        .setReadTimeout(5000)
                        .setWriteTimeout(5000)
                        .setRetryCount(2).build());

        HelloworldApi api = new HelloworldApi(client);
        try {
            String response = api.echoMessage("hi");
            System.out.println(response);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_echo_message_with_client_helper_negative() {
        ApiClient client = new ApiClient()
                .setBasePath("http://localhost:80") // wrong url
                .setHttpClient(new OkHttpClientBuilder()
                        .setConnectTimeout(2000)
                        .setReadTimeout(5000)
                        .setWriteTimeout(5000)
                        .setRetryCount(2).build());

        HelloworldApi api = new HelloworldApi(client);
        try {
            String response = api.echoMessage("hi");
            System.out.println(response);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_echo_message_with_http_info() {
        HelloworldApi api = new HelloworldApi();
        try {
            ApiResponse<String> response = api.echoMessageWithHttpInfo("hi");
            System.out.println(response.getStatusCode());
            System.out.println(response.getHeaders());
            System.out.println(response.getData());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void test_echo_number() {
        HelloworldApi api = new HelloworldApi();
        try {
            Result response = api.echoNumberInJSONFormat(99);
            System.out.println(response);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * bad practice due to only got server internal error with no details.
     *
     * @throws ApiException
     */
    @Test(expected = ApiException.class)
    public void test_echo_number_negative() throws ApiException {
        HelloworldApi api = new HelloworldApi();
        int invalidNumber = 0;
        Result response = api.echoNumberInJSONFormat(invalidNumber);
        System.out.println(response);
    }

    /**
     * Note that Generic type missing, that is not a good best practice :-(
     */
    @Test
    public void test_echo_list_back_with_generic_type_missing() {
        HelloworldApi api = new HelloworldApi();
        try {
            List<Request> requests = Lists.newArrayList(
                    new Request().name("jack").id(88),
                    new Request().name("ben").id(99),
                    new Request().name("jessie").id(100));
            List<Object> response = api.echoListBack(requests);
            System.out.println(response);
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * Leave response with no generic type is a better way
     * compared with {@link #test_echo_list_back_with_generic_type_missing()}
     */
    @Test
    public void test_echo_list_back_with_generic_type_remaining() {
        HelloworldApi api = new HelloworldApi();
        try {
            List<Request> requests = Lists.newArrayList(
                    new Request().name("jack").id(88),
                    new Request().name("ben").id(99),
                    new Request().name("jessie").id(100));
            ResultWrapper response = api.echoListBackWithWrapper(requests);
            System.out.println(response.getResultList());
        } catch (ApiException e) {
            e.printStackTrace();
        }
    }

    @Test
    //TODO fix later
    public void test_echo_date() {
        ApiClient client = new ApiClient()
                .setBasePath("http://localhost:8080") // wrong url
                .setHttpClient(new OkHttpClientBuilder()
                        .setConnectTimeout(2000)
                        .setReadTimeout(5000)
                        .setWriteTimeout(5000)
                        .setRetryCount(0).build());

        HelloworldApi api = new HelloworldApi(client);
        try {
            DateDto dateDto = new DateDto();
            dateDto.setDate(DateTime.now());
            DateDto response = api.echoDate(dateDto);
        } catch (ApiException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

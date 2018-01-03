package com.neoremind.apiframework;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author xu.zhang
 */
@Ignore
public class ServerPerformanceTest {

    public static final String URL = "http://localhost:8090/helloworld/1.0/echo/abcdefg";

    OkHttpClient client = new OkHttpClient();

    Request request = new Request.Builder()
            .url(URL)
            .build();

    @Test
    public void testConcurrentCall() throws Exception {
        for (int i = 0; i < 10000; i++) {
            testConcurrentCall(100000, 2);
        }
    }

    public void testConcurrentCall(int invokeNum, int concurrentThreadSize) throws Exception {
        ExecutorService executor = Executors.newFixedThreadPool(concurrentThreadSize);
        CompletionService<Long> completionService = new ExecutorCompletionService<>(executor);
        for (int i = 0; i < invokeNum; i++) {
            completionService.submit(new Task());
        }
        long allElaspedTime = 0;
        for (int j = 0; j < invokeNum; j++) {
            Future<Long> future = completionService.take();
            allElaspedTime += future.get();
        }
        System.out.println("ElaspedTime(nanoseconds):" + allElaspedTime);
        System.out.println("Average cost time(nanoseconds):" + new Double(allElaspedTime / invokeNum));
        System.out.println("QPS:" + 1000000000.0 / new Double(allElaspedTime / invokeNum));
        executor.shutdown();
    }

    class Task implements Callable<Long> {

        @Override
        public Long call() throws Exception {
            long begintime = System.nanoTime();
            Response r = client.newCall(request).execute();
            r.body().close();
            //long endtinme = System.nanoTime();
            //System.out.println(output);
            //System.out.println("Cost millseconds:" + (endtinme - begintime));
            return (System.nanoTime() - begintime);
        }
    }

}
package com.hulu.ap.example.simple.api.client;

import com.hulu.ap.example.simple.server.client.HelloworldApi;
import org.hamcrest.Matchers;
import org.junit.Ignore;
import org.junit.Test;

import java.util.concurrent.*;

import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Call http://localhost:8080/helloworld/1.0/echo2/ with concurrency level of multi-threads of 10
 * and carry out up to 100,000 calls to see if there are any concurrency issues.
 * <p>
 * The good news is that <code>ApiClient</code> is thread-safe!
 *
 * @author xu.zhang
 */
@Ignore
public class VerifyClientThreadSafeTest {

    public static final String URL = "http://localhost:8080/helloworld/1.0/echo2/";

    HelloworldApi api = new HelloworldApi();

    @Test
    public void testConcurrentCall() throws Exception {
        testConcurrentCall(100000, 10);
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
            String randomString = RandomUtils.generateMixString(20);
            String res = api.echoMessage(randomString);
            assertThat(res, Matchers.is(randomString));
            //long endtinme = System.nanoTime();
            //System.out.println(output);
            //System.out.println("Cost millseconds:" + (endtinme - begintime));
            return (System.nanoTime() - begintime);
        }
    }
}

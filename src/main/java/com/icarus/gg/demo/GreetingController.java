package com.icarus.gg.demo;

import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheBuilder;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();
  private LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
  .expireAfterWrite(5, TimeUnit.SECONDS)
  .recordStats()
  .build(
    new CacheLoader<String, String>() {
      @Override
      public String load(String keyName) throws Exception {
        return doSomeProcess(keyName);
      }
    });

  private String doSomeProcess(String keyName) throws ExecutionException {
    System.out.println("--[doing some process]--");
    return keyName + "-dev";
  }

	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
    Greeting returnGreeting = null;
		try {
      returnGreeting = new Greeting(counter.incrementAndGet(), loadingCache.get(name));
    } catch( Exception e) {
      e.printStackTrace();
    }
    return returnGreeting;
	}
}
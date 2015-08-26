package com.yadla;

import junit.framework.TestCase;

import java.util.InputMismatchException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by yaswanth on 26/8/15.
 */
public class LRUCacheTest extends TestCase {
    LRUCache cache;
    // assigning the values
    protected void setUp(){
        cache = new LRUCache<Integer,Integer>(25);
    }

    // test method to add two values
    public void testSimple(){
        final int number=300;
        ExecutorService es = Executors.newCachedThreadPool();
        for(int i=0;i<50;i++){
            es.execute(new Runnable() {
                public void run(){
                    for(int j=0;j<number;j++){
                        cache.put(j,j+10);
                    }
                }
            });
        }
        es.shutdown();
        try {
            boolean finshed = es.awaitTermination(1, TimeUnit.MINUTES);
            while(!finshed);
            for(int i=0;i<number;i++) {
                if(cache.get(i)!=null) {
                    assertEquals(i + 10, cache.get(i));
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}

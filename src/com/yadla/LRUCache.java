package com.yadla;

import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yaswanth on 25/8/15.
 */
public class LRUCache implements Cache<Integer,Integer> {
    int capacity;
    ConcurrentHashMap<Integer,Entry> map = new ConcurrentHashMap<Integer,Entry>();
    Queue<Node> cleanUp = new Queue<Node>;
    Node head= null;
    Node tail= null;

    public LRUCache(int capacity){
        this.capacity = capacity;
    }


    public Integer get(Integer key){
        if(map.containsKey(key)){
            Entry entry = map.get(key);
            return entry.value;3
        }
        return -1;
    }
    public void put(Integer key,Integer value){

    }
}

package com.yadla;

public class Main {

    public static void main(String[] args) {
        LRUCache c = new LRUCache(10);
        for(int i=0;i<20;i++){
            c.put(i,i*100);
        }
        System.out.println(c.tail.entry.getKey());
    }
}

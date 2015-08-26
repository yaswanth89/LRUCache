package com.yadla;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Created by yaswanth on 25/8/15.
 */
public class Entry<K,V> {
    private volatile Node n;
    private V value;
    private K key;

    private static final AtomicReferenceFieldUpdater<Entry, Node> nUpdater =
            AtomicReferenceFieldUpdater.newUpdater(Entry.class, Node.class, "n");

    public Entry(K key,V val){
        this.value = val;
        this.key = key;
    }

    Node getNode() {
        return this.n;
    }

    boolean compareAndSetNode(Node expect, Node update) {
        return nUpdater.compareAndSet(this, expect, update);
    }


    void setNode(Node n){
        nUpdater.set(this,n);
    }

    V getValue(){
        return this.value;
    }

    K getKey(){
        return this.key;
    }
}

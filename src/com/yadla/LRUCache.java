package com.yadla;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by yaswanth on 25/8/15.
 */
public class LRUCache<K,V> implements Cache<K,V> {
    int capacity;
    ConcurrentHashMap<K,Entry> map = new ConcurrentHashMap<K,Entry>();
    Node head= null;
    Node tail= null;
    AtomicInteger count;

    public LRUCache(int capacity){
        this.capacity = capacity;
        this.count = new AtomicInteger();
    }


    public V get(K key){
        if(this.map.containsKey(key)){
            Entry entry = this.map.get(key);
            return offer(entry);
        }
        return null;
    }
    public void put(K key,V value){
        Entry newEntry = new Entry(key,value);
        Entry oldEntry = this.map.putIfAbsent(key,newEntry);
        if(oldEntry==null){
            offer(newEntry);
            if(count.incrementAndGet() > capacity){
                do{
                    removeAtTail();
                }while (count.get() > capacity);
            }
        }
        else{
            if(this.map.replace(key,oldEntry,newEntry)){
                oldEntry.setNode(null); //removing the pointer to previous node so gc could clear it
                offer(newEntry);
            }
        }
    }
    private V offer(Entry<K, V> entry){
        if(this.head == null){
            this.assignAsHead(entry);
        }
        if(!this.head.entry.equals(entry)){
            Node currentNode = entry.getNode();
            Node newNode = new Node(entry);
            if(entry.compareAndSetNode(currentNode,newNode)){
                this.head.pre = newNode;
                newNode.next = this.head;
                this.head = newNode;
                if(currentNode != null){
                    if(this.tail == currentNode){
                        this.tail = this.tail.pre;
                    }
                    //remove from linked list
                    currentNode.pre.next = currentNode.next;
                    if(currentNode.next != null){
                        currentNode.next.pre = currentNode.pre;
                    }
                }
            }
        }
        return entry.getValue();
    }

    private synchronized void assignAsHead(Entry<K,V> entry){
        this.head = new Node(entry);
        this.tail = this.head;
        entry.setNode(this.head);
    }

    private synchronized void removeAtTail(){
        if(this.tail != this.head){
            this.map.remove(this.tail.entry.getKey());
            this.tail.pre.next = null;
            this.tail = this.tail.pre;
            count.decrementAndGet();
        }
    }

    public Integer getCount(){
        return this.count.get();
    }
}

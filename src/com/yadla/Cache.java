package com.yadla;

/**
 * Created by yaswanth on 25/8/15.
 */
public interface Cache<K,V>
{
    /** put this entry(key,value) in the Cache.
     * If cache has reached its capacity, then it should remove Least Recently Used
     * entry from cache and then insert this entry.
     * @param key
     * @param value
     */
    public void put(K key,V value);

    /**
     * get value corresponding to this key
     * @param key
     * @return */
    public V get(K key);
}
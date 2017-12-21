package de.knox.jp.utilities;

import java.util.List;

import com.google.common.collect.Lists;

public class SortedMap<K, V> {

	private List<K> keys;
	private List<V> values;

	public SortedMap() {
		keys = Lists.newArrayList();
		values = Lists.newArrayList();
	}

	public void put(K k, V v) {
		keys.add(k);
		values.add(v);
	}

	public V get(K k) {
		int i = keys.indexOf(k);
		return values.get(i);
	}

	public List<K> keyList() {
		return keys;
	}

	public List<V> values() {
		return values;
	}

	public void clear() {
		keys.clear();
		values.clear();
	}
}

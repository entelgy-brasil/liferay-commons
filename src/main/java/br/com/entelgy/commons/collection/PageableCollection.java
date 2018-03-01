package br.com.entelgy.commons.collection;

import java.util.Iterator;

public class PageableCollection<T> implements Iterable<T> {

	private final long count;
	private final Iterable<T> items;

	public PageableCollection(Iterable<T> items, long count) {
		this.count = count;
		this.items = items;
	}

	public Iterable<T> getItems() {
		return items;
	}

	public long getCount() {
		return count;
	}

	@Override
	public Iterator<T> iterator() {
		return items.iterator();
	}
}

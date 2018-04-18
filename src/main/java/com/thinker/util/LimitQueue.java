package com.thinker.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

public class LimitQueue<E> {

	private int limit; // 队列长度

	private ArrayList<E> queue = new ArrayList<E>();

	private TreeSet<E> uniqueQueue = new TreeSet<E>();

	public LimitQueue(int limit) {
		this.limit = limit;
	}

	/**
	 * 入列：当队列大小已满时，把队头的元素poll掉
	 */
	public void offer(E e) {
		if (queue.size() >= limit) {
			queue.remove(0);
		}
		queue.add(e);
	}

	public void ukPut(E e) {
		if (uniqueQueue.size() >= limit) {
			uniqueQueue.pollFirst();
		}
		uniqueQueue.add(e);
	}

	public E get(int position) {
		return queue.get(position);
	}

	public int getLimit() {
		return limit;
	}

	public int size() {
		return queue.size();
	}

	public List<E> getAll() {

		ArrayList<E> result = new ArrayList<E>();

		uniqueQueue.forEach((item) -> result.add(item));

		return result;

	}

	public List<E> getAllList() {

		return queue;

	}

}
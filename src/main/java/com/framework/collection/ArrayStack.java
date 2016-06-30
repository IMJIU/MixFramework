package com.framework.collection;
/**
 * 一个基于数组的栈实现
 * 
 * @author 鼎鼎  
 * @param <E>
 */
public class ArrayStack<E> implements Stack<E> {
	/**
	 * 表示要存入栈里的元素
	 */
	private E[] data;

	/**
	 * 表示当前栈的容量
	 */
	private int size = 0;

	public ArrayStack() {
		data = (E[]) new Object[10];
	//一开始的时候 栈中元素为空
		size = 0;
	}
	
	public ArrayStack(int s) {
		data = (E[]) new Object[s];
	//一开始的时候 栈中元素为空
		size = 0;
	}

	public int size() {
		return size;

	}

	public boolean isEmpty() {
		return size < 1;
	}

	/**
	 * 返回栈顶元素，但是不出栈
	 */
	public E top() {
		if (isEmpty()) {

			throw new RuntimeException("链表为空!!");
		}
		return data[size-1];
	}

	/**
	 * 出栈
	 */
	public E pop() {
		if (isEmpty()) {

			throw new RuntimeException("链表为空!!");
		}
		// 先保存那个栈顶元素
		E element = data[size - 1];
		// 然后清空栈顶元素，
		data[size] = null;
		//栈中元素减少一个
		size--;
		return element;
	}

	/**
	 * 入栈的时候先判断 栈是否已满，如栈满就扩充
	 */
	public void push(E target) {
		if(target==null)
			throw new RuntimeException("插入的元素不可为NULL");
		if (isFull()) {
			enlarge();

		}
		size++;
		// 往栈顶添加一个元素
		data[size - 1] = target;
		

	}

	/**
	 * 扩充数组长度 注意：一般是判断栈满以后 才扩充 扩充为原数组的两倍长度
	 */
	public void enlarge() {
		E[] newData = (E[]) new Object[data.length * 2];
		for (int i = 0; i < size; i++) {
			newData[i] = data[i];

		}
		data = newData;

	}

	/**
	 * 如果数组的长度 已经等于 放入栈中的元素数量 则表示 栈已经满了
	 * 
	 * @return
	 */
	public boolean isFull() {

		return data.length == size;
	}

}
package com.framework.collection;
/**
 * Stack接口
 * @author 鼎鼎
 *
 * @param <E>
 */
public interface Stack<E> {
    /**
       * 判断栈是否为空
     * @return
       */     
 public boolean isEmpty();
      /**
       * 返回栈中元素个数
       * @return
       */
      public int size();
      /**
       * 入栈
       * @param target
       * 
       */
      public void push(E target);
      /**
       * 出栈
       * @return E
       */
      public E pop();
      /**
       * 返回栈顶元素，并不出栈
       * @return
       */
      public E top();
      
     
}


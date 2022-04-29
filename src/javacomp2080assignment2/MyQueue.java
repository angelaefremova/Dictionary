package javacomp2080assignment2;

public class MyQueue<T> {
  private int load;
  private LinkedNode<T> head, tail;
  
  public MyQueue() {
    this.load = 0;
    this.head = this.tail = null;
  }
  
  public boolean isEmpty() {
    return this.head == null;
  }
  
  public void enQueue(T item) {
    LinkedNode new_word = new LinkedNode( item);
    if (this.isEmpty())
      this.head = this.tail = new_word;
    else {
      this.tail.next = new_word;
      this.tail = this.tail.next;
    }
    this.load++;
  }
  
  public T deQueue() {
    T pop_out = null;
    if (!(this.isEmpty())) {
      pop_out = this.head.word;
      this.head = this.head.next;
    }
    this.load--;
    return pop_out;
  }
  
  public int length() {
    return this.load;
  }
}


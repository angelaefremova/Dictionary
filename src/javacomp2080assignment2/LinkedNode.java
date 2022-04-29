package javacomp2080assignment2;

public class LinkedNode<K> {
  public K word;
  public LinkedNode next;
  
  public LinkedNode(K word) {
    this.word = word;
    this.next = null;
  }
}

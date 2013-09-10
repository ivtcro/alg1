import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item>  implements Iterable<Item>{
    
   private class Node<Item> {
       private Item value;
       private Node<Item> next;       
       private Node<Item> previous;
       
       public Node()
       {
           this.value = null;
           this.next = null;
           this.previous = null;           
       }
       
       public void setValue(Item value)
       {
           this.value=value;
       }

       public Item getValue()
       {
           return value;
       }
       
       public void setNext(Node<Item> node)
       {
           this.next = node;
       }       

       public Node<Item> getNext()
       {
           return next;
       }       
       public void setPrevious(Node<Item> node)
       {
           this.previous = node;
       }       

       public Node<Item> getPrevious()
       {
           return previous;
       }       
       
   }
    
   private Node<Item> head;
   private Node<Item> tail;
   private int size;   
   
   public Deque()                     // construct an empty deque
   {
       head = null;
       tail = null;
       size = 0;
       
   }
   public boolean isEmpty()           // is the deque empty?
   {
       return (head == null || tail == null);
   }
   public int size()                  // return the number of items on the deque
   {
       return size;
   }       
   public void addFirst(Item item)    // insert the item at the front
   {
       if (item == null)
           throw new NullPointerException("NULL value couln't be added in to the structure");
       
       Node<Item> tmp = new Node<Item>();
       tmp.setValue(item);
       
       if(!this.isEmpty())           
       {
           head.setPrevious(tmp);
           tmp.setNext(head);
       }
       else
       {
           tail = tmp;
       }
       
       head = tmp;
       size++;
   }
   public void addLast(Item item)     // insert the item at the end
   {
       if (item == null)
           throw new NullPointerException("NULL value couln't be added in to the structure");
       
       Node<Item> tmp = new Node<Item>();
       tmp.setValue(item);
       
       if(!this.isEmpty())           
       {                  
           tmp.setPrevious(tail);
           tail.setNext(tmp);
       }
       else
       {
           head = tmp;
       }
       
       tail = tmp;
       size++;
   }
   public Item removeFirst()          // delete and return the item at the front
   {
       if (this.isEmpty())
           throw new NoSuchElementException("No elements in the Deque");
       
       Item tmp = head.getValue();
       head = head.getNext();
       
       if (head != null)
       {
           head.setPrevious(null);           
       }
       else
       {
           tail = null;
       }
       
       size--;
       return tmp;
   }
   public Item removeLast()           // delete and return the item at the end
   {
       if (this.isEmpty())
           throw new NoSuchElementException ("No elements in the Deque");
       
       Item tmp = tail.getValue();
       tail = tail.getPrevious();
       
       if ( tail != null)
       {
           tail.setNext(null);           
       }
       else
       {
           head = null;
       }
       
       size--;
       return tmp;
   }
   public Iterator<Item> iterator()   // return an iterator over items in order from front to end
   {
       return new ListIterator();
   }
   
   private class ListIterator implements Iterator<Item> {
       private Node<Item> current = head;
       public boolean hasNext() {return current != null;}
       public void remove() {throw new UnsupportedOperationException("remove() operation is depricated"); }
       public Item next() {
       
           if (current == null)
               throw new NoSuchElementException("No more elements in the Deque");
       
           Item item = current.getValue();
           current = current.getNext();
           return item;
       }
   }
   
   public void dump()
   {   
       System.out.print("DUMP(" + size + "): "); 
       for(Item i : this)
           System.out.print(i + ";"); 
       System.out.println(""); 
   }
   
   public static void main(String[] args)
   {
       Deque<Integer> deque = new Deque<Integer>();
       deque.addFirst(1);
       deque.dump();
       deque.addFirst(0);
       deque.dump();
       deque.addLast(2);
       deque.dump();
       deque.addLast(3);
       deque.dump();
       deque.addFirst(-1);
       deque.dump();       
       deque.addFirst(-2);
       deque.dump();          
       System.out.println(deque.removeLast());
       deque.dump();
       System.out.println(deque.removeLast());
       deque.dump();
       System.out.println(deque.removeFirst());
       deque.dump();       
       System.out.println(deque.removeFirst());
       deque.dump();       

     
       System.out.println(deque.removeLast());
       System.out.println(deque.removeLast());
       deque.dump();
       deque.iterator().next();

   }
}
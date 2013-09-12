import java.util.Iterator;
//import java.util.Arrays;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
   
   private int arraySize = 10;
   
   private Item[] array;   
   private int size;
   
   private int[] shuffle()
   {
       int random[] = new int[size];
       for (int i=0;i<size;i++)
       {
           random[i] = i;
       }
       
       StdRandom.shuffle(random);
       return random;
   }
           

   private void resize(int newArraySize)
   {
       Item[] newArray = (Item [])new Object[newArraySize];
       for (int i = 0; i < Math.min(arraySize, newArraySize); i++)
       {
           newArray[i] = array[i];
       }
       
       arraySize = newArraySize;
       array = newArray;
   }
           
   public RandomizedQueue()           // construct an empty randomized queue
   {
       array = (Item [])new Object[arraySize];
       size = 0;
   }
   public boolean isEmpty()           // is the queue empty?
   {
       return (size == 0);
   }
   public int size()                  // return the number of items on the queue
   {
       return size;
   }
   public void enqueue(Item item)     // add the item
   {       
       if (item == null)
           throw new NullPointerException("NULL value couln't be added in to the structure");
       
       if( arraySize == (size + 1))
           resize(arraySize * 2);
       array[size] = item;
       size++;
   }
   public Item dequeue()              // delete and return a random item
   {
       if (this.isEmpty())
           throw new NoSuchElementException("No elements in the RandomizedQueue");

       int index = (int) (Math.random() * size);
       Item tmp = array[index];
       
       if (index != size)
           array[index] = array [(size-1)];
       
       array [(size-1)] = null;
       size--;
       
       if (size == (int) (arraySize / 4))
           resize( (int) (arraySize / 2));
       
       return tmp;
   }
   
   public Item sample()               // return (but do not delete) a random item
   {
       if (this.isEmpty())
           throw new NoSuchElementException("No elements in the RandomizedQueue");

       int index = (int) (Math.random() * size);
       return array[index];
   }
       
   public Iterator<Item> iterator()   // return an independent iterator over items in random order
   {
       return new ListIterator();
   }
   
   private class ListIterator implements Iterator<Item> {
       
       private int[] random = shuffle();
       
       private int current = 0;
              
       public boolean hasNext() {return (size - current)  > 0;}
       public void remove() {throw new UnsupportedOperationException("remove() operation is depricated"); }
       public Item next() {
       
           if (!hasNext())
               throw new NoSuchElementException("No more elements in the Queue");
       
           // TODO: create copy of object to return
           Item item = array[random[current]];
           current++;
           return item;
       }
   }
    /*public static void main(String[] args)
    {
       
       RandomizedQueue<String> rq = new RandomizedQueue <String>();
       while (!StdIn.isEmpty())
           rq.enqueue(StdIn.readString());
       Iterator itr = rq.iterator();
       while (itr.hasNext())
               System.out.println(itr.next());
       
       System.out.println("-" + rq.dequeue());
       System.out.println("-" + rq.dequeue());
       itr = rq.iterator();
       while (itr.hasNext())
               System.out.println(itr.next());
       }*/
    }

   

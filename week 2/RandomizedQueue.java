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
       
       for (int i=0;i<size;i++)
       {
           int j = (int) (Math.random() * size );
           int tmp = random[j];
           random[j]= random[i];
           random[i] = tmp;
       }
       
       //System.out.println(Arrays.toString(random));
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
       if( arraySize == (size + 1))
           resize(arraySize * 2);
       array[size] = item;
       size++;
   }
   public Item dequeue()              // delete and return a random item
   {
       int index = (int) (Math.random() * size);
       Item tmp = array[index];
       
       if ( index != size)
           array[index] = array [size];
       
       size--;
       
       if (size == (int) (arraySize/4))
           resize( (int) (arraySize / 2));
       
       return tmp;
   }
   
   public Item sample()               // return (but do not delete) a random item
   {
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
}
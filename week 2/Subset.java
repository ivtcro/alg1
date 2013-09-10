import java.util.Iterator;

public class Subset {
    public static void main(String[] args)
    {
       
       RandomizedQueue<String> rq = new RandomizedQueue <String>();
       while (!StdIn.isEmpty())
           rq.enqueue(StdIn.readString());
       
       Iterator itr = rq.iterator();
       for (int i=0;i < Integer.parseInt(args[0]); i++)
       {
           if (itr.hasNext())
               System.out.println(itr.next());
       }
    }
}    
       
        
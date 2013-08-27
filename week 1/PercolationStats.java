import java.util.*;

public class PercolationStats {
    
   private double fractions[];
   private int repeat_number;   
   
   public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   {
       if( N <= 0 || T <= 0 )
           throw new IndexOutOfBoundsException("ERROR: Incorrect input data");
       
       fractions = new double[T];
       Arrays.fill(fractions, 0);
       
       repeat_number = T;
                  
       for (int i = 0; i < T; i++)
       {
           Percolation test = new Percolation(N);
           int p = 0;
           int q = 0;
           int counter = 0;        
           
           while (!test.percolates())
           {
            
                p = 1 + (int) (Math.random() * N);
                q = 1 + (int) (Math.random() * N);
                
                if(test.isFull(p,q))
                {
                    //System.out.println("Iteration #"+ counter+ ".p="+ p +",q="+q);
                    test.open(p,q);
                    //test.dump();                    
                    counter++;
                }
           }
        //test.dump();                    
        fractions[i]=(double)counter / (double) N / (double) N;
       }     
       
   }

   public double mean()                     // sample mean of percolation threshold
   {
       double sum = 0;
       
       for (int i = 0; i < fractions.length; i++) 
            sum += fractions[i];
       
       return (sum/repeat_number);
   }
   
   public double stddev()                   // sample standard deviation of percolation threshold
   {
       double mean = this.mean();
       double stddev = 0;
       
       for (int i = 0; i < fractions.length; i++) 
            stddev += Math.pow((fractions[i] - mean), 2);
       
       return Math.sqrt( stddev / (repeat_number - 1));   
       
   }
   
   public double confidenceLo()             // returns lower bound of the 95% confidence interval
   {
       return (mean() - (double)1.96 * stddev() / Math.sqrt(repeat_number) );
   }
       
   public double confidenceHi()             // returns upper bound of the 95% confidence interval
   {
       return (mean() + (double)1.96 * stddev() / Math.sqrt(repeat_number) );
   }
       
   public static void main(String[] args)   // test client, described below
   {
       PercolationStats stat = new PercolationStats (Integer.parseInt(args[0]),Integer.parseInt(args[1]));
       System.out.println("mean\t\t\t\t = " + stat.mean());
       System.out.println("stddev\t\t\t\t = " + stat.stddev());
       System.out.println("95% confidence interval\t\t = " + stat.confidenceLo() + " " + stat.confidenceHi());
       
   }
       
}
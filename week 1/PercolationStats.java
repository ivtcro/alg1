import java.util.Arrays;

public class PercolationStats {
    
   private double[] fractions;
   private int repeatNumber;   
   
   // perform T independent computational experiments on an N-by-N grid
   public PercolationStats(int N, int T)    
   {
       if (N <= 0 || T <= 0)
           throw new IllegalArgumentException("ERROR: Incorrect input data");
       
       fractions = new double[T];
       Arrays.fill(fractions, 0);
       
       repeatNumber = T;
                  
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
                
                if (!test.isOpen(p, q))
                {
                    test.open(p, q);
                    counter++;
                }
           }
        fractions[i] = (double) counter / (double) N / (double) N;
       }     
       
   }

   public double mean()                     // sample mean of percolation threshold
   {
       double sum = 0;
       
       for (int i = 0; i < fractions.length; i++) 
            sum += fractions[i];
       
       return (sum/repeatNumber);
   }
   
   // sample standard deviation of percolation threshold
   public double stddev()                   
   {
       if (repeatNumber == 1)
           return Double.NaN;
       
       double mean = this.mean();
       double stddev = 0;
       
       for (int i = 0; i < fractions.length; i++)
       {
           double x = fractions[i] - mean;
           stddev += x*x;
       }
       
       return Math.sqrt(stddev / (repeatNumber - 1));   
       
   }
   
    // returns lower bound of the 95% confidence interval
   public double confidenceLo()            
   {
       return (mean() - (double) 1.96 * stddev() / Math.sqrt(repeatNumber));
   }
       
   // returns upper bound of the 95% confidence interval
   public double confidenceHi()             
   {
       return (mean() + (double) 1.96 * stddev() / Math.sqrt(repeatNumber));
   }
   
   // test client, described below
   public static void main(String[] args)   
   {
       PercolationStats stat = new PercolationStats(
              Integer.parseInt(args[0]), Integer.parseInt(args[1]));
       System.out.println("mean\t\t\t\t = " + stat.mean());
       System.out.println("stddev\t\t\t\t = " + stat.stddev());
       System.out.println("95% confidence interval\t\t = " + stat.confidenceLo() 
                              + " " + stat.confidenceHi());
       
   }
       
}
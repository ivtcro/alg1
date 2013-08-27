import java.util.*;

public class PercolationStats {
    
    private boolean[][] board;
   
   //public PercolationStats(int N, int T)    // perform T independent computational experiments on an N-by-N grid
   //public double mean()                     // sample mean of percolation threshold
   //public double stddev()                   // sample standard deviation of percolation threshold
   //public double confidenceLo()             // returns lower bound of the 95% confidence interval
   //public double confidenceHi()             // returns upper bound of the 95% confidence interval
    public static void main(String[] args)   // test client, described below
    {
        System.out.println("Start...");
        int p = 0;
        int q = 0;
        int counter = 0;        
        Percolation test = new Percolation(10);
        while (!test.percolates())
        {
            
                p = 1 + (int) (Math.random() * 10);
                q = 1 + (int) (Math.random() * 10);
                
                if(test.isFull(p,q))
                {
                    //System.out.println("Iteration #"+ counter+ ".p="+ p +",q="+q);
                    test.open(p,q);
                    //test.dump();                    
                    counter++;
                }
        }
        test.dump();                    
        System.out.println(counter);
        
    }
       
}
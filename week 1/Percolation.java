import java.util.Arrays;

public class Percolation {    
    
    private int[] grid;
    private int[] weight;

    private int dimension;
    
            
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)              
    {
        dimension = N;
        // Two additional elemnts for "virtual" top and bottom sites.
        grid = new int[N*N+2];
        weight = new int[N*N + 2];
        
        //Initialize grid with -1 values(except top and bottom). 
        //It means that site is block.
        Arrays.fill(grid, -1);
        Arrays.fill(weight, -1);
        
        grid[0] = 0;
        grid[N*N+1] = N*N+1;
        weight[0] = 1;
        weight[N*N+1] = 1;

    }

    /*
    public void dump()
    {
        for(int j=1; j<=dimension;j++)
        {
            for(int i=1; i<=dimension;i++)
                System.out.print(grid[i+(j-1)*dimension] + " ");
            System.out.print(" -> ");
            for(int i=1; i<=dimension;i++)
                System.out.print(weight[i+(j-1)*dimension] + " ");            
            System.out.println("");
        }
        
        System.out.print(grid[0]+", "+grid[dimension*dimension +1]+ " -> ");
        System.out.println(weight[0]+", "+weight[dimension*dimension +1]);
        
    }
    */       
        
    private void union(int ax, int ay, int bx, int by)
    {
        int an = find(ay + (ax - 1)*dimension);
        int bn = find(by + (bx - 1)*dimension);
        if (an != bn)
        {
            if (weight[an] > weight[bn])
            {
                weight[an] += weight[bn];
                grid[bn] = an;
            }
            else
            {
                weight[bn] += weight[an];
                grid[an] = bn;
            }            
        }
    }
    
    // find root
    private int find(int n)
    {
        int i = n;
        while (grid[i] != i)
            i = grid[i];
        return i;
    }
    
    // open site (row i, column j) if it is not already
    public void open(int i, int j)         
    {
        // If it's already opened - do nothing
        if (!isOpen(i, j))
        {
            int n = j + (i -1) * dimension;
            grid[n] = n;

            //If the cell not the last in the row
            if (i < dimension)
                if (isOpen(i+1, j))
                   union(i, j, i+1, j);
            //If the cell not the first in the row
            if (i > 1)
                if (isOpen(i-1, j))
                   union(i, j, i-1, j);                

            //If the cell not the last in the column
            if (j < dimension)
                if (isOpen(i, j+1))
                   union(i, j, i, j+1);
            //If the cell not the first in the column
            if (j > 1)
                if (isOpen(i, j-1))
                   union(i, j, i, j-1);
            
            if (i == 1)
                //(1,0) = top element
                union(1, 0, i, j);
            
            if (i == dimension)
                //(N+1,N) = bottom element
                union(dimension, dimension + 1, i, j);
        }
        
    }
                
    // open site (row i, column j) if it is not already
    public boolean isOpen(int i, int j)    
    {
        if (i > dimension || i < 1 
            || j > dimension || j < 1)
            throw new IndexOutOfBoundsException("ERROR: Check array indexes");
        if (grid[j+(i-1)*dimension] < 0)
            return false;
        return true;
    }
    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        return (isOpen(i, j) && (find(0) == find(j + (i-1) * dimension)));
    }
    public boolean percolates()            // does the system percolate?
    {
        return (find(0) == find(dimension*dimension+1));
    }
}
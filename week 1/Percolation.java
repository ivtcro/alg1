    
public class Percolation {    
    
    private int grid [];
    private int weight[];

    private int dimension;
    
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
            
        
    private void union(int a_x, int a_y, int b_x, int b_y)
    {
        int a_n = find(a_x + (a_y - 1)*dimension);
        int b_n = find(b_x + (b_y - 1)*dimension);
        if(a_n != b_n)
        {
            if( weight[a_n] > weight[b_n] )
            {
                weight[a_n] += weight[b_n];
                grid[b_n] = a_n;
            }
            else
            {
                weight[b_n] += weight[a_n];
                grid[a_n] = b_n;
            }            
        }
    }
    
    // find root
    private int find(int n)
    {
        //System.out.println("n " + n);
        //System.out.println("grid[n]" + grid[n]);
        while(grid[n] != n)
            n = grid[n];
        return n;
    }
        
    public Percolation(int N)              // create N-by-N grid, with all sites blocked
    {
        dimension=N;
        // Two additional elemnts for "virtual" top and bottom sites.
        grid = new int[N*N+2];
        weight = new int[N*N + 2];
        
        //Initialize grid with -1 values(except top and bottom). It means that site is block.
        for (int i = 1; i < N*N+1; i++)
        {
            grid[i] = -1;
            weight[i] = 1;
        }
        
        grid[0] = 0;
        grid[N*N+1] = N*N+1;
        weight[0] = 1;
        weight[N*N+1] = 1;

    }
    
    public void open(int i, int j)         // open site (row i, column j) if it is not already
    {
        // If it's already opened - do nothing
        if(isFull(i,j))
        {
            int n = i + (j -1) * dimension;
            grid[n] = n;

            //If the cell not the elast in the line
            if(i < dimension)
                if(isOpen(i+1,j))
                   union(i,j,i+1,j);
            //If the cell not the first in the line
            if(i > 1)
                if(isOpen(i-1,j))
                   union(i,j,i-1,j);                

            //If the cell not the last in the row
            if(j < dimension)
                if(isOpen(i,j+1))
                   union(i,j,i,j+1);
            //If the cell not the first in the row
            if(j > 1)
                if(isOpen(i,j-1))
                   union(i,j,i,j-1);
            
            if(j == 1)
                //(0,1) = top element
                union(0,1,i,j);
            
            if(j == dimension)
                //(N+1,N) = bottom element
                union(dimension+1,dimension,i,j);
        }
        
    }
                
    // TODO: Exception check should be added
    public boolean isOpen(int i, int j)    // is site (row i, column j) open?
    {
        if ( i > dimension || i < 1 ||
            j > dimension || j < 1)
            throw new IndexOutOfBoundsException("ERROR: Check array indexes");
        if (grid[i+(j-1)*dimension] < 0)
            return false;
        return true;
    }
    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        return !isOpen(i,j);
    }
    public boolean percolates()            // does the system percolate?
    {
        return (find(0) == find(dimension*dimension+1));
    }
}
public class Percolation {    
    
    private WeightedQuickUnionUF grid;
    private boolean[][] state;
    private boolean[][] water;

    private int dimension;
    
            
    // create N-by-N grid, with all sites blocked
    public Percolation(int N)              
    {
        dimension = N;
        // Two additional elements for "virtual" top and bottom sites.
        grid = new WeightedQuickUnionUF(N*N+2);
        
        // false - closed, true - opened
        state = new boolean[N][N];
        
        // false - empty, true - filled
        water = new boolean[N][N];

    }

    private int getIndex(int i, int j)
    {
        return (j + (i -1) * dimension);
    }
        
    private void flow(int i, int j)
    {
        if (i < 1 || i > dimension || j < 1 || j > dimension)
            return;
        
        if (isOpen(i, j) && !isFull(i, j))
        {
            water[i-1][j-1] = true;
            flow(i, j-1);
            flow(i, j+1);
            flow(i-1, j);
            flow(i+1, j);
        }
    }
    // open site (row i, column j) if it is not already
    public void open(int i, int j)         
    {
        // If it's already opened - do nothing
        if (!isOpen(i, j))
        {
            state[i-1][j-1] = true;
            
            //If the cell not the last in the column
            if (i < dimension)
                if (isOpen(i+1, j))
                {
                   grid.union(getIndex(i, j), getIndex(i+1, j));
                   if (isFull(i+1, j))
                       water[i-1][j-1] = true;
                }
            //If the cell not the first in the column
            if (i > 1)
                if (isOpen(i-1, j))
                {
                   grid.union(getIndex(i, j), getIndex(i-1, j));
                   if (isFull(i-1, j))
                       water[i-1][j-1] = true;
                }

            //If the cell not the last in the row
            if (j < dimension)
                if (isOpen(i, j+1))
                {
                   grid.union(getIndex(i, j), getIndex(i, j+1));
                   if (isFull(i, j+1))
                       water[i-1][j-1] = true;
                }
            //If the cell not the first in the row
            if (j > 1)
                if (isOpen(i, j-1))
                {
                   grid.union(getIndex(i, j), getIndex(i, j-1));
                   if (isFull(i, j-1))
                       water[i-1][j-1] = true;
                }
            
            if (i == 1)
            {
                //0 = top element
                grid.union(0, getIndex(i, j));
                water[i-1][j-1] = true;
            }
            
            if (i == dimension)
                //(N+1,N) = bottom element
                grid.union((dimension * dimension + 1), getIndex(i, j));
            
            // Fill with "water"
            if (water[i-1][j-1])
            {
                flow(i, j-1);
                flow(i, j+1);
                flow(i-1, j);
                flow(i+1, j);
            }
                
            
        }
        
    }
                
    // open site (row i, column j) if it is not already
    public boolean isOpen(int i, int j)    
    {
        if (i > dimension || i < 1 
            || j > dimension || j < 1)
            throw new IndexOutOfBoundsException("ERROR: Check array indexes");
        
        return state[i-1][j-1];
    }
    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        if (i > dimension || i < 1 
            || j > dimension || j < 1)
            throw new IndexOutOfBoundsException("ERROR: Check array indexes");

        return water[i-1][j-1];
    }
    public boolean percolates()            // does the system percolate?
    {
        return grid.connected(0, (dimension * dimension + 1));
    }
}
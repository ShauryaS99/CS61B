package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private int length;
    private WeightedQuickUnionUF wqu;
    private WeightedQuickUnionUF wqu2;
    private int topsite;
    private int bottomsite;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        length = N;
        grid = new boolean[length][length];
        wqu = new WeightedQuickUnionUF((length * length) + 2);
        wqu2 = new WeightedQuickUnionUF((length * length) + 1);
        topsite = (length * length); //sets topsite to 1 above max possible numbered block
        bottomsite = (length * length) + 1; //sets bottomsite to 2 above max possible numbered block


        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                grid[i][j] = false;
            }
        }
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
    //throws error if there is an index out of bounds
        if (row < 0 || row > length - 1 || col < 0 || col > length - 1) {
            throw new java.lang.IndexOutOfBoundsException();
        }

        if (!(isOpen(row, col))) {
            grid[row][col] = true;
        }
        int pos = xyTo1D(row, col);

    //connects top blocks to topsite and bottom blocks to bottom site
        if (row == 0) {
            wqu.union(pos, topsite);
        } else if (row == length - 1) {
            wqu.union(pos, bottomsite);
        }

    //unions adj blocks if they are open
        if (row != length - 1 && isOpen(row + 1, col)) {
            int posdown = xyTo1D(row + 1, col);
            wqu.union(pos, posdown);
        }
        if (row != 0 && isOpen(row - 1, col)) {
            int posup = xyTo1D(row - 1, col);
            wqu.union(pos, posup);
        }
        if (col != length - 1 && isOpen(row, col + 1)) {
            int posright = xyTo1D(row, col + 1);
            wqu.union(pos, posright);
        }
        if (row != 0 && isOpen(row, col - 1)) {
            int posleft = xyTo1D(row, col - 1);
            wqu.union(pos, posleft);
        }

    }

    //changes xy coordinates to 1Dimensional coordinates
    public int xyTo1D(int r, int c) {
        int xy = (r * length) + c;
        return xy;
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (grid[row][col]) {
            return true;
        }
        return false;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        int pos = xyTo1D(row, col);
        if (wqu.connected(pos, topsite)) {
            return true;
        }
        return false;
    }

    // number of open sites
    public int numberOfOpenSites() {
        int numopen = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (isOpen(i, j)) {
                    numopen += 1;
                }
            }
        }
        return numopen;
    }

    // does the system percolate?
    public boolean percolates() {
        if (wqu.connected(topsite, bottomsite)) {
            return true;
        }
        return false;
    }

    //

    // use for unit testing (not required)
    public static void main(String[] args) {
        /**Percolation p = new Percolation(5);
        p.open(3,4);
        p.open(2,4);
        p.open(2,2);
        p.open(2,3);
        p.open(0,2);
        p.open(1,2);
        p.open(4,4);
        boolean test = p.percolates();
        System.out.println(test);*/
    }
}

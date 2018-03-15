package hw2;

import edu.princeton.cs.introcs.StdRandom;
import edu.princeton.cs.introcs.StdStats;

//import java.util.Random;

public class PercolationStats {
    private double[] xt;
    private int T;


    // perform T independent experiments on an N-by-N grid
    public PercolationStats(int N, int T, PercolationFactory pf) {
        if (N <= 0 || T <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        xt = new double[T];
        this.T = T;
        for (int i = 0; i < T; i++) {
            Percolation p = pf.make(N);
            while (!p.percolates()) {
                int randrow = StdRandom.uniform(N);
                int randcol = StdRandom.uniform(N);
                if (!p.isOpen(randrow, randcol)) {
                    p.open(randrow, randcol);
                }
            }
            xt[i] = p.numberOfOpenSites() / (N * N);
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        /**
        double sum = 0;
        for (int i = 0; i < xt.length; i++) {
            sum += xt[i];
        }
        return (sum / this.T);
         */
        return StdStats.mean(xt);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        /**
        double sum = 0;
        double mean = mean();
        for (int i = 0; i < xt.length; i++) {
            sum += Math.pow(xt[i] - mean, 2);
        }
        return Math.sqrt(sum / (this.T - 1));
         */
        return StdStats.stddev(xt);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLow() {
        double mean = mean();
        double stddev = stddev();
        return mean - ((1.96 * stddev) / Math.sqrt(this.T));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHigh() {
        double mean = mean();
        double stddev = stddev();
        return mean + ((1.96 * stddev) / Math.sqrt(this.T));
    }

}

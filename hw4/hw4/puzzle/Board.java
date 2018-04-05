package hw4.puzzle;
import edu.princeton.cs.algs4.Queue;

//@source url http://joshh.ug/neighbors.html

public class Board implements WorldState {
    private int size;
    private int[][] board;
    public Board(int[][] tiles) {
        int lenAndWidth = tiles.length;
        board = new int[lenAndWidth][lenAndWidth];
        for (int i = 0; i < lenAndWidth; i++) {
            for (int j = 0; j < lenAndWidth; j++) {
                board[i][j] = tiles[i][j];
            }
        }
        size = board[0].length;
    }
    public int tileAt(int i, int j) {
        if (i < 0 || i > board[0].length || j < 0 || j > board.length) {
            throw new java.lang.IndexOutOfBoundsException();
        }
        return board[i][j];
    }
    public int size() {
        return size;
    }

    @Override
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == 0) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = 0;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = 0;
                }
            }
        }
        return neighbors;
    }
    public int hamming() {
        int lenAndWidth = board.length;
        int outSpot = 0;
        for (int i = 0; i < lenAndWidth; i++) {
            for (int j = 0; j < lenAndWidth; j++) {
                int val = board[i][j];
                if (val == 0) {
                    continue;
                } else if (val != (j + 1 + (i * lenAndWidth))) {
                    outSpot += 1;
                }
            }
        }
        return outSpot;
    }
    public int manhattan() {
        int lenAndWidth = board.length;
        int outSpot = 0;
        for (int i = 0; i < lenAndWidth; i++) {
            for (int j = 0; j < lenAndWidth; j++) {
                int val = board[i][j];
                int col = val % lenAndWidth - 1;
                int row = val / lenAndWidth;
                if (col == -1) {
                    col = lenAndWidth - 1;
                    row -= 1;
                }
                if (val == 0) {
                    continue;
                } else {
                    outSpot = outSpot + Math.abs(i - row) + Math.abs(j - col);
                }
            }
        }
        return outSpot;
    }
    public int estimatedDistanceToGoal() {
        return manhattan();
    }
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (y == null || getClass() != y.getClass()) {
            return false;
        }
        boolean equal = true;
        int lenAndWidth = board.length;
        Board x = (Board) y;
        if (x.size != this.size) {
            return false;
        }
        for (int i = 0; i < lenAndWidth; i++) {
            for (int j = 0; j < lenAndWidth; j++) {
                if (this.board[i][j] != (x.board[i][j])) {
                    return false;
                }
            }
        }
        return equal;
    }
    public int hashCode() {
        return 0;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i, j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}

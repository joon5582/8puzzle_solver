/* *****************************************************************************
 *  Name: Junwoo Lee
 *  Date: 6/3/2020
 *  Description: https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php
 *  I have done all the coding by myself
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;

public class Board {

    private char[] nowtiles;
    private int zerorow = -1;
    private int zerocol = -1;
    private int n;
    private int hamming = -1;
    private int manhattannum = -1;

    private int xyToid(int row, int col) {
        return row * n + col;
    }


    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    private Board(char[] tiles) {
        n = (int) Math.sqrt((double) tiles.length);
        nowtiles = tiles.clone();


    }

    public Board(int[][] tiles) {
        n = tiles.length;
        nowtiles = new char[n * n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int id = xyToid(i, j);
                nowtiles[id] = (char) tiles[i][j];
            }
        }


    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", (int) nowtiles[xyToid(i, j)]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;

    }


    // number of tiles out of place
    public int hamming() {
        if (hamming == -1) {
            int hammingnum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (nowtiles[xyToid(i, j)] != 0) {
                        int a = (int) nowtiles[xyToid(i, j)];
                        int arow = (a - 1) / n;
                        int acol = (a - 1) % n;
                        if (i != arow || j != acol) hammingnum++;
                    }

                }
            }
            hamming = hammingnum;
        }
        return hamming;


    }


    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        if (manhattannum == -1) {
            manhattannum = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (nowtiles[xyToid(i, j)] != 0) {
                        int a = nowtiles[xyToid(i, j)];
                        int arow = (a - 1) / n;
                        int acol = (a - 1) % n;
                        manhattannum += Math.abs(i - arow) + Math.abs(j - acol);
                    }

                }
            }
        }
        return manhattannum;

    }

    // is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;


    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y.getClass() != Board.class) return false;
        Board temp = (Board) y;
        if (this.n != temp.n) return false;
        return Arrays.equals(nowtiles, temp.nowtiles);

    }


    private void zeros() {
        outerLoop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (nowtiles[xyToid(i, j)] == 0) {
                    zerorow = i;
                    zerocol = j;
                    break outerLoop;
                }
            }
        }

    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        if (zerorow == -1) zeros();

        ArrayList<Board> temp = new ArrayList<Board>();

        if (zerorow < n - 1) {
            temp.add(new Board(tileswap(nowtiles, zerorow, zerocol, zerorow + 1, zerocol)));
            tileswap(nowtiles, zerorow, zerocol, zerorow + 1, zerocol);
        }
        if (zerorow > 0) {
            temp.add(new Board(tileswap(nowtiles, zerorow, zerocol, zerorow - 1, zerocol)));
            tileswap(nowtiles, zerorow, zerocol, zerorow - 1, zerocol);
        }
        if (zerocol < n - 1) {
            temp.add(new Board(tileswap(nowtiles, zerorow, zerocol, zerorow, zerocol + 1)));
            tileswap(nowtiles, zerorow, zerocol, zerorow, zerocol + 1);
        }
        if (zerocol > 0) {
            temp.add(new Board(tileswap(nowtiles, zerorow, zerocol, zerorow, zerocol - 1)));
            tileswap(nowtiles, zerorow, zerocol, zerorow, zerocol - 1);
        }

        return temp;
    }


    private char[] tileswap(char[] tiles, int row1, int col1, int row2, int col2) {

        char temp;
        temp = tiles[xyToid(row1, col1)];
        tiles[xyToid(row1, col1)] = tiles[xyToid(row2, col2)];
        tiles[xyToid(row2, col2)] = temp;
        return tiles;
    }


    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        int onerow = -1;
        int onecol = -1;
        int tworow = -1;
        int twocol = -1;
        outerLoop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (nowtiles[xyToid(i, j)] == 1) {
                    onerow = i;
                    onecol = j;
                    break outerLoop;
                }
            }
        }
        outerLoop:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (nowtiles[xyToid(i, j)] == 2) {
                    tworow = i;
                    twocol = j;
                    break outerLoop;
                }
            }
        }
        Board a = new Board(tileswap(nowtiles, onerow, onecol, tworow, twocol));
        tileswap(nowtiles, onerow, onecol, tworow, twocol);
        return a;

    }


    // unit testing (not graded)
    public static void main(String[] args) {


    }

}

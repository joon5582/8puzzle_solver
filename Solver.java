/* *****************************************************************************
 *  Name: Junwoo Lee
 *  Date: 6/3/2020
 *  Description: https://coursera.cs.princeton.edu/algs4/assignments/8puzzle/specification.php
 *  I have done all the coding by myself
 **************************************************************************** */
import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;

public class Solver {


    private Node first;
    private Node second;
    private boolean issolvable;
    private ArrayList<Board> firstsolution;

    private int moves;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        firstsolution = new ArrayList<>();

        first = new Node(initial);
        second = new Node(initial.twin());
        process();


    }

    private void process() {
        MinPQ<Node> firstPQ = new MinPQ<Node>();
        MinPQ<Node> secondPQ = new MinPQ<Node>();


        first.prev = null;
        first.move = 0;
        first.priority = first.move + first.board.manhattan();
        second.prev = null;
        second.move = 0;
        second.priority = second.move + second.board.manhattan();
        firstPQ.insert(first);

        secondPQ.insert(second);


        while (!(firstPQ.min().board.isGoal() || secondPQ.min().board.isGoal())) {
            nextstep(firstPQ);
            nextstep(secondPQ);
        }
        Node temp = firstPQ.min();
        while (temp != null) {
            firstsolution.add(temp.board);
            temp = temp.prev;
        }
        if (firstPQ.min().board.isGoal()) {
            issolvable = true;
        }
        else issolvable = false;
        Collections.reverse(firstsolution);
        moves = firstPQ.min().move;


    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return issolvable;
    }

    private void nextstep(MinPQ<Node> a) {

        Node temp = a.delMin();


        for (Board board : temp.board.neighbors()) {
            if (temp.prev == null) {
                Node temp2 = new Node(board, temp);
                a.insert(temp2);

            }

            else if (!temp.prev.board.equals(board)) {
                Node temp2 = new Node(board, temp);
                a.insert(temp2);
            }

            else {

                board = null;

            }
        }


    }


    private class Node implements Comparable<Node> {
        private int priority;
        private int move;
        private Board board;
        private Node prev;

        private Node(Board b, Node prev) {
            this.board = b;
            this.prev = prev;
            this.move = prev.move + 1;
            this.priority = move + board.manhattan();
        }


        public Node(Board b) {

            board = b;
        }

        public int compareTo(Node o) {
            if (this.priority > o.priority)
                return 1;
            if (this.priority < o.priority)
                return -1;

            else return 0;

        }


    }

    // min number of moves to solve initial board
    public int moves() {
        if (isSolvable())
            return moves;
        else
            return -1;

    }

    // sequence of boards in a shortest solution
    public Iterable<Board> solution() {
        if (isSolvable())
            return firstsolution;
        else return null;

    }

    // test client (see below)
    public static void main(String[] args) {

    }

}

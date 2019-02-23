public class LinkedListNQueensSolution {

    public class Node {

        private Node top;

        private Node bottom;

        private Node left;

        private Node right;

        private int occupiedNum;

    }

    private int j;

    private boolean safe;

    private Node[][] board;

    private Node pointer;

    private Node[] rec;

    public static final int N = 8;

    private void considerFirstColumn() {
        j = 1;
        pointer = board[0][1];
    }

    private void considerNextColumn() {
        rec[j++] = pointer;
        safe = false;
        pointer = pointer.right;
        while (pointer.top != null) {
            pointer = pointer.top;
        }
    }
    
    private void reconsiderPriorColumn() {
        j--;
        safe = false;
        pointer = rec[j];
    }

    private void advancePointer() {
        pointer = pointer.bottom;
    }

    private boolean lastSquare() {
        return pointer.bottom == null;
    }

    private boolean lastColDone() {
        return pointer.right == null;
    }

    private boolean regressOutOfFirstCol() {
        return pointer.left == null;
    }

    private void testSquare() {
        safe = pointer.occupiedNum == 0;
    }

    private void setQueen() {
        changeQueen(1);
    }

    private void removeQueen() {
        changeQueen(-1);
    }

    private void changeQueen(int val) {
        Node node = pointer;
        node.occupiedNum += val;
        while (node.left != null) {
            node = node.left;
            node.occupiedNum += val;
        }
        node = pointer;
        while (node.right != null) {
            node = node.right;
            node.occupiedNum += val;
        }
        node = pointer;
        while (node.top != null) {
            node = node.top;
            node.occupiedNum += val;
        }
        node = pointer;
        while (node.bottom != null) {
            node = node.bottom;
            node.occupiedNum += val;
        }
        node = pointer;
        while (node.top != null && node.top.left != null) {
            node = node.top.left;
            node.occupiedNum += val;
        }
        node = pointer;
        while (node.top != null && node.top.right != null) {
            node = node.top.right;
            node.occupiedNum += val;
        }
        node = pointer;
        while (node.bottom != null && node.bottom.left != null) {
            node = node.bottom.left;
            node.occupiedNum += val;
        }
        node = pointer;
        while (node.bottom != null && node.bottom.right != null) {
            node = node.bottom.right;
            node.occupiedNum += val;
        }
    }

    private void printSolution() {
        Node toBottom = board[0][1];
        while (toBottom.bottom != null) {
            toBottom = toBottom.bottom;
            int cnt = 1;
            Node toRight = toBottom;
            while (toRight.right != null && toRight.right.right != null) {
                if (rec[cnt++] == toRight) System.out.printf("● ");
                else System.out.printf("◎ ");
                toRight = toRight.right;
            }
            if (rec[cnt] == toRight) System.out.printf("●\n");
            else System.out.printf("◎\n");
        }
        System.out.printf("\n");
    }

    private void regress() {
        reconsiderPriorColumn();
        if (!regressOutOfFirstCol()) {
            removeQueen();
            if (lastSquare()) {
                reconsiderPriorColumn();
                if (!regressOutOfFirstCol()) {
                    removeQueen();
                }
            }
        }
    }

    public void solve() {
        considerFirstColumn();
        int cnt = 0;
        while (true)
        {
            while (!lastColDone() && !regressOutOfFirstCol()) {
                while (!safe && !lastSquare()) {
                    advancePointer();
                    testSquare();
                }
                if (safe) {
                    setQueen();
                    considerNextColumn();
                } else {
                    regress();
                }
            }
            if (j > N) {
                System.out.printf("Solution %d:\n", ++cnt);
                printSolution();
                regress();
            }
            else break;
        }
    }
    
    public LinkedListNQueensSolution() {
        safe = false;
        rec = new Node[N + 2];
        board = new Node[N + 1][N + 2];
        for (int m = 0; m < N + 1; m++) {
            for (int n = 0; n < N + 2; n++) {
                Node node = new Node();
                node.occupiedNum = 0;
                board[m][n] = node;
            }
        }
        for (int m = 0; m < N + 1; m++) {
            for (int n = 0; n < N + 2; n++) {
                if (m == 0) board[m][n].top = null;
                else board[m][n].top = board[m - 1][n];
                if (m == N) board[m][n].bottom = null;
                else board[m][n].bottom = board[m + 1][n];
                if (n == 0) board[m][n].left = null;
                else board[m][n].left = board[m][n - 1];
                if (n == N + 1) board[m][n].right = null;
                else board[m][n].right = board[m][n + 1];
            }
        }
        rec[0] = board[0][0];
    }

    public static void main(String[] args) {
        LinkedListNQueensSolution nQueensSolution = new LinkedListNQueensSolution();
        nQueensSolution.solve();
    }

}
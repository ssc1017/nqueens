import java.util.ArrayList;
import java.util.List;

public class LinkedListNQueensFundamentalSolution {

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

    private List<Boolean[][]> solutions;

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

    private Boolean[][] levelFlip(Boolean[][] solution) {
        Boolean[][] newSolution = new Boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newSolution[i][N - 1 - j] = solution[i][j];
            }
        }
        return newSolution;
    }

    private Boolean[][] verticalFlip(Boolean[][] solution) {
        Boolean[][] newSolution = new Boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newSolution[N - 1 - i][j] = solution[i][j];
            }
        }
        return newSolution;
    }

    // turn left by 90 degrees
    private Boolean[][] turnLeft1(Boolean[][] solution) {
        Boolean[][] newSolution = new Boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newSolution[N - 1 - j][i] = solution[i][j];
            }
        }
        return newSolution;
    }

    // turn left by 180 degrees
    private Boolean[][] turnLeft2(Boolean[][] solution) {
        Boolean[][] newSolution = new Boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newSolution[N - 1 - j][N - 1 - i] = solution[i][j];
            }
        }
        return newSolution;
    }
        
    // turn left by 270 degrees
    private Boolean[][] turnLeft3(Boolean[][] solution) {
        Boolean[][] newSolution = new Boolean[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                newSolution[i][N - 1 - j] = solution[i][j];
            }
        }
        return newSolution;
    }

    private boolean isEqual(Boolean[][] fundSolution, Boolean[][] solution) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (fundSolution[i][j] != solution[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    private boolean isDuplicate() {
        Boolean[][] solution = new Boolean[N][N];
        int i = 0;
        int j = 0;
        Node toBottom = board[0][1];
        while (toBottom.bottom != null) {
            toBottom = toBottom.bottom;
            int cnt = 1;
            Node toRight = toBottom;
            while (toRight.right != null && toRight.right.right != null) {
                if (rec[cnt++] == toRight) solution[i][j++] = true;
                else solution[i][j++] = false;
                toRight = toRight.right;
            }
            if (rec[cnt] == toRight) {
                solution[i++][j] = true;
                j = 0;
            }
            else {
                solution[i++][j] = false;
                j = 0;
            }
        }
        // check duplication
        for (Boolean[][] fundSolution : solutions) {
            if (isEqual(turnLeft1(fundSolution), solution) 
                || isEqual(turnLeft2(fundSolution), solution) 
                || isEqual(turnLeft3(fundSolution), solution)
                || isEqual(turnLeft1(fundSolution), levelFlip(solution)) 
                || isEqual(turnLeft2(fundSolution), levelFlip(solution)) 
                || isEqual(turnLeft3(fundSolution), levelFlip(solution))
                || isEqual(fundSolution, levelFlip(solution))
                || isEqual(turnLeft1(fundSolution), verticalFlip(solution))
                || isEqual(turnLeft2(fundSolution), verticalFlip(solution))
                || isEqual(turnLeft3(fundSolution), verticalFlip(solution))
                || isEqual(fundSolution, verticalFlip(solution))) return true;
        }
        solutions.add(solution);
        return false;
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
                if (!isDuplicate()) {
                    System.out.printf("Solution %d:\n", ++cnt);
                    printSolution();
                }
                regress();
            }
            else break;
        }
    }
    
    public LinkedListNQueensFundamentalSolution() {
        safe = false;
        rec = new Node[N + 2];
        board = new Node[N + 1][N + 2];
        solutions = new ArrayList<>();
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
        LinkedListNQueensFundamentalSolution nQueensSolution = new LinkedListNQueensFundamentalSolution();
        nQueensSolution.solve();
    }

}
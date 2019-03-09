import java.util.ArrayList;
import java.util.List;

public class NQueensFundamentalSolution {

    private int j; // current col, j >= 0 && j <= N + 1
    
    private int i; // current row

    private int[] x; // record i in column j so we can continue our search on row after regression, x[1 : 8] >= 0 && x[1 : 8] <= N

    private boolean[] a; // record rows

    private boolean[] b; // record diagnals

    private boolean[] c; // record diagnals

    private boolean safe; // check if the position is safe or not

    private List<Boolean[][]> solutions;

    public static final int N = 8;

    private void considerFirstColumn() {
        j = 1;
        i = 0;
    }

    private void considerNextColumn() {
        x[j] = i;
        j++;
        i = 0;
        safe = false;
    }
    
    private void reconsiderPriorColumn() {
        j--;
        i = x[j];
        safe = false;
    }

    private void advancePointer() {
        i++;
    }

    private boolean lastSquare() {
        return i == N;
    }

    private boolean lastColDone() {
        return j > N;
    }

    private boolean regressOutOfFirstCol() {
        return j < 1;
    }

    private void testSquare() {
        safe = a[i] && b[i + j] && c[i - j + N];
    }

    private void setQueen() {
        a[i] = b[i + j] = c[i - j + N] = false;
    }

    private void removeQueen() {
        a[i] = b[i + j] = c[i - j + N] = true;
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
        for (int m = 1; m <= N; m++) {
            for (int n = 1; n <= N; n++) {
                if (x[m] == n) solution[m - 1][n - 1] = true;
                else solution[m - 1][n - 1] = false;
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
        for (int m = 1; m <= N; m++) {
            for (int n = 1; n < N; n++) {
                if (x[m] == n) System.out.printf("● ");
                else System.out.printf("◎ ");
            }
            if (x[m] == N) System.out.printf("●\n");
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
    
    public NQueensFundamentalSolution() {
        x = new int[N + 1];
        a = new boolean[N + 1];
        b = new boolean[2 * N + 1];
        c = new boolean[2 * N + 1];
        solutions = new ArrayList<>();
        for (int m = 1; m <= N; m++) {
            a[m] = true;
        }
        for (int m = 1; m <= 2 * N; m++) {
            b[m] = true;
            c[m] = true;
        }
        safe = false;
    }

    public static void main(String[] args) {
        NQueensFundamentalSolution nQueensSolution = new NQueensFundamentalSolution();
        nQueensSolution.solve();
    }

}
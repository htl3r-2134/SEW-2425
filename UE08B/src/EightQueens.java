import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EightQueens {
    private static int solutionsCount = 0;

    public static void main(String[] args) {
        int maxDimension = 12; // Maximalgröße des Schachbretts
        System.out.println("Schachbrettgröße\tLösungen\tLaufzeit (ms)");

        for (int n = 1; n <= maxDimension; n++) {
            long startTime = System.currentTimeMillis();
            solutionsCount = 0;
            List<int[]> solutions = solveNQueens(n);
            long endTime = System.currentTimeMillis();

            System.out.printf("%dx%d\t\t\t%d\t\t%d\n", n, n, solutionsCount, (endTime - startTime));

            if ((endTime - startTime) > 20 * 60 * 1000) {
                System.out.println("Maximale Zeit überschritten bei " + n + "x" + n + ".");
                break;
            }
        }
    }

    // Methode zur Lösung des n-Damen-Problems
    public static List<int[]> solveNQueens(int n) {
        List<int[]> solutions = new ArrayList<>();
        int[] board = new int[n];
        placeQueen(solutions, board, 0, n);
        return solutions;
    }

    // Backtracking-Algorithmus
    private static void placeQueen(List<int[]> solutions, int[] board, int row, int n) {
        if (row == n) {
            solutions.add(board.clone());
            solutionsCount++;
            return;
        }

        for (int col = 0; col < n; col++) {
            if (isSafe(board, row, col)) {
                board[row] = col;
                placeQueen(solutions, board, row + 1, n);
            }
        }
    }

    // Überprüfen, ob das Setzen einer Dame sicher ist
    private static boolean isSafe(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            int placedCol = board[i];
            if (placedCol == col ||
                    placedCol - i == col - row ||
                    placedCol + i == col + row) {
                return false;
            }
        }
        return true;
    }
}

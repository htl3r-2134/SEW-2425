import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;


public class Sudoku {
    public static void solve_sudoku() throws IOException {
        Path folderpath = Paths.get("C:\\Users\\julia\\IdeaProjects\\_3BI\\UE08C\\src\\solved_sudokus");
        String[][] board = new String[9][9];
        String solved_board = "";
        try {
            for (Path path : Files.newDirectoryStream(Paths.get("C:\\Users\\julia\\IdeaProjects\\_3BI\\UE08C\\src\\resources"), "*.sudoku")) {
                BufferedWriter solved_sudoku = Files.newBufferedWriter(folderpath.resolve("solved.sudoku"), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
                BufferedReader sudoku = Files.newBufferedReader(path, StandardCharsets.UTF_8);
                String line;

                while ((line = sudoku.readLine()) != null) {
                    String sudoku_name = line.split(":")[0].trim();
                    String sudoku_data = line.split(":")[1].trim();
                    printSudoku(sudoku_data);

                    for (int i = 0; i < sudoku_data.length(); i++) {
                        board[i / 9][i % 9] = sudoku_data.charAt(i) == '.' ? "0" : String.valueOf(sudoku_data.charAt(i));
                    }

                    if (solve(board)) {
                        solved_board += sudoku_name + ": ";
                        for (String[] row : board) {
                            for (String col : row) {
                                solved_board += col;
                            }
                        }
                        printSudoku(solved_board.split(":")[1].trim());
                        solved_sudoku.write(solved_board);
                        solved_sudoku.newLine();
                        solved_board = "";
                    } else {
                        throw new IllegalArgumentException("Dieses Sudoku ist nicht lösbar");
                    }
                }
                solved_sudoku.close();
                sudoku.close();
            }
        } catch (NoSuchFileException e) {
            throw new IOException("Es wurde keine Datei mit der Dateiendung .sudoku gefunden");
        }
    }

    public static void printSudoku(String sudoku) {
        String printed_sudoku = "";
        int counter = 0;
        for (int i = 0; i < sudoku.length(); i++) {
            printed_sudoku += i == 0 ? "+---+---+---+\n|" : "";

            printed_sudoku += sudoku.charAt(i) == '.' ? " " : sudoku.charAt(i);
            counter++;

            if (counter == 3 || counter == 6) {
                printed_sudoku += "|";
            } else if (counter == 9) {
                printed_sudoku += i == 26 || i == 53 ? "|\n+---+---+---+\n|" : (i == 80 ? "|\n+---+---+---+\n" : "|\n|");
                counter = 0;
            }
        }
        System.out.println(printed_sudoku);
    }

    public static boolean solve(String[][] sudoku_2D) {
        for (int row = 0; row < sudoku_2D.length; row++) {
            for (int col = 0; col < sudoku_2D[row].length; col++) {
                if (sudoku_2D[row][col].equals("0")) {
                    for (int num = 1; num <= 9; num++) {
                        if (isValid(sudoku_2D, row, col, num)) {
                            sudoku_2D[row][col] = String.valueOf(num);
                            if (solve(sudoku_2D)) {
                                return true;
                            }
                            sudoku_2D[row][col] = String.valueOf(0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean isValid(String[][] sudoku_2D, int row, int col, int num) {
        for (int i = 0; i < sudoku_2D.length; i++) {
            if (sudoku_2D[row][i].equals(String.valueOf(num)) || sudoku_2D[i][col].equals(String.valueOf(num))) {
                return false;
            }
            for (int j = 0; j < 81; j++) {
                if (3 * (row / 3) + (col / 3) == 3 * ((j / 9) / 3) + ((j % 9) / 3)) {
                    if (sudoku_2D[j / 9][j % 9].equals(String.valueOf(num))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public static void main(String[] args) throws IOException {
        solve_sudoku();
    }
}
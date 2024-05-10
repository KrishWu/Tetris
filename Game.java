public class Game {
    private int[][] board;
    private GamePiece currPiece;
    private GamePiece nextPiece;
    private int score;
    private int x;
    private int y;

    public Game() {
        board = new int[20][10];
        currPiece = new GamePiece();
        nextPiece = new GamePiece();
        score = 0;
        x = 4;
        y = 0;
    }

    public void restart() {
        board = new int[20][10];
        currPiece = new GamePiece();
        nextPiece = new GamePiece();
        score = 0;
        x = 4;
        y = 0;
    }

    public void pause() {

    }

    public void nextFrame() {
        // System.out.println(currPiece.isBottomEmpty());
        if ((y + currPiece.getHeight() >= board.length && !currPiece.isBottomEmpty()) || (currPiece.isBottomEmpty() && y + currPiece.getHeight() > board.length)) {
            for (int r = 0; r < currPiece.getHeight(); r++) {
                for (int c = 0; c < currPiece.getWidth(); c++) {
                    if (currPiece.getSection(r, c) != 0) {
                        board[y + r][x + c] = currPiece.getSection(r, c);
                    }
                }
            }
            currPiece = new GamePiece(nextPiece.getType());
            nextPiece = new GamePiece();
            x = 4;
            y = 0;  
        } else {
            y++;
        }
    }

    public void rotateRight() {
        currPiece.rotateRight(board, x, y);
    }

    public void rotateLeft() {
        currPiece.rotateLeft(board, x, y);
    }

    public void moveRight() {
        for (int r = 0; r < currPiece.getHeight(); r++) {
            for (int c = 0; c < currPiece.getWidth(); c++) {
                if (currPiece.getSection(r, c) != 0) {
                    if (x + c + 1 >= board[0].length) {
                        return;
                    }
                    if (board[y + r][x + c + 1] != 0) {
                        return;
                    }
                }
            }
        }
        x++;
    }

    public void moveLeft() {
        for (int r = 0; r < currPiece.getHeight(); r++) {
            for (int c = 0; c < currPiece.getWidth(); c++) {
                if (currPiece.getSection(r, c) != 0) {
                    if (x + c - 1 < 0) {
                        return;
                    }
                    if (board[y + r][x + c - 1] != 0) {
                        return;
                    }
                }
            }
        }
        x--;
    }

    public void drop() {

    }

    public int[][] getBoard() {
        int[][] toReturn = new int[board.length][board[0].length];
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (r >= y && r < y + currPiece.getHeight() && c >= x && c < x + currPiece.getWidth() && currPiece.getSection(r - y, c - x) != 0) {
                    toReturn[r][c] = currPiece.getSection(r - y, c - x);
                } else {
                    toReturn[r][c] = board[r][c];
                }
            }
        }
        return toReturn;
    }
}

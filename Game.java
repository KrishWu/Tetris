public class Game {
    public static final int startX = 3;
    public static final int startY = 0;

    private int[][] board;
    private GamePiece currPiece;
    private GamePiece nextPiece;
    private int score;
    private int totalLinesRemoved;
    private int x;
    private int y;
    private boolean isGameOver;
    private boolean isPaused;

    public Game() {
        board = new int[20][10];
        currPiece = new GamePiece();
        nextPiece = new GamePiece();
        score = 0;
        x = startX;
        y = startY;
        isGameOver = false;
        isPaused = false;
    }

    public void pause() {
        isPaused = !isPaused;
    }

    public void nextFrame() {
        if (isPaused) {
            return;
        }
        // System.out.println(currPiece.isBottomEmpty());
        if (isAtBottom()) {
            nextPiece();
        } else if (!canGoDown()) {
            nextPiece();
        } else {
            y++;
        }
    }

    public void rotateRight() {
        if (isPaused) {
            return;
        }
        currPiece.rotateRight(board, x, y);
    }

    public void rotateLeft() {
        if (isPaused) {
            return;
        }
        currPiece.rotateLeft(board, x, y);
    }

    public void moveRight() {
        if (isPaused) {
            return;
        }
        for (int r = 0; r < currPiece.getHeight(); r++) {
            for (int c = 0; c < currPiece.getWidth(); c++) {
                if (currPiece.getSection(r, c) != 0) {
                    if (x + c + 1 >= board[0].length) {
                        return;
                    }
                    if (y + r >= 0 && board[y + r][x + c + 1] != 0) {
                        return;
                    }
                }
            }
        }
        x++;
    }

    public void moveLeft() {
        if (isPaused) {
            return;
        }
        for (int r = 0; r < currPiece.getHeight(); r++) {
            for (int c = 0; c < currPiece.getWidth(); c++) {
                if (currPiece.getSection(r, c) != 0) {
                    if (x + c - 1 < 0) {
                        return;
                    }
                    if (y + r >= 0 && board[y + r][x + c - 1] != 0) {
                        return;
                    }
                }
            }
        }
        x--;
    }

    public void drop() {
        if (isPaused) {
            return;
        }
        while (!isAtBottom() && canGoDown()) {
            y++;
        }
        nextPiece();
    }

    public int dropRowNum() {
        int tempY = y;
        while (!isAtBottom(tempY) && canGoDown(tempY)) {
            tempY++;
        }
        return tempY;
    }

    public void addPieceToBoard() {
        for (int r = 0; r < currPiece.getHeight(); r++) {
            for (int c = 0; c < currPiece.getWidth(); c++) {
                if (currPiece.getSection(r, c) != 0) {
                    if (r + y < 0) {
                        isGameOver = true;
                    }
                    if (r + y >= 0 && r + y < board.length && c + x >= 0
                            && c + x < board[0].length) {
                        board[y + r][x + c] = currPiece.getSection(r, c);
                    }
                }
            }
        }
    }

    public void nextPiece() {
        addPieceToBoard();
        removeFullLines();
        currPiece = new GamePiece(nextPiece.getType());
        nextPiece = new GamePiece();
        x = startX;
        y = -currPiece.getHeight();
    }

    public void removeFullLines() {
        int numLinesRemoved = 0;
        for (int r = 0; r < board.length; r++) {
            if (isLineFull(r)) {
                removeLine(r);
                numLinesRemoved++;
            }
        }
        addPoints(numLinesRemoved);
    }

    public void removeLine(int r) {
        for (int currR = r; currR >= 1; currR--) {
            for (int c = 0; c < board[currR].length; c++) {
                board[currR][c] = board[currR - 1][c];
            }
        }
        board[0] = new int[board[0].length];
    }

    public void addPoints(int numLinesRemoved) {
        if (numLinesRemoved == 1) {
            score += 40;
        } else if (numLinesRemoved == 2) {
            score += 100;
        } else if (numLinesRemoved == 3) {
            score += 300;
        } else if (numLinesRemoved >= 4) {
            score += 1200;
        }
        totalLinesRemoved += numLinesRemoved;
     }

    public boolean isAtBottom() {
        return (y + currPiece.getHeight() >= board.length && !currPiece.isBottomEmpty()
                && !currPiece.isSecondToBottomEmpty())
                || (y + currPiece.getHeight() > board.length && currPiece.isBottomEmpty()
                        && !currPiece.isSecondToBottomEmpty())
                || (y + currPiece.getHeight() - 1 > board.length && currPiece.isBottomEmpty()
                        && currPiece.isSecondToBottomEmpty());
    }

    public boolean isAtBottom(int y) {
        return (y + currPiece.getHeight() >= board.length && !currPiece.isBottomEmpty()
                && !currPiece.isSecondToBottomEmpty())
                || (y + currPiece.getHeight() > board.length && currPiece.isBottomEmpty()
                        && !currPiece.isSecondToBottomEmpty())
                || (y + currPiece.getHeight() - 1 > board.length && currPiece.isBottomEmpty()
                        && currPiece.isSecondToBottomEmpty());
    }

    public boolean canGoDown() {
        for (int c = 0; c < currPiece.getWidth(); c++) {
            // System.out.println(currPiece.getLowestRow(c));
            if (currPiece.getLowestRow(c) != -1) {
                if (currPiece.getLowestRow(c) + y + 1 >= 0) {
                    if (board[currPiece.getLowestRow(c) + y + 1][x + c] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean canGoDown(int y) {
        for (int c = 0; c < currPiece.getWidth(); c++) {
            // System.out.println(currPiece.getLowestRow(c));
            if (currPiece.getLowestRow(c) != -1) {
                if (currPiece.getLowestRow(c) + y + 1 >= 0) {
                    if (board[currPiece.getLowestRow(c) + y + 1][x + c] != 0) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean isLineFull(int r) {
        for (int c : board[r]) {
            if (c == 0) {
                return false;
            }
        }
        return true;
    }

    public int[][] getBoard() {
        int[][] toReturn = new int[board.length][board[0].length];
        int ghostY = dropRowNum();
        for (int r = 0; r < board.length; r++) {
            for (int c = 0; c < board[r].length; c++) {
                if (r >= y && r < y + currPiece.getHeight() && c >= x && c < x + currPiece.getWidth()
                        && currPiece.getSection(r - y, c - x) != 0) {
                    toReturn[r][c] = currPiece.getSection(r - y, c - x);
                } else if (r >= ghostY && r < ghostY + currPiece.getHeight() && c >= x && c < x + currPiece.getWidth()
                        && currPiece.getSection(r - ghostY, c - x) != 0) {
                    toReturn[r][c] = currPiece.getSection(r - ghostY, c - x)+7;
                } else {
                    toReturn[r][c] = board[r][c];
                }
            }
        }
        return toReturn;
    }

    public int[][] getNextPiece() {
        return nextPiece.getBlock();
    }

    public int getScore() {
        return score;
    }

    public double getSpeedMultiplier() {
        return (1/(1+0.05*totalLinesRemoved));
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public boolean isPaused() {
        return isPaused;
    }
}

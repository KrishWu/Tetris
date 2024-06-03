package krishwu;
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

    private boolean isLineRemovedSound;

    //Constructor for the game object.
    public Game() {
        board = new int[20][10];
        currPiece = new GamePiece();
        nextPiece = new GamePiece();
        score = 0;
        x = startX;
        y = startY;
        isGameOver = false;
        isPaused = false;
        isLineRemovedSound = false;
    }

    //A method to pause the game and make things not happen.
    public void pause() {
        if (isGameOver) {
            return;
        }
        isPaused = !isPaused;
    }

    //Call the next frame to move the piece down one.
    public void nextFrame() {
        if (isGameOver || isPaused) {
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

    //Rotate the piece right.
    public void rotateRight() {
        if (isGameOver || isPaused) {
            return;
        }
        currPiece.rotateRight(board, x, y);
    }

    //Rotate the piece left.
    public void rotateLeft() {
        if (isGameOver || isPaused) {
            return;
        }
        currPiece.rotateLeft(board, x, y);
    }

    //Move the piece right one.
    public boolean moveRight() {
        if (isGameOver || isPaused) {
            return false;
        }
        for (int r = 0; r < currPiece.getHeight(); r++) {
            for (int c = 0; c < currPiece.getWidth(); c++) {
                if (currPiece.getSection(r, c) != 0) {
                    if (x + c + 1 >= board[0].length) {
                        return false;
                    }
                    if (y + r >= 0 && board[y + r][x + c + 1] != 0) {
                        return false;
                    }
                }
            }
        }
        x++;
        return true;
    }

    //Move the piece one left.
    public boolean moveLeft() {
        if (isGameOver || isPaused) {
            return false;
        }
        for (int r = 0; r < currPiece.getHeight(); r++) {
            for (int c = 0; c < currPiece.getWidth(); c++) {
                if (currPiece.getSection(r, c) != 0) {
                    if (x + c - 1 < 0) {
                        return false;
                    }
                    if (y + r >= 0 && board[y + r][x + c - 1] != 0) {
                        return false;
                    }
                }
            }
        }
        x--;
        return true;
    }

    //Have the piece drop to the bottom with a hard drop.
    public boolean drop() {
        if (isGameOver || isPaused) {
            return false;
        }
        while (!isAtBottom() && canGoDown()) {
            y++;
        }
        nextPiece();
        return true;
    }

    //Give the row number it would go to if it was dropped for the ghost.
    public int dropRowNum() {
        int tempY = y;
        while (!isAtBottom(tempY) && canGoDown(tempY)) {
            tempY++;
        }
        return tempY;
    }

    //Add the current piece to the board after it has hit the bottom.
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

    //After piece hits the bottom make it draw to the board and set the next piece to the current piece with a new height and make a new piece for the next piece.
    public void nextPiece() {
        addPieceToBoard();
        removeFullLines();
        currPiece = new GamePiece(nextPiece.getType());
        nextPiece = new GamePiece();
        x = startX;
        y = -currPiece.getHeight();
    }

    //If a line is completely filled remove it and call the addPoints() to give more points for the piece that was removed.
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

    //Remove a line from the board with the given row number and drop the rows above it one down the make sure that there is no gaps.
    public void removeLine(int r) {
        for (int currR = r; currR >= 1; currR--) {
            for (int c = 0; c < board[currR].length; c++) {
                board[currR][c] = board[currR - 1][c];
            }
        }
        board[0] = new int[board[0].length];
    }

    //Add points to the score for the given number of lines removed at once.
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
        if (numLinesRemoved > 0) {
            isLineRemovedSound = true;
        }
        totalLinesRemoved += numLinesRemoved;
     }

    //Check if the piece is at the bottom.
    public boolean isAtBottom() {
        return (y + currPiece.getHeight() >= board.length && !currPiece.isBottomEmpty()
                && !currPiece.isSecondToBottomEmpty())
                || (y + currPiece.getHeight() > board.length && currPiece.isBottomEmpty()
                        && !currPiece.isSecondToBottomEmpty())
                || (y + currPiece.getHeight() - 1 > board.length && currPiece.isBottomEmpty()
                        && currPiece.isSecondToBottomEmpty());
    }

    //Check if given a current y if it is at the bottom.
    public boolean isAtBottom(int y) {
        return (y + currPiece.getHeight() >= board.length && !currPiece.isBottomEmpty()
                && !currPiece.isSecondToBottomEmpty())
                || (y + currPiece.getHeight() > board.length && currPiece.isBottomEmpty()
                        && !currPiece.isSecondToBottomEmpty())
                || (y + currPiece.getHeight() - 1 > board.length && currPiece.isBottomEmpty()
                        && currPiece.isSecondToBottomEmpty());
    }

    //Check if the piece has the ability to go down another one or if it can not and return a boolean.
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

    //Check if a piece can go down another row given the y of the piece that it be at for the piece's ghost.
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

    //Check if the given line is full or it is not.
    public boolean isLineFull(int r) {
        for (int c : board[r]) {
            if (c == 0) {
                return false;
            }
        }
        return true;
    }

    //The get board method to return a 2D array that is the board, plus the drawn on piece, and the drawn on ghost.
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

    //Get the next piece block to display in the next piece area on the top right of the screen.
    public int[][] getNextPiece() {
        return nextPiece.getBlock();
    }

    //Get the score.
    public int getScore() {
        return score;
    }

    //Get the speed multiplier so it goes faster as more lines are removed.
    public double getSpeedMultiplier() {
        return (1/(1+0.05*totalLinesRemoved));
    }
    
    //Get if isGameOver.
    public boolean isGameOver() {
        return isGameOver;
    }

    //Get if isPaused.
    public boolean isPaused() {
        return isPaused;
    }

    //Get if it should play the line removed sound.
    public boolean getIsLineRemovedSound() {
        if (isLineRemovedSound) {
            isLineRemovedSound = false;
            return true;
        } else {
            return false;
        }
    }
}

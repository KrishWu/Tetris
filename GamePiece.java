public class GamePiece {
    public final int[][] iBlock = {
            { 0, 0, 5, 0 },
            { 0, 0, 5, 0 },
            { 0, 0, 5, 0 },
            { 0, 0, 5, 0 }
    };
    public final int[][] jBlock = {
            { 0, 6, 0 },
            { 0, 6, 0 },
            { 6, 6, 0 }
    };
    public final int[][] lBlock = {
            { 0, 2, 0 },
            { 0, 2, 0 },
            { 0, 2, 2 }
    };
    public final int[][] oBlock = {
            { 3, 3 },
            { 3, 3 }
    };
    public final int[][] sBlock = {
            { 0, 0, 0 },
            { 0, 4, 4 },
            { 4, 4, 0 }
    };
    public final int[][] tBlock = {
            { 0, 0, 0 },
            { 7, 7, 7 },
            { 0, 7, 0 }
    };
    public final int[][] zBlock = {
            { 0, 0, 0 },
            { 1, 1, 0 },
            { 0, 1, 1 }
    };
    public static final String blocks = "ijlostz";

    private int[][] block;
    private String type;

    public GamePiece() {
        // System.out.println(blocks.substring((int)(Math.random()*7)).substring(0,1));
        this(blocks.substring((int) (Math.random() * 7)).substring(0, 1));
    }

    public GamePiece(String piece) {
        if (piece.equals("i")) {
            block = iBlock.clone();
        } else if (piece.equals("j")) {
            block = jBlock.clone();
        } else if (piece.equals("l")) {
            block = lBlock.clone();
        } else if (piece.equals("o")) {
            block = oBlock.clone();
        } else if (piece.equals("s")) {
            block = sBlock.clone();
        } else if (piece.equals("t")) {
            block = tBlock.clone();
        } else if (piece.equals("z")) {
            block = zBlock.clone();
        }

        type = piece;
    }

    // Rotate matrix 90 degrees right
    public void rotateRight(int[][] board, int x, int y) {
        // Create a new temp 2D array.
        int[][] temp = new int[block.length][block[0].length];
        // Check if rotation is possible and copy to new location in temp.
        for (int r = 0; r < temp.length; r++) {
            for (int c = 0; c < temp[r].length; c++) {
                if (block[block[0].length - c - 1][r] != 0) {
                    if (r + y >= board.length || c + x >= board[0].length) {
                        return;
                    }
                    if (c + x < 0) {
                        return;
                    }
                    if (r + y >= 0 && board[r + y][c + x] != 0) {
                        return;
                    }
                }
                temp[r][c] = block[block[0].length - c - 1][r];
            }
        }
        // Copy temp back to the block.
        for (int r = 0; r < temp.length; r++) {
            for (int c = 0; c < temp[r].length; c++) {
                block[r][c] = temp[r][c];
            }
        }
    }

    public void rotateLeft(int[][] board, int x, int y) {
        // Create a new temp 2D array.
        int[][] temp = new int[block.length][block[0].length];
        // Check if rotation is possible and copy to new location in temp.
        for (int r = 0; r < temp.length; r++) {
            for (int c = 0; c < temp[r].length; c++) {
                if (block[block[0].length - c - 1][r] != 0) {
                    if (r + y >= board.length || c + x >= board[0].length) {
                        return;
                    }
                    if (c + x < 0) {
                        return;
                    }
                    if (r + y >= 0 && board[r + y][c + x] != 0) {
                        return;
                    }
                }
                temp[r][c] = block[c][block.length - r - 1];
            }
        }
        // Copy temp back to the block.
        for (int r = 0; r < temp.length; r++) {
            for (int c = 0; c < temp[r].length; c++) {
                block[r][c] = temp[r][c];
            }
        }
    }

    public boolean isBottomEmpty() {
        for (int section : block[block.length - 1]) {
            if (section != 0) {
                return false;
            }
        }
        return true;
    }

    public boolean isSecondToBottomEmpty() {
        for (int section : block[block.length - 2]) {
            if (section != 0) {
                return false;
            }
        }
        return true;
    }

    public int getLowestRow(int c) {
        for (int r = block.length - 1; r >= 0; r--) {
            if (block[r][c] != 0) {
                return r;
            }
        }
        return -1;
    }

    public int getHeight() {
        return block.length;
    }

    public int getWidth() {
        return block[0].length;
    }

    public int getSection(int r, int c) {
        return block[r][c];
    }

    public String getType() {
        return type;
    }

    public int[][] getBlock() {
        return block;
    }
}

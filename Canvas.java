import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Canvas extends JComponent {
    public static final int TICK_SPEED = 500;
    public static final int SOFT_DROP_TICK_SPEED = 50;
    public static final int REFRESH_SPEED = 1;

    private final int WIDTH;
    private final int HEIGHT;
    private Game game;
    private boolean isGameOver;
    private int newTickTimer;
    private int currTickSpeed;

    public Canvas(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;

        game = new Game();
        isGameOver = false;
        newTickTimer = 0;
        currTickSpeed = TICK_SPEED;

        repaint();

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                if (!isGameOver) {
                    newTickTimer++;
                    if (newTickTimer >= currTickSpeed * game.getSpeedMultiplier()) {
                        game.nextFrame();
                        isGameOver = game.isGameOver();
                        newTickTimer = 0;
                        // System.out.println("Tick");
                    }
                }
                repaint();
            }
        }

        Timer t = new Timer(REFRESH_SPEED, new TimerListener());
        t.start();

        class KeyboardListener implements KeyListener {
            public void keyPressed(KeyEvent e) {
                // Right arrow key
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    game.moveRight();
                }
                // Left arrow key
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    game.moveLeft();
                }
                // Down arrow key
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    currTickSpeed = SOFT_DROP_TICK_SPEED;
                }
                // Up arrow key
                if (e.getKeyCode() == KeyEvent.VK_UP) {
                    game.rotateRight();
                }
            }

            public void keyReleased(KeyEvent e) {
                // Down arrow key
                if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                    currTickSpeed = TICK_SPEED;
                }

            }

            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == ' ') {
                    game.drop();
                }
                if (e.getKeyChar() == 'r') {
                    game = new Game();
                    isGameOver = false;
                }
                if (e.getKeyChar() == 'z') {
                    game.rotateLeft();
                }
                if (e.getKeyChar() == 'x') {
                    game.rotateRight();
                }
                if (e.getKeyChar() == 'p') {
                    game.pause();
                }
            }
        }

        addKeyListener(new KeyboardListener());
        setFocusable(true);
        requestFocus();
    }

    public void drawBoard(int[][] board, Graphics g) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] == 0) {
                    g.setColor(Color.BLACK);
                } else if (board[i][j] == 1) {
                    g.setColor(Color.RED);
                } else if (board[i][j] == 2) {
                    g.setColor(Color.ORANGE);
                } else if (board[i][j] == 3) {
                    g.setColor(Color.YELLOW);
                } else if (board[i][j] == 4) {
                    g.setColor(Color.GREEN);
                } else if (board[i][j] == 5) {
                    g.setColor(new Color(8, 240, 240));
                } else if (board[i][j] == 6) {
                    g.setColor(Color.BLUE);
                } else if (board[i][j] == 7) {
                    g.setColor(Color.PINK);
                } else if (board[i][j] == 8) {
                    g.setColor(new Color(255, 0, 0, 127));
                } else if (board[i][j] == 9) {
                    g.setColor(new Color(255, 165, 0, 127));
                } else if (board[i][j] == 10) {
                    g.setColor(new Color(255, 255, 0, 127));
                } else if (board[i][j] == 11) {
                    g.setColor(new Color(0, 255, 0, 127));
                } else if (board[i][j] == 12) {
                    g.setColor(new Color(8, 240, 240, 127));
                } else if (board[i][j] == 13) {
                    g.setColor(new Color(0, 0, 255, 127));
                } else if (board[i][j] == 14) {
                    g.setColor(new Color(255, 192, 203, 127));
                }
                g.fillRect(j * 40, i * 40 + 100, 40, 40);
            }
        }
    }

    public void drawNextPiece(int[][] nextPiece, Graphics g) {
        for (int i = 0; i < nextPiece.length; i++) {
            for (int j = 0; j < nextPiece[i].length; j++) {
                if (nextPiece[i][j] == 0) {
                    g.setColor(new Color(40, 40, 40));
                } else if (nextPiece[i][j] == 1) {
                    g.setColor(Color.RED);
                } else if (nextPiece[i][j] == 2) {
                    g.setColor(Color.ORANGE);
                } else if (nextPiece[i][j] == 3) {
                    g.setColor(Color.YELLOW);
                } else if (nextPiece[i][j] == 4) {
                    g.setColor(Color.GREEN);
                } else if (nextPiece[i][j] == 5) {
                    g.setColor(new Color(8, 240, 240));
                } else if (nextPiece[i][j] == 6) {
                    g.setColor(Color.BLUE);
                } else if (nextPiece[i][j] == 7) {
                    g.setColor(Color.PINK);
                }

                g.fillRect(j * 40 + 440, i * 40 + 100, 40, 40);
            }
        }
    }

    public void paintComponent(Graphics g) {
        g.setColor(new Color(40, 40, 40));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        drawBoard(game.getBoard(), g);
        drawNextPiece(game.getNextPiece(), g);

        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Krish Wu's Tetris!", 90, 70);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Points:", 415, 370);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString(game.getScore() + "", 490, 430);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("← - Move Left", 415, 500);
        g.drawString("→ - Move Right", 415, 540);
        g.drawString("↓ - Soft Drop", 415, 580);
        g.drawString("X / ↑ - Rotate Right", 415, 620);
        g.drawString("Z - Rotate Left", 415, 660);
        g.drawString("Space - Hard Drop", 415, 700);
        g.drawString("R - Restart", 415, 740);
        g.drawString("P - Pause", 415, 780);


        if (isGameOver) {
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(150, 360, 300, 150);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("Game Over", 170, 425);
            g.setFont(new Font("Arial", Font.PLAIN, 25));
            g.drawString("Press R to play again.", 180, 475);
        }

        if (game.isPaused()) {
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(150, 360, 300, 150);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("Paused", 210, 425);
            g.setFont(new Font("Arial", Font.PLAIN, 25));
            g.drawString("Press P to resume.", 190, 475);
        }
    }
}

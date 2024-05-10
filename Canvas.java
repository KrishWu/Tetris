import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Canvas extends JComponent {
    public static final int TICK_SPEED = 500;
    public static final int REFRESH_SPEED = 1;

    private final int WIDTH;
    private final int HEIGHT;
    private Game game;
    private boolean gameOver;
    private int newTickTimer;


    public Canvas(int width, int height) {
        this.WIDTH = width;
        this.HEIGHT = height;

        game = new Game();
        gameOver = false;
        newTickTimer = 0;

        repaint();

        class TimerListener implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                newTickTimer++;
                if (newTickTimer >= TICK_SPEED) {
                    game.nextFrame();
                    newTickTimer = 0;
                    // System.out.println("Tick");
                }
                repaint();
            }
        }

        Timer t = new Timer(REFRESH_SPEED, new TimerListener());
        t.start();

        class KeyboardListener implements KeyListener {
            public void keyPressed(KeyEvent e) {
            }

            public void keyReleased(KeyEvent e) {
            }

            public void keyTyped(KeyEvent e) {
                if (e.getKeyChar() == 'r') {
                    game.restart();
                    // for (int r = 0; r < 20; r++) {
                    //     for (int c = 0; c < 10; c++) {
                    //         System.out.print(game.getBoard()[r][c]);
                    //     }
                    //     System.out.println();
                    // }
                }
                if (e.getKeyChar() == 'd') {
                    game.moveRight();
                }
                if (e.getKeyChar() == 'a') {
                    game.moveLeft();
                }
                if (e.getKeyChar() == 's') {
                    game.drop();
                }
                if (e.getKeyChar() == '.') {
                    game.rotateRight();
                }
                if (e.getKeyChar() == ',') {
                    game.rotateLeft();
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
                }
                g.fillRect(j * 40, i * 40 + 50, 40, 40);
            }
        }
    }

    public void paintComponent(Graphics g) {
        drawBoard(game.getBoard(), g);
    }
}

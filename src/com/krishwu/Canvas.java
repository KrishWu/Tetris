package src.com.krishwu;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Canvas extends JComponent {
    //Create the fields that are public and static.
    public static final int TICK_SPEED = 500;
    public static final int SOFT_DROP_TICK_SPEED = 50;
    public static final int REFRESH_SPEED = 1;

    //Create the private fields for the drawing of the game.
    private final int WIDTH;
    private final int HEIGHT;
    private Game game;
    private boolean isGameOver;
    private int newTickTimer;
    private int currTickSpeed;

    private Sound tetrisSound;
    private Sound clearLineSound;
    private Sound dropSound;
    // private Sound rightSound;
    // private Sound leftSound;

    //Constructor for the canvas given the width and height.
    public Canvas(int width, int height) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        this.WIDTH = width;
        this.HEIGHT = height;

        //Create a new game object.
        game = new Game();
        isGameOver = false;
        newTickTimer = 0;
        currTickSpeed = TICK_SPEED;

       tetrisSound = new Sound("./src/sound/tetris.wav");
       tetrisSound.loop();
       clearLineSound = new Sound("./src/sound/clearLine.wav");
       dropSound = new Sound("./src/sound/drop.wav");
    //    rightSound = new Sound("./src/sound/right.wav");
    //    leftSound = new Sound("./src/sound/left.wav");

        // (new Thread(new MediaPlayer("tetris.wav")).start();

        //Update the screen.
        repaint();

        //Create a new timer listener for the ticks that updates the screen.
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
                if (game.getIsLineRemovedSound()) {
                    clearLineSound.play();
                }
                repaint();
            }
        }

        //Creates an instance of the timer.
        Timer t = new Timer(REFRESH_SPEED, new TimerListener());
        t.start();

        //Creates a listener for keyboard inputs.
        class KeyboardListener implements KeyListener {
            public void keyPressed(KeyEvent e) {
                // Right arrow key
                if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
                    if (game.moveRight()) {
                        // rightSound.play(); //Disabled because it was to much.
                    }
                }
                // Left arrow key
                if (e.getKeyCode() == KeyEvent.VK_LEFT) {
                    if (game.moveLeft()) {
                        // leftSound.play(); //Disabled because it was too much.
                    }
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
                    if (game.drop()) {
                        dropSound.play();
                    };
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

    //Draw the board on the screen.
    public void drawBoard(int[][] board, Graphics g) {
        //Loops through the board rows.
        for (int i = 0; i < board.length; i++) {
            //Loops through the board columns.
            for (int j = 0; j < board[i].length; j++) {
                //Draws the various colors based off their number value.
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

    //Draws the next piece.
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

    //Draws the stuff.
    public void paintComponent(Graphics g) {
        //This is the board.
        g.setColor(new Color(40, 40, 40));
        g.fillRect(0, 0, WIDTH, HEIGHT);
        drawBoard(game.getBoard(), g);
        drawNextPiece(game.getNextPiece(), g);

        //This is the title.
        g.setColor(Color.GREEN);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Krish Wu's Tetris!", 90, 70);

        //This is the points label.
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString("Points:", 415, 370);

        //This is the score.
        if (game.getScore() == 0) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString("0", 490, 430);
        } else {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            g.drawString(game.getScore() + "", 445, 430); 
        }

        //This is the instructions.
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

        //This is the game over layover.
        if (isGameOver) {
            g.setColor(new Color(0, 0, 0, 127));
            g.fillRect(150, 360, 300, 150);
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.PLAIN, 50));
            g.drawString("Game Over", 170, 425);
            g.setFont(new Font("Arial", Font.PLAIN, 25));
            g.drawString("Press R to play again.", 180, 475);
        }

        //This is the is paused lay over.
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

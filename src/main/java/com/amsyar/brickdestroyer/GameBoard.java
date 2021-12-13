/*
 *  Brick Destroy - A simple Arcade video game
 *   Copyright (C) 2021 amsyarzero
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.amsyar.brickdestroyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class GameBoard extends JComponent implements KeyListener, MouseListener, MouseMotionListener {

    private static final String CONTINUE = "Resume";
    private static final String RESTART = "Restart";
    private static final String EXIT = "Retreat";
    private static final String PAUSE = "==PAUSE==";
    private static final int TEXT_SIZE = 30;
    private static final Color MENU_COLOR = new Color(10, 225, 53);

    private static final int DEF_WIDTH = 600;
    private static final int DEF_HEIGHT = 450;

    private static final Color BG_COLOR = Color.WHITE;

    private Timer gameTimer;

    private Wall wall;

    private String message;

    private boolean showPauseMenu;

    private Font menuFont;

    private Rectangle continueButtonRect;
    private Rectangle restartButtonRect;
    private Rectangle exitButtonRect;
    private int strLen;

    private DebugConsole debugConsole;

    private final ImageIcon favicon = new ImageIcon(this.getClass().getResource("/com/amsyar/textures/App Icon.png"));

    /**
     * @param owner
     * changes the game's favicon
     * generates all panels except InfoPage
     * shows a message when gameTimer is running and at the end of each level
     */
    public GameBoard(JFrame owner) {

        super();

        strLen = 0;
        showPauseMenu = false;

        owner.setIconImage(favicon.getImage());

        menuFont = new Font("Monospaced", Font.PLAIN, TEXT_SIZE);

        this.initialize();
        message = "";
        wall = new Wall(new Rectangle(0, 0, DEF_WIDTH, DEF_HEIGHT), 30, 3, 6/2, new Point(300, 430));

        debugConsole = new DebugConsole(owner, wall, wall.ball,this);

        //initialize the first level
        wall.nextLevel();

        gameTimer = new Timer(10, e -> {

            wall.move();
            wall.findImpacts();

            message = String.format("Bricks: %d  ||  Balls %d", wall.getBrickTotal(), wall.getBallCount());

            if(wall.isBallLost()) {

                if(wall.ballEnd()) {

                    wall.wallReset();
                    message = "--GAME OVER--";

                }

                wall.ballReset();
                gameTimer.stop();

            } else if(wall.isDone()) {

                if(wall.hasLevel()) {

                    message = "Current score: " + wall.currentScore + "\n High score: " + wall.highScore;
                    gameTimer.stop();
                    wall.ballReset();
                    wall.wallReset();
                    wall.nextLevel();

                } else {

                    message = "--CONGRATULATIONS--";
                    gameTimer.stop();

                }

            }
            repaint();
        });

    }

    /**
     * makes a frame with the settings passed into it, along with key, mouse, and focus listeners
     */
    private void initialize() {

        this.setPreferredSize(new Dimension(DEF_WIDTH,DEF_HEIGHT));
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.addMouseListener(this);
        this.addMouseMotionListener(this);

    }

    /**
     * @param g
     * clears any existing elements in the window and shows the game elements on it
     */
    public void paint(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;

        clear(g2d);

        g2d.setColor(Color.RED.darker());
        g2d.drawString(message, 250, 225);

        drawBall(wall.ball, g2d);

        for(Brick b : wall.bricks)
            if(!b.isBrickBroken())
                drawBrick(b,g2d);

        drawPlayer(wall.player, g2d);

        if(showPauseMenu)
            drawMenu(g2d);

        Toolkit.getDefaultToolkit().sync();

    }

    /**
     * @param g2d
     * method to clear any existing elements out of the window
     */
    private void clear(Graphics2D g2d) {

        Color tmp = g2d.getColor();
        g2d.setColor(BG_COLOR);
        g2d.fillRect(0,0,getWidth(),getHeight());
        g2d.setColor(tmp);

    }

    /**
     * @param brick
     * @param g2d
     * shows the brick on GameBoard
     */
    private void drawBrick(Brick brick,Graphics2D g2d) {

        Color tmp = g2d.getColor();

        g2d.setColor(brick.getInnerColor());
        g2d.fill(brick.getBrick());

        g2d.setColor(brick.getBorderColor());
        g2d.draw(brick.getBrick());

        g2d.setColor(tmp);
    }

    /**
     * @param ball
     * @param g2d
     * shows the ball on GameBoard
     */
    private void drawBall(Ball ball,Graphics2D g2d) {

        Color tmp = g2d.getColor();

        Shape s = ball.getBallFace();

        g2d.setColor(ball.getInnerColor());
        g2d.fill(s);

        g2d.setColor(ball.getBorderColor());
        g2d.draw(s);

        g2d.setColor(tmp);

    }

    /**
     * @param p
     * @param g2d
     * shows the paddle on GameBoard
     */
    private void drawPlayer(Player p,Graphics2D g2d) {

        Color tmp = g2d.getColor();

        Shape s = p.getPlayerFace();
        g2d.setColor(Player.INNER_COLOR);
        g2d.fill(s);

        g2d.setColor(Player.BORDER_COLOR);
        g2d.draw(s);

        g2d.setColor(tmp);

    }

    /**
     * @param g2d
     * shows pause menu while GameBoard is active
     */
    private void drawMenu(Graphics2D g2d) {

        obscureGameBoard(g2d);
        drawPauseMenu(g2d);

    }

    /**
     * @param g2d
     * darkens the GameBoard while pause menu is shown
     */
    private void obscureGameBoard(Graphics2D g2d) {

        Composite tmp = g2d.getComposite();
        Color tmpColor = g2d.getColor();

        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER,0.55f);
        g2d.setComposite(ac);

        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, DEF_WIDTH, DEF_HEIGHT);

        g2d.setComposite(tmp);
        g2d.setColor(tmpColor);

    }

    /**
     * @param g2d
     * draws pause menu and its elements
     */
    private void drawPauseMenu(Graphics2D g2d) {

        Font tmpFont = g2d.getFont();
        Color tmpColor = g2d.getColor();

        g2d.setFont(menuFont);
        g2d.setColor(MENU_COLOR);

        if(strLen == 0){
            FontRenderContext frc = g2d.getFontRenderContext();
            strLen = menuFont.getStringBounds(PAUSE, frc).getBounds().width;
        }

        int x = (this.getWidth() - strLen) / 2;
        int y = this.getHeight() / 10;

        g2d.drawString(PAUSE, x, y);

        x = this.getWidth() / 2 - 55;
        y = this.getHeight() / 4;

        if(continueButtonRect == null) {

            FontRenderContext frc = g2d.getFontRenderContext();
            continueButtonRect = menuFont.getStringBounds(CONTINUE, frc).getBounds();
            continueButtonRect.setLocation(x,y - continueButtonRect.height);

        }

        g2d.drawString(CONTINUE, x, y);

        y *= 2;

        if(restartButtonRect == null) {

            restartButtonRect = (Rectangle) continueButtonRect.clone();
            restartButtonRect.setLocation(x,y - restartButtonRect.height);
        }

        g2d.drawString(RESTART, x, y);

        y *= 3.0 / 2;

        if(exitButtonRect == null) {

            exitButtonRect = (Rectangle) continueButtonRect.clone();
            exitButtonRect.setLocation(x,y - exitButtonRect.height);

        }

        g2d.drawString(EXIT, x, y);

        g2d.setFont(tmpFont);
        g2d.setColor(tmpColor);

    }

    /**
     * @param keyEvent
     */
    @Override
    public void keyTyped(KeyEvent keyEvent) {

    }

    /**
     * @param keyEvent
     * input binding
     */
    @Override
    public void keyPressed(KeyEvent keyEvent) {

        switch(keyEvent.getKeyCode()) {

            case KeyEvent.VK_A:
                wall.player.moveLeft();
                break;
            case KeyEvent.VK_D:
                wall.player.moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
                showPauseMenu = !showPauseMenu;
                repaint();
                gameTimer.stop();
                break;
            case KeyEvent.VK_SPACE:
                if(!showPauseMenu)
                    if(gameTimer.isRunning())
                        gameTimer.stop();
                    else
                        gameTimer.start();
                break;
            case KeyEvent.VK_F1:
                debugConsole.setVisible(true);
            default:
                wall.player.stop();

        }

    }

    /**
     * @param keyEvent
     * if no keys are pressed, paddle stops moving
     */
    @Override
    public void keyReleased(KeyEvent keyEvent) {
        wall.player.stop();
    }

    /**
     * @param mouseEvent
     * listens for mouse when pause menu is activated
     * Resume = remove pause menu, resumes game
     * Restart = remove pause menu, resets ball, bricks, and paddle
     * Retreat = game closes
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        Point p = mouseEvent.getPoint();

        if(!showPauseMenu)
            return;

        if(continueButtonRect.contains(p)) {

            showPauseMenu = false;
            repaint();

        } else if(restartButtonRect.contains(p)) {

            message = "Restarting Game...";
            wall.ballReset();
            wall.wallReset();
            showPauseMenu = false;

            repaint();

        } else if(exitButtonRect.contains(p)) {
            System.exit(0);
        }

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     */
    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    /**
     * @param mouseEvent
     * changes icon of mouse when hovering over clickable elements
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

        Point p = mouseEvent.getPoint();

        if(exitButtonRect != null && showPauseMenu) {
            if (exitButtonRect.contains(p) || continueButtonRect.contains(p) || restartButtonRect.contains(p))
                this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            else
                this.setCursor(Cursor.getDefaultCursor());
        } else {
            this.setCursor(Cursor.getDefaultCursor());
        }

    }

    /**
     * stops game timer and displays message when window is not focused
     */
    public void onLostFocus() {

        gameTimer.stop();
        message = "Window Focus Lost";
        repaint();

    }

}

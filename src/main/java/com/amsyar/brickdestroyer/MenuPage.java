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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class MenuPage extends JComponent implements MouseListener, MouseMotionListener {

    private static final String GREETINGS = "Welcome to";
    private static final String GAME_TITLE = "Brick Destroy";
    private static final String CREDITS = "Peaceful Version ;)";
    private static final String PLAY_TEXT = "Play";
    private static final String INFO_TEXT = "Info";
    private static final String EXIT_TEXT = "Exit";

    public static final Color BG_COLOR = new Color(0, 0, 0 ,0);
    public static final Color BORDER_COLOR = new Color(19, 18, 18);
    public static final Color DASH_BORDER_COLOR = new  Color(54, 54, 54);
    public static final Color TEXT_COLOR = new Color(25, 98, 24);
    public static final Color CLICKED_BUTTON_COLOR = BG_COLOR.brighter();
    public static final Color CLICKED_TEXT = Color.WHITE;
    public static final int BORDER_SIZE = 10;
    public static final float[] DASHES = {12,6};

    private Rectangle menuFace;
    private Rectangle playButton;
    private Rectangle infoButton;
    private Rectangle exitButton;

    private BasicStroke borderStroke;
    private BasicStroke borderStroke_noDashes;

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameFrame owner;

    private boolean playClicked;
    private boolean infoClicked;
    private boolean exitClicked;

    private final Image backgroundImage = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("/com/amsyar/textures/Menu Background.jpg"));

    /**
     * @param owner
     * @param area
     * defines attribute of menu page
     */
    public MenuPage(GameFrame owner, Dimension area) {

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        playButton = new Rectangle(btnDim);
        infoButton = new Rectangle(btnDim);
        exitButton = new Rectangle(btnDim);

        borderStroke = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,DASHES,0);
        borderStroke_noDashes = new BasicStroke(BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

        greetingsFont = new Font("Noto Mono",Font.PLAIN,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,40);
        creditsFont = new Font("Monospaced",Font.PLAIN,10);
        buttonFont = new Font("Monospaced",Font.PLAIN,playButton.height-2);

    }


    /**
     * @param g
     * creates menu over InfoPage
     */
    public void paint(Graphics g) {
        drawMenu((Graphics2D)g);
    }


    /**
     * @param g2d
     * draws the elements in menu page
     */
    public void drawMenu(Graphics2D g2d) {

        drawContainer(g2d);

        /*
        all the following method calls need a relative
        painting directly into the HomeMenu rectangle,
        so the translation is made here so the other methods do not do that.
         */
        Color prevColor = g2d.getColor();
        Font prevFont = g2d.getFont();

        double x = menuFace.getX();
        double y = menuFace.getY();

        g2d.translate(x,y);

        //methods calls
        drawText(g2d);
        drawButton(g2d);
        //end of methods calls

        g2d.translate(-x,-y);
        g2d.setFont(prevFont);
        g2d.setColor(prevColor);

    }

    /**
     * @param g2d
     * sets colours and background image
     */
    private void drawContainer(Graphics2D g2d) {

        g2d.drawImage(backgroundImage, -400, -100, this);

        Color prev = g2d.getColor();

        g2d.setColor(BG_COLOR);
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStroke_noDashes);
        g2d.setColor(DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStroke);
        g2d.setColor(BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(tmp);

        g2d.setColor(prev);

    }

    /**
     * @param g2d
     * draws text in their respective fonts and colours
     */
    private void drawText(Graphics2D g2d) {

        g2d.setColor(TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(GREETINGS,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(GAME_TITLE,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(CREDITS,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(GREETINGS,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(GAME_TITLE,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(CREDITS,sX,sY);

    }

    /**
     * @param g2d
     * draws buttons in their shapes and positions
     */
    private void drawButton(Graphics2D g2d) {

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(PLAY_TEXT,frc);
        Rectangle2D mTxtRect = buttonFont.getStringBounds(EXIT_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - playButton.width) / 2;
        int y = (int)((menuFace.height - playButton.height) * 0.8);


        // draw start button (renamed to info)
        playButton.setLocation(x,y);

        x = (int)(playButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(playButton.getHeight() - txtRect.getHeight()) / 2;

        x += playButton.x;
        y += playButton.y + (playButton.height * 0.9);

        if (playClicked) {

            Color tmp = g2d.getColor();
            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(playButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(PLAY_TEXT,x,y);
            g2d.setColor(tmp);

        } else {

            g2d.draw(playButton);
            g2d.drawString(PLAY_TEXT,x,y);

        }

        x = playButton.x;
        y = playButton.y;

        y *= 1.2;


        // draw exit button
        exitButton.setLocation(x,y);

        x = (int)(exitButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(exitButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += exitButton.x;
        y += exitButton.y + (playButton.height * 0.9);

        if (infoClicked) {

            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(exitButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(EXIT_TEXT,x,y);
            g2d.setColor(tmp);

        } else {

            g2d.draw(exitButton);
            g2d.drawString(EXIT_TEXT,x,y);

        }

        x = playButton.x;
        y = playButton.y;

        y *= 0.8;


        // draw info button
        infoButton.setLocation(x,y);

        x = (int)(infoButton.getWidth() - mTxtRect.getWidth()) / 2;
        y = (int)(infoButton.getHeight() - mTxtRect.getHeight()) / 2;

        x += infoButton.x;
        y += infoButton.y + (playButton.height * 0.9);

        if (infoClicked) {

            Color tmp = g2d.getColor();

            g2d.setColor(CLICKED_BUTTON_COLOR);
            g2d.draw(infoButton);
            g2d.setColor(CLICKED_TEXT);
            g2d.drawString(INFO_TEXT,x,y);
            g2d.setColor(tmp);

        } else {

            g2d.draw(infoButton);
            g2d.drawString(INFO_TEXT,x,y);

        }

    }

    /**
     * @param mouseEvent
     * if play button is clicked, go to game
     * if info button is clicked, go to info page
     * if exit button is clicked, close game
     */
    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        Point p = mouseEvent.getPoint();

        if (playButton.contains(p)) {
            owner.enableGameBoard();
        } else if (exitButton.contains(p)) {

            System.out.println("WHERE'S YOUR MOTIVATION?");
            System.exit(0);

        } else if(infoButton.contains(p)) {
            owner.enableInfoBoard();
        }
    }

    /**
     * @param mouseEvent
     * changes colour of buttons when pressed
     */
    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        Point p = mouseEvent.getPoint();

        if (playButton.contains(p)) {

            playClicked = true;
            repaint(playButton.x,playButton.y,playButton.width+1,playButton.height+1);

        } else if (exitButton.contains(p)) {

            exitClicked = true;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);

        } else if (infoButton.contains(p)) {

            infoClicked = true;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);

        }
    }

    /**
     * @param mouseEvent
     * changes button colour to normal once mouse is released
     */
    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        if (playClicked) {

            playClicked = false;
            repaint(playButton.x,playButton.y,playButton.width+1,playButton.height+1);

        } else if(exitClicked) {

            exitClicked = false;
            repaint(exitButton.x,exitButton.y,exitButton.width+1,exitButton.height+1);

        } else if (infoClicked) {

            infoClicked = false;
            repaint(infoButton.x,infoButton.y,infoButton.width+1,infoButton.height+1);

        }
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
     * changes cursor icon when hovering over clickable items
     */
    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

        Point p = mouseEvent.getPoint();

        if(playButton.contains(p) || exitButton.contains(p) || infoButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());

    }
}

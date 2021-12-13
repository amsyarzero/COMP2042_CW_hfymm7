package com.amsyar.brickdestroyer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;

public class InfoBoard extends JComponent implements MouseListener, MouseMotionListener {

    private static final String TEXT_LINE_1 = "SPACE to start/pause the game";
    private static final String TEXT_LINE_2 = "A to move left, D to move right";
    private static final String TEXT_LINE_3 = "ESC to open pause menu";
    private static final String TEXT_LINE_4 = "F1 to open debug panel";
    private static final String BACK_TEXT = "Back";

    private Rectangle menuFace;
    private Rectangle backButton;

    private final BasicStroke borderStroke = new BasicStroke(HomeMenu.BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND,0,HomeMenu.DASHES,0);
    private final BasicStroke borderStroke_noDashes = new BasicStroke(HomeMenu.BORDER_SIZE,BasicStroke.CAP_ROUND,BasicStroke.JOIN_ROUND);

    private Font greetingsFont;
    private Font gameTitleFont;
    private Font creditsFont;
    private Font buttonFont;

    private GameFrame owner;

    private boolean backClicked;

    private static final Image backgroundImage = Toolkit.getDefaultToolkit().getImage("src/main/resources/com/amsyar/textures/Menu Background.jpg");


    public InfoBoard(GameFrame owner,Dimension area) {

        this.setFocusable(true);
        this.requestFocusInWindow();

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        this.owner = owner;

        menuFace = new Rectangle(new Point(0,0),area);
        this.setPreferredSize(area);

        Dimension btnDim = new Dimension(area.width / 3, area.height / 12);
        backButton = new Rectangle(btnDim);

        greetingsFont = new Font("Noto Mono",Font.BOLD,25);
        gameTitleFont = new Font("Noto Mono",Font.BOLD,25);
        creditsFont = new Font("Noto Mono",Font.BOLD,25);
        buttonFont = new Font("Monospaced",Font.PLAIN,backButton.height-2);

    }


    public void paint(Graphics g) {
        drawMenu((Graphics2D)g);
    }


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

    private void drawContainer(Graphics2D g2d) {

        g2d.drawImage(backgroundImage, -400, -100, this);

        Color prev = g2d.getColor();

        g2d.setColor(HomeMenu.BG_COLOR);
        g2d.fill(menuFace);

        Stroke tmp = g2d.getStroke();

        g2d.setStroke(borderStroke_noDashes);
        g2d.setColor(HomeMenu.DASH_BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(borderStroke);
        g2d.setColor(HomeMenu.BORDER_COLOR);
        g2d.draw(menuFace);

        g2d.setStroke(tmp);

        g2d.setColor(prev);

    }

    private void drawText(Graphics2D g2d) {

        g2d.setColor(HomeMenu.TEXT_COLOR);

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D greetingsRect = greetingsFont.getStringBounds(TEXT_LINE_1,frc);
        Rectangle2D gameTitleRect = gameTitleFont.getStringBounds(TEXT_LINE_2,frc);
        Rectangle2D creditsRect = creditsFont.getStringBounds(TEXT_LINE_3,frc);

        int sX,sY;

        sX = (int)(menuFace.getWidth() - greetingsRect.getWidth()) / 2;
        sY = (int)(menuFace.getHeight() / 4);

        g2d.setFont(greetingsFont);
        g2d.drawString(TEXT_LINE_1,sX,sY);

        sX = (int)(menuFace.getWidth() - gameTitleRect.getWidth()) / 2;
        sY += (int) gameTitleRect.getHeight() * 1.1;//add 10% of String height between the two strings

        g2d.setFont(gameTitleFont);
        g2d.drawString(TEXT_LINE_2,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(TEXT_LINE_3,sX,sY);

        sX = (int)(menuFace.getWidth() - creditsRect.getWidth()) / 2;
        sY += (int) creditsRect.getHeight() * 1.1;

        g2d.setFont(creditsFont);
        g2d.drawString(TEXT_LINE_4,sX,sY);

    }

    private void drawButton(Graphics2D g2d) {

        FontRenderContext frc = g2d.getFontRenderContext();

        Rectangle2D txtRect = buttonFont.getStringBounds(BACK_TEXT,frc);

        g2d.setFont(buttonFont);

        int x = (menuFace.width - backButton.width) / 2;
        int y = (int)((menuFace.height - backButton.height) * 0.8);

        backButton.setLocation(x,y);

        x = (int)(backButton.getWidth() - txtRect.getWidth()) / 2;
        y = (int)(backButton.getHeight() - txtRect.getHeight()) / 2;

        x += backButton.x;
        y += backButton.y + (backButton.height * 0.9);

        if (backClicked) {

            Color tmp = g2d.getColor();
            g2d.setColor(HomeMenu.CLICKED_BUTTON_COLOR);
            g2d.draw(backButton);
            g2d.setColor(HomeMenu.CLICKED_TEXT);
            g2d.drawString(BACK_TEXT,x,y);
            g2d.setColor(tmp);

        } else {

            g2d.draw(backButton);
            g2d.drawString(BACK_TEXT,x,y);

        }

        x = backButton.x;
        y = backButton.y;

        y *= 1.2;

    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {

        Point p = mouseEvent.getPoint();

        if (backButton.contains(p))
            owner.enableHomeMenu();

    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {

        Point p = mouseEvent.getPoint();

        if (backButton.contains(p)) {

            backClicked = true;
            repaint(backButton.x, backButton.y, backButton.width+1, backButton.height+1);

        }

    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {

        if (backClicked) {

            backClicked = false;
            repaint(backButton.x, backButton.y, backButton.width+1, backButton.height+1);

        }

    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {

    }


    @Override
    public void mouseDragged(MouseEvent mouseEvent) {

    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {

        Point p = mouseEvent.getPoint();

        if (backButton.contains(p))
            this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        else
            this.setCursor(Cursor.getDefaultCursor());
    }

}

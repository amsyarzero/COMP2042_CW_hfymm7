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
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;

public class GameFrame extends JFrame implements WindowFocusListener {

    private static final String DEF_TITLE = "Brick Destroy";

    private GameBoard gameBoard;
    private MenuPage menuPage;
    private InfoPage infoPage;

    private boolean gaming;

    public GameFrame() {

        super();

        gaming = false;

        this.setLayout(new BorderLayout());

        gameBoard = new GameBoard(this);

        menuPage = new MenuPage(this,new Dimension(640,480));
        infoPage = new InfoPage(this,new Dimension(640,480));

        this.add(menuPage,BorderLayout.CENTER);

        this.setUndecorated(true);

    }

    public void initialize() {

        this.setTitle(DEF_TITLE);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.pack();
        this.autoLocate();
        this.setVisible(true);

    }

    public void enableGameBoard() {

        this.dispose();
        this.remove(menuPage);
        this.add(gameBoard, BorderLayout.CENTER);
        this.setUndecorated(true);
        initialize();

        /*to avoid problems with graphics focus controller is added here*/
        this.addWindowFocusListener(this);
        this.addKeyListener(gameBoard);

    }

    public void enableInfoBoard() {

        this.dispose();
        this.remove(menuPage);
        this.add(infoPage, BorderLayout.CENTER);
        this.setUndecorated(true);
        initialize();

    }

    public void enableHomeMenu() {

        this.dispose();
        this.remove(infoPage);
        this.add(menuPage, BorderLayout.CENTER);
        this.setUndecorated(true);
        initialize();

    }

    private void autoLocate() {

        Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (size.width - this.getWidth()) / 2;
        int y = (size.height - this.getHeight()) / 2;
        this.setLocation(x,y);

    }


    @Override
    public void windowGainedFocus(WindowEvent windowEvent) {
        /*
            the first time the frame loses focus is because
            it has been disposed to install the GameBoard,
            so went it regains the focus it's ready to play.
            of course calling a method such as 'onLostFocus'
            is useful only if the GameBoard as been displayed
            at least once
         */
        gaming = true;
    }

    @Override
    public void windowLostFocus(WindowEvent windowEvent) {

        if(gaming)
            gameBoard.onLostFocus();

    }
}

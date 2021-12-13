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
import java.awt.event.WindowListener;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class DebugConsole extends JDialog implements WindowListener{

    private static final String TITLE = "Debug Console";

    private JFrame owner;
    private DebugPanel debugPanel;
    private GameBoard gameBoard;
    private Wall wall;

    /**
     * @param owner
     * @param wall
     * @param ball
     * @param gameBoard
     * defines attribute of debug console
     */
    public DebugConsole(JFrame owner, Wall wall, Ball ball, GameBoard gameBoard){

        this.wall = wall;
        this.owner = owner;
        this.gameBoard = gameBoard;

        initialize();

        debugPanel = new DebugPanel(wall, ball);
        this.add(debugPanel,BorderLayout.CENTER);

        this.pack();
    }

    /**
     * initializes the debug console with these settings
     */
    private void initialize() {

        this.setModal(true);
        this.setTitle(TITLE);
        this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        this.setLayout(new BorderLayout());
        this.addWindowListener(this);
        this.setFocusable(true);

    }


    /**
     * sets location of debug console to the exact middle
     */
    private void setLocation() {

        int x = ((owner.getWidth() - this.getWidth()) / 2) + owner.getX();
        int y = ((owner.getHeight() - this.getHeight()) / 2) + owner.getY();
        this.setLocation(x,y);

    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowOpened(WindowEvent windowEvent) {

    }

    /**
     * @param windowEvent
     * shows the game in its current state after closing debug console
     */
    @Override
    public void windowClosing(WindowEvent windowEvent) {
        gameBoard.repaint();
    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowClosed(WindowEvent windowEvent) {

    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowIconified(WindowEvent windowEvent) {

    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowDeiconified(WindowEvent windowEvent) {

    }

    /**
     * @param windowEvent
     * opens debug console in the middle of the window, then gets needed attributes
     */
    @Override
    public void windowActivated(WindowEvent windowEvent) {

        setLocation();
        Ball b = wall.ball;

        debugPanel.setValues(b.getXVelocity(),b.getYVelocity());

    }

    /**
     * @param windowEvent
     */
    @Override
    public void windowDeactivated(WindowEvent windowEvent) {

    }
}

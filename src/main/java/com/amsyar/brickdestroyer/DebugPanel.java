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
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class DebugPanel extends JPanel {

    private static final Color DEF_BKG = Color.WHITE;

    private JButton skipLevel;
    private JButton resetBalls;

    private JSlider ballXSpeed;
    private JSlider ballYSpeed;

    private Wall wall;
    private Ball ball;

    /**
     * @param wall
     * @param ball
     * defines the attributes of the debug console
     */
    public DebugPanel(Wall wall, Ball ball) {

        this.wall = wall;
        this.ball = ball;

        initialize();

        skipLevel = makeButton("Skip Level",e -> wall.nextLevel());
        resetBalls = makeButton("Reset Ball Count",e -> wall.resetBallCount());

        ballXSpeed = makeSlider(-4,4,e -> ball.setXVelocity(ballXSpeed.getValue()));
        ballYSpeed = makeSlider(-4,4,e -> ball.setYVelocity(ballYSpeed.getValue()));

        this.add(skipLevel);
        this.add(resetBalls);

        this.add(ballXSpeed);
        this.add(ballYSpeed);

    }

    /**
     * sets background and layout of the panel
     */
    private void initialize() {

        this.setBackground(DEF_BKG);
        this.setLayout(new GridLayout(2,2));

    }

    /**
     * @param title
     * @param e
     * @return
     * creates a button at the specified position
     */
    private JButton makeButton(String title, ActionListener e) {

        JButton out = new JButton(title);
        out.addActionListener(e);

        return out;

    }

    /**
     * @param min
     * @param max
     * @param e
     * @return
     * creates sliders at specified positions
     */
    private JSlider makeSlider(int min, int max, ChangeListener e) {

        JSlider out = new JSlider(min,max);
        out.setMajorTickSpacing(1);
        out.setSnapToTicks(true);
        out.setPaintTicks(true);
        out.addChangeListener(e);

        return out;
    }

    /**
     * @param x
     * @param y
     * set ball's x and y velocity values with sliders
     */
    public void setValues(int x,int y) {

        ballXSpeed.setValue(x);
        ballYSpeed.setValue(y);

    }

}

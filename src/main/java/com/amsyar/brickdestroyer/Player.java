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

import java.awt.*;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class Player {

    public static final Color BORDER_COLOR = Color.BLACK;
    public static final Color INNER_COLOR = Color.BLACK;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point initPlayerPos;
    private int moveAmount;
    private int min;
    private int max;

    /**
     * @param initPlayerPos
     * @param width
     * @param height
     * @param container
     * defines attribute of the paddle controlled by the player
     */
    public Player(Point initPlayerPos, int width, int height, Rectangle container) {

        this.initPlayerPos = initPlayerPos;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    /**
     * @param width
     * @param height
     * @return
     * draw paddle
     */
    private Rectangle makeRectangle(int width, int height) {

        Point p = new Point((int)(initPlayerPos.getX() - (width / 2)),(int)initPlayerPos.getY());
        return  new Rectangle(p,new Dimension(width,height));

    }

    /**
     * @param b
     * @return
     * if ball hits paddle, return true
     */
    public boolean impactOnPlayer(Ball b) {
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    /**
     * moves paddle by this amount, and stops paddle from crossing the window border
     */
    public void movePaddle() {

        double x = initPlayerPos.getX() + moveAmount;

        if (x < min || x > max)
            return;

        initPlayerPos.setLocation(x,initPlayerPos.getY());
        playerFace.setLocation(initPlayerPos.x - (int)playerFace.getWidth()/2,initPlayerPos.y);

    }

    /**
     * move paddle by -5 horizontally
     */
    public void moveLeft() {
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    /**
     * move paddle by 5 horizontally
     */
    public void moveRight() {
        moveAmount = DEF_MOVE_AMOUNT;
    }

    /**
     * stops the paddle
     */
    public void stop() {
        moveAmount = 0;
    }

    /**
     * @return
     * gets paddle shape
     */
    public Shape getPlayerFace() {
        return  playerFace;
    }

    /**
     * @param p
     * move paddle to a specified position
     */
    public void moveTo(Point p) {

        initPlayerPos.setLocation(p);
        playerFace.setLocation(initPlayerPos.x - (int)playerFace.getWidth()/2,initPlayerPos.y);

    }
}

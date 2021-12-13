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

public class Player {

    public static final Color BORDER_COLOR = Color.BLACK;
    public static final Color INNER_COLOR = Color.BLACK;

    private static final int DEF_MOVE_AMOUNT = 5;

    private Rectangle playerFace;
    private Point initPlayerPos;
    private int moveAmount;
    private int min;
    private int max;

    public Player(Point initPlayerPos, int width, int height, Rectangle container) {

        this.initPlayerPos = initPlayerPos;
        moveAmount = 0;
        playerFace = makeRectangle(width, height);
        min = container.x + (width / 2);
        max = min + container.width - width;

    }

    private Rectangle makeRectangle(int width, int height) {

        Point p = new Point((int)(initPlayerPos.getX() - (width / 2)),(int)initPlayerPos.getY());
        return  new Rectangle(p,new Dimension(width,height));

    }

    public boolean impact(Ball b) {
        return playerFace.contains(b.getPosition()) && playerFace.contains(b.down) ;
    }

    public void movePaddle() {

        double x = initPlayerPos.getX() + moveAmount;

        if (x < min || x > max)
            return;

        initPlayerPos.setLocation(x,initPlayerPos.getY());
        playerFace.setLocation(initPlayerPos.x - (int)playerFace.getWidth()/2,initPlayerPos.y);

    }

    public void moveLeft() {
        moveAmount = -DEF_MOVE_AMOUNT;
    }

    public void moveRight() {
        moveAmount = DEF_MOVE_AMOUNT;
    }

    public void stop() {
        moveAmount = 0;
    }

    public Shape getPlayerFace() {
        return  playerFace;
    }

    public void moveTo(Point p) {

        initPlayerPos.setLocation(p);
        playerFace.setLocation(initPlayerPos.x - (int)playerFace.getWidth()/2,initPlayerPos.y);

    }
}

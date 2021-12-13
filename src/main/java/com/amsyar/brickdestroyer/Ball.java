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
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public abstract class Ball {

    private Shape ballFace;

    private Point2D ballCenter;

    Point2D up;
    Point2D down;
    Point2D left;
    Point2D right;

    private Color border;
    private Color inner;

    private int speedX;
    private int speedY;

    /**
     * @param ballCenter
     * @param radiusA
     * @param radiusB
     * @param inner
     * @param border
     * defines what a brick is along with its attributes
     */
    public Ball(Point2D ballCenter, int radiusA, int radiusB, Color inner, Color border) {

        this.ballCenter = ballCenter;

        up = new Point2D.Double();
        down = new Point2D.Double();
        left = new Point2D.Double();
        right = new Point2D.Double();

        up.setLocation(ballCenter.getX(),ballCenter.getY() - (radiusB / 2));
        down.setLocation(ballCenter.getX(),ballCenter.getY() + (radiusB / 2));

        left.setLocation(ballCenter.getX() - (radiusA /2), ballCenter.getY());
        right.setLocation(ballCenter.getX() + (radiusA /2), ballCenter.getY());

        ballFace = makeBall(ballCenter, radiusA, radiusB);
        this.border = border;
        this.inner  = inner;
        speedX = 0;
        speedY = 0;

    }

    /**
     * @param center
     * @param radiusA
     * @param radiusB
     * @return
     */
    protected abstract Shape makeBall(Point2D center, int radiusA, int radiusB);

    /**
     * move the ball based on the velocity given
     */
    public void moveBall() {

        RectangularShape tmp = (RectangularShape) ballFace;
        ballCenter.setLocation((ballCenter.getX() + speedX), (ballCenter.getY() + speedY));
        double width = tmp.getWidth();
        double height = tmp.getHeight();

        tmp.setFrame((ballCenter.getX() -(width / 2)), (ballCenter.getY() - (height / 2)), width, height);
        setPoints(width, height);

        ballFace = tmp;

    }

    /**
     * @param x
     * @param y
     * sets the initial velocity of the ball
     */
    public void setInitVelocity(int x, int y) {

        speedX = x;
        speedY = y;

    }

    /**
     * @param s
     * sets the x velocity for the ball
     */
    public void setXVelocity(int s) {
        speedX = s;
    }

    /**
     * @param s
     * sets the y velocity for the ball
     */
    public void setYVelocity(int s) {
        speedY = s;
    }

    /**
     * reverses the x velocity
     */
    public void reverseX() {
        speedX *= -1;
    }

    /**
     * reverses the y velocity
     */
    public void reverseY() {
        speedY *= -1;
    }

    /**
     * @return
     * gets the colour of the ball's border
     */
    public Color getBorderColor() {
        return border;
    }

    /**
     * @return
     * gets the colour of the ball
     */
    public Color getInnerColor() {
        return inner;
    }

    /**
     * @return
     * gets the ball's position
     */
    public Point2D getPosition() {
        return ballCenter;
    }

    /**
     * @return
     * gets the ball's shape
     */
    public Shape getBallFace() {
        return ballFace;
    }

    /**
     * @param p
     * moves the ball to a specific coordinate
     */
    public void moveTo(Point p) {

        ballCenter.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double width = tmp.getWidth();
        double height = tmp.getHeight();

        tmp.setFrame((ballCenter.getX() -(width / 2)), (ballCenter.getY() - (height / 2)), width, height);
        ballFace = tmp;

    }

    /**
     * @param width
     * @param height
     * set x and y points for the ball
     */
    private void setPoints(double width, double height) {

        up.setLocation(ballCenter.getX(),ballCenter.getY() - (height / 2));
        down.setLocation(ballCenter.getX(),ballCenter.getY() + (height / 2));

        left.setLocation(ballCenter.getX() - (width / 2), ballCenter.getY());
        right.setLocation(ballCenter.getX() + (width / 2), ballCenter.getY());

    }

    /**
     * @return
     * gets the x velocity of the ball
     */
    public int getXVelocity() {
        return speedX;
    }

    /**
     * @return
     * gets the y velocity of the ball
     */
    public int getYVelocity() {
        return speedY;
    }

}

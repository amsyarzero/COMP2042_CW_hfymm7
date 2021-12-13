package com.amsyar.brickdestroyer;

import java.awt.*;
import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;

abstract public class Ball {

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

    protected abstract Shape makeBall(Point2D center, int radiusA, int radiusB);

    public void move() {

        RectangularShape tmp = (RectangularShape) ballFace;
        ballCenter.setLocation((ballCenter.getX() + speedX), (ballCenter.getY() + speedY));
        double width = tmp.getWidth();
        double height = tmp.getHeight();

        tmp.setFrame((ballCenter.getX() -(width / 2)), (ballCenter.getY() - (height / 2)), width, height);
        setPoints(width, height);

        ballFace = tmp;

    }

    public void setSpeed(int x, int y) {

        speedX = x;
        speedY = y;

    }

    public void setXSpeed(int s) {
        speedX = s;
    }

    public void setYSpeed(int s) {
        speedY = s;
    }

    public void reverseX() {
        speedX *= -1;
    }

    public void reverseY() {
        speedY *= -1;
    }

    public Color getBorderColor() {
        return border;
    }

    public Color getInnerColor() {
        return inner;
    }

    public Point2D getPosition() {
        return ballCenter;
    }

    public Shape getBallFace() {
        return ballFace;
    }

    public void moveTo(Point p) {

        ballCenter.setLocation(p);

        RectangularShape tmp = (RectangularShape) ballFace;
        double width = tmp.getWidth();
        double height = tmp.getHeight();

        tmp.setFrame((ballCenter.getX() -(width / 2)), (ballCenter.getY() - (height / 2)), width, height);
        ballFace = tmp;

    }

    private void setPoints(double width, double height) {

        up.setLocation(ballCenter.getX(),ballCenter.getY() - (height / 2));
        down.setLocation(ballCenter.getX(),ballCenter.getY() + (height / 2));

        left.setLocation(ballCenter.getX() - (width / 2), ballCenter.getY());
        right.setLocation(ballCenter.getX() + (width / 2), ballCenter.getY());

    }

    public int getSpeedX() {
        return speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

}

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
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class Crack {

    private static final int CRACK_SECTIONS = 3;
    private static final double JUMP_PROBABILITY = 0.7;

    public static final int LEFT = 10;
    public static final int RIGHT = 20;
    public static final int UP = 30;
    public static final int DOWN = 40;
    public static final int VERTICAL = 100;
    public static final int HORIZONTAL = 200;

    private GeneralPath crack;

    private int crackDepth;
    private Shape brickFace;
    private int steps;

    /**
     * @param crackDepth
     * @param steps
     * @param brickFace
     * defines the attributes of cracks
     */
    public Crack(int crackDepth, int steps, Shape brickFace) {

        crack = new GeneralPath();
        this.crackDepth = crackDepth;
        this.steps = steps;
        this.brickFace = brickFace;

    }

    /**
     * @return
     * returns crack shape
     */
    public GeneralPath draw() {
        return crack;
    }

    /**
     * deletes the cracks on a brick
     */
    public void reset() {
        crack.reset();
    }

    /**
     * @param point
     * @param direction
     * determines the direction of cracks to be drawn
     */
    protected void makeCrack(Point2D point, int direction) {

        Rectangle bounds = brickFace.getBounds();

        Point impact = new Point((int)point.getX(),(int)point.getY());
        Point start = new Point();
        Point end = new Point();


        switch (direction) {

            case LEFT:
                start.setLocation(bounds.x + bounds.width, bounds.y);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                Point tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case RIGHT:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,VERTICAL);
                makeCrack(impact,tmp);

                break;
            case UP:
                start.setLocation(bounds.x, bounds.y + bounds.height);
                end.setLocation(bounds.x + bounds.width, bounds.y + bounds.height);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);
                break;
            case DOWN:
                start.setLocation(bounds.getLocation());
                end.setLocation(bounds.x + bounds.width, bounds.y);
                tmp = makeRandomPoint(start,end,HORIZONTAL);
                makeCrack(impact,tmp);

                break;

        }

    }

    /**
     * @param start
     * @param end
     * determines the start and end points of cracks to be drawn
     */
    protected void makeCrack(Point start, Point end) {

        GeneralPath path = new GeneralPath();

        path.moveTo(start.x,start.y);

        double w = (end.x - start.x) / (double)steps;
        double h = (end.y - start.y) / (double)steps;

        int bound = crackDepth;
        int jump  = bound * 5;

        double x,y;

        for (int i = 1; i < steps;i++) {

            x = (i * w) + start.x;
            y = (i * h) + start.y + randomInBounds(bound);

            if(inMiddle(i,CRACK_SECTIONS,steps))
                y += jumps(jump,JUMP_PROBABILITY);

            path.lineTo(x,y);

        }

        path.lineTo(end.x,end.y);
        crack.append(path,true);

    }

    /**
     * @param bound
     * @return
     * returns a random point within brick boundaries
     */
    private int randomInBounds(int bound) {

        int n = (bound * 2) + 1;
        return Brick.rnd.nextInt(n) - bound;

    }

    /**
     * @param i
     * @param steps
     * @param divisions
     * @return
     * returns true if it's in middle of brick
     */
    private boolean inMiddle(int i,int steps,int divisions) {

        int low = (steps / divisions);
        int up = low * (divisions - 1);

        return  (i > low) && (i < up);

    }

    /**
     * @param bound
     * @param probability
     * @return
     */
    private int jumps(int bound,double probability) {

        if(Brick.rnd.nextDouble() > probability)
            return randomInBounds(bound);

        return  0;

    }

    /**
     * @param from
     * @param to
     * @param direction
     * @return
     * returns random x and y position
     */
    private Point makeRandomPoint(Point from,Point to, int direction) {

        Point out = new Point();
        int pos;

        switch(direction) {

            case HORIZONTAL:
                pos = Brick.rnd.nextInt(to.x - from.x) + from.x;
                out.setLocation(pos,to.y);
                break;
            case VERTICAL:
                pos = Brick.rnd.nextInt(to.y - from.y) + from.y;
                out.setLocation(to.x,pos);
                break;

        }

        return out;

    }

}

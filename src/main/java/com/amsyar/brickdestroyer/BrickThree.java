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
import java.util.Random;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class BrickThree extends Brick {

    private static final String NAME = "Brick Three";
    private static final Color DEF_INNER = Color.BLACK;
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int STEEL_STRENGTH = 1;
    private static final double STEEL_PROBABILITY = 0.4;

    private Random rnd;
    private Shape brickFace;

    /**
     * @param point
     * @param size
     * sets the shape and attribute of "Brick Three"
     */
    public BrickThree(Point point, Dimension size) {

        super(NAME, point, size, DEF_BORDER, DEF_INNER, STEEL_STRENGTH);
        rnd = new Random();
        brickFace = super.brickFace;

    }

    /**
     * @param pos
     * @param size
     * @return
     * returns brick size and position
     */
    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    /**
     * @return
     * gets brick shape
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    public boolean setImpact(Point2D point , int dir) {

        if(super.isBrickBroken())
            return false;

        impact();
        return super.isBrickBroken();

    }

    /**
     * if brick is broken, then ball won't trigger impact on brick. else, it will crack the brick
     */
    public void impact() {

        if(rnd.nextDouble() < STEEL_PROBABILITY) {
            super.impact();
        }

    }

}

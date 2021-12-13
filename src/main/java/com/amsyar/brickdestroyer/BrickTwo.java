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
public class BrickTwo extends Brick {

    private static final String NAME = "Brick Two";
    private static final Color DEF_INNER = Color.GRAY;
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;

    /**
     * @param point
     * @param size
     * sets the shape and attribute of "Brick Two"
     */
    public BrickTwo(Point point, Dimension size) {

        super(NAME,point, size, DEF_BORDER, DEF_INNER, CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS, super.brickFace);
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
     * @param point
     * @param dir
     * @return
     * if brick is broken, then ball won't trigger impact on brick
     */
    @Override
    public boolean trackForImpact(Point2D point, int dir) {

        if(super.isBrickBroken())
            return false;

        super.impact();
        if(!super.isBrickBroken()) {

            crack.makeCrack(point,dir);
            updateBrick();
            return false;

        }
        return true;

    }


    /**
     * @return
     * gets brick shape
     */
    @Override
    public Shape getBrick() {
        return brickFace;
    }

    private void updateBrick() {

        if(!super.isBrickBroken()) {

            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;

        }

    }

    /**
     * repairs the brick and deletes any existing cracks
     */
    public void repair() {

        super.repair();
        crack.reset();
        brickFace = super.brickFace;

    }
}

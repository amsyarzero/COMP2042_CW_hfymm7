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
import java.util.Random;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class BrickFour extends Brick {

    private static final String NAME = "Brick of Hell";
    private static final Color DEF_INNER = new Color(255, 36, 0);
    private static final Color DEF_BORDER = Color.yellow;
    private static final int HELL_STRENGTH = 3;
    private static final double TOUGH_PROBABILITY = 0.5;
    private static final double REPAIR_PROBABILITY = 0.25;

    private Random rnd;
    private Crack crack;
    private Shape brickFace;

    /**
     * @param point
     * @param shape
     * sets the shape and attribute of "Brick of Hell"
     */
    public BrickFour(Point point, Dimension shape) {

        super(NAME,point,shape,DEF_BORDER, DEF_INNER, HELL_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS, super.brickFace);
        brickFace = super.brickFace;
        rnd = new Random();

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
     * if brick is broken, then ball won't trigger impact on brick. else, it will crack the brick
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
     * @param point
     * @param dir
     * @return
     */
    public boolean impact(Point2D point, int dir) {

        if(rnd.nextDouble() < TOUGH_PROBABILITY) {

            super.impact();
            if(!super.isBrickBroken()) {

                crack.makeCrack(point,dir);
                updateBrick();

                return false;
            }

        } else {

            crack.makeCrack(point,dir);
            repair();

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

    /**
     * updates the brick, appending cracks onto it
     */
    private void updateBrick() {

        if(!super.isBrickBroken()) {

            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;

        }

    }

    /**
     * repairs the brick and deletes any existing cracks ONLY if the generated number is lower than REPAIR_PROBABILITY
     */
    public void repair(){

        if(rnd.nextDouble() < REPAIR_PROBABILITY) {

            System.out.println("repaired");
            super.repair();
            crack.reset();
            brickFace = super.brickFace;

        }

    }

}

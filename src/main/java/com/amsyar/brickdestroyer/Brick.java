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
import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Random;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public abstract class Brick {

    public static final int DEF_CRACK_DEPTH = 1;
    public static final int DEF_STEPS = 35;

    public static final int UP_IMPACT = 100;
    public static final int DOWN_IMPACT = 200;
    public static final int LEFT_IMPACT = 300;
    public static final int RIGHT_IMPACT = 400;

    public static Random rnd;

    private String name;
    public Shape brickFace;

    private Color border;
    private Color inner;

    private int maxStrength;
    private int strength;

    private boolean brickBroken;

    /**
     * @param name
     * @param pos
     * @param size
     * @param border
     * @param inner
     * @param strength
     * defines what a brick is along with its attributes
     */
    public Brick(String name, Point pos, Dimension size, Color border, Color inner, int strength) {

        rnd = new Random();
        brickBroken = false;
        this.name = name;
        brickFace = makeBrickFace(pos,size);
        this.border = border;
        this.inner = inner;
        this.maxStrength = this.strength = strength;

    }

    /**
     * @param pos 
     * @param size
     * @return
     */
    protected abstract Shape makeBrickFace(Point pos,Dimension size);

    /**
     * @param point
     * @param dir
     * @return
     * if brick is broken, then ball won't trigger impact on brick
     */
    public boolean trackForImpact(Point2D point, int dir) {

        if (brickBroken)
            return false;
        else
            impact();

        return brickBroken;

    }

    /**
     * @return
     */
    public abstract Shape getBrick();

    /**
     * @return
     * gets the colour of the brick's border
     */
    public Color getBorderColor() {
        return border;
    }

    /**
     * @return
     * gets the colour of the brick
     */
    public Color getInnerColor() {
        return inner;
    }

    /**
     * @param b
     * @return
     * returns a value based on the location of the ball's collision with a brick
     */
    public final int findImpact(Ball b) {

        if (brickBroken)
            return 0;

        int out  = 0;
        if (brickFace.contains(b.right))
            out = LEFT_IMPACT;
        else if (brickFace.contains(b.left))
            out = RIGHT_IMPACT;
        else if (brickFace.contains(b.up))
            out = DOWN_IMPACT;
        else if (brickFace.contains(b.down))
            out = UP_IMPACT;

        return out;

    }

    /**
     * @return 
     */
    public final boolean isBrickBroken() {
        return brickBroken;
    }

    /**
     * brick is repaired and set to max strength
     */
    public void repair() {

        brickBroken = false;
        strength = maxStrength;

    }

    /**
     * decrements the strength of the brick by 1 until it reaches 0, where it then breaks
     */
    public void impact() {

        strength--;
        brickBroken = (strength == 0);

    }

}

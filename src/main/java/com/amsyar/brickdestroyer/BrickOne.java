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

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class BrickOne extends Brick {

    private static final String NAME = "Brick One";
    private static final Color DEF_INNER = Color.WHITE;
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int CLAY_STRENGTH = 1;

    /**
     * @param point
     * @param size
     * sets the shape and attribute of "Brick One"
     */
    public BrickOne(Point point, Dimension size) {
        super(NAME,point,size,DEF_BORDER, DEF_INNER, CLAY_STRENGTH);
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
        return super.brickFace;
    }

}

package com.amsyar.brickdestroyer;

import java.awt.*;
import java.awt.Point;

public class BrickOne extends Brick {

    private static final String NAME = "Brick One";
    private static final Color DEF_INNER = Color.WHITE;
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int CLAY_STRENGTH = 1;

    public BrickOne(Point point, Dimension size) {
        super(NAME,point,size,DEF_BORDER, DEF_INNER, CLAY_STRENGTH);
    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public Shape getBrick() {
        return super.brickFace;
    }

}

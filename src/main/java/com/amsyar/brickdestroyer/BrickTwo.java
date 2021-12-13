package com.amsyar.brickdestroyer;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

public class BrickTwo extends Brick {

    private static final String NAME = "Brick Two";
    private static final Color DEF_INNER = Color.GRAY;
    private static final Color DEF_BORDER = Color.BLACK;
    private static final int CEMENT_STRENGTH = 2;

    private Crack crack;
    private Shape brickFace;

    public BrickTwo(Point point, Dimension size) {

        super(NAME,point, size, DEF_BORDER, DEF_INNER, CEMENT_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS, super.brickFace);
        brickFace = super.brickFace;

    }

    @Override
    protected Shape makeBrickFace(Point pos, Dimension size) {
        return new Rectangle(pos, size);
    }

    @Override
    public boolean setImpact(Point2D point, int dir) {

        if(super.isBroken())
            return false;

        super.impact();
        if(!super.isBroken()) {

            crack.makeCrack(point,dir);
            updateBrick();
            return false;

        }
        return true;

    }


    @Override
    public Shape getBrick() {
        return brickFace;
    }

    private void updateBrick() {

        if(!super.isBroken()) {

            GeneralPath gp = crack.draw();
            gp.append(super.brickFace,false);
            brickFace = gp;

        }

    }

    public void repair() {

        super.repair();
        crack.reset();
        brickFace = super.brickFace;

    }
}

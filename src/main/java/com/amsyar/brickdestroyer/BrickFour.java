package com.amsyar.brickdestroyer;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.Random;

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

    public BrickFour(Point point, Dimension shape) {

        super(NAME,point,shape,DEF_BORDER, DEF_INNER, HELL_STRENGTH);
        crack = new Crack(DEF_CRACK_DEPTH, DEF_STEPS, super.brickFace);
        brickFace = super.brickFace;
        rnd = new Random();

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

    public boolean impact(Point2D point, int dir) {

        if(rnd.nextDouble() < TOUGH_PROBABILITY) {

            super.impact();
            if(!super.isBroken()) {

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

    public void repair(){

        if(rnd.nextDouble() < REPAIR_PROBABILITY) {
            repairBrick();
        }

    }

    public void repairBrick() {
        super.repair();
        crack.reset();
        brickFace = super.brickFace;
    }

}

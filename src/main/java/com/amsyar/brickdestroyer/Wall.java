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
import java.io.File;
import java.util.Random;
import java.util.Scanner;

/**
 * edited by amsyarzero
 * 13/12/2021
 */
public class Wall {

    private static final int LEVELS_COUNT = 5;

    public int currentScore = 0;
    public int highScore;
    public int higherScore;

    private static final int CLAY = 1;
    private static final int CEMENT = 2;
    private static final int STEEL = 3;
    private static final int HELLRISE = 4;

    private Random rnd;
    private Rectangle area;

    Brick[] bricks;
    Ball ball;
    Player player;

    private Brick[][] levels;
    private int level;

    private Point startPoint;
    private int brickTotal;
    private int ballCount;
    private boolean ballLost;

    private Crack crack;
    
    private int velocityX;
    private int velocityY;

    /**
     * @param drawArea
     * @param brickTotal
     * @param lineTotal
     * @param brickDimensionRatio
     * @param ballPos
     * defines attribute for "drawing" area, brick total, brick rows, brick ratio, and ball + paddle initial position
     */
    public Wall(Rectangle drawArea, int brickTotal, int lineTotal, double brickDimensionRatio, Point ballPos) {

        this.startPoint = new Point(ballPos);

        levels = makeLevels(drawArea, brickTotal, lineTotal, brickDimensionRatio);
        level = 0;

        ballCount = 3;
        ballLost = false;

        rnd = new Random();

        makeBall(ballPos);
        
        do {
            velocityX = rnd.nextInt(5) - 2;
        } while(velocityX == 0);
        
        do {
            velocityY = -rnd.nextInt(3);
        } while(velocityY == 0);

        ball.setInitVelocity(velocityX, velocityY);

        player = new Player((Point) ballPos.clone(),150,10, drawArea);

        area = drawArea;
        
    }

    /**
     * @param drawArea
     * @param brickTotal
     * @param lineTotal
     * @param brickSizeRatio
     * @param type
     * @return
     * make brick layout for single types only (Brick One and Brick of Hell)
     */
    private Brick[] makeSingleTypeLevel(Rectangle drawArea, int brickTotal, int lineTotal, double brickSizeRatio, int type) {
        /*
          if brickTotal is not divisible by line count,brickTotal is adjusted to the biggest
          multiple of lineTotal smaller then brickTotal
         */
        brickTotal -= brickTotal % lineTotal;

        int brickOnLine = brickTotal / lineTotal;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickTotal += lineTotal / 2;

        Brick[] tmp  = new Brick[brickTotal];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++) {
            
            int line = i / brickOnLine;
            
            if(line == lineTotal)
                break;
            
            double x = (i % brickOnLine) * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x, y);
            tmp[i] = makeBrick(p, brickSize, type);
            
        }

        for(double y = brickHgt; i < tmp.length; i++, y += 2*brickHgt) {
            
            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = new BrickOne(p, brickSize);
            
        }
        
        return tmp;

    }

    /**
     * @param drawArea
     * @param brickTotal
     * @param lineTotal
     * @param brickSizeRatio
     * @param typeA
     * @param typeB
     * @return
     * make mixed bricks layout (Brick Two, Brick Three)
     */
    private Brick[] makeChessboardLevel(Rectangle drawArea, int brickTotal, int lineTotal, double brickSizeRatio, int typeA, int typeB) {
        /*
          if brickTotal is not divisible by line count,brickTotal is adjusted to the biggest
          multiple of lineTotal smaller then brickTotal
         */
        brickTotal -= brickTotal % lineTotal;

        int brickOnLine = brickTotal / lineTotal;

        int centerLeft = brickOnLine / 2 - 1;
        int centerRight = brickOnLine / 2 + 1;

        double brickLen = drawArea.getWidth() / brickOnLine;
        double brickHgt = brickLen / brickSizeRatio;

        brickTotal += lineTotal / 2;

        Brick[] tmp  = new Brick[brickTotal];

        Dimension brickSize = new Dimension((int) brickLen,(int) brickHgt);
        Point p = new Point();

        int i;
        for(i = 0; i < tmp.length; i++) {

            int line = i / brickOnLine;

            if(line == lineTotal)
                break;

            int posX = i % brickOnLine;
            double x = posX * brickLen;
            x =(line % 2 == 0) ? x : (x - (brickLen / 2));
            double y = (line) * brickHgt;
            p.setLocation(x,y);

            boolean b = ((line % 2 == 0 && i % 2 == 0) || (line % 2 != 0 && posX > centerLeft && posX <= centerRight));
            tmp[i] = b ?  makeBrick(p,brickSize,typeA) : makeBrick(p,brickSize,typeB);

        }

        for(double y = brickHgt;i < tmp.length;i++, y += 2*brickHgt) {

            double x = (brickOnLine * brickLen) - (brickLen / 2);
            p.setLocation(x,y);
            tmp[i] = makeBrick(p,brickSize,typeA);

        }

        return tmp;

    }

    /**
     * @param ballPos
     * creates the ball at starting position
     */
    private void makeBall(Point2D ballPos) {
        ball = new BlackBall(ballPos);
    }

    private Brick[][] makeLevels(Rectangle drawArea,int brickTotal,int lineTotal,double brickDimensionRatio) {

        Brick[][] tmp = new Brick[LEVELS_COUNT][];
        tmp[0] = makeSingleTypeLevel(drawArea,brickTotal,lineTotal,brickDimensionRatio,CLAY);
        tmp[1] = makeChessboardLevel(drawArea,brickTotal,lineTotal,brickDimensionRatio,CLAY,CEMENT);
        tmp[2] = makeChessboardLevel(drawArea,brickTotal,lineTotal,brickDimensionRatio,CLAY,STEEL);
        tmp[3] = makeChessboardLevel(drawArea,brickTotal,lineTotal,brickDimensionRatio,STEEL,CEMENT);
        tmp[4] = makeSingleTypeLevel(drawArea,brickTotal,lineTotal,brickDimensionRatio,HELLRISE);

        return tmp;

    }

    /**
     * calls methods to move paddle and ball
     */
    public void move() {

        player.movePaddle();
        ball.moveBall();

    }

    /**
     * if paddle and ball collide, reverse y velocity of ball
     * if ball collides with brick, reverse either x or y velocity of ball depending on brick surface
     */
    public void findImpacts() {

        if(player.impactOnPlayer(ball)) {
            ball.reverseY();
        } else if(impactWall()) {
            /*for efficiency reverse is done into method impactWall
            * because for every brick program checks for horizontal and vertical impacts
            */
            brickTotal--;
        } else if(impactBorder()) {
            ball.reverseX();
        } else if(ball.getPosition().getY() < area.getY()) {
            ball.reverseY();
        } else if(ball.getPosition().getY() > area.getY() + area.getHeight()) {

            ballCount--;
            ballLost = true;

        }

    }

    /**
     * @return
     * changes ball direction when colliding with a brick, determined by the brick surface orientation
     */
    private boolean impactWall() {

        for(Brick b : bricks) {

            switch(b.findImpact(ball)) {
                //Vertical Impact
                case Brick.UP_IMPACT:
                    ball.reverseY();
                    return b.trackForImpact(ball.down, crack.UP);
                case Brick.DOWN_IMPACT:
                    ball.reverseY();
                    return b.trackForImpact(ball.up, crack.DOWN);

                //Horizontal Impact
                case Brick.LEFT_IMPACT:
                    ball.reverseX();
                    return b.trackForImpact(ball.right, crack.RIGHT);
                case Brick.RIGHT_IMPACT:
                    ball.reverseX();
                    return b.trackForImpact(ball.left, crack.LEFT);

            }

        }

        return false;

    }

    /**
     * @return
     * changes ball direction when it hits window border
     */
    private boolean impactBorder() {

        Point2D p = ball.getPosition();
        return ((p.getX() < area.getX()) ||(p.getX() > (area.getX() + area.getWidth())));

    }

    /**
     * @return
     * returns remaining brick total
     */
    public int getBrickTotal() {
        return brickTotal;
    }

    /**
     * @return
     * returns remaining chances to play
     */
    public int getBallCount() {
        return ballCount;
    }

    /**
     * @return
     * if ball is lost, return true, else false
     */
    public boolean isBallLost() {
        return ballLost;
    }

    /**
     * resets ball and paddle to their initial positions
     * sets random velocity for ball
     */
    public void ballReset() {

        player.moveTo(startPoint);
        ball.moveTo(startPoint);

        int speedX,speedY;
        do {
            speedX = rnd.nextInt(5) - 2;
        } while(speedX == 0);

        do {
            speedY = -rnd.nextInt(3);
        } while(speedY == 0);

        ball.setInitVelocity(speedX,speedY);
        ballLost = false;

    }

    /**
     * all bricks will be repaired
     * brick count and ball count will be reset
     */
    public void wallReset() {

        for(Brick b : bricks)
            b.repair();

        brickTotal = bricks.length;
        ballCount = 3;

    }

    /**
     * @return
     */
    public boolean ballEnd() {
        return ballCount == 0;
    }

    /**
     * @return
     * if no more bricks are remaining, level is done
     */
    public boolean isDone() {
        return brickTotal == 0;
    }

    /**
     * changes bricks to next level's bricks and reset brick count when level is done
     */
    public void nextLevel() {

        bricks = levels[level++];
        this.brickTotal = bricks.length;

    }

    /**
     * @return
     * if there are still more levels, return true
     */
    public boolean hasLevel() {
        return level < levels.length;
    }

    /**
     * resets ball count to default value
     */
    public void resetBallCount() {
        ballCount = 3;
    }

    /**
     * @param point
     * @param size
     * @param type
     * @return
     * gets the kind of brick
     */
    private Brick makeBrick(Point point, Dimension size, int type) {

        Brick out;
        switch(type) {
            case CLAY:
                out = new BrickOne(point,size);
                break;
            case STEEL:
                out = new BrickThree(point,size);
                break;
            case CEMENT:
                out = new BrickTwo(point, size);
                break;
            case HELLRISE:
                out = new BrickFour(point, size);
                break;
            default:
                throw  new IllegalArgumentException(String.format("Unknown Type:%d\n",type));
        }

        return out;

    }

    /**
     * gets high score from highscorelist.txt
     */
    public void getHighscore(){
        File score = new File(this.getClass().getResource("/com/amsyar/brickdestroyer/highscorelist.txt").toString());

        try{
            Scanner scoreScan = new Scanner(score);
            while (scoreScan.hasNextLine()){
                higherScore = Integer.parseInt(scoreScan.nextLine());
                highScore = Integer.parseInt(String.valueOf(higherScore));
            }
            scoreScan.close();
        }catch(Exception e){ }
    }

}

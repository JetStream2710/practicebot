package org.usfirst.frc.team2710.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2710.robot.Robot;
import org.usfirst.frc.team2710.util.PixyLine;
import org.usfirst.frc.team2710.util.PixyVision;

public class FollowLine3 extends Command {
    private double minTurnSpeed = 0.45;
    private double maxTurnSpeed = 0.3;
    private double minDriveSpeed;
    private double maxDriveSpeed;

    // Viewport constants
    public static final int MAX_Y = 51;
    public static final int MAX_X = 78;

    // The line following algorithm stops when the upper Y coordinate of the line reaches this value.
    public static final int LINE_FOLLOW_STOP_AT_Y = 41;

    // The line following algorithm attempts to target this X coordinate (when the line is centered for the robot)
    public static final int LINE_FOLLOW_TARGET_X = 71;

<<<<<<< HEAD
    public static final double IDEAL_ANGLE = 15;

    // The ideal slope of the line we want to follow
    public static final double LINE_FOLLOWING_IDEAL_SLOPE = Math.tan(Math.toRadians(IDEAL_ANGLE));
=======
    // The ideal slope of the line we want to follow
    public static final double LINE_FOLLOWING_IDEAL_SLOPE = Math.tan(Math.toRadians(15));
>>>>>>> 571befe8e6a03ec113904ab812930eced4b5a9dd

    
    public FollowLine3() {
        requires(Robot.drivetrain);
        System.out.println("Follow line constructor");
    }

    @Override
    protected void initialize() {
        System.out.println("Follow line initialize");
    }

    /**
     * @return the bottom x coordinate of the line if it were to extend to the bottom of the screen
     */
    private int[] extrapolate(PixyLine line) {
        int extraY = line.getLowerY() + 5;
        if (extraY > MAX_Y) {
            extraY = MAX_Y;
        }
        double ratio = (double)(line.getLowerX() - line.getUpperX()) / (line.getLowerY() - line.getUpperY());
        int xOffset = (int)((extraY - line.getLowerY()) * ratio);
        return new int[] {line.getLowerX() + xOffset, extraY};
=======
        int xOffset = (int)((MAX_Y - line.getLowerY()) * ratio);
        return line.getLowerX() + xOffset;
>>>>>>> 571befe8e6a03ec113904ab812930eced4b5a9dd
    }

    /**
     *  @return the distance between a line's lower X coordinate and the ideal X coordinate,
     *          a positive value indicates the line is to the right of the ideal target,
     *          a negative means the line is to the left of the target
     */
    private int getXDiff(int x, int y) {
        return getIdealX(y) - x;
<<<<<<< HEAD
=======
    }

    private int getIdealX(int y) {
        return (int)((LINE_FOLLOWING_IDEAL_SLOPE * y) + 57.33);
>>>>>>> 571befe8e6a03ec113904ab812930eced4b5a9dd
    }

    private int getIdealX(int y) {
        return (int)((LINE_FOLLOWING_IDEAL_SLOPE * y) + 57.33);
    }

    private void move(PixyLine line, int error, double speed, double turnSpeed) {
        // go slower when we see a larger error
        
        double moveSpeed = speed;
        double moveTurnSpeed = turnSpeed * getTurnSpeed(line);
        System.out.println("... error: " + error + " speed: " + moveSpeed +
        "  turn: " + turnSpeed + " x " + getTurnSpeed(line) + " = " + moveTurnSpeed);
        Robot.drivetrain.arcadeDrive(moveSpeed, moveTurnSpeed);
    }
    /**
     * Returns adjusted turn speed based upon angleError and bottomYError
     * @param line
     * @return double from 0.0-1.0
     */
    public double getTurnSpeed(PixyLine line) {
        double angleError = Math.abs(getAngleFromVertical(line) - IDEAL_ANGLE);
        double bottomYError = MAX_Y - line.getLowerY();
        double adjAngleError = (angleError/45); // 0.0 < x < 0.5 
        double adjBottomYError = 1.0 - (bottomYError / MAX_Y); // 0.0 < x < 0.5


        //angle is great, bottom is small = medium
        //angle is small, bottom is great = medium
        //angle is great, bottom is great = large
        //angle is small, bottom is small = small

        double turnSpeed = adjAngleError*adjBottomYError;
        System.out.println("adjAngleError: " + adjAngleError + " adjBottomYError: " + adjBottomYError);
        return turnSpeed + 0.5;
    }

    @Override
    public void execute(){
        PixyLine line = Robot.pixy.getLatestLine();
        // stop following if line info is too old
        if (line == null) {
            System.out.println("no line found");
            return;
        }

<<<<<<< HEAD
        /*
        if (line.getLowerY() < MAX_Y && Math.abs(line.getLowerX() - LINE_FOLLOW_TARGET_X) > 1) {
            // drive toward the extrapolated bottom point of the line
            int x = extrapolateBottomX(line);
            int xDiff = getXDiff(x, MAX_Y);
            System.out.println("drive to view, xDiff=" + xDiff + " , " + line + " slope: " + slope(line) + " extrap: " + x);
            move(line, xDiff, 0.55, xDiff > 0 ? 0.6 : -0.6);
=======
         double speed = 0.5;
         double speedDiff = 0.2 * ((15 - getAngleFromVertical(line)) / 90);

         if (line.getLowerY() < MAX_Y && Math.abs(line.getLowerX() - LINE_FOLLOW_TARGET_X) > 2) {
            // drive toward the extrapolated bottom point of the line
            int x = extrapolateBottomX(line);
            int xDiff = getXDiff(x, MAX_Y);
            System.out.println("drive to view, xDiff=" + xDiff + " lowX=" + line.getLowerX() + " lowY=" + line.getLowerY() + " highX=" + line.getUpperX() + " highY=" + line.getUpperY());
            turn(line, speed, xDiff < 0 ? (speedDiff * -1) : speedDiff);
>>>>>>> 571befe8e6a03ec113904ab812930eced4b5a9dd
        } else {
            */

            /*
            int x, y;
            if (line.getLowerY() < MAX_Y) {
                int[] temp = extrapolate(line);
                x = temp[0];
                y = temp[1];
            } else {
                x = line.getLowerX();
                y = line.getLowerY();
            }
            int xDiff = getXDiff(x, y);
            */
            int xDiff = getXDiff(line.getLowerX(), line.getLowerY());
<<<<<<< HEAD
            if (line.getLowerY() < 20) {
                move(line, xDiff, 0.55, 0);
                return;
            }
            if (Math.abs(xDiff) > 1) {
                // drive toward the bottom point of the line
                System.out.println("drive to low, xDiff=" + xDiff + " , " + line + " slope: " + slope(line));
                move(line, xDiff, 0.55, 0.45);
            } else {
                // drive toward the upper point of the line
                xDiff = getXDiff(line.getUpperX(), line.getUpperY());
                System.out.println("drive to high, xDiff=" + xDiff + " , " + line);
                move(line, xDiff, 0.55, 0.35);
=======
            if (xDiff < -2 || xDiff > 2) {
                // drive toward the bottom point of the line
                System.out.println("drive to low, xDiff=" + xDiff + " lowX=" + line.getLowerX() + " lowY=" + line.getLowerY() + " highX=" + line.getUpperX() + " highY=" + line.getUpperY());
                turn(line, speed, xDiff < 0 ? (speedDiff * -1) : speedDiff);
            } else {
                // drive toward the upper point of the line
                xDiff = getXDiff(line.getUpperX(), line.getUpperY());
                System.out.println("drive to hi, xDiff=" + xDiff + " lowX=" + line.getLowerX() + " lowY=" + line.getLowerY() + " highX=" + line.getUpperX() + " highY=" + line.getUpperY());
                turn(line, speed, xDiff < 0 ? (speedDiff * -1) : speedDiff);
>>>>>>> 571befe8e6a03ec113904ab812930eced4b5a9dd
            }
//        }
    }

    public String slope(PixyLine line) {
        return "" + (double) (line.getLowerX() - line.getUpperX()) / (line.getLowerY() - line.getUpperY()) +
        " (" + LINE_FOLLOWING_IDEAL_SLOPE + ")";
    }

    /**
     * @param line
     * @return the angle the line is at, positive if the slope is positive
     *     and negative if the slope is negative. Note pixy coordinates extend
     *     down and to the right.
     * In other words, left is positive and right is negative due to 0,0 being at top left
     */
    public double getAngleFromVertical(PixyLine line){

        int lowerX = line.getLowerX();
        int upperX = line.getUpperX();
        int lowerY = line.getLowerY();
        int upperY = line.getUpperY();

        double differX = (double)(upperX-lowerX);
        double differY = (double)(upperY-lowerY);

        double radians = Math.atan(differX/differY);
        double angle = radians*180/Math.PI;
        return angle;
    }
    
    @Override
    public boolean isFinished() {
        boolean temp = shouldStop(Robot.pixy.getLatestLine());
        if (temp) {
            System.out.println("FINISHED");
        }
        return temp;
    }

    public boolean shouldStop(PixyLine line){
        int upperY = line.getUpperY();
        return upperY > LINE_FOLLOW_STOP_AT_Y;
    }

    @Override
	protected void end() {
        Robot.drivetrain.arcadeDrive(0, 0);
	}
	
    @Override
	protected void interrupted() {
		end();
	}
}
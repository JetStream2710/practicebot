package org.usfirst.frc.team2710.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2710.robot.Robot;
import org.usfirst.frc.team2710.util.PixyLine;

public class FollowLine3 extends Command {
    private double minTurnSpeed = 0.45;
    private double maxTurnSpeed = 0.3;
    private double minDriveSpeed;
    private double maxDriveSpeed;

    public static final int MAX_Y = 78;
    public static final int MAX_X = 51;

    // The line following algorithm stops when the upper Y coordinate of the line reaches this value.
    public static final int LINE_FOLLOW_STOP_AT_Y = 41;

    // The line following algorithm attempts to target this X coordinate (when the line is centered for the robot)
    public static final int LINE_FOLLOW_TARGET_X = 71;

    // The ideal slope of the line we want to follow
    public static final double LINE_FOLLOWING_IDEAL_SLOPE = Math.tan(Math.toRadians(15));

    
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
    private int extrapolateBottomX(PixyLine line) {
        if (line.getLowerY() == MAX_Y) {
            return line.getLowerX();
        }
        double ratio = (double)(line.getLowerX() - line.getUpperX()) / (line.getLowerY() - line.getUpperY());
        int xOffset = (int)((MAX_Y - line.getLowerY()) * ratio);
        return line.getLowerX() + xOffset;
    }

    /**
     *  @return the distance between a line's lower X coordinate and the ideal X coordinate,
     *          a positive value indicates the line is to the right of the ideal target,
     *          a negative means the line is to the left of the target
     */
    private int getXDiff(int x, int y) {
        return getIdealX(y) - x;
    }

    private int getIdealX(int y) {
        return (int)((LINE_FOLLOWING_IDEAL_SLOPE * y) + 57.33);
    }

    // Negative speedDiff means turn left, positive speedDiff means turn right
    // Note that this function assumes the robot does not need to turn more than 90 degrees left or right.
    private void turn(PixyLine line, double speed, double speedDiff) {
        if (speedDiff > 0.2) {
            speedDiff = 0.2;
        }
        if (speedDiff < -0.2) {
            speedDiff = -0.2;
        }
        if (line.getLowerX() > line.getUpperX()) {
            // turn left or go straight
            if (speedDiff > 0) {
                speedDiff = 0;
            }
            Robot.drivetrain.tankDrive(speed - speedDiff, speed);
        } else {
            // turn right or go straight
            if (speedDiff < 0) {
                speedDiff = 0;
            }
            Robot.drivetrain.tankDrive(speed, speed + speedDiff);
        }
    }

    /**
     * @param line
     * @return the angle the line is at, positive if the slope is positive
     *     and negative if the slope is negative. Note pixy coordinates extend
     *     down and to the right.
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
    public void execute(){
        PixyLine line = Robot.pixy.getLatestLine();
        // stop following if line info is too old
        if (line == null) {
            System.out.println("no line found");
            return;
        }

         double speed = 0.5;
         double speedDiff = 0.2 * ((15 - getAngleFromVertical(line)) / 90);

         if (line.getLowerY() < MAX_Y && Math.abs(line.getLowerX() - LINE_FOLLOW_TARGET_X) > 2) {
            // drive toward the extrapolated bottom point of the line
            int x = extrapolateBottomX(line);
            int xDiff = getXDiff(x, MAX_Y);
            System.out.println("drive to view, xDiff=" + xDiff + " lowX=" + line.getLowerX() + " lowY=" + line.getLowerY() + " highX=" + line.getUpperX() + " highY=" + line.getUpperY());
            turn(line, speed, xDiff < 0 ? (speedDiff * -1) : speedDiff);
        } else {
            int xDiff = getXDiff(line.getLowerX(), line.getLowerY());
            if (xDiff < -2 || xDiff > 2) {
                // drive toward the bottom point of the line
                System.out.println("drive to low, xDiff=" + xDiff + " lowX=" + line.getLowerX() + " lowY=" + line.getLowerY() + " highX=" + line.getUpperX() + " highY=" + line.getUpperY());
                turn(line, speed, xDiff < 0 ? (speedDiff * -1) : speedDiff);
            } else {
                // drive toward the upper point of the line
                xDiff = getXDiff(line.getUpperX(), line.getUpperY());
                System.out.println("drive to hi, xDiff=" + xDiff + " lowX=" + line.getLowerX() + " lowY=" + line.getLowerY() + " highX=" + line.getUpperX() + " highY=" + line.getUpperY());
                turn(line, speed, xDiff < 0 ? (speedDiff * -1) : speedDiff);
            }
        }
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

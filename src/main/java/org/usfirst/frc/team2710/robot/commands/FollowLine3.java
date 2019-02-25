package org.usfirst.frc.team2710.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2710.robot.Robot;
import org.usfirst.frc.team2710.util.PixyLine;

public class FollowLine3 extends Command {
    private double minTurnSpeed = 0.45;
    private double maxTurnSpeed = 0.3;
    private double minDriveSpeed;
    private double maxDriveSpeed;

    // Viewport constants
    public static final int MAX_Y = 78;
    public static final int MAX_X = 51;

    // The line following algorithm stops when the upper Y coordinate of the line reaches this value.
    public static final int LINE_FOLLOW_STOP_AT_Y = 41;

    // The line following algorithm attempts to target this X coordinate (when the line is centered for the robot)
    public static final int LINE_FOLLOW_TARGET_X = 71;


    public static final double IDEAL_LOWER_X = 73;
    public static final double IDEAL_LOWER_Y = 51;
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

    private void move(PixyLine line, int error, double speed, double turnSpeed) {
        // go slower when we see a larger error
        double speedErr = Math.abs(1.0 / error);
        if (speedErr < 0.5) {
            speedErr = 0.5;
        }
        // turn faster when we see a larger error
        double turnErr = error / 10.0;
        if (turnErr > 1) {
            turnErr = 1;
        }
        double moveSpeed = speed * speedErr;
        double moveTurnSpeed = turnSpeed * turnErr;
        System.out.println("... error: " + error + " speed: " + speed + " x " + speedErr + " = " + moveSpeed +
        "  turn: " + turnSpeed + " x " + turnErr + " = " + moveTurnSpeed);
        Robot.drivetrain.arcadeDrive(moveSpeed, moveTurnSpeed);
    }

    @Override
    public void execute(){
        PixyLine line = Robot.pixy.getLatestLine();
        // stop following if line info is too old
        if (line == null) {
            System.out.println("no line found");
            return;
        }

        if (line.getLowerY() < MAX_Y && Math.abs(line.getLowerX() - LINE_FOLLOW_TARGET_X) > 1) {
            // drive toward the extrapolated bottom point of the line
            int x = extrapolateBottomX(line);
            int xDiff = getXDiff(x, MAX_Y);
            System.out.println("drive to view, xDiff=" + xDiff + " , " + line);
            move(line, xDiff, 0.5, 0.6);
        } else {
            int xDiff = getXDiff(line.getLowerX(), line.getLowerY());
            if (Math.abs(xDiff) > 1) {
                // drive toward the bottom point of the line
                System.out.println("drive to low, xDiff=" + xDiff + " , " + line);
                move(line, xDiff, 0.5, 0.45);
            } else {
                // drive toward the upper point of the line
                xDiff = getXDiff(line.getUpperX(), line.getUpperY());
                System.out.println("drive to high, xDiff=" + xDiff + " , " + line);
                move(line, xDiff, 0.5, 0.35);
            }
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
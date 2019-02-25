package org.usfirst.frc.team2710.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2710.robot.Robot;
import org.usfirst.frc.team2710.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2710.util.PixyLine;

/**
 * Assumes that robot's center is near the ray
 * of the line and that the line is in view
 * 
 * Attempts to turn until the top point
 * 
 * of tape intersects the ideal line
 */ 
public class FollowLineMethod2 extends Command {
    /**
     * References to "ideal" represent a theoretical line extending from
     * the center of the Robot perpendicular to the front of the chassis; 
     * 
     * This is our goal to line up the tape with
     */

    // Viewport Constants
    public static final int MAX_X = 78;
    public static final int MAX_Y = 51;

    // Ideal Constants
    public static final int IDEAL_BOTTOM_X = 71;
    public static final int IDEAL_BOTTOM_Y = 51;
    public static final double IDEAL_ANGLE = 18.43;
    public static final double IDEAL_SLOPE = (Math.tan(Math.toRadians(IDEAL_ANGLE))); // This is an x/y value, not y/x

    // TODO: Test out values for ideal turning; below values taken from TurnDegrees.java
    private final double minSpinSpeed = 0.5;
    private final double maxSpinSpeed = 0.65;
    private final double startSlowingFrom = 45; // Angle at which spinning begins to slow

    private PixyLine line; // Line acquired by Pixy
    
    public FollowLineMethod2() {
        requires(Robot.drivetrain);
    }

    /**
     * Used to compare real offset to ideal
     * @param line Line Object returned by the Pixy
     * @return X value of any given Y point on the ideal line
     */
    public int getXOnIdealLine(int y) {
        double intercept = IDEAL_BOTTOM_X - (IDEAL_SLOPE*IDEAL_BOTTOM_Y);
        return (int)( (IDEAL_SLOPE*y) + intercept);
    }

    /**
     * Used to faciliate proportional spinning
     * @param line Line Object returned by the Pixy
     * @return Value of the angle created by the line and vertical
     */
    public double getAngleFromVertical(PixyLine line) {

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
    protected void initialize() {
        System.out.println("FollowLineMethod2: Initializing");
    }

    @Override
    public void execute(){
        line = Robot.pixy.getLatestLine();
        // Returns if there is no line to avoid NullPointerException(s)
        if(line == null) {
            System.out.println("No Line");
            return;
        }

        double angle = getAngleFromVertical(line);
        double spinSpeed = Math.abs(angle/startSlowingFrom);
        if(spinSpeed > maxSpinSpeed) spinSpeed = maxSpinSpeed;
        else if(spinSpeed < minSpinSpeed) spinSpeed = minSpinSpeed;

        // +/- 1 unit for some leeway
        // actually removed buffer for testing
        if(line.getUpperX() < (getXOnIdealLine(line.getUpperY()))) { // If bottom of line is left/ideal is right
            System.out.println("Spin left");
            Robot.drivetrain.tankDrive(-spinSpeed, spinSpeed); //spin left
        } else if(line.getUpperX() > (getXOnIdealLine(line.getUpperY())) ) { // If bottom of line is right/iedal is left
            System.out.println("Spin right");
            Robot.drivetrain.tankDrive(spinSpeed, -spinSpeed); //spin right
        }

        System.out.println("UpperX: " + line.getUpperX());
        System.out.println("UpperY: " + line.getUpperY());
        System.out.println("LowerX: " + line.getLowerX());
        System.out.println("LowerY: " + line.getLowerY());
        System.out.println("Angle: " + getAngleFromVertical(line));
        System.out.println("X on Ideal: " + getXOnIdealLine(line.getUpperY()));

    }

    @Override
    public boolean isFinished() {
        if(line == null) return false;
        /**  
        * Returns true if the top point of the real line
        * intersects some point on the ideal line
        */ 
        if(line.getUpperX() == (getXOnIdealLine(line.getUpperY()) )) {
            System.out.println("Finished");
        }
        return (line.getUpperX() == (getXOnIdealLine(line.getUpperY()) ));
    }

    @Override
	protected void end() {
        Robot.drivetrain.tankDrive(0.0, 0.0);
	}
	
    @Override
	protected void interrupted() {
		end();
    }
}
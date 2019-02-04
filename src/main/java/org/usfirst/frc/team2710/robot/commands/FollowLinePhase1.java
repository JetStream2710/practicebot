package org.usfirst.frc.team2710.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2710.robot.Robot;
import org.usfirst.frc.team2710.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2710.util.PixyLine;

/**
 * Attempts to turn until the bottom point of tape is
 * lined up with the ideal line
 */
public class FollowLinePhase1 extends Command {
    /**
     * References to "ideal" represent a theoretical line extending from
     * the center of the Robot perpendicular to the front of the chassis; 
     * 
     * This is our goal to line up the tape with
     */
    private final int idealBottomX = 76;
    private final int idealBottomY = 51;
    private final double idealAngle = 12.34;
    private final double idealSlope = (Math.tan(Math.toRadians(15))); // This is an x/y value, not y/x

    // TODO: Test out values for ideal turning; below values taken from TurnDegrees.java
    private final double minSpinSpeed = 0.45;
    private final double maxSpinSpeed = 0.65;
    private final double startSlowingFrom = 45; // Angle at which spinning begins to slow

    private PixyLine line; // Line acquired by Pixy
    
    public FollowLinePhase1() {
        requires(Robot.drivetrain);
    }

    /**
     * Used to compare real offset to ideal
     * @param line Line Object returned by the Pixy
     * @return X value of any given Y point on the ideal line
     */
    public int getXOnIdealLine(int y) {
        double intercept = idealBottomX - (idealSlope*idealBottomY);
        return (int)( (idealSlope*y) + intercept);
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

        System.out.println("LowX: " + line.getLowerX());
        System.out.println("LowY: " + line.getLowerY());
        System.out.println("X on Ideal: " + getXOnIdealLine(line.getLowerY()));

        // +/- 1 unit for some leeway
        // actually removed buffer for testing
        if(line.getLowerX() < (getXOnIdealLine(line.getLowerY()))) { // If bottom of line is left/ideal is right
            System.out.println("Spin left nibba");
            Robot.drivetrain.tankDrive(-spinSpeed, spinSpeed); //spin left
        } else if(line.getLowerX() > (getXOnIdealLine(line.getLowerY())) ) { // If bottom of line is right/iedal is left
            System.out.println("Spin right daddy");
            Robot.drivetrain.tankDrive(spinSpeed, -spinSpeed); //spin right
        }

    }

    @Override
    public boolean isFinished() {
        if(line == null) return false;
        /**  
        * Returns true if the bottom point of the real line is
        * within +/- 1 pixel of some point on the ideal line
        */ 
        return (line.getLowerX() == (getXOnIdealLine(line.getLowerY()) ));
            //&& line.getLowerX() <= (getXOnIdealLine(line.getLowerY()) + 1));
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
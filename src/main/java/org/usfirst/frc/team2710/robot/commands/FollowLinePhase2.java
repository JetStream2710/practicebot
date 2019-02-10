package org.usfirst.frc.team2710.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2710.robot.Robot;
import org.usfirst.frc.team2710.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2710.util.PixyLine;

/**
 * Assumption is that bottom point of line is somewhere on ideal line
 * 
 * Attempts to drive forward and turn such that Robot is on top of and paralell to line
 * (Lines up line with ideal)
 */

public class FollowLinePhase2 extends Command {
    /**
     * References to "ideal" represent a theoretical line extending from
     * the center of the Robot perpendicular to the front of the chassis; 
     * 
     * This is our goal to line up the tape with
     */
    private final int idealBottomX = 71;
    private final int idealBottomY = 51;
    private final double idealAngle = 15;
    private final double idealSlope = (Math.tan(Math.toRadians(15))); // This is an x/y value, not y/x

    // TODO: Test out values for ideal turning; below values taken from TurnDegrees.java.
    // the below values SHOULD be tested as they may be okay for Phase1, but not Phase2
    private final double minTurnSpeed = 0.07;
    private final double maxTurnSpeed = 0.2;
    private final double minBaseSpeed = 0.47;
    private final double maxBaseSpeed = 0.7;
    private final double startSlowingFrom = 30; // Angle at which movement begins to slow

    private double angle;
    private PixyLine line; // Line acquired by Pixy

    public FollowLinePhase2() {
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

        
        angle = getAngleFromVertical(line);
        double angleDist = Math.abs(angle - idealAngle);

        // If smaller angle, tape is angled right, so turn right
        // If larger angle, tape is angled left, so turn left

        // Case0: ideal angle, ideal offset
            // Solution: drive straight
        // Case1: ideal angle, offset to left
            // Solution: dab
        // Case2: ideal angle, offset to right
            // Solution: dab
        // Case3: larger angle, offset to left
            // Solution: dab
        // Case4: larger angle, offset to right
            // Solution: dab
        // Case5: larger angle, no offset
            // Solution: Drive and turn left
        // Case6: smaller angle, offset to left
            // Solution: dab
        // Case7: smaller angle, offset to right
            // Solution: dab
        // Case8: smaller angler, no offset
            // Solution: Drive and turn right

        if(line.getLowerX() < getXOnIdealLine(line.getLowerY())
            || getAngleFromVertical(line) > idealAngle) {
            //turn left
            double rightOffset = minTurnSpeed;
            double leftOffset = -minTurnSpeed;
        } else if(line.getLowerX() > getXOnIdealLine(line.getLowerY())
            || getAngleFromVertical(line) < idealAngle) {
            //turn right
            double rightOffset = -minTurnSpeed;
            double leftOffset = minTurnSpeed;
        }

        double baseSpeed = minBaseSpeed;

        /*
        double baseSpeed = (1.0 - (angleDist / startSlowingFrom)) * maxBaseSpeed; //when dist is small, should be 1; when dist is large, should be 0
        if(baseSpeed > maxBaseSpeed) {
            baseSpeed = maxBaseSpeed;
        } else if(baseSpeed < minBaseSpeed) {
            baseSpeed = minBaseSpeed;
        }
        */

//        Robot.drivetrain.tankDrive(baseSpeed + leftOffset, baseSpeed + rightOffset);
    }

    @Override
    public boolean isFinished() {
        if(line == null) return false;

        return ((line.getLowerX() == getXOnIdealLine(line.getLowerY()))  // Bottom of line is within +/- 1 of ideal;
            && (getAngleFromVertical(line) >= idealAngle - 1) // line angle is within +/- 1 of idealAngle
            && (getAngleFromVertical(line) <= idealAngle - 1));
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

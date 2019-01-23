package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnDegrees extends Command {

	private double degrees; //degrees to turn
	private double errorDegrees; //difference between current and target angle for PID implementation
	private double intialAngle;
	private double turnSpeed; //between -1.0 and 1.0
	private double targetAngle;
	private final double kP = 0.03, //proportional constant; tune later
						 kI = 0; //integral constant; implement if necessary
	
	public static enum Units 
	{
		Degrees, Radians, Rotations;
	}
    
    public TurnDegrees(double turnAmount, double maxTurnSpeed, Units turnUnits)
    {
    	requires(Robot.drivetrain);
    	this.degrees = convertToDeg(turnAmount, turnUnits)%360; // Negative values are left; Positive values are right
    	turnSpeed = maxTurnSpeed;
    }
    
    public double convertToDeg(double turnAmount, Units turnUnits)
    {
    	switch(turnUnits)
    	{
    	case Radians:
    		return Math.toDegrees(turnAmount);
    	case Rotations:
    		return turnAmount*360.0;
    	default:
    		return turnAmount;
    	}
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	intialAngle = Robot.ahrs.getAngle();
    	targetAngle = intialAngle + degrees;
    	errorDegrees = targetAngle - intialAngle;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	
    	turnSpeed = errorDegrees * kP;
    	Robot.drivetrain.arcadeDrive(turnSpeed, turnSpeed*-1);
    	
    	errorDegrees = targetAngle - intialAngle;
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
    	return (Math.abs(errorDegrees) <= 1) /*|| turnSpeed <= ?*/;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.drivetrain.arcadeDrive(0.0, 0.0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}

package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
	 * Turns the robot a requested amount of degrees
	 * 
	 * @param degrees
	 *            Degrees to turn; positive is right, negative is left
	 * @param maxTurnSpeed
	 *            The fastest speed you want the robot to turn at; postive value
	 */
public class TurnDegrees extends Command {

	private double targetAngle;
	private double degrees;
	private int turnLeft; // 1 is true, -1 is false

	private double maxTurnSpeed;
	private double minTurnSpeed;
    
    public TurnDegrees(double degrees, double maxTurnSpeed)
    {
    	requires(Robot.drivetrain);
    	this.degrees = degrees;
    	this.maxTurnSpeed = maxTurnSpeed ;
	}

	protected void initialize() 
	{
		turnLeft = (degrees < 0.0) ? 1 : -1;
		targetAngle = Robot.ahrs.getAngle() + degrees; 
		minTurnSpeed = (Math.abs(degrees) <= 15) ? 0.6 : 0.4;
    }

	protected void execute() 
	{
		double currentAngle=Robot.ahrs.getAngle();
		double errorDegrees = targetAngle - currentAngle;

		double turnSpeed = Math.abs(errorDegrees/60.0);
		if(turnSpeed < minTurnSpeed)
		{
			turnSpeed = minTurnSpeed;
		}
		if(turnSpeed > maxTurnSpeed)
		{
			turnSpeed = maxTurnSpeed;
		}

    	Robot.drivetrain.arcadeDrive(0.0, turnSpeed*turnLeft);

    }

    protected boolean isFinished() {
		return((turnLeft == -1 && Robot.ahrs.getAngle()<=targetAngle) || (turnLeft == 1 && Robot.ahrs.getAngle()>=targetAngle));
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

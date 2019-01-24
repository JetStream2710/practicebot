package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TurnDegrees extends Command {

//	private double degrees; //degrees to turn
//	private double intialAngle;
//	private double currentAngle;
	private double targetAngle;
	private double degrees;

	// Only for debugging, remove later
	private double initialAngle;
	private long timeFinished;
	private boolean reachedEnd;

	private double maxTurnSpeed;
	private double minTurnSpeed;

	private final double kP = 0.03, //proportional constant; tune later
						 kI = 0; //integral constant; implement if necessary
    
    public TurnDegrees(double degrees, double maxTurnSpeed)
    {
    	requires(Robot.drivetrain);
    	this.degrees = degrees; // Negative values are left; Positive values are right
    	this.maxTurnSpeed = maxTurnSpeed ;
	}

    // Called just before this Command runs the first time
    protected void initialize() {
//		Robot.ahrs.zeroYaw();
		initialAngle = Robot.ahrs.getAngle();
		targetAngle = initialAngle + degrees; 
		System.out.println("initial angle: " + initialAngle);
		System.out.println("target angle: " + targetAngle);
		minTurnSpeed = (Math.abs(degrees) <= 15) ? 0.6 : 0.4;
//		intialAngle = Robot.ahrs.getAngle();
//		currentAngle= intialAngle;
//    	targetAngle = intialAngle + degrees;
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
		if (reachedEnd) {
			Robot.drivetrain.arcadeDrive(0.0, 0.0);
			return;
		}
		double currentAngle=Robot.ahrs.getAngle();
		// positive error means we want to turn right; negative is left
		double errorDegrees = targetAngle - currentAngle;
		double turnSpeed = Math.abs(errorDegrees/60.0);
		System.out.println("turnspeed: " + turnSpeed);
		//System.out.println("errorDegrees: " + errorDegrees);
		//System.out.println("currentAngle: " + currentAngle);
		//System.out.println("targetAngle: " + targetAngle);
		if(turnSpeed < minTurnSpeed)
		{
			turnSpeed = minTurnSpeed;
		}
		if(turnSpeed > maxTurnSpeed)
		{
			turnSpeed = maxTurnSpeed;
		}
		if (errorDegrees>0.0) 
		{
			turnSpeed*=-1;
		}
		System.out.println("final turn speed: " + turnSpeed);
    	Robot.drivetrain.arcadeDrive(0.0, turnSpeed);
		//System.out.println("curent:"+currentAngle);
		//System.out.println("target:"+targetAngle);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
//    	return (Math.abs(errorDegrees) <= 1) /*|| turnSpeed <= ?*/;
		if((initialAngle > targetAngle && Robot.ahrs.getAngle()<=targetAngle) || (initialAngle < targetAngle && Robot.ahrs.getAngle()>=targetAngle))
		{
			if (!reachedEnd) {
				System.out.println("REACHED END");
			}
			reachedEnd = true;
		}
		if (!reachedEnd) {
			timeFinished = System.currentTimeMillis() + 2000;
		}
		if (timeFinished < System.currentTimeMillis()) {
			Robot.drivetrain.arcadeDrive(0.0, 0.0);
			double finalAngle = Robot.ahrs.getAngle();
			System.out.println("final angle: " + finalAngle);
			System.out.println("    final diff: " + (finalAngle - initialAngle));
			return true;
		}
		return false;
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

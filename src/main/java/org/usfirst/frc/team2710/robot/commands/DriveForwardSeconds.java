package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardSeconds extends Command {

	private long startingTime;
	private int seconds;
	private long time; 

	public DriveForwardSeconds(long startingTime, int seconds) {
        this.startingTime = startingTime;
		this.seconds = seconds;
    }

    protected void initialize() {
    	requires(Robot.drivetrain);
    }

    protected void execute() {
    	Robot.drivetrain.driveForward();
    }

    protected boolean isFinished() {
    	time = System.currentTimeMillis();
        if (time - startingTime >= seconds*1000)
        	return true;
        else
        	return false;
    }

    protected void end() {
    	Robot.drivetrain.arcadeDrive(0, 0);
		System.out.println("STOP");
    }

    protected void interrupted() {
    	end();
    }
}
 
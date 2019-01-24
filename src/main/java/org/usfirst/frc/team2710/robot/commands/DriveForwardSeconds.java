package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class DriveForwardSeconds extends Command {

	private long targetTime;
	private long millis;
    private long time; 
    private double maxSpeed;

	public DriveForwardSeconds(long millis, double maxSpeed) {
        this.millis = millis/2;
        this.maxSpeed = maxSpeed;
        requires(Robot.drivetrain);
    }

    protected void initialize() {
        targetTime = System.currentTimeMillis() + millis;
    }

    protected void execute() {
    	Robot.drivetrain.arcadeDrive(maxSpeed, 0.0);
    }

    protected boolean isFinished() {
        time = System.currentTimeMillis();
        return (time >= targetTime);
    }

    protected void end() {
    	Robot.drivetrain.arcadeDrive(0, 0);
		System.out.println("STOP");
    }

    protected void interrupted() {
    	end();
    }
}
 
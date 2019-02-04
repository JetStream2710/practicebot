package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class TankDriveSeconds extends Command {

	private long targetTime;
	private long millis;
    private long time; 
    private double leftSpeed;
    private double rightSpeed;

	public TankDriveSeconds(long millis, double leftSpeed, double rightSpeed) {
        this.millis = millis;
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
        requires(Robot.drivetrain);
    }

    protected void initialize() {
        targetTime = System.currentTimeMillis() + millis;
    }

    protected void execute() {
    	Robot.drivetrain.tankDrive(rightSpeed, leftSpeed);
    }

    protected boolean isFinished() {
        time = System.currentTimeMillis();
        return (time >= targetTime);
    }

    protected void end() {
    	Robot.drivetrain.tankDrive(0.0, 0.0);
		System.out.println("STOP");
    }

    protected void interrupted() {
    	end();
    }
}
 
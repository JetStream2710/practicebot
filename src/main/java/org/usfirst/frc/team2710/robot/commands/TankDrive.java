package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class TankDrive extends Command {
    private double leftSpeed;
    private double rightSpeed;

	public TankDrive(double leftSpeed, double rightSpeed) {
        requires(Robot.drivetrain);
        this.leftSpeed = leftSpeed;
        this.rightSpeed = rightSpeed;
	}
	
	@Override
	protected void initialize() {
	}
	
	@Override
	protected void execute() {
        Robot.drivetrain.tankDrive(rightSpeed, leftSpeed);
	}
	
	@Override
	protected boolean isFinished() {
		return true;
	}
	
	@Override
	protected void end() {
	}
	
	@Override
	protected void interrupted() {
		end();
	}


}

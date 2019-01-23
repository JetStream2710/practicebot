package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveShiftUp extends Command {

	public static final boolean DEBUG = true;

	public DriveShiftUp() {
		requires(Robot.drivetrain);
		debug("constructor");
	}
	
	// Called just before this Command runs the first time
	protected void initialize() {
		debug("initialize");
		Robot.drivetrain.shiftUp();
	}
	
	// Called repeatedly when this Command is scheduled to run
	protected void execute() {
	}
	
	protected boolean isFinished() {
		return true;
	}
	
	protected void end() {
	}
	
	protected void interrupted() {
		debug("interrupted");
		end();
	}
	
	private void debug(String s) {
		if (DEBUG) {
			System.out.println("DriveShiftUp Command: " + s);
		}
	}
}

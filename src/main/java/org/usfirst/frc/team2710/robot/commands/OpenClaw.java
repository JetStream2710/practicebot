package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OpenClaw extends Command {

	public OpenClaw() {
		requires(Robot.claw);
	}
	
	@Override
	protected void initialize() {
		Robot.claw.openClaw();
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
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

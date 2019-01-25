package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class OpenClaw extends Command {

	public OpenClaw() {
		requires(Robot.claw);
		System.out.println("open claw constructor");
	}
	
	@Override
	protected void initialize() {
		Robot.claw.openClaw();
		System.out.println("open claw init");
	}
	
	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
	}
	
	@Override
	protected boolean isFinished() {
		System.out.println("open claw finished");
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

package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PushInClaw extends Command {

    public PushInClaw() {
    	requires(Robot.claw);
    }

    protected void initialize() {
    	Robot.claw.PushInClaw();
    	System.out.println("Push In Claw");
    }

    protected void execute() {
    }

    protected boolean isFinished() {
        return true;
    }

    protected void end() {
    }

    protected void interrupted() {
    	end();
    }
}

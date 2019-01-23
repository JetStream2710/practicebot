package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class PushOutClaw extends Command {

    public PushOutClaw() {
    	requires(Robot.claw);
    }

    protected void initialize() {
    	Robot.claw.PushOutClaw();
    	System.out.println("Push Out Claw");
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

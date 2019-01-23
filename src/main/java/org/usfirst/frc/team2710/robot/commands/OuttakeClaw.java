package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class OuttakeClaw extends Command {

    public OuttakeClaw() {
        requires(Robot.claw);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.claw.OuttakeClaw();
    	System.out.println("Outtake Claw");
    }

    protected boolean isFinished() {
        return false;
    }

    protected void end() {
    	Robot.claw.StopClaw();
    }

    protected void interrupted() {
    	end();
    }
}

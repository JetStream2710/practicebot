package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class IntakeClaw extends Command {

    public IntakeClaw() {
        requires(Robot.claw);
    }

    protected void initialize() {
    }

    protected void execute() {
    	Robot.claw.IntakeClaw();
    	System.out.println("Intake Claw");
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

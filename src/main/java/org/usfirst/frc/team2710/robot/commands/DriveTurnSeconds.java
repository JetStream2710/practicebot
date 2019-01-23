package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.Command;

public class DriveTurnSeconds extends Command {

	long duration;
	long targetTime;
	long error;
	double speed;
	
    public DriveTurnSeconds(long durationSeconds, double speed) {
        this.duration = durationSeconds;
        this.speed = speed;
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	targetTime = System.currentTimeMillis() + 1000*duration;
        System.out.println("DriveTurnSeconds init speed: " + speed + " endTime: " + targetTime);
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	System.out.println("DriveTurnSeconds speed: " + speed + " endTime: " + targetTime);
    	Robot.drivetrain.arcadeDrive(0, speed);
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return System.currentTimeMillis() >= targetTime;
    }

    // Called once after isFinished returns true
    protected void end() {
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    }
}

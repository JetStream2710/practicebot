package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class Autonomous extends CommandGroup {
	
	public Autonomous() {
		System.out.println("Autonomous Command Group Called");
//		addSequential(new DriveForwardSeconds(2, 0.4));
		addSequential(new WaitCommand(1));
		addSequential(new DriveTurnSeconds(2, 0.5));
		addSequential(new WaitCommand(1));
//		addSequential(new DriveForwardSeconds(2, -0.4));
		addSequential(new WaitCommand(1));
		addSequential(new DriveTurnSeconds(2, -0.5));
    }
}

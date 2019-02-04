package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;

import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 *
 */
public class FollowLineGroup extends CommandGroup {
	
	public FollowLineGroup() {
        addSequential(new FollowLinePhase1());
        addSequential(new FollowLinePhase2());
    }
}

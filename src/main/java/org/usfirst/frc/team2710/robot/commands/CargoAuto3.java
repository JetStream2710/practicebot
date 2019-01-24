/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2710.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoAuto3 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CargoAuto3() {
    addSequential(new DriveForwardSeconds(8600,0.5));
    addSequential(new TurnDegrees(-65,0.6));
    addSequential(new DriveForwardSeconds(5400,0.5));
    addSequential(new TurnDegrees(65,0.6));
    addSequential(new DriveForwardSeconds(8600,0.5));
    addSequential(new TurnDegrees(90,0.6));
    addSequential(new DriveForwardSeconds(3000,0.5));
    // Acquire Line
    // Install Hatch
    // Win?
  }
}

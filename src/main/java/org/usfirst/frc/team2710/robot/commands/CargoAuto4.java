/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team2710.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;

public class CargoAuto4 extends CommandGroup {
  /**
   * Add your docs here.
   */
  public CargoAuto4() {
    addSequential(new DriveForwardSeconds(13300,0.5));
    // Acquire Line
    // Install Hatch
    // Win?
  }
}

package org.usfirst.frc.team2710.robot.subsystems;

import org.usfirst.frc.team2710.robot.RobotMap;
import org.usfirst.frc.team2710.robot.commands.DriveCommand;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.AnalogAccelerometer;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;

/**
 * Test drivetrain for practice robot
 */
public class Nav extends Subsystem {
	
	public static final boolean DEBUG = true;


	private AnalogAccelerometer sensor;
	
	// TODO: enable shifting gear via solenoid
//	private boolean lowGear = true;
//	private Solenoid driveSolenoid = null;
	
	// TODO: figure out how to use this
//	private Encoder driveEncoder = null;
	
	public Nav() {
		super();
		debug("constructor");
	}
	
	public void shiftUp() {
		debug("shiftUp");
		// TODO: VERIFY SHIFTING UP IS TRUE/FALSE
//		driveSolenoid.set(true);
	}
	
	public void shiftDown() {
		debug("shiftDown");
		// TODO: VERIFY SHIFTING UP IS TRUE/FALSE
//		driveSolenoid.set(false);
	}

	public void initDefaultCommand() {
		debug("initDefaultCommand to DriveArcade");
		// Set the default command for a subsystem here.
		setDefaultCommand(new DriveCommand());
	}
	
	private void debug(String s) {
		if (DEBUG) {
			System.out.println("Drivetrain Subsystem: " + s);
		}
	}
}
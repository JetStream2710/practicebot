package org.usfirst.frc.team2710.robot.subsystems;

import org.usfirst.frc.team2710.robot.RobotMap;
import org.usfirst.frc.team2710.robot.commands.DriveCommand;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
//import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * Test drivetrain for practice robot
 */
public class Drivetrain extends Subsystem {
	
	public static final boolean DEBUG = false ;

	private WPI_TalonSRX frontLeftTalon = null;		
	private WPI_TalonSRX rearLeftTalon = null;
	
	private WPI_TalonSRX frontRightTalon = null;
	private WPI_TalonSRX rearRightTalon = null;
	
//	private AHRS ahrs;
	
	// TODO: enable shifting gear via solenoid
//	private boolean lowGear = true;
//	private Solenoid driveSolenoid = null;
	
	// TODO: figure out how to use this
//	private Encoder driveEncoder = null;
	
	private DifferentialDrive differentialDrive = null;
	
	public Drivetrain() {
		super();
		frontLeftTalon = new WPI_TalonSRX(RobotMap.DRIVETRAIN_FRONT_LEFT_TALON);
		rearLeftTalon = new WPI_TalonSRX(RobotMap.DRIVETRAIN_REAR_LEFT_TALON);
		
		frontRightTalon = new WPI_TalonSRX(RobotMap.DRIVETRAIN_FRONT_RIGHT_TALON);
		rearRightTalon = new WPI_TalonSRX(RobotMap.DRIVETRAIN_REAR_RIGHT_TALON);
		
		frontLeftTalon.setNeutralMode(NeutralMode.Brake);
		rearLeftTalon.setNeutralMode(NeutralMode.Brake);
		frontRightTalon.setNeutralMode(NeutralMode.Brake);
		rearRightTalon.setNeutralMode(NeutralMode.Brake);
		
		frontLeftTalon.setSafetyEnabled(false);
		frontRightTalon.setSafetyEnabled(false);
		rearLeftTalon.setSafetyEnabled(false);
		rearRightTalon.setSafetyEnabled(false);
		
		SpeedControllerGroup leftGroup = new SpeedControllerGroup(frontLeftTalon, rearLeftTalon);
		SpeedControllerGroup rightGroup = new SpeedControllerGroup(frontRightTalon, rearRightTalon);
		
//		ahrs = new AHRS(SPI.Port.kMXP);

		/*
		// TODO: figure out if this is useful or else delete it
		leftTalonSlave.follow(leftTalon);
		rightTalonSlave.follow(rightTalon);
		
		leftTalonSlave.setNeutralMode(NeutralMode.Brake);
		rightTalonSlave.setNeutralMode(NeutralMode.Brake);
		
		leftTalonSlave.setSafetyEnabled(false);
		rightTalonSlave.setSafetyEnabled(false);
		*/
		
//		driveSolenoid = new Solenoid(RobotMap.DRIVETRAIN_SOLENOID);
		
//		driveEncoder = new Encoder(RobotMap.DRIVETRAIN_ENCODER_A, RobotMap.DRIVETRAIN_ENCODER_B);
		
		differentialDrive = new DifferentialDrive(leftGroup, rightGroup);
//		differentialDrive.setSafetyEnabled(false);
		debug("constructor");
	}
	
	public void tankDrive(double leftSpeed, double rightSpeed) {
		debug("tank drive leftSpeed: " + leftSpeed + " rightSpeed: " + rightSpeed);
		differentialDrive.tankDrive(rightSpeed, leftSpeed);
	}

	public void arcadeDrive(double moveSpeed, double rotateSpeed) {
		//System.out.println("arcade drive: " + moveSpeed + " , " + rotateSpeed
		//		             + " @ " + System.currentTimeMillis());
		debug("arcade drive movespeed: " + moveSpeed + " rotatespeed: " + rotateSpeed);
		differentialDrive.arcadeDrive(moveSpeed, rotateSpeed);
	}
	
	public void curvatureDrive(double moveSpeed, double rotateSpeed) {
		debug("curvature drive movespeed: " + moveSpeed + " rotatespeed: " + rotateSpeed);
		differentialDrive.curvatureDrive(moveSpeed, rotateSpeed, false);
	}
	
	public void shiftUp() {
		debug("shiftUp");
		// TODO: VERIFY SHIFTING UP IS TRUE/FALSE
//		driveSolenoid.set(true);
	}
	
	public void shiftDown() {
		debug("shiftDown");
		// TO DO: VERIFY SHIFTING UP IS TRUE/FALSE
//		driveSolenoid.set(false);
	}
	
	
	public void driveForward(/*long startingTime, int seconds*/) {
		differentialDrive.arcadeDrive(0.4, 0);
		
		/*
		long time = System.currentTimeMillis();;
		long endTime = time + (seconds * 1000);
		System.out.println("drive forward at: " + time + " end at: " + endTime);
		while (System.currentTimeMillis() < endTime) {
			differentialDrive.arcadeDrive(.40, 0);
		}
		differentialDrive.arcadeDrive(0,  0);
		System.out.println("Stop!!!");
		*/
	}
	
	public void turn(double speed) {
		
	/*	int currentAngle = (int) ahrs.getAngle();
		float currentYaw = ahrs.getYaw();
		
		double error = currentAngle%360 - degrees;
		
		if (Math.abs(error) < 20)
			differentialDrive.arcadeDrive(0, 0);
		else
			differentialDrive.arcadeDrive(0.0, -0.5);
		
		differentialDrive.arcadeDrive(0,  0.4);
		
		System.out.println("currentAngle =  " + currentAngle + ","
				+ " yaw = " + currentYaw);
		
	*/	//double currentAngle = ahrs.getRate();
		//double angle1 = currentAngle - degrees;
		//double angle2 = currentAngle + degrees;
		//while ((angle1 < currentAngle && currentAngle < angle2) ||
		//		(angle2 < currentAngle && currentAngle < angle1)) {
		//	differentialDrive.arcadeDrive(0, .25);
		//}
		//differentialDrive.arcadeDrive(0,  0);
		
	}

	@Override
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

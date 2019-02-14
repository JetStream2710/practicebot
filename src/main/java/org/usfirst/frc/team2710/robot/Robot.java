package org.usfirst.frc.team2710.robot;

import org.usfirst.frc.team2710.robot.subsystems.Claw;
import org.usfirst.frc.team2710.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2710.util.PixyI2CDriver;
import org.usfirst.frc.team2710.util.PixyI2CDriver2;
import org.usfirst.frc.team2710.util.PixyLine;
import org.usfirst.frc.team2710.util.PixyVision;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team2710.robot.commands.*;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends TimedRobot {
	Command auto;
	public static Drivetrain drivetrain;
	public static Claw claw;
	public static OI oi;
	public static AHRS ahrs = new AHRS(SPI.Port.kMXP);
	public static PixyVision pixy = new PixyVision(true, true);

	public static long startingTime;
	public static boolean isAuto;
	
	public static DigitalInput limitSwitch;
	
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	private FollowLinePhase1 linePhase1;

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		SmartDashboard.putString("Event Log: ", "Robot Init");
		drivetrain = new Drivetrain();
//		claw = new Claw();
		oi = new OI();
		limitSwitch = new DigitalInput(9);
		pixy.start();
		
		
		//chooser.addObject("My Auto", new MyAutoCommand());
//		Telemetry.addEvent("Robot Init");


//		CameraServer.getInstance().startAutomaticCapture();

		//drivetrain = new Drivetrain();
		
//		auto = new RightRocketAuto();
	}
	
	public void robotPeriodic() {
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
//		Telemetry.addEvent("Autonomous Init");
//		SmartDashboard.putString("Event Log: ", "Autonomous Init");
//		startingTime = System.currentTimeMillis();
		//auto.start();
		isAuto = true;	

		//followLineCmd = new FollowLine();

		//linePhase1 = new FollowLinePhase1();
		//linePhase1.start();
//		FollowLine3 fl3 = new FollowLine3();
//		fl3.start();

		//tankDriveCmd = new TankDriveSeconds(1000, 0.6,0.4);
		//tankDriveCmd.start();
		
		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		/*
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
		*/
		
		FollowLine3 fl = new FollowLine3();
		fl.start();

		//		drivetrain.turn(ahrs, 180);
//		drivetrain.driveForward(startingTime, 1);

	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		/*
		PixyLine line = Robot.pixy.getLatestLine();
		if(line == null) {
			System.out.println("No line");
			return;
		}
		int lowerX = line.getLowerX();
        int upperX = line.getUpperX();
        int lowerY = line.getLowerY();
        int upperY = line.getUpperY();

        double differX = (double)(upperX-lowerX);
        double differY = (double)(upperY-lowerY);

        double radians = Math.atan(differX/differY);
        double angle = radians*180/Math.PI;

		System.out.println("PixyLine: \n" + "LowX" + lowerX + "LowY" + lowerY + "Angle" + angle);
		*/
		Scheduler.getInstance().run();
		//System.out.println("hello");
		
		//drivetrain.driveForward(1);
		//System.out.println("auto run");
	}

	@Override
	public void teleopInit() {
		Telemetry.addEvent("Teleop Init");
		SmartDashboard.putString("Event Log: ", "Teleop Init");
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		isAuto = false;
		ahrs.zeroYaw();

//		pixy1.turnOnLamp();

		testMotor();
	}

	public static boolean isTested = false;
	public void testMotor() {
		if (isTested) {
			return;
		}
		isTested = true;

		drivetrain.frontLeftTalon.set(0.2);
		try { Thread.currentThread().sleep(500); } catch (InterruptedException e) { }
		System.out.println("FRONT LEFT TALON, expect 0.2 actual " + drivetrain.frontLeftTalon.get());
		drivetrain.frontLeftTalon.set(-0.2);
		try { Thread.currentThread().sleep(500); } catch (InterruptedException e) { }
		System.out.println("FRONT LEFT TALON, expect 0.2 actual " + drivetrain.frontLeftTalon.get());
		drivetrain.frontLeftTalon.setInverted(true);
		drivetrain.frontLeftTalon.set(0.2);
		try { Thread.currentThread().sleep(500); } catch (InterruptedException e) { }
		System.out.println("FRONT LEFT TALON inverted, expect 0.2 actual " + drivetrain.frontLeftTalon.get());
		drivetrain.frontLeftTalon.set(-0.2);
		try { Thread.currentThread().sleep(500); } catch (InterruptedException e) { }
		System.out.println("FRONT LEFT TALON inverted, expect 0.2 actual " + drivetrain.frontLeftTalon.get());
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();

		//System.out.println(pixy.getLatestLine());

/*		System.out.println("Angle: " + ahrs.getAngle() + " Yaw: " + ahrs.getYaw() +
				" Pitch: " + ahrs.getPitch() + " Roll: " + ahrs.getRoll());
*/	//	System.out.println("x: " + ahrs.getRawGyroX() + " y: " + ahrs.getRawGyroY() + " z: " + ahrs.getRawGyroZ());
		//trying new logitech controller
		
	//	double forward = Robot.oi.dJoy.getRawAxis(1);
		//double turn = Robot.oi.dJoy.getRawAxis(0);
		//System.out.println("teleop forward" + forward + "  turn:" + turn);
		//Robot.drivetrain.arcadeDrive(forward, turn);
 		
		/*
		double moveSpeed = Robot.oi.joystick1.getRawAxis(RobotMap.DRIVER_MOVE_AXIS);
		double rotateSpeed = Robot.oi.joystick1.getRawAxis(RobotMap.DRIVER_ROTATE_AXIS);
	//	System.out.println("teleop hack movespeed: " + moveSpeed + "  rotateSpeed: " + rotateSpeed);
		Robot.drivetrain.arcadeDrive(moveSpeed, rotateSpeed);
		
		Robot.oi.D3.whileHeld(new IntakeClaw());
		Robot.oi.D4.whileHeld(new OuttakeClaw());
		*/
		
//		System.out.println("LimitSwitch1 " + LimitSwitch1.get());
		
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}

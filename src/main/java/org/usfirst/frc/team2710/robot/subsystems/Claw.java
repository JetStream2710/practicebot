package org.usfirst.frc.team2710.robot.subsystems;

import org.usfirst.frc.team2710.robot.RobotMap;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;

public class Claw extends Subsystem {

	private Solenoid solenoidOn;
	private Solenoid solenoidOff;	
	private WPI_VictorSPX victorLeft = null;
	private WPI_VictorSPX victorRight = null;
	private Solenoid solenoidLLOn;
	private Solenoid solenoidLROn;
	private Solenoid solenoidRLOn;
	private Solenoid solenoidRROn;

	private Solenoid solenoidRROff;
	
	public Claw() {
		super();
		/*solenoidOn = new Solenoid(RobotMap.CLAW_SOLENOID_NODE, RobotMap.CLAW_SOLENOID_ON);
		solenoidOff = new Solenoid(RobotMap.CLAW_SOLENOID_NODE, RobotMap.CLAW_SOLENOID_OFF);

		solenoidLLOn = new Solenoid(RobotMap.CLAW_SOLENOID_NODE, RobotMap.PUSH_LL_SOLENOID_ON);
		solenoidLROn = new Solenoid(RobotMap.CLAW_SOLENOID_NODE, RobotMap.PUSH_LR_SOLENOID_ON);
		solenoidRLOn = new Solenoid(RobotMap.CLAW_SOLENOID_NODE, RobotMap.PUSH_RL_SOLENOID_ON);
		solenoidRROn = new Solenoid(RobotMap.CLAW_SOLENOID_NODE, RobotMap.PUSH_RR_SOLENOID_ON);
		
		solenoidRROff = new Solenoid(RobotMap.CLAW_SOLENOID_NODE, RobotMap.PUSH_RR_SOLENOID_OFF);
		*/
		victorLeft = new WPI_VictorSPX(RobotMap.CLAW_LEFT_VICTOR);
		victorRight = new WPI_VictorSPX(RobotMap.CLAW_RIGHT_VICTOR);
		victorLeft.setNeutralMode(NeutralMode.Brake);
		victorRight.setNeutralMode(NeutralMode.Brake);
		victorLeft.setSafetyEnabled(false);
		victorRight.setSafetyEnabled(false);
	}
	
	public void openClaw() {
		solenoidOff.set(false);
		solenoidOn.set(true);
		System.out.print("OpenClaw");
	}
	
	public void closeClaw() {
		solenoidOn.set(false);
		solenoidOff.set(true);
		System.out.print("CloseClaw");
	}
	
	public void IntakeClaw() {
		victorLeft.set(ControlMode.PercentOutput,.5);
		victorRight.set(ControlMode.PercentOutput,0.5);
	}
	
	public void OuttakeClaw() {
		victorLeft.set(ControlMode.PercentOutput,-0.25);
		victorRight.set(ControlMode.PercentOutput,-0.25);	
	}
	
	public void StopClaw() {
		victorLeft.set(ControlMode.PercentOutput, 0);
		victorRight.set(ControlMode.PercentOutput, 0);
	}
	
	public void PushOutClaw() {
		solenoidLLOn.set(true);
		solenoidLROn.set(true);
		solenoidRLOn.set(true);
		solenoidRROn.set(true);
		
		solenoidRROff.set(false);
	}
	
	public void PushInClaw() {
		solenoidLLOn.set(false);
		solenoidLROn.set(false);
		solenoidRLOn.set(false);
		solenoidRROn.set(false);

		solenoidRROff.set(true);
	}
	
	@Override
	public void initDefaultCommand() {
	}
}



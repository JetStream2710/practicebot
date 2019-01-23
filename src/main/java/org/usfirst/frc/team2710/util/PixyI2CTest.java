package org.usfirst.frc.team2710.util;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PixyI2CTest {

	public PixyI2CDriver pixy;
	Port port = Port.kOnboard;
	public PixyPacket[] packet1 = new PixyPacket[7];
	public PixyPacket[] packet2 = new PixyPacket[7];

	public PixyI2CTest() {
	}

	public void testpixy() {
		//pixy.turnOnLamp()
		//pixy.turnOffLamp();
		pixy.lineTracking();
	}
}

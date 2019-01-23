package org.usfirst.frc.team2710.util;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class PixyI2CDriver {
	I2C pixy;
	Port port = Port.kOnboard;

	public PixyI2CDriver() {
		pixy = new I2C(port, 0x54);
	}

	private byte[] TURN_ON_LAMP = {
		(byte) 174,
		(byte) 193,
		(byte) 22,
		(byte) 2,
		(byte) 1,
		(byte) 0
	};

	private byte[] TURN_OFF_LAMP = {
		(byte) 174,
		(byte) 193,
		(byte) 22,
		(byte) 2,
		(byte) 1,
		(byte) 1
	};

	private byte[] LINE_TRACKING = {
		(byte) 174,
		(byte) 193,
		(byte) 48,
		(byte) 2,
		(byte) 0,
		(byte) 7
	};

	public int convertUnsigned(byte b) {
		return b & 0xFF;		
	}

	public void turnOnLamp() {
		System.out.println("calling turnOnLamp");
		try {
			for (byte b : TURN_ON_LAMP) {
				System.out.println((int) b + " :: " + convertUnsigned(b));
			}
			byte[] dataReceived = new byte[9];
			pixy.transaction(TURN_ON_LAMP, TURN_ON_LAMP.length, dataReceived, dataReceived.length);
			System.out.println("turnOnLamp data received");
			for (byte b : dataReceived) {
				System.out.println((int) b + " :: " + convertUnsigned(b));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void turnOffLamp() {
		System.out.println("calling turnOffLamp");
		try {
			for (byte b : TURN_OFF_LAMP) {
				System.out.println((int) b + " :: " + convertUnsigned(b));
			}
			byte[] dataReceived = new byte[9];
			pixy.transaction(TURN_OFF_LAMP, TURN_OFF_LAMP.length, dataReceived, dataReceived.length);
			System.out.println("turnOffLamp data received");
			for (byte b : dataReceived) {
				System.out.println((int) b + " :: " + convertUnsigned(b));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void lineTracking() {
		System.out.println("calling lineTracking");
		try {
			for (byte b : LINE_TRACKING) {
				System.out.println((int) b + " :: " + convertUnsigned(b));
			}
			byte[] dataReceived = new byte[15];
			pixy.transaction(LINE_TRACKING, LINE_TRACKING.length, dataReceived, dataReceived.length);
			System.out.println("lineTracking data received");
			int i=0;
			for (byte b : dataReceived) {
				System.out.println(i+": "+(int) b + " :: " + convertUnsigned(b));
				i++;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}	
}

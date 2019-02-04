package org.usfirst.frc.team2710.util;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.SPI.Port;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PixySpiDriver {

    public static final boolean DEBUG = true;
    
	private static final byte[] TURN_ON_LAMP = {
		(byte) 174, (byte) 193, (byte) 22, (byte) 2, (byte) 1, (byte) 0
	};

	private static final byte[] TURN_OFF_LAMP = {
		(byte) 174, (byte) 193, (byte) 22, (byte) 2, (byte) 1, (byte) 1
	};

	private static final byte[] LINE_TRACKING = {
        (byte) 174, (byte) 193, (byte) 48, (byte) 2, (byte) 0, (byte) 7
    };
    
    private static final byte[] OBJECT_TRACKING = {
        (byte) 174, (byte) 193, (byte) 32, (byte) 2, (byte) 1, (byte) 2
    };

   	private SPI pixy;

    public PixySpiDriver(Port port) {
		pixy = new SPI(port);
		pixy.setMSBFirst();
		pixy.setChipSelectActiveLow();
		pixy.setClockRate(1000);
		pixy.setSampleDataOnTrailingEdge();
		pixy.setClockActiveLow();
	}

	public void turnOnLamp() {
        debug("turnOnLamp send: " + bytesToString(TURN_ON_LAMP));
        byte[] data = new byte[9];
        pixy.transaction(TURN_ON_LAMP, data, 2);
        debug("... turnOnLamp receive: " + bytesToString(data));
	}

	public void turnOffLamp() {
        debug("turnOffLamp send: " + bytesToString(TURN_OFF_LAMP));
        byte[] data = new byte[9];
        pixy.transaction(TURN_OFF_LAMP, data, 2);
        debug("... turnOffLamp receive: " + bytesToString(data));
	}

	public PixyLine lineTracking() {
        debug("lineTracking send: " + bytesToString(LINE_TRACKING));
		byte[] data = new byte[14];
        pixy.transaction(LINE_TRACKING, data, 2);
        debug("... lineTracking receive: " + bytesToString(data));
		return new PixyLine(data);
    }

    public PixyBlock[] objectTracking() {
        debug("objectTracking send: " + bytesToString(OBJECT_TRACKING));
        byte[] data = new byte[17];
        //byte[] data = new byte[34];
        pixy.transaction(OBJECT_TRACKING, data, 2);
        debug("... objectTracking receive: " + bytesToString(data));
        byte[] data2 = new byte[34];
        pixy.read(false, data2, 2);
        debug("... objectTracking receive2: " + bytesToString(data2));
        return new PixyBlock[] {
            new PixyBlock(data),
            new PixyBlock(data2)
        };
    }

    private void debug(String msg) {
        if (DEBUG) {
            System.out.println("PixySpiDriver: " + msg);
            SmartDashboard.putString("PixySpiDriver: ", msg);
        }
    }

    public static String bytesToString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append((int) (b & 0xFF));
            sb.append(' ');
        }
        return sb.toString();
    }
}

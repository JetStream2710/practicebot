package org.usfirst.frc.team2710.util;

public class PixyPacket {
	public int X;
	public int Y;
	public int Width;
	public int Height;
	public int checksumError;

	public String toString() {
		return "X: " + X + " Y: " + Y + " Width: " + Width + " Height: " + Height + " ChecksumError: " + checksumError;
	}
}
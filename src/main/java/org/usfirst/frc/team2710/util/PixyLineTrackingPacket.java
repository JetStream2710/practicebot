package org.usfirst.frc.team2710.util;

public class PixyLineTrackingPacket {

    private byte[] bytes;

    public PixyLineTrackingPacket(byte[] bytes){
        this.bytes = bytes;
    }

    public int getLowerX(){
        return (int) bytes[0];
    }

    public int getLowerY(){
        return (int) bytes[1];
    }

    public int getUpperX(){
        return (int) bytes[2];
    }

    public int getUpperY(){
        return (int) bytes[3];
    }

    public int getId(){
        return (int) bytes[4];
    }

}

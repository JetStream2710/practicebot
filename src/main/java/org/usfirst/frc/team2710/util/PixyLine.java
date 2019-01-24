package org.usfirst.frc.team2710.util;

public class PixyLine {

    private byte[] bytes;

    public PixyLine(byte[] bytes){
        this.bytes = bytes;
    }

    public int getLowerX(){
        return (int) bytes[8];
    }

    public int getLowerY(){
        return (int) bytes[9];
    }

    public int getUpperX(){
        return (int) bytes[10];
    }

    public int getUpperY(){
        return (int) bytes[11];
    }

    public int getId(){
        return (int) bytes[12];
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("lower coordinates: (");
        sb.append(getLowerX());
        sb.append(",");
        sb.append(getLowerY());
        sb.append(") upper coordinates: (");
        sb.append(getUpperX());
        sb.append(",");
        sb.append(getUpperY());
        sb.append(") id: ");
        sb.append(getId());
        return sb.toString();
    }

}

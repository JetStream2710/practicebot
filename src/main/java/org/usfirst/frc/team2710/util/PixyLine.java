package org.usfirst.frc.team2710.util;

public class PixyLine {

    private byte[] bytes;

    public PixyLine(byte[] bytes){
        this.bytes = bytes;
    }

    public int getLowerX(){
        if(shouldFlip()){
            return (int) bytes[10];
        }
        return (int) bytes[8];
    }

    public int getLowerY(){
        if(shouldFlip()){
            return (int) bytes[11];
        }
        return (int) bytes[9];
    }

    public int getUpperX(){
        if(shouldFlip()){
            return (int) bytes[8];
        }
        return (int) bytes[10];
    }

    public int getUpperY(){
        if(shouldFlip()){
            return (int) bytes[9];
        }
        return (int) bytes[11];
    }

    public int getId(){
        return (int) bytes[12];
    }

    public boolean shouldFlip(){
        return (int) bytes[9] < (int) bytes[11];
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

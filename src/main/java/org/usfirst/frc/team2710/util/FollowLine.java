package org.usfirst.frc.team2710.util;

import org.usfirst.frc.team2710.robot.subsystems.Drivetrain;

public class FollowLine {
    private final int MIDDLE_X = 40;
    
    public int getOffsetFromMiddle(PixyLine line){
        int lowerX = line.getLowerX();
        int upperX = line.getUpperX();
        return MIDDLE_X - (lowerX + upperX)/2;
    }

    public double getAngleFromVertical(PixyLine line){
        int lowerX = line.getLowerX();
        int upperX = line.getUpperX();
        int lowerY = line.getLowerY();
        int upperY = line.getUpperY();

        double differX = (double)(upperX-lowerX);
        double differY = (double)(upperY-lowerY);

        double radians = Math.atan(differX/differY);
        double angle = radians*180./Math.PI;
        return angle;
    }

    public void execute(PixyLine line, Drivetrain drivetrain){
        int offsetFromMiddle = getOffsetFromMiddle(line);
        double angleFromVertical = getAngleFromVertical(line);
        System.out.println("offset: " + offsetFromMiddle + " angle: " + angleFromVertical);

        //angle
        if(angleFromVertical>10)
            drivetrain.arcadeDrive(.4, -.5);
        
        else if(angleFromVertical<-10)
            drivetrain.arcadeDrive(.4,.5);
        else 
            drivetrain.arcadeDrive(0, 0);
    }
}

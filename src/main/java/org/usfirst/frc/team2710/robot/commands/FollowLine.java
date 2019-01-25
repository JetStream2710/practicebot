package org.usfirst.frc.team2710.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team2710.robot.Robot;
import org.usfirst.frc.team2710.robot.subsystems.Drivetrain;
import org.usfirst.frc.team2710.util.PixyLine;

public class FollowLine extends Command {
    private final int MIDDLE_X = 40;
    
    public FollowLine() {
//        requires(Robot.drivetrain);
        System.out.println("Follow line constructor");
    }

    @Override
    protected void initialize() {
        System.out.println("Follow line initialize");
    }

    public int getOffsetFromMiddle(PixyLine line){
        int lowerX = line.getLowerX();
        return MIDDLE_X - lowerX;
    }

    /**
     * @param line
     * @return the angle the line is at, positive if the slope is positive
     *     and negative if the slope is negative. Note pixy coordinates extend
     *     down and to the right.
     */
    public double getAngleFromVertical(PixyLine line){
        int lowerX = line.getLowerX();
        int upperX = line.getUpperX();
        int lowerY = line.getLowerY();
        int upperY = line.getUpperY();

        double differX = (double)(upperX-lowerX);
        double differY = (double)(upperY-lowerY);

        double radians = Math.atan(differX/differY);
        double angle = radians*180/Math.PI;
        return angle;
    }

    @Override
    public void execute(){
        PixyLine line = Robot.pixy.getLatestLine();
        int offsetFromMiddle = getOffsetFromMiddle(line);
        double angleFromVertical = getAngleFromVertical(line);
        System.out.println("offset: " + offsetFromMiddle + " angle: " + angleFromVertical +
         " lowX=" + line.getLowerX() + " lowY=" + line.getLowerY() +
         " highX=" + line.getUpperX() + " highY=" + line.getUpperY());

        //offset
        double turnMagnitude = 0.5;
        double speedValue = 0.5;
        double turnValue = turnMagnitude;
        if(offsetFromMiddle<0){
            turnValue = -1 * turnMagnitude;
        }

        //angle
        if(Math.abs(offsetFromMiddle) < 5){
            if(Math.abs(angleFromVertical) < 5){
                turnValue = 0;
                System.out.println("Move towards angle: angle=" + angleFromVertical + " turn=" + turnValue);
            } else{
                if(angleFromVertical < 0){
                    turnValue = turnMagnitude;
                    System.out.println("Move towards positive angle: angle=" + angleFromVertical + " turn=" + turnValue);
                } else{
                    turnValue = -1 * turnMagnitude;
                    System.out.println("Move towards negative angle: angle=" + angleFromVertical + " turn=" + turnValue);
                }
            }
        } else {
            System.out.println("Move towards offset: offset=" + offsetFromMiddle + " turn=" + turnValue);
        }
        Robot.drivetrain.arcadeDrive(speedValue, turnValue);
//        Robot.drivetrain.arcadeDrive(0, 0);
    }

    @Override
    public boolean isFinished() {
        boolean temp = shouldStop(Robot.pixy.getLatestLine());
        if (temp) {
            System.out.println("FINISHED");
        }
        return temp;
    }

    public boolean shouldStop(PixyLine line){
        int upperY = line.getUpperY();
        return upperY > 26;
    }

    @Override
	protected void end() {
        Robot.drivetrain.arcadeDrive(0, 0);
	}
	
    @Override
	protected void interrupted() {
		end();
	}
}

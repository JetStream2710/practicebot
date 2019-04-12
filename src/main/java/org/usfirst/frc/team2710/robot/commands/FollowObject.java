package org.usfirst.frc.team2710.robot.commands;

import org.usfirst.frc.team2710.robot.Robot;
import org.usfirst.frc.team2710.util.PixyBlock;

import edu.wpi.first.wpilibj.command.Command;

public class FollowObject extends Command {

  // CHANGE WHEN NEEDED
  public static final double MOVE_SPEED = 0.6;
  public static final double MAX_SPEED = 0.6;
  public static final double MIN_SPEED = 0.5; 
  public static final double SPEED_RANGE = MAX_SPEED - MIN_SPEED;
  public static final double EDGE_DISTANCE = 50;
  public static final double EDGE_RANGE = 3/EDGE_DISTANCE;

  // Stop distance is the number of pixels both edges need to be from their respective side
  // before the command finishes.
  public static final double STOP_DISTANCE = 20;

  private boolean isFinished;

  public FollowObject() {
  }

  @Override
  protected void initialize() {
    isFinished = false;
  }

  @Override
  protected void execute() {
    PixyBlock leftBlock = Robot.pixy.getLeftBlock();
    PixyBlock rightBlock = Robot.pixy.getRightBlock();
 
    if (leftBlock == null || rightBlock == null){
        return;
    }

    //lastHeight = leftBlock.getHeight();

    int heightDifference = leftBlock.getHeight() - rightBlock.getHeight();
    int centerX = (leftBlock.getCenterX() + rightBlock.getCenterX())/2 - 157;

    double angleTurnValue = 0;
    double centerTurnValue = 0;
    double edgeTurnValue = 0;
    double turnValue = 0;

    double edgeLeft = leftBlock.getCenterX() - (leftBlock.getWidth()/2);
    double edgeRight = 315 - rightBlock.getCenterX() + (rightBlock.getWidth()/2);

    // at 0.1, a 10-pixel difference in height will cause us to increase turning speed by 1
    if (heightDifference > 2){
      angleTurnValue = (heightDifference-2)*-0.1;
      if (angleTurnValue < -1){
        angleTurnValue = -1;
      }
    }
    if (heightDifference < -2){
      angleTurnValue = (heightDifference+2)*-0.1;
     if (angleTurnValue > 1){
       angleTurnValue = 1;
     } 
    }

    if (centerX < -2 || centerX > 2){
      centerTurnValue = centerX/-157.0;
    }

    if (edgeLeft < EDGE_DISTANCE && edgeRight > EDGE_DISTANCE){
      edgeTurnValue = (EDGE_DISTANCE - edgeLeft) * EDGE_RANGE;
    } 
    if (edgeRight < EDGE_DISTANCE && edgeLeft > EDGE_DISTANCE){
      edgeTurnValue = -(EDGE_DISTANCE - edgeRight) * EDGE_RANGE;
    }

    turnValue = angleTurnValue + centerTurnValue + edgeTurnValue;

    if (turnValue < -0.1){
      turnValue = turnValue - MIN_SPEED;
      if (turnValue < -MAX_SPEED){
        turnValue = -MAX_SPEED;
      }
    }
    if (turnValue > 0.1){
      turnValue = turnValue + MIN_SPEED;
      if (turnValue > MAX_SPEED){
        turnValue = MAX_SPEED;
      }
    }

    isFinished = isFinished || (edgeLeft < STOP_DISTANCE && edgeRight < STOP_DISTANCE);

//    System.out.println("leftblock: " + leftBlock + " rightblock: " + rightBlock);
    System.out.println("height diff: " + heightDifference + " center: " + centerX +
      " angle turn: " + angleTurnValue + " center turn: " + centerTurnValue +
      " edge turn: " + edgeTurnValue + " turn: "  + turnValue);
    Robot.drivetrain.arcadeDrive(MOVE_SPEED, turnValue);
  }

  @Override
  protected boolean isFinished() {
    return Robot.pixy.getBadVisionCount() > 3;
  }
 
  @Override
  protected void end() {
    System.out.println("FollowObject end");
    Robot.drivetrain.arcadeDrive(0, 0);
  }

  @Override
  protected void interrupted() {
  }
}

package org.usfirst.frc.team2710.util;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.I2C;

public class PixyI2CDriver {
//  private Logger logger = new Logger(PixyI2CDriver.class.getName());
  private I2C pixy;

  public PixyI2CDriver(int port) {
    pixy = new I2C(I2C.Port.kOnboard, port);
  }

  public void turnOnLamp() {
  //  logger.info("turnOnLamp send: " + PixyMessage.bytesToString(PixyMessage.TURN_ON_LAMP));
    byte[] data = new byte[9];
    pixy.transaction(PixyMessage.TURN_ON_LAMP, PixyMessage.TURN_ON_LAMP.length, data, data.length);
  //  logger.info("turnOnLamp receive: " + PixyMessage.bytesToString(data));
  }

  public void turnOffLamp() {
  //  logger.info("turnOffLamp send: " + PixyMessage.bytesToString(PixyMessage.TURN_OFF_LAMP));
    byte[] data = new byte[9];
    pixy.transaction(PixyMessage.TURN_OFF_LAMP, PixyMessage.TURN_OFF_LAMP.length, data, data.length);
  //  logger.info("turnOffLamp receive: " + PixyMessage.bytesToString(data));
  }

  /*
  public PixyLine lineTracking(boolean isMirror) {
  //  logger.info("lineTracking send: " + PixyMessage.bytesToString(PixyMessage.LINE_TRACKING));
    byte[] data = new byte[14];
    pixy.transaction(PixyMessage.LINE_TRACKING, PixyMessage.LINE_TRACKING.length, data, data.length);
  //  logger.info("lineTracking receive: " + PixyMessage.bytesToString(data));
    return new PixyLine(data, isMirror);
  }
  */

  public PixyBlock[] objectTracking1() {
  //  logger.info("objectTracking send: " + PixyMessage.bytesToString(PixyMessage.OBJECT_TRACKING));
    //byte[] data = new byte[17];
    byte[] data = new byte[34];
    byte[] request = PixyMessage.getBlockRequest(1);
    pixy.transaction(request, request.length, data, data.length);
//pixy.transaction(PixyMessage.OBJECT_TRACKING_1, PixyMessage.OBJECT_TRACKING_1.length, data, data.length);
  //  logger.info("objectTracking receive: " + PixyMessage.bytesToString(data));
    // FIX THIS LINE
    byte[] firstBlock = new byte[14];
    byte[] secondBlock = new byte[14];
    System.arraycopy(data, 6, firstBlock, 0, 14);
    System.arraycopy(data, 20, secondBlock, 0, 14);
    return new PixyBlock[] {new PixyBlock(firstBlock), new PixyBlock(secondBlock)};
  }

  private int numBlocks = 8;
  public List<PixyBlock> objectTrackingForSig(int sig) {
    //System.out.println("objectTrackingForSig called");
    //  logger.info("objectTracking send: " + PixyMessage.bytesToString(PixyMessage.OBJECT_TRACKING));
    //byte[] data = new byte[17];
    byte[] data = new byte[6 + (14 * numBlocks)];
    byte[] request = PixyMessage.getBlockRequest(sig);
    pixy.transaction(request, request.length, data, data.length);
    List<PixyBlock> blockList = new ArrayList<>();
    for (int i = 0; i < numBlocks; i++) {
      //System.out.println("found: " + i);
      byte[] block = new byte[14];
      //System.arraycopy(data, 6, block, 0, 14);
      System.arraycopy(data, 6 + (i * 14), block, 0, 14);
      PixyBlock pixyBlock = new PixyBlock(block);
      if (pixyBlock.getSignature() >= 1 && pixyBlock.getSignature() <= 4) {
        blockList.add(pixyBlock);
      }
    }
    return blockList;
  }
  
}

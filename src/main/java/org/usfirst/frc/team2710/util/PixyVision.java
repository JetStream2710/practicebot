package org.usfirst.frc.team2710.util;

import java.util.List;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class PixyVision {
    public static boolean DEBUG = true;
    private static final long POLL_FREQUENCY_MILLIS = 1 + (1000 / 50);

    private PixyLine latestLine;
    private PixyBlock leftBlock;
    private PixyBlock rightBlock;

    private boolean turnOnLamp;
    private boolean turnOffLamp;
    private boolean trackLines;
    private boolean trackObjects;

    private int badVisionCounter;

    // signature number
    // The number in this array should be the binary representation of all of the
    // signatures we want to track. For example: 15 = 00001111 (in binary)
    private int[] signatures = new int[] {15};

    private boolean isRunning;
    private PixyVisionThread thread;


    public PixyVision(boolean trackLines, boolean trackObjects) {
        this.trackLines = trackLines;
        this.trackObjects = trackObjects;
    }

    public PixyLine getLatestLine() {
        return latestLine;
    }

    public PixyBlock getLeftBlock() {
        return leftBlock;
    }

    public PixyBlock getRightBlock() {
        return rightBlock;
    }

    public void turnOnLamp() {
        turnOnLamp = true;
    }

    public void turnOffLamp() {
        turnOffLamp = false;
    }

    public synchronized void start() {
        System.out.println("pixy start");
        if (thread == null) {
            thread = new PixyVisionThread();
            isRunning = true;
            thread.start();
            System.out.println("starting pixy");
        }
    }

    public synchronized void stop() {
        if (thread != null) {
            isRunning = false;
            thread.interrupt();
            thread = null;
        }
    }

    class PixyVisionThread extends Thread {
        private PixyI2CDriver driver = new PixyI2CDriver(0x53);
//        private PixyI2CDriver driver2 = new PixyI2CDriver(0x53);
        //private PixySpiDriver driver = new PixySpiDriver(SPI.Port.kOnboardCS1);
        //private PixySpiDriver driver2 = new PixySpiDriver(SPI.Port.kOnboardCS1);
      //  private PixyI2CDriver2 driver3 = new PixyI2CDriver2();
    
        @Override
        public void run() {
            debug("running thread");
           // turnOnLamp = true;
            while (isRunning) {
                if (turnOnLamp) {
                    driver.turnOnLamp();
                    turnOnLamp = false;
                }
                if (turnOffLamp) {
                    driver.turnOffLamp();
                    turnOffLamp = false;
                }
                /*
                if (trackLines) {
                    PixyLine line = driver.lineTracking();
                    if (line != null && isValid(line)) {
                        //debug("found line: " + line);
                        latestLine = line;
                    }
                }
                */
                if (trackObjects) {
                    PixyBlock[] blocks = new PixyBlock[2];
                    int blockIndex = 0;
                    for (int sig : signatures) {
                        //debug("Trying sig: "+ sig);
                        List<PixyBlock> blockList = driver.objectTrackingForSig(sig);
                        for (PixyBlock block : blockList) {
                            //debug("Considering " + sig + ": " + block);
                            // check to make sure block is valid
                            if (blockIndex < 2 &&
                                block.getHeight() > block.getWidth()) {
                                    //debug("... added block");
                                    blocks[blockIndex] = block;
                                    blockIndex++;
                            }
                        }
                    }
                    if (blockIndex == 2){
                        badVisionCounter = 0;
                        if (blocks[0].getCenterX() < blocks[1].getCenterX()) {
                            leftBlock = blocks[0];
                            rightBlock = blocks[1];
                        } else {
                            leftBlock = blocks[1];
                            rightBlock = blocks[0];
                        }
                        //debug("found objects left: " + leftBlock + " right: " + rightBlock);
                    } else{
                        badVisionCounter ++;
                    }
                }

                try {
                    Thread.sleep(POLL_FREQUENCY_MILLIS);
                }
                catch (InterruptedException e) {
                    debug("interrupted thread");
                }
            }
        }
    }

    private boolean isValid(PixyLine line) {
        if (line.getLowerX() == line.getUpperX() && 
            line.getLowerY() == line.getUpperY()) {
            return false;
        }
        return true;
    }

    private boolean isValid(PixyBlock[] blocks, int sig) {
        return blocks.length == 2 && blocks[0].getSignature() == sig && blocks[1].getSignature() == sig;
    }

    public int getBadVisionCount(){
        return badVisionCounter;
    }

    private void debug(String msg) {
        if (DEBUG) {
            System.out.println("PixyVision: " + msg);
            SmartDashboard.putString("PixyVision: ", msg);
        }
    }
}

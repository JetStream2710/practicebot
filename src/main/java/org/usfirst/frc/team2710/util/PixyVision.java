package org.usfirst.frc.team2710.util;

public class PixyVision {
    private static final long pollFrequencyMillis = 16;

    private boolean isRunning;
    private PixyLine latestLine;

    public PixyLine getLatestLine(){
        return latestLine;
    }

    public void start(){
        PixyVisionThread thread = new PixyVisionThread();
        isRunning = true;
        thread.start();
    }

    public void stop(){
        isRunning = false;
    }

    public final class PixyVisionThread extends Thread{
        private PixyI2CDriver driver = new PixyI2CDriver();
        @Override
        public void run(){
            System.out.println("starting Pixy Vision Thread");
            while(isRunning){
                PixyLine line = driver.lineTracking();
                if(line != null && isValid(line)){
                    latestLine = line;
                }
                try{
                    Thread.sleep(pollFrequencyMillis);
                }
                catch(InterruptedException e){
                    System.out.println("pixy vision was interrupted");
                }
            }
            System.out.println("stopping Pixy Vision Thread");
        }
        private boolean isValid(PixyLine line){
            if(line.getLowerX() == line.getUpperX() && 
               line.getLowerY() == line.getUpperY()){
                return false;
            }
            return true;
        }
    }
}

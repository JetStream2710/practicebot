package org.usfirst.frc.team2710.util;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class PixyBlock {

    private byte[] bytes;

    public PixyBlock(byte[] bytes) {
        this.bytes = bytes;
    }

    /** @return a number from 0 to 255 */
    public int getSignature() {
        return byteToUint(bytes[0]);
    }

    /** @return a number from 0 to 315 */
    public int getCenterX() {
        return concatBytes(bytes[2], bytes[3]);
    }

    /** @return a number from 0 to 207 */
    public int getCenterY() {
        return concatBytes(bytes[4], bytes[5]);
    }

    /** @return a number from 0 to 316 */
    public int getWidth() {
        return concatBytes(bytes[6], bytes[7]);
    }

    /** @return a number from 0 to 208 */
    public int getHeight() {
        return concatBytes(bytes[8], bytes[9]);
    }

    /** @return a number from 0 to 255 */
    public int getTrackingIndex() {
        return byteToUint(bytes[12]);
    }

    /** @return a number from 0 to 255 */
    public int getAgeInFrames() {
        return byteToUint(bytes[13]);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("signature: ");
        sb.append(getSignature());
        sb.append(" center coordinates(");
        sb.append(getCenterX());
        sb.append(",");
        sb.append(getCenterY());
        sb.append(") width: ");
        sb.append(getWidth());
        sb.append(" height: ");
        sb.append(getHeight());
        sb.append(" track-index: ");
        sb.append(getTrackingIndex());
        sb.append(" age: ");
        sb.append(getAgeInFrames());
        sb.append(" distance: ");
        sb.append(getDistance());
        return sb.toString();
    }

    public static int byteToUint(byte b) {
        return (int) (b & 0xFF);
    }
    
    public static int concatBytes(byte lower, byte upper) {
//        ByteBuffer bb = ByteBuffer.wrap(new byte[] {lower, upper});
//        bb.order(ByteOrder.LITTLE_ENDIAN);
//       return bb.getShort();
		return (((short) (upper & 0xff)) << 8) | ((short) (lower & 0xff));
    }

    public double getDistance(){
        return 1380 * Math.pow(getHeight(), -0.982);
    }
}

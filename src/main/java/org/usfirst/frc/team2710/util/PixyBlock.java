package org.usfirst.frc.team2710.util;

public class PixyBlock {

    private byte[] bytes;

    public PixyBlock(byte[] bytes) {
        this.bytes = bytes;
    }

    /** @return a number from 0 to 255 */
    public int getSignature() {
        return byteToUint(bytes[6]);
    }

    /** @return a number from 0 to 315 */
    public int getCenterX() {
        return concatBytes(bytes[7], bytes[8]);
    }

    /** @return a number from 0 to 207 */
    public int getCenterY() {
        return byteToUint(bytes[9]);
    }

    /** @return a number from 0 to 316 */
    public int getWidth() {
        return concatBytes(bytes[10], bytes[11]);
    }

    /** @return a number from 0 to 208 */
    public int getHeight() {
        return concatBytes(bytes[12], bytes[13]);
    }

    /** @return a number from 0 to 255 */
    public int getTrackingIndex() {
        return byteToUint(bytes[16]);
    }

    /** @return a number from 0 to 255 */
    public int getAgeInFrames() {
        return byteToUint(bytes[17]);
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
        return sb.toString();
    }

    public static int byteToUint(byte b) {
        return (int) (b & 0xFF);
    }
    
    public static int concatBytes(byte upper, byte lower) {
		return (((int) (upper & 0xff)) << 8) | ((int) (lower & 0xff));
    }
}

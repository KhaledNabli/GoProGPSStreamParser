import java.io.ByteArrayInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;

public abstract class HelperUtils {
	public static short readNextShort(ByteArrayInputStream byteReader) {
		byte[] dataBuffer = new byte[2];
		byteReader.read(dataBuffer, 0, 2);
		short result  = ByteBuffer.wrap(dataBuffer).order(ByteOrder.BIG_ENDIAN).getShort();
		return result;
	}
	
	public static String readNextString(ByteArrayInputStream byteReader, int length) {
		byte[] dataBuffer = new byte[length];
		byteReader.read(dataBuffer, 0, length);
		return new String(dataBuffer, Charset.forName("UTF-8"));
	}
	
	
	public static int readNextInt(ByteArrayInputStream byteReader) {
		byte[] dataBuffer = new byte[4];
		byteReader.read(dataBuffer, 0, 4);
		int result  = ByteBuffer.wrap(dataBuffer).order(ByteOrder.BIG_ENDIAN).getInt();
		return result;
	}
	
	public static float readNextFloat(ByteArrayInputStream byteReader) {
		byte[] dataBuffer = new byte[4];
		byteReader.read(dataBuffer, 0, 4);
		float result  = ByteBuffer.wrap(dataBuffer).order(ByteOrder.BIG_ENDIAN).getFloat();
		return result;
	}
}

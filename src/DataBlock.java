import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class DataBlock {
	public String name;
	public int dataType;
	public int dataTypeSize; // how many bytes per unit
	public int dataSize; // how many units
	public Object value;

	@SuppressWarnings("unchecked")
	public DataBlock(ByteArrayInputStream byteReader) {
		name = HelperUtils.readNextString(byteReader, 4);
		dataType = byteReader.read();
		dataTypeSize = byteReader.read();
		dataSize = HelperUtils.readNextShort(byteReader);
		int requiredPadding = 0;
		

		switch (dataType) {
		case 0:
			
			value = dataSize;
			dataSize = 1;
			dataTypeSize = 4;
			break;
		case 102:
			// float[]
			value = new ArrayList<Float>();
			for(int j = 0; j < dataSize; j++) {
				((ArrayList<Float>) value).add(HelperUtils.readNextFloat(byteReader));
			}
			
			break;
		case 76:
			// int
			value = HelperUtils.readNextInt(byteReader);
			break;
		case 99:
		case 85:
			// char*
			int totalLength = dataTypeSize * dataSize;
			value = HelperUtils.readNextString(byteReader, totalLength);
			requiredPadding = 4 - (totalLength % 4); 
			break;
		case 115:
			// short[]
			value = new ArrayList<Short>();
			totalLength = dataSize * (dataTypeSize / 2);
			for(int j = 0; j < totalLength; j++) {
				((ArrayList<Short>) value).add(HelperUtils.readNextShort(byteReader));
			}
			
			requiredPadding = 4 - ((totalLength * 2) % 4); 
			break;
		case 108:
			// int[]
			value = new ArrayList<Integer>();
			totalLength = dataSize * (dataTypeSize / 4);
			for(int j = 0; j < totalLength; j++) {
				((ArrayList<Integer>) value).add(HelperUtils.readNextInt(byteReader));
			}
			break;
		case 83:
			// short
			value = HelperUtils.readNextShort(byteReader);
			requiredPadding = 2;
			break;
		default:
			System.err.println("UNKNOWN dataType: " + dataType + " for BlockHeader " + name);
		}

		
		if(requiredPadding > 0 && requiredPadding < 4) {
			//System.out.println("Padding by " + requiredPadding + " bytes");
			byteReader.skip(requiredPadding);
		}
	}

	@Override
	public String toString() {
		return "DataBlock [name=" + name + ", dataType=" + dataType + ", dataTypeSize=" + dataTypeSize + ", dataSize="
				+ dataSize + ", value=" + value + "]";
	}

	

}

import java.util.ArrayList;

public class SensorBlock {
	private String streamNm;
	private String SIUnit;
	private ArrayList<Float> rawData;
	
	public String getStreamNm() {
		return streamNm;
	}
	public void setStreamNm(String streamNm) {
		this.streamNm = streamNm;
	}
	public String getSIUnit() {
		return SIUnit;
	}
	public void setSIUnit(String sIUnit) {
		SIUnit = sIUnit;
	}
	public ArrayList<Float> getRawData() {
		return rawData;
	}
	public void setRawData(ArrayList<Float> rawData) {
		this.rawData = rawData;
	}
	
	
	@Override
	public String toString() {
		return "SensorBlock [streamNm=" + streamNm + ", SIUnit=" + SIUnit + ", rawData=" + rawData + "]";
	}

	
	
	
}

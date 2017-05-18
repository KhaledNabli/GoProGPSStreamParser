import java.util.ArrayList;

public class GyroBlock {
	private String streamNm;
	private float tmpc;
	private String SIUnit;
	private int Scale;
	private ArrayList<Short> rawData;
	private ArrayList<GyroData> normalizedData = new ArrayList<GyroData>();
	
	
	public String getStreamNm() {
		return streamNm;
	}
	public void setStreamNm(String streamNm) {
		this.streamNm = streamNm;
	}
	public float getTmpc() {
		return tmpc;
	}
	public void setTmpc(float tmpc) {
		this.tmpc = tmpc;
	}
	public String getSIUnit() {
		return SIUnit;
	}
	public void setSIUnit(String sIUnit) {
		SIUnit = sIUnit;
	}
	public int getScale() {
		return Scale;
	}
	public void setScale(int scale) {
		Scale = scale;
	}
	public ArrayList<Short> getRawData() {
		return rawData;
	}
	public void setRawData(ArrayList<Short> rawData) {
		this.rawData = rawData;
		normalizeData();
	}
	public ArrayList<GyroData> getNormalizedData() {
		return normalizedData;
	}
	
	private void normalizeData() {
		normalizedData.clear();
		for(int i = 0; i < rawData.size(); i=i+3) {
			GyroData data = new GyroData();
			data.z = (float) rawData.get(i) / Scale;
			data.x = (float) rawData.get(i+1) / Scale;
			data.y = (float) rawData.get(i+2) / Scale;
			
			normalizedData.add(data);
		}
	}
	@Override
	public String toString() {
		return "\r\n\tGyroBlock [streamNm=" + streamNm + ", tmpc=" + tmpc + ", SIUnit=" + SIUnit + ", Scale=" + Scale
				+ ", normalizedData=" + normalizedData + "]";
	}



	
	
}

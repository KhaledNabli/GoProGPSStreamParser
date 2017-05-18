import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class GPSBlock {
	private int gpsf;
	private String gpsu;
	private int gpsp;
	private String streamNm;
	private String units;
	private ArrayList<Integer> Scales;
	private ArrayList<Integer> rawData;
	private ArrayList<GPSData> normalizedData = new ArrayList<GPSData>();
	private Calendar referenceDate;
	private SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

	public int getGpsf() {
		return gpsf;
	}
 
	public void setGpsf(int gpsf) {
		this.gpsf = gpsf;
	}

	public String getGpsu() {
		return gpsu;
	}

	public void setGpsu(String gpsu) {
		int year = Integer.parseInt(gpsu.substring(0, 2)) + 2000;
		int month = Integer.parseInt(gpsu.substring(2, 4)) ;
		int day = Integer.parseInt(gpsu.substring(4, 6)) ;
		int hour = Integer.parseInt(gpsu.substring(6, 8)) ;
		int minute = Integer.parseInt(gpsu.substring(8, 10)) ;
		int sec = Integer.parseInt(gpsu.substring(10, 12));
		int sub = Integer.parseInt(gpsu.substring(13, 16));
		
		referenceDate = Calendar.getInstance();
		referenceDate.set(year, month-1, day, hour, minute, sec);
		referenceDate.set(Calendar.MILLISECOND, sub);


		this.gpsu = gpsu;
	}


	public int getGpsp() {
		return gpsp;
	}

	public void setGpsp(int gpsp) {
		this.gpsp = gpsp;
	}

	public String getStreamNm() {
		return streamNm;
	}

	public void setStreamNm(String streamNm) {
		this.streamNm = streamNm;
	}

	public String getUnits() {
		return units;
	}

	public void setUnits(String units) {
		this.units = units;
	}

	public ArrayList<Integer> getScales() {
		return Scales;
	}

	public void setScales(ArrayList<Integer> scales) {
		Scales = scales;
	}

	public ArrayList<Integer> getRawData() {
		return rawData;
	}

	public void setRawData(ArrayList<Integer> rawData) {
		this.rawData = rawData;
		normalizeData();
	}

	public ArrayList<GPSData> getNormalizedData() {
		return normalizedData;
	}
	
	public Calendar getReferenceDate() {
		int year = Integer.parseInt(gpsu.substring(0, 2)) + 2000;
		int month = Integer.parseInt(gpsu.substring(2, 4)) ;
		int day = Integer.parseInt(gpsu.substring(4, 6)) ;
		int hour = Integer.parseInt(gpsu.substring(6, 8)) ;
		int minute = Integer.parseInt(gpsu.substring(8, 10)) ;
		int sec = Integer.parseInt(gpsu.substring(10, 12));
		int sub = Integer.parseInt(gpsu.substring(13, 16));
		
		referenceDate = Calendar.getInstance();
		referenceDate.set(year, month-1, day, hour, minute, sec);
		referenceDate.set(Calendar.MILLISECOND, sub);
		
		return referenceDate;
	}


	private void normalizeData() {
		normalizedData.clear();
		for (int i = 0; i < rawData.size(); i = i + 5) {
			GPSData data = new GPSData();
			data.timeStamp = dateFormatter.format(referenceDate.getTime());
			referenceDate.add(Calendar.MILLISECOND, 1000/rawData.size());
			
			
			data.latitude = (float) rawData.get(i) / Scales.get(0);
			data.longtitude = (float) rawData.get(i + 1) / Scales.get(1);
			data.altitude = (float) rawData.get(i + 2) / Scales.get(2);
			data.speed2d = (float) rawData.get(i + 3) / Scales.get(3);
			data.speed3d = (float) rawData.get(i + 4) / Scales.get(4);

			normalizedData.add(data);
		}
	}

	@Override
	public String toString() {
		return "\r\n\tGPSBlock [gpsf=" + gpsf + ", gpsu=" + gpsu + ", gpsp=" + gpsp + ", streamNm=" + streamNm + ", units="
				+ units + ", Scales=" + Scales + ", normalizedData=" + normalizedData + "]";
	}
	
	
}


public class GPSData {
	public String timeStamp;
	public float latitude;
	public float longtitude;
	public float altitude;
	public float speed2d;
	public float speed3d;
	
	public String getTrackPoint() {
		return "\r\n<trkpt lat=\""+ latitude + "\" lon=\"" + longtitude + "\"><ele>"+ altitude +"</ele><time>"+ timeStamp +"</time></trkpt>";
	}

	@Override
	public String toString() {
		return "\r\n\t\tGPSData [timeStamp=" + timeStamp + ", latitude=" + latitude + ", longtitude=" + longtitude
				+ ", altitude=" + altitude + ", speed2d=" + speed2d + ", speed3d=" + speed3d + "]";
	}
	

	
	
}


public class AccelerometerData {
	public float up_down;
	public float right_left;
	public float forward_back;
	@Override
	public String toString() {
		return " [up=" + up_down + ", right=" + right_left + ", forward=" + forward_back
				+ "]";
	}
}

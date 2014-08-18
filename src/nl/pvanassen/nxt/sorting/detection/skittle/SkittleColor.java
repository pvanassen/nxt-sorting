package nl.pvanassen.nxt.sorting.detection.skittle;

public enum SkittleColor {
	ORANGE(0), PURPLE(100), RED(200), YELLOW(300), GREEN(400), TRACK(0), NONE(0);

	private final int angle;
	private SkittleColor(int angle) {
		this.angle = angle;
	}
	
	public int getAngle() {
		return angle;
	}
}
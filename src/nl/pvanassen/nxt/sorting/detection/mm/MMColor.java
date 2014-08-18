package nl.pvanassen.nxt.sorting.detection.mm;

public enum MMColor {
	
	BLUE(0), ORANGE(90), BROWN(180), GREEN(270), RED(360), YELLOW(450), TRACK(0), NONE(0);
	private final int angle;
	private MMColor(int angle) {
		this.angle = angle;
	}
	
	public int getAngle() {
		return angle;
	}
}
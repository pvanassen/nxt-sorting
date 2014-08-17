package nl.pvanassen.nxt.sorting;

enum MMColor {
	
	BLUE(0), ORANGE(90), BROWN(180), GREEN(270), RED(360), YELLOW(450), TRACK(0);
	private final int angle;
	private MMColor(int angle) {
		this.angle = angle;
	}
	
	int getAngle() {
		return angle;
	}
}
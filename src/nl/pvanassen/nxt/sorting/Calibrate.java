package nl.pvanassen.nxt.sorting;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;

public class Calibrate {
	private final Shute shute;
	private final ColorSensor cs;
	private final int diff;
	private static final int BELT_SPEED = 100;
	
	public Calibrate() throws InterruptedException {
		cs = new ColorSensor(SensorPort.S1);
		diff = (int)Math.max(0, 78 - cs.getLightValue());
		System.out.println("Base: " + cs.getLightValue());
		// 100 -> 3000
		// 200 -> 1500
		// 300 -> 1000
		// 400 -> 750
		// 1000 -> 300
		shute = new Shute(3250);
		Thread shuteThread = new Thread(shute);
		shuteThread.setDaemon(true);
		shuteThread.start();
		Motor.A.setSpeed(BELT_SPEED);
		Motor.A.forward();
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Calibrate().execute();
	}
	
	private void execute() {
		MMColor oldColor = MMColor.TRACK;
		MMColor color = MMColor.TRACK;
		while (!Button.ENTER.isDown()) {
			color = Detector.getMMColor(cs);
			if (color != oldColor) {
				if (color != MMColor.TRACK) {
					shute.detected(color);
				}
				oldColor = color;
			}
		}
		shute.stop();
	}
}

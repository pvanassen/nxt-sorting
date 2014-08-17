package nl.pvanassen.nxt.sorting;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import nl.pvanassen.nxt.sorting.detection.ColorCallback;
import nl.pvanassen.nxt.sorting.detection.Detector;

public class Sorter implements ColorCallback {
	private final Shute shute;
	private final Detector detector;
	private static final int BELT_SPEED = 104;
	
	public Sorter() throws InterruptedException {
		// 100 -> 3250
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
		detector = new Detector(this, new ColorSensor(SensorPort.S1));
		Thread detectorThread = new Thread(detector);
		detectorThread.setDaemon(true);
		detectorThread.start();
	}
	
	public static void main(String[] args) throws InterruptedException {
		new Sorter().execute();
	}
	
	private void execute() throws InterruptedException {
		while (!Button.ENTER.isDown()) {
			Thread.sleep(250);
		}
		shute.stop();
	}
	
	@Override
	public void detected(MMColor color) {
		shute.detected(color);
	}
}

package nl.pvanassen.nxt.sorting;

import lejos.nxt.Button;
import lejos.nxt.ColorSensor;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.SensorPort;
import nl.pvanassen.nxt.sorting.detection.mm.MMDetector;
import nl.pvanassen.nxt.sorting.detection.skittle.SkittleDetector;

public class Sorter {
	private final Shute shute;
	private final Runnable detector;
	private static final int BELT_SPEED = 104;

	public Sorter() throws InterruptedException {
		String type = getType();
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
		//Motor.A.flt();
		if ("MM".equals(type)) {
			detector = new MMDetector(shute, new ColorSensor(SensorPort.S1));
		} else {
			detector = new SkittleDetector(shute,
					new ColorSensor(SensorPort.S1));
		}
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

	private String getType() {
		String type = getType("MM", "M&M's");
		LCD.clearDisplay();
		return type;
	}

	private String getType(String type, String name) {
		LCD.clearDisplay();
		LCD.drawString("I want to sort ", 0, 0);
		LCD.drawString(name + "!", 4, 1);

		int button = Button.waitForAnyPress();
		if (button == Button.ID_ENTER) {
			return type;
		}
		if (button == Button.ID_LEFT || button == Button.ID_RIGHT) {
			if ("MM".equals(type)) {
				return getType("Skittle", "Skittles");
			}
			return getType("MM", "M&M's");
		}
		return getType(type, name);
	}

}

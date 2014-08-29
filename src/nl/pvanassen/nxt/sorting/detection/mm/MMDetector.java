package nl.pvanassen.nxt.sorting.detection.mm;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import nl.pvanassen.nxt.sorting.detection.ColorCallback;

public class MMDetector implements Runnable {
	private final ColorCallback colorCallback;
	private final TouchSensor touchSensor = new TouchSensor(SensorPort.S2);
	private final ColorSensor cs;
	private boolean stop;

	public MMDetector(ColorCallback colorCallback, ColorSensor cs) {
		this.colorCallback = colorCallback;
		this.cs = cs;
	}

	@Override
	public void run() {
		try {
			ColorCounter counter = new ColorCounter();
			MMColor color = MMColor.TRACK;
			int tracks;
			while (!stop) {
				while (color == MMColor.TRACK) {
					color = getMMColor(cs);
					Thread.sleep(50);
				}
				tracks = 0;
				while (tracks < 2) {
					counter.count(color);
					Thread.sleep(10);
					color = getMMColor(cs);
					if (color == MMColor.TRACK) {
						tracks++;
					}
				}
				MMColor detected = counter.guess();
				if (detected != MMColor.TRACK) {
					colorCallback.detected(detected);
				}
				counter.reset();
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			return;
		}
	}

	private MMColor getMMColor(ColorSensor cs) {
		Color raw = cs.getColor();
		if (touchSensor.isPressed()) {
			System.out.println("R" + raw.getRed() + " G" + raw.getGreen() + " B" + raw.getBlue());
		}
		if (around(raw.getRed(), 105) && around(raw.getGreen(), 81)
				&& around(raw.getBlue(), 64)) {
			return MMColor.TRACK;
		}
		if (around(raw.getRed(), 308) && around(raw.getGreen(), 84)
				&& around(raw.getBlue(), 55)) {
			return MMColor.ORANGE;
		}
		if (around(raw.getRed(), 307) && around(raw.getGreen(), 125)
				&& around(raw.getBlue(), 102)) {
			return MMColor.ORANGE;
		}
		if (around(raw.getRed(), 150) && around(raw.getGreen(), 212)
				&& around(raw.getBlue(), 113)) {
			return MMColor.GREEN;
		}
		if (around(raw.getRed(), 180) && around(raw.getGreen(), 236)
				&& around(raw.getBlue(), 147)) {
			return MMColor.GREEN;
		}
		if (around(raw.getRed(), 305) && around(raw.getGreen(), 255)
				&& around(raw.getBlue(), 125)) {
			return MMColor.YELLOW;
		}
		if (around(raw.getRed(), 286) && around(raw.getGreen(), 66)
				&& around(raw.getBlue(), 82)) {
			return MMColor.RED;
		}
		if (around(raw.getRed(), 271) && around(raw.getGreen(), 103)
				&& around(raw.getBlue(), 124)) {
			return MMColor.RED;
		}
		if (around(raw.getRed(), 110) && around(raw.getGreen(), 180)
				&& around(raw.getBlue(), 250)) {
			return MMColor.BLUE;
		}
		if (around(raw.getRed(), 135) && around(raw.getGreen(), 190)
				&& around(raw.getBlue(), 250)) {
			return MMColor.BLUE;
		}
		if (around(raw.getRed(), 185) && around(raw.getGreen(), 133) && around(raw.getBlue(), 134)) {
			return MMColor.BROWN;
		}
		if (around(raw.getRed(), 150)
				&& around(raw.getGreen(), 66)
				&& around(raw.getBlue(), 66)) {
			return MMColor.BROWN;
		}
		return MMColor.NONE;
	}

	private boolean between(int color, int lowLimit, int highLimit) {
		return color > lowLimit && color < highLimit;
	}

	private boolean around(int color, int value) {
		return between(color, value - 25, value + 25);
	}

	void stop() {
		stop = true;
	}
}

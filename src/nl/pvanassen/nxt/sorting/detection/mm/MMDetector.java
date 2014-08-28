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
		if (around(raw.getRed(), 85) && around(raw.getGreen(), 68)
				&& around(raw.getBlue(), 62)) {
			return MMColor.TRACK;
		}
		if (around(raw.getRed(), 139) && around(raw.getGreen(), 215)
				&& around(raw.getBlue(), 275)) {
			return MMColor.BLUE;
		}
		if (around(raw.getRed(), 156) && around(raw.getGreen(), 215)
				&& around(raw.getBlue(), 265)) {
			return MMColor.BLUE;
		}
		if (around(raw.getRed(), 277) && around(raw.getGreen(), 243)
				&& around(raw.getBlue(), 130)) {
			return MMColor.YELLOW;
		}
		if (around(raw.getRed(), 325) && around(raw.getGreen(), 296)
				&& around(raw.getBlue(), 189)) {
			return MMColor.YELLOW;
		}
		if (around(raw.getRed(), 162) && around(raw.getGreen(), 218)
				&& around(raw.getBlue(), 151)) {
			return MMColor.GREEN;
		}
		if (around(raw.getRed(), 170) && around(raw.getGreen(), 229)
				&& around(raw.getBlue(), 161)) {
			return MMColor.GREEN;
		}
		if (around(raw.getRed(), 260) && around(raw.getGreen(), 136)
				&& around(raw.getBlue(), 116)) {
			return MMColor.ORANGE;
		}
		if (around(raw.getRed(), 235) && around(raw.getGreen(), 146)
				&& around(raw.getBlue(), 130)) {
			return MMColor.ORANGE;
		}
		if (around(raw.getRed(), 234) && around(raw.getGreen(), 118)
				&& around(raw.getBlue(), 118)) {
			return MMColor.RED;
		}
		if (around(raw.getRed(), 277) && around(raw.getGreen(), 128)
				&& around(raw.getBlue(), 132)) {
			return MMColor.RED;
		}
		if (around(raw.getRed(), 146)
				&& around(raw.getGreen(), 84)
				&& around(raw.getBlue(), 78)) {
			return MMColor.BROWN;
		}
		if (around(raw.getRed(), 177)
				&& around(raw.getGreen(), 155)
				&& around(raw.getBlue(), 149)) {
			return MMColor.BROWN;
		}
		return MMColor.NONE;
	}

	private boolean between(int color, int lowLimit, int highLimit) {
		return color > lowLimit && color < highLimit;
	}

	private boolean around(int color, int value) {
		return between(color, value - 20, value + 20);
	}

	void stop() {
		stop = true;
	}
}

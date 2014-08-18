package nl.pvanassen.nxt.sorting.detection.skittle;

import lejos.nxt.ColorSensor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.ColorSensor.Color;
import nl.pvanassen.nxt.sorting.detection.ColorCallback;

public class SkittleDetector implements Runnable {
	private final ColorCallback colorCallback;
	private final ColorSensor cs;
	private boolean stop;

	public SkittleDetector(ColorCallback colorCallback, ColorSensor cs) {
		this.colorCallback = colorCallback;
		this.cs = cs;
	}

	@Override
	public void run() {
		try {
			ColorCounter counter = new ColorCounter();
			SkittleColor color = SkittleColor.TRACK;
			int tracks;
			while (!stop) {
				while (color == SkittleColor.TRACK) {
					color = getSkittleColor(cs);
					Thread.sleep(50);
				}
				tracks = 0;
				while (tracks < 2) {
					counter.count(color);
					Thread.sleep(10);
					color = getSkittleColor(cs);
					if (color == SkittleColor.TRACK) {
						tracks++;
					}
					// System.out.println(color);
				}
				SkittleColor detected = counter.guess();
				if (detected != SkittleColor.TRACK) {
					colorCallback.detected(detected);
				}
				counter.reset();
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			return;
		}
	}

	private SkittleColor getSkittleColor(ColorSensor cs) {
		Color raw = cs.getColor();
		if (new TouchSensor(SensorPort.S2).isPressed()) {
			System.out.println("R" + raw.getRed() + " G" + raw.getGreen() + " B" + raw.getBlue());
		}
		if (around(raw.getRed(), 85) && around(raw.getGreen(), 68)
				&& around(raw.getBlue(), 62)) {
			return SkittleColor.TRACK;
		}
		if (around(raw.getRed(), 272) && around(raw.getGreen(), 196)
				&& around(raw.getBlue(), 93)) {
			return SkittleColor.YELLOW;
		}
		if (around(raw.getRed(), 263) && around(raw.getGreen(), 197)
				&& around(raw.getBlue(), 106)) {
			return SkittleColor.YELLOW;
		}
		if (around(raw.getRed(), 98) && around(raw.getGreen(), 127)
				&& around(raw.getBlue(), 59)) {
			return SkittleColor.GREEN;
		}
		if (around(raw.getRed(), 127) && around(raw.getGreen(), 158)
				&& around(raw.getBlue(), 77)) {
			return SkittleColor.GREEN;
		}
		if (around(raw.getRed(), 282) && around(raw.getGreen(), 67)
				&& around(raw.getBlue(), 39)) {
			return SkittleColor.ORANGE;
		}
		if (around(raw.getRed(), 265) && around(raw.getGreen(), 84)
				&& around(raw.getBlue(), 61)) {
			return SkittleColor.ORANGE;
		}
		if (around(raw.getRed(), 207) && around(raw.getGreen(), 14)
				&& around(raw.getBlue(), 10)) {
			return SkittleColor.RED;
		}
		if (around(raw.getRed(), 229) && around(raw.getGreen(), 34)
				&& around(raw.getBlue(), 39)) {
			return SkittleColor.RED;
		}
		if (around(raw.getRed(), 136) && around(raw.getGreen(), 33)
				&& around(raw.getBlue(), 53)) {
			return SkittleColor.PURPLE;
		}
		if (around(raw.getRed(), 136) && around(raw.getGreen(), 44)
				&& around(raw.getBlue(), 59)) {
			return SkittleColor.PURPLE;
		}
		return SkittleColor.NONE;
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

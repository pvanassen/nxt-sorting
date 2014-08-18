package nl.pvanassen.nxt.sorting.detection.skittle;

import lejos.nxt.ColorSensor;
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
				while (tracks < 30) {
					counter.count(color);
					Thread.sleep(10);
					color = getSkittleColor(cs);
					if (color == SkittleColor.TRACK) {
						tracks++;
					}
					System.out.println(color);
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
		if (around(raw.getRed(), 85) && around(raw.getGreen(), 68)
				&& around(raw.getBlue(), 62)) {
			return SkittleColor.TRACK;
		}
		if (around(raw.getRed(), 311) && around(raw.getGreen(), 274)
				&& around(raw.getBlue(), 134)) {
			return SkittleColor.YELLOW;
		}
		if (around(raw.getRed(), 315) && around(raw.getGreen(), 279)
				&& around(raw.getBlue(), 170)) {
			return SkittleColor.YELLOW;
		}
		if (around(raw.getRed(), 150) && around(raw.getGreen(), 227)
				&& around(raw.getBlue(), 138)) {
			return SkittleColor.GREEN;
		}
		if (around(raw.getRed(), 180) && around(raw.getGreen(), 256)
				&& around(raw.getBlue(), 163)) {
			return SkittleColor.GREEN;
		}
		if (around(raw.getRed(), 310) && around(raw.getGreen(), 110)
				&& around(raw.getBlue(), 74)) {
			return SkittleColor.ORANGE;
		}
		if (around(raw.getRed(), 216) && around(raw.getGreen(), 154)
				&& around(raw.getBlue(), 126)) {
			return SkittleColor.ORANGE;
		}
		if (around(raw.getRed(), 293) && around(raw.getGreen(), 87)
				&& around(raw.getBlue(), 106)) {
			return SkittleColor.RED;
		}
		if (around(raw.getRed(), 282) && around(raw.getGreen(), 121)
				&& around(raw.getBlue(), 146)) {
			return SkittleColor.RED;
		}
		if (around(raw.getRed(), 146) && around(raw.getGreen(), 66)
				&& around(raw.getBlue(), 66)) {
			return SkittleColor.PURPLE;
		}
		if (around(raw.getRed(), 166) && around(raw.getGreen(), 99)
				&& around(raw.getBlue(), 100)) {
			return SkittleColor.PURPLE;
		}
		return SkittleColor.TRACK;
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

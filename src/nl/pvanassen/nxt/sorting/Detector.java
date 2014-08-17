package nl.pvanassen.nxt.sorting;

import lejos.nxt.ColorSensor;
import lejos.nxt.ColorSensor.Color;

public class Detector {
	static MMColor getMMColor(ColorSensor cs) {
		Color raw = cs.getColor();
		if (around(raw.getRed(), 85) && around(raw.getGreen(), 68)
				&& around(raw.getBlue(), 62)) {
			return MMColor.TRACK;
		}
		if (around(raw.getRed(), 146) && around(raw.getGreen(), 215)
				&& around(raw.getBlue(), 284)) {
			return MMColor.BLUE;
		}
		if (around(raw.getRed(), 124) && around(raw.getGreen(), 199)
				&& around(raw.getBlue(), 274)) {
			return MMColor.BLUE;
		}
		if (around(raw.getRed(), 311) && around(raw.getGreen(), 274)
				&& around(raw.getBlue(), 134)) {
			return MMColor.YELLOW;
		}
		if (around(raw.getRed(), 315) && around(raw.getGreen(), 279)
				&& around(raw.getBlue(), 170)) {
			return MMColor.YELLOW;
		}
		if (around(raw.getRed(), 150) && around(raw.getGreen(), 227)
				&& around(raw.getBlue(), 138)) {
			return MMColor.GREEN;
		}
		if (around(raw.getRed(), 180) && around(raw.getGreen(), 256)
				&& around(raw.getBlue(), 163)) {
			return MMColor.GREEN;
		}
		if (around(raw.getRed(), 310) && around(raw.getGreen(), 110)
				&& around(raw.getBlue(), 74)) {
			return MMColor.ORANGE;
		}
		if (around(raw.getRed(), 216) && around(raw.getGreen(), 154)
				&& around(raw.getBlue(), 126)) {
			return MMColor.ORANGE;
		}
		if (around(raw.getRed(), 293) && around(raw.getGreen(), 87)
				&& around(raw.getBlue(), 106)) {
			return MMColor.RED;
		}
		if (around(raw.getRed(), 282) && around(raw.getGreen(), 121)
				&& around(raw.getBlue(), 146)) {
			return MMColor.RED;
		}
/*		if (aroundPrecise(raw.getRed(), 146)
				&& aroundPrecise(raw.getGreen(), 66)
				&& aroundPrecise(raw.getBlue(), 66)) {
			System.out.println("BG1: " + raw.getBackground());
			return MMColor.BROWN;
		}*/
		if (aroundPrecise(raw.getRed(), 166)
				&& aroundPrecise(raw.getGreen(), 99)
				&& aroundPrecise(raw.getBlue(), 100)) {
			System.out.println("BG2: " + raw.getBackground());
			return MMColor.BROWN;
		}
		return MMColor.TRACK;
	}

	private static boolean between(int color, int lowLimit, int highLimit) {
		return color > lowLimit && color < highLimit;
	}

	private static boolean around(int color, int value) {
		return between(color, value - 20, value + 20);
	}

	private static boolean aroundPrecise(int color, int value) {
		return between(color, value - 10, value + 10);
	}
}

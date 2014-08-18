package nl.pvanassen.nxt.sorting.detection.skittle;


class ColorCounter {
	private int []counts = new int[5];
	private static final int GREEN = 0;
	private static final int ORANGE = 1;
	private static final int PURPLE = 2;
	private static final int RED = 3;
	private static final int YELLOW = 4;
	
	void reset() {
		for (int i=0;i!=counts.length;i++) {
			counts[i] = 0;
		}
	}
	
	void count(SkittleColor color) {
		switch (color) {
			case GREEN: counts[GREEN]++; break;
			case ORANGE: counts[ORANGE]++; break;
			case PURPLE: counts[PURPLE]++; break;
			case RED: counts[RED]++; break;
			case YELLOW: counts[YELLOW]++; break;
			case TRACK: return;
			case NONE: return;
		}
	}
	
	SkittleColor guess() {
		int color = 0;
		int max = 0;
		for (int i=0;i!=counts.length;i++) { 
			int count = counts[i];
			if (count > 0) {
				System.out.println("Found " + count + " for " + i);
			}
			if (count > max) {
				max = count;
				color = i;
			}
		}
		if (color == PURPLE) {
			return SkittleColor.PURPLE;
		}
		if (color == GREEN) {
			return SkittleColor.GREEN;
		}
		if (color == ORANGE) {
			return SkittleColor.ORANGE;
		}
		if (color == RED) {
			return SkittleColor.RED;
		}
		if (color == YELLOW) {
			return SkittleColor.YELLOW;
		}
		return SkittleColor.NONE;
	}
}

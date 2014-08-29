package nl.pvanassen.nxt.sorting.detection.mm;


class ColorCounter {
	private int []counts = new int[6];
	private static final int BLUE = 0;
	private static final int BROWN = 1;
	private static final int GREEN = 2;
	private static final int ORANGE = 3;
	private static final int RED = 4;
	private static final int YELLOW = 5;
	
	void reset() {
		for (int i=0;i!=counts.length;i++) {
			counts[i] = 0;
		}
	}
	
	void count(MMColor color) {
		switch (color) {
			case BLUE: counts[BLUE]++; break;
			case BROWN: counts[BROWN]++; break;
			case GREEN: counts[GREEN]++; break;
			case ORANGE: counts[ORANGE]++; break;
			case RED: counts[RED]++; break;
			case YELLOW: counts[YELLOW]++; break;
			case TRACK: return;
			case NONE: return;
		}
	}
	
	MMColor guess() {
		int color = 0;
		int max = 0;
		for (int i=0;i!=counts.length;i++) { 
			int count = counts[i];
			if (i == BROWN) {
				// Lower count for brown
				count = count / 2;
			}
			if (count > 0) {
				String cstr = "?";
				switch (i) {
					case BLUE: cstr =  "blue"; break;
					case BROWN: cstr =  "brown"; break;
					case GREEN: cstr =  "green"; break;
					case ORANGE: cstr =  "orange"; break;
					case RED: cstr =  "red"; break;
					case YELLOW: cstr =  "yellow"; break;
				}
				System.out.println("Found " + count + " for " + cstr);
			}
			if (count > max) {
				max = count;
				color = i;
			}
		}
		if (color == BLUE) {
			return MMColor.BLUE;
		}
		if (color == BROWN) {
			return MMColor.BROWN;
		}
		if (color == GREEN) {
			return MMColor.GREEN;
		}
		if (color == ORANGE) {
			return MMColor.ORANGE;
		}
		if (color == RED) {
			return MMColor.RED;
		}
		if (color == YELLOW) {
			return MMColor.YELLOW;
		}
		return MMColor.TRACK;
	}
}

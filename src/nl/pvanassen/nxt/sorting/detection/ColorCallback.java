package nl.pvanassen.nxt.sorting.detection;

import nl.pvanassen.nxt.sorting.detection.mm.MMColor;
import nl.pvanassen.nxt.sorting.detection.skittle.SkittleColor;

public interface ColorCallback {
	void detected(MMColor color);
	void detected(SkittleColor color);
}

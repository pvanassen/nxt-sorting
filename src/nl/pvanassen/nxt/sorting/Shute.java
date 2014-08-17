package nl.pvanassen.nxt.sorting;

import java.util.LinkedList;
import java.util.Queue;

import lejos.nxt.Motor;

class Shute implements Runnable {
	private Queue<SortEntry> colorQueue = new Queue<SortEntry>();
	private boolean stop;
	// Travel time in ms
	private final int travelTime;

	Shute(int travelTime) throws InterruptedException {
		this.travelTime = travelTime;
		Motor.B.setSpeed(10);
		Motor.B.backward();
		while (!Motor.B.isStalled()) {
			Thread.sleep(10);
			Motor.B.setStallThreshold(1, 1);
		}
		Motor.B.setStallThreshold(10, 60);
		Motor.B.setSpeed(1000);
		Motor.B.rotate(30);
		Motor.B.resetTachoCount();
	}

	@Override
	public void run() {
		try {
			while (!stop) {
				while (colorQueue.isEmpty()) {
					Thread.sleep(50);
				}
				SortEntry entry = (SortEntry) colorQueue.pop();
				System.out.println("Rotating to: " + entry.pos);
				Motor.B.rotateTo(entry.pos);
				long wait = entry.time - System.currentTimeMillis();
				System.out.println("Wait " + wait + " ms");
				Thread.sleep(Math.max(0, wait));
			}
		} catch (InterruptedException e) {
			// Shutting down
			return;
		}
	}

	void detected(MMColor color) {
		colorQueue.push(new SortEntry(color.getAngle(), System
				.currentTimeMillis() + travelTime));
	}

	private static class SortEntry {
		private final int pos;
		private final long time;

		SortEntry(int pos, long time) {
			this.pos = pos;
			this.time = time;
		}
	}
	
	void stop() {
		stop = true;
	}
}

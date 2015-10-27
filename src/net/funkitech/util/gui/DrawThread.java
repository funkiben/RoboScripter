package net.funkitech.util.gui;

public class DrawThread extends Thread {
	
	private final FunkiWindow window;
	private final int updateInterval;
	
	public DrawThread(FunkiWindow window, int updateInterval) {
		this.window = window;
		this.updateInterval = updateInterval;
		
		setDaemon(true);
		
		start();
	}
	
	@Override
	public void run() {
		while (true) {
			window.getCanvas().repaint();
			try {
				Thread.sleep(1000 / updateInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}

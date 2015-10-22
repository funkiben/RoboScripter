package gui;

import javax.swing.JPanel;

public class DrawThread extends Thread {
	
	private final JPanel panel;
	private final int updateInterval;
	
	public DrawThread(JPanel panel, int updateInterval) {
		this.panel = panel;
		this.updateInterval = updateInterval;
		
		setDaemon(true);
		
		start();
	}
	
	@Override
	public void run() {
		while (true) {
			panel.repaint();
			try {
				Thread.sleep(1000 / updateInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}


}

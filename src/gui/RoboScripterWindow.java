package gui;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;

import main.RoboScripter;

public class RoboScripterWindow extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	private final RoboScripter scripter;
	
	public RoboScripterWindow(RoboScripter scripter) {
		super("RoboScripter");
		this.scripter = scripter;
	}
	
	public RoboScripter getScripter() {
		return scripter;
	}
	
	public void draw(Graphics g) {
		
	}
	
	private class DrawCanvas extends JPanel {
		
		private static final long serialVersionUID = 6333028362943156947L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.WHITE);
			draw(g);
		}
		
	}

}

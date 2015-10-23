package net.funkitech.util.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class FunkiWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private final List<FunkiFrame> frames = new ArrayList<FunkiFrame>();

	private final DrawCanvas canvas;
	
	public FunkiWindow(String name, int width, int height) {
		super(name);
		
		canvas = new DrawCanvas();
		add(canvas);
		
		setSize(width, height);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(new EventListener());
		
		setVisible(true);
		
	}
	
	@Override
	public int getWidth() {
		return canvas.getWidth();
	}
	
	@Override
	public int getHeight() {
		return canvas.getHeight();
	}
	
	public DrawCanvas getCanvas() {
		return canvas;
	}
	
	public void addFrame(FunkiFrame...frames) {
		for (FunkiFrame frame : frames) {
			this.frames.add(frame);
		}
	}
	
	public List<FunkiFrame> frames() {
		return frames;
	}
	
	public void draw(Graphics g) {
		g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
		
		for (FunkiFrame frame : frames) {
			frame.draw(g, this);
		}
	}
	
	private void invokeMouseEvents(int cx, int cy, int btn, int id) {
		for (FunkiFrame frame : frames) {
			frame.mouseEvent(cx, cy, btn, this, id);
		}
		
		if (id == 0) {
			onPressMouse(cx, cy, btn);
		} else if (id == 1) {
			onReleaseMouse(cx, cy, btn);
		} else if (id == 2) {
			onMoveMouse(cx, cy);
		}
	}
	
	private void invokeKeyEvents(char key, int id) {
		for (FunkiFrame frame : frames) {
			frame.keyEvent(key, id);
		}
		
		if (id == 0) {
			onPressKey(key);
		} else if (id == 1) {
			onReleaseKey(key);
		}
	}
	
	public void onPressMouse(int x, int y, int button) {
		
	}
	
	public void onReleaseMouse(int x, int y, int button) {
		
	}
	
	public void onMoveMouse(int x, int y) {
		
	}
	
	public void onPressKey(char key) {
		
	}
	
	public void onReleaseKey(char key) {
		
	}
	
	private class EventListener implements MouseListener, MouseMotionListener, KeyListener {

		@Override
		public void mousePressed(MouseEvent e) {
			invokeMouseEvents(e.getX(), e.getY(), e.getButton(), 0);
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			invokeMouseEvents(e.getX(), e.getY(), e.getButton(), 1);
		}
		
		@Override
		public void mouseMoved(MouseEvent e) {
			invokeMouseEvents(e.getX(), e.getY(), -1, 2);
		}
		
		@Override
		public void keyPressed(KeyEvent e) {
			invokeKeyEvents(e.getKeyChar(), 0);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			invokeKeyEvents(e.getKeyChar(), 1);
		}

		@Override
		public void mouseEntered(MouseEvent e) { }

		@Override
		public void mouseExited(MouseEvent e) { }
		
		@Override
		public void mouseClicked(MouseEvent e) { }

		@Override
		public void mouseDragged(MouseEvent e) { }

		@Override
		public void keyTyped(KeyEvent e) { }
		
	}
	
	public class DrawCanvas extends JPanel {
		
		private static final long serialVersionUID = 6333028362943156947L;
		
		public DrawCanvas() {
			EventListener listeners = new EventListener();
			
			addMouseListener(listeners);
			addMouseMotionListener(listeners);
		}

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			setBackground(Color.WHITE);
			draw(g);
		}
		
	}

}

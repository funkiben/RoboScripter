package net.funkitech.util.gui;

import java.awt.Color;
import java.awt.Graphics;

public class FunkiFrame {
	
	private String name;
	private boolean showName = false, showBorder = true;
	private float width, height;
	private float xAlign, yAlign;
	
	public FunkiFrame(String name, float xAlign, float yAlign, float width, float height) {
		this.name = name;
		this.xAlign = xAlign;
		this.yAlign = yAlign;
		this.width = width;
		this.height = height;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean canShowName() {
		return showName;
	}
	
	public void setShowName(boolean showName) {
		this.showName = showName;
	}
	
	public boolean canShowBorder() {
		return showBorder;
	}
	
	public void setShowBorder(boolean showBorder) {
		this.showBorder = showBorder;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public float getXAlignment() {
		return xAlign;
	}
	
	public float getYAlignment() {
		return yAlign;
	}
	
	public void setXAlignment(float x) {
		xAlign = x;
	}
	
	public void setYAlignment(float y) {
		yAlign = y;
	}
	
	public float getWidth() {
		return width;
	}
	
	public float getHeight() {
		return height;
	}
	
	public void setWidth(float width) {
		this.width = width;
	}
	
	public void setHeight(float height) {
		this.height = height;
	}
	
	void draw(Graphics g, FunkiWindow window) {
		int w = getWidth(window), h = getHeight(window), x = getX(window), y = getY(window);
		
		g.setColor(Color.BLACK);
		
		if (showBorder) {
			g.drawRect(x, y, w, h);
		}
		
		if (showName) {
			drawCenteredString(g, name, x + (w / 2), y - 4);
		}
		
		draw(g, x, y, w, h);
		
		
	}
	
	public int getX(FunkiWindow window) {
		return (int) (xAlign * window.getWidth()) - (getWidth(window) / 2);
	}
	
	public int getY(FunkiWindow window) {
		return (int) (yAlign * window.getHeight()) - (getHeight(window) / 2);
	}
	
	public int getWidth(FunkiWindow window) {
		return (int) (width * window.getWidth());
	}
	
	public int getHeight(FunkiWindow window) {
		return (int) (height * window.getHeight());
	}
	
	void mouseEvent(int cx, int cy, int btn, FunkiWindow window, int id) {
		int w = getWidth(window), h = getHeight(window), x = getX(window), y = getY(window);
		
		if (cx > x && cx < x + w && cy > y && cy < y + h) {
			if (id == 0) {
				onPressMouse(cx - x, cy - y, btn);
			} else if (id == 1) {
				onReleaseMouse(cx - x, cy - y, btn);
			} else if (id == 2) {
				onMoveMouse(cx - x, cy - y);
			}
		}
		
	}
	
	
	void keyEvent(char key, int id) {
		if (id == 0) {
			onPressKey(key);
		} else if (id == 1) {
			onReleaseKey(key);
		}
		
	}
	
	public void onPressMouse(int clickX, int clickY, int btn) {
		
	}
	
	public void onReleaseMouse(int clickX, int clickY, int btn) {
		
	}
	
	public void onMoveMouse(int clickX, int clickY) {
		
	}
	
	public void onPressKey(char key) {
		
	}
	
	public void onReleaseKey(char key) {
		
	}
	
	public void draw(Graphics g, int x, int y, int width, int height) {
		
	}
	
	public static void drawCenteredString(Graphics g, String s, int x, int y) {
		g.setColor(Color.BLACK);
		g.drawString(s, x - (g.getFontMetrics().stringWidth(s) / 2), y);
	}

}

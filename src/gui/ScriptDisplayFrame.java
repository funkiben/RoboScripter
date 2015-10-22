package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import main.RoboScripter;
import net.funkitech.util.gui.FunkiFrame;
import script.Script;
import script.instruction.Instruction;

public class ScriptDisplayFrame extends FunkiFrame {

	private static final int CHECKPOINT_SIZE = 15;
	
	private Script script = null;
	private final RoboScripterWindow window;
	
	public ScriptDisplayFrame(RoboScripterWindow window) {
		super("Board", 0.33f, 0.5f, 0.60f, 0.95f);
		this.window = window;
		
		setShowName(true);
		setShowBorder(false);
		
	}
	
	public Script getScript() {
		return script;
	}
	
	public void setScript(Script script) {
		this.script = script;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		int s = Math.min(w, h);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, s, s);
		
		if (script != null) {
			List<Instruction> instructions = script.getInstructions();
			
			for (int i = 0; i < instructions.size(); i++) {
				g.setColor(getColor(i, instructions.size()));
				
				Instruction instruction = instructions.get(i);
				
				int sx = (int) (((double) instruction.getStartX() / RoboScripter.BOARD_SIZE) * s) + x;
				int sy = (int) (((double) instruction.getStartY() / RoboScripter.BOARD_SIZE) * s) + y;
				int ex = (int) (((double) instruction.getEndX() / RoboScripter.BOARD_SIZE) * s) + x;
				int ey = (int) (((double) instruction.getEndY() / RoboScripter.BOARD_SIZE) * s) + y;
				
				String str = instruction.getName() + " (" + (i + 1) + ")";
				
				if (instruction.isMove()) {
					g.drawLine(sx, sy, ex, ey);
					double m = (double) (ey - sy) / (ex - sx);
					double b = sy - (m * sx);
					int half = (ex + sx) / 2;
					
					if (Double.isInfinite(m)) {
						drawCenteredString(g, str, sx, (ey + sy) / 2);
					} else {
						drawCenteredString(g, str, half, (int) ((half * m) + b));
					}
					
				} else {
					g.fillOval(sx - CHECKPOINT_SIZE / 2, sy - CHECKPOINT_SIZE / 2, CHECKPOINT_SIZE, CHECKPOINT_SIZE);
					drawCenteredString(g, str, sx, sy - CHECKPOINT_SIZE / 2);
				}
				
			}
		}
	}
	
	private static void drawCenteredString(Graphics g, String s, int x, int y) {
		g.setColor(Color.BLACK);
		g.drawString(s, x - (g.getFontMetrics().stringWidth(s) / 2), y);
	}
	
	private static Color getColor(int i, int amount) {
		int s = (int) (((double) i / amount) * 255.0);
		return new Color(s, 255 - s, 0);
	}
	
	
}

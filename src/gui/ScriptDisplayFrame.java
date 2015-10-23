package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
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
		if (script == null) {
			drawCenteredString(g, "No Script Selected", x + w/2, y + h/2);
			return;
		}
		
		int s, sx, sy, ex, ey, half;
		double m, b;
		String str;
		Instruction instruction;

		s = Math.min(w, h);
		g.setColor(Color.LIGHT_GRAY);
		g.fillRect(x, y, s, s);

		List<Instruction> instructions = script.getInstructions();

		for (int i = 0; i < instructions.size(); i++) {
			g.setColor(getColor(i, instructions.size()));
			
			instruction = instructions.get(i);
			
			if (instruction == window.getInstructionInspectorFrame().getInstruction()) {
				g.setColor(Color.WHITE);
			}

			sx = (int) (((double) instruction.getStartX() / RoboScripter.BOARD_SIZE) * s) + x;
			sy = (int) (((double) instruction.getStartY() / RoboScripter.BOARD_SIZE) * s) + y;
			ex = (int) (((double) instruction.getEndX() / RoboScripter.BOARD_SIZE) * s) + x;
			ey = (int) (((double) instruction.getEndY() / RoboScripter.BOARD_SIZE) * s) + y;

			str = instruction.getName() + " (" + (i + 1) + ")";

			if (instruction.isMove()) {
				g.drawLine(sx, sy, ex, ey);
				m = (double) (ey - sy) / (ex - sx);
				b = sy - (m * sx);
				half = (ex + sx) / 2;

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

	public void onPressMouse(int cx, int cy, int btn) {
		Instruction i = getInstructionAt(cx, cy);
		if (i != null) {
			window.getInstructionInspectorFrame().setInstruction(i);
		} else {
			window.getInstructionInspectorFrame().clearInstruction();
		}

	}

	private Instruction getInstructionAt(int cx, int cy) {
		int sx, sy, ex, ey, s;

		s = Math.min(getWidth(window), getHeight(window));

		Instruction moveInstruction = null;

		for (Instruction instruction : script.getInstructions()) {
			sx = (int) (((double) instruction.getStartX() / RoboScripter.BOARD_SIZE) * s);
			sy = (int) (((double) instruction.getStartY() / RoboScripter.BOARD_SIZE) * s);
			ex = (int) (((double) instruction.getEndX() / RoboScripter.BOARD_SIZE) * s);
			ey = (int) (((double) instruction.getEndY() / RoboScripter.BOARD_SIZE) * s);

			if (instruction.isMove()) {
				if (isInLine(cx, cy, sx, sy, ex, ey)) {
					moveInstruction = instruction;
				}
			} else {
				if (isInCheckPoint(cx, cy, sx, sy)) {
					return instruction;
				}
			}

		}

		return moveInstruction;
	}

	private static Color getColor(int i, int amount) {
		int s = (int) (((double) i / amount) * 255.0);
		return new Color(s, 255 - s, 0);
	}

	private static boolean isInCheckPoint(int x, int y, int tx, int ty) {
		return Math.pow(tx - x, 2) + Math.pow(ty - y, 2) <= CHECKPOINT_SIZE * CHECKPOINT_SIZE;
	}

	private static boolean isInLine(int x, int y, int sx, int sy, int ex, int ey) {
		double m, b;
		int ly;
		m = (double) (ey - sy) / (ex - sx);
		b = sy - (m * sx);

		ly = (int) ((m * x) + b);

		if (Double.isInfinite(m)) {
			return y > Math.min(ey, sy) && y < Math.max(ey, sy) && Math.abs(x - sx) <= 6;
		} else {
			return x > Math.min(ex, sx) && x < Math.max(ex, sx) && Math.abs(y - ly) <= 6;
		}
	}

}

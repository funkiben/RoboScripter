package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.List;

import main.RoboScripter;
import net.funkitech.util.gui.FunkiFrame;
import script.Script;
import script.instruction.Instruction;

public class BoardFrame extends FunkiFrame {

	private static final int CHECKPOINT_SIZE = 15;

	private Script script = null;
	private final RoboScripterWindow window;

	public BoardFrame(RoboScripterWindow window) {
		super("Board", 0.40f, 0.47f, 0.72f, 0.70f);
		this.window = window;

		setShowName(true);
		setShowBorder(false);

	}

	void setScript(Script script) {
		this.script = script;
		window.getInstructionInspectorFrame().clearInstruction();
	}

	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		if (script == null) {
			drawCenteredString(g, "No Script Selected", x + w / 2, y + h / 2);
			return;
		}

		int s, sx, sy, ex, ey;

		String str;
		Instruction instruction;

		s = getBoardSize();
		x += getBoardX();
		y += getBoardY();

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
//				m = (double) (ey - sy) / (ex - sx);
//				b = sy - (m * sx);
//				half = (ex + sx) / 2;
//
//				if (Double.isInfinite(m)) {
//					drawCenteredString(g, str, sx, (ey + sy) / 2);
//				} else {
//					drawCenteredString(g, str, half, (int) ((half * m) + b));
//				}
				
				drawCenteredString(g, str, getLineCenterX(sx, sy, ex, ey), getLineCenterY(sx, sy, ex, ey));

			} else {
				g.fillOval(sx - CHECKPOINT_SIZE / 2, sy - CHECKPOINT_SIZE / 2, CHECKPOINT_SIZE, CHECKPOINT_SIZE);
				drawCenteredString(g, str, sx, sy - CHECKPOINT_SIZE / 2);
			}

		}

	}
	
	public void onMoveMouse(int cx, int cy) {
		int s, x, y;

		s = getBoardSize();
		x = getBoardX();
		y = getBoardY();
		
		if (window.getInstructionInspectorFrame().isSettingPosition()) {
			window.getInstructionInspectorFrame().getInstruction().setEnd((int) ((double) (cx - x) / s * RoboScripter.BOARD_SIZE), (int) ((double) (cy - y) / s * RoboScripter.BOARD_SIZE));
			window.getInstructionInspectorFrame().updateValues();
		}
	}

	public void onPressMouse(int cx, int cy, int btn) {
		if (window.getInstructionInspectorFrame().isSettingPosition()) {
			if (btn == MouseEvent.BUTTON1) {
				window.getInstructionInspectorFrame().doneSettingPosition();
			} else {
				window.getInstructionInspectorFrame().cancelSettingPosition();
			}
			
			return;
		}

		Instruction i = getInstructionAt(cx, cy);
		if (i != null) {
			window.getInstructionInspectorFrame().setInstruction(i);
		} else {
			window.getInstructionInspectorFrame().clearInstruction();
		}

	}

	private int getBoardSize() {
		return Math.min(getWidth(window), getHeight(window));
	}

	private int getBoardX() {
		int s = getBoardSize(), w = getWidth(window), h = getHeight(window);

		if (s == h) {
			return (w / 2) - (s / 2);
		}

		return 0;
	}

	private int getBoardY() {
		int s = getBoardSize(), w = getWidth(window), h = getHeight(window);

		if (s == w) {
			return (h / 2) - (s / 2);
		}

		return 0;
	}

	private Instruction getInstructionAt(int cx, int cy) {
		int sx, sy, ex, ey, s, x, y;

		s = getBoardSize();
		x = getBoardX();
		y = getBoardY();

		Instruction moveInstruction = null;

		for (Instruction instruction : script.getInstructions()) {
			sx = (int) (((double) instruction.getStartX() / RoboScripter.BOARD_SIZE) * s) + x;
			sy = (int) (((double) instruction.getStartY() / RoboScripter.BOARD_SIZE) * s) + y;
			ex = (int) (((double) instruction.getEndX() / RoboScripter.BOARD_SIZE) * s) + x;
			ey = (int) (((double) instruction.getEndY() / RoboScripter.BOARD_SIZE) * s) + y;
			
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
		
		m = (double) (ey - sy) / (ex - sx);
		
		if (Double.isInfinite(m) || m > 1) {
			m = 1.0 / m;
			b = sx - (m * sy);
			
			return y >= Math.min(ey, sy) && y <= Math.max(ey, sy) && Math.abs(x - ((m * y) + b)) <= 6;
		}
		
		b = sy - (m * sx);

		return x >= Math.min(ex, sx) && x <= Math.max(ex, sx) && Math.abs(y - ((m * x) + b)) <= 6;
	
	}
	
	private static int getLineCenterX(int sx, int sy, int ex, int ey) {
		double m;
		
		m = (double) (ey - sy) / (ex - sx);
		
		if (Double.isInfinite(m) || m > 1) {
			m = 1.0 / m;
			
			double b = sx - (m * sy);
			
			return (int) (m * ((ey + sy) / 2) + b);
		}
		
		return (sx + ex) / 2;
		
	}
	
	private static int getLineCenterY(int sx, int sy, int ex, int ey) {
		double m;
		
		m = (double) (ey - sy) / (ex - sx);
		
		if (Double.isInfinite(m) || m > 1) {
			return (ey + sy) / 2;
		}
		
		double b = sy - (m * sx);
		
		return (int) (m * ((sx + ex) / 2) + b);
		
	}

}

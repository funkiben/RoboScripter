package gui;

import java.awt.Graphics;

import script.Script;
import script.instruction.Instruction;

public class ScriptDisplayer {
	
	private Script script;
	private final int border;
	
	public ScriptDisplayer(Script script, int border) {
		this.script = script;
		this.border = border;
	}
	
	public Script getScript() {
		return script;
	}
	
	public void setScript(Script script) {
		this.script = script;
	}
	
	public void draw(Graphics g) {
		int w = Math.min(g.getClipBounds().width, g.getClipBounds().height);
		g.fillRect(border, border, w - border, w - border);
		
		for (Instruction i : script.getInstructions()) {
			i.drawVisual(g, border + i.getStartX(), border + i.getStartY());
		}
		
		
	}
	
}

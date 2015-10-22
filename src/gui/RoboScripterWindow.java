package gui;

import main.RoboScripter;
import net.funkitech.util.gui.FunkiWindow;

public class RoboScripterWindow extends FunkiWindow {
	
	private static final long serialVersionUID = 1L;
	
	private final RoboScripter scripter;
	private final ScriptDisplayFrame scriptDisplayFrame;
	private final InstructionInspectorFrame instructionInspectorFrame;
	private final ScriptDBFrame scriptDBFrame;
	
	
	public RoboScripterWindow(RoboScripter scripter) {
		super("RoboScripter", 1200, 800);
		this.scripter = scripter;
		
		scriptDisplayFrame = new ScriptDisplayFrame(this);
		instructionInspectorFrame = new InstructionInspectorFrame(this);
		scriptDBFrame = new ScriptDBFrame(this);
		
		addFrame(scriptDisplayFrame, instructionInspectorFrame, scriptDBFrame);
		
		new DrawThread(getCanvas(), 30);
	}
	
	public ScriptDisplayFrame getScriptDisplayFrame() {
		return scriptDisplayFrame;
	}
	
	public InstructionInspectorFrame getInstructionInspectorFrame() {
		return instructionInspectorFrame;
	}
	
	public RoboScripter getScripter() {
		return scripter;
	}

}

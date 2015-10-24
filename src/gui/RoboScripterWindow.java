package gui;

import main.RoboScripter;
import net.funkitech.util.gui.FunkiWindow;
import script.Script;

public class RoboScripterWindow extends FunkiWindow {
	
	private static final long serialVersionUID = 1L;
	
	private final RoboScripter scripter;
	private final ScriptDisplayFrame scriptDisplayFrame;
	private final InstructionInspectorFrame instructionInspectorFrame;
	private final ScriptDBFrame scriptDBFrame;
	private final ScriptPropertiesFrame scriptPropertiesFrame;
	
	
	public RoboScripterWindow(RoboScripter scripter) {
		super("RoboScripter", 1200, 900);
		this.scripter = scripter;
		
		scriptDisplayFrame = new ScriptDisplayFrame(this);
		instructionInspectorFrame = new InstructionInspectorFrame(this);
		scriptDBFrame = new ScriptDBFrame(this);
		scriptPropertiesFrame = new ScriptPropertiesFrame(this);
		
		addFrame(scriptDisplayFrame, instructionInspectorFrame, scriptDBFrame, scriptPropertiesFrame);
		
		new DrawThread(getCanvas(), 30);
	}
	
	public ScriptDisplayFrame getScriptDisplayFrame() {
		return scriptDisplayFrame;
	}
	
	public InstructionInspectorFrame getInstructionInspectorFrame() {
		return instructionInspectorFrame;
	}
	
	public ScriptPropertiesFrame getScriptPropertiesFrame() {
		return scriptPropertiesFrame;
	}
	
	public ScriptDBFrame getScriptDBFrame() {
		return scriptDBFrame;
	}
	
	public RoboScripter getScripter() {
		return scripter;
	}
	
	public void setScript(Script script) {
		scriptDisplayFrame.setScript(script);
		scriptPropertiesFrame.setScript(script);
	}

}

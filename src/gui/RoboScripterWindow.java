package gui;

import main.RoboScripter;
import net.funkitech.util.gui.DrawThread;
import net.funkitech.util.gui.FunkiWindow;
import script.Script;

public class RoboScripterWindow extends FunkiWindow {
	
	private static final long serialVersionUID = 1L;
	
	private Script script;
	
	private final RoboScripter scripter;
	private final BoardFrame boardFrame;
	private final InstructionInspectorFrame instructionInspectorFrame;
	private final ScriptDBFrame scriptDBFrame;
	private final ScriptPropertiesFrame scriptPropertiesFrame;
	private final ToolsFrame toolsFrame;
	
	
	public RoboScripterWindow(RoboScripter scripter) {
		super("RoboScripter", 1200, 900);
		this.scripter = scripter;
		
		boardFrame = new BoardFrame(this);
		instructionInspectorFrame = new InstructionInspectorFrame(this);
		scriptDBFrame = new ScriptDBFrame(this);
		scriptPropertiesFrame = new ScriptPropertiesFrame(this);
		toolsFrame = new ToolsFrame(this);
		
		addFrame(boardFrame, instructionInspectorFrame, scriptDBFrame, scriptPropertiesFrame, toolsFrame);
		
		new DrawThread(this, 30);
	}
	
	public BoardFrame getBoardFrame() {
		return boardFrame;
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
		this.script = script;
		boardFrame.setScript(script);
		scriptPropertiesFrame.setScript(script);
	}
	
	public Script getScript() {
		return script;
	}

}

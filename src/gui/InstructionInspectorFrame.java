package gui;

import net.funkitech.util.gui.FunkiFrame;
import script.instruction.Instruction;

public class InstructionInspectorFrame extends FunkiFrame {
	
	private Instruction instruction = null;
	private final RoboScripterWindow window;
	
	public InstructionInspectorFrame(RoboScripterWindow window) {
		super("Instruction", 0.83f, 0.33f, 0.30f, 0.60f);
		this.window = window;	
		
		setShowName(true);
		
	}
	
	public Instruction getInstruction() {
		return instruction;
	}
	
	public void setInstruction(Instruction instruction) {
		this.instruction = instruction;
	}

}

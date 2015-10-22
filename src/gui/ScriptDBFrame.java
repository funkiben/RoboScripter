package gui;

import net.funkitech.util.gui.FunkiFrame;

public class ScriptDBFrame extends FunkiFrame {

	private final RoboScripterWindow window;
	
	public ScriptDBFrame(RoboScripterWindow window) {
		super("File", 0.83f, 0.83f, 0.30f, 0.30f);
		this.window = window;
		
		setShowName(true);
	}

}

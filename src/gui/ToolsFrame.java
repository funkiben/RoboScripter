package gui;

import net.funkitech.util.gui.FunkiFrame;

public class ToolsFrame extends FunkiFrame {

	private final RoboScripterWindow window;
	
	public ToolsFrame(RoboScripterWindow window) {
		super("Tools", 0.33f, 0.925f, 0.60f, 0.10f);
		setShowName(true);
		
		this.window = window;
		
	}

}

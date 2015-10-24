package gui;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import net.funkitech.util.gui.FunkiFrame;
import script.Script;
import script.ScriptDB;

public class ScriptPropertiesFrame extends FunkiFrame {

	private final RoboScripterWindow window;
	private final ScriptDB scriptDB;
	private Script script;
	
	private final JButton saveButton;
	private final JButton exportButton;
	private final JTextField nameInput;
	
	public ScriptPropertiesFrame(RoboScripterWindow window) {
		super("Script Properties", 0.33f, 0.075f, 0.60f, 0.1f);
		setShowName(true);
		
		this.window = window;
		scriptDB = window.getScripter().getScriptDB();
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(new SaveButtonActionListener());
		window.getCanvas().add(saveButton);
		
		exportButton = new JButton("Export");
		exportButton.addActionListener(new ExportButtonActionListener());
		window.getCanvas().add(exportButton);
		
		nameInput = new JTextField();
		nameInput.addCaretListener(new NameInputEventListener());
		window.getCanvas().add(nameInput);
		
		setScript(null);
		
	}
	
	public void setScript(Script script) {
		this.script = script;
		if (script != null) {
			setName("Script - " + script.getName());
			saveButton.setVisible(true);
			exportButton.setVisible(true);
			nameInput.setVisible(true);
			nameInput.setText(script.getName());
		} else {
			setName("Script");
			saveButton.setVisible(false);
			exportButton.setVisible(false);
			nameInput.setVisible(false);
		}
	}
	
	public Script getScript() {
		return script;
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		if (script == null) {
			drawCenteredString(g, "No Script Selected", x + w/2, y + h/2);
			return;
		}
		
		saveButton.setLocation(x + w/4 - 50, y + h/2 - 10);
		saveButton.setSize(100, 20);
		
		exportButton.setLocation(x + w/2 - 50, y + h/2 - 10);
		exportButton.setSize(100, 20);
		
		nameInput.setLocation(x + 3*(w/4) - 50, y + h/2 - 10);
		nameInput.setSize(100, 20);
		
	}
	
	private void changeName(String name) {
		script.setName(name);
		setName("Script - " + name);
	}
	
	@Override
	public void onPressMouse(int cx, int cy, int btn) {
		changeName(nameInput.getText());
	}
	
	class SaveButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			scriptDB.save(script);
		}
		
	}
	
	class ExportButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			window.getScripter().compile(script);
		}
		
	}
	
	class NameInputEventListener implements CaretListener {

		@Override
		public void caretUpdate(CaretEvent e) {
			changeName(nameInput.getText());
		}
		
	}

}

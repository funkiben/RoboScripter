package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;

import net.funkitech.util.gui.FunkiFrame;
import script.Script;
import script.ScriptDB;

public class ScriptDBFrame extends FunkiFrame {

	private final RoboScripterWindow window;
	private final ScriptDB scriptDB;
	
	private final JScrollPane scrollPane;
	private final JList<Script> scriptsList;
	private final JButton refreshButton;
	private final JButton loadButton;
	
	public ScriptDBFrame(RoboScripterWindow window) {
		super("Scripts", 0.83f, 0.83f, 0.30f, 0.30f);
		this.window = window;
		scriptDB = window.getScripter().getScriptDB();
		
		setShowName(true);
		
		scrollPane = new JScrollPane();
		scriptsList = new JList<Script>();
		scriptsList.setBorder(new LineBorder(Color.lightGray));
		scriptsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scriptsList.setVisibleRowCount(4);
		updateModel();
		
		scrollPane.setViewportView(scriptsList);
		window.getCanvas().add(scrollPane);
		
		

		
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new RefreshButtonActionListener());
		window.getCanvas().add(refreshButton);
		
		loadButton = new JButton("Load Script");
		loadButton.addActionListener(new LoadButtonActionListener());
		window.getCanvas().add(loadButton);
		
	}
	
	private void updateModel() {
		DefaultListModel<Script> model = new DefaultListModel<Script>();

		for (Script script : scriptDB.getScripts()) {
			model.addElement(script);
		}
		
		scriptsList.setModel(model);
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {

		scrollPane.setSize(w - 20, h - 50);
		scrollPane.setLocation(x + 10, y + 10);
		scriptsList.setSize(scrollPane.getSize());
		
		refreshButton.setLocation(x + 20, y + h - 25);
		refreshButton.setSize((w/2) - 40, 20);
		
		loadButton.setLocation(x + (w/2) + 20, y + h - 25);
		loadButton.setSize((w/2) - 40, 20);
		
	}
	
	class RefreshButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			scriptDB.load();
			updateModel();
		}
		
	}
	
	class LoadButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!scriptsList.isSelectionEmpty()) {
				if (window.getScriptDisplayFrame().getScript() != null) {
					scriptDB.save(window.getScriptDisplayFrame().getScript());
				}
				
				Script script = scriptsList.getModel().getElementAt(scriptsList.getLeadSelectionIndex());
				window.setScript(script);
			}
		}
		
	}
	
}

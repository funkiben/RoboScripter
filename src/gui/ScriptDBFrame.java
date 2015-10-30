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
	private final JButton addButton;
	private final JButton duplicateButton;

	public ScriptDBFrame(RoboScripterWindow window) {
		super("Scripts", 0.90f, 0.83f, 0.18f, 0.30f);
		this.window = window;
		scriptDB = window.getScripter().getScriptDB();
		
		setShowName(true);
		
		scrollPane = new JScrollPane();
		scriptsList = new JList<Script>();
		scriptsList.setBorder(new LineBorder(Color.lightGray));
		scriptsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		scriptsList.setVisibleRowCount(4);
		updateList();
		
		scrollPane.setViewportView(scriptsList);
		window.getCanvas().add(scrollPane);
		
		

		
		refreshButton = new JButton("Refresh");
		refreshButton.addActionListener(new RefreshButtonActionListener());
		window.getCanvas().add(refreshButton);
		
		loadButton = new JButton("Load");
		loadButton.addActionListener(new LoadButtonActionListener());
		window.getCanvas().add(loadButton);
		
		addButton = new JButton("Add");
		addButton.addActionListener(new AddButtonActionListener());
		window.getCanvas().add(addButton);
		
		duplicateButton = new JButton("Duplicate");
		duplicateButton.addActionListener(new DuplicateButtonActionListener());
		window.getCanvas().add(duplicateButton);
		
	}
	
	public void updateList() {
		DefaultListModel<Script> model = new DefaultListModel<Script>();

		for (Script script : scriptDB.getScripts()) {
			model.addElement(script);
		}
		
		scriptsList.setModel(model);
		
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		
		if (scrollPane.getWidth() != w - 20 || scrollPane.getHeight() != h - 80) {
			scrollPane.setSize(w - 20, h - 80);
			scriptsList.setSize(scrollPane.getSize());
		}
		
		if (scrollPane.getX() != x + 10 || scrollPane.getY() != y + 10) {
			scrollPane.setLocation(x + 10, y + 10);
		}

		
		refreshButton.setLocation(x + 10, y + h - 50);
		refreshButton.setSize((w/2) - 20, 20);
		
		loadButton.setLocation(x + (w/2) + 10, y + h - 50);
		loadButton.setSize((w/2) - 20, 20);
		
		addButton.setLocation(x + 10, y + h - 25);
		addButton.setSize((w/2) - 20, 20);
		
		duplicateButton.setLocation(x + (w/2) + 10, y + h - 25);
		duplicateButton.setSize((w/2) - 20, 20);
		
	}
	
	class RefreshButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			scriptDB.load();
			window.setScript(null);
			updateList();

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
	
	class AddButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Script script = new Script("Untitled");
			scriptDB.save(script);
			window.setScript(script);
			updateList();
		}
		
	}
	
	class DuplicateButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int i = scriptsList.getSelectedIndex();
			if (i != -1) {
				if (window.getScriptDisplayFrame().getScript() != null) {
					scriptDB.save(window.getScriptDisplayFrame().getScript());
				}
				
				Script script = scriptDB.duplicate(scriptsList.getModel().getElementAt(i));
				updateList();
				window.setScript(script);
			}
		}
		
	}
	
}

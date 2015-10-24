package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.funkitech.util.gui.FunkiFrame;
import script.instruction.GUISettingField;
import script.instruction.Instruction;

public class InstructionInspectorFrame extends FunkiFrame {
	
	private Instruction instruction = null;
	private final RoboScripterWindow window;
	private final List<Component> inputs = new ArrayList<Component>();
	
	public InstructionInspectorFrame(RoboScripterWindow window) {
		super("Instruction", 0.83f, 0.33f, 0.30f, 0.60f);
		this.window = window;	
		
		setShowName(true);
		
	}
	
	public Instruction getInstruction() {
		return instruction;
	}
	
	public void setInstruction(Instruction instruction) {
		clearInstruction();
		
		setName("Instruction " + instruction.getName());
		this.instruction = instruction;

		List<GUISettingField> settings = instruction.getGUISettings();
		for (GUISettingField f : settings) {
			if (!f.getData().canEdit()) {
				inputs.add(null);
				continue;
			}
			
			if (f.getField().getType() == Double.TYPE || f.getField().getType() == Integer.TYPE) {
				JTextField input = new JTextField();
				input.setText(f.getValue().toString());
				TextBoxEventListener el = new TextBoxEventListener(input, f);
				input.addCaretListener(el);
				input.addActionListener(el);
				inputs.add(input);
				window.getCanvas().add(input);
				input.setSize(100, 20);
			} else if (f.getField().getType() == Boolean.TYPE) {
				JCheckBox input = new JCheckBox();
				input.setSelected((boolean) f.getValue());
				input.addChangeListener(new CheckBoxEventListener(input, f));
				inputs.add(input);
				window.getCanvas().add(input);
				
			} else {
				inputs.add(null);
			}
		}
	}
	
	public void clearInstruction() {
		setName("Instruction");
		instruction = null;
		for (Component input : inputs) {
			if (input == null) {
				continue;
			}
			window.getCanvas().remove(input);
		}
		inputs.clear();
	}
	
	@Override
	public void draw(Graphics g, int x, int y, int w, int h) {
		if (instruction == null) {
			drawCenteredString(g, "No Instruction Selected", x + w/2, y + h/2);
			return;
		}
		
		List<GUISettingField> settings = instruction.getGUISettings();
			
		int i, sx, sy;
		String str;
		
		i = 0;
			
		for (GUISettingField f : settings) {
			str = f.getName() + ": ";
			sx = x + 10;
			sy = y + 20 + (i * 30);
			if (f.getData().canEdit()) {
				inputs.get(i).setLocation(sx + g.getFontMetrics().stringWidth(str), sy - 15);
				inputs.get(i).setSize(100, 20);
			} else {
				str += f.getValue().toString();
			}
			g.drawString(str, sx, sy);
			i++;
		}

		
	}
	
	@Override
	public void onPressMouse(int cx, int cy, int btn) {
		if (instruction == null) {
			return;
		}
		
		for (Component input : inputs) {
			if (input == null) continue;
			if (input instanceof JTextField) {
				input.getListeners(ActionListener.class)[0].actionPerformed(null);
			}
		}
	}

	private static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	private static boolean isDouble(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
	
	class TextBoxEventListener implements CaretListener, ActionListener {
		
		private final JTextField input;
		private final GUISettingField setting;
		
		public TextBoxEventListener(JTextField input, GUISettingField field) {
			this.input = input;
			this.setting = field;
		}

		@Override
		public void caretUpdate(CaretEvent e) {
			String str = input.getText();
			
			if (str.isEmpty()) {
				str = "0";
			}
			
			if (setting.getField().getType() == Integer.TYPE) {
				
				if (!isInteger(str)) {
					input.setBackground(Color.red);
					
				} else {
					
					int i = Integer.parseInt(str);
			
					i = (int) Math.max(i, setting.getData().min());
					i = (int) Math.min(i, setting.getData().max());
					
					setting.setValue(i);
					input.setBackground(Color.white);
					
				}
				
			} else if (setting.getField().getType() == Double.TYPE) {
				
				if (!isDouble(str)) {
					input.setBackground(Color.red);
				} else {
					
					double i = Double.parseDouble(str);
					
					i = Math.max(i, setting.getData().min());
					i = Math.min(i, setting.getData().max());
					
					setting.setValue(i);
					input.setBackground(Color.white);
				}
			}
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			input.setText(setting.getValue().toString());
		}
		
	}
	
	class CheckBoxEventListener implements ChangeListener {
		
		private final JCheckBox input;
		private final GUISettingField setting;
		
		public CheckBoxEventListener(JCheckBox input, GUISettingField field) {
			this.input = input;
			this.setting = field;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			setting.setValue(input.isSelected());
		}
		
	}
	

}

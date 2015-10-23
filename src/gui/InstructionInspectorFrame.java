package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.funkitech.util.gui.FunkiFrame;
import script.instruction.GUISetting;
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

		Map<Field, GUISetting> settings = instruction.getGUISettings();
		for (Field f : settings.keySet()) {
			if (!settings.get(f).canEdit()) {
				inputs.add(null);
				continue;
			}
			
			if (f.getType() == Double.TYPE || f.getType() == Integer.TYPE) {
				JTextField input = new JTextField();
				input.setText(getValue(f).toString());
				TextBoxEventListener el = new TextBoxEventListener(input, settings.get(f), f);
				input.addCaretListener(el);
				input.addActionListener(el);
				inputs.add(input);
				window.getCanvas().add(input);
				input.setSize(100, 20);
			} else if (f.getType() == Boolean.TYPE) {
				JCheckBox input = new JCheckBox();
				input.setSelected((boolean) getValue(f));
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
		
		Map<Field, GUISetting> settings = instruction.getGUISettings();
			
		int i, sx, sy;
		String str;
		
		i = 0;
			
		for (Field f : settings.keySet()) {
			str = f.getName() + ": ";
			sx = x + 10;
			sy = y + 20 + (i * 30);
			if (settings.get(f).canEdit()) {
				inputs.get(i).setLocation(sx + g.getFontMetrics().stringWidth(str), sy - 15);
				inputs.get(i).setSize(100, 20);
			} else {
				str += getValue(f);
			}
			g.drawString(str, sx, sy);
			i++;
		}

		
	}
	
	public Object getValue(Field f) {
		try {
			return f.get(instruction);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setValue(Field f, Object val) {
		try {
			if (f.getType() == Integer.TYPE) {
				f.setInt(instruction, (int) val);
			} else if (f.getType() == Double.TYPE) {
				f.setDouble(instruction, (double) val);
			} else if (f.getType() == Boolean.TYPE) {
				f.setBoolean(instruction, (boolean) val);
			}
			instruction.onChangeField(f, val);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
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
		private final GUISetting settingData;
		private final Field field;
		
		public TextBoxEventListener(JTextField input, GUISetting settingData, Field field) {
			this.input = input;
			this.settingData = settingData;
			this.field = field;
		}

		@Override
		public void caretUpdate(CaretEvent e) {
			String str = input.getText();
			
			if (str.isEmpty()) {
				str = "0";
			}
			
			if (field.getType() == Integer.TYPE) {
				
				if (!isInteger(str)) {
					input.setBackground(Color.red);
					
				} else {
					
					int i = Integer.parseInt(str);
			
					i = (int) Math.max(i, settingData.min());
					i = (int) Math.min(i, settingData.max());
					
					setValue(field, i);
					input.setBackground(Color.white);
					
				}
				
			} else if (field.getType() == Double.TYPE) {
				
				if (!isDouble(str)) {
					input.setBackground(Color.red);
				} else {
					
					double i = Double.parseDouble(str);
					
					i = Math.max(i, settingData.min());
					i = Math.min(i, settingData.max());
					
					setValue(field, i);
					input.setBackground(Color.white);
				}
			}
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			input.setText(getValue(field).toString());
		}
		
	}
	
	class CheckBoxEventListener implements ChangeListener {
		
		private final JCheckBox input;
		private final Field field;
		
		public CheckBoxEventListener(JCheckBox input, Field field) {
			this.input = input;
			this.field = field;
		}

		@Override
		public void stateChanged(ChangeEvent e) {
			setValue(field, input.isSelected());
		}
		
	}
	

}

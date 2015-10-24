package script.instruction;

import java.lang.reflect.Field;

public class GUISettingField {
	
	private final GUISetting data;
	private final Field field;
	private final Object inst;
	private final Instruction instruction;
	
	public GUISettingField(GUISetting data, Field field, Object inst, Instruction instruction) {
		this.data = data;
		this.field = field;
		this.inst = inst;
		this.instruction = instruction;
	}
	
	public String getName() {
		return inst != instruction ? inst.getClass().getSimpleName() + "_" + field.getName() : field.getName();
	}
	
	public Object getValue() {
		try {
			return field.get(inst);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void setValue(Object val) {
		try {
			if (field.getType() == Integer.TYPE) {
				field.setInt(inst, (int) val);
			} else if (field.getType() == Double.TYPE) {
				field.setDouble(inst, (double) val);
			} else if (field.getType() == Boolean.TYPE) {
				field.setBoolean(inst, (boolean) val);
			}
			instruction.onChangeSetting(this, val);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	public GUISetting getData() {
		return data;
	}
	
	public Field getField() {
		return field;
	}
	
	public Object getObject() {
		return inst;
	}
	
	public Instruction getInstruction() {
		return instruction;
	}

}

package script.instruction;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import script.Script;

public abstract class Instruction implements Serializable {
	
	private static final long serialVersionUID = 2892439625112243052L;
	
	private final Script script;
	private final String name;
	private Instruction prevInstruction = null;
	@GUISetting(min = 0, max = 10000)
	private int endX, endY;
	private boolean isMove = false;
	
	public Instruction(Script script, String name, int endX, int endY) {
		this.script = script;
		this.name = name;
		this.endX = endX;
		this.endY = endY;
	}
	
	public Script getScript() {
		return script;
	}
	
	public String getName() {
		return name;
	}
	
	public int getStartX() {
		return prevInstruction != null ? prevInstruction.endX : endX;
	}
	
	public int getStartY() {
		return prevInstruction != null ? prevInstruction.endY : endY;
	}
	
	public void setPrevInstruction(Instruction instruction) {
		isMove = instruction.getEndX() != endX || instruction.getEndY() != endY;
		prevInstruction = instruction;
	}
	
	public int getEndX() {
		return !isMove() && prevInstruction != null ? prevInstruction.endX : endX;
	}
	
	public int getEndY() {
		return !isMove() && prevInstruction != null ? prevInstruction.endY : endY;
	}
	
	public void setEnd(int x, int y) {
		endX = x;
		endY = y;
	}
	
	public boolean isMove() {
		return isMove;
	}
	
	public abstract List<String> getImports();
	public abstract List<String> getGlobalVars();
	public abstract List<String> getInitCode();
	public abstract List<String> getRunCode();
	public abstract void onCreate(int x, int y);
	public abstract void onChangeSetting(GUISettingField field, Object newVal);
	
	protected static List<String> list(String...code) {
		return Arrays.asList(code);
	}
	
	public List<GUISettingField> getGUISettings() {
		try {
			return getGUISettings(this, this);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static List<GUISettingField> getGUISettings(Object inst, Instruction instruction) throws IllegalArgumentException, IllegalAccessException {
		List<GUISettingField> list = new ArrayList<GUISettingField>();
		Field[] fields = inst.getClass().getDeclaredFields();
		
		if (inst == instruction) {
			int i = fields.length;
			fields = Arrays.copyOf(fields, Instruction.class.getDeclaredFields().length + i);
			for (Field f : Instruction.class.getDeclaredFields()) {
				fields[i++] = f;
			}
		}
		
		for (Field field : fields) {
			field.setAccessible(true);
			GUISetting setting = field.getAnnotation(GUISetting.class);
			if (setting != null) {
				if (!Object.class.isAssignableFrom(field.getType())) {
					list.add(new GUISettingField(setting, field, inst, instruction));
				} else {
					Object obj = field.get(inst);
					if (obj != null) {
						list.addAll(getGUISettings(obj, instruction));
					}
				}
			}
			
		}
		
		return list;
	}

}

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
	private int startX, startY, endX, endY;
	
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
		return startX;
	}
	
	public int getStartY() {
		return startY;
	}
	
	public int getEndX() {
		return endX;
	}
	
	public int getEndY() {
		return endY;
	}
	
	public void setStart(int x, int y) {
		startX = x;
		startY = y;
	}
	
	public void setEnd(int x, int y) {
		endX = x;
		endY = y;
	}
	
	public boolean isMove() {
		return startX != endX || startY != endY;
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
		Field[] fields = inst.getClass().getFields();
		
		for (Field field : fields) {
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

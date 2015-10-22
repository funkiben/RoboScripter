package script.instruction;

import java.awt.Graphics;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import script.Script;

public abstract class Instruction implements Serializable {
	
	private static final long serialVersionUID = 2892439625112243052L;
	
	private final Script script;
	private final String name;
	private int startX, startY, endX, endY;
	
	public Instruction(Script script, String name, int startX, int startY, int endX, int endY) {
		this.script = script;
		this.name = name;
		this.startX = startX;
		this.startY = startY;
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
	
	public abstract List<String> getImports();
	public abstract List<String> getGlobalVars();
	public abstract List<String> getInitCode();
	public abstract List<String> getRunCode();
	public abstract void drawVisual(Graphics graphics, int x, int y);
	public abstract void onCreate(int x, int y);
	
	private static List<String> list(String...code) {
		return Arrays.asList(code);
	}
	
	public Map<Field,GUISetting> getGUISettings() {
		return getGUISettings(getClass());
	}
	
	private static Map<Field,GUISetting> getGUISettings(Class<?> clazz) {
		Map<Field,GUISetting> map = new HashMap<Field,GUISetting>();
		Field[] fields = clazz.getFields();
		
		for (Field field : fields) {
			GUISetting setting = field.getAnnotation(GUISetting.class);
			if (setting != null) {
				if (Object.class.isAssignableFrom(field.getType())) {
					map.put(field, setting);
				} else {
					map.putAll(getGUISettings(field.getType()));
				}
			}
			
		}
		
		return map;
	}

}

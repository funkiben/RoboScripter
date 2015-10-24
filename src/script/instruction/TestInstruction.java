package script.instruction;

import java.util.List;

import script.Script;

public class TestInstruction extends Instruction {

	private static final long serialVersionUID = -6336451377740513969L;
	
	@GUISetting(canEdit = false)
	public double nonEditable = 4;
	@GUISetting
	public int integer;
	@GUISetting
	public double decimal;
	@GUISetting
	public boolean bool = false;
	
	public TestInstruction(Script script, int endX, int endY) {
		super(script, "Test", endX, endY);
	}

	@Override
	public List<String> getImports() {
		return list();
	}

	@Override
	public List<String> getGlobalVars() {
		return list();
	}

	@Override
	public List<String> getInitCode() {
		return list();
	}

	@Override
	public List<String> getRunCode() {
		return list();
	}

	@Override
	public void onCreate(int x, int y) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onChangeSetting(GUISettingField setting, Object newVal) {
		if (setting.getName().equals("integer") || setting.getName().equals("decimal")) {
			nonEditable = integer * decimal;
		}
		
	}

}

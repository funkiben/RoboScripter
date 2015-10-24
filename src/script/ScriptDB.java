package script;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ScriptDB {
	
	private final File directory;
	private final List<Script> scripts = new ArrayList<Script>();
	
	public ScriptDB() {
		directory = new File("scripts");
		directory.mkdir();
		
		load();
	}
	
	public void add(Script script) {
		scripts.add(script);
	}
	
	public void delete(Script script) {
		scripts.remove(script);
		getFile(script).delete();
	}
	
	public void delete(String name) {
		delete(getScript(name));
	}
	
	public List<Script> getScripts() {
		return scripts;
	}
	
	public Script getScript(String name) {
		for (Script script : scripts) {
			if (script.getName().equals(name)) {
				return script;
			}
		}
		
		return null;
	}
	
	public File getFile(Script script) {
		return new File(directory, script.getName() + ".roboscript");
	}
	
	public void load() {
		Script script;
		
		scripts.clear();
		
		for (File file : directory.listFiles()) {
			script = read(file);
			
			if (script != null) {
				scripts.add(script);
			}
		}
	}
	
	private Script read(File file) {
		FileInputStream fis;
		ObjectInputStream ois;
		Object raw;
		
		try {
			fis = new FileInputStream(file);
			ois = new ObjectInputStream(fis);
			
			raw = ois.readObject();
			
			ois.close();
			
			if (raw instanceof Script) {
				return (Script) raw;
			}
			
		} catch (IOException | ClassNotFoundException e) { }
		
		return null;
	}
	
	public void saveAll() {
		for (Script script : scripts) {
			save(script);
		}
	}
	
	public void save(Script script) {
		if (!scripts.contains(script)) {
			scripts.add(script);
		}
		
		File file = getFile(script);
		
		FileOutputStream fos;
		ObjectOutputStream oos;
		
		try {
			file.createNewFile();
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			
			oos.writeObject(script);
			
			oos.flush();
			
			oos.close();
			
		} catch (IOException e) { }
		
	}
	
	
	
}

package script.compiler;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import script.Script;
import script.instruction.Instruction;

public class ScriptCompiler {
	
	private final Script script;
	
	public ScriptCompiler(Script script) {
		this.script = script;
	}
	
	public Script getScript() {
		return script;
	}
	
	public void compile() {
		try {
			File dir = new File("exports");
			dir.mkdir();
			File file = new File(dir, script.getName() + ".java");
			
			file.createNewFile();
			
			FileOutputStream fos = new FileOutputStream(file);
			PrintWriter writer = new PrintWriter(fos);
			
			writeCode(writer);
			
			writer.flush();
			writer.close();
			
		} catch (IOException e) {
			
		}
	}
	
	private Set<String> getImports() {
		Set<String> set = new HashSet<String>();
		
		for (Instruction instruction : script.getInstructions()) {
			for (String imprt : instruction.getImports()) {
				set.add(imprt);
			}
		}
		
		return set;
	}
	
	private Set<String> getGlobalVars() {
		Set<String> set = new HashSet<String>();
		
		for (Instruction instruction : script.getInstructions()) {
			for (String var : instruction.getGlobalVars()) {
				set.add(var);
			}
		}
		
		return set;
	}
	
	private Set<String> getInitCode() {
		Set<String> set = new HashSet<String>();
		
		for (Instruction instruction : script.getInstructions()) {
			for (String code : instruction.getInitCode()) {
				set.add(code);
			}
		}
		
		return set;
	}
	
	private void writeCode(PrintWriter writer) {
		
		// package
		writer.println("package com.qualcomm.ftcrobotcontroller.opmodes;");
		

		
		// imports
		for (String dependency : getImports()) {
			writer.println("import " + dependency);
		}
		
		writer.println();
		

		
		// class header
		writer.println("public class " + script.getName() + " extends LinearOpMode {");
		writer.println();
		
		
		
		// global vars
		for (String var : getGlobalVars()) {
			writer.println("\t" + var);
		}
		writer.println();
		

		
		// runOpMode method
		writer.println("\t@Override\n\tpublic void runOpMode() throws InterruptedException {");
		
		// init code
		writer.println("\n\t\t// Init Code");
		for (String code : getInitCode()) {
			writer.println("\t\t" + code);
		}
		
		// run code
		writer.println("\n\t\t// Run Code");
		
		List<Instruction> instructions = script.getInstructions();
		for (int i = 0; i < instructions.size(); i++) {
			writer.println("\t\t" + "// Instruction " + (i + 1) + " - " + instructions.get(i).getName());
			
			for (String code : instructions.get(i).getRunCode()) {
				writer.println("\t\t" + code);
			}
		}
		
		writer.println("\n\t}\n}");
		
		
		
	}
	
	
}

package com.altHealth.Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.ManagedBean;

@ManagedBean
public class ShellScriptRunner {
	
	public final static String DIR = "C:\\Users\\Ninja\\git\\unisa-project\\scripts\\";

	public String scriptRunner(String script, String... args) {
		ProcessBuilder processBuilder = new ProcessBuilder();
		
		Map<String, String> environmentVariables = processBuilder.environment();
		boolean newEnvVar = setEnvVar(environmentVariables, args);
		List<String> cmdList = new ArrayList<String>();
		// -- Linux --
		// Run a shell command
//		processBuilder.command("bash", "-c", "dir");

		// Run a shell script
		// processBuilder.command("dir/hello.sh");

		// -- Windows --
		// Run a command
		// processBuilder.command("cmd.exe", "/c", "dir");

		// Run a bat file
//		processBuilder.command("cmd.exe", "/c", "dir/test.bat");
		
		boolean isWindows = System.getProperty("os.name")
				  .toLowerCase().startsWith("windows");
		if (isWindows) {
			// -- Windows --
			cmdList.add("cmd.exe");
			cmdList.add("/C");
//			cmdList.add("start");
			if(newEnvVar) {
				cmdList.add("start");
			}
			addEnvVar(newEnvVar, environmentVariables, cmdList);
			cmdList.add(DIR+script+".bat");
			
			//Use for debug
//			processBuilder.command("cmd.exe", "/C", "start", DIR+script+".bat");
			processBuilder.command(cmdList);
		} else {
			// -- Linux --
			cmdList.add(DIR+script+".sh");
			addEnvVar(newEnvVar, environmentVariables, cmdList);
			
			processBuilder.command(DIR+script+".sh");
		}
		
		String dbFileName = "";
		try {

			Process process = processBuilder.start();

			StringBuilder output = new StringBuilder();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			
			String line;
			while ((line = reader.readLine()) != null) {
				if(line.matches("altHealthDB(.*)")) {
					dbFileName = line;
				}
				output.append(line + "\n");
			}

			int exitVal = process.waitFor();
			if (exitVal == 0) {
				System.out.println("Success!");
				System.out.println(output);
				System.out.println("Return backup file: "+dbFileName);
//				System.exit(0);
			} else {
				// abnormal...
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return dbFileName;
	}
	
	private boolean setEnvVar(Map<String, String> environmentVariables, String...args) {
		boolean newEnvVar = false;
		int count = 1;
		for(String envVariable : args) {
			environmentVariables.put("var"+count, envVariable);
			count++;
			newEnvVar = true;
		}
		
		return newEnvVar;
	}
	
	private void addEnvVar(boolean newEnvVar, Map<String, String> environmentVariables, List<String> cmdList) {
		if(newEnvVar) {
			int count = 1;
			for(int x=0; x<environmentVariables.size(); x++) {
				if(environmentVariables.containsKey("var"+count)) {
					cmdList.add("%"+"var"+count+"%");
					count++;
				}
			}
			
		}
	}
}

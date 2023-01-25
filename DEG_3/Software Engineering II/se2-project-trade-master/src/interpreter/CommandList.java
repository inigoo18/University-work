package interpreter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class CommandList {

	private static Map<String, String> commandList;
	
	
	public static Set<String> getCommands(){
		
		if (commandList == null) {
			commandList = buildCommandList();
		}
		
		return commandList.keySet();
		
	}
	
	public static List<String> getCommandDescriptions(){
		
		if (commandList == null) {
			commandList = buildCommandList();
		}
		
		List<String> commands = new ArrayList<String>();
		
		for (String command : commandList.values()) {
			commands.add(command);
		}
		Collections.sort(commands);
		return commands;
		
	}
	
	private static Map<String, String> buildCommandList(){
		
		Map<String, String> commandList = new HashMap<String, String>();
		
		commandList.put("north", "moves the player north");
		commandList.put("south", "moves the player south");
		commandList.put("east", "moves the player east");
		commandList.put("west", "moves the player west");
		commandList.put("pickup", "picks up food");
		commandList.put("quit", "allows the player to exit the system.");
		
		return commandList;
		
	}
	
	
}

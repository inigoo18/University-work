package interpreter;

import java.util.List;

import interpreter.command.Command;
import trade.model.Inventory;

public class Interpreter {

	private static Interpreter instance;
	
	public Interpreter() {
		
	}
	
	public static Interpreter getInstance() {
		if (instance == null) {
			instance = new Interpreter();
		}
		
		return instance;
	}
	
	public synchronized void processCommand(Inventory player, String textCommand) {
		List<String> parsedCommandSequence = CommandTokenizer.parseCommand(textCommand);
		
		if (!parsedCommandSequence.isEmpty()) {
			Command command = CommandInstantiator.instantiate(player, parsedCommandSequence);
			if (command != null) {
				command.execute();
			}
		}
	}
	
	
}

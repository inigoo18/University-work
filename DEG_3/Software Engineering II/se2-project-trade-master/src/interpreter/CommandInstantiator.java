package interpreter;


import java.util.List;

import interpreter.command.Command;
import interpreter.command.CommandMove;
import interpreter.command.CommandPickUp;
import trade.model.Inventory;

public class CommandInstantiator {

	public static Command instantiate(Inventory player, List<String> parsedCommandSequence) {
		
		Command command = null;
		
		String commandWord = parsedCommandSequence.get(0);
		
		if (commandWord.equalsIgnoreCase("north") || commandWord.equalsIgnoreCase("n")) {
			command = new CommandMove(player, "n");
		}
		else if (commandWord.equalsIgnoreCase("south") || commandWord.equalsIgnoreCase("s")) {
			command = new CommandMove(player, "s");
		}
		else if (commandWord.equalsIgnoreCase("east") || commandWord.equalsIgnoreCase("e")) {
			command = new CommandMove(player, "e");
		}
		else if (commandWord.equalsIgnoreCase("west") || commandWord.equalsIgnoreCase("w")) {
			command = new CommandMove(player, "w");
		}
		else if (commandWord.equalsIgnoreCase("pickUp")) {
			command = new CommandPickUp(player);
		}
		// pick up
		
		return command;
	}
	
	
	
}

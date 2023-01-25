package Commands;

import Exceptions.WrongArgumentsException;

public abstract class CommandGenerator {
	private static Command[] availableCommands = {
		new ListCommand(),
		new HelpCommand(),
		new ResetCommand(),
		new ExitCommand(),
		new UpdateCommand(),
		new BuyCommand(),
		new MoveCommand(),
		new ShockwaveCommand(),
		new ShootCommand(),
		new ListPrintersCommand(),
		new StringifyCommand(),
		new SaveCommand()
	};
	
	public static Command parse(String[] string) throws CommandParseException {
		Command command = null;
		boolean ok = false;
		int i = 0;
		if (string[0].equals("")) {
			string[0] = "none";
		}
		try {
			while (i < availableCommands.length && !ok) {
				command = availableCommands[i].parse(string); // COMMAND
				if (command != null) {
					ok = true;
				}
				i++;
			}
		}
		catch (WrongArgumentsException ex) {
			throw new CommandParseException("error when parsing", ex);
		}
		return command;
	}
	
	public static String commandHelp() {
		String result = "";
		for (int i = 0; i < availableCommands.length; i++) {
			result += availableCommands[i].helpText();
		}
		return result;
	}
}

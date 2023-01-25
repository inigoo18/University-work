package Commands;

public abstract class NoParamsCommand extends Command {

	public NoParamsCommand(String name, String shortcut, String details, String help) {
		super(name, shortcut, details, help);
		// TODO Auto-generated constructor stub
	}
	
	public Command parse(String[] commandWords) {
		if (name.equalsIgnoreCase(commandWords[0]) || shortcut.equalsIgnoreCase(commandWords[0])) {
			return this;
		}
		else {
			return null;
		}
	}
}

package Commands;

import Game.Game;

public class HelpCommand extends NoParamsCommand {

	public HelpCommand() {
		super("Help", "H", "help", "prints this help message.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		System.out.println(CommandGenerator.commandHelp());
		return false;
	}
}

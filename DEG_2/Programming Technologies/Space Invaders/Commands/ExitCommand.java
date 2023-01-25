package Commands;

import Game.Game;

public class ExitCommand extends NoParamsCommand {

	public ExitCommand() {
		super("Exit", "E", "exit", "terminates the program.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		game.exit();
		game.update();
		return false;
	}

}

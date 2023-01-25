package Commands;

import Game.Game;

public class ResetCommand extends NoParamsCommand {

	public ResetCommand() {
		super("Reset", "R", "reset", "starts a new game.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		game.reset();
		return true;
	}

}

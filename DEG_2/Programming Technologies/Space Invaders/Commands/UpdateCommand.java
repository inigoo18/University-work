package Commands;

import Game.Game;

public class UpdateCommand extends NoParamsCommand {

	public UpdateCommand() {
		super("None", "N", "[none]", "skips one cycle.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		game.update();
		return true;
	}

}

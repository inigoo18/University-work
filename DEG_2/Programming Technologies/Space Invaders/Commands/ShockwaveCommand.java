package Commands;

import Exceptions.ShockwaveException;
import Game.Game;

public class ShockwaveCommand extends NoParamsCommand {

	public ShockwaveCommand() {
		super("Shockwave", "W", "shockwave", "causes the UCM-Ship to release a shock wave.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException { // throws executeException
		try {
			game.canUCMshockWave();
			game.shockWave();
		} catch (ShockwaveException e) {
			throw new CommandExecuteException("Shockwave could not be fired", e);
		}
		game.update();
		return true;
	}

}

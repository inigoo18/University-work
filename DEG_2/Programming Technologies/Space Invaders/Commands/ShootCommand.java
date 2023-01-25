package Commands;

import Exceptions.MissileInFlightException;
import Exceptions.NoSuperException;
import Exceptions.WrongArgumentsException;
import Game.Game;

public class ShootCommand extends Command {

	private boolean superMissile;
	
	public ShootCommand() {
		super("Shoot", "S", "shoot <supermissile>", "causes the UCM-Ship to launch a missile. If user has a supermissile, it'll be launched instead.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		boolean result = true;
		try {
			game.canUCMShoot();
		} catch (MissileInFlightException ex) {
			result = false;
			throw new CommandExecuteException("Missile could not be fired", ex);
		}
		if (!superMissile) {
				game.shootMissile();
				game.update();
		} else {
			try {
				game.canUCMSuper();
				game.superMissile();
				game.update();
			} catch (NoSuperException ex2) {
				result = false;
				throw new CommandExecuteException("Missile could not be fired", ex2);
			}
		}
		return result;
	}

	@Override
	public Command parse(String[] commandWords) throws WrongArgumentsException {
		if ((name.equalsIgnoreCase(commandWords[0]) || shortcut.equalsIgnoreCase(commandWords[0]))) {
			if (commandWords.length == 2 && commandWords[1].equalsIgnoreCase("supermissile")) {
				superMissile = true;
			}
			else if (commandWords.length == 1) {
				superMissile = false;
			}
			else {
				throw new WrongArgumentsException("wrong shoot arguments");
			}
			ShootCommand shootCommand = this;
			return shootCommand;
		}
		else {
			return null;
		}
	}

}

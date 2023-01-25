package Commands;

import Exceptions.OffWorldException;
import Exceptions.WrongArgumentsException;
import Game.Game;

public class MoveCommand extends Command{
	
	private boolean dir;
	private int moveCells;
	
	public MoveCommand() {
		super("Move", "M", "move <left|right> <1|2>", "causes the UCM-Ship to move as indicated.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		if (dir) {
			moveCells = -moveCells;
		}
		boolean result = true;
		try {
			game.canUCMMove(moveCells);
			game.move(moveCells);
			game.update();
		}
		catch(OffWorldException ex) {
			result = false;
			throw new CommandExecuteException("Cannot perform move", ex);
		}
		return result;
	}

	@Override
	public Command parse(String[] commandWords) throws WrongArgumentsException {
		if ((name.equalsIgnoreCase(commandWords[0]) || shortcut.equalsIgnoreCase(commandWords[0]))) {
			if (commandWords.length == 3 && validStr(commandWords[1]) && validInt(commandWords[2])) {	
				dir = stringToDir(commandWords[1]);
				moveCells = Integer.parseInt(commandWords[2]);
				MoveCommand moveCommand = this;
				return moveCommand;
			}
			else {
				throw new WrongArgumentsException("wrong move arguments, 1st arg: <left/right>; 2nd arg: <1,2>");
			}
		}
		else {
			return null;
		}
	}
	
	public boolean stringToDir(String str) {
		if (str.equalsIgnoreCase("Left")) {
			return true; // LEFT
		}
		return false;
	}
	
	public boolean validInt(String str) {
		if (Integer.parseInt(str) <= 2 && Integer.parseInt(str) > 0) {
			return true;
		}
		return false;
	}
	
	public boolean validStr(String str) {
		if (!str.matches("[0-9]+") && str.equalsIgnoreCase("Left") || str.equalsIgnoreCase("Right")) {
			return true;
		}
		return false;
	}
}

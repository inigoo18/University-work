package Commands;

import Exceptions.NotEnoughScoreException;
import Exceptions.WrongArgumentsException;
import Game.Game;

public class BuyCommand extends Command{
	private String item;
	
	public BuyCommand() {
		super("Buy", "B", "buy <item>", "lets the player buy a supermissile for 20 pts.");
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		boolean result = true;
		try {
			if (item.equalsIgnoreCase("supermissile")) {
				game.canUCMPurchase(Game.SUPER_COST);
				game.purchaseSuper();
			}
		}
		catch(NotEnoughScoreException e) {
			result = false;
			throw new CommandExecuteException("Could not buy product", e);
		}
		return result;
	}

	@Override
	public Command parse(String[] commandWords) throws WrongArgumentsException {
		if ((name.equalsIgnoreCase(commandWords[0]) || shortcut.equalsIgnoreCase(commandWords[0]))) {
			if (commandWords.length == 2 && commandWords[1].equalsIgnoreCase("supermissile")) {
				item = commandWords[1];
				BuyCommand buyCommand = this;
				return buyCommand;
			}
			else {
				throw new WrongArgumentsException("wrong buy argument");
			}
		}
		else {
			return null;
		}
	}
}

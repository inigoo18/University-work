package Commands;

import Functionality.GamePrinter;
import Functionality.PrinterGenerator;
import Game.Game;

public class StringifyCommand extends NoParamsCommand {

	public StringifyCommand() {
		super("Stringify", "F", "stringify", "prints the board as a string");
	}

	@Override
	public boolean execute(Game game) {
		GamePrinter printer = PrinterGenerator.STRINGIFIER.getObject(game);
		printer.setGame(game);
		System.out.println(printer);
		return false;
	}

}

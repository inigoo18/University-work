package Commands;

import Functionality.PrinterGenerator;
import Game.Game;

public class ListPrintersCommand extends NoParamsCommand {

	public ListPrintersCommand() {
		super("HelpPrinter", "P", "helpprinter", "shows the user the different print methods.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		System.out.println(PrinterGenerator.printerHelp(game));
		return false;
	}
}

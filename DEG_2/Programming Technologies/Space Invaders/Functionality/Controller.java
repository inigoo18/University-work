package Functionality;

import java.util.Scanner;

import Commands.Command;
import Commands.CommandExecuteException;
import Commands.CommandGenerator;
import Commands.CommandParseException;
import Game.Game;

public class Controller {

	private Scanner in;
	private Game game;

	public Controller(Game game) {
		in = new Scanner(System.in);
		this.game = game;
	}

	public static final String UNKNOWNCOMMANDMSG = "That command is unknown.";
	public static final String PROMPT = "Command >";

	public void run() {
		GamePrinter printer = PrinterGenerator.BOARDPRINTER.getObject(game);
		System.out.println(printer);
		while (!game.isFinished()) {
			System.out.print(PROMPT);
			String[] words = in.nextLine().trim().split("\\s+");
			try {
				Command command = CommandGenerator.parse(words);
				if (command != null) {
					if (command.execute(game)) {
						printer = PrinterGenerator.BOARDPRINTER.getObject(game);
						System.out.println(printer);
					}
				}
				else {
					System.out.println(UNKNOWNCOMMANDMSG);
				}
			} catch (CommandParseException | CommandExecuteException ex) {
				System.err.format(ex.getMessage() + "%nCause of Exception: %n" + ex.getCause() + "%n");
			}
		}
		System.out.println(game.getWinnerMessage());
	}
}
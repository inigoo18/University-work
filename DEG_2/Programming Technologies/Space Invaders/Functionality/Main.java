package Functionality;

import java.util.Random;

import Game.Game;

public class Main {
	
	public static void main(String[] args) {
		boolean ok = true;
		Random rand = new Random();
		Level newLevel = Level.parse(args[0]);
		if (newLevel == null) {
			ok = false;
			System.err.println("Usage: Main <EASY|HARD|INSANE> [seed]: level must be one of: EASY, HARD, INSANE");
		}
		if (args.length == 0 || args.length > 2) {
			ok = false;
			System.err.println("Usage: Main <EASY|HARD|INSANE> [seed]");
		}
		else {
			if (args.length == 2){
				if (args[1].matches("[0-9]+")) {
					rand = new Random(Integer.parseInt(args[1]));
				}
				else {
					ok = false;
					System.err.println("Usage: Main <EASY|HARD|INSANE> [seed]: the seed must be a number");
				}
			}
		}
		if (ok) {
			Game game = new Game(newLevel, rand);
			Controller controller = new Controller(game);
			controller.run();
		}
	}
	
}
package Functionality;

import Game.Game;

public class Stringifier extends GamePrinter {
	
	private Game game;
	
	
	@Override
	public void setGame(Game game) {
		this.game = game;
	}
	
	public String toString() {
		String result = game.stringify();
		return result;
	}

}

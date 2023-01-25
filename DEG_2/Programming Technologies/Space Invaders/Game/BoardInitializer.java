package Game;

import Functionality.Level;

public class BoardInitializer {
	private Level level;
	private Board board;
	private Game game;

	public Board initialize(Game game, Level level) {
		this.level = level;
		this.game = game;
		board = new Board(Game.DIM_X, Game.DIM_Y);
		initializeUfo();
		initializeCarrierShips();
		initializeDestroyers();
		return board;
	}

	private void initializeUfo() {
		Ufo ufo = new Ufo(game, Game.DIM_X - 1, 0, 1);
		board.add(ufo);
	}

	private void initializeCarrierShips() {
		int numCarrierShips = game.getLevel().getNumCarrierShips();
		int numCarrierShipsPerRow = game.getLevel().getNumCarrierShipsPerRow();
		for (int i = 0; i < (numCarrierShips / numCarrierShipsPerRow); i++){
			for (int j = 0; j < numCarrierShipsPerRow; j++) {
				Carrier carrier = new Carrier(game, Game.DIM_X - 1 - j, i + 1, 2, level.getNumCyclesToMoveOneCell());
				board.add(carrier);
			}
		}
	}

	private void initializeDestroyers() {
		int numCarrierShipsPerRow = game.getLevel().getNumRowsOfCarrierShips();
		int numDestroyers = game.getLevel().getNumDestroyers();
		int aux = 0;
		if (game.getLevel() == Level.EASY) {
			aux = 1;
		}
		for (int j = 0; j < numDestroyers; j++) {		
			Destroyer destroyer = new Destroyer(game, Game.DIM_X - 1 - j - aux, numCarrierShipsPerRow + 1, 1, level.getNumCyclesToMoveOneCell());
			board.add(destroyer);
		}
	}
}
package Interfaces;

import Game.Game;

// use percentages
public interface IExecuteRandomActions {
	static boolean canGenerateUfo(Game game){
		return game.getRandom().nextInt(100) < 100 * game.getLevel().getUfoFrequency();
	}

	static boolean canGenerateBomb(Game game){
		return game.getRandom().nextInt(100) < 100 * game.getLevel().getShootFrequency();
	}
	
	static boolean canGenerateExplosiveShip(Game game) {
		return game.getRandom().nextInt(100) < 100 * game.getLevel().turnExplosiveFrequency();
	}
}
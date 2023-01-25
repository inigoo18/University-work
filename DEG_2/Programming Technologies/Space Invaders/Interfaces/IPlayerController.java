package Interfaces;

import Exceptions.MissileInFlightException;
import Exceptions.NoSuperException;
import Exceptions.NotEnoughScoreException;
import Exceptions.OffWorldException;
import Exceptions.ShockwaveException;

public interface IPlayerController {
// PLAYER ACTIONS
	void move(int numCells);

	void shootMissile();

	void shockWave();
	
	void purchaseSuper();
	
	void superMissile();

// CALLBACKS
	void receivePoints(int points);

	void enableShockWave();

	void enableMissile();
}
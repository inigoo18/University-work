package Game;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import Exceptions.MissileInFlightException;
import Exceptions.NoSuperException;
import Exceptions.NotEnoughScoreException;
import Exceptions.OffWorldException;
import Exceptions.ShockwaveException;
import Functionality.Level;
import Interfaces.IPlayerController;

public class Game implements IPlayerController {
	public final static int DIM_X = 9;
	public final static int DIM_Y = 8;
	public final static int SUPER_COST = 20;
	private int currentCycle;
	private Random rand;
	private Level level;
	private Board board;
	private UCMShip player;
	private boolean doExit;
	private BoardInitializer initializer;
	

	public Game(Level gameLevel, Random random) {
		rand = random;
		level = gameLevel;
		initializer = new BoardInitializer();
		initGame();
	}

	public void initGame () {
		currentCycle = 0;
		AlienShip.numShips = 0;
		board = initializer.initialize (this, level );
		player = new UCMShip(this, DIM_X / 2, DIM_Y - 1, 3);
		board.add(player);
	}

	public Random getRandom() {
		return rand;
	}

	public Level getLevel() {
		return level;
	}
	
	public Board getBoard() {
		return board;
	}

	public void reset() {
		initGame();
	}

	public void addObject(GameElement object) {
		board.add(object);
	}
	
	public void swapObject(GameElement object, GameElement other) {
		board.swap(object, other);
	}

	public String positionToString( int x, int y ) {
		return board.toString( x, y );
	}

	public boolean isFinished() {
		return playerWin() || aliensWin() || doExit;
	}

	public boolean aliensWin() {
		return !player.isAlive() || AlienShip.haveLanded();
	}

	public boolean playerWin() {
		return AlienShip.allDead();
	}
	
	public int getCycle() {
		return currentCycle;
	}

	public void update() {
		board.computerAction();
		board.update();
		currentCycle += 1;
		AlienShip.timesDown = 0;
	}

	public boolean isOnBoard(int posX, int posY) {
		return ((posX >= 0 && posX < DIM_X) && (posY >= 0 && posY < DIM_Y)) ;
	}

	public void exit() {
		doExit = true;
	}

	public String infoToString() {
		String output = "";
		if (!isFinished()) {
			output += "Current cycle: " + currentCycle + "\n";
			output += "Player lives: " + player.getShield() + "\n";
			output += "Remaining aliens: " + AlienShip.numShips + "\n";
			output += "Shockwave: " + player.getShockwave() + "\n";
			output += "Super: " + player.getSuper() + "\n";
			output += "Score: " + player.getScore();
		}
		return output;
	}

	@Override
	public void move(int numCells){
		player.setPosX(player.getPosX() + numCells);
	}

	@Override
	public void shootMissile(){
		player.setCanShoot(false);
		Missile missile = new Missile(this, player.getPosX(), player.getPosY(), 1);
		board.add(missile);
	}
	
	public void canUCMShoot() throws MissileInFlightException {
		player.canShoot();
	}
	
	public void canUCMSuper() throws NoSuperException{
		player.canSuper();
	}
	
	public void canUCMshockWave() throws ShockwaveException{
		player.canShockwave();
	}
	
	public void canUCMPurchase(int price) throws NotEnoughScoreException{
		player.scoreHigher(price);
	}
	
	public void canUCMMove(int numCells) throws OffWorldException{
		player.canMoveSpaces(numCells);
	}
	
	@Override
	public void shockWave(){
		for (int i = 0; i < Game.DIM_Y; i++) {
			Shockwave shockwave = new Shockwave(this, -1, i, 1);
			board.add(shockwave);
		}
	}
	
	public void superMissile(){
		player.setCanShoot(false);
		player.setSuper(false);
		Supermissile missile = new Supermissile(this, player.getPosX(), player.getPosY(), 2);
		board.add(missile);
	}

	@Override
	public void receivePoints(int points) {
		player.addScore(points);
		
	}

	@Override
	public void enableShockWave() {
		player.setShockwave(true);
		
	}

	@Override
	public void enableMissile() {
		player.setCanShoot(true);
		
	}
	
	public void purchaseSuper(){
		player.setSuper(true);
		player.setScore(player.getScore() - SUPER_COST);
	}
	
	public String stringify() {
		String result = "";
		result += "- - - Space Invaders <version 2.0> - - - \n";
		result += "\n";
		result += "G " + currentCycle + "\n";
		result += "L " + level.name() + "\n";
		result += board.stringify();
		return result;
	}
	
	public String getWinnerMessage() {
		if (doExit) {
			return "Player exits the game.";
		}
		if (playerWin()) {
			return "Player wins!";
		}
		else if (aliensWin()) {
			return "Aliens win!";
		}
		else {
			return "This shouldn't happen";
		}
	}
	
}
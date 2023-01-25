package Functionality;//some_package_name//;

import Game.Board;
import Game.Game;
import tp.p1.util.MyStringUtils;

public class BoardPrinter extends GamePrinter {
	
	private int numRows, numCols;
	public final static String space = " ";
	private Board board;
	private String[][] tablero;
	private Game game;
	
	public int getNumCols() { // QUITAR
		return numCols;
	}
	
	public int getNumRows() { // QUITAR
		return numRows;
	}
	
	
	private void encodeGame(Board board) {
		tablero = new String[numRows][numCols];
		for(int i = 0; i < numRows; i++) {
			for(int j = 0; j < numCols; j++) {
				tablero[i][j] = board.toString(j, i);
			}
			
		}
	}
	
	public String toString() {
		int cellSize = 7;
		int marginSize = 2;
		String vDelimiter = "|";
		String hDelimiter = "-";
		String intersect = space;
		String vIntersect = space;
		String hIntersect = "-";
		String corner = space;
		
		String cellDelimiter = MyStringUtils.repeat(hDelimiter, cellSize);
		
		String rowDelimiter = vIntersect + MyStringUtils.repeat(cellDelimiter + intersect, numCols-1) + cellDelimiter + vIntersect;
		String hEdge =  corner + MyStringUtils.repeat(cellDelimiter + hIntersect, numCols-1) + cellDelimiter + corner;
		
		String margin = MyStringUtils.repeat(space, marginSize);
		String lineEdge = String.format("%n%s%s%n", margin, hEdge);
		String lineDelimiter = String.format("%n%s%s%n", margin, rowDelimiter);
		
		StringBuilder str = new StringBuilder();
		System.out.println(game.infoToString());
		str.append(lineEdge);
		for(int i=0; i<numRows; i++) {
				str.append(margin).append(vDelimiter);
				for (int j=0; j<numCols; j++)
					str.append( MyStringUtils.centre(tablero[i][j], cellSize)).append(vDelimiter);
				if (i != numRows - 1) str.append(lineDelimiter);
				else str.append(lineEdge);	
		}
		
		return str.toString();
	}

	@Override
	public void setGame(Game game) {
		this.numRows = Game.DIM_Y;
		this.numCols = Game.DIM_X;
		this.board = game.getBoard();
		this.game = game;
		encodeGame(board);
		
	}
}
package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import server.Client;
import trade.model.Direction;
import trade.model.Inventory;
import trade.model.Place;
import trade.model.Season;
import trade.model.TradeObserver;
import trade.model.World;

public class MapModel extends JComponent implements TradeObserver {

	private static final Color LINE_COLOR = Color.black;
	
	private Inventory player;
	private List<Place> places;
	private List<Image> playerImages;
	
	
	MapModel(Client client){
		this.setPreferredSize(new Dimension(300,200));
		World.getInstance().addObserver(this);
		player = client.getPlayer();
		playerImages = new ArrayList<Image>();
		initGUI();
	}
	
	private void initGUI() {
		playerImages.add(loadImage("Player1.png"));
		playerImages.add(loadImage("Player2.png"));
		playerImages.add(loadImage("Player3.png"));
		playerImages.add(loadImage("Player4.png"));
	}
	
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setFont(new Font("Lucida Console", Font.BOLD, 32));
		drawMap(g);
	}
	
	
	private void drawMap(Graphics g) {
		drawLines(g);
		drawCurrentPlace(g);
		drawNextPlaces(g);
	}
	
	private void drawCurrentPlace(Graphics g) {
		int x = getWidth()/2;
		int y = getHeight()/2;
		int sizeX = 75;
		int sizeY = 75;
		Place place = player.getCurrentPlace();
		g.setColor(place.getColor());
		g.fillRect(x- sizeX/2, y-sizeY/2, sizeX, sizeY);
		g.setColor(Color.GRAY);
		g.drawString(place.getQuantity() + "", x-15, y+15);
		placePlayers(g, place, x, y);
	}
	
	private void drawNextPlaces(Graphics g) {
		int sizeX = 70;
		int sizeY = 70; // 100
		int x = getWidth()/2;
		int y = getHeight()/2;
		int modX;
		int modY;
		int offset = 135; // 200
		
		Place playerPlace = player.getCurrentPlace();
		Direction dir;
		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				dir = Direction.NORTH;
				modX = x - sizeX/2;
				modY = y - offset;
			}
			else if (i == 1) {
				dir = Direction.SOUTH;
				modX = x - sizeX/2;
				modY = y + offset / 2;
			}
			else if (i == 2) {
				dir = Direction.EAST;
				modX = x + offset / 2;
				modY = y - sizeY/2;
			}
			else {
				dir = Direction.WEST;
				modX = x - offset;
				modY = y - sizeY/2;
			}
			Place nextPlace = playerPlace.getExitDestination(dir);
			if (nextPlace != null) {
				g.setColor(nextPlace.getColor());
				g.fillRect(modX, modY, sizeX, sizeY);
				g.setColor(Color.GRAY);
				g.drawString(nextPlace.getQuantity() + "", modX+20, modY+50);
				placePlayers(g, nextPlace, modX+ 75/2, modY + 75/2);
			}
		}
	}
	
	private void placePlayers(Graphics g, Place place, int x, int y) {
		List<Inventory> players = place.getPlayers();
		int num = players.size();
		int offset = 50;
		int modX = x + 15 - offset;
		int modY = y - offset;
		for (int i = 0; i < num; i++) {
			g.drawImage(playerImages.get(players.get(i).getID() - 1),modX ,modY ,20, 35, this);
			if (i == 0) {
				modX = modX + offset;
			}
			else if (i == 1) {
				modX = modX - offset;
				modY = modY + offset;
			}
			else if (i == 2) {
				modX = modX + offset;
			}
		}
	}
	
	
	
	private void drawLines(Graphics g) {
		g.setColor(LINE_COLOR);
		int x1 = getWidth()/2;
		int y1 = getHeight()/2;
		int modx1 = x1;
		int mody1 = y1;
		int x2, y2;
		int offset1 = 35; // 50
		int offset2 = 120; // 150
		
		Place playerPlace = player.getCurrentPlace();
		Direction dir;
		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				dir = Direction.NORTH;
				modx1 = x1;
				mody1 = y1 + offset1;
				x2 = x1;
				y2 = y1 - offset2;
			}
			else if (i == 1) {
				dir = Direction.SOUTH;
				modx1 = x1;
				mody1 = y1 + offset1;
				x2 = x1;
				y2 = y1 + offset2;
			}
			else if (i == 2) {
				dir = Direction.EAST;
				modx1 = x1 + offset1;
				mody1 = y1;
				x2 = x1 + offset2;
				y2 = y1;
			}
			else {
				modx1 = x1 - offset1;
				mody1 = y1;
				dir = Direction.WEST;
				x2 = x1 - offset2;
				y2 = y1;
			}
			
			
			if (playerPlace.getExitDestination(dir) != null) {
				g.drawLine(x1, y1, x2, y2);
			}
		}
	}
	
	private Image loadImage(String img) {
		Image i = null;
		try {
			return ImageIO.read(new File ("resources/icons/" + img));
		} catch (IOException e) {
		}
		return i;
	}
	
	
	
	@Override
	public void onAdvance(List<Inventory> players, List<Place> places, Season season) {
		this.places = places;
		repaint();
		
	}

	@Override
	public void onRegister(List<Inventory> players, List<Place> places, Season season) {
		this.places = places;
		repaint();
		
	}

	@Override
	public void onMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

}

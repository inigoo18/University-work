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
import trade.model.Food;
import trade.model.Inventory;
import trade.model.Place;
import trade.model.Season;
import trade.model.TradeObserver;
import trade.model.World;

public class InventoryModel extends JComponent implements TradeObserver {

	private Inventory player;
	private Season season;
	private final int offsetY = 60;
	private List<Image> _foods;
	
	
	
	
	InventoryModel(Client client){
		this.setPreferredSize(new Dimension(300,200));
		World.getInstance().addObserver(this);
		player = client.getPlayer();
		_foods = new ArrayList<Image>();
		initGUI();
		
	}
	
	private void initGUI() {
		_foods.add(loadImage("fruit.png"));
		_foods.add(loadImage("meat.png"));
		_foods.add(loadImage("cereal.png"));
		_foods.add(loadImage("fish.png"));
	}
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setFont(new Font("Lucida Console", Font.BOLD, 32));
		drawMap(g);
	}
	
	
	private void drawMap(Graphics g) {
		drawItems(g);
	}
	
	private void drawItems(Graphics g) {
		List<Food> items = player.getInventory();
		int num = items.size();
		int x = 0;
		int y = 0;
		for (Food f: items) {
			Color color;
			if (f.getExpiration() == 1) {
				color = Color.red;
			}
			else {
				color = Color.green;
			}
			g.setColor(color);
			g.fillRect(x, y, 500, offsetY);
			fillInfo(g, f, y);
			y = y + offsetY;
		}
	}
	
	private void fillInfo(Graphics g, Food f, int y) {
		int x = 0;
		int idx;
		Color color;
		if (f.getType().equalsIgnoreCase("Fish")) {
			idx = 3;
		}
		else if (f.getType().equalsIgnoreCase("Meat")) {
			idx = 1;
		}
		else if (f.getType().equalsIgnoreCase("Fruit")) {
			idx = 0;
		}
		else {
			idx = 2;
		}
		// Draw food portrait
		g.setColor(Color.WHITE);
		g.fillRect(x + 5, y + 5, offsetY - 10, offsetY - 10);
		g.drawImage(_foods.get(idx),x+5 ,y+5 ,offsetY - 10, offsetY - 10, this);
		///
		x = offsetY + 5;
		// Line
		g.setColor(Color.black);
		g.drawLine(x, y, x, y + offsetY);
		///
		x = x + 10;
		// Draw name of food
		g.setFont(new Font("Lucida Console", Font.PLAIN, 16));
		g.drawString(f.getName(), x, y+offsetY / 2);
		///
		x = x + 100;
		// Draw line
		g.drawLine(x, y , x, y + offsetY);
		//
		x = x + 15;
		// Draw expiration of food
		g.setFont(new Font("Lucida Console", Font.PLAIN, 32));
		g.drawString(f.getExpiration() + "", x, y + offsetY - 20);
		//
		x = x + 45;
		// Draw line
		g.drawLine(x, y , x, y + offsetY);
		x = x + 15;
		// Draw price of food depending on season
		if (f.getGoodSeason().equals(season)) {
			color = Color.orange;
		}
		else if (f.getBadSeason().equals(season)) {
			color = Color.blue;
		}
		else {
			color = Color.black;
		}
		g.setColor(color);
		g.drawString(f.getCost(season) + "", x, y + offsetY - 20);
		x = x + 45;
		// Draw line
		g.setColor(Color.black);
		g.drawLine(x, y, x, y + offsetY);
		x = x + 15;
		// Draw price of food depending on next season
		Season nextSeason = season.nextSeason();
		if (f.getGoodSeason().equals(nextSeason)) {
			color = Color.orange;
		}
		else if (f.getBadSeason().equals(nextSeason)) {
			color = Color.blue;
		}
		else {
			color = Color.black;
		}
		g.setColor(color);
		g.drawString(f.getCost(nextSeason) + "", x, y + offsetY - 20);
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
		repaint();
		this.season = season;
	}

	@Override
	public void onRegister(List<Inventory> players, List<Place> places, Season season) {
		// TODO Auto-generated method stub
		this.season = season;
		
	}

	@Override
	public void onMessage(String msg) {
		// TODO Auto-generated method stub
		
	}

}

package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import server.Client;
import trade.model.Inventory;
import trade.model.Place;
import trade.model.Season;
import trade.model.TradeObserver;
import trade.model.World;

public class StatusModel extends JComponent implements TradeObserver{

	private JLabel _currentLocation;
	String name;
	//private Client client;
	private Inventory player;
	private List<Inventory> players;
	Season season;
	
	private static final Color SPRING = Color.green;
	private static final Color WINTER = Color.gray;
	private static final Color SUMMER = Color.yellow;
	private static final Color FALL = Color.orange;
	
	
	StatusModel(Client client){
		player = client.getPlayer();
		players = new ArrayList<Inventory>();
		JPanel mainPanel = new JPanel();
		
		add(mainPanel);
		
		name = player.getName();
		
		this.setPreferredSize(new Dimension(200,70));
		World.getInstance().addObserver(this);
		
	}
	
	public void paintComponent(Graphics graphics) {
		Graphics2D g = (Graphics2D) graphics;
		g.setFont(new Font("Lucida Console", Font.BOLD, 14));
		drawMap(g);
	}
	
	
	private void drawMap(Graphics g) {
		drawSeason(g);
		drawPlayers(g);
		drawLocation(g);
		drawGold(g);
	}
	
	private void drawSeason(Graphics g) {
		Color color;
		if (season == null) {
			color = Color.white;
		}
		else {
			if (season.equals(Season.SUMMER)) {
				color = SUMMER;
			}
			else if (season.equals(Season.FALL)) {
				color = FALL;
			}
			else if (season.equals(Season.WINTER)) {
				color = WINTER;		
			}
			else{
				color = SPRING;
			}
		}
		g.setColor(color);
		g.fillRect(0, 0, 500, 100);
		
	}
	
	private void drawName(Graphics g, String name, int x, int y) {
		g.setColor(Color.black);
		g.drawString(name, x, y);
	}
	
	private void drawLocation(Graphics g) {
		int x = 15;
		int y = 65;
		g.setColor(Color.black);
		g.drawString("Current location: " + player.getCurrentPlace().getName(), x, y);
	}
	
	private void drawGold(Graphics g) {
		int x = 375;
		int y = 65;
		g.drawString("Cash: " + player.getCash(), x, y);
	}
	
	private void drawPlayers(Graphics g) {
		
		int x = 15;
		int y = 20;
		int energy;
		int maxEnergy;
		String name;
		for (int i = 0; i < players.size(); i++) {
			energy = players.get(i).getEnergy();
			maxEnergy = players.get(i).getMaxEnergy();
			name = players.get(i).getName();
			drawName(g, name, x, y);
			drawEnergy(g, maxEnergy, energy, x, y + 5);
			x = x + 125;
		}
	}
	
	private void drawEnergy(Graphics g, int maxEnergy, int energy, int x, int y) {
		for (int i = 1; i <= maxEnergy; i++) {
			g.setColor(Color.black);
			g.fillRect(x, y, 5, 5);
			if (energy >= i) {
				g.setColor(Color.red);
				g.fillRect(x, y, 5, 5);
			}
			x = x + 5;
		}
	}

	@Override
	public void onAdvance(List<Inventory> players, List<Place> places, Season season) {
		this.players = players;
		this.season = season;
		repaint();
		
		//_energy.setText("Energy: + [" + player.getEnergy() + "]");
		//_currentLocation.setText("Located at: " + player.getCurrentPlace().getName());
		
	}

	@Override
	public void onRegister(List<Inventory> players, List<Place> places, Season season) {
		this.players = players;
		//this.season = season;
		repaint();
		//_energy.setText("Energy: + [" + player.getEnergy() + "]");
		//_currentLocation.setText("Located at: " + player.getCurrentPlace().getName());
		
	}

	@Override
	public void onMessage(String msg) {
		// TODO Auto-generated method stub
		
	}
}

package server;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.SwingUtilities;

import trade.creation.builder.WorldBuilder;
import trade.creation.builder.WorldBuilderImpl;
import trade.creation.builder.WorldDataParser;
import trade.model.Inventory;
import trade.model.World;
import view.MainWindow;

public class TradeServer {
	
	public static void main(String[] args) {
		
		String[] nameArray = {"Johnny", "Paula", "Mark", "Diego", "Inigo", "Yaiza", "Pedro", "Veronica", "Tin", "Mar", "Valentin", "Alvaro", "Diego", "Peter", "Alfredo", "Pablo", "Alberto", "Paul", "Pablo", "Willy"};
		int nameSize = nameArray.length;
		List<String> nameList;
		
		nameList = new ArrayList<String>();
		for (int i = 0; i < nameSize; i++) {
			nameList.add(nameArray[i]);
		}
		
		Random rand = new Random();
		int seed = rand.nextInt();
		//Builder
		WorldBuilder builder = new WorldBuilderImpl();
		
		WorldDataParser dataParser = new WorldDataParser(builder);
		
		dataParser.worldCreator(seed);
		
		Client client = new Client();
		
		String playerName = client.welcome();
		
		
		Inventory player = new Inventory(playerName, 1);
		player.setAI(false);
		client.setPlayer(player);
		World.getInstance().addPerson(player);
		
		for (int i = 0; i < 3; i++) {
			String nameGotten = playerName;
			int nextInt = 0;
			while (nameGotten == playerName) {
				nextInt = rand.nextInt(nameList.size());
				nameGotten = nameList.get(nextInt);
			}
			dataParser.buildPlayer(nameGotten, i + 2);
			nameList.remove(nextInt);
		}
		
		startGUI(client);
		
	}
	
	private static void startGUI(Client client) {
		SwingUtilities.invokeLater(() -> new MainWindow(client));
	}
	
	
}

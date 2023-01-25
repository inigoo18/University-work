package server;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Scanner;

import javax.swing.Timer;

import interpreter.Interpreter;
import trade.model.Inventory;
import trade.model.TradeObserver;
import trade.model.World;

public class Client {
	
	
	private Scanner in;
	private Inventory player;
	
	public Client() {
		in = new Scanner(System.in);
	}
	
	public Inventory getPlayer() {
		return this.player;
	}
	
	public void setPlayer(Inventory player) {
		this.player = player;
	}
	
	public String welcome() {
		System.out.println("Hello and welcome to Trade!");
		System.out.println("First of all, select a name for your player:");
		String name = "";
		while (name == "" || name.length() < 1) {
			name = in.nextLine();
		}
		System.out.println("Hey there " + name + "!");
		return name;
	}
	
	public void continueServer() {
		World.getInstance().advance();
		if (player.getEnergy() <= 0) {
			finishGame();
		}
		
	}
	
	private void finishGame() {
		new Thread(new Runnable(){
            public void run(){
            	while (!World.getInstance().isDone()) {
	                try {
	                	World.getInstance().advance();
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
            	}
            	World.getInstance().sendMsg("Closing app in 10 seconds.");
            	try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
            	shutdown();
            }
            
      }).start();
	}
	
	public void listenForInputGUI(String textIn) {
		if (player.getEnergy() > 0) {
			
			if (textIn.contentEquals("quit")) {
				shutdown();
			}
			else if (!textIn.contentEquals("") || textIn != null) {
				Interpreter.getInstance().processCommand(this.player, textIn);
				
			}
		
		}
	}
	
	private void shutdown() {
		System.exit(0);
	}
	

}

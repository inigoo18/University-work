package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.AbstractTableModel;

import trade.model.Inventory;
import trade.model.Place;
import trade.model.Season;
import trade.model.TradeObserver;
import trade.model.World;

public class CommandPanel extends JPanel implements TradeObserver {
	private JTextArea worldText;
	private JScrollPane allTextPane;
	private JTextField textField;
	private final static String newLine = "\n";
	
	
	public CommandPanel() {
		this.setLayout(null);
		//this.setTextField(field);
		worldText = new JTextArea();
		worldText.setFont(new Font("Lucida Console", Font.PLAIN, 16));
		worldText.setEditable(false);
		worldText.setLineWrap(true);
		worldText.setWrapStyleWord(true);
		worldText.setSize(new Dimension(800, 640));
		//worldText.setPreferredSize(new Dimension(getWidth(), getHeight()));
		worldText.setBackground(Color.BLACK);
		allTextPane = new JScrollPane(worldText);
		allTextPane.setSize(new Dimension(780,640));
		//allTextPane.setPreferredSize(new Dimension(getWidth(), getHeight()));
		allTextPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		allTextPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		
		allTextPane.setBackground(Color.BLACK);
		
		worldText.setCaretPosition(worldText.getText().length());
		this.add(allTextPane);
		
		World.getInstance().addObserver(this);
	}
	
	
	

	public void postMessage(String str) {
		String temp = str;
		worldText.setForeground(Color.WHITE);
		worldText.append(temp + newLine);
		worldText.setCaretPosition(worldText.getText().length() - 1);;
		//worldText.setCaretPosition(0);
		repaint();
	}
	
	public void setTextField(JTextField textField) {
		this.textField = textField;
		postMessage(textField.getText());
	}
	
	public JTextField getTextField() {
		return this.textField;
	}




	@Override
	public void onAdvance(List<Inventory> players, List<Place> places, Season season) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onRegister(List<Inventory> players, List<Place> places, Season season) {
		// TODO Auto-generated method stub
		
	}
	
	public void onMessage(String str) {
		postMessage(str);
	}


}

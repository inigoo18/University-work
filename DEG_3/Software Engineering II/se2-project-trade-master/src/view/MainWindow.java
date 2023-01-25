package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import server.Client;
import trade.model.World;

public class MainWindow extends JFrame {
	
	private CommandPanel commandPanel;
	private Client client;
	JTextField textField;
	
	public MainWindow(Client client) {
		super("Trade");
		this.client = client;
		initGUI();
	}
	
	private void initGUI() {
		
		JPanel mainPanel = new JPanel(new BorderLayout());
		
		this.setContentPane(mainPanel);
		
		
		JPanel commandBar = new JPanel(); // cmd + enter
		mainPanel.add(commandBar, BorderLayout.PAGE_END);
		
		JPanel statusBar = new JPanel();
		mainPanel.add(statusBar, BorderLayout.PAGE_START);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
		mainPanel.add(leftPanel, BorderLayout.CENTER);
		
		JPanel rightPanel = new JPanel(new GridLayout(1,2));
		mainPanel.add(rightPanel, BorderLayout.EAST);
		
		JPanel tablesPanel = new JPanel(); // cp + status
		tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));
		leftPanel.add(tablesPanel);
		
		JPanel viewsPanel = new JPanel();
		viewsPanel.setLayout(new BoxLayout(viewsPanel, BoxLayout.Y_AXIS));
		rightPanel.add(viewsPanel);
		
		
		// Status
		
		JPanel statusPanel = createViewPanel(new StatusModel(client), "Status");
		statusPanel.setPreferredSize(new Dimension(500,100));
		statusBar.add(statusPanel);
		
		// Command panel
		
		commandPanel = new CommandPanel();
		JPanel comPanel = createViewPanel(commandPanel, "Command Panel");
		comPanel.setPreferredSize(new Dimension(500, 200));
		tablesPanel.add(comPanel);
		
		// Map panel
		
		JPanel mapPanel = createViewPanel(new MapModel(client), "Map");
		mapPanel.setPreferredSize(new Dimension(500, 200));
		viewsPanel.add(mapPanel);
		
		//Inventory panel
		JPanel invPanel = createViewPanel(new InventoryModel(client), "Inventory");
		invPanel.setPreferredSize(new Dimension(500,200));
		viewsPanel.add(invPanel);
		
		// COMMAND BAR
		
		textField = new JTextField();
		textField.addKeyListener(new EnterListener());
		textField.setPreferredSize(new Dimension(500,20));
		
		JButton enterBut = new JButton("Enter");
		enterBut.addActionListener(new ButtonListener());
		enterBut.setSize(new Dimension(70,30));
		
		commandBar.add(textField);
		commandBar.add(enterBut);
		
		//////////////
		
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
		
		
	}
	
	private JPanel createViewPanel(JComponent c, String title) {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), title));
		p.add(new JScrollPane(c));
		return p;
	}
	
	private void sendCommand() {
		String temp = textField.getText();
		client.listenForInputGUI(temp);
		textField.setText("");
		client.continueServer();
	}
	

private class ButtonListener implements ActionListener{
	public void actionPerformed(ActionEvent arg0) {
		sendCommand();
	}
}

private class EnterListener implements KeyListener{
	public void keyTyped(KeyEvent e) {
		
	}
	
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_ENTER) {
			sendCommand();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		
	}
	
}
	

}

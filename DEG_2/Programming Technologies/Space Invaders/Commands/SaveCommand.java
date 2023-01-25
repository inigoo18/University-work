package Commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import Functionality.Stringifier;

import Exceptions.WrongArgumentsException;
import Game.Game;

public class SaveCommand extends Command {

	private String fileName;
	
	public SaveCommand() {
		super("Save", "G", "save", "saves the current game in a file");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) throws CommandExecuteException {
		boolean result = true;
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".dat"))){
			Stringifier stringifier = new Stringifier();
			stringifier.setGame(game);
			writer.write(stringifier.toString());
			
		}
		catch (IOException e) {
			result = false;
			throw new CommandExecuteException("couldn't save file because of file name", e);
		}
		if (result) {
			System.out.println("Game successfully saved in file " + fileName+ ".dat. Use the load command to load it. (not implemented)");
		}
		
		
		
		return false;
	}

	@Override
	public Command parse(String[] commandWords) throws WrongArgumentsException {
		if ((name.equalsIgnoreCase(commandWords[0]) || shortcut.equalsIgnoreCase(commandWords[0]))) {
			if (commandWords.length == 2) {
				fileName = commandWords[1];
			}
			else {
				throw new WrongArgumentsException("wrong shoot arguments");
			}
			SaveCommand saveCommand = this;
			return saveCommand;
		}
		else {
			return null;
		}
	}

}

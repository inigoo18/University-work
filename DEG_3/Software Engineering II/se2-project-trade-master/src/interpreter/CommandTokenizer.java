package interpreter;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class CommandTokenizer {

	public static List<String> parseCommand(String textCommand){
		
		Scanner scanner = new Scanner(textCommand);
		
		List<String> parsedCommandSequence = new LinkedList<String>();
		
		while(scanner.hasNext()) {
			
			String command = scanner.next().trim().toLowerCase();
			parsedCommandSequence.add(command);
			
		}
		
		scanner.close();
		
		return parsedCommandSequence;
		
	}
	
}

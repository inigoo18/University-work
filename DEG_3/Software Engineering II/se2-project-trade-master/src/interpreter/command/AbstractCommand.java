package interpreter.command;

import trade.model.World;

public abstract class AbstractCommand implements Command {

	World world;
	
	public AbstractCommand() {
		world = World.getInstance();
	}
	
	public abstract void execute();
	
}

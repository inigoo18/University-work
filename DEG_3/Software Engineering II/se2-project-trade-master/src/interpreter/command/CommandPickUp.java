package interpreter.command;

import trade.model.Inventory;
import trade.model.World;

public class CommandPickUp extends AbstractCommand {

	Inventory player;
	
	public CommandPickUp() {
		
	}
	
	public CommandPickUp(Inventory player) {
		this.player = player;
	}

	@Override
	public void execute() {
		World.getInstance().sendMsg("You try to find something to pick up...");
		pickUp(player);
	}
	
	
	private void pickUp(Inventory player) {
		player.setPickingUp(true);
		player.setMove(false);
	}
	
}

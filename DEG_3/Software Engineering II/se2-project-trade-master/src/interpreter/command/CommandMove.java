package interpreter.command;

import trade.creation.builder.DirectionEncoder;
import trade.model.Direction;
import trade.model.Inventory;
import trade.model.Place;
import trade.model.World;

public class CommandMove extends AbstractCommand {

	Inventory player;
	String target;
	
	public CommandMove() {
		
	}
	
	public CommandMove(Inventory player, String target) {
		this.player = player;
		this.target = target;
	}

	@Override
	public void execute() {
		Direction dir = DirectionEncoder.convertDirection(target);
		String msg;
		Place place = player.getCurrentPlace().getExitDestination(dir);
		if (place != null) {
			msg = "You move " + dir + ", thus arriving to " + place.getName();
			if (place.getType().equalsIgnoreCase("city") && player.getInventory().size() > 0) {
				msg += ". You sell any food you have.";
			}
			if (place.getFood() != null) {
				msg += ". Here you can get " + place.getFood();
			}
		}
		else {
			msg = "You try to move " + dir + " but bump into a wall. You lose 1 energy for needless effort.";
		}
		World.getInstance().sendMsg(msg);
		move(player,dir);
	}
	
	
	private void move(Inventory player, Direction dir) {
		player.setDir(dir);
		player.setMove(true);
		player.setPickingUp(false);
	}
	
}

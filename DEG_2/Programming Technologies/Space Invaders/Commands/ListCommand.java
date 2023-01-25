package Commands;

import Game.Game;

public class ListCommand extends NoParamsCommand {

	public ListCommand() {
		super("List", "L", "list", "displays the list of ship types in the game.");
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean execute(Game game) {
		System.out.println("-<x>-: Points: 5 - Damage: 0 [5% of becoming *(x)*] - Resistence: 2 \n"
				+"*(x)*: Points: 5 - Damage: 1, area (2x2) [on death] - Resistence: 2 \n"
				+"!<x>!: Points: 10 - Damage: 1 - Resistence: 1 \n"
				+"<(+)>: Points: 25 - Damage: 0 - Resistence: 1 \n"
				+"/-^-: Damage: 1 - Resistence: 3 \n");
		
		return false;
	}

}

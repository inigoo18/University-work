package trade.model;

import java.util.List;

public interface TradeObserver {
	
	void onAdvance(List<Inventory> players, List<Place> places, Season season);
	void onRegister(List<Inventory> players, List<Place> places, Season season);
	void onMessage(String msg);

}

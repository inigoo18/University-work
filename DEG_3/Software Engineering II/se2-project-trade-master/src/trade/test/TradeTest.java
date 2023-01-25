package trade.test;

import org.junit.runner.*;
import org.junit.runners.*;

import trade.creation.builder.test.WorldBuilderTest;
import trade.creation.builder.test.WorldRandomGenerationTest;
import trade.model.test.FoodTest;
import trade.model.test.InventoryTest;
import trade.model.test.PlaceTest;
import trade.model.test.WorldTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	WorldBuilderTest.class,
	InventoryTest.class,
	FoodTest.class, 
	PlaceTest.class,
	WorldRandomGenerationTest.class,
	WorldTest.class
})

public class TradeTest {
	
}

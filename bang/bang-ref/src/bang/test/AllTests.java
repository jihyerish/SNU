package bang.test;


import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

	public static Test suite() {
		TestSuite suite = new TestSuite("Test for bang");
		//$JUnit-BEGIN$
		suite.addTestSuite(DistanceTest.class);
		suite.addTestSuite(HandTest.class);
		suite.addTestSuite(DeckTest.class);
		suite.addTestSuite(DiscardTest.class);
		suite.addTestSuite(InPlayTest.class);
		suite.addTestSuite(FigureTest.class);
		suite.addTestSuite(CardTest.class);
		suite.addTestSuite(SetupTest.class);
		suite.addTestSuite(PlayerTest.class);
		suite.addTestSuite(TurnTest.class);
		//suite.addTestSuite(GameStateTest.class);
		//$JUnit-END$
		return suite;
	}

}

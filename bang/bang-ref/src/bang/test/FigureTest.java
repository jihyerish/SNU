package bang.test;

import bang.Figure;

import junit.framework.TestCase;

public class FigureTest extends TestCase {
	public void testName(){
		Figure figure = new Figure();
		String setName = "Big Hank";
		figure.setName(setName);
		String gotName = figure.getName();
		assertEquals(setName, gotName);
	}
}

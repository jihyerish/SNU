package bang.test;

import java.util.ArrayList;

import bang.userinterface.WebGameUserInterface;

import junit.framework.TestCase;

public class WebGameuserInterfaceTest extends TestCase {
	public void testAI(){
		WebGameUserInterface wgui = new WebGameUserInterface(new ArrayList<String>(), 0);
		assertEquals("1", wgui.somethingAI("Jimmy", "respondBeer Shoot@true, Beer@false, Missed!@false, Missed!@false, "));
	}
}

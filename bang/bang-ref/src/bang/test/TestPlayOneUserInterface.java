package bang.test;

import java.util.List;

import bang.InPlay;
import bang.Player;
import bang.userinterface.UserInterface;

public class TestPlayOneUserInterface extends TestUserInterface implements UserInterface  {

	boolean askedPlay = false;
	
	public int askDiscard(Player player) {
		return 0;
	}

	@Override
	public int chooseGeneralStoreCard(Player generalPlayer,
			List<Object> generalStoreCards) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int askPlayer(Player player, List<String> otherPlayers) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int askOthersCard(Player player, InPlay inPlay, boolean hasHand) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<Object> chooseTwoDiscardForLife(Player sidKetchum) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int askPlay(Player player) {
		if(!askedPlay){
			askedPlay = true;
			return 0;	
		}
		return -1;
	}

	@Override
	public boolean chooseDiscard(Player player, Object card) {
		// TODO Auto-generated method stub
		return false;
	}
	public void printInfo(String info){
		//do nothing
	}

	@Override
	public boolean chooseFromPlayer(Player player) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int chooseDrawCard(Player player, List<Object> cards) {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public int chooseCardToPutBack(Player player, List<Object> cards) {
		// TODO Auto-generated method stub
		return 0;
	}
}

package bang.card;

import java.util.ArrayList;

import bang.Deck;
import bang.Discard;
import bang.HelpFunctions;
import bang.Player;
import bang.userinterface.JavaUserInterface;

public class Catbalou extends Card {
	private HelpFunctions HelpFunctions = new HelpFunctions();
	private JavaUserInterface userInterface = new JavaUserInterface();
	
	public Catbalou(String name, String suit, int value) {
		super(name, suit, value);
	}
	
	public boolean canPlay(Player currentPlayer, ArrayList<Player> players) {
		return true;
	}

	public boolean play(Player currentPlayer, ArrayList<Player> players, Deck deck, Discard discard) {
		ArrayList<Player> targets = targets(currentPlayer, players);
		int index = userInterface.askTarget(targets);	//TODO: ask target
		if (index == -1)
			return false;
		
		Player targetPlayer = targets.get(index);
		index = userInterface.askTargetCard(targetPlayer);	//TODO: ask card

		if (index == -3)
			return false;
		
		discard.add(this);
		if (index == -2)	//Remove Hand
			discard.add(targetPlayer.getHand().removeRandom());
		else if (index == -1)	//Remove gun
			discard.add(targetPlayer.getMounting().removeGun());
		else	//Remove mounting
			discard.add(targetPlayer.getMounting().remove(index));
		return true;
	}

	public ArrayList<Player> targets(Player currentPlayer, ArrayList<Player> players) {
		return HelpFunctions.getOthers(currentPlayer, players);
	}
}
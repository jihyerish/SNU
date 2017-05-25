package bang.cards;

import java.util.List;

import bang.CancelPlayer;
import bang.Deck;
import bang.Discard;
import bang.Player;
import bang.Turn;
import bang.userinterface.UserInterface;

public class Jail extends Card implements Playable {

	public Jail(String name, int suit, int value, int type) {
		super(name, suit, value, type);
	}

	@Override
	public boolean canPlay(Player player, List<Player> players, int bangsPlayed) {
		return targets(player, players).size() > 1;
	}

	@Override
	public boolean play(Player currentPlayer, List<Player> players,
			UserInterface userInterface, Deck deck, Discard discard, Turn turn) {
		Object target = Turn.getValidChosenPlayer(currentPlayer, targets(currentPlayer, players), userInterface);
		if(!(target instanceof CancelPlayer)){
			Player targetPlayer = (Player)target;
			targetPlayer.addInPlay(this);
			userInterface.printInfo(currentPlayer.getName() + " put " + targetPlayer.getName() + " in jail.");
			return true;
		} else {
			currentPlayer.getHand().add(this);
			return false;
		}
	}

	@Override
	public List<Player> targets(Player player, List<Player> players) {
		return Turn.getJailablePlayers(player, players);
	}

}

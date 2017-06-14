package bang.cards;

import java.util.List;

import bang.Deck;
import bang.Discard;
import bang.Player;
import bang.Turn;
import bang.userinterface.UserInterface;

public interface Playable {

	public abstract boolean canPlay(Player player, List<Player> players,
			int bangsPlayed);

	public abstract List<Player> targets(Player player, List<Player> players);

	public abstract boolean play(Player currentPlayer, List<Player> players,
			UserInterface userInterface, Deck deck, Discard discard, Turn turn);

}
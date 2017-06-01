package bang;

import java.util.ArrayList;
import bang.card.Card;

public class Mounting {
	private Card gun = null;
	private ArrayList<Card> mounting = new ArrayList<Card>();

	public boolean hasGun() {
		return gun != null;
	}

	public void setGun(Card gun) {
		this.gun = gun;
	}

	public Card getGun() {
		return gun;
	}

	public Card removeGun() {
		Card temp = gun;
		gun = null;
		return temp;
	}

	public void add(Card card) {
		mounting.add(card);
	}

	public Card remove(int index) {
		return mounting.remove(index);
	}

	public Card peek(int index) {
		return mounting.get(index);
	}

	public boolean hasCard(String card_name) {
		for(Card card: mounting) {
			if(card.getName().equals(card_name))
				return true;
		}
		return false;
	}

	public int find(String name) {
		for (int i = 0; i < mounting.size(); i++) {
			if (mounting.get(i).getName().equals(name))
				return i;
		}
		return -1;
	}

	public int size() {
		return mounting.size();
	}

	public String toArray() {
			String s = "[";
			if (gun != null)
				s += "\"" + gun.getName() + "\", ";

			for(Card card: mounting)
				s += "\"" + card.getName() + "\", ";

			if(s.equals("["))
				return "[]";
			return s.substring(0, s.length() - 2) + "]";
	}
}

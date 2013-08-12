/**
 * ポーカー
 */
package moscowmule2240.java006;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * デッキを表します。
 * 
 * @author moscowmule2240
 */
public class Deck {

	/**
	 * デッキに格納してあるカード。
	 */
	private List<Card> cards;

	/**
	 * コンストラクター。<br>
	 * カードを５２枚切ります。
	 */
	public Deck() {
		this.cards = new ArrayList<>();
		for (int i = 0; i < 52; i++) {
			this.cards.add(new Card(i / 13, i % 13));
		}
		Collections.shuffle(this.cards);
	}

	/**
	 * 一枚カードを引きます。
	 * 
	 * @return カード
	 */
	public Card draw() {
		if (this.cards.isEmpty()) {
			throw new IllegalAccessError("カードがありません。");
		}

		Card returnCard = this.cards.get(0);
		this.cards.remove(0);
		return returnCard;
	}
}

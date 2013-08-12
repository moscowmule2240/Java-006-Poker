/**
 * ポーカー
 */
package moscowmule2240.java006;

import java.util.Comparator;

/**
 * カードを並び替える比較クラス
 * 
 * @author moscowmule2240
 */
public class CardComparator implements Comparator<Card> {

	@Override
	public int compare(Card card1, Card card2) {
		if (card1.getNumber() < card2.getNumber()) {
			return -1;
		}
		if (card2.getNumber() < card1.getNumber()) {
			return 1;
		}
		if (card1.getMark() < card2.getMark()) {
			return -1;
		}
		return 1;
	}
}

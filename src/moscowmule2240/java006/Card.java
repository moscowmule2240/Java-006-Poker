/**
 * ポーカー
 */
package moscowmule2240.java006;

import java.util.ArrayList;
import java.util.List;

/**
 * トランプ１枚のカードを表します。
 * 
 * @author moscowmule2240
 */
public class Card {

	/**
	 * マークのリスト。
	 */
	private static final List<String> MARKS;

	/**
	 * 番号のリスト。
	 */
	private static final List<String> NUMBERS;

	static {
		MARKS = new ArrayList<>();
		Card.MARKS.add("スペード");
		Card.MARKS.add("クラブ");
		Card.MARKS.add("ダイヤ");
		Card.MARKS.add("ハート");

		NUMBERS = new ArrayList<>();
		Card.NUMBERS.add("2");
		Card.NUMBERS.add("3");
		Card.NUMBERS.add("4");
		Card.NUMBERS.add("5");
		Card.NUMBERS.add("6");
		Card.NUMBERS.add("7");
		Card.NUMBERS.add("8");
		Card.NUMBERS.add("9");
		Card.NUMBERS.add("10");
		Card.NUMBERS.add("J");
		Card.NUMBERS.add("Q");
		Card.NUMBERS.add("K");
		Card.NUMBERS.add("A");
	}

	/**
	 * マーク。
	 */
	private int mark;

	/**
	 * 番号。
	 */
	private int number;

	/**
	 * コンストラクター。
	 * 
	 * @param mark
	 *            マーク
	 * @param number
	 *            数値
	 */
	public Card(int mark, int number) {
		if ((mark < 0) || (Card.MARKS.size() <= mark)) {
			throw new IllegalArgumentException(Card.MARKS.size() + "以下の正の整数を入力してください。");
		}

		if ((number < 0) || (Card.NUMBERS.size() < number)) {
			throw new IllegalArgumentException(Card.NUMBERS.size() + "以下の正の整数を入力してください。");
		}

		this.mark = mark;
		this.number = number;
	}

	/**
	 * マークとして設定した数値を返します。
	 * 
	 * @return mark マークとして設定した数値
	 */
	public int getMark() {
		return this.mark;
	}

	/**
	 * 数値として設定した数値を返します。
	 * 
	 * @return number 数値として設定した数値
	 */
	public int getNumber() {
		return this.number;
	}

	/**
	 * 内容を出力します。
	 * 
	 * @return マーク＋番号
	 */
	public String value() {
		return Card.MARKS.get(this.mark) + "の" + Card.NUMBERS.get(this.number);
	}
}

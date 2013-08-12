/**
 * ポーカー
 */
package moscowmule2240.java006;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * ポーカークラス。
 * 
 * @author moscowmule2240
 */
public class Poker {

	/**
	 * ゲームを開始します。
	 * 
	 * @throws IOException
	 *             標準入力からの読み込みに失敗した場合
	 */
	private static void gameStart() throws IOException {
		// 手札
		List<Card> cards = new ArrayList<>();

		// ５枚ドロー
		Deck deck = new Deck();
		for (int i = 0; i < 5; i++) {
			cards.add(deck.draw());
		}

		// 手札表示
		Collections.sort(cards, new CardComparator());
		Poker.display(cards);

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		System.out.println("交換するカードの番号を入力してください。(例：135)");
		System.out.println("0を入力するとカードを交換しません。");

		while (true) {
			try {
				// 入力チェック
				List<Integer> input = Poker.checkInput(reader.readLine());

				// 入れ替え
				if (input.get(0) != 0) {
					for (int index : input) {
						cards.set(index - 1, deck.draw());
					}
				}

				// 手札表示
				Collections.sort(cards, new CardComparator());
				Poker.display(cards);

				// 役合わせ
				String pokerHand = Poker.checkPokerHand(cards);
				System.out.println("役は" + pokerHand + "でした。");

				break;

			} catch (IllegalArgumentException e) {
				System.out.println(e.getMessage());
			}
		}
	}

	/**
	 * カード内容を表示します。
	 * 
	 * @param cards
	 *            カード内容
	 */
	private static void display(List<Card> cards) {
		for (int i = 0; i < cards.size(); i++) {
			System.out.println((i + 1) + ":" + cards.get(i).value());
		}
	}

	/**
	 * 入力内容をチェックします。
	 * 
	 * @param line
	 *            入力内容
	 * @return 入力した数値のリスト
	 */
	private static List<Integer> checkInput(String line) {
		if (line.length() > 5) {
			throw new IllegalArgumentException("5枚以上は交換できません。");
		}

		int inputNumber;
		try {
			inputNumber = Integer.parseInt(line);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("入力は数値のみです。", e);
		}

		if (inputNumber < 0) {
			throw new IllegalArgumentException("入力は正の整数のみです。");
		}

		List<Integer> checkNumbers = new ArrayList<>();
		for (int i = 0; i < line.length(); i++) {
			int integer = Integer.parseInt(String.valueOf(line.charAt(i)));
			if ((line.length() != 1) && (integer == 0)) {
				throw new IllegalArgumentException("1〜5番のカードを選んでください。");
			}
			if ((integer < 0) || (5 < integer)) {
				throw new IllegalArgumentException("1〜5番のカードを選んでください。");
			}
			if (checkNumbers.contains(integer)) {
				throw new IllegalArgumentException("入力に重複があります。");
			}
			checkNumbers.add(integer);
		}

		return checkNumbers;
	}

	/**
	 * 役を判定します。
	 * 
	 * @param cards
	 *            判定するカード
	 * @return 役
	 */
	private static String checkPokerHand(List<Card> cards) {

		// フラッシュ
		boolean isFlush = true;
		int mark = -1;
		for (Card card : cards) {
			if (mark == -1) {
				mark = card.getMark();
			} else if (card.getMark() != mark) {
				isFlush = false;
				break;
			}
		}

		// ストレート
		boolean isStraight = true;
		int number = -1;
		for (Card card : cards) {
			if ((number != -1) && ((number + 1) != card.getNumber())) {
				isStraight = false;
				break;
			}
			number = card.getNumber();
		}

		if (isFlush && isStraight) {
			if (cards.get(cards.size() - 1).getNumber() == 12) {
				return "ロイヤルストレートフラッシュ";
			}
			return "ストレートフラッシュ";
		}
		if (isFlush) {
			return "フラッシュ";
		}
		if (isStraight) {
			return "ストレート";
		}

		// ペア
		Map<Integer, Integer> pair = new HashMap<>();
		for (Card card : cards) {
			if (pair.containsKey(card.getNumber())) {
				pair.put(card.getNumber(), pair.get(card.getNumber()) + 1);
			} else {
				pair.put(card.getNumber(), 1);
			}
		}

		if (pair.containsValue(4)) {
			return "フォーカード";
		}
		if (pair.containsValue(3) && pair.containsValue(2)) {
			return "フルハウス";
		}
		if (pair.containsValue(3)) {
			return "スリーカード";
		}
		if (pair.containsValue(2)) {
			int count = 0;
			for (Entry<Integer, Integer> item : pair.entrySet()) {
				if (item.getValue() == 2) {
					count++;
				}
			}
			if (count == 2) {
				return "ツーペア";
			}
			return "ワンペア";
		}

		return "ノーペア";
	}

	/**
	 * エントリーポイント。
	 * 
	 * @param args
	 *            debug を入力するとデバッグを実行します。
	 */
	public static void main(String[] args) {

		if ((args.length == 1) && args[0].equals("debug")) {
			List<Card> cards = new ArrayList<>();
			int temp;

			// ロイヤルストレートスラッシュ
			cards.clear();
			temp = 8;
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			// ストレートフラッシュ
			cards.clear();
			temp = 0;
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			// フラッシュ
			cards.clear();
			temp = 0;
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, 12));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			// ストレート
			cards.clear();
			temp = 0;
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(0, temp++));
			cards.add(new Card(1, temp++));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			// フォーカード
			cards.clear();
			temp = 0;
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 0));
			cards.add(new Card(0, 1));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			// フルハウス
			cards.clear();
			temp = 0;
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 0));
			temp = 0;
			cards.add(new Card(temp++, 1));
			cards.add(new Card(temp++, 1));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			// スリーカード
			cards.clear();
			temp = 0;
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 0));
			cards.add(new Card(0, 1));
			cards.add(new Card(0, 2));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			// ツーペア
			cards.clear();
			temp = 0;
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 0));
			temp = 0;
			cards.add(new Card(temp++, 1));
			cards.add(new Card(temp++, 1));
			cards.add(new Card(temp++, 2));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			// ワンペア
			cards.clear();
			temp = 0;
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 0));
			cards.add(new Card(temp++, 1));
			cards.add(new Card(temp++, 2));
			cards.add(new Card(0, 3));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			// ノーペア
			cards.clear();
			temp = 0;
			cards.add(new Card(temp, temp++));
			cards.add(new Card(temp, temp++));
			cards.add(new Card(temp, temp++));
			cards.add(new Card(temp, temp++));
			cards.add(new Card(0, 12));
			Collections.sort(cards, new CardComparator());
			Poker.display(cards);
			System.out.println(Poker.checkPokerHand(cards));

			return;
		}

		try {
			Poker.gameStart();
		} catch (Exception e) {
			System.out.println("続行不可能な例外が発生しました。ゲームを終了します。");
			e.printStackTrace();
		}
	}
}

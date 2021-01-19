package org.poker.hands.calculator.util;

import java.util.Map;

import static java.util.Map.entry;
import static org.poker.hands.calculator.common.Const.Cards.*;

public class Utils {
  private final static Map<String, Integer> suits = Map.ofEntries(
          entry("S", SPADE),
          entry("D", DIAMOND),
          entry("C", CLUBS),
          entry("H", HEART)
  );
  private final static Map<Integer, Integer> strengths = Map.ofEntries(
          entry(2, TWO),
          entry(3, THREE),
          entry(4, FOUR),
          entry(5, FIVE),
          entry(6, SIX),
          entry(7, SEVEN),
          entry(8, EIGHT),
          entry(9, NINE),
          entry(10, TEN),
          entry(11, JACK),
          entry(12, KING),
          entry(13, QUEEN),
          entry(14, ACE)

  );

  public static int[] parseHand(String hand) {
    String[] cards = hand.split(";");
    int[] parsedHand = new int[5];
    try {
      for (int i = 0; i< cards.length; i++) {
        String suitsKey = cards[i].substring(0, 1);
        Integer strengthsKey = Integer.parseInt(cards[i].substring(1));
        parsedHand[i] = suits.get(suitsKey) + strengths.get(strengthsKey);
      }
    } catch (Exception e) {
      System.out.println("Error: invalid hand syntax.");
      System.out.println("Make sure you enter hands correctly.");
      System.exit(1);
    }
    return parsedHand;
  }
}

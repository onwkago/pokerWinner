package org.poker.hands.calculator.util;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;

public class CalculatorUtils {

  public static int getCardStrength(int card) {
    return card - (1 << getCardSuitBitPosition(card));
  }

  private final static Map<Integer, Double> multipliers = Map.ofEntries(
          entry(1, 0.0001),
          entry(2, 0.00000001),
          entry(3, 0.0000001),
          entry(4, 0.000001)
  );

  public static int getHighCard(int[] hand) {
    int[] handStrengths = getCardsStrengths(hand);
    return handStrengths[4];
  }

  public static int getCardSuitBitPosition(int card) {
    int suit = -1;
    for (int j = 13; j <= 16; j++) {
      if ((card >> j) == 1) {
        suit = j;
        break;
      }
    }
    return suit;
  }

  public static int getCardStrengthBitPosition(int card) {
    int cardStrength = getCardStrength(card);
    int cardBit = -1;
    for (int i = 0; i <= 13; i++) {
      if ((cardStrength >> i - 1) == 1) {
        cardBit = i;
        break;
      }
    }
    return cardBit;
  }

  public static int[] getCardsStrengths(int[] hand) {
    int[] cardsStrengths = new int[5];
    for (int i = 0; i < hand.length; i++) {
      cardsStrengths[i] = getCardStrength(hand[i]);
    }
    return cardsStrengths;
  }

  public static double getHandStrengthCoefficient(int[] hand) {
    int[] handStrengths = getCardsStrengths(hand);

    HashMap<Integer, Integer> coeficients = new HashMap<>();
    int coef = 1;
    for (int card : handStrengths) {
      if (coeficients.containsKey(card)) {
        coef++;
      } else if (!coeficients.containsKey(card) && !coeficients.isEmpty()) {
        coef = 1;
      }
      coeficients.put(card, coef);
    }

    return coeficients.entrySet()
            .stream()
            .mapToDouble(keySet -> keySet.getKey()
                    * multipliers.get(keySet.getValue())
                    * keySet.getValue()
            )
            .reduce(0, (a, r) -> a + r);
  }

}

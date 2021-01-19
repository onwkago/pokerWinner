package org.poker.hands.calculator.engine;

import org.poker.hands.calculator.entity.Player;

import java.util.Arrays;

import static org.poker.hands.calculator.common.Const.*;
import static org.poker.hands.calculator.util.CalculatorUtils.*;

/**
 * HandCalculator determines players hand strength
 * Using bit shift operations hand combination is calculated
 * to account for same strength combinations i.e. fullHouse vs fullHouse
 * additional hand is calculated and added to the hands strength
 */
public class HandCalculator {

  private boolean isFullHouse(int[] hand) {
    /*
      x x x y y
      y y x x x
     */
    boolean isFullHouse;
    int[] handStrengths = getCardsStrengths(hand);
    Arrays.sort(handStrengths);

    isFullHouse = handStrengths[0] == handStrengths[1]
            && handStrengths[0] == handStrengths[2]
            && handStrengths[3] == handStrengths[4];

    if (isFullHouse) {
      return isFullHouse;
    }

    return handStrengths[0] == handStrengths[1]
            && handStrengths[2] == handStrengths[3]
            && handStrengths[2] == handStrengths[4];
  }

  private boolean isPair(int[] hand) {
    /*
      + + - - -
      - + + - -
      - - + + -
      - - - + +
     */
    boolean isPair = false;
    int[] handStrengths = getCardsStrengths(hand);
    Arrays.sort(handStrengths);

    for (int i = 0; i < 4; i++) {
      isPair = handStrengths[i] == handStrengths[i + 1];
      if (isPair) {
        break;
      }
    }
    return isPair;
  }

  private boolean isTwoPair(int[] hand) {
    /*
      1 1 2 2 -
      1 1 - 2 2
      - 1 1 2 2
     */
    boolean isTwoPair;
    int[] handStrengths = getCardsStrengths(hand);
    Arrays.sort(handStrengths);
    for (int i = 0; i < 2; i++) {
      isTwoPair = handStrengths[i] == handStrengths[i + 1]
              && handStrengths[i + 2] == handStrengths[i + 3];

      if (isTwoPair) {
        return isTwoPair;
      }
    }
    return handStrengths[0] == handStrengths[1]
            && handStrengths[3] == handStrengths[4];
  }


  private static boolean isFourOfAKind(int[] hand) {
    boolean isFourOfAKind = false;
    int[] handStrengths = getCardsStrengths(hand);
    for (int i = 0; i < 2; i++) {
      isFourOfAKind = handStrengths[i] == handStrengths[i + 1]
              && handStrengths[i] == handStrengths[i + 2]
              && handStrengths[i] == handStrengths[i + 3];
      if (isFourOfAKind) {
        break;
      }
    }
    return isFourOfAKind;
  }

  private boolean isThreeOfAKind(int[] hand) {
    /*
     * + + + - -
     * - + + + -
     * - - + + +
     * */
    boolean isThreeOfAKind = false;
    int[] handStrengths = getCardsStrengths(hand);
    Arrays.sort(handStrengths);

    for (int i = 0; i < 3; i++) {
      isThreeOfAKind = handStrengths[i] == handStrengths[i + 1]
              && handStrengths[i] == handStrengths[i + 2];
      if (isThreeOfAKind) {
        break;
      }
    }
    return isThreeOfAKind;
  }


  private boolean isRoyalFlush(int[] hand) {
    boolean isAce = getCardStrength(getHighCard(hand)) == (0 | 4096);
    return isStraightFlush(hand) && isAce;
  }


  private boolean isStraightFlush(int[] hand) {
    return isFlush(hand) && isStraight(hand);
  }


  private boolean isFlush(int[] hand) {
    int suit = getCardSuitBitPosition(hand[0]);
    boolean flush = true;

    for (int i = 1; i < hand.length; i++) {
      if ((hand[i] & 1 << suit) == 0) {
        flush = false;
        break;
      }
    }
    return flush;
  }


  private boolean isStraight(int[] hand) {
    boolean isStraight = true;
    int[] sortedHand = new int[5];
    for (int i = 0; i < hand.length; i++) {
      sortedHand[i] = hand[i] - (1 << getCardSuitBitPosition(hand[i]));
    }

    // find bit position of lowest ranking card
    Arrays.sort(sortedHand);
    int firstCardBit = 0;
    for (int i = 0; i < 13; i++) {
      if ((sortedHand[0] >> i) == 1) {
        firstCardBit = i;
        break;
      }
    }

    for (int i = 1; i <= sortedHand.length; i++) {
      if ((sortedHand[i - 1] >> firstCardBit) != 1) {

        isStraight = false;
      }
      firstCardBit++;
    }

    return isStraight;
  }

  public double calculateStrength(Player player) {
    double coef =  getHandStrengthCoefficient(player.getHand());
    double strength = 0;
    String handName =HIGH_CARD;
    if (isPair(player.getHand())) {
      strength = 1;
      handName = PAIR;
      if (isThreeOfAKind(player.getHand())) {
        strength = 3;
        handName = THREE_OF_A_KIND;
        if (isFullHouse(player.getHand())) {
          strength = 6;
          handName = FULL_HOUSE;
        }
        if (isFourOfAKind(player.getHand())) {
          strength = 7;
          handName = FOUR_OF_A_KIND;
        }
      }
      if (isTwoPair(player.getHand()) && strength < 2) {
        strength = 2;
        handName = TWO_PAIR;
      }
    } else {
      if (isStraight(player.getHand())) {
        strength = 4;
        handName = STRAIGHT;
        if (isStraightFlush(player.getHand())) {
          strength = 8;
          handName = STRAIGHT_FLUSH;
          if (isRoyalFlush(player.getHand())) {
            strength = 9;
            handName = ROYAL_FLUSH;
          }
        }
      } else {
        if (isFlush(player.getHand())) {
          strength = 5;
          handName =FLUSH;
        }
      }
    }
    player.setHandName(handName);
    return strength + coef;
  }




}

package org.poker.hands.calculator.entity;

public class Player {

  private String handName;
  private int[] hand;

  public String getHandName() {
    return handName;
  }

  public void setHandName(String handName) {
    this.handName = handName;
  }

  public int[] getHand() {
    return hand;
  }

  public void setHand(int[] hand) {
    this.hand = hand;
  }

}

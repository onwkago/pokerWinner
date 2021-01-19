package org.poker.hands.calculator.engine;

import org.poker.hands.calculator.entity.Player;

public class Resolver {

  public void resolve(Player p1, Player p2) {
    HandCalculator calculator  = new HandCalculator();

    double p1Strength = calculator.calculateStrength(p1);
    double p2Strength = calculator.calculateStrength(p2);

    if (p1Strength > p2Strength) {
      System.out.println(String.format("player 1 wins with %s",p1.getHandName()));
    }
    else
    if (p2Strength > p1Strength) {
      System.out.println(String.format("player 1 wins with %s",p2.getHandName()));
    }else {
      System.out.println("Its a tie.");
    }

  }

}

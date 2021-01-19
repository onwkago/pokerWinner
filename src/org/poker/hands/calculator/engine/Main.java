package org.poker.hands.calculator.engine;

import org.poker.hands.calculator.entity.Player;

import static org.poker.hands.calculator.util.Utils.parseHand;

public class Main {
  public static void main(String[] args) {
    System.out.println("Winning poker hand calculator");
    System.out.println("to pass player hands use suits and strengths combinations separated by semicolon");
    System.out.println("Suits: S = spade, C = clubs, H = heart D = diamond");
    System.out.println("Strengths: two to ace = 2 to 14");
    System.out.println("for example: S10;D14;C3;H12;D8");
    System.out.println("you must pass hands for both players.\n");



    if (args.length < 2) {
      System.out.println("Error: not enough hands passed.");
      System.out.println("Make sure you enter hands correctly.");
      System.exit(1);
    }

    Player p1 = new Player();
    p1.setHand(parseHand(args[0]));

    Player p2 = new Player();
    p2.setHand(parseHand(args[1]));

    Resolver resolver = new Resolver();

    resolver.resolve(p1, p2);

  }

}

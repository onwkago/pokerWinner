***************** poker winning hand calculator *****************

simple java application to determine which poker hand is a winner

compiled with Java 11


possible hand variables:

Suits: S = spade, C = clubs, H = heart D = diamond
Strengths: two to ace = 2 to 14

to pass player hands use suits and strengths combinations separated by semicolon

for example: S14;D14;C14;H14;D8  would be a four of a kind of aces following eight of diamond's



HOW TO RUN:

to run the program simply"
  open cmd  change directory to pokerHandsCalculator/jar
  enter command: java -jar pokerHandsCalculator.jar  <handOfPlayer1> <handOfPlayer2>


the program uses bit shift operations to determine
the strength of a poker hand
to account for same strength hands i.e. full house vs full house
additional coeficient is calculated and added to the strength of a hand
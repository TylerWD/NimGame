
import java.util.*;
public class NimGame {
    public static void main(String[] args) {

        //Variables
        int numberOfMarbles;
        int goingFirst;
        int playAgain = 1;
        int playerMarblesTaken = 0;
        int playerMarblesTaking = 101;
        int nimMarblesTaken = 0;
        int nimMarblesTaking = 0;
        int noviceOrExpert = 0;
        int maxMarblesToTake;
        int turn = 0;

        //New RNG Object
        Random rng = new Random();

        //New Input Object
        Scanner in = new Scanner(System.in);

        do {
            //Game Starts
            System.out.println("Welcome to the game of Nim!");

            //Determine who goes first
            System.out.println("Determining who will go first...");
            goingFirst = rng.nextInt(2); // Generate a number from 0 to 1. 0 is Dr. Nim, 1 is Person.
            switch (goingFirst) {
                case 0: // If 0 is generated, Dr. Nim will go first.
                    System.out.println("Dr. Nim will be going first.");
                    break;
                case 1: // If 1 is generated, the player will go first.
                    System.out.println("You will be going first.");
                    break;
                default:
                    return;
            }

            //Determine the starting amount of marbles
            System.out.println("Generating number of marbles...");
            numberOfMarbles = rng.nextInt(91) + 10; // Generates a number from (10, 100), this will be the amount of marbles used in the game.
            System.out.printf("You are playing with %d marbles.\n", numberOfMarbles); // Output how many marbles are being used this game.
            maxMarblesToTake = numberOfMarbles / 2; // Update the amount of marbles a player can take on their turn.

            //Determine expert or novice.
            do { // Have the user choose Novice, Expert, or Random
                System.out.print("Would you like Dr. Nim to play as: \n" +
                        "Novice: Type 0\n" +
                        "Expert: Type 1\n" +
                        "Let us decide: Type 2\n");
                noviceOrExpert = in.nextInt();
            } while (noviceOrExpert != 0 && noviceOrExpert != 1 && noviceOrExpert != 2);

            // Output result of expert or novice.
            switch (noviceOrExpert) {
                case 0: // Novice
                    System.out.println("Dr. Nim will be going easy on you.");
                    break;
                case 1: // Expert
                    System.out.println("Dr. Nim will be going hard on you.");
                    break;
                case 2: // Random pick between novice or expert.
                    noviceOrExpert = rng.nextInt(2);
                    switch (noviceOrExpert) {
                        case 0: // If 0 is generated, Novice mode is selected.
                            System.out.println("Dr. Nim will be going easy on you.");
                            break;
                        case 1: // If 1 is generated, Expert mode is selected.
                            System.out.println("Dr. Nim will be going hard on you.");
                            break;
                    }
                    break;
                default:
                    return;
            }

            //Game Play
            switch (noviceOrExpert) {
                case 0: // Novice Mode
                    //First Turn
                    switch (goingFirst) {
                        case 0:
                            System.out.println("It is Dr. Nim's turn.");
                            nimMarblesTaking = rng.nextInt(numberOfMarbles / 2) + 1; // Set the amount of marbles that Dr. Nim is taken to a random number between 1 and the half of the current pile of marbles in the game.
                            numberOfMarbles -= nimMarblesTaking; // Decrease the total pile of marbles by the amount Dr. Nim is taking.
                            nimMarblesTaken += nimMarblesTaking; // Increase the total amount of marbles that Dr. Nim has by the amount he is currently taking.
                            maxMarblesToTake = numberOfMarbles / 2; // Set the new amount of marbles a user may take.
                            System.out.printf("Dr. Nim took %d marbles from the pile. Dr Nim now has %d marbles total and there are %d marbles left in the pile.\n", nimMarblesTaking, nimMarblesTaken, numberOfMarbles);
                            break;
                        case 1:
                            System.out.println("It is your turn.");
                            System.out.printf("You may take from 1 to %d marbles.\n", maxMarblesToTake);
                            if (maxMarblesToTake > 1) { // If the user can take more than one marble, do this.
                                do { // Make the user input a value that is not equal to 0 and that is not greater than the max amount of marbles they may take from the pile.
                                    System.out.println("How many marbles would you like to take?");
                                    playerMarblesTaking = in.nextInt();
                                } while (playerMarblesTaking > maxMarblesToTake && playerMarblesTaking != 0);

                                if (playerMarblesTaking == 0 || playerMarblesTaking > maxMarblesToTake) { // This makes it so the player cannot take 0 marbles or more than their allotted amount to take.
                                    while (playerMarblesTaking == 0 || playerMarblesTaking > maxMarblesToTake) {
                                        System.out.println("How many marbles would you like to take?");
                                        playerMarblesTaking = in.nextInt();
                                    }
                                }
                            } else {
                                do { // Do this if they user may only take one marble, the user must input one as their answer.
                                    System.out.println("How many marbles would you like to take?");
                                    playerMarblesTaking = in.nextInt();
                                } while (playerMarblesTaking != 1);
                            }
                            playerMarblesTaken += playerMarblesTaking; // Update the amount of marbles a user has taken by the amount they are taking this turn.
                            numberOfMarbles -= playerMarblesTaking; // Update the total amount of marbles by decreasing the total pool by how much marbles the player is taking this turn.
                            maxMarblesToTake = numberOfMarbles / 2; // Update the max amount of marbles a user may take on their turn.
                            System.out.printf("You are taking %d marbles from the pile. You know have %d marbles total and there are %d marbles left in the pile.\n", playerMarblesTaking, playerMarblesTaken, numberOfMarbles);
                            break;
                        default:
                            return;
                    }

                    switch (goingFirst) { // Switch whos turn it will be after the first turn.
                        case 0: // If it was Dr. Nim's turn, make it the player's turn.
                            turn = 1;
                            break;
                        case 1: // If it was the player's turn, make it Dr. Nim's turn.
                            turn = 0;
                            break;
                        default:
                            return;
                    }

                    do { // Play the game while there are more than 0 marbles in the game.
                        switch (turn) { // Does a process depending on who's turn it is.
                            case 0: // Process for Dr. Nim's turn.
                                System.out.println("It is Dr. Nim's turn.");
                                if (numberOfMarbles > 1) { // If the amount of marbles is one than Nim may only take one marble.
                                    nimMarblesTaking = rng.nextInt(numberOfMarbles / 2) + 1; // Nim may take from one to half of the pile.
                                } else {
                                    nimMarblesTaking = 1; // Take one marble Dr. Nim, no cheating.
                                }
                                numberOfMarbles -= nimMarblesTaking; // Negate the total amount of marbles by the amount of marbles Nim is taking.
                                nimMarblesTaken += nimMarblesTaking; // Add the amount of marbles in Nim's pile by the amount he is taking this turn.
                                maxMarblesToTake = numberOfMarbles / 2; // Change the max amount of marbles a user may take next turn.
                                System.out.printf("Dr. Nim took %d marbles from the pile. Dr Nim now has %d marbles total and there are %d marbles left in the pile.\n", nimMarblesTaking, nimMarblesTaken, numberOfMarbles);
                                turn = 1; // Switch turns.
                                break;
                            case 1: // Process for the players turn.
                                System.out.println("It is your turn.");
                                if (maxMarblesToTake > 1) { // Changes response based on if there is only one marble left to take.
                                    System.out.printf("You may take from 1 to %d marbles.\n", maxMarblesToTake); // State how many marbles a user may take.
                                } else {
                                    System.out.println("You may take one marble."); // Outputs if the player can only  take one marble from the pile.
                                }

                                // Gets players response as to many many marbles they want to take.
                                System.out.println("How many marbles would you like to take?");
                                playerMarblesTaking = in.nextInt();
                                if (maxMarblesToTake == 0) { // If the computer does numberOfMarbles/2 and maxMarblesToTake equals 0 then make it so maxMarblesToTake is equal to one. This is so that the user can always at-least take one marble.
                                    maxMarblesToTake = 1;
                                }
                                if (playerMarblesTaking == 0 || playerMarblesTaking > maxMarblesToTake) { // This makes it so the player cannot take 0 marbles or more than their allotted amount to take.
                                    while (playerMarblesTaking == 0 || playerMarblesTaking > maxMarblesToTake) {
                                        System.out.println("How many marbles would you like to take?");
                                        playerMarblesTaking = in.nextInt();
                                    }
                                }
                                playerMarblesTaken += playerMarblesTaking; // Add the amount of marbles in the Players pile by the amount he is taking this turn.
                                numberOfMarbles -= playerMarblesTaking; // Negate the total amount of marbles by the amount of marbles the player is taking.
                                maxMarblesToTake = numberOfMarbles / 2; // Change the max amount of marbles a user may take next turn.
                                System.out.printf("You are taking %d marbles from the pile. You know have %d marbles total and there are %d marbles left in the pile.\n", playerMarblesTaking, playerMarblesTaken, numberOfMarbles);
                                turn = 0;
                                break;
                            default:
                                return;
                        }
                    } while (numberOfMarbles > 0);

                    switch (turn) {
                        case 0: // If the new turn after the game has ended is Dr. Nim's then the player has lost.
                            System.out.println("Dr. Nim out smarts you again! You Lose!");
                            break;
                        case 1: // If the new turn after the game has ended is the players then Dr. Nim has lost.
                            System.out.println("You have won!");
                            break;
                        default: return;
                    }

                    break;
                case 1: // Expert Mode
                    //First Turn
                    switch (goingFirst) {
                        case 0:
                            System.out.println("It is Dr. Nim's turn.");

                            if (numberOfMarbles == 63 || numberOfMarbles == 31 || numberOfMarbles == 15 || numberOfMarbles == 7 || numberOfMarbles == 3) {
                                nimMarblesTaking = rng.nextInt(numberOfMarbles / 2) + 1; // Set the amount of marbles that Dr. Nim is taken to a random number between 1 and the half of the current pile of marbles in the game.
                            } else { // Nim takes marbles that equal a power of two less than one.
                                if (numberOfMarbles > 63) {
                                    nimMarblesTaking = numberOfMarbles - 63;
                                    numberOfMarbles = 63;
                                    nimMarblesTaken += nimMarblesTaking;
                                    maxMarblesToTake = numberOfMarbles / 2;
                                } else if (numberOfMarbles > 31) {
                                    nimMarblesTaking = numberOfMarbles - 31;
                                    numberOfMarbles = 31;
                                    nimMarblesTaken += nimMarblesTaking;
                                    maxMarblesToTake = numberOfMarbles / 2;
                                } else if (numberOfMarbles > 15) {
                                    nimMarblesTaking = numberOfMarbles - 15;
                                    numberOfMarbles = 15;
                                    nimMarblesTaken += nimMarblesTaking;
                                    maxMarblesToTake = numberOfMarbles / 2;
                                } else if (numberOfMarbles > 7) {
                                    nimMarblesTaking = numberOfMarbles - 7;
                                    numberOfMarbles = 7;
                                    nimMarblesTaken += nimMarblesTaking;
                                    maxMarblesToTake = numberOfMarbles / 2;
                                } else if (numberOfMarbles > 3) {
                                    nimMarblesTaking = numberOfMarbles - 3;
                                    numberOfMarbles = 3;
                                    nimMarblesTaken += nimMarblesTaking;
                                    maxMarblesToTake = numberOfMarbles / 2;
                                }
                            }

                            System.out.printf("Dr. Nim took %d marbles from the pile. Dr Nim now has %d marbles total and there are %d marbles left in the pile.\n", nimMarblesTaking, nimMarblesTaken, numberOfMarbles);
                            break;
                        case 1:
                            System.out.println("It is your turn.");
                            System.out.printf("You may take from 1 to %d marbles.\n", maxMarblesToTake);
                            if (maxMarblesToTake > 1) { // If the user can take more than one marble, do this.
                                do { // Make the user input a value that is not equal to 0 and that is not greater than the max amount of marbles they may take from the pile.
                                    System.out.println("How many marbles would you like to take?");
                                    playerMarblesTaking = in.nextInt();
                                } while (playerMarblesTaking > maxMarblesToTake && playerMarblesTaking != 0);

                                if (playerMarblesTaking == 0 || playerMarblesTaking > maxMarblesToTake) { // This makes it so the player cannot take 0 marbles or more than their allotted amount to take.
                                    while (playerMarblesTaking == 0 || playerMarblesTaking > maxMarblesToTake) {
                                        System.out.println("How many marbles would you like to take?");
                                        playerMarblesTaking = in.nextInt();
                                    }
                                }
                            } else {
                                do { // Do this if they user may only take one marble, the user must input one as their answer.
                                    System.out.println("How many marbles would you like to take?");
                                    playerMarblesTaking = in.nextInt();
                                } while (playerMarblesTaking != 1);
                            }
                            playerMarblesTaken += playerMarblesTaking; // Update the amount of marbles a user has taken by the amount they are taking this turn.
                            numberOfMarbles -= playerMarblesTaking; // Update the total amount of marbles by decreasing the total pool by how much marbles the player is taking this turn.
                            maxMarblesToTake = numberOfMarbles / 2; // Update the max amount of marbles a user may take on their turn.
                            System.out.printf("You are taking %d marbles from the pile. You know have %d marbles total and there are %d marbles left in the pile.\n", playerMarblesTaking, playerMarblesTaken, numberOfMarbles);
                            break;
                        default:
                            return;
                    }

                    switch (goingFirst) { // Switch whos turn it will be after the first turn.
                        case 0: // If it was Dr. Nim's turn, make it the player's turn.
                            turn = 1;
                            break;
                        case 1: // If it was the player's turn, make it Dr. Nim's turn.
                            turn = 0;
                            break;
                        default:
                            return;
                    }

                    do { // Play the game while there are more than 0 marbles in the game.
                        switch (turn) { // Does a process depending on who's turn it is.
                            case 0: // Process for Dr. Nim's turn.
                                System.out.println("It is Dr. Nim's turn.");

                                if (numberOfMarbles == 63 || numberOfMarbles == 31 || numberOfMarbles == 15 || numberOfMarbles == 7 || numberOfMarbles == 3) {
                                    nimMarblesTaking = rng.nextInt(numberOfMarbles / 2) + 1; // Set the amount of marbles that Dr. Nim is taken to a random number between 1 and the half of the current pile of marbles in the game.
                                    numberOfMarbles -= nimMarblesTaking;
                                    nimMarblesTaken += nimMarblesTaking;
                                    maxMarblesToTake = numberOfMarbles / 2;
                                } else { // Nim takes marbles that equal a power of two less than one.
                                    if (numberOfMarbles > 63) {
                                        nimMarblesTaking = numberOfMarbles - 63;
                                        numberOfMarbles = 63;
                                        nimMarblesTaken += nimMarblesTaking;
                                        maxMarblesToTake = numberOfMarbles / 2;
                                    } else if (numberOfMarbles > 31) {
                                        nimMarblesTaking = numberOfMarbles - 31;
                                        numberOfMarbles = 31;
                                        nimMarblesTaken += nimMarblesTaking;
                                        maxMarblesToTake = numberOfMarbles / 2;
                                    } else if (numberOfMarbles > 15) {
                                        nimMarblesTaking = numberOfMarbles - 15;
                                        numberOfMarbles = 15;
                                        nimMarblesTaken += nimMarblesTaking;
                                        maxMarblesToTake = numberOfMarbles / 2;
                                    } else if (numberOfMarbles > 7) {
                                        nimMarblesTaking = numberOfMarbles - 7;
                                        numberOfMarbles = 7;
                                        nimMarblesTaken += nimMarblesTaking;
                                        maxMarblesToTake = numberOfMarbles / 2;
                                    } else if (numberOfMarbles > 3) {
                                        nimMarblesTaking = numberOfMarbles - 3;
                                        numberOfMarbles = 3;
                                        nimMarblesTaken += nimMarblesTaking;
                                        maxMarblesToTake = numberOfMarbles / 2;
                                    } else if (numberOfMarbles <= 3) {
                                        nimMarblesTaking = rng.nextInt(numberOfMarbles / 2) + 1; // Set the amount of marbles that Dr. Nim is taken to a random number between 1 and the half of the current pile of marbles in the game.
                                        numberOfMarbles -= nimMarblesTaking;
                                        nimMarblesTaken += nimMarblesTaking;
                                        maxMarblesToTake = numberOfMarbles / 2;
                                    }
                                }

                                System.out.printf("Dr. Nim took %d marbles from the pile. Dr Nim now has %d marbles total and there are %d marbles left in the pile.\n", nimMarblesTaking, nimMarblesTaken, numberOfMarbles);
                                turn = 1; // Switch turns.
                                break;
                            case 1: // Process for the players turn.
                                System.out.println("It is your turn.");
                                if (maxMarblesToTake > 1) { // Changes response based on if there is only one marble left to take.
                                    System.out.printf("You may take from 1 to %d marbles.\n", maxMarblesToTake); // State how many marbles a user may take.
                                } else {
                                    System.out.println("You may take one marble."); // Outputs if the player can only  take one marble from the pile.
                                }

                                // Gets players response as to many many marbles they want to take.
                                System.out.println("How many marbles would you like to take?");
                                playerMarblesTaking = in.nextInt();

                                if (maxMarblesToTake == 0) { // If the computer does numberOfMarbles/2 and maxMarblesToTake equals 0 then make it so maxMarblesToTake is equal to one. This is so that the user can always at-least take one marble.
                                    maxMarblesToTake = 1;
                                }

                                if (playerMarblesTaking == 0 || playerMarblesTaking > maxMarblesToTake) { // This makes it so the player cannot take 0 marbles or more than their allotted amount to take.
                                    while (playerMarblesTaking == 0 || playerMarblesTaking > maxMarblesToTake) {
                                        System.out.println("How many marbles would you like to take?");
                                        playerMarblesTaking = in.nextInt();
                                    }
                                }

                                playerMarblesTaken += playerMarblesTaking; // Add the amount of marbles in the Players pile by the amount he is taking this turn.
                                numberOfMarbles -= playerMarblesTaking; // Negate the total amount of marbles by the amount of marbles the player is taking.
                                maxMarblesToTake = numberOfMarbles / 2; // Change the max amount of marbles a user may take next turn.

                                System.out.printf("You are taking %d marbles from the pile. You know have %d marbles total and there are %d marbles left in the pile.\n", playerMarblesTaking, playerMarblesTaken, numberOfMarbles);
                                turn = 0;
                                break;
                            default:
                                return;
                        }
                    } while (numberOfMarbles > 0);

                    switch (turn) { // Determines who loses the game based off of the turn number that the number of marbles hit zero.
                        case 0: // If the new turn after the game has ended is Dr. Nim's then the player has lost.
                            System.out.println("Dr. Nim out smarts you again! You Lose!");
                            break;
                        case 1: // If the new turn after the game has ended is the players then Dr. Nim has lost.
                            System.out.println("You have won!");
                            break;
                        default:
                            return;
                    }
                    break;
            }
            do { // Validate that 0 or 1 was entered for PLay Again
                System.out.print("Would you like to play again?\n"
                        + "Enter 1 for yes.\n"
                        + "Enter 0 for no.\n");
                playAgain = in.nextInt();

                if (playAgain == 0) { // If the user does not want to play again, tell them goodbye.
                    System.out.println("Okay! Goodbye!");
                }
            } while(playAgain != 0 && playAgain != 1);
        } while(playAgain == 1); // Play the game again if the user enters one.
    }
}
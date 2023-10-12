package com.github.mystery135.wordlearrayproject;
import com.github.dhiraj072.randomwordgenerator.RandomWordGenerator;
import java.util.Arrays;
import java.util.Scanner;

public class Wordle {

    public static final String ANSWER = RandomWordGenerator.getRandomWord().toUpperCase();//Generates a random word (uppercase)
    public static final int STARTING_GUESSES = 5;

    public static void main(String[] args) {
        int guessesLeft = STARTING_GUESSES;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Wordle!");
        System.out.println("After each guess, you will be able to see how close you were to the answer.");
        System.out.println("Letters that are correct will be in " + ChatColor.GREEN + "green" + ChatColor.RESET + ", letters that are correct but in the wrong place will be in " + ChatColor.YELLOW + "yellow" + ChatColor.RESET + ", and letters that are wrong will be in " + ChatColor.RED + "red" + ChatColor.RESET + ".");
        System.out.println();
        char[] correctLetters = new char[ANSWER.length()];
        Arrays.fill(correctLetters, '_');//fills correctLetters with '_'.
        do {
            System.out.println("Guesses remaining: " + ChatColor.RED + guessesLeft + ChatColor.RESET);
            System.out.print("Guess a " + ANSWER.length() + "-letter word: ");
            String guess = scanner.nextLine();//stores user's input as guess
            if (guess.length() != ANSWER.length()) {//checks if the guess's length is equal to the answer. If not, continue.
                System.out.println("Not a " + ANSWER.length() + "-letter word!");
                System.out.println();
                continue;
            }
            char[][] guessFeedback = guess(guess.toUpperCase(), ANSWER, correctLetters);//assigns guessFeedback to what was returned by guess. guessFeedback[0] is the color coded feedback while guessFeedback[1] is a list of the correct letters
            char[] guessColorcoded = guessFeedback[0];
            correctLetters = guessFeedback[1];

            System.out.println(guessColorcoded);
            System.out.println("Right letters: " + Arrays.toString(correctLetters));
            if (guess.equalsIgnoreCase(ANSWER)) {//breaks out of the loop if user guesses correctly
                break;
            }
            guessesLeft--;
            System.out.println();
        } while (guessesLeft > 0);

        //If the code breaks out of the do while, the user has to have either won or lost.
        System.out.println();
        if (guessesLeft <= 0) {
            System.out.println("You lost! The word was: " + ANSWER);
        } else {//user has to have won
            System.out.println("You won with " + guessesLeft + " guesses remaining!");
        }

    }

    private static char[][] guess(String guess, String answer, char[] correctLetters) {
        char[] guessCharArray = guess.toCharArray();
        char[] answerCharArray = answer.toCharArray();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < answer.length(); i++) {//loops through every letter in the guess
            if (guessCharArray[i] == answerCharArray[i]) {
                builder.append(ChatColor.GREEN);//if the letter is right, make it green, and add that letter to correctLetters.
                correctLetters[i] = guessCharArray[i];
            } else if (answer.contains(String.valueOf(guessCharArray[i]))) {
                builder.append(ChatColor.YELLOW);//if the letter is right, but in the wrong spot, make it yellow.
            } else {
                builder.append(ChatColor.RED);//if the letter is wrong, make it red.
            }
            builder.append(guessCharArray[i]).append(ChatColor.RESET);//adds the character, then the color reset code to the string builder so that the color code only affects 1 character.
        }
        return new char[][]{builder.toString().toCharArray(), correctLetters};
    }


    public enum ChatColor {//Enum class to manage letter colors
        RED("\u001B[31m"),
        GREEN("\u001B[32m"),
        YELLOW("\u001B[33m"),
        RESET("\u001B[0m");

        private final String code;

        ChatColor(String code) {
            this.code = code;
        }

        @Override
        public String toString() {//overrides the toString() method so that ChatColor.x returns the actual ANSI escape code.
            return code;
        }
    }

}

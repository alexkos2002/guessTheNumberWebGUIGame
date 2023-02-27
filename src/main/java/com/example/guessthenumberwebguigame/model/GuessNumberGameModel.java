package com.example.guessthenumberwebguigame.model;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component("guessNumberGameModel")
public class GuessNumberGameModel {
    private int numberToGuess;
    private int lowerBound;
    private int upperBound;

    private int totalAttemptsUsed;

    private final List<Integer> alreadyGuessedNumbers;
    private final Random random;

    public GuessNumberGameModel() {
        this.totalAttemptsUsed = 0;
        this.alreadyGuessedNumbers = new ArrayList<>();
        this.random = new Random();
    }

    public GuessNumberGameModel(int lowerBound, int upperBound) {
        this.totalAttemptsUsed = 0;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
        this.alreadyGuessedNumbers = new ArrayList<>();
        this.random = new Random();
    }

    public void initGame() {
        totalAttemptsUsed = 0;
        alreadyGuessedNumbers.clear();
    }

    public boolean areNumberBoundsCorrect(int lowerBound, int upperBound) {
        return upperBound > lowerBound;
    }

    public boolean isGuessedNumberInsideCorrectRange(int guessedNumber) {
        return guessedNumber >= lowerBound && guessedNumber < upperBound;
    }

    public boolean isGuessedNumberAlreadyTried(int guessedNumber) {
        return alreadyGuessedNumbers.contains(guessedNumber);
    }

    public GuessNumberResult guessNumber(int guessedNumber) {
        totalAttemptsUsed++;
        if (guessedNumber == numberToGuess) {
            return GuessNumberResult.EQUAL;
        } else {
            alreadyGuessedNumbers.add(guessedNumber);
            if (guessedNumber > numberToGuess) {
                return GuessNumberResult.GREATER_THAN;
            } else {
                return GuessNumberResult.LESS_THAN;
            }
        }
    }

    public void generateNumberToGuess() {
        this.numberToGuess = lowerBound + random.nextInt(upperBound - lowerBound);
    }

    public int getLowerBound() {
        return lowerBound;
    }

    public int getUpperBound() {
        return upperBound;
    }

    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }

    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }

    public int getTotalAttemptsUsed() {
        return totalAttemptsUsed;
    }

    public void setTotalAttemptsUsed(int totalAttemptsUsed) {
        this.totalAttemptsUsed = totalAttemptsUsed;
    }
}

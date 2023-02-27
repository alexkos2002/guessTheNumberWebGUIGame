package com.example.guessthenumberwebguigame.controller;

import com.example.guessthenumberwebguigame.dto.NumberBoundsDTO;
import com.example.guessthenumberwebguigame.model.GuessNumberGameModel;
import com.example.guessthenumberwebguigame.model.GuessNumberResult;
import com.example.guessthenumberwebguigame.view.GuessNumberGameMessagesGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/game")
public class GuessNumberGameController {

    GuessNumberGameModel guessNumberGameModel;

    GuessNumberGameMessagesGenerator messagesGenerator;

    @GetMapping
    public String showWelcomePage() {
        return "index";
    }

    @GetMapping("/customizeBounds")
    public String showCustomizeBoundsPage(@ModelAttribute("numberBoundsDTO") NumberBoundsDTO numBoundsDTO) {
        return "customizeBounds";
    }

    @PostMapping("/customizeBounds")
    public String createLowerUpperBounds(Model model, @ModelAttribute("numberBoundsDTO") NumberBoundsDTO numberBoundsDTO) {
        int lowerBound;
        int upperBound;
        try {
            lowerBound = Integer.parseInt(numberBoundsDTO.getLowerBound());
            upperBound = Integer.parseInt(numberBoundsDTO.getUpperBound());
        } catch (NumberFormatException e) {
            model.addAttribute("message", messagesGenerator.generateNotIntegerInputMessage());
            return "customizeBounds";
        }

        if (!guessNumberGameModel.areNumberBoundsCorrect(lowerBound, upperBound)) {
            model.addAttribute("message", messagesGenerator.generateUpperAndLowerBoundsIncorrectMessage());
            return "customizeBounds";
        }
        guessNumberGameModel.setLowerBound(lowerBound);
        guessNumberGameModel.setUpperBound(upperBound);
        return "redirect:/game/play";
    }

    @GetMapping("/play")
    public String showPlayGamePage() {
        guessNumberGameModel.initGame();
        guessNumberGameModel.generateNumberToGuess();
        return "game";
    }

    @PostMapping("/play")
    public String guessNumber(@RequestParam("guessedNumber") String guessedNumber,
                              Model model) {
        int parsedGuessedNumber;
        try {
            parsedGuessedNumber = Integer.parseInt(guessedNumber);
        } catch (NumberFormatException e) {
            model.addAttribute("message", messagesGenerator.generateNotIntegerInputMessage());
            return "game";
        }
        if (!guessNumberGameModel.isGuessedNumberInsideCorrectRange(parsedGuessedNumber)) {
            model.addAttribute("message",
                    messagesGenerator.generateNumberIsntInCorrectRangeMessage(guessNumberGameModel.getLowerBound(),
                            guessNumberGameModel.getUpperBound()));
            return "game";
        } else if (guessNumberGameModel.isGuessedNumberAlreadyTried(parsedGuessedNumber)) {
            model.addAttribute("message",
                    messagesGenerator.generateGuessedNumberAlreadyTriedMessage(parsedGuessedNumber));
            return "game";
        }
        GuessNumberResult curGuessedNumberResult = guessNumberGameModel.guessNumber(parsedGuessedNumber);
        switch (curGuessedNumberResult) {
            case GREATER_THAN:
                model.addAttribute("message",
                        messagesGenerator.generateYourNumberGreaterMessage());
                return "game";
            case LESS_THAN:
                model.addAttribute("message",
                        messagesGenerator.generateYourNumberLessMessage());
                return "game";
            case EQUAL:
                model.addAttribute("message", messagesGenerator
                        .generateYourNumberGuessedCorrectMessage(guessNumberGameModel.getTotalAttemptsUsed()));
                return "gameFinished";
        }
        return "game";
    }

    @Autowired
    public void setGuessNumberGameModel(GuessNumberGameModel guessNumberGameModel) {
        this.guessNumberGameModel = guessNumberGameModel;
    }

    @Autowired
    public void setMessagesGenerator(GuessNumberGameMessagesGenerator messagesGenerator) {
        this.messagesGenerator = messagesGenerator;
    }
}

package baseball.manager;

import baseball.config.GameConfig;
import baseball.domain.BaseballGame;
import baseball.domain.GuessResult;
import baseball.io.InputManager;
import baseball.io.OutputManager;

import java.util.List;

public class GameManager {

    private final BaseballGame baseballGame;
    private final InputManager inputManager;
    private final OutputManager outputManager;

    public GameManager() {
        this.baseballGame = new BaseballGame();
        this.inputManager = new InputManager();
        this.outputManager = new OutputManager();
    }

    public void run() {
        outputManager.printStartMessage();
        while (true) {
            if (!playGameAndHandleException()) {
                break;
            }
            if (!askForReplayAndHandleException()) {
                break;
            }
            baseballGame.resetGame();
        }
    }

    private boolean playGameAndHandleException() {
        try {
            playRound();
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private boolean askForReplayAndHandleException() {
        try {
            return askForReplay();
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private void playRound() {
        while (!baseballGame.isGameEnded()) {
            List<Integer> userInput = inputManager.readInput();
            GuessResult result = baseballGame.guess(userInput);
            outputManager.printGuessResult(result);
        }
        outputManager.printGameOverMessage();
    }

    private boolean askForReplay() {
        String userInput = inputManager.readReplayOrStopInput();
        return userInput.equals(String.valueOf(GameConfig.REPLAY.getValue()));
    }
}

package io.joshatron.tak.ai.player;

import io.joshatron.tak.engine.game.GameState;
import io.joshatron.tak.engine.game.Player;

public interface Evaluator {

    /**
     * This function evaluates the state of a player for a certain game state
     * @param state: the current state of the game
     * @param player: the player to evaluate from the perspective of
     * @return a number between -100 and 100 for how well the player is doing where negative is worse
     */
    double evaluate(GameState state, Player player);

    /**
     * This function determines what depth to go based on the size of the board
     * @param size: the size of the board
     * @return the depth to go down
     */
    int depthFromBoardSize(int size);
}

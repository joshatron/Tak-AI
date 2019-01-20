package io.joshatron.tak.ai.player;

import io.joshatron.tak.engine.game.GameState;
import io.joshatron.tak.engine.game.Player;

public class DefensiveEvaluator implements Evaluator {

    @Override
    public double evaluate(GameState state, Player player) {
        if(player == Player.WHITE) {
            return state.getWhitePoints() - state.getBlackPoints();
        }
        else {
            return state.getBlackPoints() - state.getWhitePoints();
        }
    }
}

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

    @Override
    public int depthFromBoardSize(int size) {
        switch (size) {
            case 3:
                return 5;
            case 4:
                return 4;
            case 5:
                return 3;
            case 6:
                return 3;
            case 8:
                return 2;
            default:
                return 2;
        }
    }
}

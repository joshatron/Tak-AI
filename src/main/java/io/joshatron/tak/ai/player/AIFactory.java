package io.joshatron.tak.ai.player;

import io.joshatron.tak.ai.player.evaluators.DefensiveEvaluator;
import io.joshatron.tak.ai.player.evaluators.PatternEvaluator;
import io.joshatron.tak.engine.game.Player;
import io.joshatron.tak.engine.player.TakPlayer;

public class AIFactory {

    public static TakPlayer createPlayer(AI ai, int size, Player player, Player first) {
        switch(ai) {
            case RANDOM:
                return new RandomPlayer();
            case DEFENSIVE_MINIMAX:
                return new MiniMaxPlayer(new DefensiveEvaluator(), size);
            case AGGRESIVE_MINIMAX:
                return new MiniMaxPlayer(new PatternEvaluator(), size);
            default:
                return null;
        }
    }
}

package io.joshatron.tak.ai.player;

import io.joshatron.tak.engine.game.Player;
import io.joshatron.tak.engine.player.TakPlayer;

public class AIFactory {

    public static TakPlayer createPlayer(AI ai, int size, Player player, Player first) {
        switch(ai) {
            case RANDOM:
                return new RandomPlayer();
            case DEFENSIVE_MINIMAX:
                return new MiniMaxPlayer(new DefensiveEvaluator(), size);
            default:
                return null;
        }
    }
}

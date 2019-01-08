package io.joshatron.tak.ai.player;

import io.joshatron.tak.engine.game.GameResult;
import io.joshatron.tak.engine.game.GameState;
import io.joshatron.tak.engine.game.Player;
import io.joshatron.tak.engine.player.TakPlayer;
import io.joshatron.tak.engine.turn.Turn;

public class MiniMaxPlayer implements TakPlayer {

    private Evaluator evaluator;
    private int depth;

    public MiniMaxPlayer(Evaluator evaluator, int depth) {
        this.evaluator = evaluator;
        this.depth = depth;
    }

    @Override
    public Turn getTurn(GameState state) {
        double alpha = -9999;
        double beta = 9999;

        double best = -9999;
        Turn bestTurn = null;
        for(Turn turn : state.getPossibleTurns()) {
            state.executeTurn(turn);
            double value = getTurnValue(state, false, state.getCurrentPlayer().other(), depth, alpha, beta);

            if(value > best) {
                best = value;
                bestTurn = turn;
            }

            state.undoTurn();
        }

        return bestTurn;
    }

    private double getTurnValue(GameState state, boolean max, Player player, int depth, double alpha, double beta) {
        GameResult result = state.checkForWinner();
        if(result.isFinished() && result.getWinner() == player) {
            return 999;
        }
        else if(result.isFinished()) {
            return -999;
        }

        if(depth == 0) {
            return evaluator.evaluate(state, player);
        }

        double extreme;
        if(max) {
            extreme = -9999;
        }
        else {
            extreme = 9999;
        }

        for(Turn turn : state.getPossibleTurns()) {
            state.executeTurn(turn);
            double value = getTurnValue(state, !max, player, depth - 1, alpha, beta);
            if(max) {
                if(value > extreme) {
                    extreme = value;
                    alpha = value;
                }
            }
            else {
                if(value < extreme) {
                    extreme = value;
                    beta = value;
                }
            }

            state.undoTurn();

            if(alpha >= beta) {
                return value;
            }
        }

        return extreme;
    }
}

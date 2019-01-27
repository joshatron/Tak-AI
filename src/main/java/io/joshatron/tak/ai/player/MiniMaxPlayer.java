package io.joshatron.tak.ai.player;

import io.joshatron.tak.engine.exception.TakEngineException;
import io.joshatron.tak.engine.game.GameResult;
import io.joshatron.tak.engine.game.GameState;
import io.joshatron.tak.engine.game.Player;
import io.joshatron.tak.engine.player.TakPlayer;
import io.joshatron.tak.engine.turn.Turn;

import java.util.ArrayList;
import java.util.Random;

public class MiniMaxPlayer implements TakPlayer {

    private Evaluator evaluator;
    private int depth;
    private Random rand;

    public MiniMaxPlayer(Evaluator evaluator, int size) {
        this.evaluator = evaluator;
        this.depth = evaluator.depthFromBoardSize(size);
        this.rand = new Random();
    }

    @Override
    public Turn getTurn(GameState state) {
        double alpha = -9999;
        double beta = 9999;

        double best = -9999;
        ArrayList<Turn> bestTurns = new ArrayList<>();
        for(Turn turn : state.getPossibleTurns()) {
            try {
                state.executeTurn(turn);
                double value = getTurnValue(state, false, state.getCurrentPlayer().opposite(), depth, alpha, beta);

                if(Math.abs(value - best) < .00001) {
                    bestTurns.add(turn);
                }
                else if(value > best) {
                    best = value;
                    bestTurns = new ArrayList<>();
                    bestTurns.add(turn);
                }

                state.undoTurn();
            } catch (TakEngineException e) {
                System.out.println(e.getCode());
                e.printStackTrace();
            }
        }

        if(bestTurns.size() > 1) {
            return bestTurns.get(rand.nextInt(bestTurns.size()));
        }
        else {
            return bestTurns.get(0);
        }
    }

    private double getTurnValue(GameState state, boolean max, Player player, int depth, double alpha, double beta) throws TakEngineException {
        GameResult result = state.checkForWinner();
        if(result.isFinished() && result.getWinner() == player) {
            return 999. + depth;
        }
        else if(result.isFinished()) {
            return -999. - depth;
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

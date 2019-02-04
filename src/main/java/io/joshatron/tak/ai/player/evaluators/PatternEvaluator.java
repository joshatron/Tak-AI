package io.joshatron.tak.ai.player.evaluators;

import io.joshatron.tak.engine.board.PieceType;
import io.joshatron.tak.engine.game.GameState;
import io.joshatron.tak.engine.game.Player;

public class PatternEvaluator implements Evaluator {

    @Override
    public double evaluate(GameState state, Player player) {
        return getPatternLevelForPlayer(state, player);
    }

    private double getPatternLevelForPlayer(GameState state, Player player) {
        double best = 0;

        for(int i = 0; i < state.getBoardSize(); i++) {
            for(int j = 0; j < state.getBoardSize(); j++) {
                int current = 0;
                for(int k = 0; k < state.getBoardSize(); k++) {
                    //check x
                    //Don't check the center yet
                    if(k != j) {
                        if(state.getBoard().getPosition(i, k).getStackOwner() == player &&
                           state.getBoard().getPosition(i, k).getTopPiece().getType() != PieceType.WALL) {
                            current++;
                        }
                        else if(state.getBoard().getPosition(i, k).getStackOwner() == player.opposite()) {
                            current -= 10;
                        }
                    }
                    //check y
                    //Don't check center yet
                    if(k != i) {
                        if(state.getBoard().getPosition(k, j).getStackOwner() == player &&
                           state.getBoard().getPosition(k, j).getTopPiece().getType() != PieceType.WALL) {
                            current++;
                        }
                        else if(state.getBoard().getPosition(k, j).getStackOwner() == player.opposite()) {
                            current -= 10;
                        }
                    }
                }

                //Just one missing excluding the center point, give extra points
                if(current > 0 && current == state.getBoardSize() - 2) {
                    current += 10;
                }

                if(current == state.getBoardSize() - 2 + 10 &&
                   state.getBoard().getPosition(i, j).getStackOwner() == player &&
                   state.getBoard().getPosition(i, j).getTopPiece().getType() != PieceType.WALL) {
                    current++;
                }
                else if(state.getBoard().getPosition(i, j).getStackOwner() == player.opposite()) {
                    current -= 10;
                }

                if(current > best) {
                    best = current;
                }

                //Ideal position where the player is guaranteed to win is boardSize - 2 + 10 + 1 or boardSize + 9
                if(best == state.getBoardSize() + 9) {
                    return best;
                }
            }
        }

        return best;
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

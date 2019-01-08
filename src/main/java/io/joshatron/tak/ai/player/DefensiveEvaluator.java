package io.joshatron.tak.ai.player;

import io.joshatron.tak.engine.board.GameBoard;
import io.joshatron.tak.engine.board.PieceStack;
import io.joshatron.tak.engine.board.PieceType;
import io.joshatron.tak.engine.game.GameState;
import io.joshatron.tak.engine.game.Player;

public class DefensiveEvaluator implements Evaluator {

    @Override
    public double evaluate(GameState state, Player player) {
        int whiteTotal = 0;
        int blackTotal = 0;

        GameBoard board = state.getBoard();

        for(int x = 0; x < board.getBoardSize(); x++) {
            for(int y = 0; y < board.getBoardSize(); y++) {
                PieceStack stack = board.getPosition(x, y);
                if(stack != null && stack.getTopPiece() != null) {
                    if (stack.getTopPiece().isWhite() && stack.getTopPiece().getType() == PieceType.STONE) {
                        whiteTotal++;
                    }
                    if (stack.getTopPiece().isBlack() && stack.getTopPiece().getType() == PieceType.STONE) {
                        blackTotal++;
                    }
                }
            }
        }

        if(player == Player.WHITE) {
            return whiteTotal - blackTotal;
        }
        else {
            return blackTotal - whiteTotal;
        }
    }
}

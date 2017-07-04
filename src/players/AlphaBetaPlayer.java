package players;

import game.AbstractPlayer;
import game.BoardSquare;
import game.Move;
import game.OthelloGame;

/**
 *
 * @author Kristof Dinkgräve
 * @author Jan Abelmann
 */
public class AlphaBetaPlayer extends AbstractPlayer {

    private OthelloGame game;
    private Move bestMove = null;

    public AlphaBetaPlayer(int depth){
        super(depth);
    }

    @Override
    public BoardSquare play(int[][] tab) {
        game = new OthelloGame();
        bestMove = null;
        alphaBeta(tab, getDepth(), getMyBoardMark(), Integer.MIN_VALUE, Integer.MAX_VALUE);
        if (bestMove != null) {
            return bestMove.getBoardPlace();
        } else {
            return new BoardSquare(-1, -1);
        }
    }

    private int alphaBeta(int[][] bord, int depth, int mark, int alpha, int beta) {
        if (depth == 0 || game.getValidMoves(bord, mark).size() == 0) {
            return evaluate(bord);
        }

        if (mark == getMyBoardMark()) {
            int best = alpha;
            for (Move move : game.getValidMoves(bord, getMyBoardMark())) {
                int temp = alphaBeta(move.getBoard(), depth - 1, getOpponentBoardMark(), best, beta);
                if (best < temp) {
                    best = temp;
                    if (best >= beta) {
                        break;
                    }
                    if (depth == getDepth()) {
                        bestMove = move;
                    }
                }
            }
            return best;
        } else {
            int best = beta;
            for (Move move : game.getValidMoves(bord, getOpponentBoardMark())) {
                int temp = alphaBeta(move.getBoard(), depth - 1, getMyBoardMark(), alpha, best);
                if (best > temp) {
                    best = temp;
                    if (best <= alpha) {
                        break;
                    }
                }

            }
            return best;
        }

    }

    private int evaluate(int[][] bord) {
        int myMark = 0;
        int opponentMark = 0;
        for (int[] row : bord) {
            for (int field : row) {
                if (field == getMyBoardMark()) {
                    myMark++;
                } else if (field == getOpponentBoardMark()) {
                    opponentMark++;
                }
            }
        }

        myMark = myMark + corner(bord, getMyBoardMark());
        opponentMark = opponentMark + corner(bord, getOpponentBoardMark());

        myMark = myMark + edge(bord, getMyBoardMark());
        opponentMark = opponentMark + edge(bord, getOpponentBoardMark());

        return myMark - opponentMark;
    }

    private int corner(int[][] bord, int markBord) {
        int mark = 0;
        int corner = 9;
        int subCorner = 5;

        if (bord[0][0] == markBord) {
            mark = mark + corner;
        }
        if (bord[0][1] == markBord) {
            mark = mark + subCorner;
        }
        if (bord[1][0] == markBord) {
            mark = mark + subCorner;
        }

        if (bord[0][bord[0].length - 1] == markBord) {
            mark = mark + corner;
        }
        if (bord[0][bord[0].length - 2] == markBord) {
            mark = mark + subCorner;
        }
        if (bord[1][bord[0].length - 1] == markBord) {
            mark = mark + subCorner;
        }

        if (bord[bord.length - 1][0] == markBord) {
            mark = mark + corner;
        }
        if (bord[bord.length - 1][1] == markBord) {
            mark = mark + subCorner;
        }
        if (bord[bord.length - 2][0] == markBord) {
            mark = mark + subCorner;
        }

        if (bord[bord.length - 1][bord[0].length - 1] == markBord) {
            mark = mark + corner;
        }
        if (bord[bord.length - 1][bord[0].length - 2] == markBord) {
            mark = mark + subCorner;
        }
        if (bord[bord.length - 2][bord[0].length - 1] == markBord) {
            mark = mark + subCorner;
        }

        return mark;
    }

    private int edge(int[][] bord, int markBord) {
        int mark = 0;
        for (int i = 1; i < bord.length - 1; i++) {
            if (bord[i][0] == markBord) {
                mark = mark + 2;
            }
            if (bord[i][bord[0].length - 1] == markBord) {
                mark = mark + 2;
            }
        }
        for (int i = 1; i < bord[0].length - 1; i++) {
            if (bord[0][i] == markBord) {
                mark = mark + 2;
            }
            if (bord[bord.length-1][i] == markBord) {
                mark = mark + 2;
            }
        }

        return mark;
    }
}

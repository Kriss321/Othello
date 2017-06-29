package players;

import game.AbstractPlayer;
import game.BoardSquare;
import game.Move;
import game.OthelloGame;

/**
 * Created on 29.06.17.
 *
 * @author Kristof Dinkgräve
 */
public class MinimaxPlayer extends AbstractPlayer {

    private OthelloGame game;
    private Move bestMove = null;

    public MinimaxPlayer(int depth) {
        super(depth);
    }

    @Override
    public BoardSquare play(int[][] tab) {
        game = new OthelloGame();
        long time = System.currentTimeMillis();
        minimax(tab, getDepth(), getMyBoardMark());
        System.out.println(System.currentTimeMillis()-time + "ms");
        return bestMove.getBoardPlace();
    }

    private int minimax(int[][] bord, int depth, int mark) {
        if (depth == 0 || game.getValidMoves(bord, mark).size() == 0) {
            return evaluate(bord);
        }

        if (mark == getMyBoardMark()) {
            int best = Integer.MIN_VALUE;
            for (Move move : game.getValidMoves(bord, getMyBoardMark())) {
                int temp = minimax(move.getBoard(), depth - 1, getOpponentBoardMark());
                if (best < temp) {
                    best = temp;
                    if (depth == getDepth()) {
                        bestMove = move;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (Move move : game.getValidMoves(bord, getOpponentBoardMark())) {
                int temp = minimax(move.getBoard(), depth - 1, getMyBoardMark());
                best = Math.min(best, temp);
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
        if (bord[0][0] == markBord) {
            mark = mark + 6;
        }
        if (bord[0][1] == markBord) {
            mark = mark + 4;
        }
        if (bord[1][0] == markBord) {
            mark = mark + 4;
        }

        if (bord[0][bord[0].length - 1] == markBord) {
            mark = mark + 6;
        }
        if (bord[0][bord[0].length - 2] == markBord) {
            mark = mark + 4;
        }
        if (bord[1][bord[0].length - 1] == markBord) {
            mark = mark + 4;
        }

        if (bord[bord.length - 1][0] == markBord) {
            mark = mark + 6;
        }
        if (bord[bord.length - 1][1] == markBord) {
            mark = mark + 4;
        }
        if (bord[bord.length - 2][0] == markBord) {
            mark = mark + 4;
        }

        if (bord[bord.length - 1][bord[0].length - 1] == markBord) {
            mark = mark + 6;
        }
        if (bord[bord.length - 1][bord[0].length - 2] == markBord) {
            mark = mark + 4;
        }
        if (bord[bord.length - 2][bord[0].length - 1] == markBord) {
            mark = mark + 4;
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
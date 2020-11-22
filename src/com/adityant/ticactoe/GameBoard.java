package com.adityant.ticactoe;


import java.util.ArrayList;
import java.util.List;

class GameBoard {

    List<Tile> availableTiles;
    int[][] board = new int[3][3];

    enum Result {XWON, OWON, DRAW};

    List<TilesAndScores> rootsChildrenScore = new ArrayList<>();

    public int evaluateBoard() {
        int score = 0;

        //Check all rows
        for (int i = 0; i < 3; ++i) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    blank++;
                } else if (board[i][j] == 1) {
                    X++;
                } else {
                    O++;
                }

            }
            score+=changeInScore(X, O);
        }

        //Check all columns
        for (int j = 0; j < 3; ++j) {
            int blank = 0;
            int X = 0;
            int O = 0;
            for (int i = 0; i < 3; ++i) {
                if (board[i][j] == 0) {
                    blank++;
                } else if (board[i][j] == 1) {
                    X++;
                } else {
                    O++;
                }
            }
            score+=changeInScore(X, O);
        }

        int blank = 0;
        int X = 0;
        int O = 0;

        //Check diagonal (first)
        for (int i = 0, j = 0; i < 3; ++i, ++j) {
            if (board[i][j] == 1) {
                X++;
            } else if (board[i][j] == 2) {
                O++;
            } else {
                blank++;
            }
        }

        score+=changeInScore(X, O);

        blank = 0;
        X = 0;
        O = 0;

        //Check Diagonal (Second)
        for (int i = 2, j = 0; i > -1; --i, ++j) {
            if (board[i][j] == 1) {
                X++;
            } else if (board[i][j] == 2) {
                O++;
            } else {
                blank++;
            }
        }

        score+=changeInScore(X, O);

        return score;
    }

    private int changeInScore(int X, int O){
        int change;
        if (X == 3) {
            change = 100;
        } else if (X == 2 && O == 0) {
            change = 10;
        } else if (X == 1 && O == 0) {
            change = 1;
        } else if (O == 3) {
            change = -100;
        } else if (O == 2 && X == 0) {
            change = -10;
        } else if (O == 1 && X == 0) {
            change = -1;
        } else {
            change = 0;
        }
        return change;
    }

    //Set this to some value if you want to have some specified depth limit for search
    int uptoDepth = 4;

    public int alphaBetaMinimax(int alpha, int beta, int depth, int turn){

        if(beta<=alpha){ System.out.println("Pruning at depth = "+depth);if(turn == 1) return Integer.MAX_VALUE; else return Integer.MIN_VALUE; }

        if(depth == uptoDepth || isGameOver()) return evaluateBoard();

        List<Tile> states = getAvailableStates();

        if(states.isEmpty()) return 0;

        if(depth==0) rootsChildrenScore.clear();

        int maxValue = Integer.MIN_VALUE, minValue = Integer.MAX_VALUE;

        for(int i=0;i<states.size(); ++i){
            Tile tile = states.get(i);

            int currentScore = 0;

            if(turn == 1){
                placeAMove(tile, 1);
                currentScore = alphaBetaMinimax(alpha, beta, depth+1, 2);
                maxValue = Math.max(maxValue, currentScore);

                //Set alpha
                alpha = Math.max(currentScore, alpha);

                if(depth == 0)
                    rootsChildrenScore.add(new TilesAndScores(currentScore, tile));
            }else if(turn == 2){
                placeAMove(tile, 2);
                currentScore = alphaBetaMinimax(alpha, beta, depth+1, 1);
                minValue = Math.min(minValue, currentScore);

                //Set beta
                beta = Math.min(currentScore, beta);
            }
            //reset board
            board[tile.x][tile.y] = 0;

            //If a pruning has been done, don't evaluate the rest of the sibling states
            if(currentScore == Integer.MAX_VALUE || currentScore == Integer.MIN_VALUE) break;
        }
        return turn == 1 ? maxValue : minValue;
    }

    public boolean isGameOver() {
        //Game is over is someone has won, or board is full (draw)
        return (hasXWon() || hasOWon() || getAvailableStates().isEmpty());
    }


    public Result getGameResult() {
        //Game is over is someone has won, or board is full (draw)

        if(hasXWon()){
            return Result.XWON;
        }
        else if(hasOWon()){
            return Result.OWON;
        }
        else {
            return Result.DRAW;
        }

    }

    public boolean hasXWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 1) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 1)) {
            //System.out.println("X Diagonal Win");
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if (((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 1)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 1))) {
                // System.out.println("X Row or Column win");
                return true;
            }
        }
        return false;
    }

    public boolean hasOWon() {
        if ((board[0][0] == board[1][1] && board[0][0] == board[2][2] && board[0][0] == 2) || (board[0][2] == board[1][1] && board[0][2] == board[2][0] && board[0][2] == 2)) {
            return true;
        }
        for (int i = 0; i < 3; ++i) {
            if ((board[i][0] == board[i][1] && board[i][0] == board[i][2] && board[i][0] == 2)
                    || (board[0][i] == board[1][i] && board[0][i] == board[2][i] && board[0][i] == 2)) {
                return true;
            }
        }

        return false;
    }

    public List<Tile> getAvailableStates() {
        availableTiles = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                if (board[i][j] == 0) {
                    availableTiles.add(new Tile(i, j));
                }
            }
        }
        return availableTiles;
    }

    public void placeAMove(Tile tile, int player) {
        board[tile.x][tile.y] = player;   //player = 1 for X, 2 for O
    }

    public Tile returnBestMove() {
        int MAX = -100000;
        int best = -1;

        for (int i = 0; i < rootsChildrenScore.size(); ++i) {
            if (MAX < rootsChildrenScore.get(i).score) {
                MAX = rootsChildrenScore.get(i).score;
                best = i;
            }
        }

        return rootsChildrenScore.get(best).tile;
    }




    public void displayBoard() {
        System.out.println();

        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {

                System.out.print(board[i][j]+" ");
                if(board[i][j]==0){
                }
                else if(board[i][j]==1) {
                    GameWindow.button[i * 3 + j].setIcon(GridButton.iconX);
                    GameWindow.button[i * 3 + j].setDisabledIcon(GridButton.iconX);
                }
                else if(board[i][j]==2) {
                    GameWindow.button[i * 3 + j].setIcon(GridButton.iconO);
                    GameWindow.button[i * 3 + j].setDisabledIcon(GridButton.iconO);
                }
            }
            System.out.println();

        }
    }




    public void resetBoard() {
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                board[i][j] = 0;
            }
        }
    }
}
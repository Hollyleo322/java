package datasource.repository;

import datasource.constants.Constants;
import datasource.model.DSCurrentGame;
import datasource.model.DSGameField;
import domain.model.Move;
import domain.service.Service;

import java.util.Vector;

@org.springframework.stereotype.Service
public class RepositoryService implements Service {
    final private DSRepository _repository;

    public RepositoryService(DSRepository repository)
    {
        _repository = repository;
    }

    public Move getMove(int uuid)
    {
        Move result = new Move();
        minMax(_repository.findById(uuid).get(), Constants.AI, result);
        return result;
    }
    private boolean isWinner(DSCurrentGame currentGame, int player) {
        return checkHorizontal(currentGame, player) || checkVertical(currentGame, player) || checkDiagonal(currentGame, player);
    }

    private boolean checkHorizontal(DSCurrentGame currentGame, int player) {
        boolean res = false;
        for (int i = 0; i < Constants.ROWSANDCOLS; i++) {
            boolean check = true;
            for (int j = 0; j < Constants.ROWSANDCOLS; j++) {
                if (currentGame.getGameField().getField()[i][j] != player) {
                    check = false;
                    break; // Можно сразу прервать внутренний цикл
                }
            }
            if (check) {
                res = true;
                break;
            }
        }
        return res;
    }

    private boolean checkVertical(DSCurrentGame currentGame, int player) {
        boolean res = false;
        for (int j = 0; j < Constants.ROWSANDCOLS; j++) {
            boolean check = true;
            for (int i = 0; i < Constants.ROWSANDCOLS; i++) {
                if (currentGame.getGameField().getField()[i][j] != player) {
                    check = false;
                    break; // Можно сразу прервать внутренний цикл
                }
            }
            if (check) {
                res = true;
                break;
            }
        }
        return res;
    }

    private boolean checkDiagonal(DSCurrentGame currentGame, int player) {
        int[][] field = currentGame.getGameField().getField();
        // Проверка главной диагонали
        if (field[0][0] == player && field[1][1] == player && field[2][2] == player) {
            return true;
        }
        // Проверка побочной диагонали
        if (field[0][2] == player && field[1][1] == player && field[2][0] == player) {
            return true;
        }
        return false;
    }

    private Vector<Integer> getEmptyIndexes(DSCurrentGame currentGame) {
        Vector<Integer> res = new Vector<>();
        int[][] field = currentGame.getGameField().getField();
        for (int i = 0; i < Constants.ROWSANDCOLS; i++) {
            for (int j = 0; j < Constants.ROWSANDCOLS; j++) {
                if (field[i][j] == 0) {
                    res.add(Constants.ROWSANDCOLS * i + j);
                }
            }
        }
        return res;
    }

    private int minMax(DSCurrentGame currentGame, int player, Move bestMove) {
        // Проверка победы для обоих игроков
        if (isWinner(currentGame, Constants.PLAYER)) {
            return -10;
        } else if (isWinner(currentGame, Constants.AI)) {
            return 10;
        }

        Vector<Integer> emptyIndexes = getEmptyIndexes(currentGame);

        // Если нет свободных клеток и никто не выиграл - ничья
        if (emptyIndexes.isEmpty()) {
            return 0;
        }

        Vector<Move> moves = new Vector<>();

        for (Integer emptyIndex : emptyIndexes) {
            Move move = new Move();
            move.setI(emptyIndex / Constants.ROWSANDCOLS);
            move.setJ(emptyIndex % Constants.ROWSANDCOLS);

            // Сделать ход текущим игроком
            makeMove(move, player, currentGame);

            // Рекурсивный вызов minMax для другого игрока
            int score;
            if (player == Constants.AI) {
                score = minMax(currentGame, Constants.PLAYER, bestMove);
            } else {
                score = minMax(currentGame, Constants.AI, bestMove);
            }

            move.setScore(score);

            // Отменить ход
            makeMove(move, 0, currentGame);

            moves.add(move);
        }

        // Выбор лучшего хода в зависимости от текущего игрока
        int bestMoveIndex = getBestMove(player, moves);
        bestMove.setI(moves.get(bestMoveIndex).getI());
        bestMove.setJ(moves.get(bestMoveIndex).getJ());
        return moves.get(bestMoveIndex).getScore();
    }

    private static int getBestMove(int player, Vector<Move> moves) {
        int bestIndex = 0;

        if (player == Constants.AI) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getScore() > bestScore) {
                    bestScore = moves.get(i).getScore();
                    bestIndex = i;
                }
            }
        } else { // Игрок минимизирует результат
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < moves.size(); i++) {
                if (moves.get(i).getScore() < bestScore) {
                    bestScore = moves.get(i).getScore();
                    bestIndex = i;
                }
            }
        }

        return bestIndex;
    }

    public void makeMove(Move move, int player, DSCurrentGame game) {
        game.getGameField().getField()[move.getI()][move.getJ()] = player;
    }
    public boolean isChangedPreviousMoves(int uiid, DSGameField gameField)
    {
        boolean res = false;
        DSGameField currentField = _repository.findById(uiid).get().getGameField();
        int countDifference = 0;
        for (int i = 0; i < Constants.ROWSANDCOLS; i++)
        {
            for (int j = 0 ; j < Constants.ROWSANDCOLS; j++)
            {
                if (currentField.getField()[i][j] != gameField.getField()[i][j])
                {
                     countDifference++;
                }
            }
        }
        if (countDifference != 1)
        {
            res = true;
        }
        return res;
    }
    public boolean isEndOfGame(int uuid)
    {
        return isWinner(_repository.findById(uuid).get(), Constants.AI) || isWinner(_repository.findById(uuid).get(), Constants.PLAYER) || isWinner(_repository.findById(uuid).get(), Constants.SECOND_PLAYER);
    }
    public boolean isTie(int uuid)
    {
        boolean res = true;
        int [][] field = _repository.findById(uuid).get().getGameField().getField();
        for (int i = 0; i < Constants.ROWSANDCOLS && res; i++)
        {
            for (int j = 0; j < Constants.ROWSANDCOLS; j++)
            {
                if (field[i][j] == 0)
                {
                    res = false;
                    break;
                }
            }
        }
        return res;
    }
    public boolean incorrectData(int uuid)
    {
        boolean res = false;
        int [][] field = _repository.findById(uuid).get().getGameField().getField();
        for (int i = 0; i < Constants.ROWSANDCOLS && !res; i++)
        {
            for (int j = 0; j < Constants.ROWSANDCOLS; j++)
            {
                if (field[i][j] != 1 && field[i][j] != 10 && field[i][j] != 0 && field[i][j] != 20)
                {
                    res = true;
                    break;
                }
            }
        }
        return res;
    }
}


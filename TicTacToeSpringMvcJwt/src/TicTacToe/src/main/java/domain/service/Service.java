package domain.service;
import datasource.model.DSGameField;
import domain.model.Move;

import java.util.Vector;

public interface Service {
    Move getMove(int player);
    boolean isChangedPreviousMoves(int uiid, DSGameField gameField);
    boolean isEndOfGame(int uiid);
}

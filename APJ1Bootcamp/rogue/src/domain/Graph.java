package domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Random;

/**
 * Класс, являющийся графом, для создания связей комнат, и проверкой обходом в
 * ширину, что граф связный
 */
public class Graph {
    private List<List<Integer>> _connectedRooms;

    /**
     * Инициализация связей комнат
     */

    private void init() {
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            if (i % 3 != 2) {
                _connectedRooms.get(i).add(i + 1);
            }
            if (i < 6) {
                _connectedRooms.get(i).add(i + Constants.ROOMS_IN_WIDTH);
            }
            _connectedRooms.get(i).sort(null);
        }
    }

    /**
     * Рандомно обрезаем связи, максимум 4, потому что по правилам граф связный при
     * n - 1 рёбер
     */
    private void cut() {
        Random rnd = new Random();
        int howMuchCut = rnd.nextInt(5);
        for (int i = 0; i < howMuchCut; i++) {
            int index = rnd.nextInt(Constants.ROOMS_NUM - 2);
            int removed = 0;
            if (_connectedRooms.get(index).size() > 1) {
                removed = _connectedRooms.get(index).remove(rnd.nextInt(1));
            } else if (_connectedRooms.get(index).size() == 1) {
                removed = _connectedRooms.get(index).remove(0);
            } else {
                i--;
            }
            if (!isGraphConnected()) {
                _connectedRooms.get(index).add(removed);
                i--;
            }
        }
    }

    /**
     * Обходом в ширину проверяем, что граф связный
     * 
     * @return true если связный, иначе false
     */
    private boolean isGraphConnected() {
        boolean res = true;
        Deque<Integer> q = new ArrayDeque<>();
        List<Integer> visited = new ArrayList<>();
        q.add(0);
        while (!q.isEmpty()) {
            int current = q.removeFirst();
            visited.add(current);
            for (int neighbour : _connectedRooms.get(current)) {
                if (!visited.contains(neighbour)) {
                    q.add(neighbour);
                }
            }
        }
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            if (!visited.contains(i)) {
                res = false;
            }
        }
        return res;
    }

    /**
     * Конструктор
     */

    public Graph() {
        _connectedRooms = new ArrayList<>();
        for (int i = 0; i < Constants.ROOMS_NUM; i++) {
            _connectedRooms.add(new ArrayList<Integer>());
        }
        init();
        cut();
    }

    /**
     * Геттер размера
     * 
     * @return размер графа (количество рёбер)
     */
    public int size() {
        int counter = 0;
        for (List<Integer> neighbours : _connectedRooms) {
            counter += neighbours.size();
        }
        return counter;
    }

    /**
     * Геттер графа
     * 
     * @return Список соседей каждой вершины графа
     */
    public List<List<Integer>> getConnectedRooms() {
        return _connectedRooms;
    }
}

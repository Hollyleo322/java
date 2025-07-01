package datalayer;

import java.io.File;
import java.io.IOException;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Data {
    private String _name = "";
    private int _countTreasures = 0;
    private int _lvl = 1;
    private int _destroyedEnemies = 0;
    private int _eatedFood = 0;
    private int _drinkedElixirs = 0;
    private int _readedScrolls = 0;
    private int _countHits = 0;
    private int _countMisses = 0;
    private int _countSteps = 0;

    public void writeDataJson() {
        ObjectMapper mapper = new ObjectMapper();
        Data tmp = new Data();
        File file = new File("stat.json");
        if (_lvl > 2 && file.exists()) {
            try {
                tmp = mapper.readValue(file, Data.class);
            } catch (IOException e) {
                System.out.println("Cannot open stat.json");
            }
            _countTreasures += tmp._countTreasures;
            _lvl = tmp._lvl;
            _destroyedEnemies += tmp._destroyedEnemies;
            _eatedFood += tmp._eatedFood;
            _drinkedElixirs += tmp._drinkedElixirs;
            _readedScrolls += tmp._readedScrolls;
            _countHits += tmp._countHits;
            _countMisses += tmp._countMisses;
            _countSteps += tmp._countSteps;
        }
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(file, this);
        } catch (IOException e) {
            System.out.println("Cannot write to stat.json");
        }
    }

    public void writeGeneralStats() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("general_stats.json");
        File fileLast = new File("stat.json");
        List<Data> dataList = new ArrayList<>();
        boolean flag = false;
        if (file.exists()) {
            try {
                dataList = mapper.readValue(file, new TypeReference<List<Data>>() {
                });
                flag = true;
            } catch (IOException e) {
                System.out.println("Cannot open general_stats.json");
            }
        }
        if (fileLast.exists()) {
            try {
                Data tmp = mapper.readValue(fileLast, Data.class);
                dataList.add(tmp);
            } catch (IOException ignore) {
            }
        }
        if (file.exists()) {
            try {
                dataList.sort(new DataComparator());
                if (dataList.size() > 10) {
                    dataList.subList(10, dataList.size()).clear();
                }
                mapper.writerWithDefaultPrettyPrinter().writeValue(file, dataList);
            } catch (IOException e) {
                System.out.println("Cannot write to general_stats.json");
            }
        }
    }

    public List<Data> ListData() {
        ObjectMapper mapper = new ObjectMapper();
        File file = new File("general_stats.json");
        List<Data> dataList = new ArrayList<>();
        if (file.exists()) {
            try {
                dataList = mapper.readValue(file, new TypeReference<List<Data>>() {
                });
            } catch (IOException e) {
                System.out.println("Cannot open general_stats.json");
            }
        }
        return dataList;
    }

    public String getName() {
        return _name;
    }

    public void setName(String name) {
        _name = name;
    }

    public int getLvl() {
        return _lvl;
    }

    public void setLvl(int lvl) {
        _lvl = lvl;
    }

    public int getCountTreasures() {
        return _countTreasures;
    }

    public void setCountTreasures(int countTreasures) {
        _countTreasures = countTreasures;
    }

    public int getDestroyedEnemies() {
        return _destroyedEnemies;
    }

    public void setDestroyedEnemies(int destroyedEnemies) {
        _destroyedEnemies = destroyedEnemies;
    }

    public int getEatedFood() {
        return _eatedFood;
    }

    public void setEatedFood(int eatedFood) {
        _eatedFood = eatedFood;
    }

    public int getDrinkedElixirs() {
        return _drinkedElixirs;
    }

    public void setDrinkedElixirs(int drinkedElixirs) {
        _drinkedElixirs = drinkedElixirs;
    }

    public int getReadedScrolls() {
        return _readedScrolls;
    }

    public void setReadedScrolls(int readedScrolls) {
        _readedScrolls = readedScrolls;
    }

    public int getCountHits() {
        return _countHits;
    }

    public void setCountHits(int countHits) {
        _countHits = countHits;
    }

    public int getCountMisses() {
        return _countMisses;
    }

    public void setCountMisses(int countMisses) {
        _countMisses = countMisses;
    }

    public int getCountSteps() {
        return _countSteps;
    }

    public void setCountSteps(int countSteps) {
        _countSteps = countSteps;
    }
}

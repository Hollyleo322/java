import java.awt.*;
import java.util.concurrent.TimeUnit;

import jcurses.system.Toolkit;
import presentation.*;
import java.io.IOException;
import javax.tools.Tool;
import domain.*;

public class Main {
    public static void main(String[] args) {
        Toolkit.init();
        Controller controller = new Controller();
        boolean flag = true;
        StartData data = Presentation.outputStartMenu();
        if (data.IsLoad()) {
            controller.load();
        } else {
            controller.genLevel();
        }
        controller.setName(data.getName());
        while (flag) {
            Presentation.outputRooms(controller.getArrayRooms(), controller.inIntrance());
            Presentation.drawCorridors(controller.getCorridors());
            Presentation.outputItems(controller.getArrayRooms());
            Presentation.outputEnemies(controller.getArrayRooms(),
                    controller.inIntrance());
            Presentation.outputСharacteristics(controller);
            Presentation.outputElixirsinfo(controller);
            Presentation.outputExit(controller.getExit());
            Presentation.fog(controller.getArrayRooms(), controller.inIntrance());
            Presentation.fogCorridor(controller.getCorridors());
            Presentation.outputEntrances(controller.getArrayRooms());
            Presentation.outputPlayer(controller.getPlayerPos());
            flag = controller.processUserInput();
        }
        Toolkit.shutdown();
    }

}

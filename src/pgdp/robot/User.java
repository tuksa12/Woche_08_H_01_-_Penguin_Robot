package pgdp.robot;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class User {
    public static void main(String[] args) {
        //run the simulation
        String map = "################\n" +
                "#  #0     1    #\n" +
                "#  #   ##   #  #\n" +
                "#  ###   #T #  #\n" +
                "#   3# a ### W #\n" +
                "# ###   # 2   ##\n" +
                "#      #       #\n" +
                "###########$$$##";
        World world = new World(map);
        new Robot("Pengu", Math.toRadians(90), 1).spawnInWorld(world, '3');
        world.run();
    }
}

package pgdp.robot;

import java.awt.event.KeyEvent;
import java.util.*;

public class User {
    public static  Robot makeLineFollower(){
        //Creation of a robot and a sensor
        Robot lineFollowerPenguin = new Robot("Robot",0,0.5);
        TerrainSensor terrainSensor = new TerrainSensor();
        lineFollowerPenguin.attachSensor(terrainSensor);
        //Program
        lineFollowerPenguin.setProgram(robot -> {
            //Variable for the first step, the robot is no standing on top of "v,^,<,>", to be used in the loop
            int z = 1;
            //Variables for position and direction
            Position firstPosition = lineFollowerPenguin.getPosition();
            double currentX = firstPosition.x;
            double currentY = firstPosition.y;
            double firstDirection = lineFollowerPenguin.getDirection();
            double currentDirection = firstDirection;
            //Command list
            List<Command> commands = new ArrayList<>();
            if(terrainSensor.getData()!='v' || terrainSensor.getData()!='<'||terrainSensor.getData()!='>'||terrainSensor.getData()!='^'){
                commands.add(r -> r.go(0.2));//First step
            }
            //Loop for the line (my robot only managed to run half of the way)
            while(firstPosition.x != currentX && firstPosition.y != currentY || z != 0){
                if (currentDirection == 0){
                    if(terrainSensor.getData() == '>'){
                        commands.add(r -> r.go(0.2));
                        currentX +=1;
                    } else if(terrainSensor.getData() =='v'){
                        commands.add(r -> r.turnTo(Math.PI/2));
                        currentDirection = Math.PI/2;
                    } else if(terrainSensor.getData() =='<'){
                        commands.add(r -> r.turnTo(Math.PI));
                        currentDirection = Math.PI;
                    } else if(terrainSensor.getData() =='^'){
                        commands.add(r -> r.turnTo((3*Math.PI/2)));
                        currentDirection = 3*Math.PI/2;
                    }
                } else if (currentDirection == Math.PI/2){
                    if(terrainSensor.getData() =='v'){
                        commands.add(r -> r.go(0.2));
                        currentY +=1;
                    } else if(terrainSensor.getData() == '>'){
                        commands.add(r -> r.turnTo(0));
                        currentDirection = 0;
                    } else if(terrainSensor.getData() =='<'){
                        commands.add(r -> r.turnTo(Math.PI));
                        currentDirection = Math.PI;
                    } else if(terrainSensor.getData() =='^'){
                        commands.add(r -> r.turnTo((3*Math.PI/2)));
                        currentDirection = 3*Math.PI/2;
                    }
                } else if (currentDirection == Math.PI){
                    if(terrainSensor.getData() =='<'){
                        commands.add(r -> r.go(0.2));
                        currentX -=1;
                    } else if(terrainSensor.getData() == '>'){
                        commands.add(r -> r.turnTo(0));
                        currentDirection = 0;
                    } else if(terrainSensor.getData() =='v'){
                        commands.add(r -> r.turnTo(Math.PI/2));
                        currentDirection = Math.PI/2;
                    } else if(terrainSensor.getData() =='^'){
                        commands.add(r -> r.turnTo((3*Math.PI/2)));
                        currentDirection = 3*Math.PI/2;
                    }
                } else if (currentDirection == (3*Math.PI/2)){
                    if(terrainSensor.getData() =='^'){
                        commands.add(r -> r.go(0.2));
                        currentY -=1;
                    } else if(terrainSensor.getData() == '>'){
                        commands.add(r -> r.turnTo(0));
                        currentDirection = 0;
                    } else if(terrainSensor.getData() =='v'){
                        commands.add(r -> r.turnTo(Math.PI/2));
                        currentDirection = Math.PI/2;
                    } else if(terrainSensor.getData() =='<'){
                        commands.add(r -> r.turnTo(Math.PI));
                        currentDirection = Math.PI;
                    }
                }
                z = 0;
            }
            return commands;
        });
    return lineFollowerPenguin;}

    public static Robot makeMazeRunner(){//Right-Hand Rule
        //Creation of a robot and a sensor
        Robot mazeRunner = new Robot("Maze Runner",0,0.5);
        TerrainSensor read = new TerrainSensor();
        mazeRunner.attachSensor(read);
        //Memory
        mazeRunner.createMemory(new Memory<>("terrain",""));
        //Program
        mazeRunner.setProgram(robot -> {
            //Commands list
            List<Command> commands = new ArrayList<>();
            //Positions variables for later
            double direction = mazeRunner.getDirection();
            Position current = mazeRunner.getPosition();
            //Loop until the sensor reads '#'
            while (true) {
                if (read.getData() == '#') {
                    break;
                } else {
                    if (direction == 0) {
                        if (mazeRunner.getWorld().getTerrain(current.x , current.y+1) == '#') {
                            commands.add(r -> r.go(0.2));
                        } else {
                            commands.add(r -> r.turnBy(Math.PI / 2));
                            direction = Math.PI / 2;
                        }
                    } else if (direction == Math.PI / 2) {
                        if (mazeRunner.getWorld().getTerrain(current.x-1, current.y ) == '#') {
                            commands.add(r -> r.go(0.2));
                        } else {
                            commands.add(r -> r.turnBy(Math.PI / 2));
                            direction = Math.PI;
                        }
                    } else if (direction == Math.PI) {
                        if (mazeRunner.getWorld().getTerrain(current.x , current.y-1) == '#') {
                            commands.add(r -> r.go(0.2));
                        } else {
                            commands.add(r -> r.turnBy(Math.PI / 2));
                            direction = 3 * Math.PI / 2;
                        }
                    } else if(direction == 3 * Math.PI / 2){
                        if (mazeRunner.getWorld().getTerrain(current.x+1 , current.y) == '#') {
                            commands.add(r -> r.go(0.2));
                        } else {
                            commands.add(r -> r.turnBy(Math.PI / 2));
                            direction = 0;
                        }
                    }
                }
            }
            return commands;
            });
        return mazeRunner;
    }



    public static void main(String[] args) {
////        //run the simulation
////        String map = "################\n" +
////                "#  #0     1    #\n" +
////                "#  #   ##   #  #\n" +
////                "#  ###   #T #  #\n" +
////                "#   3# a ### W #\n" +
////                "# ###   # 2   ##\n" +
////                "#      #       #\n" +
////                "###########$$$##";
////        World world = new World(map);
////        new Robot("Pengu", Math.toRadians(90), 1).spawnInWorld(world, '3');
////        world.run();
//
//
//        Robot panicPenguin = new Robot("Panic!", 0, 0.5);
//
////create memory
//        Memory<Character> terrain = panicPenguin.createMemory(new Memory<>("terrain", ' '));
//
////create and attach sensors
//        panicPenguin.attachSensor(new TerrainSensor().setProcessor(terrain::set));
//
////program the robot
//        panicPenguin.setProgram(robot -> {
//            List<Command> commands = new ArrayList<>();
//
//            commands.add(r -> r.say(terrain.get().toString()));
//            commands.add(r -> r.turnBy(Math.toRadians(5)));
//            commands.add(r -> r.go(0.1));
//
//            return commands;
//        });
//
//        World world = new World("""
//                #######
//                #  0  #
//                #Don't#
//                #PANIC#
//                #     #
//                #######""");
//
//        panicPenguin.spawnInWorld(world, '0');
//        world.run();



        String map = ("""
                ################
                #v<#           #
                #v^#   #v<< #  #
                #v^<<<<<<0^ #  #
                #v   # >>>^#   #
                #v###  ^#     ##
                #>>>>>>^       #
                ################""");
        World world = new World(map);
        makeLineFollower().spawnInWorld(world, '0');
        world.run();
    }

}

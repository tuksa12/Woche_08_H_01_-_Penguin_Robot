package pgdp.robot;

import java.awt.event.KeyEvent;
import java.util.*;

public class User {
    public static  Robot makeLineFollower(){

        Robot lineFollowerPenguin = new Robot("Robot",0,0.5);
        TerrainSensor terrainSensor = new TerrainSensor();
        terrainSensor.setOwner(lineFollowerPenguin);
        lineFollowerPenguin.attachSensor(terrainSensor);
        Position firstPosition = lineFollowerPenguin.getPosition();
        double firstDirection = lineFollowerPenguin.getDirection();


        Memory<Character> terrain = lineFollowerPenguin.createMemory(new Memory<>("terrain", ' '));
        lineFollowerPenguin.attachSensor(new TerrainSensor().setProcessor(terrain::set));
//

        lineFollowerPenguin.setProgram(robot -> {
            double currentX = firstPosition.x;
            double currentY = firstPosition.y;
            int z = 1;

            double currentDirection = firstDirection;
            List<Command> commands = new ArrayList<>();

            commands.add(r -> r.say(terrain.get().toString()));

//

//            if(terrainSensor.getData()!='v' || terrainSensor.getData()!='<'||terrainSensor.getData()!='>'||terrainSensor.getData()!='^'){
//                commands.add(r -> r.go(0.2));
//            }
//            while(firstPosition.x != currentX && firstPosition.y != currentY && firstDirection != currentDirection || z != 0){
//                if (currentDirection == 0){
//                    if(terrainSensor.getData() == '>'){
//                        commands.add(r -> r.go(0.2));
//                        currentX +=1;
//                    } else if(terrainSensor.getData() =='v'){
//                        commands.add(r -> r.turnTo(Math.PI/2));
//                        currentDirection = Math.PI/2;
//                    } else if(terrainSensor.getData() =='<'){
//                        commands.add(r -> r.turnTo(Math.PI));
//                        currentDirection = Math.PI;
//                    } else if(terrainSensor.getData() =='^'){
//                        commands.add(r -> r.turnTo((3*Math.PI/2)));
//                        currentDirection = 3*Math.PI/2;
//                    }
//                } else if (currentDirection == Math.PI/2){
//                    if(terrainSensor.getData() =='v'){
//                        commands.add(r -> r.go(0.1));
//                        currentY +=1;
//                    } else if(terrainSensor.getData() == '>'){
//                        commands.add(r -> r.turnTo(0));
//                        currentDirection = 0;
//                    } else if(terrainSensor.getData() =='<'){
//                        commands.add(r -> r.turnTo(Math.PI));
//                        currentDirection = Math.PI;
//                    } else if(terrainSensor.getData() =='^'){
//                        commands.add(r -> r.turnTo((3*Math.PI/2)));
//                        currentDirection = 3*Math.PI/2;
//                    }
//                } else if (currentDirection == Math.PI){
//                    if(terrainSensor.getData() =='<'){
//                        commands.add(r -> r.go(0.1));
//                        currentX -=1;
//                    } else if(terrainSensor.getData() == '>'){
//                        commands.add(r -> r.turnTo(0));
//                        currentDirection = 0;
//                    } else if(terrainSensor.getData() =='v'){
//                        commands.add(r -> r.turnTo(Math.PI/2));
//                        currentDirection = Math.PI/2;
//                    } else if(terrainSensor.getData() =='^'){
//                        commands.add(r -> r.turnTo((3*Math.PI/2)));
//                        currentDirection = 3*Math.PI/2;
//                    }
//                } else if (currentDirection == (3*Math.PI/2)){
//                    if(terrainSensor.getData() =='^'){
//                        commands.add(r -> r.go(0.1));
//                        currentY -=1;
//                    } else if(terrainSensor.getData() == '>'){
//                        commands.add(r -> r.turnTo(0));
//                        currentDirection = 0;
//                    } else if(terrainSensor.getData() =='v'){
//                        commands.add(r -> r.turnTo(Math.PI/2));
//                        currentDirection = Math.PI/2;
//                    } else if(terrainSensor.getData() =='<'){
//                        commands.add(r -> r.turnTo(Math.PI));
//                        currentDirection = Math.PI;
//                    }
//                }
//                z = 0;
//                break;
//
//            }
            return commands;
        });
    return lineFollowerPenguin;}


    public static Robot makeMazeRunner(){
        Robot mazeRunner = new Robot("Maze Runner",0,0.5);
        mazeRunner.createMemory(new Memory<>("terrain",""));
        TerrainSensor read = new TerrainSensor();
        mazeRunner.attachSensor(read);
        boolean wasThereBefore = false;
        List<Command> commands = new List<Command>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Command> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Command command) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Command> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Command> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Command get(int index) {
                return null;
            }

            @Override
            public Command set(int index, Command element) {
                return null;
            }

            @Override
            public void add(int index, Command element) {

            }

            @Override
            public Command remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Command> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Command> listIterator(int index) {
                return null;
            }

            @Override
            public List<Command> subList(int fromIndex, int toIndex) {
                return null;
            }
        };

        int current = 1;
        int previous = 0;
        while(read.getData() != '$'){
            if (current == previous){
                commands.add(r -> r.turnBy(Math.PI/2));
            }
            if(mazeRunner.go(0.2)){
                if (current != previous){
                    commands.add(r -> r.go(0.2));
                    previous = current;
                }else{
                    commands.add(r -> r.turnBy(Math.PI/2));
                }
                current ++;
            }
        }
        mazeRunner.setProgram(robot -> commands);
    return mazeRunner;}


    public static void main(String[] args) {
        //run the simulation
//        String map = "################\n" +
//                "#  #0     1    #\n" +
//                "#  #   ##   #  #\n" +
//                "#  ###   #T #  #\n" +
//                "#   3# a ### W #\n" +
//                "# ###   # 2   ##\n" +
//                "#      #       #\n" +
//                "###########$$$##";
//        World world = new World(map);
//        new Robot("Pengu", Math.toRadians(90), 1).spawnInWorld(world, '3');
//        world.run();




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
        System.out.println(makeLineFollower().memoryToString());
        world.run();
    }
}

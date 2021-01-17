package pgdp.robot;

import java.util.*;
import java.util.function.Function;

public class Robot{

    /// Attributes
    private List<Memory<?>> memory;
    private List<Sensor<?>> sensors;
    private Queue<Command> todo;
    private Function<Robot, List<Command>> program;

    //final attributes
    private final String name;
    private final double size;

    //internal states
    private Position position = new Position();
    private double direction;
    private World world;


    /// Methods
    public Robot(String name, double direction, double size) {
        this.name = name;
        this.direction = direction;
        this.size = Math.min(Math.max(0.5, size), 1);
    }

    <T> Memory<T> createMemory(Memory<T> newMemory){
        if (memory != null){
            memory.add(newMemory);
        }else{
            memory = new List<Memory<?>>() {
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
                public Iterator<Memory<?>> iterator() {
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
                public boolean add(Memory<?> memory) {
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
                public boolean addAll(Collection<? extends Memory<?>> c) {
                    return false;
                }

                @Override
                public boolean addAll(int index, Collection<? extends Memory<?>> c) {
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
                public Memory<?> get(int index) {
                    return null;
                }

                @Override
                public Memory<?> set(int index, Memory<?> element) {
                    return null;
                }

                @Override
                public void add(int index, Memory<?> element) {

                }

                @Override
                public Memory<?> remove(int index) {
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
                public ListIterator<Memory<?>> listIterator() {
                    return null;
                }

                @Override
                public ListIterator<Memory<?>> listIterator(int index) {
                    return null;
                }

                @Override
                public List<Memory<?>> subList(int fromIndex, int toIndex) {
                    return null;
                }
            };
            memory.add(newMemory);
        }
        return newMemory;
    }

    public String memoryToString(){
        StringBuilder memoryStringBuilder = new StringBuilder();
        for (int i = 0; i <memory.size() ; i++) {
            memoryStringBuilder.append(memory.indexOf(i));
        }
        return memoryStringBuilder.toString();
    }

    public void attachSensor(Sensor<?> sensor){
        if (sensors == null){
            sensors = new List<Sensor<?>>() {
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
                public Iterator<Sensor<?>> iterator() {
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
                public boolean add(Sensor<?> sensor) {
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
                public boolean addAll(Collection<? extends Sensor<?>> c) {
                    return false;
                }

                @Override
                public boolean addAll(int index, Collection<? extends Sensor<?>> c) {
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
                public Sensor<?> get(int index) {
                    return null;
                }

                @Override
                public Sensor<?> set(int index, Sensor<?> element) {
                    return null;
                }

                @Override
                public void add(int index, Sensor<?> element) {

                }

                @Override
                public Sensor<?> remove(int index) {
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
                public ListIterator<Sensor<?>> listIterator() {
                    return null;
                }

                @Override
                public ListIterator<Sensor<?>> listIterator(int index) {
                    return null;
                }

                @Override
                public List<Sensor<?>> subList(int fromIndex, int toIndex) {
                    return null;
                }
            };
        }
        sensors.add(sensor);
        sensor.setOwner(this);
    }

    private <T> void helper (List<Sensor<T>> sens){
        for (Sensor<T> sensor: sens) {
            sensor.processor.accept(sensor.getData());
        }

    }

//    private void sense(){
//        for (Sensor<?> sensor: sensors) {
//            sensor.processor.accept(sensor.getData());
//        }
//    }

    public void setProgram(Function<Robot, List<Command>> program) {
        this.program = program;
    }


    private void think(){
        List<Command> list = program.apply(this);
        for (Command command: list) {
            todo.add(command);
        }
    }

    private void act(){
        for (int i = 0; i < todo.size(); i++) {
            Command current = todo.peek();
            if (!current.execute(this)){
                break;
            }else{
                current.execute(this);
                todo.remove();
            }
        }
    }

//    public void work(){
//        if(todo.size() == 0){
//            sense();
//            think();
//        }
//        act();
//    }

    /// Pre-programmed Commands
    public boolean go(double distance) {
        //step can be negative if the penguin walks backwards
        double sign = Math.signum(distance);
        distance = Math.abs(distance);
        //penguin walks, each step being 0.2m
        while (distance > 0) {
            position.moveBy(sign * Math.min(distance, 0.2), direction);
            world.resolveCollision(this, position);
            distance -= 0.2;
        }
        return true;
    }

    public boolean turnBy(double deltaDirection) {
        direction += deltaDirection;
        return true;
    }

    public boolean turnTo(double newDirection) {
        direction = newDirection;
        return true;
    }

    public boolean say(String text) {
        world.say(this, text);
        return true;
    }

    public boolean paintWorld(Position pos, char blockType) {
        world.setTerrain(pos, blockType);
        return true;
    }


    /// Getters and Setters
    public String getName() {
        return name;
    }

    public double getSize() {
        return size;
    }

    public Position getPosition() {
        return new Position(position);
    }

    public double getDirection() {
        return direction;
    }

    public World getWorld() {
        return world;
    }

    public void spawnInWorld(World world, char spawnMarker) {
        this.world = world;
        this.position = new Position(world.spawnRobotAt(this, spawnMarker));
    }

    @Override
    public String toString() {
        return String.format(Locale.ENGLISH, "\"%s\" position=%s direction=%.2f°", name, position, Math.toDegrees(direction));
    }

}

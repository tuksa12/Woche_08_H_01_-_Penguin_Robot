package pgdp.robot;

public class Memory<T>{
    private final String label;
    private T data;

    public Memory(String label, T data) {
        this.label = label;
        this.data = data;
    }

    public T get() {
        return data;
    }

    public void set(T data){
    this.data = data;
    }

    public String toString(){
        return (this.label + ": " + this.data);
    }
}

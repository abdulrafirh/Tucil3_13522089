public class Pair<T, E extends Comparable<E>> implements Comparable<Pair<T, E>>{

    private T first;
    private E second;

    public Pair(T _first, E _second){
        first = _first;
        second = _second;
    }

    public T first(){
        return first;
    }

    public E second(){
        return second;
    }

    public void setFirst(T newFirst){
        first = newFirst;
    }

    public void setSecond(E newSecond){
        second = newSecond;
    }

    @Override
    public int compareTo(Pair<T, E> o) {
        return second().compareTo(o.second());
    }
}

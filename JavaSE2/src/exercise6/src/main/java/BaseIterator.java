public interface BaseIterator<T> {
    T next();
    boolean hasNext();
    void reset();
}

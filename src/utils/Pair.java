package utils;

import java.util.Objects;

public class Pair<F, S> {

    private F _first;
    private S _second;

    public Pair(F first, S second) {
        _first = first;
        _second = second;
    }

    public F getFirst() {
        return _first;
    }

    public void setFirst(F first) {
        _first = first;
    }

    public S getSecond() {
        return _second;
    }

    public void setSecond(S second) {
        _second = second;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pair<?, ?> pair = (Pair<?, ?>) o;

        if (!Objects.equals(_first, pair._first)) return false;
        return Objects.equals(_second, pair._second);
    }

    @Override
    public int hashCode() {
        return 31 * _first.hashCode() + _second.hashCode();
    }

    @Override
    public String toString() {
        return "Pair{" + _first + ", " + _second + '}';
    }

}

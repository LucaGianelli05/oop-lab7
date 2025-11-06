package it.unibo.inner;

import java.util.Arrays;
import java.util.Iterator;

import it.unibo.inner.api.IterableWithPolicy;
import it.unibo.inner.api.Predicate;

public class IterableWithPolicyImpl<T> implements IterableWithPolicy<T> {

    private final T[] elements;
    private Predicate<T> predicate = null;

    public IterableWithPolicyImpl(final T[] elements) {
        this(elements, new Predicate<T>() {

            @Override
            public boolean test(T elem) {
                return true;
            }
        });
    }

    public IterableWithPolicyImpl(final T[] elements, final Predicate<T> predicate) {
        if (elements == null || predicate == null) {
            throw new IllegalArgumentException("elements and predicate must be not null");
        }
        this.elements = Arrays.copyOf(elements, elements.length);
        this.predicate = predicate;
    } 

    @Override
    public Iterator<T> iterator() {
        return new IteratorWithPolicy();
    }

    @Override
    public void setIterationPolicy(final Predicate<T> filter) {
        if (filter == null) {
            throw new IllegalArgumentException("filter must be not null");
        }
        predicate = filter;
    }

    private class IteratorWithPolicy implements Iterator<T> {

        private int index = -1;

        @Override
        public boolean hasNext() {
            int i;
            for (i = index + 1; i < elements.length && !predicate.test(elements[i]); i++)
                ;
            return i < elements.length;
        }

        @Override
        public T next() {
            index++;
            while (!predicate.test(elements[index])) {
                index++;
            }
            return elements[index];
        }
    }
}

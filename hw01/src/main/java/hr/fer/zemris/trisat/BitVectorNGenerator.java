package hr.fer.zemris.trisat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BitVectorNGenerator implements Iterable<MutableBitVector> {
    private BitVector assignment;
    private int numberOfVariables;

    public BitVectorNGenerator(BitVector assignment) {
        this.assignment = assignment;
    }

    // Vraća lijeni iterator koji na svaki next() računa sljedećeg susjeda
    @Override
    public Iterator<MutableBitVector> iterator() {
        return new Iterator<MutableBitVector>() {
            private MutableBitVector current = BitVectorNGenerator.this.assignment.copy();
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < current.getSize();
            }

            @Override
            public MutableBitVector next() {
                MutableBitVector next = current.copy();
                next.set(index, !next.get(index));
                index++;
                return next;
            }
        };
    }

    // Vraća kompletno susjedstvo kao jedno polje
    public MutableBitVector[] createNeighborhood() {
        List<MutableBitVector> neighborhood = new ArrayList<>();

        for (MutableBitVector mutableBitVector : this) {
            neighborhood.add(mutableBitVector);
        }

        return neighborhood.toArray(new MutableBitVector[0]);
    }
}

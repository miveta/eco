package hr.fer.zemris.trisat;

import java.util.Random;

public class MutableBitVector extends BitVector {
    public MutableBitVector(boolean... bits) {
        super(bits);
    }

    public MutableBitVector(int numberOfBits) {
        super(numberOfBits);
    }

    public MutableBitVector(Random rand, int numberOfBits) {
        super(rand, numberOfBits);
    }

    public void set(int index, boolean value) {
        if (index < 0 || index >= numberOfBits) {
            throw new IndexOutOfBoundsException();
        }

        bits[index] = value;
    }
}

package hr.fer.zemris.trisat;

import java.util.Random;

public class BitVector {
    int numberOfBits;
    boolean[] bits;

    public BitVector(Random rand, int numberOfBits) {
        this.numberOfBits = numberOfBits;
        bits = new boolean[numberOfBits];
        for (int i = 0; i < numberOfBits; i++) {
            bits[i] = rand.nextBoolean();
        }
    }

    public BitVector(boolean... bits) {
        this.numberOfBits = bits.length;
        this.bits = bits;
    }

    public BitVector(int numberOfBits) {
        this.numberOfBits = numberOfBits;
        bits = new boolean[numberOfBits];
    }

    public boolean get(int index) {
        if (index < 0 || index >= numberOfBits) {
            throw new IndexOutOfBoundsException();
        }

        return bits[index];
    }

    public int getSize() {
        return numberOfBits;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfBits; i++) {
            sb.append(bits[i] ? "1" : "0");
        }
        return sb.toString();
    }

    public MutableBitVector copy() {
        return new MutableBitVector(bits);
    }
}

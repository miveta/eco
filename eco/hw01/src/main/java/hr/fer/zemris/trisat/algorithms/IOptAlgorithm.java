package hr.fer.zemris.trisat.algorithms;

import hr.fer.zemris.trisat.BitVector;

import java.util.Optional;

public interface IOptAlgorithm {
    Optional<BitVector> solve(Optional<BitVector> initial);
}

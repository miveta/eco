package hr.fer.zemris.trisat;

import java.util.Optional;

public interface IOptAlgorithm {
    Optional<BitVector> solve(Optional<BitVector> initial);
}

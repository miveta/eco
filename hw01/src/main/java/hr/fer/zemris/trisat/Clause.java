package hr.fer.zemris.trisat;

public class Clause {
    /**
     * Indexed from 1 until the number of variables.
     * Index of the variable is positive if the variable is positive in the clause.
     * */
    int indexes[];

    public Clause(int[] indexes) {
        this.indexes = indexes;
    }
    // vraća broj literala koji čine klauzulu
    public int getSize() {
        return indexes.length;
    }
    // vraća indeks varijable koja je index-ti član ove klauzule
    public int getLiteral(int index) {
        if (index < 0 || index >= indexes.length) {
            throw new IndexOutOfBoundsException();
        }
        return indexes[index];
    }
    // vraća true ako predana dodjela zadovoljava ovu klauzulu
    public boolean isSatisfied(BitVector assignment) {
        for (int i = 0; i < getSize(); i++) {
            boolean literalValue = indexes[i] > 0; // literal value, if > 0 then it's positive
            boolean assignmentValue = assignment.get(Math.abs(indexes[i]) - 1); // get the bit value

            if(literalValue == assignmentValue) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int index : indexes) {
            sb.append(index).append(" ");
        }
        return sb.toString();
    }
}

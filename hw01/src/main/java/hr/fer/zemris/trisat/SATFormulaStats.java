package hr.fer.zemris.trisat;

public class SATFormulaStats {
    private SATFormula formula;
    private int numberOfSatisfiedClauses;
    private int numberOfUnsatisfiedClauses;
    private boolean[] satisfiedClauses;


    public SATFormulaStats(SATFormula formula) {
        this.formula = formula;
    }

    // analizira se predano rješenje i pamte svi relevantni pokazatelji
// primjerice, ažurira elemente polja post[...] ako drugi argument to dozvoli; računa Z; ...
    public void setAssignment(BitVector assignment, boolean updatePercentages) {
        numberOfSatisfiedClauses = 0;
        numberOfUnsatisfiedClauses = 0;
        satisfiedClauses = new boolean[formula.getNumberOfClauses()];

        for (int i = 0; i < formula.getNumberOfClauses(); i++) {
            Clause clause = formula.getClause(i);
            boolean isSatisfied = clause.isSatisfied(assignment);
            if (isSatisfied) {
                numberOfSatisfiedClauses++;
            } else {
                numberOfUnsatisfiedClauses++;
            }
            satisfiedClauses[i] = isSatisfied;
        }
    }

    // vraća temeljem onoga što je setAssignment zapamtio: broj klauzula koje su zadovoljene
    public int getNumberOfSatisfied() {
        return numberOfSatisfiedClauses;
    }

    // vraća temeljem onoga što je setAssignment zapamtio
    public boolean isSatisfied() {
        return numberOfUnsatisfiedClauses == 0;
    }

    // vraća temeljem onoga što je setAssignment zapamtio: suma korekcija klauzula
// to je korigirani Z iz algoritma 3
    public double getPercentageBonus() {
        return 0;
    }

    // vraća temeljem onoga što je setAssignment zapamtio: procjena postotka za klauzulu
// to su elementi polja post[...]
    public double getPercentage(int index) {
        return 0;
    }

    // resetira sve zapamćene vrijednosti na početne (tipa: zapamćene statistike)
    public void reset() {
        numberOfSatisfiedClauses = 0;
        numberOfUnsatisfiedClauses = 0;
        satisfiedClauses = new boolean[formula.getNumberOfClauses()];
    }
}

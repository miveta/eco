package hr.fer.zemris.trisat;

public class SATFormulaStats {
    private SATFormula formula;
    public static final double percentageConstantUp = 0.01;
    public static final double percentageConstantDown = 0.1;
    public static final double percentageUnitAmount = 50;


    private double[] percentages;
    private int numberOfSatisfied;
    private double percentageBonus;

    public SATFormulaStats(SATFormula formula) {
        this.formula = formula;
        percentages = new double[formula.getNumberOfClauses()];
    }

    // analizira se predano rješenje i pamte svi relevantni pokazatelji
// primjerice, ažurira elemente polja post[...] ako drugi argument to dozvoli; računa Z; ...
    public void setAssignment(BitVector assignment, boolean updatePercentages) {
        numberOfSatisfied = 0;

        if (updatePercentages) {
            for (int i = 0; i < formula.getNumberOfClauses(); i++) {
                if (formula.getClause(i).isSatisfied(assignment)) {
                    percentages[i] += (1 - percentages[i]) * percentageConstantUp;
                    numberOfSatisfied++;
                } else {
                    percentages[i] += (0 - percentages[i]) * percentageConstantDown;
                }
            }
        } else {
            percentageBonus = 0;

            for (int i = 0; i < formula.getNumberOfClauses(); i++) {
                if (formula.getClause(i).isSatisfied(assignment)) {
                    numberOfSatisfied++;
                    percentageBonus += percentageUnitAmount * (1 - percentages[i]);
                } else {
                    percentageBonus -= percentageUnitAmount * (1 - percentages[i]);
                }
            }
        }
    }

    // vraća temeljem onoga što je setAssignment zapamtio: broj klauzula koje su zadovoljene
    public int getNumberOfSatisfied() {
        return numberOfSatisfied;
    }

    // vraća temeljem onoga što je setAssignment zapamtio
    public boolean isSatisfied() {
        return numberOfSatisfied == formula.getNumberOfClauses();
    }

    // vraća temeljem onoga što je setAssignment zapamtio: suma korekcija klauzula
// to je korigirani Z iz algoritma 3
    public double getPercentageBonus() {
        return percentageBonus;
    }

    // vraća temeljem onoga što je setAssignment zapamtio: procjena postotka za klauzulu
    // to su elementi polja post[...]
    public double getPercentage(int index) {
        if (index < 0 || index >= formula.getNumberOfClauses()) {
            throw new IndexOutOfBoundsException();
        }

        return percentages[index];
    }

    // resetira sve zapamćene vrijednosti na početne (tipa: zapamćene statistike)
    public void reset() {
        numberOfSatisfied = 0;
        percentageBonus = 0;
        percentages = new double[formula.getNumberOfClauses()];
    }
}

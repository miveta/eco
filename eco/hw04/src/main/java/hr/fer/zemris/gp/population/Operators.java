package hr.fer.zemris.gp.population;

import hr.fer.zemris.gp.population.node.NodeOperator;

public class Operators {
    public static NodeOperator ADD = new NodeOperator() {
        @Override
        public double calculate(double left, double right) {
            return left + right;
        }

        @Override
        public boolean isUnary() {
            return false;
        }

        @Override
        public String toString() {
            return "+";
        }
    };

    public static NodeOperator SUB = new NodeOperator() {
        @Override
        public double calculate(double left, double right) {
            return left - right;
        }

        @Override
        public boolean isUnary() {
            return false;
        }

        @Override
        public String toString() {
            return "-";
        }
    };

    public static NodeOperator MUL = new NodeOperator() {
        @Override
        public double calculate(double left, double right) {
            return left * right;
        }

        @Override
        public boolean isUnary() {
            return false;
        }

        @Override
        public String toString() {
            return "*";
        }
    };

    public static NodeOperator DIV = new NodeOperator() {
        @Override
        public double calculate(double left, double right) {
            if(right == 0) {
                return 1;
            }

            return left / right;
        }

        @Override
        public boolean isUnary() {
            return false;
        }

        @Override
        public String toString() {
            return "/";
        }
    };

    public static NodeOperator SIN = new NodeOperator() {
        @Override
        public double calculate(double left, double right) {
            return Math.sin(left);
        }

        @Override
        public boolean isUnary() {
            return true;
        }

        @Override
        public String toString() {
            return "sin";
        }
    };

    public static NodeOperator COS = new NodeOperator() {
        @Override
        public double calculate(double left, double right) {
            return Math.cos(left);
        }

        @Override
        public boolean isUnary() {
            return true;
        }

        @Override
        public String toString() {
            return "cos";
        }
    };

    public static NodeOperator SQRT = new NodeOperator() {
        @Override
        public double calculate(double left, double right) {
            if(left < 0) {
                return 1;
            }

            return Math.sqrt(left);
        }

        @Override
        public boolean isUnary() {
            return true;
        }

        @Override
        public String toString() {
            return "sqrt";
        }
    };

    public static NodeOperator LOG = new NodeOperator() {
        @Override
        public double calculate(double left, double right) {
            if(left <= 0) {
                return 1;
            }

            return Math.log(left);
        }

        @Override
        public boolean isUnary() {
            return true;
        }

        @Override
        public String toString() {
            return "log";
        }
    };

    public static NodeOperator EXP = new NodeOperator() {
        @Override
        public double calculate(double left, double right) {
            return Math.exp(left);
        }

        @Override
        public boolean isUnary() {
            return true;
        }

        @Override
        public String toString() {
            return "exp";
        }
    };
}

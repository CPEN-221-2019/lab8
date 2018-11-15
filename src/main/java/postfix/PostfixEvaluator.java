package postfix;

import java.util.Stack;

/**
 *
 * @author Sathish Gopalakrishnan
 *
 * This class contains a method to evaluate an arithmetic expression
 * that is in Postfix notation (or Reverse Polish Notation).
 * See <a href="https://en.wikipedia.org/wiki/Reverse_Polish_notation">Wikipedia</a>
 * for details on the notation.
 *
 */
public class PostfixEvaluator {
	private String arithmeticExpr;

	/**
	 * This is the only constructor for this class.
	 * It takes a string that represents an arithmetic expression
	 * as input argument.
	 *
	 * @param expr is a string that represents an arithmetic expression <strong>in Postfix notation</strong>.
	 */
	public PostfixEvaluator(String expr) {
		arithmeticExpr = expr;
	}

	/**
	 * This method evaluates the arithmetic expression that
	 * was passed as a string to the constructor for this class.
	 *
	 * @return the value of the arithmetic expression
	 * @throws MalformedExpressionException if the provided expression is not
	 * 	a valid expression in Postfix notation
	 */
	double eval() throws MalformedExpressionException {
		if (arithmeticExpr.length() < 3) {
			throw new MalformedExpressionException();
		}

		double result;

		Scanner scanner = new Scanner(arithmeticExpr);
		Token currToken = scanner.getToken();

		Stack<Double> stack = new Stack<>();

		while (!scanner.isEmpty()) {

			if (currToken.isDouble()) {
				stack.push(currToken.getValue());
				scanner.eatToken();
				currToken = scanner.getToken();
			}

			if (currToken.isVariable()) {

				if (stack.size() > 1) {
					String op = currToken.getName();
					double b = stack.peek();
					stack.pop();
					double a = stack.peek();
					stack.pop();
					stack.push(operation(a, b, op));

					scanner.eatToken();
					currToken = scanner.getToken();


				}

				else {
					throw new MalformedExpressionException();
				}
			}
		}

		result = stack.peek();
		stack.pop();

		if (!stack.empty()) {
			throw new MalformedExpressionException();
		}

		return result;
	}

	private static double operation(double a, double b, String op) {
		double result = 0;
		if (op.equals("+")) {
			result = a + b;
		}
		if (op.equals("-")) {
			result = a - b;
		}
		if (op.equals("*")) {
			result = a * b;
		}
		if (op.equals("/")) {
			result = a / b;
		}

		return result;
	}

}
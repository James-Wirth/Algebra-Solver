# algebra-solver

## Usage ##

The linear equation function can be used by passing in the unknown variable letter and the equation, with terms and operators separated by spaces. At the moment, only '+' and '-' operators can be used.

`double solution = Solver.solveLinearEquationFor("x", "2x + 5 - 7x = 24 + 6x");`

Using the quadratic formula solver:

`double[] solutions = Solver.solveQuadraticFor(a, b, c)`

where a, b & c are the coefficients of the zeroed quadratic equation.

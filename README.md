# algebra-solver

## Usage ##

The class 'Solver' in 'src/Solver.java' can currently be used to solve linear equations in the form of a string containing '+', '-', '*' & '/'.  
NB: At the moment, there must be a space between all of the terms.

`double solution = Solver.solveLinearEquationFor("x", "2x * 5 - 7x = 24 + 6x / 2x + 4");`

There is also a small quadratic solver function, which passes the solutions into a double array of length 2, given the coefficients a, b & c:

`double[] solutions = Solver.solveQuadraticFor(a, b, c)`


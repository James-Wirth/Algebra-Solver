# algebra-solver

## Usage ##

The class 'Solver' in 'src/Solver.java' can currently be used to solve linear equations containing '+', '-', '*' & '/'.  
For instance:

`double solution = Solver.solveLinearEquationFor("x", "2x * 5 - 7x = 24 + 6x / 2x + 4");`

There is also a small quadratic solver function, which passes the solutions into a double array of length 2, given the coefficients of x a, b & c:

`double[] solutions = Solver.solveQuadraticFor(a, b, c)`


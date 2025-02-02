package dhanar10.artificialbeecolony;

public interface IOptimizationProblem {
	public int length();
	public double[] upperBound();
	public double[] lowerBound();
	public double getOutput(double x[]);
	public double getFitness(double x[]);
}

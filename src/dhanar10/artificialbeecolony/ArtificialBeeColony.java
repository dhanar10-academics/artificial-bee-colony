package dhanar10.artificialbeecolony;

public class ArtificialBeeColony {
	private int foodSource;
	private double[] bestSolution;
	
	public static void main(String[] args) {
		IOptimizationProblem problem = new IOptimizationProblem() {
			public int length() {
				return 2;
			}
			public double[] upperBound() {
				return new double[] {4, 7};
			}
			public double[] lowerBound() {
				return new double[] {0, 0};
			}
			public double getOutput(double x[]) {
				return x[0] + x[1];
			}
			public double getFitness(double x[]) {
				return 1 / (10 - this.getOutput(x)); // x[0] + x[1] = 10
			}
		};
		
		ArtificialBeeColony abc = new ArtificialBeeColony(10);
		abc.optimize(problem, 10);
		
		System.out.println();
		
		for (int i = 0; i < abc.getBestSolution().length; i++) {
			System.out.println("x[" + i + "]\t= " + abc.getBestSolution()[i]);
		}
		
		System.out.println("y\t= " + problem.getOutput(abc.getBestSolution()));
	}
	
	public ArtificialBeeColony(int foodSource) {
		this.foodSource = foodSource;
	}
	
	public void optimize(IOptimizationProblem problem, int maximumCycleNumber) {
		double x[][] = new double[foodSource][problem.length()];
		double xfit[] = new double[foodSource];
		int xlimit[] = new int[foodSource];
		double xbest[] = new double[problem.length()];
		double xbestfit = 0;
		
		// Initialization
		
		for (int m = 0; m < x.length; m++) {
			for (int i = 0; i < x[0].length; i++) {
				x[m][i] = problem.lowerBound()[i] + Math.random() * (problem.upperBound()[i] - problem.lowerBound()[i]);
			}
			
			xfit[m] = problem.getFitness(x[m]);
		}
		
		for (int mcn = 1; mcn <= maximumCycleNumber; mcn++) {
			// Employed Bee
			
			for (int m = 0; m < x.length; m++) {
				double v[] = new double[problem.length()];
				double vfit = 0;
				int k = 0;
				
				do {
					k = (int) Math.round(Math.random() * (x.length - 1));
				} while (k == m);
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = x[m][i] + (Math.random() * 2 - 1) * (x[m][i] - x[k][i]);
				}
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = v[i] < problem.lowerBound()[i] ? problem.lowerBound()[i] : v[i];
					v[i] = v[i] > problem.upperBound()[i] ? problem.upperBound()[i] : v[i];
				}
				
				vfit = problem.getFitness(v);
				
				if (vfit > xfit[m]) {
					x[m] = v;
					xfit[m] = vfit;
				}
				else {
					xlimit[m]++;
				}
			}
			
			// Onlooker Bee
			
			for (int t = 0; t < x.length; t++) {
				double xfitmax = 0;
				double v[] = new double[problem.length()];
				double vfit = 0;
				int m = 0;
				int k = 0;
				
				// BEGIN Stochastic Roulette Wheel
				
				for (int i = 0; i < x.length; i++) {
					if (xfit[i] > xfitmax) {
						xfitmax = xfit[i];
					}
				}
				
				while (true) {
					m = (int) (Math.random() * x.length);
					
					if (Math.random() < xfit[m] / xfitmax)
						break;
				}
				
				// END Stochastic Roulette Wheel
				
				do {
					k = (int) Math.round(Math.random() * (x.length - 1));
				} while (k == m);
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = x[m][i] + (Math.random() * 2 - 1) * (x[m][i] - x[k][i]);
				}
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = v[i] < problem.lowerBound()[i] ? problem.lowerBound()[i] : v[i];
					v[i] = v[i] > problem.upperBound()[i] ? problem.upperBound()[i] : v[i];
				}
				
				vfit = problem.getFitness(v);
				
				if (vfit > xfit[m]) {
					x[m] = v;
					xfit[m] = vfit;
				}
			}
			
			// Scout Bee
			
			for (int m = 0; m < x.length; m++) {
				if (xlimit[m] > foodSource * 2) {
					for (int i = 0; i < x[0].length; i++) {
						x[m][i] = problem.lowerBound()[i] + Math.random() * (problem.upperBound()[i] - problem.lowerBound()[i]);
					}
				}
			}
			
			// Remember the best solution so far
			
			for (int m = 0; m < x.length; m++) {
				if (xfit[m] > xbestfit) {
					xbest = x[m].clone();
					xbestfit = xfit[m];
				}
			}
			
			System.out.println(mcn + "\t" + xbestfit);
		}
		
		bestSolution = xbest;
	}
	
	public double[] getBestSolution() {
		return bestSolution;
	}
}

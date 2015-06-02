package dhanar10.ArtificialBeeColony;

public class ArtificialBeeColony {
	private IOptimizationProblem optimizationProblem;
	private double[] bestSolution;
	
	public static void main(String[] args) {
		IOptimizationProblem optimizationProblem = new IOptimizationProblem() {
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
		
		ArtificialBeeColony abc = new ArtificialBeeColony(optimizationProblem);
		abc.optimize(10, 100);
		
		System.out.println();
		
		for (int i = 0; i < abc.getBestSolution().length; i++) {
			System.out.println("x[" + i + "]\t= " + abc.getBestSolution()[i]);
		}
		
		System.out.println("Output\t= " + optimizationProblem.getOutput(abc.getBestSolution()));
		System.out.println("Fitness\t= " + optimizationProblem.getFitness(abc.getBestSolution()));
	}
	
	public ArtificialBeeColony(IOptimizationProblem optimizationProblem) {
		this.optimizationProblem = optimizationProblem;
	}
	
	public void optimize(int foodSource, int maximumCycleNumber) {
		double x[][] = new double[foodSource][optimizationProblem.length()];
		int xlimit[] = new int[foodSource];
		
		double xbest[] = new double[optimizationProblem.length()];
		
		for (int m = 0; m < x.length; m++) {
			for (int i = 0; i < x[0].length; i++) {
				x[m][i] = optimizationProblem.lowerBound()[i] + Math.random() * (optimizationProblem.upperBound()[i] - optimizationProblem.lowerBound()[i]);
			}
		}
		
		for (int mcn = 1; mcn <= maximumCycleNumber; mcn++) {
			for (int m = 0; m < x.length; m++) {
				double v[] = new double[optimizationProblem.length()];
				int k = 0;
				
				do {
					k = (int) Math.round(Math.random() * (x.length - 1));
				} while (k == m);
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = x[m][i] + (Math.random() * 2 - 1) * (x[m][i] - x[k][i]);
				}
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = v[i] < optimizationProblem.lowerBound()[i] ? optimizationProblem.lowerBound()[i] : v[i];
					v[i] = v[i] > optimizationProblem.upperBound()[i] ? optimizationProblem.upperBound()[i] : v[i];
				}
				
				if (optimizationProblem.getFitness(v) > optimizationProblem.getFitness(x[m])) {
					x[m] = v;
				}
				else {
					xlimit[m]++;
				}
			}
			
			for (int t = 0; t < x.length; t++) {
				double xfitmax = 0;
				double v[] = new double[optimizationProblem.length()];
				int m = 0;
				int k = 0;
				
				for (int i = 0; i < x.length; i++) {
					if (optimizationProblem.getFitness(x[i]) > xfitmax) {
						xfitmax = optimizationProblem.getFitness(x[i]);
					}
				}
				
				while (true) {
					m = (int) (Math.random() * x.length);
					
					if (Math.random() < optimizationProblem.getFitness(x[m]) / xfitmax)
						break;
				}
				
				do {
					k = (int) Math.round(Math.random() * (x.length - 1));
				} while (k == m);
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = x[m][i] + (Math.random() * 2 - 1) * (x[m][i] - x[k][i]);
				}
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = v[i] < optimizationProblem.lowerBound()[i] ? optimizationProblem.lowerBound()[i] : v[i];
					v[i] = v[i] > optimizationProblem.upperBound()[i] ? optimizationProblem.upperBound()[i] : v[i];
				}
				
				if (optimizationProblem.getFitness(v) > optimizationProblem.getFitness(x[m])) {
					x[m] = v;
				}
			}
			
			for (int m = 0; m < x.length; m++) {
				if (xlimit[m] > foodSource * 2) {
					for (int i = 0; i < x[0].length; i++) {
						x[m][i] = optimizationProblem.lowerBound()[i] + Math.random() * (optimizationProblem.upperBound()[i] - optimizationProblem.lowerBound()[i]);
					}
				}
			}
			
			for (int m = 0; m < x.length; m++) {
				if (optimizationProblem.getFitness(x[m]) > optimizationProblem.getFitness(xbest)) {
					xbest = x[m].clone();
				}
			}
			
			System.out.println(mcn + "\t" + optimizationProblem.getOutput(xbest) + "\t" + optimizationProblem.getFitness(xbest));
		}
		
		bestSolution = xbest;
	}
	
	public double[] getBestSolution() {
		return bestSolution;
	}
}

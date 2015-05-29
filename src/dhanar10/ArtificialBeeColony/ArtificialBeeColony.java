package dhanar10.ArtificialBeeColony;

public class ArtificialBeeColony {
	public static final int LOWER_BOUND = 0;
	public static final int UPPER_BOUND = 10;
	public static final int FOOD_SOURCE = 10;
	public static final int MAXIMUM_CYCLE_NUMBER = 100;
	
	public static void main(String[] args) {
		double x[][] = new double[FOOD_SOURCE][2];
		int xlimit[] = new int[FOOD_SOURCE];
		
		double xbest[] = new double[2];
		
		for (int m = 0; m < x.length; m++) {
			for (int i = 0; i < x[0].length; i++) {
				x[m][i] = LOWER_BOUND + Math.random() * (UPPER_BOUND - LOWER_BOUND);
			}
		}
		
		for (int mcn = 1; mcn <= MAXIMUM_CYCLE_NUMBER; mcn++) {
			for (int m = 0; m < x.length; m++) {
				double v[] = new double[2];
				double vfit = 0;
				double xfit = 0;
				int k = 0;
				
				do {
					k = (int) Math.round(Math.random() * (x.length - 1));
				} while (k == m);
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = x[m][i] + (Math.random() * 2 - 1) * (x[m][i] - x[k][i]);
				}
				
				vfit = 1 / (10 - (v[0] + v[1]));
				xfit = 1 / (10 - (x[m][0] + x[m][1])); 
				
				if (vfit > xfit) {
					x[m] = v;
				}
				else {
					xlimit[m]++;
				}
			}
			
			for (int t = 0; t < x.length; t++) {
				double xfitmax = 0;
				double v[] = new double[2];
				double vfit = 0;
				double xfit = 0;
				int m = 0;
				int k = 0;
				
				for (int i = 0; i < x.length; i++) {
					xfit = 1 / (10 - (x[i][0] + x[i][1])); 
					
					if (xfit > xfitmax) {
						xfitmax = xfit;
					}
				}
				
				while (true) {
					m = (int) (Math.random() * x.length);
					
					xfit = 1 / (10 - (x[m][0] + x[m][1])); 
					
					if (Math.random() < xfit / xfitmax)
						break;
				}
				
				do {
					k = (int) Math.round(Math.random() * (x.length - 1));
				} while (k == m);
				
				for (int i = 0; i < x[0].length; i++) {
					v[i] = x[m][i] + (Math.random() * 2 - 1) * (x[m][i] - x[k][i]);
				}
				
				vfit = 1 / (10 - (v[0] + v[1]));
				xfit = 1 / (10 - (x[m][0] + x[m][1])); 
				
				if (vfit > xfit) {
					x[m] = v;
				}
			}
			
			for (int m = 0; m < x.length; m++) {
				if (xlimit[m] > FOOD_SOURCE * 2) {
					for (int i = 0; i < x[0].length; i++) {
						x[m][i] = LOWER_BOUND + Math.random() * (UPPER_BOUND - LOWER_BOUND);
					}
				}
			}
			
			for (int m = 0; m < x.length; m++) {
				double xfit = 0;
				double xbestfit = 0;
				
				xfit = 1 / (10 - (x[m][0] + x[m][1]));
				xbestfit = 1 / (10 - (xbest[0] + xbest[1])); 
				
				if (xfit > xbestfit) {
					xbest = x[m].clone();
				}
			}
			
			System.out.println(mcn + "\t" + (xbest[0] + xbest[1]) + "\t" + 1 / (10 - (xbest[0] + xbest[1])));
		}
	}
	
	
}

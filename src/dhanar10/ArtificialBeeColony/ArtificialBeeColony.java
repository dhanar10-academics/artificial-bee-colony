package dhanar10.ArtificialBeeColony;

public class ArtificialBeeColony {

	public static void main(String[] args) {
		double x[][] = new double[10][2];
		double xfit[] = new double[10];
		int xlimit[] = new int[10];
		
		double xbestfit = Double.MIN_VALUE;
		double xbest[] = new double[2];
		
		for (int m = 0; m < x.length; m++) 
			for (int i = 0; i < x[0].length; i++)
				x[m][i] = 0 + Math.random() * (10 - 0);
		
		for (int mcn = 0; mcn < 100; mcn++) {
			for (int m = 0; m < x.length; m++) {
				xfit[m] = 1 / (10 - (x[m][0] + x[m][1]));
			}
			
			for (int m = 0; m < x.length; m++) {
				double v[] = new double[2];
				double vfit = Double.MIN_VALUE;
				int k = 0;
				
				do {
					k = (int) Math.round(Math.random() * (x.length - 1));
				} while (k == m);
				
				for (int i = 0; i < x[0].length; i++)
					v[i] = x[m][i] + (Math.random() * 2 - 1) * (x[m][i] - x[k][i]);
				
				vfit = 1 / (10 - (v[0] + v[1]));
				
				if (vfit > xfit[m]) {
					x[m] = v;
					xfit[m] = vfit;
				}
				else {
					xlimit[m]++;
				}
			}
			
			for (int t = 0; t < x.length; t++) {
				double xfitmax = Double.MIN_VALUE;
				double v[] = new double[2];
				double vfit = Double.MIN_VALUE;
				int m = 0;
				int k = 0;
				
				for (double value : xfit)
					if (value > xfitmax) 
						xfitmax = value;
				
				while (true){
					m = (int) (Math.random() * x.length);
					
					if (Math.random() < xfit[m] / xfitmax)
						break;
				}
				
				do {
					k = (int) Math.round(Math.random() * (x.length - 1));
				} while (k == m);
				
				for (int i = 0; i < x[0].length; i++)
					v[i] = x[m][i] + (Math.random() * 2 - 1) * (x[m][i] - x[k][i]);
				
				vfit = 1 / (10 - (v[0] + v[1]));
				
				if (vfit > xfit[m]) {
					x[m] = v;
					xfit[m] = vfit;
				}
			}
			
			for (int m = 0; m < x.length; m++)
				if (xlimit[m] > 10 * 2)
					for (int i = 0; i < x[0].length; i++)
						x[m][i] = 0 + Math.random() * (10 - 0);
			
			for (int m = 0; m < x.length; m++)
				if (xfit[m] > xbestfit) {
					xbestfit = xfit[m];
					xbest = x[m];
				}
			
			System.out.println(xbest[0] + " " + xbest[1] + " " + (xbest[0] + xbest[1]) + " " + xbestfit + " " + 1 / (10 - (xbest[0] + xbest[1])));
		}
	}
	
	
}

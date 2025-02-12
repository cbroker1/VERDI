/**
 * MinMaxInfo - Used to calculate min/max values across levels and timesteps
 * @author Tony Howard
 * @version $Revision$ $Date$
 **/


package anl.verdi.plot.data;

import anl.verdi.data.DataUtilities;

public class MinMaxInfo {

	double max = Double.NEGATIVE_INFINITY;
	double min = Double.POSITIVE_INFINITY;
	int minIndex = -1;
	int maxIndex = -1;
	int count = 0;
	int totalCount = 0;
	
	public MinMaxInfo(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public synchronized void incrementCount(int amount) {
		count += amount;
	}
	
	public void visitValue(double value, int index) {
		if (value <= min && value > DataUtilities.BADVAL3 && value > DataUtilities.AMISS3 && value < DataUtilities.NC_FILL_FLOAT) {
			min = value;
			minIndex = index;
		}
		if (value >= max && value > DataUtilities.BADVAL3 && value > DataUtilities.AMISS3 && value < DataUtilities.NC_FILL_FLOAT) {
			max = value;
			maxIndex = index;
		}
	}
	
	public int getMinIndex() {
		return minIndex;
	}
	
	public int getMaxIndex() {
		return maxIndex;
	}
	
	public double getMin() {
		return min;
	}
	
	public double getMax() {
		return max;
	}
	
	public void calcDone() {
	}
	
	public int getCount() {
		return count;
	}
	
	public double getCompletion() {
		return count / (double)totalCount * 100;
	}
	
}

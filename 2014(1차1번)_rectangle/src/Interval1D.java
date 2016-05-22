import java.util.LinkedList;

public class Interval1D implements Comparable<Interval1D> {
    public final int min;  // min endpoint
    public final int max;  // max endpoint

    // precondition: min <= max
    public Interval1D(int min, int max) {
        if (min <= max) {
            this.min = min;
            this.max = max;
        }
        else throw new RuntimeException("Illegal interval");
    }

    // does this interval intersect that one?
    public boolean intersects(Interval1D that) {
        if (that.max < this.min) return false;
        if (this.max < that.min) return false;
        return true;
    }
    
    public boolean hasIntersects(LinkedList<Interval1D> list){
    	for (Interval1D i : list) {
    		if(this.intersects(i))
    			return true;
    	}
    	return false;
    }

    // does this interval a intersect b?
    public boolean contains(int x) {
        return (min <= x) && (x <= max);
    }

    public int compareTo(Interval1D that) {
        if      (this.min < that.min) return -1;
        else if (this.min > that.min) return +1;
        else if (this.max < that.max) return -1;
        else if (this.max > that.max) return +1;
        else                          return  0;
    }

    public String toString() {
        return "[" + min + ", " + max + "]";
    }

}
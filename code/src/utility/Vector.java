package utility;
import java.awt.geom.Point2D;

/**
 * Class of Vector with transformation methods
 * 
 * @author Mohamed Errazki
 */
public class Vector extends Point2D.Double {

	/**
	 * Creates PVector (0,0)
	 */
	public Vector() {
		super();
	}

	/**
	 * Creates PVector (x, y)
	 * @param x 	x coordinate
	 * @param y 	y coordinate
	 */
	public Vector(double x, double y) {
		super(x, y);
	}


	/**
	 * @return returns a copy of the vector
	 */
	public Vector clone() {
		return (Vector)super.clone();
	}

	/**
	 * @return Returns True if PVector is (0,0)
	 */
	public boolean isNull() {
		return (x == 0 && y == 0);
	}

	/**
	 * Computes sum of PVector p and this
	 * 
	 * @param p 	PVector
	 * 
	 * @return 		sum of p and this
	 */
	public Vector add(Vector p) {
		x += p.x;
		y += p.y;
		return this;
	}

	/**
	 * Computes difference of p and this
	 * 
	 * @param p		Pvector
	 * 
	 * @return 		Difference of p and this
	 */
	public Vector sub(Vector p) {
		x -= p.x;
		y -= p.y;
		return this;
	}

	/**
	 * Computes norm of the vector
	 * 
	 * @return Norm
	 */
	public double norm() {
		double n = Math.sqrt(x*x + y*y);
		return n;
	}

	/**
     * Boids get fined for speeding
     */
    public void speedLimit(float vlim){
        if (this.norm() > vlim) {
            this.x = (int) ((this.x / this.norm()) * vlim);
            this.y = (int) ((this.y / this.norm()) * vlim);
        }
    }

	/**
	 * Computes limit value
	 * 
	 * @param value Valeur
	 * @param lim	Limite
	 * 
	 * @return Valeur tronquée
	 */
	public static double getLimit(double value, double lim) {
		if (value > lim)
			value = lim;
		else if (value < -lim)
			value = -lim;

		return value;
	}

	/**
	 * Limit of the vector
	 * 
	 * @param lim	limit
	 * 
	 * @return	limited vector
	 * @see PVector#getLimit(double, double)
	 */
	public Vector limit(double lim) {
		x = getLimit(x, lim);
		y = getLimit(y, lim);
		return this;
	}

	/**
	 * Computes product of this and value v
	 * 
	 * @param v Vector
	 * 
	 * @return Product
	 */
	public Vector mult(double v) {
		x *= v;
		y *= v;
		return this;
	}

	/**
	 * Computes product of vector p and this
	 * @param p Vector
	 * @return  Vector product
	 */
	public Vector mult(Vector p) {
		x *= p.x;
		y *= p.y;

		return this;
	}

	/**
	 * Divides vector y v
	 * @param v value
	 * @return result
	 */
	public Vector div(double v) {
		x /= v;
		y /= v;

		return this;
	}
	
	/**
	 * resets Vector to origin
	 */
	public void reset() {
		x = y = 0;
	}
}

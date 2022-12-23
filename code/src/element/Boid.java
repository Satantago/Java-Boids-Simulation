package element;

import utility.Vector;
import group.Boids;
import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public class Boid implements Cloneable {

	private static final int NEIGHBORHOOD = 100; //Distance that defines whether two boids are neighbors
	private static final int BASE_MAX_VELOCITY = 10;
	private static final int BASE_MAX_FORCE = 10;
	private static final Color color = Color.WHITE;
	private static final int size = 3;

	private static final int MOVE_FACTOR = 100; 
	private static final int MIN_DIST = 20;  //minimal distance between two boids
	private static final double VISION = 2*Math.PI/3;

	private Vector position;
	private Vector nextPosition;
	private Vector velocity;
	private Vector nextVelocity;
	private Vector acceleration;
	private LinkedList<Boid> boids;

	private double direction;

	private int triXPts[];
	private int triYPts[];

	
	public Boid(double x, double y, double vx, double vy, double ax, double ay) {
		position = new Vector(x, y);
		velocity = new Vector(vx, vy);
		acceleration = new Vector(ax, ay);
		
		nextPosition = position;
		nextVelocity = velocity;

		computeDirection();

		triXPts = new int[3];
		triYPts = new int[3];
	}

	
	/**
	 * Clones boid
	 */
	@Override
	public Boid clone() {
		Boid b = null;
		try {
			b = (Boid)super.clone();
			b.position = position.clone();
			b.velocity = velocity.clone();
			b.acceleration = acceleration.clone();
		}
		catch(Exception e) {}
		return b;
	}
	
	public Vector getPosition() {return position;}
	public Vector getvelocity() {return velocity;}
	public Vector getAcceleration() {return acceleration;}
	public Vector getNextPosition() {return nextPosition;}
	public Vector getNextVelocity() {return nextVelocity;}
	public int GetMinDist() {return Boid.MIN_DIST;}
	public double getDirection() {return direction;}
	public double getMass() {return 1;}
	public int[] getTriangleX() {return triXPts;}
	public int[] getTriangleY() {return triYPts;}
	public Color getColor() {return color;}
	public int getSize() {return size;}
	public void setPosition(Vector v) {this.position = v;}
	public void setVelocity(Vector v) {this.velocity = v;}
	public void setNextPosition(Vector v) {this.nextPosition = v;}
	public void setNextVelocity(Vector v) {this.nextVelocity = v;}
	public void setGroup(LinkedList<Boid> group) {this.boids = group;}
	

	/**
	 * Computes positive modulo
	 */
	public static double mod(double a, double b) {
		double r = a % b;
		if (r < 0) {return r + b;}
		return r;
	}

	/**
	 * Computes positive modulo 2*PI of an angle	
	 */
	public static double aMod(double a) {
		return mod(a, 2 * Math.PI);
	}
	/**
	 * Compute direction of boid
	 */
	public void computeDirection() {
		this.direction = aMod(Math.atan2(nextVelocity.getY(), nextVelocity.getX()));
	}

	/**
	 * Apply rotation of center {@link #position} and angle
	 * {@link #direction} to the point (x,y).
	 * 
	 * @param x 	x coordinate
	 * @param y 	y coordinate
	 * 
	 * @return result
	 * @see Boid#computeTriangle()
	 */
	public double[] turn(double x, double y) {
		double[] pts = { x , y };
		AffineTransform.getRotateInstance(getDirection(), position.getX(),
							position.getY()).transform(pts, 0, pts, 0, 1);
		return pts;
	}

	/**
	 * Computes the three vertices of the triangle corresponding to the boid
	 */
	public void computeTriangle() {
		final double cx = position.getX();
		final double cy = position.getY();
		final double curSize = (double)size;
		final double downSize = curSize / 4;

		double[] pts = turn(cx + 2*curSize/3, cy);
		triXPts[0] = (int)pts[0];
		triYPts[0] = (int)pts[1];

		pts = turn(cx - curSize/3, cy + downSize);
		triXPts[1] = (int)pts[0];
		triYPts[1] = (int)pts[1];

		pts = turn(cx - curSize/3, cy - downSize);
		triXPts[2] = (int)pts[0];
		triYPts[2] = (int)pts[1];
	}

	

	/**
	 * updates position of boid while considering boundaries of graphical windows
	 * @param pos  Boid position
	 */
	public void setPos(Vector pos) {
		pos.x = mod(pos.x, Boids.getWidth());
		pos.y = mod(pos.y, Boids.getHeight());
		this.setNextPosition(pos);
	}

	
	/**
	 * Checks whether b is very close to this
	 */
	public boolean isNear(Boid b) {
		return this != b && position.distance(b.position) < MIN_DIST;
	}

	/**
	 * Checks whether b is a neighbor in the field of vision of this
	 */
	public boolean isNeighbor(Boid b) {
		
		// Calculates orientation of b :/: to this
		double dirRelativX = b.position.x - position.x;
		double dirRelativY = b.position.y - position.y;
		
		double neighborDir = aMod(Math.atan2(dirRelativY, dirRelativX));

		double minAngle = aMod(direction + VISION);
		double maxAngle = aMod(direction - VISION);
		
		if (maxAngle < minAngle) {
			final double tmp = maxAngle;
			maxAngle = minAngle;
			minAngle = tmp;
		}
		
		/*
		 * if we want to simulate in an unlimited sky, we need to take into consideration
		 * boids that are close enough on the other side of the screen. For example if screen
		 * width is 500 and height is 500, let's consider Boid1(440, 5) and Boid2(438, 488)
		 * then in an unlimited sky this means that the distance between the two boids is
		 * SQRT(2**2 + 17**2) = 17.11 < MIN_DIST = 20
		 * 
		 * dirRelativX = b.position.x + position.x - Boids.getWidth();
		 * dirRelativY = b.position.y + position.y - Boids.getHeight();
		 * Vector diff = new Vector(dirRelativX, dirRelativY);
		 * double dist = diff.norm();
		 */
		
		
		// Checks whether it's a neighbor in the field of vision
		boolean a = position.distance(b.position) < NEIGHBORHOOD;
		          //|| dist < NEIGHBORHOOD;
		return this != b && a && !(minAngle <= neighborDir && neighborDir <= maxAngle);

	}
	
	/**
     * Return the force f1 required to move to the centre of mass
     * by MOVE_FACTOR% of the distance between the boids and the center
	 * 
	 */
	private Vector ruleFlyToCenter() {
		Vector force = new Vector();
		int count = 0;

		for(Boid b : boids) {
			if(isNeighbor(b)) {
				force.add(b.position);
				count++;
			}
		}

		if (count > 0) {
			force.div(count);
			force.sub(position);
			force.div(MOVE_FACTOR);
		}

		return force;
	}
	
	/**
     * Returns the force f2 required in order to stop each boid from
     * colliding with its neighbours
     */
	private Vector ruleKeepDistance() {
		Vector steeringForce = new Vector();
		for(Boid b : boids) {
			if(isNear(b)) {
				steeringForce.add(position);
				steeringForce.sub(b.position);
			}
		}
		return steeringForce;
	}
	
	/**
     * Returns the velocity f3 required by the boid in order to
     * be aligned with his neighbours
     * @return a Point representing f3
     */
	private Vector ruleMatchvelocity() {
		Vector v = new Vector();
		int count = 0;

		for(Boid b : boids) {
			if(isNeighbor(b)) {
				v.add(b.velocity);
				count++;
			}
		}

		if (count > 0) {
			v.div(count);
			v.sub(velocity);
			v.div(4);
		}
		
		return v;
	}
	
	private Vector ruleBoundary() {
		int xmin = 100;
		int xmax = Boids.getWidth() - 100;
		int ymin = 100;
		int ymax = Boids.getHeight() - 100;
		Vector force = new Vector();
		if (position.x < xmin) {
			force.x = 5;
		}
		else if (position.x > xmax) {
			force.x -= 5;
		}

		if (position.y < ymin) {
			force.x = 5;
		}
		else if (position.y > ymax) {
			force.x -= 5;
		}

		return force;
	}
	/**
	 * PreUpdate
	 */
	public void update() {
		Vector force = ruleFlyToCenter();
		force.add(ruleKeepDistance());
		force.add(ruleMatchvelocity());

		force.speedLimit(BASE_MAX_FORCE);
		acceleration.add(force.div(getMass()));

		nextVelocity.add(acceleration);
		nextVelocity.speedLimit(BASE_MAX_VELOCITY);
		setPos(nextPosition.add(nextVelocity));
		acceleration.reset();
	}

	/**
	 * Applies force to boid
	 */
	public void applyForce(Vector force) {
		force.speedLimit(BASE_MAX_FORCE);
		acceleration.add(force.div(getMass()));
	}

	/**
	 * Applies the three rules of the model to the boid and updates it.
	 * @see Boid#update()
	 */
	public void move() {
		Vector force = ruleFlyToCenter();
		force.add(ruleKeepDistance());
		force.add(ruleMatchvelocity());
		//force.add(ruleBoundary());
		applyForce(force);
		update();
	}
	
	/**
	 * returns a textual representation of boid
	 * 
	 */
	public String toString() {
		return "Boid(x : " + position.x + ", y :" + position.y + ", vx : " + velocity.x + ", vy : " 
				+ velocity.y + ", ax : " + acceleration.x + ", ay : " + acceleration.y + ")";
	}
}


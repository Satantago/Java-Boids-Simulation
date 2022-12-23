package group;

import element.Boid;
import java.util.LinkedList;
import java.util.Iterator;

public class Boids {

	private static int width;
	private static int height;

	private LinkedList<Boid> boids;
	private LinkedList<Boid> beginning;

	/**
	 * Constructor
	 */
	public Boids() {
		boids = new LinkedList<Boid>();
		beginning = new LinkedList<Boid>();
	}

	/**
	 * Adds a boid to the group
	 */
	public void add(Boid b) {
		boids.add(b);
		b.setGroup(boids);
		beginning.add(b.clone());
	}


	public static void setWidth(int width) {Boids.width = width;}
	public static void setHeight(int height) {Boids.height = height;}
	public static int getWidth() {return width;}
	public static int getHeight() {return height;}


	/**
	 * Moves all boids
	 */
	public void update() {
		
		Iterator<Boid> it = boids.iterator();
		Boid b;

		while (it.hasNext()) {
			b = it.next();
			b.update();
		}

		it = boids.iterator();
		while (it.hasNext()) {
			b = it.next();
			b.setPosition(b.getNextPosition());
			b.setVelocity(b.getNextVelocity());	
			b.computeDirection();
		}
	}
	
	public Iterator<Boid> iterator(){
		return boids.iterator();
	}

	/**
	 * Resets the group
	 */
	public void reset() {
		boids.clear();
		for(Boid b : beginning) {
			boids.add(b.clone());
		}
		//beginning.clear();

	}

	/**
	 * Displays boids
	 * @return	Textual representation
	 */
	public String toString() {
		String str = "Boids\n";

		for (Boid b : boids) {
			str += b + "\n";
		}

		return str;
	}
}
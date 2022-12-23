package test;


import group.Boids;
import simulator.BoidsSimulator;
import element.Boid;
import gui.GUISimulator;
import java.awt.Color;
import java.util.Random;


public class TestBoidsSimulator {

	
	public static void main(String[] args) {
		int x, y, vx, vy, ax, ay;
		Random rand;

		Boids boids = new Boids();
		for (int i = 0; i < 100; i++) {
			rand = new Random();
			x = rand.nextInt(850);
			y = rand.nextInt(500);
			vx = rand.nextInt(10);
			vx -= 5;
			vy = rand.nextInt(10);
			vx -= 5;
			ax = rand.nextInt(10);
			ay = rand.nextInt(10);
			ax -= 5;
			ay -= 5;

			boids.add(new Boid(x, y, vx, vy, ax, ay));
		}
		
		//System.out.println(boids);

		GUISimulator gui = new GUISimulator(850, 500, Color.GRAY);

		gui.setSimulable(new BoidsSimulator(gui, boids));
	}
}

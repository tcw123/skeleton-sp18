public class NBody {
	//the image of background
	private static String background = "images/starfield.jpg";
	/** 读取文件中宇宙的半径范围。*/
	public static double readRadius(String file){
		In in = new In(file);
		int i = in.readInt();
		double radius = in.readDouble();
		return radius;
	}
	/** 读取文件中五个星球的信息。*/
	public static Planet[] readPlanets(String file){
		In in = new In(file);
		int ammofPlanets = in.readInt();
		Planet planets[] = new Planet[ammofPlanets];
		in.readDouble();
		for(int i = 0; i < ammofPlanets; i++){
			double pxxPos = in.readDouble();
			double pyyPos = in.readDouble();
			double pxxVel = in.readDouble();
			double pyyVel = in.readDouble();
			double pmass  = in.readDouble();
			String pimgFileName = in.readString();
			Planet p = new Planet(pxxPos, pyyPos, pxxVel, pyyVel, pmass, pimgFileName);
			planets[i] = new Planet(p);
		}
		return planets;
	}
	/** drawing the initial universe state. */
	public static void main(String[] args) {
		//collecting all needed input
		double T  = Double.parseDouble(args[0]);
		double dt = Double.parseDouble(args[1]);
		String filename = args[2];
		double radius = NBody.readRadius(filename);
		Planet[] planets = NBody.readPlanets(filename);
		//drawing the background
		StdDraw.setScale(-radius, radius);
		StdDraw.clear();
		StdDraw.picture(0, 0, background);
		//drawing all of the planets
		for(Planet p : planets){
			p.draw();
		}
		StdDraw.show();
		//enable double buffering 
		StdDraw.enableDoubleBuffering();
		StdAudio.play("audio/2001.wav");
		int time = 0;
		while(time < (int)T) {
			double[] xForces = new double[planets.length];
			double[] yForces = new double[planets.length];
			for(int i = 0; i < planets.length; i++){
				xForces[i] = planets[i].calcNetForceExertedByX(planets);
				yForces[i] = planets[i].calcNetForceExertedByY(planets);
			}
			for (int i = 0; i < planets.length; i++) {
				planets[i].update(dt, xForces[i], yForces[i]);
			}
			StdDraw.clear();
			StdDraw.picture(0, 0, background);
			for(Planet p : planets) {
				p.draw();
			}
			StdDraw.show();
			StdDraw.pause(10);
			time += dt;
		}
		//把结果星体运动结束后的结果输出
		StdOut.printf("%d\n", planets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < planets.length; i++) {
  			StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel,
            planets[i].yyVel, planets[i].mass, planets[i].imgFileName);   
		}
	}
}
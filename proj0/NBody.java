public class NBody{
	public static double readRadius(String filename){
		In in = new In(filename);
		int numberplanets = in.readInt();
		double radius = in.readDouble();

		return radius;

	}

	public static Planet[] readPlanets(String filename){
		In in = new In(filename);
		int numberplanets = in.readInt();
		double radius = in.readDouble();
        Planet[] allplanets = new Planet[numberplanets];
        
        for (int i=0; i<numberplanets; i++) {
        	double xP = in.readDouble();
            double yP = in.readDouble();
            double xV = in.readDouble();
            double yV = in.readDouble();
            double m = in.readDouble();
            String img = in.readString();
            allplanets[i] = new Planet(xP, yP, xV, yV, m, img);
        }  
            
        return allplanets;
    }

    public static void main(String[] args) {
    	double T = Double.parseDouble(args[0]);
    	double dt = Double.parseDouble(args[1]);
    	String filename = args[2];
    	double radius = readRadius(filename);
    	Planet[] allplanets= readPlanets(filename);

    	StdDraw.setScale(-radius, radius);
    	StdDraw.picture(0, 0, "images/starfield.jpg");

    	for (int i=0; i< allplanets.length ; i++) {
    		allplanets[i].draw();
    	}

    	StdDraw.enableDoubleBuffering();

    	for (double time=0;time<T ; time+=dt ) {
    		double[] xForces = new double[allplanets.length];
    		double[] yForces = new double[allplanets.length];

    		for (int i=0; i<allplanets.length ;i++ ) {
    			xForces[i]= allplanets[i].calcNetForceExertedByX(allplanets);
    			yForces[i]= allplanets[i].calcNetForceExertedByY(allplanets);

    		}
    		for (int i=0; i<allplanets.length ;i++ ) {
    			allplanets[i].update(dt,xForces[i],yForces[i]);

    		}
    		StdDraw.picture(0, 0, "images/starfield.jpg");
    		for (int i =0; i<allplanets.length ;i++ ) {
    			allplanets[i].draw();
    		}
    		StdDraw.show();
    		StdDraw.pause(10);



    	}

    	StdOut.printf("%d\n", allplanets.length);
		StdOut.printf("%.2e\n", radius);
		for (int i = 0; i < allplanets.length; i++) {
    		StdOut.printf("%11.4e %11.4e %11.4e %11.4e %11.4e %12s\n",
                  allplanets[i].xxPos, allplanets[i].yyPos, allplanets[i].xxVel,
                  allplanets[i].yyVel, allplanets[i].mass, allplanets[i].imgFileName);   
}
    }
}

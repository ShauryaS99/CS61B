public class Planet{
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;
	public String imgFileName;
	private static final double gravity = 6.67e-11;

	public Planet(double xP, double yP, double xV,
              double yV, double m, String img){
		xxPos = xP;
		yyPos = yP;
		xxVel = xV;
		yyVel = yV;
		mass = m;
		imgFileName = img;
	}

	public Planet(Planet p){
		xxPos = p.xxPos;
		yyPos = p.yyPos;
		xxVel = p.xxVel;
		yyVel = p.yyVel;
		mass = p.mass;
		imgFileName = p.imgFileName;

	}

	public double calcDistance(Planet p){
		double dx = Math.pow(this.xxPos -p.xxPos, 2);
		double dy = Math.pow(this.yyPos -p.yyPos, 2);
		return Math.pow(dx+dy,0.5);
	}

	public double calcForceExertedBy(Planet p){
		
		double rad2 = Math.pow(calcDistance(p),2);
		double force = (gravity*this.mass*p.mass)/rad2;
		return force;
	}

	public double calcForceExertedByX(Planet p){
		double rad = calcDistance(p);
		double fx = (calcForceExertedBy(p) * (p.xxPos - this.xxPos))/rad;
		return fx;
		
	}
	public double calcForceExertedByY(Planet p){
		double rad = calcDistance(p);
		double fy = (calcForceExertedBy(p) * (p.yyPos - this.yyPos))/rad;
		return fy;
		
	}

	public double calcNetForceExertedByX(Planet[] allPlanets){
		double fx = 0;
		int i =0;
		while (i < allPlanets.length){
			if (!this.equals(allPlanets[i]))
            {
                fx += calcForceExertedByX(allPlanets[i]);
            }
            i++;
		}
		return fx;
        		
	}
	public double calcNetForceExertedByY(Planet[] allPlanets){
		double fy = 0;
		int i =0;
		while (i < allPlanets.length){
			if (!this.equals(allPlanets[i]))
            {
                fy += calcForceExertedByY(allPlanets[i]);
            }
            i++;
		}
		return fy;
        		
	}
	public void update(double dt, double fX, double fY){
		double ax= fX/this.mass;
		double ay= fY/this.mass;
		this.xxVel = this.xxVel + (dt*ax);
		this.yyVel = this.yyVel + (dt*ay);
		this.xxPos = this.xxPos + (dt*this.xxVel);
		this.yyPos = this.yyPos + (dt*this.yyVel);
	}

	public void draw(){
		StdDraw.picture(this.xxPos,this.yyPos,"images/" + this.imgFileName);
	}

}
public class Planet {	
	public double xxPos;
	public double yyPos;
	public double xxVel;
	public double yyVel;
	public double mass;//质量
	public String imgFileName;
	private static final double G = 6.67e-11;
	/** 初始化一颗行星的坐标等信息     */
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
		this.xxPos = p.xxPos;
		this.yyPos = p.yyPos;
		this.xxVel = p.xxVel;
		this.yyVel = p.yyVel;
		this.mass = p.mass;
		this.imgFileName = p.imgFileName;
	}
	/** 计算两颗行星之间的距离      */
	public double calcDistance(Planet p){
		double r;
		double x_distance;
		double y_distance;
		x_distance = this.xxPos - p.xxPos;
		y_distance = this.yyPos - p.yyPos;
		r = Math.sqrt(x_distance * x_distance + y_distance * y_distance);
		return r;
	}
	/** 两颗行星之间的相互作用力     */
	public double calcForceExertedBy(Planet p){
		double r = this.calcDistance(p);
		double force;
		force = (G * this.mass * p.mass) / (r * r);
		return force;
	}
    /** 两颗行星之间相互作用力在x方向上的分力   */
	public double calcForceExertedByX(Planet p){
		double force = this.calcForceExertedBy(p);
		double xforce = force * ((p.xxPos - this.xxPos) / this.calcDistance(p));
	 	return xforce;
	}
	/** 两颗行星之间相互作用力在y方向上的分力  */
	public double calcForceExertedByY(Planet p){
		double force = this.calcForceExertedBy(p);
		double yforce = force * ((p.yyPos - this.yyPos) / this.calcDistance(p));
		return yforce;
	}
	/** 星体所受的其他星体的合力在x方向上的分量   */
	public double calcNetForceExertedByX(Planet[] allPlanets){
		double netforce_x = 0.0;
		for(Planet p : allPlanets){
			if(p.equals(this)){
				continue;
			}
			netforce_x += this.calcForceExertedByX(p);
		}
		return netforce_x;
	}
	/** 星体所受的其他星体的合力在x方向上的分量   */
	public double calcNetForceExertedByY(Planet[] allPlanets){
		double netforce_y = 0.0;
		for(Planet p : allPlanets){
			if(p.equals(this)){
				continue;
			}
			netforce_y += this.calcForceExertedByY(p);
		}
		return netforce_y;
	}
	/** 根据施加的力的大小及其时间，对目标星体加速,计算出加速后星体的坐标。 */
	public void update(double time, double x_force, double y_force){
		double a_x, a_y;
		double new_xxVel, new_yyVel;
		double new_xxPos, new_yyPos;
		a_x = x_force / this.mass;
		a_y = y_force / this.mass;
		new_xxVel = this.xxVel + time * a_x;
		new_yyVel = this.yyVel + time * a_y;
		new_xxPos = this.xxPos + time * new_xxVel;
		new_yyPos = this.yyPos + time * new_yyVel;
		this.xxVel = new_xxVel;
		this.yyVel = new_yyVel;
		this.xxPos = new_xxPos;
		this.yyPos = new_yyPos;
	}
	/** let a planet to be able to draw itself at its appropriate position. */
	public void draw(){
		StdDraw.picture(this.xxPos, this.yyPos, "images/"+this.imgFileName);
	}
	
}
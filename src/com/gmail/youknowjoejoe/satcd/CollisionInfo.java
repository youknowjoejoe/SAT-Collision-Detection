package com.gmail.youknowjoejoe.satcd;

public class CollisionInfo {
	private Polygon p1;
	private Polygon p2;
	private boolean colliding = true;
	private float penetrationDistance = 0;
	private Vector2f collisionNormal = new Vector2f(0,0);
	private boolean penetrationDistanceSet = false;
	
	public Polygon getP1() {
		return p1;
	}

	public void setP1(Polygon p1) {
		this.p1 = p1;
	}

	public Polygon getP2() {
		return p2;
	}

	public void setP2(Polygon p2) {
		this.p2 = p2;
	}

	public boolean isColliding() {
		return colliding;
	}

	public void setColliding(boolean colliding) {
		this.colliding = colliding;
	}

	public float getPenetrationDistance() {
		return penetrationDistance;
	}

	public void setPenetrationDistance(float penetrationDistance) {
		this.penetrationDistance = penetrationDistance;
	}

	public Vector2f getCollisionNormal() {
		return collisionNormal;
	}

	public void setCollisionNormal(Vector2f collisionNormal) {
		this.collisionNormal = collisionNormal;
	}

	public CollisionInfo(Polygon p1, Polygon p2){
		this.p1 = p1;
		this.p2 = p2;
		
		Vector2f[] p1Verts = p1.getTransformedVertices();
		Vector2f[] p2Verts = p2.getTransformedVertices();
		
		generateAndTestPolygonsAxis(p1Verts,p2Verts);
		generateAndTestPolygonsAxis(p2Verts,p1Verts);
	}
	
	public void generateAndTestPolygonsAxis(Vector2f[] p1Verts, Vector2f[] p2Verts){
		for(int i = 0; i < p1Verts.length; i++){
			if(!colliding){
				break;
			}
			Vector2f cn = p1Verts[(i+1)%p1Verts.length].minus(p1Verts[i]).perpendicular().normalized();
			
			Vector2f minMaxP1;
			Vector2f minMaxP2;
			
			minMaxP1 = CollisionInfo.projectOntoAxis(p1Verts, cn);
			minMaxP2 = CollisionInfo.projectOntoAxis(p2Verts, cn);
			
			float overlap = getMinMaxOverlap(minMaxP1,minMaxP2);
			
			if(overlap <= 0){
				colliding = false;
				break;
			} else if(penetrationDistanceSet && overlap < penetrationDistance) {
				penetrationDistance = overlap;
				collisionNormal = cn;
			} else if(!penetrationDistanceSet){
				penetrationDistance = overlap;
				collisionNormal = cn;
				penetrationDistanceSet = true;
			}
		}
	}
	
	//verts = a line segment
	public static Vector2f projectOntoAxis(Vector2f[] verts, Vector2f axis){
		float min;
		float max;
		
		min = verts[0].dot(axis);
		max = min;
		
		for(int i = 1; i < verts.length; i++){
			float dot = verts[i].dot(axis);
			if(dot < min){
				min = dot;
			}
			if(dot > max){
				max = dot;
			}
		}
		
		return new Vector2f(min,max);
	}
	
	public static float getMinMaxOverlap(Vector2f mm1, Vector2f mm2){
		if(mm1.getY() > mm2.getX() && mm2.getY() > mm1.getX()){
			return Math.min(Math.abs(mm1.getY()-mm2.getX()),Math.abs(mm2.getY()-mm1.getX()));
		}
		return -1;
	}
}

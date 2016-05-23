package com.gmail.youknowjoejoe.satcd;

public class Vector2f {
	private float x,y;
	
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	
	public Vector2f plus(Vector2f v){
		return new Vector2f(this.x+v.getX(),this.y+v.getY());
	}
	
	public Vector2f minus(Vector2f v){
		return new Vector2f(this.x-v.getX(),this.y-v.getY());
	}
	
	public Vector2f perpendicular(){
		return new Vector2f(this.y,-this.x);
	}
	
	public Vector2f normalized(){
		float magnitude = (float) Math.sqrt(this.x*this.x+this.y*this.y);
		return new Vector2f(this.x/magnitude, this.y/magnitude);
	}
	
	public float dot(Vector2f v){
		return (this.x*v.getX()) + (this.y*v.getY());
	}
	
	public Vector2f scaledBy(float f){
		return new Vector2f(this.x*f,this.y*f);
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}
}

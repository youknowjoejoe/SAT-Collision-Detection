package com.gmail.youknowjoejoe.satcd;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;

public class Polygon {
	private Vector2f[] vertices;
	private Matrix33 transform;
	private java.awt.Polygon polygon;
	private Color color;
	
	public Polygon(Vector2f[] vertices){
		this.vertices = vertices;
		this.transform = Matrix33.getIdentity();
		this.color = new Color((float) Math.random(),(float) Math.random(),(float) Math.random(), 0.5f);
		
		this.polygon = Polygon.getPolygon(vertices);
	}
	
	public void draw(Graphics2D g2d){
		g2d.setColor(color);
		AffineTransform save = g2d.getTransform();
		AffineTransform at = new AffineTransform(transform.getM11(), transform.getM21(), transform.getM12(), transform.getM22(), transform.getM13(), transform.getM23());
		g2d.transform(at);
		g2d.fill(polygon);
		g2d.setTransform(save);
	}
	
	public Matrix33 getTransform() {
		return transform;
	}

	public void setTransform(Matrix33 transform) {
		this.transform = transform;
	}

	public static java.awt.Polygon getPolygon(Vector2f[] v){
		int[] xverts = new int[v.length];
		int[] yverts = new int[v.length];
		int nverts = v.length;
		
		for(int i = 0; i < v.length; i++){
			xverts[i] = (int)v[i].getX();
			yverts[i] = (int)v[i].getY();
		}
		
		return new java.awt.Polygon(xverts,yverts,nverts);
	}
	
	public int getVerticesLength(){
		return vertices.length;
	}
	
	public void transform(Matrix33 m){
		this.transform = m.times(this.transform);
	}
	
	public Vector2f[] getTransformedVertices(){
		Vector2f[] transformedVertices = new Vector2f[vertices.length];
		for(int i = 0; i < vertices.length; i++){
			transformedVertices[i] = transform.times(vertices[i]);
		}
		return transformedVertices;
	}
}

package com.gmail.youknowjoejoe.satcd;

public class Matrix33 {
	private float m11,m12,m13;
	private float m21,m22,m23;
	private float m31,m32,m33;
	
	public Matrix33(float m11, float m12, float m13, float m21, float m22, float m23, float m31, float m32, float m33){
		this.m11 = m11;
		this.m12 = m12;
		this.m13 = m13;
		this.m21 = m21;
		this.m22 = m22;
		this.m23 = m23;
		this.m31 = m31;
		this.m32 = m32;
		this.m33 = m33;
	}
	
	public Matrix33(float theta){
		this.m11 = (float) Math.cos(theta);
		this.m12 = (float) -Math.sin(theta);
		this.m13 = 0;
		this.m21 = (float) Math.sin(theta);
		this.m22 = (float) Math.cos(theta);
		this.m23 = 0;
		this.m31 = 0;
		this.m32 = 0;
		this.m33 = 0;
	}
	
	public Matrix33(float x, float y, boolean translation){
		defineMatrix(x,y,translation);
	}
	
	public Matrix33(Vector2f v, boolean translation){
		defineMatrix(v.getX(),v.getY(),translation);
	}
	
	private void defineMatrix(float x, float y, boolean translation){
		if(translation){
			this.m11 = 1;
			this.m12 = 0;
			this.m13 = x;
			this.m21 = 0;
			this.m22 = 1;
			this.m23 = y;
			this.m31 = 0;
			this.m32 = 0;
			this.m33 = 1;
		} else {
			this.m11 = x;
			this.m12 = 0;
			this.m13 = 0;
			this.m21 = 0;
			this.m22 = y;
			this.m23 = 0;
			this.m31 = 0;
			this.m32 = 0;
			this.m33 = 1;
		}
	}
	
	
	
	public Matrix33 times(Matrix33 m){
		return new Matrix33(m11*m.getM11()+m12*m.getM21()+m13*m.getM31(),m11*m.getM12()+m12*m.getM22()+m13*m.getM32(),m11*m.getM13()+m12*m.getM23()+m13*m.getM33(),
				m21*m.getM11()+m22*m.getM21()+m23*m.getM31(),m21*m.getM12()+m22*m.getM22()+m23*m.getM32(),m21*m.getM13()+m22*m.getM23()+m23*m.getM33(),
				m31*m.getM11()+m32*m.getM21()+m33*m.getM31(),m31*m.getM12()+m32*m.getM22()+m33*m.getM32(),m31*m.getM13()+m32*m.getM23()+m33*m.getM33());
	}
	
	public Vector3f times(Vector3f v){
		return new Vector3f(this.m11*v.getX()+this.m12*v.getY()+this.m13*v.getZ(),this.m21*v.getX()+this.m22*v.getY()+this.m23*v.getZ(),this.m31*v.getX()+this.m32*v.getY()+this.m33*v.getZ());
	}
	
	public Vector2f times(Vector2f v){
		return new Vector2f(this.m11*v.getX()+this.m12*v.getY()+this.m13*1,this.m21*v.getX()+this.m22*v.getY()+this.m23*1);
	}

	public float getM11() {
		return m11;
	}

	public float getM12() {
		return m12;
	}

	public float getM13() {
		return m13;
	}

	public float getM21() {
		return m21;
	}

	public float getM22() {
		return m22;
	}

	public float getM23() {
		return m23;
	}

	public float getM31() {
		return m31;
	}

	public float getM32() {
		return m32;
	}

	public float getM33() {
		return m33;
	}
	
	public static Matrix33 getIdentity(){
		return new Matrix33(1,0,0,0,1,0,0,0,1);
	}
}

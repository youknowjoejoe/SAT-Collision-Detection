package com.gmail.youknowjoejoe.satcd;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TestPanel extends JPanel implements Runnable, KeyListener{
	private int width,height;
	private boolean running = true;
	private Polygon p1;
	private Polygon p2;
	private boolean upDown=false,rightDown=false,leftDown=false,downDown=false;
	private Matrix33 identity = Matrix33.getIdentity();
	private Matrix33 rightVel = new Matrix33(0.0001f,0,true);
	private Matrix33 leftVel = new Matrix33(-0.0001f,0,true);
	private Matrix33 upVel = new Matrix33(0,-0.0001f,true);
	private Matrix33 downVel = new Matrix33(0,0.0001f,true);
	private double dt;
	private double oldTime;
	private double currentTime;
	private double FPS;
	
	public TestPanel(int width, int height){
		this.width = width;
		this.height = height;
		this.setPreferredSize(new Dimension(width,height));
		this.addKeyListener(this);
		this.setFocusable(true);
		this.requestFocus();
		
		p1 = new Polygon(new Vector2f[]{new Vector2f(0,50), new Vector2f(70,30), new Vector2f(100,100), new Vector2f(50,200)});
		p2 = new Polygon(new Vector2f[]{new Vector2f(300,400), new Vector2f(400,500), new Vector2f(450,300), new Vector2f(300,200)});
		
		(new Thread(this)).start();
	}
	
	public static void main(String args[]){
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				JFrame window = new JFrame("SAT Collision Detection Demo");
				window.add(new TestPanel(800,600));
				window.setResizable(false);
				window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				window.pack();
				window.setVisible(true);
			}
		});
	}
	
	@Override
	public void run() {
		
		oldTime = System.nanoTime()/1000000000;
		currentTime = oldTime;
		dt = 0;
		while(running){
			currentTime = System.nanoTime()/1000000000;
			dt = currentTime-oldTime;
			oldTime = currentTime;
			FPS = 1/dt;
			logic();
			repaint();
		}
	}
	
	public void logic(){
		Matrix33 movement = identity;
		if(upDown){
			movement = upVel.times(movement);
		}
		if(rightDown){
			movement = rightVel.times(movement);
		}
		if(leftDown){
			movement = leftVel.times(movement);
		}
		if(downDown){
			movement = downVel.times(movement);
		}
		p1.transform(movement);
		CollisionInfo ci = new CollisionInfo(p1,p2);
		if(ci.isColliding()){
			p1.transform(new Matrix33(ci.getCollisionNormal().scaledBy(-ci.getPenetrationDistance()),true));
		}
		
		
	}
	
	@Override
	public void paintComponent(Graphics g){
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setColor(Color.white);
		g2d.fillRect(0, 0, width, height);
		
		g2d.setColor(Color.black);
		g2d.drawString(""+FPS, 10, 10);
		
		p1.draw(g2d);
		p2.draw(g2d);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			upDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			leftDown = true;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			downDown = true;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_UP){
			upDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_RIGHT){
			rightDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_LEFT){
			leftDown = false;
		}
		if(e.getKeyCode() == KeyEvent.VK_DOWN){
			downDown = false;
		}
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
		
	}
}

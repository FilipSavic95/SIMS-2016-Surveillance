package model;

import java.awt.Point;

import controler.Observer;

public abstract class Widget implements Observer{
	public Point position;
	String scriptCode;
	
	public Widget() {
	}
}

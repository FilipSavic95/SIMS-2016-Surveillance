package model.states;

public class Initial implements State{
	
	public Initial() {
	}

	@Override
	public void drawComponent(int X, int Y, char c) {
		// TODO Auto-generated method stub
		System.out.println("Initial state, no drawings.");
//		panel = "action" if c=='a', else "monitoring"
		String panel = c == 'a' ? "action" : "monitoring";
		System.out.println("X = " + X + ", Y = " + Y + ", panel = " + panel + "\n");
	}
}

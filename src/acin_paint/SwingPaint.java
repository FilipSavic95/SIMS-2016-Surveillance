package acin_paint;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class SwingPaint {
	
	public JButton clrBtn, blackBtn, blueBtn, greenBtn, redBtn, magentaBtn;
	public DrawArea drawArea;
	
	
	public static void main(String[] args) {
		new SwingPaint().showIt();
	}
	
	public void showIt() {
		JFrame frame = new JFrame("CRTANJEEEEEee");
		
		Container content = frame.getContentPane();
		content.setLayout(new BorderLayout());
		drawArea = new DrawArea();
		content.add(drawArea, BorderLayout.CENTER);
		
		// kontorle za boje i ostale komande
		JPanel controls = new JPanel();
		
		clrBtn = new JButton("Clear");
		clrBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				drawArea.clear();
			}
		});
		
		blackBtn = new JButton("black");
		blackBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				drawArea.black();
			}
		});
		
		blueBtn = new JButton("blue");
		blueBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				drawArea.blue();
			}
		});
		
		greenBtn = new JButton("green");
		greenBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				drawArea.green();
			}
		});
		
		redBtn = new JButton("red");
		redBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				drawArea.red();
			}
		});
		
		magentaBtn = new JButton("Magenta");
		magentaBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				drawArea.magenta();
			}
		});

		// dodajemo u panel
		controls.add(redBtn);
		controls.add(greenBtn);
		controls.add(blueBtn);
		controls.add(blackBtn);
		controls.add(magentaBtn);
		controls.add(clrBtn);
		
		// dodajemo u sadrzaj frame-a
		content.add(controls, BorderLayout.NORTH);
		
		frame.setSize(500, 500);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		frame.setVisible(true);
		
	}
}

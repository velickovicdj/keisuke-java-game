import javax.swing.*;
import java.awt.*;
import java.io.FileNotFoundException;

public class Menu{
	public void mainMenu(JFrame frame) {
		for(int i=100; i<=300; i+=200) {
			int state = i;
			String txt = state == 100 ? "NEW LAYOUT" : "LOAD LAYOUT";
			JButton btn = new JButton();
			btn.setBounds(150, i, 200, 50);
			btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn.setFocusable(false);
			btn.setText(txt);
			btn.addActionListener(e -> {
				frame.getContentPane().removeAll();
				frame.repaint();
				if(state == 100) {
					newGameMenu(frame);
				}else {
					try {
						new Game(frame, 0, 0).loadLayout();
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					}
					//loadGameMenu(frame);
				}
			});
			frame.add(btn);
			frame.setTitle("Keisuke");
		}
	}
	public void newGameMenu(JFrame frame) {
		frame.setTitle("New Game - Keisuke");
		for(int i=50; i<=350; i+=100) {
			int state = i;
			String txt = "";
			switch(state) {
				case 50:
					txt = "STANDARD";
					break;
				case 150:
					txt = "ENDLESS";
					break;
				case 250:
					txt = "SET SIZE";
					break;
				case 350:
					txt = "MAIN MENU";
					break;
			}
			JButton btn = new JButton();
			btn.setBounds(150, i, 200, 50);
			btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
			btn.setFocusable(false);
			btn.setText(txt);
			btn.addActionListener(e -> {
				frame.getContentPane().removeAll();
				frame.repaint();
				switch(state) {
				case 50:
					frame.getContentPane().removeAll();
					frame.repaint();
					new Game(frame, 5, 5).newLayout();
					break;
				case 150:
					frame.getContentPane().removeAll();
					frame.repaint();
					new Game(frame, 5, 5).endless();
					break;
				case 250:
					JTextField width = new JTextField();
					JTextField height = new JTextField();
					
					height.setBounds(150, 150, 50, 50);
					width.setBounds(300, 150, 50, 50);

					JLabel label = new JLabel();
					JButton okBtn = new JButton();
					JButton cancelBtn = new JButton();
					
					label.setText("X");
					label.setBounds(250, 150, 50, 50);
					
					okBtn.setText("OK");
					okBtn.setBounds(150, 250, 200, 50);
					okBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					okBtn.setFocusable(false);
					okBtn.addActionListener(event ->{
						int h = Integer.parseInt(height.getText());
						int w = Integer.parseInt(width.getText());
						
						if(h<=12 && w<=12) {
							if(h<2) {
								JOptionPane.showMessageDialog(frame, "It's too easy to play with just one row! Please set the field size to something more appropriate.");
								return;
							}
							if(w<2) {
								JOptionPane.showMessageDialog(frame, "It's too easy to play with just one column! Please set the field size to something more appropriate.");
								return;
							}
							if(h<3 && w<3) {
								JOptionPane.showMessageDialog(frame, "It doesn't make sense to play on such a small field... Please set the field size to at least 3x3.");
								return;
							}
							frame.getContentPane().removeAll();
							frame.repaint();
							new Game(frame, h, w).newLayout();
						}else {
							JOptionPane.showMessageDialog(frame, "Error! Cannot create the field. Maximum field size is 12x12.");
						}
					});
					
					cancelBtn.setText("Cancel");
					cancelBtn.setBounds(150, 350, 200, 50);
					cancelBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
					cancelBtn.setFocusable(false);
					cancelBtn.addActionListener(event -> {
						frame.getContentPane().removeAll();
						frame.repaint();
						newGameMenu(frame);
					});
					frame.add(height);
					frame.add(width);
					frame.add(label);
					frame.add(okBtn);
					frame.add(cancelBtn);
					break;
				case 350:
					mainMenu(frame);
					break;
			}
			});
			frame.add(btn);
		}
	}
//	public void loadGameMenu(JFrame frame) {
//		JTextField filename = new JTextField();
//		
//		filename.setBounds(200, 150, 150, 50);
//
//		JLabel label = new JLabel();
//		JButton okBtn = new JButton();
//		JButton cancelBtn = new JButton();
//		
//		label.setText("Enter the name of saved layout:");
//		label.setBounds(200, 100, 150, 50);
//		
//		frame.add(filename);
//		frame.add(okBtn);
//		frame.add(cancelBtn);
//		frame.setTitle("Load Game - Keisuke");
//	}
}

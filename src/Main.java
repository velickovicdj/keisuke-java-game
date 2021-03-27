import javax.swing.*;

public class Main{
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		new Menu().mainMenu(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.setSize(500, 500);
		frame.setTitle("Keisuke");
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}

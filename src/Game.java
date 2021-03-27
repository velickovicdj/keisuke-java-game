import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Scanner;

public class Game{
	Button Button = new Button();
	Settings Settings = new Settings();
	
	JFrame frame;
	
	JPanel playingField = new JPanel();
	
	JButton quitBtn = new JButton();
	JButton settingsBtn = new JButton();
	JButton nextBtn = new JButton();
	JButton newLayoutBtn = new JButton();
	JButton replayBtn = new JButton();
	JButton showSolutionBtn = new JButton();
	JButton saveLayoutBtn = new JButton();
	
	ArrayList<ArrayList<JButton>> buttons;
	ArrayList<ArrayList<Integer>> solution;
	
	ArrayList<Long> across;
	ArrayList<Long> down;
	
	String acrossNum = "";
	String downNum = "";
	
	int m;
	int n;
	
	String mode = "";
	
	Game(JFrame frame1, int height, int width){
		frame = frame1;
		n = height;
		m = width;
		
		Button.draw(quitBtn, "QUIT", 0, 0, 100, 25);
		quitBtn.addActionListener(e -> {
			frame.getContentPane().removeAll();
			frame.repaint();
			new Menu().newGameMenu(frame);
		});
		
		Button.draw(settingsBtn, "SETTINGS", 400, 0, 100, 25);
		settingsBtn.addActionListener(e -> {
			Settings.settings(frame);
		});
		
		Button.draw(nextBtn, "NEXT", 200, 435, 100, 25);
		nextBtn.addActionListener(e -> {
			switch(mode) {
				case "endless":
					if(n>=12 && m>=12) {
						nextBtn.setVisible(false);
					}else {
						m++;
						n++;
						frame.getContentPane().removeAll();
						frame.repaint();
						new Game(frame, n++, m++).endless();
					}
					break;
			}
		});
				
		frame.add(quitBtn);
		frame.add(settingsBtn);
		frame.add(nextBtn);
		
		frame.add(newLayoutBtn);
		frame.add(replayBtn);
		frame.add(showSolutionBtn);
		frame.add(saveLayoutBtn);
		
		nextBtn.setVisible(false);
		
		newLayoutBtn.setVisible(false);
		replayBtn.setVisible(false);
		showSolutionBtn.setVisible(false);
		saveLayoutBtn.setVisible(false);
	}
	public void endless() {
		mode = "endless";
		playingField.setLayout(new GridLayout(n, m));
		playingField.setBounds((500-(m*25))/2, (500-(m*25))/2, m*25, m*25);	
		
		generateField(n, m);
		
		frame.add(playingField);
		frame.revalidate();
		frame.repaint();
	}
	public void newLayout() {
		playingField.setLayout(new GridLayout(n, m));
		playingField.setBounds((500-(m*25))/2, (500-(m*25))/2, m*25, m*25);
		
		generateField(n, m);
		
		frame.add(playingField);
		frame.revalidate();
		frame.repaint();
	}
	public void loadLayout() throws FileNotFoundException {
		Scanner scan = new Scanner(new File("Layout_5x5"));
		while (scan.hasNext()){
		    //buttons.add(scan.next());
		}
		scan.close();
		System.out.print(buttons);
	}
	public void generateField(int n, int m){
		buttons = new ArrayList<ArrayList<JButton>>();
		solution = new ArrayList<ArrayList<Integer>>();
		
		across = new ArrayList<Long>();
		down = new ArrayList<Long>();
		
		for(int i=0; i<m; i++) {
			ArrayList<JButton> tmp = new ArrayList<JButton>();
			for(int j=0; j<n; j++) {
				JButton btn = new JButton();
				btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
				btn.setFocusable(false);
				btn.setBounds(j*25, i*25, 25, 25);
				btn.setBorder(BorderFactory.createEtchedBorder());
				btn.setOpaque(true);
				btn.setBackground(Color.WHITE);
				tmp.add(btn);
			}
			buttons.add(tmp);
			
			int ran = generateRandomNumber(1, n-1);
			
			if(i>0) {
				if(buttons.get(i-1).get(ran).getBackground() != Color.BLACK) {
					if(ran>0) {
						if(buttons.get(i).get(ran-1).getBackground() != Color.BLACK && buttons.get(i).get(ran+1).getBackground() != Color.BLACK) {
							buttons.get(i).get(ran).setEnabled(false);
							buttons.get(i).get(ran).setBackground(Color.BLACK);
						}
					}else {
						if(buttons.get(i).get(ran+1).getBackground() != Color.BLACK) {
							buttons.get(i).get(ran).setEnabled(false);
							buttons.get(i).get(ran).setBackground(Color.BLACK);
						}
					}
				}
			}else {
				if(ran>0) {
					if(buttons.get(i).get(ran-1).getBackground() != Color.BLACK && buttons.get(i).get(ran+1).getBackground() != Color.BLACK) {
						buttons.get(i).get(ran).setEnabled(false);
						buttons.get(i).get(ran).setBackground(Color.BLACK);
					}
				}else {
					if(buttons.get(i).get(ran+1).getBackground() != Color.BLACK) {
						buttons.get(i).get(ran).setEnabled(false);
						buttons.get(i).get(ran).setBackground(Color.BLACK);
					}
				}
			}
		}

		for(int i=0; i<m; i++) {
			ArrayList<Integer> tmp = new ArrayList<Integer>();
			for(int j=0; j<n; j++) {
				playingField.add(buttons.get(i).get(j));
				if(buttons.get(i).get(j).getBackground() != Color.BLACK) {
					int ran = generateRandomNumber(1, n-1);
					tmp.add(ran);

					buttons.get(i).get(j).setText(Integer.toString(ran));
					buttons.get(i).get(j).setForeground(Color.WHITE);
				}
			}
			solution.add(tmp);
		}
		int[] mark = {0, 0};
			
		for(int i=0; i<m; i++) {
			int sp = shadedPosition(mark[0], n) == -1 ? n : shadedPosition(mark[0], n);

			if(((sp - 1) == 0) || ((sp+1) == n)) {
				downNum += Integer.toString(solution.get(i).get(((sp-1) == 0 ? 0 : n))+mark[0]);
				for(int j=i+1; j<m; j++) {
					if(buttons.get(j).get(((sp-1) == 0 ? 0 : n)+mark[0]).getBackground() != Color.BLACK) {
						downNum += Integer.toString(solution.get(j).get(((sp-1) == 0 ? 0 : n))+mark[0]);
						mark[1] = j;
					}
				}
				down.add(Long.parseLong(downNum));
				downNum = "";
				mark[0]++;
			}
		}
		for(int i=0; i<m; i++) {
			int sp = shadedPosition(i, n) == -1 ? n : shadedPosition(i, n);

			for(int j=mark[0]; j<sp; j++) {
				acrossNum += Integer.toString(solution.get(i).get(j));
			}
			if(acrossNum.length()>0) {
				across.add(Long.parseLong(acrossNum));
				acrossNum = "";
			}
		}
		
		int xPos = 0;
		int yPos = 30;
		
		
		for(int i=0; i<across.size(); i++) {
			JButton btn = new JButton();
			btn.setText(Long.toString(across.get(i)));
			
			if(xPos >= (500)) {
				xPos = 0;
				yPos = 60;
			}
			
			btn.setBounds(xPos, yPos, 100, 25);
			frame.add(btn);
			xPos += 100;
		}
		
		System.out.print(across);
		System.out.print(down);
		System.out.println();
	}
	private class Settings {
		public void settings(JFrame frame) {
			if(quitBtn.isVisible()) {
				playingField.setVisible(false);
				quitBtn.setVisible(false);
				
				settingsBtn.setBounds(150, 350, 200, 50);
				settingsBtn.setText("CANCEL");
				
				Button.draw(newLayoutBtn, "NEW LAYOUT", 150, 25, 200, 50);
				newLayoutBtn.addActionListener(event ->{
					frame.getContentPane().removeAll();
					frame.repaint();
					new Game(frame, m, n).newLayout();
				});
				
				Button.draw(replayBtn, "REPLAY", 150, 100, 200, 50);
				replayBtn.addActionListener(event ->{
					replay();
				});
				
				Button.draw(showSolutionBtn, "SHOW SOLUTION", 150, 175, 200, 50);
				showSolutionBtn.addActionListener(event ->{
					showSolutionBtn.setEnabled(false);
					settingsBtn.doClick();
					showSolution(n, m);
				});
				
				Button.draw(saveLayoutBtn, "SAVE LAYOUT", 150, 250, 200, 50);
				saveLayoutBtn.addActionListener(event ->{
					try {
						saveLayout();
					}catch(Exception e) {
						JOptionPane.showMessageDialog(frame, "Oops! Something went wrong, please try again.");
					}
				});
				
				newLayoutBtn.setVisible(true);
				replayBtn.setVisible(true);
				showSolutionBtn.setVisible(true);
				saveLayoutBtn.setVisible(true);
			}else {				
				newLayoutBtn.setVisible(false);
				replayBtn.setVisible(false);
				showSolutionBtn.setVisible(false);
				saveLayoutBtn.setVisible(false);
				
				playingField.setVisible(true);
				quitBtn.setVisible(true);
				
				settingsBtn.setBounds(400, 0, 100, 25);
				settingsBtn.setText("SETTINGS");
			}
		}
		public void showSolution(int n, int m) {
			for(int i=0; i<m; i++) {
				for(int j=0; j<n; j++) {
					buttons.get(i).get(j).setForeground(Color.BLACK);
				}
			}
			nextBtn.setVisible(true);
		}
		public void replay() {
			frame.getContentPane().remove(playingField);
			for(int i=0; i<m; i++) {
				for(int j=0; j<n; j++) {
					playingField.add(buttons.get(i).get(j));
					buttons.get(i).get(j).setForeground(Color.WHITE);
				}
			}
			frame.add(playingField);
			frame.revalidate();
			frame.repaint();
			settingsBtn.doClick();
			showSolutionBtn.setEnabled(true);
		}
		public void saveLayout() throws IOException{
			try {
				//FileOutputStream fout= new FileOutputStream("Layout_"+m+"x"+n);
				FileOutputStream fout= new FileOutputStream("Layout_"+m+"x"+n);
				ObjectOutputStream oos = new ObjectOutputStream(fout);
				oos.writeObject(buttons);
				fout.close();
				JOptionPane.showMessageDialog(frame, "Layout successfully saved!");
			}catch(IOException e) {
				JOptionPane.showMessageDialog(frame, "Oops! Something went wrong, please try again.");
			}
		}
	}
	public int generateRandomNumber(int min, int max) {
	    return (int) ((Math.random() * (max - min)) + min);
	}
	public int shadedPosition(int m, int n) {		
		for(int i=0; i<n; i++) {
			if(buttons.get(m).get(i).getBackground() == Color.BLACK) {
				return i;
			}
		}
		return -1;
	}
}

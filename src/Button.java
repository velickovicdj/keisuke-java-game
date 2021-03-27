import javax.swing.*;
import java.awt.*;

public class Button {
	public void draw(JButton btn, String txt, int x, int y, int width, int height) {
		btn.setBounds(x, y, width, height);
		btn.setText(txt);
		btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btn.setFocusable(false);
	}
}

import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class IconRotation implements ActionListener {
	JLabel label;
	ImageIcon icon;
	int angle = 90;

	public void actionPerformed(ActionEvent e) {
		String ac = e.getActionCommand();
		if (ac.equals("CCW"))
			angle = -90;
		else
			angle = 90; // degrees clockwise
		rotateIcon();
		label.setIcon(icon);
	}

	private void rotateIcon() {
		int w = icon.getIconWidth();
		int h = icon.getIconHeight();
		int type = BufferedImage.TYPE_INT_RGB; // other options, see api
		BufferedImage image = new BufferedImage(h, w, type);
		Graphics2D g2 = image.createGraphics();
		double x = (h - w) / 2.0;
		double y = (w - h) / 2.0;
		AffineTransform at = AffineTransform.getTranslateInstance(x, y);
		at.rotate(Math.toRadians(angle), w / 2.0, h / 2.0);
		g2.drawImage(icon.getImage(), at, label);
		g2.dispose();
		icon = new ImageIcon(image);
	}

	private JLabel getCenter() {
		URL url = getClass().getResource("dukeWaveRed.gif");
		icon = new ImageIcon(url);
		label = new JLabel(icon, JLabel.CENTER);
		return label;
	}

	private JPanel getUIPanel() {
		String[] ids = { "ccw", "cw" };
		JPanel panel = new JPanel();
		for (int j = 0; j < ids.length; j++) {
			JButton button = new JButton(ids[j]);
			button.setActionCommand(ids[j].toUpperCase());
			button.addActionListener(this);
			panel.add(button);
		}
		return panel;
	}

	public static void main(String[] args) {
		IconRotation test = new IconRotation();
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.getContentPane().add(test.getCenter());
		f.getContentPane().add(test.getUIPanel(), "Last");
		f.setSize(500, 500);
		f.setLocation(200, 100);
		f.setVisible(true);
	}
}
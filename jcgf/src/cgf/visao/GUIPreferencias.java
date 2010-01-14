package cgf.visao;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;

/**
 * @version 1.0
 * @created 16-jun-2007 05:23:46
 */
public class GUIPreferencias {
	// TODO o diretorio do layout de deck preferido.
	public static String deck = "";

	public static Color corBorda = Color.YELLOW;

	/**
	 * TODO Criar enum de tipoBorda com cor, tamanho alwaysVis, etc...
	 */
	public static int larguraBorda = 3;

	/**
	 * Distancia horizontal entre as entidades.
	 */
	public static int minDistX = 15;

	public static int minDistY = 10;

	public static int deckX = 70;

	public static int deckY = 100;

	public static BufferedImage getRotatedImage(BufferedImage src, int angle) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsConfiguration gc = ge.getDefaultScreenDevice().getDefaultConfiguration();

		if (src == null) {
			System.err.println("Image loading error");
			return null;
		} else {
			int transparency = src.getColorModel().getTransparency();
			BufferedImage dest = gc.createCompatibleImage(src.getWidth(), src.getHeight(), transparency);
			Graphics2D g2d = dest.createGraphics();

			// Save the original graphic
			AffineTransform origAT = g2d.getTransform();

			// Rotate the coordinate system of the dest. image around its center
			AffineTransform rot = new AffineTransform();
			rot.rotate(Math.toRadians(angle), src.getWidth() / 2, src.getHeight() / 2);
			// rot.scale(.5, .5);
			g2d.transform(rot);

			// Copy the image
			g2d.drawImage(src, 0, 0, null);
			g2d.setTransform(origAT);
			g2d.dispose();
			return dest;
		}
	}

	public static BufferedImage toBufferedImage(Image image) {
		if (image instanceof BufferedImage) {
			return (BufferedImage) image;
		}

		// This code ensures that all the pixels in the image are loaded
		image = new ImageIcon(image).getImage();

		// Determine if the image has transparent pixels; for this method's
		// implementation, see e661 Determining If an Image Has Transparent
		// Pixels
		boolean hasAlpha = false;// Imageh.hasAlpha(image);

		// Create a buffered image with a format that's compatible with the
		// screen
		BufferedImage bimage = null;
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		try {
			// Determine the type of transparency of the new buffered image
			int transparency = Transparency.OPAQUE;
			if (hasAlpha) {
				transparency = Transparency.BITMASK;
			}

			// Create the buffered image
			GraphicsDevice gs = ge.getDefaultScreenDevice();
			GraphicsConfiguration gc = gs.getDefaultConfiguration();
			bimage = gc.createCompatibleImage(image.getWidth(null), image.getHeight(null), transparency);
		} catch (HeadlessException e) {
			// The system does not have a screen
		}

		if (bimage == null) {
			// Create a buffered image using the default color model
			int type = BufferedImage.TYPE_INT_RGB;
			if (hasAlpha) {
				type = BufferedImage.TYPE_INT_ARGB;
			}
			bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
		}

		// Copy image to buffered image
		Graphics g = bimage.createGraphics();

		// Paint the image onto the buffered image
		g.drawImage(image, 0, 0, null);
		g.dispose();

		return bimage;
	}
}
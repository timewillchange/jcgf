package cgf.visao;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.io.Serializable;
import java.util.Hashtable;

import cgf.rmi.IPlayer;

public class CircularLayoutStrategy implements ILayoutStrategy, LayoutManager2, Serializable {
	protected Hashtable<String, Component> comptable;
	private boolean rightToLeft;

	public CircularLayoutStrategy() {
		// this.setSize(100, 100);
		// JPanel p = new JPanel(this);
		// JPanel f = new JPanel();
		// for (int i = 0; i < 5; i++) {
		// f = new JPanel();
		// f.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		// // f.setSize(5, 5);
		// f.setBackground(Color.BLUE);
		// p.add(f);
		// }
		// this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		// this.add(p);
		// this.setVisible(true);
	}

	public void addLayoutComponent(Component comp, Object constraints) {
		// Sempre que da um add em estadoJogo passando alguma zona
	}

	public float getLayoutAlignmentX(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	public float getLayoutAlignmentY(Container target) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void invalidateLayout(Container target) {
		// JSplitPane.add estadoJogo ou JSplit.validate
	}

	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	public void addLayoutComponent(String name, Component comp) {
		// TODO Auto-generated method stub

	}

	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
		int wTotal = parent.getWidth();
		int hTotal = parent.getHeight();
		// System.out.println("insets = " + getInsets());
		// System.out.println("parent insets = " + parent.getInsets());
		rightToLeft = !parent.getComponentOrientation().isLeftToRight();
		Component components[] = parent.getComponents();

		for (int i = 0; i < components.length; i++) {
			Component comp = components[i];

			if (!comp.isVisible()) {
				continue;
			}
			// int div = i/4;
			int posX = 0;
			int posY = 0;
			int mod = i % 4;
			int compMidWidth = (int) comp.getWidth() / 2;
			// Math.floor(components.length / 4);
			int midWidth = wTotal / 2;
			int midHeight = hTotal / 2;

			int w = wTotal - insets.left - insets.right;
			int h = hTotal - insets.top - insets.bottom;
			switch (mod) {
			case 0:
				posX = midWidth - compMidWidth - insets.left;
				posY = 0;
				break;
			case 1:
				posX = midWidth - compMidWidth - insets.left;
				posY = hTotal - (2 * comp.getHeight()) - insets.bottom;
				break;
			case 2:
				posX = 0;
				posY = (h + comp.getHeight()) / 2;// midHeight -
				// comp.getHeight() +
				// insets.bottom;
				System.out.println(posY);
				System.out.println(hTotal);
				break;
			case 3:
				posX = wTotal - comp.getWidth() - insets.left - insets.right;
				posY = midHeight - comp.getHeight() + insets.bottom;
				break;
			default:
				break;
			}

			// constraints = lookupConstraints(comp);
			// adjustForGravity(constraints, r);
			comp.setBounds(posX, posY, (int) comp.getPreferredSize().getWidth(), (int) comp.getPreferredSize()
					.getHeight());
			// comp.setBounds(w, h, 5, 5);
		}
	}

	public Dimension minimumLayoutSize(Container parent) {
		// GridBagLayoutInfo info = getLayoutInfo(parent, MINSIZE);
		// return getMinSize(parent, info);
		return parent.getMinimumSize();
	}

	public Dimension preferredLayoutSize(Container parent) {
		// GridBagLayoutInfo info = getLayoutInfo(parent, PREFERREDSIZE);
		// TODO or insets??
		Dimension info = parent.getSize();// new Dimension(300, 400);//
		// parent.getPreferredSize(); ==
		// loop
		// Dimension d = new Dimension();
		// int i, t;
		// Insets insets = parent.getInsets();
		//
		// t = 0;
		// for (i = 0; i < info.width; i++)
		// t += info.widthminWidth[i];
		// d.width = t + insets.left + insets.right;
		//
		// t = 0;
		// for (i = 0; i < info.height; i++)
		// t += info.minHeight[i];
		// d.height = t + insets.top + insets.bottom;
		//
		// return d;
		return info;
	}

	public void removeLayoutComponent(Component comp) {
		comptable.remove(comp);
	}

	@Override
	public void reorganiza(IPlayer modelo) {
		// TODO ninguem chama
	}
}
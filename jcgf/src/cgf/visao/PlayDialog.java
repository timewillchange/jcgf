package cgf.visao;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.KeyEvent;
import java.rmi.AccessException;
import java.rmi.RemoteException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;

import cgf.Constantes;
import cgf.Util;

public class PlayDialog extends JDialog {
	private JPanel jContentPane;

	private JComboBox jogo;

	private JSpinner minPlayer;

	private JSpinner maxPlayer;

	private ButtonGroup bg; // @jve:decl-index=0:

	private JRadioButton nj;

	private JRadioButton coj;

	private JRadioButton caj;

	private JTextField ip;

	private JButton play;

	private JButton close;

	private JTextField nome = null;

	private JTextField fileSave = null;

	private JButton load = null;

	private JLabel lmin = null;

	private JLabel lmax = null;

	private JLabel lname = null;

	public PlayDialog(Visao visao) {
		super(visao);
		initialize();
		// TODO melhorar isso
		try {
			if (Util.getRegistry("127.0.0.1").list().length != 0) {
				coj.setSelected(true);
			}
		} catch (AccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		play.requestFocusInWindow();
	}

	private void initialize() {
		this.setSize(250, 220);
		this.setTitle("JCF Game Loader");
		this.setContentPane(getJContentPane());
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	private JPanel getJContentPane() {
		if (jContentPane == null) {
			GridBagConstraints gridBagConstraints3 = new GridBagConstraints();
			gridBagConstraints3.gridx = 1;
			gridBagConstraints3.gridy = 0;
			lname = new JLabel();
			lname.setText("Jogador");
			GridBagConstraints gridBagConstraints2 = new GridBagConstraints();
			gridBagConstraints2.gridx = 2;
			gridBagConstraints2.gridy = 0;
			lmax = new JLabel();
			lmax.setFont(new Font("Dialog", Font.PLAIN, 10));
			lmax.setText("Max Players");
			GridBagConstraints gridBagConstraints17 = new GridBagConstraints();
			gridBagConstraints17.gridx = 1;
			gridBagConstraints17.weightx = 0.5;
			gridBagConstraints17.gridy = 0;
			lmin = new JLabel();
			lmin.setText("Min Players");
			lmin.setFont(new Font("Dialog", Font.PLAIN, 10));
			GridBagConstraints gridBagConstraints16 = new GridBagConstraints();
			gridBagConstraints16.gridx = 2;
			gridBagConstraints16.anchor = GridBagConstraints.EAST;
			gridBagConstraints16.gridy = 2;
			GridBagConstraints gridBagConstraints15 = new GridBagConstraints();
			gridBagConstraints15.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints15.gridy = 2;
			gridBagConstraints15.weightx = 1.0;
			gridBagConstraints15.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints15.gridwidth = 2;
			gridBagConstraints15.anchor = GridBagConstraints.WEST;
			gridBagConstraints15.gridx = 1;
			GridBagConstraints gridBagConstraints14 = new GridBagConstraints();
			gridBagConstraints14.fill = GridBagConstraints.BOTH;
			gridBagConstraints14.gridy = 0;
			gridBagConstraints14.weightx = 1.0;
			gridBagConstraints14.gridx = 2;
			GridBagConstraints gridBagConstraints13 = new GridBagConstraints();
			gridBagConstraints13.fill = GridBagConstraints.BOTH;
			gridBagConstraints13.gridx = 1;
			gridBagConstraints13.gridy = 3;
			gridBagConstraints13.weightx = 1.0;
			gridBagConstraints13.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints13.gridwidth = 2;
			GridBagConstraints gridBagConstraints12 = new GridBagConstraints();
			gridBagConstraints12.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints12.gridy = 1;
			gridBagConstraints12.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints12.weightx = 0.5;
			gridBagConstraints12.gridx = 2;
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints11.weightx = 0.5;
			gridBagConstraints11.gridx = 1;
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.gridx = 0;
			gridBagConstraints10.anchor = GridBagConstraints.WEST;
			gridBagConstraints10.gridy = 3;
			GridBagConstraints gridBagConstraints9 = new GridBagConstraints();
			gridBagConstraints9.gridx = 0;
			gridBagConstraints9.anchor = GridBagConstraints.WEST;
			gridBagConstraints9.gridy = 2;
			GridBagConstraints gridBagConstraints81 = new GridBagConstraints();
			gridBagConstraints81.gridx = 0;
			gridBagConstraints81.anchor = GridBagConstraints.WEST;
			gridBagConstraints81.gridy = 1;
			GridBagConstraints gridBagConstraints71 = new GridBagConstraints();
			gridBagConstraints71.gridx = 0;
			gridBagConstraints71.fill = GridBagConstraints.BOTH;
			gridBagConstraints71.gridwidth = 3;
			gridBagConstraints71.gridy = 1;
			GridBagConstraints gridBagConstraints5 = new GridBagConstraints();
			gridBagConstraints5.gridx = 0;
			gridBagConstraints5.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints5.gridy = 1;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.VERTICAL;
			gridBagConstraints1.gridy = 0;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.anchor = GridBagConstraints.WEST;
			gridBagConstraints1.insets = new Insets(0, 5, 0, 0);
			gridBagConstraints1.gridx = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.gridwidth = 3;
			gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
			gridBagConstraints.gridy = 2;
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridBagLayout());
			JPanel tipos = new JPanel(new GridBagLayout());
			tipos.setBorder(BorderFactory.createTitledBorder("Tipo de Jogo"));
			// for (AbstractButton b : Collections.list(getBg().getElements()))
			getBg();
			tipos.add(getNj(), gridBagConstraints81);
			tipos.add(getCaj(), gridBagConstraints9);
			tipos.add(getCoj(), gridBagConstraints10);
			tipos.add(getMinPlayer(), gridBagConstraints11);
			tipos.add(getMaxPlayer(), gridBagConstraints12);
			tipos.add(getIp(), gridBagConstraints13);
			tipos.add(getFileSave(), gridBagConstraints15);
			tipos.add(getLoad(), gridBagConstraints16);
			tipos.add(lmin, gridBagConstraints17);
			tipos.add(lmax, gridBagConstraints2);
			// tipos.add(b);
			// }
			JPanel botoes = new JPanel();
			botoes.add(getPlay());
			botoes.add(getClose());
			jContentPane.add(botoes, gridBagConstraints);
			jContentPane.add(getJogo(), gridBagConstraints1);
			jContentPane.add(tipos, gridBagConstraints71);
			jContentPane.add(getNome(), gridBagConstraints14);
			jContentPane.add(lname, gridBagConstraints3);
		}
		return jContentPane;
	}

	/**
	 * This method initializes nome
	 * 
	 * @return javax.swing.JTextField
	 */
	public JTextField getNome() {
		if (nome == null) {
			nome = new JTextField();
			// TODO melhorar isso
			try {
				nome.setText(Constantes.REMOTE_PLAYER + Util.getRegistry("127.0.0.1").list().length);
			} catch (AccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return nome;
	}

	/**
	 * This method initializes fileSave
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getFileSave() {
		if (fileSave == null) {
			fileSave = new JTextField();
			fileSave.setPreferredSize(new Dimension(120, 20));
			fileSave.setEnabled(false);
		}
		return fileSave;
	}

	/**
	 * This method initializes load
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getLoad() {
		if (load == null) {
			load = new JButton();
			load.setText("...");
			load.setPreferredSize(new Dimension(20, 20));
			load.setEnabled(false);
		}
		return load;
	}

	public ButtonGroup getBg() {
		if (bg == null) {
			bg = new ButtonGroup();
			bg.add(getNj());
			bg.add(getCaj());
			bg.add(getCoj());
		}
		return bg;
	}

	private JRadioButton getNj() {
		if (nj == null) {
			nj = new JRadioButton("Novo");
			nj.setSelected(true);
			nj.setActionCommand("novo");
			nj.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					getMinPlayer().setEnabled(e.getStateChange() == ItemEvent.SELECTED);
					getMaxPlayer().setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				}
			});
		}
		return nj;
	}

	private JRadioButton getCaj() {
		if (caj == null) {
			caj = new JRadioButton();
			caj.setText("Salvo");
			caj.setActionCommand("salvo");
			caj.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					getFileSave().setEnabled(e.getStateChange() == ItemEvent.SELECTED);
					getLoad().setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				}
			});
		}
		return caj;
	}

	private JRadioButton getCoj() {
		if (coj == null) {
			coj = new JRadioButton();
			coj.setText("Remoto");
			coj.setActionCommand("remoto");
			coj.addItemListener(new java.awt.event.ItemListener() {
				public void itemStateChanged(java.awt.event.ItemEvent e) {
					getIp().setEnabled(e.getStateChange() == ItemEvent.SELECTED);
				}
			});
		}
		return coj;
	}

	private JButton getClose() {
		if (close == null) {
			close = new JButton("Fechar");
			close.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					PlayDialog.this.dispose();
				}
			});
		}
		return close;
	}

	public JTextField getIp() {
		if (ip == null) {
			ip = new JTextField("127.0.0.1");
			ip.setPreferredSize(new Dimension(60, 20));
			ip.setEnabled(false);
		}
		return ip;
	}

	private JComboBox getJogo() {
		if (jogo == null) {
			jogo = new JComboBox(new String[] { "escova" });
		}
		return jogo;
	}

	private JSpinner getMaxPlayer() {
		if (maxPlayer == null) {
			maxPlayer = new JSpinner();
			maxPlayer.setPreferredSize(new Dimension(30, 20));
			maxPlayer.setValue(4);
		}
		return maxPlayer;
	}

	public JSpinner getMinPlayer() {
		if (minPlayer == null) {
			minPlayer = new JSpinner();
			minPlayer.setPreferredSize(new Dimension(30, 20));
			minPlayer.setValue(2);
		}
		return minPlayer;
	}

	public JButton getPlay() {
		if (play == null) {
			play = new JButton("Jogar");
			play.setMnemonic(KeyEvent.VK_J);
		}
		return play;
	}
}
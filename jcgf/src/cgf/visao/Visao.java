package cgf.visao;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import cgf.estado.EstadoJogo;

public class Visao extends JFrame {
	private EstadoJogo jContentPane = null;

	private PlayDialog playDialog;

	private JSplitPane splitMsg = null;

	private JPanel painelBotoes = null;

	private JSplitPane splitBotoes = null;

	private JPanel panelMsg = null;

	private JButton buttonSendMsg = null;

	private JTextField tfMsg = null;

	private JButton buttonDraw = null;

	private JButton buttonVez = null;

	private JTextArea taMsgs = null;

	/**
	 * This is the default constructor
	 */
	public Visao() {
		super();
		initialize();
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		this.setSize(800, 600);
		this.setContentPane(getJSplitPane());
		this.setTitle("JFrame");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
		playDialog = new PlayDialog(this);
	}

	public EstadoJogo getEstadoJogo() {
		return (EstadoJogo) splitBotoes.getRightComponent();
	}

	/**
	 * Monta a visao de acordo com o estado recebido e as preferencias visuais.
	 * 
	 * @param estado
	 */
	public void setEstadoJogo(EstadoJogo estado) {
		/*
		 * try { estado = (EstadoJogo) estado.clone(); } catch
		 * (CloneNotSupportedException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		splitBotoes.setRightComponent(estado);
	}

	public PlayDialog getPlayDialog() {
		return playDialog;
	}

	/**
	 * This method initializes jSplitPane
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getJSplitPane() {
		if (splitMsg == null) {
			splitMsg = new JSplitPane();
			splitMsg.setSize(this.getSize());
			splitMsg.setOrientation(JSplitPane.VERTICAL_SPLIT);
			splitMsg.setDividerLocation(0.7);
			splitMsg.setOneTouchExpandable(true);
			splitMsg.setBottomComponent(getPanelMsg());
			splitMsg.setTopComponent(getSplitBotoes());
		}
		return splitMsg;
	}

	/**
	 * This method initializes painelBotoes
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPainelBotoes() {
		if (painelBotoes == null) {
			painelBotoes = new JPanel();
			// painelBotoes.setLayout(new GridBagLayout());
			painelBotoes.setPreferredSize(new Dimension(120, 500));
			painelBotoes.setMaximumSize(new Dimension(120, 500));
			painelBotoes.setSize(new Dimension(120, 500));
			painelBotoes.add(getButtonDraw()/* , gridBagConstraints2 */);
			painelBotoes.add(getButtonVez()/* , gridBagConstraints3 */);
		}
		return painelBotoes;
	}

	/**
	 * This method initializes splitBotoes
	 * 
	 * @return javax.swing.JSplitPane
	 */
	private JSplitPane getSplitBotoes() {
		if (splitBotoes == null) {
			splitBotoes = new JSplitPane();
			splitBotoes.setDividerLocation(120);
			splitBotoes.setOneTouchExpandable(true);
			splitBotoes.setLeftComponent(getPainelBotoes());
		}
		return splitBotoes;
	}

	/**
	 * This method initializes panelMsg
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getPanelMsg() {
		if (panelMsg == null) {
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = GridBagConstraints.BOTH;
			gridBagConstraints11.gridy = 0;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.weighty = 1.0;
			gridBagConstraints11.gridwidth = 2;
			gridBagConstraints11.gridx = 0;
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.fill = GridBagConstraints.BOTH;
			gridBagConstraints1.gridy = 1;
			gridBagConstraints1.weightx = 1.0;
			gridBagConstraints1.insets = new Insets(10, 10, 10, 10);
			gridBagConstraints1.gridx = 1;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 0;
			gridBagConstraints.insets = new Insets(10, 10, 10, 0);
			gridBagConstraints.gridy = 1;
			panelMsg = new JPanel();
			panelMsg.setLayout(new GridBagLayout());
			panelMsg.add(getButtonSendMsg(), gridBagConstraints);
			panelMsg.add(getTfMsg(), gridBagConstraints1);
			panelMsg.add(getTaMsgs(), gridBagConstraints11);
		}
		return panelMsg;
	}

	/**
	 * This method initializes buttonSendMsg
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getButtonSendMsg() {
		if (buttonSendMsg == null) {
			buttonSendMsg = new JButton();
			buttonSendMsg.setText("Enviar Msg");
			buttonSendMsg.setPreferredSize(new Dimension(100, 20));
		}
		return buttonSendMsg;
	}

	/**
	 * This method initializes tfMsg
	 * 
	 * @return javax.swing.JTextField
	 */
	private JTextField getTfMsg() {
		if (tfMsg == null) {
			tfMsg = new JTextField();
		}
		return tfMsg;
	}

	/**
	 * This method initializes buttonQuit
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonDraw() {
		if (buttonDraw == null) {
			buttonDraw = new JButton();
			buttonDraw.setPreferredSize(new Dimension(100, 20));
			buttonDraw.setText("Comprar");
		}
		return buttonDraw;
	}

	/**
	 * This method initializes buttonVez
	 * 
	 * @return javax.swing.JButton
	 */
	public JButton getButtonVez() {
		if (buttonVez == null) {
			buttonVez = new JButton();
			buttonVez.setPreferredSize(new Dimension(100, 20));
			buttonVez.setText("Passar Vez");
		}
		return buttonVez;
	}

	/**
	 * This method initializes taMsgs
	 * 
	 * @return javax.swing.JTextArea
	 */
	private JTextArea getTaMsgs() {
		if (taMsgs == null) {
			taMsgs = new JTextArea();
		}
		return taMsgs;
	}
}

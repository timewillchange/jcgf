package modelo;

import java.util.List;

import cgf.estado.Zona;

public/* abstract */class AbsCommand {
	public static enum COMMAND {
		SELECT, UNSELECT, FLIP, MOVE, UNDO, DRAW, /*VALIDATE,*/ PASS;
	}

	private COMMAND command;
	private List<Zona> origens;
	private Zona destino;
	private StringBuffer msg;
	private String nomePlayer;
	public String getNomePlayer() {
		return nomePlayer;
	}

	public void setNomePlayer(String player) {
		this.nomePlayer = player;
	}

	private int qnt;

	public int getQnt() {
		return qnt;
	}

	public void setQnt(int qnt) {
		this.qnt = qnt;
	}

	public StringBuffer getMsg() {
		return msg;
	}

	public void appendMsg(String msg) {
		this.msg.append(msg);
	}

	public AbsCommand(COMMAND command, List<Zona> origens, Zona destino) {
		super();
		this.command = command;
		this.origens = origens;
		this.destino = destino;
		msg = new StringBuffer();
	}

	public AbsCommand(COMMAND command) {
		this.command = command;
	}

	public AbsCommand(COMMAND command, String nomePlayer, int i) {
		this.command = command;
		this.nomePlayer = nomePlayer;
		this.qnt = i;
	}

	public COMMAND getCommand() {
		return command;
	}

	public void setCommand(COMMAND command) {
		this.command = command;
	}

	public List<Zona> getOrigens() {
		return origens;
	}

	public void setOrigens(List<Zona> origens) {
		this.origens = origens;
	}

	public Zona getDestino() {
		return destino;
	}

	public void setDestino(Zona destino) {
		this.destino = destino;
	}

	@Override
	public String toString() {
		return command.toString() + origens + destino + msg;
	}
}
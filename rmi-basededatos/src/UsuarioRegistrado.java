

import java.io.Serializable;
/*
 * Copyright (C) IDB Mobile Technology S.L. - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */
import java.util.ArrayList;
import java.util.List;

import Common.Trino;

/**
 *
 * Clase que almacena la informacion referente a los usuarios registrados
 *
 * @author rafar
 *
 */
/**
 * @author rafar
 *
 */
public class UsuarioRegistrado implements Serializable {

	private static final long serialVersionUID = 1L;
	private String nombre;
	private String nick;
	private String password;

	/**
	 * La lista de los usuarios que siguen a este usuario
	 */
	private List<UsuarioRegistrado> seguidores;

	/**
	 * La lisat de los usuarios a seguir por este usuario
	 */
	private List<UsuarioRegistrado> siguiendo;

	private List<Trino> trinos;

	/**
	 * Boolean para indicar si el usuario esta actualmente logeado o no
	 */
	private boolean online = false;

	/**
	 * @param nombre
	 * @param nick
	 * @param password
	 * @param seguidores
	 * @param trinos
	 */
	public UsuarioRegistrado(String nombre, String nick, String password) {
		this.nombre = nombre;
		this.nick = nick;
		this.password = password;
		this.seguidores = new ArrayList<UsuarioRegistrado>();
		this.siguiendo = new ArrayList<UsuarioRegistrado>();
		this.trinos = new ArrayList<Trino>();
		online = false;
	}

	/**
	 * @return the nombre
	 */
	public String getNombre() {
		return nombre;
	}

	/**
	 * @param nombre the nombre to set
	 */
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	/**
	 * @return the nick
	 */
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	public void setNick(String nick) {
		this.nick = nick;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the seguidores
	 */
	public List<UsuarioRegistrado> getSeguidores() {
		return seguidores;
	}

	/**
	 * @param seguidores the seguidores to set
	 */
	public void setSeguidores(List<UsuarioRegistrado> seguidores) {
		this.seguidores = seguidores;
	}

	/**
	 * @return the trinos
	 */
	public List<Trino> getTrinos() {
		return trinos;
	}

	/**
	 * @param trinos the trinos to set
	 */
	public void setTrinos(List<Trino> trinos) {
		this.trinos = trinos;
	}

	/**
	 * @return the siguiendo
	 */
	public List<UsuarioRegistrado> getSiguiendo() {
		return siguiendo;
	}

	/**
	 * @param siguiendo the siguiendo to set
	 */
	public void setSiguiendo(List<UsuarioRegistrado> siguiendo) {
		this.siguiendo = siguiendo;
	}

	/**
	 * @return the online
	 */
	public boolean isOnline() {
		return online;
	}

	/**
	 * @param online the online to set
	 */
	public void setOnline(boolean online) {
		this.online = online;
	}

	@Override
	public String toString() {
		final StringBuilder builder = new StringBuilder();
		builder.append("Usuario [nombre=");
		builder.append(nombre);
		builder.append(", nick=");
		builder.append(nick);
		builder.append(", password=");
		builder.append(password);
		builder.append(", seguidores=");
		builder.append(seguidores);
		builder.append(", trinos=");
		builder.append(trinos);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nick == null) ? 0 : nick.hashCode());
		result = prime * result + ((nombre == null) ? 0 : nombre.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final UsuarioRegistrado other = (UsuarioRegistrado) obj;
		if (nick == null) {
			if (other.nick != null) {
				return false;
			}
		} else if (!nick.equals(other.nick)) {
			return false;
		}
		return true;
	}

}

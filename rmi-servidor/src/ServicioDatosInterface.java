
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Common.Trino;

public interface ServicioDatosInterface extends Remote {

	/**
	 * Registra un usuario en la base de datos
	 *
	 * @param nombre
	 * @param nick
	 * @param password
	 * @return true si registro con exito, false en otro caso
	 * @throws RemoteException
	 */
	public boolean registrarUsuario(String nombre, String nick, String password) throws RemoteException;

	/**
	 *
	 * Registra que un usuario se ha logeado en el sistema
	 *
	 * @param nick
	 * @param password
	 * @return true si el login ha sido correcto, false en otro caso
	 * @throws RemoteException
	 */
	public boolean autenticarUsuario(String nick, String password) throws RemoteException;

	/**
	 *
	 * Registra que un usuario ha salido del sistema
	 *
	 * @param nick
	 * @return true si ha salido con exito, false en otro caso
	 * @throws RemoteException
	 */
	public boolean logout(String nick) throws RemoteException;

	/**
	 * Devuelve los usuarios registrados en el sistema
	 *
	 * @return la lista con los nicks de los usuarios
	 * @throws RemoteException
	 */
	public List<String> listarUsuarios() throws RemoteException;

	/**
	 *
	 * Devuelve la lista de los usuarios actualmente logeados en el sistema
	 *
	 * @return la lista con los nicks de los usuarios logeados
	 * @throws RemoteException
	 */
	public List<String> listarUsuariosLogeados() throws RemoteException;

	/**
	 *
	 * Añade un seguidor a un usuario
	 *
	 * @param nickSeguidor nick del usuario que quiere seguir a alguien
	 * @param nickSeguido  nick del usuario a seguir
	 * @return true si la operacion se ha realizado con exito, false en otro caso
	 * @throws RemoteException
	 */
	public boolean seguirA(String nickSeguidor, String nickSeguido) throws RemoteException;

	/**
	 * Deja de seguir a un usuario
	 *
	 * @param nickSeguidor nick del usuario que quiere dejar de seguir a alguien
	 * @param nickSeguido  nick del usuario a quien se quiere dejar de seguir
	 * @return true si la operacion se ha realizado con exito, false en otro caso
	 * @throws RemoteException
	 */
	public boolean dejarSeguirA(String nickSeguidor, String nickSeguido) throws RemoteException;

	/**
	 *
	 * Devuelve la lista de trinos en el sistema
	 *
	 * @return la lista con los trinos del sistema
	 * @throws RemoteException
	 */
	public List<Trino> listarTrinos() throws RemoteException;

	/**
	 * Registra la publicacion de un trino
	 *
	 * @param trino
	 * @return el mensaje contenido en el trino
	 * @throws RemoteException
	 */
	public String enviarTrino(Trino trino) throws RemoteException;

	/**
	 *
	 * Devuelve la lista de nicks de los seguidores actualmente logeados de un
	 * usuario
	 *
	 * @param nick el nick del usuario de quien se quieren obtener los seguidores
	 * @return la lista de nicks de los seguidores actualmente logeados de un
	 *         usuario
	 * @throws RemoteException
	 */
	public List<String> listarSeguidoresLogeados(String nick) throws RemoteException;

	/**
	 * Devuelve la lista de nicks de los seguidores actualmente NO logeados de un
	 * usuario
	 *
	 * @param nick el nick del usuario de quien se quieren obtener los seguidores
	 * @return la lista de nicks de los seguidores actualmente NO logeados de un
	 *         usuario
	 * @throws RemoteException
	 */
	public List<String> listarSeguidoresNoLogeados(String nick) throws RemoteException;

}
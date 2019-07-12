
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import Common.Trino;

public interface ServicioGestorInterface extends Remote {

	/**
	 * Devuelve la lista de los usuarios del sistema
	 *
	 * @return la lista de los usuarios del sistema
	 * @throws RemoteException
	 */
	public List<String> listarUsuarios() throws RemoteException;

	/**
	 * Devuelve la lista de los usuarios actualmente ogeados en el sistema
	 *
	 * @return la lista de los usuarios actualmente ogeados en el sistema
	 * @throws RemoteException
	 */
	public List<String> listarUsuariosLogeados() throws RemoteException;

	/**
	 * Publica un trino
	 *
	 * @param trino
	 * @return el mensaje del trino
	 * @throws RemoteException
	 */
	public String enviarTrino(Trino trino) throws RemoteException;

	/**
	 * Hace que el usuario nickSeguidor siga al usuario nickSeguido
	 *
	 * @param nickSeguidor
	 * @param nickSeguido
	 * @return true si la operacion se ha realizado con exito, false en caso
	 *         contrario
	 * @throws RemoteException
	 */
	public boolean seguirA(String nickSeguidor, String nickSeguido) throws RemoteException;

	/**
	 * Hace que el usuario nickSeguidor deje de seguir al usuario nickSeguido
	 *
	 * @param nickSeguidor
	 * @param nickSeguido
	 * @return true si la operacion se ha realizado con exito, false en caso
	 *         contrario
	 * @throws RemoteException
	 */
	public boolean dejarSeguirA(String nickSeguidor, String nickSeguido) throws RemoteException;

	/**
	 * Anula el registro de un cliente RMI para los callbacks
	 *
	 * @param callbackClientObject
	 * @throws RemoteException
	 */
	void anularRegistroParaCallback(CallbackUsuarioInterface callbackClientObject) throws RemoteException;

	/**
	 * Registra un cliente RMI para los callbacks
	 *
	 * @param callbackClientObject
	 * @throws RemoteException
	 */
	void registrarParaCallback(CallbackUsuarioInterface callbackClientObject) throws RemoteException;

	/**
	 *
	 * Elimina los trinos no recibidos por los usuarios no logeados en el sistema.
	 *
	 * @param nick
	 *
	 * @throws RemoteException
	 */
	public void eliminarTrinosNoRecibidos(String nick) throws RemoteException;

}

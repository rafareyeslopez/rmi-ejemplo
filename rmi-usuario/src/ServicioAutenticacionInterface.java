
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServicioAutenticacionInterface extends Remote {

	/**
	 * Intenta autenticar un usuario en el sistema
	 *
	 * @param nombre
	 * @param nick
	 * @param password
	 * @param callbackUsuario
	 * @return true si la operacion se ha realizado con exito, false en caso
	 *         contrario
	 * @throws RemoteException
	 */
	public boolean autenticarUsuario(String nick, String password, CallbackUsuarioInterface callbackUsuario)
			throws RemoteException;

	/**
	 * Registra un nuevo usuario en el sistema
	 *
	 * @param nombre
	 * @param nick
	 * @param password
	 * @return true si la operacion se ha realizado con exito, false en caso
	 *         contrario
	 * @throws RemoteException
	 */
	public boolean altaUsuario(String nombre, String nick, String password) throws RemoteException;

	/**
	 * Deslogea un usuario del sistema
	 *
	 * @param nick
	 * @return true si la operacion se ha realizado con exito, false en caso
	 *         contrario
	 * @throws RemoteException
	 */
	public boolean salir(String nick) throws RemoteException;

}
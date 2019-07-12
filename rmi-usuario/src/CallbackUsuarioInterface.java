
import java.rmi.Remote;
import java.rmi.RemoteException;

import Common.Trino;

public interface CallbackUsuarioInterface extends Remote {

	/**
	 * El usuario recibe un trino publicado por un usuario al que sigue
	 *
	 * @param trino
	 * @throws RemoteException
	 */
	public void recibirTrino(Trino trino) throws RemoteException;

	/**
	 * Obtiene el nick del usuario
	 * 
	 * @return el nick
	 * @throws RemoteException
	 */
	public String getNick() throws RemoteException;

	/**
	 * Establece el nick del usuario
	 * 
	 * @param nick
	 * @throws RemoteException
	 */
	public void setNick(String nick) throws RemoteException;

}

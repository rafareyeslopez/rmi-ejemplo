
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import Common.Trino;

public class CallbackUsuarioImpl extends UnicastRemoteObject implements CallbackUsuarioInterface, Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * El nick del usuario del cliente RMI
	 */
	private String nick;

	protected CallbackUsuarioImpl() throws RemoteException {
		super();

	}

	/**
	 * @return the nick
	 */
	@Override
	public String getNick() {
		return nick;
	}

	/**
	 * @param nick the nick to set
	 */
	@Override
	public void setNick(String nick) {
		this.nick = nick;
	}

	@Override
	public void recibirTrino(Trino trino) throws RemoteException {

		System.out.println(trino);

	}

}

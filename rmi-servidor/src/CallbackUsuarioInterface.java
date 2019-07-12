
import java.rmi.Remote;
import java.rmi.RemoteException;

import Common.Trino;

public interface CallbackUsuarioInterface extends Remote {

	public void recibirTrino(Trino trino) throws RemoteException;

	public String getNick() throws RemoteException;

	public void setNick(String nick) throws RemoteException;

}

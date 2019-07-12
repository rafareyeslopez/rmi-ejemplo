
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Common.Trino;

public class ServicioGestorImpl extends UnicastRemoteObject implements ServicioGestorInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private static final String URL_CALLBACK = "rmi://localhost:9002/callback/usuario";

	private ServicioDatosInterface servicioDatos;
	private CallbackUsuarioInterface callbackUsuario;
	private ClientesContectados clientesContectados;

	public ServicioGestorImpl(ServicioDatosInterface servicioDatos, ClientesContectados clientesContectados)
			throws MalformedURLException, RemoteException, NotBoundException {
		super();

		this.servicioDatos = servicioDatos;
		this.clientesContectados = clientesContectados;

		System.out.println("Servicio Gestor Conectado a Base de datos!");
	}

	@Override
	public List<String> listarUsuarios() throws RemoteException {
		return servicioDatos.listarUsuarios();
	}

	@Override
	public List<String> listarUsuariosLogeados() throws RemoteException {
		return servicioDatos.listarUsuariosLogeados();

	}

	@Override
	public String enviarTrino(Trino trino) throws RemoteException {

		// obtenemos la lista de los seguidores del usuario que ha publicado el trino
		// que esten actualmente logeados
		List<String> listarSeguidoresLogeados = servicioDatos.listarSeguidoresLogeados(trino.ObtenerNickPropietario());

		for (int i = 0; i < clientesContectados.getClientesContectadosOnline().size(); i++) {

			// Obtenemos la lista de los clientes RMI actualmente conectados
			CallbackUsuarioInterface nextClient = (CallbackUsuarioInterface) clientesContectados
					.getClientesContectadosOnline().elementAt(i);

			// Comprobamos si para el cliente RMI conectado, su usuario logeado es seguidor
			// del publicador del trino. Si es asi hacemos la llamada callback para que
			// reciba el trino
			if (listarSeguidoresLogeados.contains(nextClient.getNick())) {
				nextClient.recibirTrino(trino);
			}
		}

		// Obtenemos la lista de los seguidores que no estan actualmente logeados
		List<String> listarSeguidoresNoLogeados = servicioDatos
				.listarSeguidoresNoLogeados(trino.ObtenerNickPropietario());

		// Para cada uno de ellos registramos el trino como pendiente de ser recibido
		for (String seguidorNoLogeado : listarSeguidoresNoLogeados) {
			clientesContectados.addTrinoPendiente(seguidorNoLogeado, trino);
		}

		return servicioDatos.enviarTrino(trino);

	} // function

	@Override
	public boolean seguirA(String nickSeguidor, String nickSeguido) throws RemoteException {
		return servicioDatos.seguirA(nickSeguidor, nickSeguido);
	}

	@Override
	public boolean dejarSeguirA(String nickSeguidor, String nickSeguido) throws RemoteException {
		return servicioDatos.dejarSeguirA(nickSeguidor, nickSeguido);
	}

	@Override
	public synchronized void anularRegistroParaCallback(CallbackUsuarioInterface callbackClientObject)
			throws RemoteException {
		if (clientesContectados.eliminarClienteConectado(callbackClientObject)) {
			System.out.println("Cliente RMI eliminado");
		} else {
			System.out.println("El cliente RMI no estaba registrado");
		}
	}

	@Override
	public synchronized void registrarParaCallback(CallbackUsuarioInterface callbackClientObject)
			throws RemoteException {
		clientesContectados.addClienteConectado(callbackClientObject);
	}

	@Override
	public void eliminarTrinosNoRecibidos(String nick) throws RemoteException {
		// Obtenemos los usuarios no logeados en el sistema
		List<String> listarSeguidoresNoLogeados = servicioDatos.listarSeguidoresNoLogeados(nick);

		// Para cada uno de ellos eliminamos sus trinos pendientes
		for (String usuarioNoLogeado : listarSeguidoresNoLogeados) {

			clientesContectados.eliminarTrinosPendientes(usuarioNoLogeado);
		}

	}
}


import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

import Common.Trino;

public class ServicioAutenticacionImpl extends UnicastRemoteObject implements ServicioAutenticacionInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private ServicioDatosInterface servicioDatos;
	private ClientesContectados clientesContectados;

	public ServicioAutenticacionImpl(ServicioDatosInterface servicioDatos, ClientesContectados clientesContectados)
			throws MalformedURLException, RemoteException, NotBoundException {
		super();

		this.servicioDatos = servicioDatos;
		this.clientesContectados = clientesContectados;
		System.out.println("Servicio Autenticacion Conectado a Base de datos!");

	}

	@Override
	public boolean autenticarUsuario(String nick, String password, CallbackUsuarioInterface callbackUsuarioInterface)
			throws RemoteException {
		// Invocamos al servicio de datos para autenticar al usuario
		boolean autenticacion = servicioDatos.autenticarUsuario(nick, password);
		// Si la autenticacion ha sido con exito registramos que dicho cliente contiene
		// un usuario logeado
		if (autenticacion) {
			clientesContectados.addClienteConectadoOnline(callbackUsuarioInterface);
		}

		// Obetenemos los trinos pendientes de ser recibido por dicho usuario (si los
		// hubiera)
		List<Trino> obtenerTrinosPendientes = clientesContectados.obtenerTrinosPendientes(nick);
		if (obtenerTrinosPendientes != null) {
			// Para cada uno de ellos le hacemos la llamada por callback para que reciba el
			// trino
			for (Trino trino : obtenerTrinosPendientes) {
				callbackUsuarioInterface.recibirTrino(trino);
			}
			// Limpiamos la lista de trinos pendientes de ese usuario
			clientesContectados.eliminarTrinosPendientes(nick);
		}

		return autenticacion;
	}

	@Override
	public boolean altaUsuario(String nombre, String nick, String password) throws RemoteException {

		return servicioDatos.registrarUsuario(nombre, nick, password);

	}

	@Override
	public boolean salir(String nick) throws RemoteException {

		boolean autenticacion = servicioDatos.logout(nick);
		return autenticacion;
	}

}

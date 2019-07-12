import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import Common.Trino;

/**
 *
 * Clase auxiliar que contiene los clientes RMI conectados
 *
 * @author rafar
 *
 */
public class ClientesContectados {

	/**
	 * lista de los clientes RMI conectados
	 */
	private Vector listaClientes;
	/**
	 * lista de los clientes RMI conectados y cuyo usuario esta logeado
	 */
	private Vector listaClientesOnline;
	/**
	 * Mapa contenido como clave los nicks de los usuarios registrados en el sistema
	 * y como valor la lista de trinos pendientes de recibir por haber estado
	 * offline
	 */
	private HashMap<String, List<Trino>> trinosPendientes;

	public ClientesContectados() {
		listaClientes = new Vector<CallbackUsuarioInterface>();
		listaClientesOnline = new Vector<CallbackUsuarioInterface>();
		trinosPendientes = new HashMap<String, List<Trino>>();
	}

	public Vector getClientesContectados() {
		return listaClientes;
	}

	public Vector getClientesContectadosOnline() {
		return listaClientesOnline;
	}

	public void addClienteConectado(CallbackUsuarioInterface callbackUsuarioInterface) {

		if (!listaClientes.contains(callbackUsuarioInterface)) {
			listaClientes.add(callbackUsuarioInterface);
		}
	}

	public boolean eliminarClienteConectado(CallbackUsuarioInterface callbackUsuarioInterface) {
		return listaClientes.remove(callbackUsuarioInterface);
	}

	public void addClienteConectadoOnline(CallbackUsuarioInterface callbackUsuarioInterface) {
		listaClientesOnline.add(callbackUsuarioInterface);
	}

	public boolean eliminarClienteConectadoOnline(CallbackUsuarioInterface callbackUsuarioInterface) {
		return listaClientesOnline.remove(callbackUsuarioInterface);
	}

	/**
	 * Añade un trino pendiente de recibir a un usuario
	 *
	 * @param nick
	 * @param trino
	 * @return true si la operacion se ha realizado con exito, false en caso
	 *         contrario
	 */
	public boolean addTrinoPendiente(String nick, Trino trino) {

		List<Trino> listTrinosPendientes = trinosPendientes.get(nick);
		if (listTrinosPendientes == null) {
			listTrinosPendientes = new Vector();
			listTrinosPendientes.add(trino);

		} else {
			listTrinosPendientes.add(trino);
		}
		trinosPendientes.put(nick, listTrinosPendientes);

		return true;
	}

	public boolean eliminarTrinosPendientes(String nick) {

		trinosPendientes.remove(nick);

		return true;
	}

	public List<Trino> obtenerTrinosPendientes(String nick) {
		return trinosPendientes.get(nick);
	}

}

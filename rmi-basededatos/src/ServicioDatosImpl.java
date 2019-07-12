
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Common.Trino;

public class ServicioDatosImpl extends UnicastRemoteObject implements ServicioDatosInterface {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Mapa contenido la lista de usuarios del sistema
	 */
	private static Map<String, UsuarioRegistrado> usuarios = null;

	protected ServicioDatosImpl() throws RemoteException {
		super();
		usuarios = new HashMap<String, UsuarioRegistrado>();
	}

	@Override
	public boolean registrarUsuario(String nombre, String nick, String password) throws RemoteException {

		boolean resultado = false;

		UsuarioRegistrado usuarioRegistrado = null;

		// Comprobamos inicialmente que el nick no este previamente registrado
		if (!usuarios.containsKey(nick)) {

			usuarioRegistrado = new UsuarioRegistrado(nombre, nick, password);
			usuarios.put(nick, usuarioRegistrado);

			resultado = true;
		}
		return resultado;

	}

	@Override
	public boolean autenticarUsuario(String nick, String password) throws RemoteException {

		boolean resultado = false;

		// Obetenemos el usuario con dicho nick
		UsuarioRegistrado usuario = usuarios.get(nick);

		if (usuario != null) {
			// Verificamos que la contraseña proveida es la correcta
			if (usuario.getPassword().equals(password)) {
				// Establecemos que ese usuario esta actualmente online
				usuario.setOnline(true);
				resultado = true;
			}
		}
		return resultado;
	}

	@Override
	public boolean logout(String nick) throws RemoteException {

		// Obtenemos el usuario con dicho nick y establecemos que actualmente no esta
		// logeado
		UsuarioRegistrado usuario = usuarios.get(nick);
		usuario.setOnline(false);
		return true;

	}

	@Override
	public List<String> listarUsuarios() {

		List<String> listadoUsuarios = new ArrayList<String>();
		Collection<UsuarioRegistrado> values = usuarios.values();

		for (UsuarioRegistrado usuarioRegistrado : values) {

			listadoUsuarios.add(usuarioRegistrado.getNick());
			System.out.println(usuarioRegistrado);

		}

		return listadoUsuarios;

	}

	@Override
	public List<String> listarUsuariosLogeados() {

		List<String> usuariosLogeados = new ArrayList<String>();
		Collection<UsuarioRegistrado> usuariosRegistrados = usuarios.values();

		for (UsuarioRegistrado usuario : usuariosRegistrados) {

			if (usuario.isOnline()) {
				usuariosLogeados.add(usuario.getNick());
			}

		}

		return usuariosLogeados;
	}

	@Override
	public String enviarTrino(Trino trino) {

		UsuarioRegistrado usuario = usuarios.get(trino.ObtenerNickPropietario());
		// Añadimos a dicho usuario el trino recibido
		List<Trino> trinos = usuario.getTrinos();
		trinos.add(trino);

		return trino.ObtenerTrino();
	}

	@Override
	public boolean seguirA(String nickSeguidor, String nickSeguido) {

		boolean resultado = false;

		// Obtenemos almos usarios, tanto el seguidor como el seguido
		UsuarioRegistrado seguidorRegistrado = usuarios.get(nickSeguidor);
		UsuarioRegistrado seguidoRegistrado = usuarios.get(nickSeguido);

		// Verificamos que ambos existen en el sistema
		if (seguidorRegistrado != null && seguidoRegistrado != null) {

			// Añadimos al usuario a seguir un nuevo seguidor
			List<UsuarioRegistrado> siguiendo = seguidorRegistrado.getSiguiendo();
			siguiendo.add(seguidoRegistrado);

			// Añadimos al seguidor nuevo usuario a seguir
			List<UsuarioRegistrado> seguidores = seguidoRegistrado.getSeguidores();
			seguidores.add(seguidorRegistrado);
			resultado = true;
		}

		return resultado;
	}

	@Override
	public boolean dejarSeguirA(String nickSeguidor, String nickSeguido) {

		boolean resultado = false;

		// Obtenemos almos usarios, tanto el seguidor como el seguido
		UsuarioRegistrado seguidorRegistrado = usuarios.get(nickSeguidor);
		UsuarioRegistrado seguidoRegistrado = usuarios.get(nickSeguido);

		// Verificamos que ambos existen en el sistema
		if (seguidorRegistrado != null && seguidoRegistrado != null) {

			// Eliminamos del usuario a seguir el seguidor
			List<UsuarioRegistrado> siguiendo = seguidorRegistrado.getSiguiendo();
			siguiendo.remove(seguidoRegistrado);

			// Eliminamos del seguidor el usuario a seguir
			List<UsuarioRegistrado> seguidores = seguidoRegistrado.getSeguidores();
			seguidores.remove(seguidorRegistrado);

			resultado = true;

		}
		return resultado;
	}

	@Override
	public List<Trino> listarTrinos() {
		List<Trino> trinos = new ArrayList<Trino>();

		Collection<UsuarioRegistrado> datosUsuarios = usuarios.values();

		for (UsuarioRegistrado usuario : datosUsuarios) {

			List<Trino> trinosUsuario = usuario.getTrinos();
			for (Trino trino : trinosUsuario) {
				trinos.add(trino);
				System.out.println(trino);

			}
		}
		return trinos;
	}

	@Override
	public List<String> listarSeguidoresLogeados(String nick) throws RemoteException {

		List<String> listaSeguidoresLogeados = new ArrayList<String>();
		UsuarioRegistrado seguidoRegistrado = usuarios.get(nick);
		List<UsuarioRegistrado> seguidores = seguidoRegistrado.getSeguidores();

		for (UsuarioRegistrado usuarioRegistrado : seguidores) {
			if (usuarioRegistrado.isOnline()) {
				listaSeguidoresLogeados.add(usuarioRegistrado.getNick());
			}
		}

		return listaSeguidoresLogeados;
	}

	@Override
	public List<String> listarSeguidoresNoLogeados(String nick) throws RemoteException {
		List<String> listaSeguidoresNoLogeados = new ArrayList<String>();
		UsuarioRegistrado seguidoRegistrado = usuarios.get(nick);
		List<UsuarioRegistrado> seguidores = seguidoRegistrado.getSeguidores();

		for (UsuarioRegistrado usuarioRegistrado : seguidores) {
			if (!usuarioRegistrado.isOnline()) {
				listaSeguidoresNoLogeados.add(usuarioRegistrado.getNick());
			}
		}

		return listaSeguidoresNoLogeados;
	}

}

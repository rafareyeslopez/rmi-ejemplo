
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import Common.Trino;

/**
 * @author rafar
 *
 */
public class Usuario {

	private static final String URL_SERVIDOR_AUTENTICACION = "rmi://localhost:9001/servidor/autenticacion";
	private static final String URL_SERVIDOR_GESTOR = "rmi://localhost:9001/servidor/gestor";

	private static ServicioAutenticacionInterface servicioAutenticacion;
	private static ServicioGestorInterface servicioGestor;
	private static CallbackUsuarioInterface callbackUsuario;

	public Usuario() throws MalformedURLException, RemoteException, NotBoundException {

		// Creamos la instancia para el callback
		callbackUsuario = new CallbackUsuarioImpl();

		// Se conectado tanto al servidor de autenticacion como al gestor
		servicioAutenticacion = (ServicioAutenticacionInterface) Naming.lookup(URL_SERVIDOR_AUTENTICACION);
		servicioGestor = (ServicioGestorInterface) Naming.lookup(URL_SERVIDOR_GESTOR);

		System.out.println("Conectado a Servidor!");

	}

	public static void main(String[] args) {

		try {

			// Arrancamos el registry
			startRegistry(9002);

			// Creamos instancias tanto del servicio para callback como del usuario en si
			Usuario usuario = new Usuario();
			callbackUsuario = new CallbackUsuarioImpl();

			// Invocamos al servicio gestor para que registre el callback del usuario
			servicioGestor.registrarParaCallback(callbackUsuario);

			// Mostramos el menu deseado, de momento el inicial
			mostrarMenuInicial(usuario);

		} catch (final Exception e) {

			System.err.println("Error al iniciar cliente usuario: " + e.toString());
			e.printStackTrace();

		}

	}

	/**
	 * Muestra el menu inicial
	 *
	 * @param usuario
	 * @throws RemoteException
	 */
	private static void mostrarMenuInicial(Usuario usuario) throws RemoteException {

		System.out.println("Seleccione una opcion:");
		System.out.println("1. Registrar un nuevo usuario");
		System.out.println("2. Hacer login");
		System.out.println("3. Salir");

		Scanner in = new Scanner(System.in);
		String opcion = in.nextLine();

		if (opcion.equals("1")) {
			System.out.println("Introduzca nombre");
			in = new Scanner(System.in);
			String nombre = in.nextLine();
			System.out.println("Introduzca nick");
			in = new Scanner(System.in);
			String nick = in.nextLine();
			System.out.println("Introduzca password");
			in = new Scanner(System.in);
			String password = in.nextLine();

			// El usuario ha elegido registrarse, por lo tanto invicamos al servicio de
			// autenticacion
			if (servicioAutenticacion.altaUsuario(nombre, nick, password)) {
				// Si el registro ha sido con exito lo inficamos
				System.out.println("Registro con exito!");
			} else {
				System.err
						.println("El registro del usuario ha fallado! Es posible que el nick elegido ya este en uso.");
			}
			mostrarMenuInicial(usuario);

		} else if (opcion.equals("2")) {

			// El usuario ha elegido logearse
			System.out.println("Introduzca nick");
			in = new Scanner(System.in);
			String nick = in.nextLine();
			System.out.println("Introduzca password");
			in = new Scanner(System.in);
			String password = in.nextLine();

			// Invocamos al servicio de autenticacion para intentar hacer login
			if (servicioAutenticacion.autenticarUsuario(nick, password, callbackUsuario)) {

				// Si el login ha sido con exito registramos en el callback el nick empleado en
				// el cliente RMI
				callbackUsuario.setNick(nick);
				System.out.println("Login correcto!");
				// Mostramos el menu para un usuario logeado
				mostrarMenuUsuario(nick, usuario);

			} else {
				System.err.println("Login INCORRECTO!");
				mostrarMenuInicial(usuario);
			}

		} else if (opcion.equals("3")) {
			System.out.println("Apagando Cliente Usuario ...");
			in.close();
			// Al apagar anulamos el registro para callback en el servidor
			servicioGestor.anularRegistroParaCallback(callbackUsuario);
			System.exit(0);

		} else {
			System.out.println("Opcion invalida!");
		}

		if (!opcion.equals("3")) {
			mostrarMenuInicial(usuario);
		}
	}

	/**
	 *
	 * Muestra el menu para un usuario logeado
	 *
	 * @param nick
	 * @param usuario
	 * @throws RemoteException
	 */
	private static void mostrarMenuUsuario(String nick, Usuario usuario) throws RemoteException {

		System.out.println("Seleccione una opcion:");
		System.out.println("1. Informacion del usuario");
		System.out.println("2. Enviar Trino");
		System.out.println("3. Listar usuarios del sistema");
		System.out.println("4. Seguir a");
		System.out.println("5. Dejar de seguir a");
		System.out.println("6. Borrar trino a los usuarios que todavia no lo han recibido");
		System.out.println("7. Salir \"Logout\"");

		Scanner in = new Scanner(System.in);
		String opcion = in.nextLine();

		if (opcion.equals("1")) {

			System.out.println(nick);

		} else if (opcion.equals("2")) {

			System.out.println("Escriba su mensaje");
			in = new Scanner(System.in);
			String mensaje = in.nextLine();
			// Publica el trino a traves del servicio gestor
			servicioGestor.enviarTrino(new Trino(mensaje, nick));

		} else if (opcion.equals("3")) {

			for (String usuarioRegistrado : servicioGestor.listarUsuarios()) {
				System.out.println(usuarioRegistrado);
			}

		} else if (opcion.equals("4")) {

			System.out.println("Introduzca nick del usuario a seguir");
			in = new Scanner(System.in);
			String siguiendo = in.nextLine();
			// Invovamos al servicio gestor para hacerse seguidor del usuario indicado
			boolean seguirA = servicioGestor.seguirA(nick, siguiendo);

			if (seguirA) {
				System.out.println("Eres seguidor de: " + siguiendo);

			} else {
				System.err.println("Usuario a seguir no existente!");
			}

		} else if (opcion.equals("5")) {

			System.out.println("Introduzca nick del usuario a dejar de seguir");
			in = new Scanner(System.in);
			String siguiendo = in.nextLine();

			// Invovamos al servicio gestor para dejar de ser seguidor del usuario indicado
			boolean dejarSeguirA = servicioGestor.dejarSeguirA(nick, siguiendo);

			if (dejarSeguirA) {
				System.out.println("Has dejado de serseguidor de: " + siguiendo);

			} else {
				System.err.println("Usuario a dejar de seguir no existente!");
			}

		} else if (opcion.equals("6")) {
			// TODO: por implementar
			System.out.println("Opcion aun no disponible");

			servicioGestor.eliminarTrinosNoRecibidos(nick);

		} else if (opcion.equals("7")) {

			System.out.println("Cerrando session usuario ...");
			// Invocamos al servicio de autenticacion para indicar que le usuario se ha
			// deslogeado del sistema
			servicioAutenticacion.salir(nick);
			// Mostramos de nuevo el menu inicial

			mostrarMenuInicial(usuario);
		} else {
			System.out.println("Opcion invalida!");
		}

		if (!opcion.equals("7")) {
			mostrarMenuUsuario(nick, usuario);
		}
	}

	/**
	 * Metodo para arrancar el registry y asi controlar la posibilidad de varios
	 * clientes ejecutansose a la vez
	 *
	 * @param puertoRMI
	 * @throws RemoteException
	 */
	private static void startRegistry(int puertoRMI) throws RemoteException {
		try {
			Registry registry = LocateRegistry.getRegistry(puertoRMI);
			registry.list();
		} catch (RemoteException e) {
			// No valid registry at that port.
			Registry registry = LocateRegistry.createRegistry(puertoRMI);
		}
	}

}

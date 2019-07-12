
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.util.Scanner;

/**
 * @author rafar
 *
 */
public class Servidor {

	private static final String URL_SERVIDOR_AUTENTICACION = "rmi://localhost:9001/servidor/autenticacion";
	private static final String URL_SERVIDOR_GESTOR = "rmi://localhost:9001/servidor/gestor";

	private static ServicioAutenticacionInterface servicioAutenticacion;
	private static ServicioGestorInterface servicioGestor;

	private static ServicioDatosInterface servicioDatos;

	protected Servidor() throws RemoteException, MalformedURLException, NotBoundException {

		super();

		servicioDatos = (ServicioDatosInterface) Naming.lookup("rmi://localhost:9000/basededatos/db");
		System.out.println("Servidor Conectado a Base de datos!");

	}

	public static void main(String[] args) {

		try {

			// Arrancamos el registry
			LocateRegistry.createRegistry(9001);

			// Creamos instancias tanto de los servicio de autenticacion como el gestor, asi
			// como del servidor en si.
			// Ademas de la clase auxiliar para gestionar los clientes RMI conectados
			Servidor servidor = new Servidor();
			ClientesContectados clientesContectados = new ClientesContectados();
			servicioAutenticacion = new ServicioAutenticacionImpl(servicioDatos, clientesContectados);
			servicioGestor = new ServicioGestorImpl(servicioDatos, clientesContectados);

			// Exponemos el servicio de datos
			Naming.rebind(URL_SERVIDOR_AUTENTICACION, servicioAutenticacion);
			Naming.rebind(URL_SERVIDOR_GESTOR, servicioGestor);
			System.out.println("Servidor listo!");

			// Mostramos el menu deseado
			mostrarMenu(servidor);

		} catch (final Exception e) {

			System.err.println("Error al iniciar servidor: " + e.toString());
			e.printStackTrace();

		}

	}

	/**
	 *
	 * Muestra el menu
	 *
	 * @param servidor
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws MalformedURLException
	 */
	private static void mostrarMenu(Servidor servidor)
			throws RemoteException, MalformedURLException, NotBoundException {

		System.out.println("Seleccione una opcion:");
		System.out.println("1. Informacion del Servidor");
		System.out.println("2. Listar usuarios logeados");
		System.out.println("3. Salir");

		Scanner in = new Scanner(System.in);
		String opcion = in.nextLine();

		if (opcion.equals("1")) {
			System.out.println("Servidor en arriba");
		} else if (opcion.equals("2")) {
			servidor.servicioGestor.listarUsuariosLogeados();
		} else if (opcion.equals("3")) {
			System.out.println("Apagando Servidor ...");
			in.close();
			Naming.unbind(URL_SERVIDOR_AUTENTICACION);
			Naming.unbind(URL_SERVIDOR_GESTOR);
			System.exit(0);
		} else {
			System.out.println("Opcion invalida!");
		}

		if (!opcion.equals("3")) {
			mostrarMenu(servidor);
		}
	}

}

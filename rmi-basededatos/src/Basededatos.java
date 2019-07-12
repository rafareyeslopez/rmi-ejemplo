

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
public class Basededatos   {


	private static final String URL_BASE_DATOS = "rmi://localhost:9000/basededatos/db";

	private static ServicioDatosInterface servicioDatos;

	protected Basededatos() throws RemoteException {

		super();

	}

	public static void main(String[] args) {

		try {

			// Arrancamos el registry
			LocateRegistry.createRegistry(9000);

			// Creamos instancias tanto del servicio de datos como de la base de datos en si
			servicioDatos = new ServicioDatosImpl();
			Basededatos basededatos = new Basededatos();

			// Exponemos el servicio de datos
			Naming.rebind(URL_BASE_DATOS, servicioDatos);
			System.out.println("Base de datos lista!");

			// Mostramos el menu deseado
			mostrarMenu(basededatos);


		} catch (final Exception e) {

			System.err.println("Error al iniciar base de datos: " + e.toString());
			e.printStackTrace();

		}

	}

	/**
	 * Muestra el menu
	 *
	 * @param basededatos
	 * @throws RemoteException
	 * @throws NotBoundException
	 * @throws MalformedURLException
	 */
	private static void mostrarMenu(Basededatos basededatos) throws RemoteException, MalformedURLException, NotBoundException {

		System.out.println("Seleccione una opcion:");
		System.out.println("1. Informacion de la Base de Datos");
		System.out.println("2. Listar usuarios registrados");
		System.out.println("3. Listar trinos");
		System.out.println("4. Salir");

		Scanner in = new Scanner(System.in);
		String opcion = in.nextLine();

		if (opcion.equals("1")) {
			System.out.println("Base de datos en " + URL_BASE_DATOS);
		} else if (opcion.equals("2")) {
			basededatos.servicioDatos.listarUsuarios();
		} else if (opcion.equals("3")) {
			basededatos.servicioDatos.listarTrinos();
		} else if (opcion.equals("4")) {
			System.out.println("Apagando base de datos ...");
			in.close();
			Naming.unbind(URL_BASE_DATOS);
			System.exit(0);
		} else {
			System.out.println("Opcion invalida!");
		}

		if (!opcion.equals("4")) {
			mostrarMenu(basededatos);
		}
	}

}

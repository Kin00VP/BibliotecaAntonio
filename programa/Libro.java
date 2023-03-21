package programa;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

/**
 * 
 * LIBROS DE HARRY POTTER
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "titulo", "precio", "desc"})
public class Libro implements Serializable{

	
	private static final long serialVersionUID = 1L;
	// Elementos del libro
	@XmlElement
	String titulo;
	@XmlElement
	double precio;
	@XmlElement(name = "descripcion")
	String desc;

	// Atributos del libro
	@XmlAttribute
	String tapa;
	@XmlAttribute
	int id;

	// ---------------------CONSTRUCTORES--------------------------------------------
	
	public Libro() {/*CONSTRUCTOR VACIO NECESARIO PARA RECUPERACION XML*/}
	
	public Libro(String titulo, double precio, String desc, String tapa) {
		this.titulo = titulo;
		this.precio = precio;
		this.desc = desc;
		this.tapa = tapa;

		// EL ID ES AUTOINCREMENTAL (SE LE SUMA 1 AL TAMAÑO PARA QUE NO HAYA ID = 0)
		this.id = Biblioteca.biblio.size() + 1;
	}

	// --------------------------------------------------------------------------------------------------------------------------

	public static void creaLibro() {

		String ruta = "src\\ficherosInfo\\";
		String[] titulos, descripciones, tapas;
		double[] precios;

		// HEMOS EMPLEADO CLASES ESTATICAS PARA REDUCIR LA CANTIDAD DE CLASES Y
		// CONSTRUCTORES EN EL PROGRAMA
		// AL METODO EXTRAER() SE LE PASA UN FICHERO SEGÚN SU CONTENIDO
		// CADA UNO DEVOLVERA UN ARRAY DEL TIPO STRING/DOUBLE (SEGÚN PROCEDA), POR ESO
		// LO GUARDAMOS EN NUEVOS ARRAYS
		titulos = ObtenerDatoXXX.ObtenerDatoString.extraer(new File(ruta + "titulos.txt"));
		System.out.println("\u001b[35;1mREFERENCIANDO TITULOS...\u001b[0m");
		descripciones = ObtenerDatoXXX.ObtenerDatoString.extraer(new File(ruta + "descripcionesharrypotter.txt"));
		System.out.println("\u001b[35;1mDESCRIBIENDO...\u001b[0m");
		tapas = ObtenerDatoXXX.ObtenerDatoString.extraer(new File(ruta + "Tapas.txt"));
		System.out.println("\u001b[35;1mCOMPROBANDO CATÁLOGO...\u001b[0m");
		precios = ObtenerDatoXXX.ObtenerDatoDouble.extraer(new File(ruta + "precios.txt"));
		System.out.println("\u001b[35;1mCONSULTANDO PRECIOS...\u001b[0m");

		// CREAMOS CADA UNO DE LOS LIBROS Y LOS VAMOS INCLUYENDO EN LA BIBLIOTECA
		for (int i = 0; i < titulos.length; i++) {
			
			Biblioteca.biblio.add(new Libro(titulos[i], precios[i], descripciones[i], tapas[i]));
		}

	}

	// ---------------------------------------------------------------------------------------------------------------------------
	public static void menu() {

		Integer n;
		existeXML();
		
		do {

			System.out.println("1. CONSULTAR LIBRO\n" + "2. LISTAR LIBRERíA\n" + "3. ELIMINAR BASE DE DATOS\n"
					+ "4. CREAR COPIA DE SEGURIDAD\n" + "5. RESTAURAR COPIA DE SEGURIDAD\n" + "6. SALIR DEL PROGRAMA");
			n = pedirNumero();

			switch (n) {

			case 1:
				int idConsulta;
				System.out.print("INTRODUZCA ID DEL LIBRO: ");
				idConsulta = pedirNumero();
				realizarConsulta(idConsulta);
				break;

			case 2:
				listaLibros();
				break;

			case 3:
				eliminarXML();
				break;

			case 4:
				crearCopiaSeguridad();
				break;
				
			case 5:
				restaurarCopiaSeguridad();
				break;
				
			case 6:
				// NO HACE NADA (HAY QUE PONERLO POR EL FORMATO DO-WHILE)
				break;

			default:
				System.err.println("OPCION NO VÁLIDA");
				break;
			}

		} while (n != 6);
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	static void realizarConsulta(int con) {
		
		Libro libro_solicitado = null;
		for (Libro l : Biblioteca.biblio) {
			if(l.id == con)
				libro_solicitado = l;
		}
		
		System.out.println(libro_solicitado.toString());
	}

	// ---------------------------------------------GUARDADO DE ARCHIVO XML (CON JAXB)--------------------------------------------
	public static void escrituraJAXB(ArrayList<Libro> bibliotecaDeLibros, File fichero) throws JAXBException {

		JAXBContext jctx = JAXBContext.newInstance(Biblioteca.class);
		Marshaller documentoXML = jctx.createMarshaller();
		documentoXML.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		Biblioteca objetoRaiz = new Biblioteca();
		objetoRaiz.setBiblio(bibliotecaDeLibros);
		documentoXML.marshal(objetoRaiz, fichero);
	}

	// ---------------------------------------------LECTURA DE ARCHIVO XML (CON JAXB)---------------------------------------------
	public static Biblioteca lecturaJAXB(File fichero) throws JAXBException {

		JAXBContext jctx = JAXBContext.newInstance(Biblioteca.class);
		@SuppressWarnings("unused")
		Biblioteca bbdd = (Biblioteca) jctx.createUnmarshaller().unmarshal(fichero);
		
		return bbdd;
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	@SuppressWarnings("resource")
	public static Integer pedirNumero() {

		Integer n = null;

		while (n == null) {
			try {

				n = new Scanner(System.in).nextInt();
			} catch (Exception e) {
				System.err.println(e.getMessage());
				n = new Scanner(System.in).nextInt();
			}
		}

		return n;
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	public static void existeXML() {

		String ruta = "src\\ficherosInfo\\BBDD.xml";
		File bbdd = new File(ruta);
		Biblioteca b = null;

		if (bbdd.exists()) {
			System.out.println("\u001b[33;1mBASE DE DATOS ACTUALIZADA Y OPERATIVA\u001b[0m");
			try {
				b = lecturaJAXB(bbdd);
			} catch (JAXBException e) {
				e.printStackTrace();
			}
		} else {
			System.out.println("\u001b[35;1mGENERANDO BASE DE DATOS...\u001b[0m");
			creaLibro();
			try {
				escrituraJAXB(Biblioteca.biblio, bbdd);
			} catch (JAXBException e) {
				System.err.println(e.getMessage());
				e.printStackTrace();
			}
			System.out.println("\u001b[33;1mBASE DE DATOS ACTUALIZADA Y OPERATIVA\u001b[0m");
		}
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------
	public static void eliminarXML() {

		String ruta = "src\\ficherosInfo\\BBDD.xml";
		File bbdd = new File(ruta);

		System.out.println("¿ESTÁ SEGURO QUE DESEA ELIMINAR LA BASE DE DATOS ACTUAL?\nESTA ACCIÓN NO TIENE VUELTA ATRÁS");
		System.out.println("SI                                    NO");
		System.out.println("> ");
		@SuppressWarnings("resource")
		String resp = new Scanner(System.in).next();
		resp = resp.toUpperCase();
		
		switch (resp) {
		case "SI":
		case "SÍ":
			if (bbdd.exists()) {
				bbdd.delete();
				Biblioteca.biblio.clear();
			}
			break;
			
		case "NO":
		default:
			System.out.println("ACCIÓN CANCELADA");
			break;
		}
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------
	public static void ordenaTitulo() {
		creaCopiaTabla(new Comparadores.comparaTitulo());
	}
	public static void ordenaPrecio() {
		creaCopiaTabla(new Comparadores.comparaPrecio());
	}
	public static void ordenaFormato() {
		creaCopiaTabla(new Comparadores.comparaTapa());
	}
	
	static <T> void creaCopiaTabla(T c) {
		ArrayList<Libro> copia = new ArrayList<>(Biblioteca.biblio);
		Collections.sort(copia, new Comparadores.comparaTitulo());
		System.out.println(copia.toString());
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------
	public static void listaLibros() {
		System.out.println("Seleccione el criterio según el cual desea mostrar los libros ordenados:");
		System.out.println("1. Ordenar por título");
		System.out.println("2. Ordenar por precio");
		System.out.println("3. Ordenar por tipo de tapa y título");
		int opcion = pedirNumero();
		switch (opcion) {
		case 1:
			ordenaTitulo();
			break;
		case 2:
			ordenaPrecio();
			break;
		case 3:
			ordenaFormato();
			break;
		default:
			System.err.println("OPCIÓN NO VÁLIDA");
			break;
		}
	}

	// ---------------------------------------------------------------------------------------------------------------------------
	
	@Override
	public String toString() {
		return this.titulo.toUpperCase() + "\nDESCRIPCIÓN: " + this.desc + "\nPRECIO: " 
				+ this.precio + "\nFORMATO: " + this.tapa + "\n\nID DEL LIBRO: " + this.id;
	}	
	
	// ---------------------------------------------------------------------------------------------------------------------------
	
	public static void extraerDatosDat() {

		String ruta = "src\\ficherosInfo\\copia_de_seguridad.dat";
		File copiaSeguridad = new File(ruta);

		System.out.println("COMPROBANDO EXISTENCIA DE DATOS GUARDADOS...");

		if (copiaSeguridad.exists()) {

			try (ObjectInputStream rd = new ObjectInputStream(new FileInputStream(copiaSeguridad))) {

				Biblioteca.biblio.addAll((ArrayList<Libro>) rd.readObject());
				System.out.println("DATOS GUARDADOS RECUPERADOS...");
				System.out.println("COPIA DE SEGURIDAD RESTAURADA");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------
	
	public static void restaurarCopiaSeguridad() {
		
		String ruta = "src\\ficherosInfo\\copia_de_seguridad.dat";
		File copiaSeguridad = new File(ruta);
		
		if(copiaSeguridad.exists())
			extraerDatosDat();
		else
			System.out.println("NO EXISTEN COPIAS DE SEGURIDAD PARA RESTAURACIÓN DE DATOS");
			
		
	}
	
	// ---------------------------------------------------------------------------------------------------------------------------
	
	public static void crearCopiaSeguridad() {

		String ruta = "src\\ficherosInfo\\copia_de_seguridad.dat";
		
		System.out.println("CREANDO COPIA DE SEGURIDAD...");
		try (ObjectOutputStream wrt = new ObjectOutputStream(new FileOutputStream(ruta))) {

			wrt.writeObject(Biblioteca.biblio);
			System.out.println("GUARDANDO DATOS...");
			System.out.println("COPIA DE SEGURIDAD REALIZADA CON ÉXITO");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
} // FIN PROGRAMA

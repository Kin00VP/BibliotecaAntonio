package programa;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * BIBLIOTECA DONDE SE INCLUIRAN LOS LIBROS 
 * (INCLUYE ARRAYLIST DEL PROGRAMA)
 *
 */
@XmlRootElement
public class Biblioteca {

	public static ArrayList<Libro> biblio = new ArrayList<>();

	/**
	 * Nos devuelve el arraylist de manera literal
	 * @return ArrayList<Libro>
	 */
	public static ArrayList<Libro> getBiblio() {
		return biblio;
	}

	/**
	 * Designa a la biblioteca un ArrayList de objetos Libros
	 * Designamos al setter como XmlElement para que cada uno de sus objetos Libro 
	 * contenido se convierta en un elemento literal del archivo XML 
	 * 
	 * @param biblio ArrayList de libros
	 */
	@XmlElement(name = "Libro")
	public static void setBiblio(ArrayList<Libro> biblio) {
		Biblioteca.biblio = biblio;
	}
	
	
}

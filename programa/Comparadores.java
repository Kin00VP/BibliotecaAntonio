package programa;

import java.util.Comparator;

/**
 * 
 * COMPARADORES QUE SERÁN NECESARIOS A 
 * LO LARGO DEL PROGRAMA PARA LISTAR 
 * SEGÚN INDIQUE EL USUARIO
 *
 */
public class Comparadores {
	
	public static class comparaTitulo implements Comparator<Libro> {

		@Override
		public int compare(Libro o1, Libro o2) {
			return o1.titulo.compareTo(o2.titulo);
		}

	}

	public static class comparaPrecio implements Comparator<Libro> {

		@Override
		public int compare(Libro o1, Libro o2) {
			return (int) (o1.precio - o2.precio);
		}

	}

	public static class comparaTapa implements Comparator<Libro> {

		@Override
		public int compare(Libro o1, Libro o2) {
			
			int comp;
			if (o1.tapa.compareTo(o2.tapa) < 0) {
				comp = o1.tapa.compareTo(o2.tapa);
			} else if (o1.tapa.compareTo(o2.tapa) == 0) {
				comp = (int) (o1.precio - o2.precio);
			} else {
				comp = o1.tapa.compareTo(o2.tapa);
			}
			return comp;
		}

	}

}

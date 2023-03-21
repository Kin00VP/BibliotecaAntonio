package programa;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class ObtenerDatoXXX {

	public static class ObtenerDatoString {

		public static String[] extraer(File archivo) {

			String datichis = "";
			String[] datos = null;

			try (BufferedReader bf = new BufferedReader(new FileReader(archivo))) {

				String dato_extraido = bf.readLine();
				

				while (dato_extraido != null) {
					datichis += dato_extraido + "\t";
					dato_extraido = bf.readLine();
				}

				datos = datichis.split("\t");

				return datos;
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
			return datos;
		}
	}

	public static class ObtenerDatoDouble {
		
		public static double[] extraer(File archivo) {

			String precichis = "";
			double[] precios_en_formato_double = new double[7];

			try (BufferedReader bf = new BufferedReader(new FileReader(archivo))) {

				String precio_extraido = bf.readLine();
				String[] precios;

				while (precio_extraido != null) {
					precichis += precio_extraido + "\t";
					precio_extraido = bf.readLine();
				}

				precios = precichis.split("\t");
				
				
				
				for (int i = 0; i < precios.length; i++) {
					precios_en_formato_double[i] = Double.valueOf(precios[i]);
				}
				
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
			
			return precios_en_formato_double;
		}
	}
	
}

package dir;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
/**
 * 
 * @author marta
 *
 */
public class Principal {

	private static Scanner sc;

	public static void main(String[] args) {
		sc = new Scanner(System.in);
		
		//Input de datos.
		System.out.println("Introduce el nombre del archivo que estes buscando.");
		String nom = sc.next();
		System.out.println("Introduce la ruta de la carpeta en la que quires buscar.");
		String dir = sc.next();
		
		File carpeta = new File(dir);
		
		try {
			if(comparar(carpeta, nom)) System.out.println("Se encontró el fichero.");
			else System.out.println("No se ha encontrado el fichero.");
		} catch (FileNotFoundException e) {
			System.err.println("Error de IO.");
			e.printStackTrace();
		}

	}
	
	/**
	 * Localiza la carpeta y dice si existe o no.
	 * 
	 * @param carpeta
	 * @param nomFich
	 * @return
	 * @throws FileNotFoundException
	 */
	public static boolean localizar(File carpeta, String nomFich) throws FileNotFoundException{
		boolean isValid = true;
		
		if(!carpeta.exists()) throw new FileNotFoundException("La carpeta no existe.");
		if(!carpeta.isDirectory()) throw new RuntimeException("La carpeta no es un directorio.");
		
						
		return isValid;
	}
	
	/**
	 * Compara los ficheros de la carpeta con el nombre.
	 * 
	 * @param carpeta
	 * @param nomFich
	 * @return
	 * @throws FileNotFoundException
	 */
	public static boolean comparar(File carpeta, String nomFich) throws FileNotFoundException{
		boolean enc = false;
		
		if(localizar(carpeta, nomFich)) {
			File[] fich = carpeta.listFiles();
			for(int i = 0; i < fich.length; i++) {
				String nom = fich[i].getName();
				if(nomFich.compareTo(nom) == 0) enc = true;
			}
		}
		
		return enc;
	}

}

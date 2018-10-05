package model;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
/**
 * 
 * @author marta
 *
 */
public class GestionDatos {
	
	/**
	 * Constructor vacío.
	 */
	public GestionDatos() {

	}

	/**
	 * Abre ficheros.
	 * 
	 * @param nombreFichero
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public static BufferedReader abrirFichero(String nombreFichero) throws FileNotFoundException, IOException {
	     
	      FileReader fr = new FileReader(nombreFichero); 
	      BufferedReader br = new BufferedReader(fr); 
	   
	      return br;      
	}
	
	/**
	 * Cierra ficheros.
	 * 
	 * @param br
	 * @throws IOException
	 */
	public static void cerrarFichero(BufferedReader br) throws IOException {
		br.close();
	}
	
	/**
	 * Compara contenido de ficheros.
	 * 
	 * @param fichero1
	 * @param fichero2
	 * @return true o false
	 * @throws IOException
	 * @throws IOException
	 */
	public boolean compararContenido (String fichero1, String fichero2) throws IOException, IOException{
		BufferedReader br = abrirFichero(fichero1), br2 = abrirFichero(fichero2);	
		String cadenaFichero, cadenaFichero2, contenido1 =null, contenido2 =null; 
		
		//Lee los ficheros línea por línea.
		while((cadenaFichero = br.readLine())!=null) {
			contenido1 += cadenaFichero;
	    }
		
		while((cadenaFichero2 = br2.readLine())!=null) {
			contenido2 += cadenaFichero2; 
	    }
		
		cerrarFichero(br); // Cerramos la lectura del fichero1
		cerrarFichero(br2); // Cerramos la lectura del fichero2
		
		// Hacemos la comparacion de que si los ficheros son iguales o no.
		if( contenido1.compareTo(contenido2) == 0 ) return true;
		else return false;
		
	
	}
	
	/**
	 * Busca palabras.
	 * 
	 * @param fichero1
	 * @param palabra
	 * @param primera_aparicion
	 * @return
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public int buscarPalabra (String fichero1, String palabra, boolean primera_aparicion) 
			throws FileNotFoundException, IOException{
		
		 BufferedReader br = abrirFichero(fichero1);
		 String linea = null, ap = null;
		 int primera = 0, cont = 0, ult = 0;	 		 
		 
		 while ((linea = br.readLine()) != null) {
			 ap = linea;
			 cont++;			 
			 if(ap.compareTo(palabra)==0) { 			 
				 ult = cont;		    	
				 if(primera == 0 && primera_aparicion) primera = cont;				 							  	    	
			 }
		 }
		
		 cerrarFichero(br);
		 
		 //Devuelve según el check box.
		 if(primera_aparicion) return primera;
		 else return ult;
		  
	}	

	
	/**
	 * Guarda libros.
	 * @param libro
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void guardar_libro(Libro libro) throws FileNotFoundException, IOException {
		
		ObjectOutputStream out = null;
		
		out = new ObjectOutputStream(new FileOutputStream(libro.id +".txt"));
		out.writeObject(libro);

		intentarCerrar(out);
			
	}
	
	/**
	 * Cerrar cualquier cosa.
	 * @param aCerrar
	 * @throws IOException 
	 */
	public void intentarCerrar(Closeable aCerrar) throws IOException {
			if(aCerrar!= null) aCerrar.close();
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 * @throws ClassNotFoundException 
	 */
	public Libro recuperar_libro(String id) throws FileNotFoundException, IOException, ClassNotFoundException {
		Libro libro= null;
		ObjectInputStream in = null;
		
		in = new ObjectInputStream(new FileInputStream(id));
		libro = (Libro) in.readObject();

		intentarCerrar(in);
		
		return libro;
	}
	
	/**
	 * Devuelve todos los ficheros, el nombre.
	 * 
	 * @return libros
	 */
	public ArrayList<String> recuperar_todos(){
		
		ArrayList<String> libros = new ArrayList<>();		
		File carpeta = new File("").getAbsoluteFile();		
		File[] ficheros = carpeta.listFiles();
		
		//Si el fichero no es nulo realiza operaciones.
		if (ficheros != null) {
			//Recorre los ficheros y extrae los que tengan .txt
			for (int i=0; i<ficheros.length; i++) {
				String ext = ficheros[i].getName().substring(ficheros[i].getName().lastIndexOf(".") + 1);
				if(ext.compareTo("txt") == 0) libros.add(ficheros[i].getName());
								
			}	
		}
		
		//Devuelve los ficheros .txt
		return libros;
	}
	
	/**
	 * Primer ejercicio: Prefijos
	 * 
	 * @param prefijo
	 * @param fichero
	 * @return sb
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public StringBuilder mostrar_prefijo(String prefijo, String fichero) throws FileNotFoundException, IOException {
		BufferedReader br = abrirFichero(fichero);
		String cadenaFichero, contenido1 = null;
		StringBuilder sb = new StringBuilder();
		
		//Recorremos el fichero y guardamos las palabras en una cadena de texto.
		while((cadenaFichero = br.readLine())!=null) {
			contenido1 = cadenaFichero;
			if(contenido1.startsWith(prefijo)) sb.append(contenido1).append("\n");
	    }
		
		return sb;	
		
	}

	
	
}

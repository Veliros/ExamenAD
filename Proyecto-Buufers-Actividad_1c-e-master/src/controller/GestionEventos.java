package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import model.*;
import view.*;

/**
 * 
 * @author marta
 *
 */
public class GestionEventos {

	private GestionDatos model;
	private LaunchView view;
	private ActionListener actionListener_comparar, actionListener_buscar, 
	actionListener_guardarLibro,actionListener_recuperarLibro,actionListener_recuperarTodosLibro,
	actionListener_buscar_por_prefijo, actionListener_cantidadLibros;
	private ArrayList<Libro> libros;

	public GestionEventos(GestionDatos model, LaunchView view) {
		this.model = model;
		this.view = view;
	}

	public void contol() {
		actionListener_comparar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				call_compararContenido();
				
			}
		};
		view.getComparar().addActionListener(actionListener_comparar);

		actionListener_buscar = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {	
			try {
				int res = call_buscarPalabra();
				
				if(res > 0) view.getTextArea().setText("La palabra ha sido encontrada en la linea "+ res);
				else view.getTextArea().setText("No se ha encontrado ninguna palabra.");
				
			} catch (IOException e) {
				view.showError("Ha habido un error de IO.");
			}
			}
		};
		view.getBuscar().addActionListener(actionListener_buscar);
		
		// Guardar Libro
		actionListener_guardarLibro = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					call_guardarLibro();
					view.getTextArea().setText("El libro se ha guardado.");
				} catch (IOException e) {
					view.showError("Ha habido un error de IO al guardar el libro.");
				}
				
			}
		};
		view.getBtnGuardarLibro().addActionListener(actionListener_guardarLibro);
		
		actionListener_recuperarLibro = new ActionListener() {
			public void actionPerformed(ActionEvent actionEvent) {
				try {
					call_recuperar();
				} catch (ClassNotFoundException ex) {
					view.showError("No se encuentra la clase en el paquete modelo.");
				} catch(IOException e) {
					view.showError("No se ncuentra el fichero.");
				}
				
			}
		};
		view.getBtnRecuperarLibro().addActionListener(actionListener_recuperarLibro);
		
		actionListener_recuperarTodosLibro = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {				
				ArrayList<String> arrayLibros = call_recuperar_todos();
				Iterator<String> it = arrayLibros.iterator();
				StringBuilder sb = new  StringBuilder();
				
				//Concatena las strings.
				while(it.hasNext()) {				
					sb.append(it.next())
					  .append("\n");					
				}	
				
				view.getTextArea().setText("Lista de libros:\n " + sb);
			}
		};
		view.getBtnRecuperarTodos().addActionListener(actionListener_recuperarTodosLibro);
		
		actionListener_buscar_por_prefijo = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {				
				try {
					StringBuilder sb = call_mostrar_prefijo();
					
					if(sb.length() == 0) view.showError("No hay palabras con ese prefijo.");
					else view.getTextArea().setText("Las palabras encontradas son:\n" + sb);
				} catch (IOException e) {
					view.showError("No se encuentra el fichero.");
				}
			}
		};
		view.getBtnBuscarPorPrefijo().addActionListener(actionListener_buscar_por_prefijo);
		
		actionListener_cantidadLibros = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent actionEvent) {				
				try {				
					ArrayList<Libro> librosAutor = mostrar_libros();
					Iterator<Libro> it = librosAutor.iterator();
					int cont = 0;
					
					if(librosAutor != null) {
						while(it.hasNext()) {
							cont++;
						}					
						view.getTextArea().setText("Número de libros: " + cont);
					} else view.showError("No hay libros de este autor.");
					
					
				} catch (ClassNotFoundException ex) {
					view.showError("No están las clases correspondientes en el paquete model.");
				} catch (IOException e) {
					view.showError("Error de IO.");
				}
			}
		};
		view.getBtnEncontrarAutor().addActionListener(actionListener_cantidadLibros);
		
	}

	private int call_compararContenido() {
		String fichero1 = view.getFichero1().getText(); //almacena el nombre de fichero1
		String fichero2 = view.getFichero2().getText(); //almacena el nombre de fichero2
		
		try {
			//Mostramos el nombre del fichero1 y fichero 2 dentro del .compararContenido()
			boolean res = model.compararContenido(fichero1, fichero2 );
			
			if(res) view.getTextArea().setText("Su contenido es Igual");
			else view.getTextArea().setText("No tienen el contenido Igual");
			
		} catch (IOException e) {
			view.showError("Ha habido un error");
		}
		
		return 1;
	}
	
	/**
	 * Buscar palabras.
	 * 
	 * @return
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	private int call_buscarPalabra() throws FileNotFoundException, IOException {
		String fichero1 = view.getFichero1().getText(), 
				palabra = view.getPalabra().getText(); 
		boolean primera_aparicion = view.getPrimera().isSelected(); 
		
		int res = model.buscarPalabra(fichero1, palabra, primera_aparicion);
					
		return res;
	}
	
	/**
	 * Guarda libros.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	private void call_guardarLibro() throws FileNotFoundException, IOException {
		String identificador=view.getIdentificador().getText(),
			   titulo=view.getLibro().getText(),
			   autor=view.getAutor().getText(),
			   editor=view.getEditor().getText();
		
		int numeroDePaginas= Integer.parseInt(view.getTxtNumeroDePaginas().getText()),
			anyoDePublicacion= Integer.parseInt(view.getTxtNumeroDePaginas().getText());
		
		
		Libro libro1 = new Libro(identificador, titulo, autor, editor,  numeroDePaginas, 
				 anyoDePublicacion);
		
		model.guardar_libro(libro1);		
	}
	
	/**
	 * Llamada a recuperar libro.
	 * 
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 * @throws FileNotFoundException 
	 */
	private void call_recuperar() throws FileNotFoundException, ClassNotFoundException, IOException {
		String identificador = view.getIdentificador().getText();
		Libro libro = model.recuperar_libro(identificador);
		
		String id = libro.getIdentificador(),
			   titulo = libro.getTitulo(),
			   editor = libro.getEditor(),
			   autor = libro.getAutor();
		int  anyo = libro.getAnyoDePublicacion(),
			 numPag = libro.getNumeroDePaginas();
		
		// Mostramos por el textArea la informacion del libro recuperado.	  
		view.getTextArea().setText("Id: " + id + "\nTítulo: " + titulo + "\nEditor: " + editor
				+ "\nAutor: " + autor + "\nAño: " + anyo + "\nN. Páginas: " + numPag);
	}
	
	/**
	 * Devuelve todos los libros.
	 * 
	 * @return libros
	 */
	public ArrayList<String> call_recuperar_todos(){
		return model.recuperar_todos();	
	}
	
	/**
	 * Primer ejercicio: Mostrar prefijos.
	 * 
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public StringBuilder call_mostrar_prefijo() throws FileNotFoundException, IOException {
		String prefijo = view.getPalabra().getText(),
				fichero = view.getFichero1().getText();
		
		return model.mostrar_prefijo(prefijo, fichero);
	}
	
	
	/**
	 * Ejercicio 3: Aprovechando otros métodos encuentra los libros de un autor.
	 * 
	 * Suponiendo que el nombre del fichero es igual al título y que son todos txts añade libros del mismo autor
	 * Esto quiere decir que titulo == identificador.
	 * @return
	 * @throws FileNotFoundException
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public ArrayList<Libro> mostrar_libros() 
			throws FileNotFoundException, ClassNotFoundException, IOException{
		libros = null;
		ArrayList<String> recu = model.recuperar_todos();
		
		String autor = view.getAutor().getText();		
		
		for(int i = 0; i < recu.size(); i++) {		
			String id = model.recuperar_libro(recu.get(i)).getIdentificador();
			if(model.recuperar_libro(id).getAutor().compareTo(autor) == 0) libros.add(model.recuperar_libro(id));					
		}
		
		return libros;
		
	}
	
}

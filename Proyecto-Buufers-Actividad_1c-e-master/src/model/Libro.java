package model;

import java.io.Serializable;
/**
 * 
 * @author marta
 *
 */

public class Libro implements Serializable{
	private static final long serialVersionUID = 1L;
	String id = null, titulo = null, autor = null, editor = null;
	 int numPag;
	 int anyo;
	 
	 
	 /**
	  * 
	  * @param identificador
	  * @param titulo
	  * @param autor
	  * @param editor
	  * @param numeroDePaginas
	  * @param anyoDePublicacion
	  */
	 public Libro(String identificador, String titulo, String autor, String editor, int numeroDePaginas,
			int anyoDePublicacion) {
		super();
		this.id = identificador;
		this.titulo = titulo;
		this.autor = autor;
		this.editor = editor;
		this.numPag = numeroDePaginas;
		this.anyo = anyoDePublicacion;
	}
	 
	/**
	 *  
	 * @return id
	 */
	public String getIdentificador() {
		return id;
	}
	
	/**
	 * 
	 * @param identificador
	 */
	public void setIdentificador(String identificador) {
		this.id = identificador;
	}
	
	/**
	 * 
	 * @return titulo
	 */
	public String getTitulo() {
		return titulo;
	}
	
	/**
	 * 
	 * @param titulo
	 */
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	
	/**
	 * 
	 * @return autor
	 */
	public String getAutor() {
		return autor;
	}
	
	/**
	 * 
	 * @param autor
	 */
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	/**
	 * 
	 * @return editor
	 */
	public String getEditor() {
		return editor;
	}
	
	/**
	 * 
	 * @param editor
	 */
	public void setEditor(String editor) {
		this.editor = editor;
	}
	
	/**
	 * 
	 * @return numPag
	 */
	public int getNumeroDePaginas() {
		return numPag;
	}
	
	/**
	 * 
	 * @param numeroDePaginas
	 */
	public void setNumeroDePaginas(int numeroDePaginas) {
		this.numPag = numeroDePaginas;
	}
	
	/**
	 * 
	 * @return anyo
	 */
	public int getAnyoDePublicacion() {
		return anyo;
	}
	
	/**
	 * 
	 * @param anyoDePublicacion
	 */
	public void setAnyoDePublicacion(int anyoDePublicacion) {
		this.anyo = anyoDePublicacion;
	}
	
	
	
}

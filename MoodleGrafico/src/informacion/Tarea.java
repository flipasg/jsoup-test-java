/**
 *
 */
package informacion;

/**
 * @author Ikk
 * @date 6/5/2015
 *
 */
public class Tarea {
    private String nombre;
    private String comentarioProfesor;
    private String url;
    private String nota;

    /**
     * Constructor de la clase Tarea
     *
     * @param nombre
     * @param comentarioProfesor
     * @param nota
     */
    public Tarea(String nombre, String comentarioProfesor, String url,
	    String nota) {
	this.nombre = nombre;
	this.comentarioProfesor = comentarioProfesor;
	this.url = url;
	this.nota = nota;
    }

    /*
     * (non-Javadoc)
     *
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
	return nombre;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
	return nombre;
    }

    /**
     * @return the comentarioProfesor
     */
    public String getComentarioProfesor() {
	return comentarioProfesor;
    }

    /**
     * @return the url
     */
    public String getUrl() {
	return url;
    }

    /**
     * @return the nota
     */
    public String getNota() {
	return nota;
    }

}

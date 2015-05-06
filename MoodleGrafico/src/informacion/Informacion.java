/**
 *
 */
package informacion;

/**
 * @author Ikk
 * @date 6/5/2015
 *
 */
public class Informacion {
    private String usuario;
    private String contrasenia;

    /**
     * Constructor de la clase Informacion
     *
     * @param usuario
     * @param contrasenia
     */
    public Informacion(String usuario, String contrasenia) {
	this.usuario = usuario;
	this.contrasenia = contrasenia;
    }

    /**
     * @return the usuario
     */
    public String getUsuario() {
	return usuario;
    }

    /**
     * @return the contrasenia
     */
    public String getContrasenia() {
	return contrasenia;
    }

}

/**
 *
 */
package scrapper;

import informacion.Tarea;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author Ikk
 * @date 6/5/2015
 *
 */
public class BuscadorDeTareas {
    private static final String MOODLE_URL = "http://moodle.icjardin.com/login/index.php";
    private Map<String, String> cookies;
    private Document doc;

    /**
     * Constructor de la clase BuscadorDeTareas
     *
     * @param usuario
     * @param contrasenia
     * @throws IOException
     */
    public BuscadorDeTareas(String usuario, String contrasenia)
	    throws IOException {
	parsearWeb(usuario, contrasenia);
    }

    /**
     * @param usuario
     * @param contrasenia
     * @throws IOException
     */
    public void parsearWeb(String usuario, String contrasenia)
	    throws IOException {
	Connection.Response res = Jsoup.connect(MOODLE_URL).method(Method.GET)
		.execute();
	cookies = res.cookies();
	doc = Jsoup
		.connect(MOODLE_URL)
		.data("cookieexist", "false", "username", usuario, "password",
			contrasenia).cookies(cookies).post();
    }

    public void descargarFichero(String url) throws IOException {
	Document doc = Jsoup.connect(url).cookies(cookies).post();
	Elements files = doc.getElementsByClass("files").select("a");
	for (Element file : files) {
	    String nameFile = file.text();
	    String urlFile = file.absUrl("href");

	    byte[] b = Jsoup.connect(urlFile).cookies(cookies)
		    .ignoreContentType(true).execute().bodyAsBytes();

	    OutputStream oos = new BufferedOutputStream(new FileOutputStream(
		    nameFile));
	    oos.write(b);
	    oos.close();
	}
    }

    /**
     * @return la lista de tareas
     * @throws IOException
     */
    public ArrayList<Tarea> obtenerTareas() throws IOException {
	ArrayList<Tarea> tareas = new ArrayList<Tarea>();

	Element cursoProgramacion = doc.getElementsByClass("name").select("a")
		.first();
	if (cursoProgramacion == null)
	    return null;
	String absUrlProg = cursoProgramacion.absUrl("href");

	Document docProgramacion = Jsoup.connect(absUrlProg).cookies(cookies)
		.post();

	Elements calificacionesProgramacion = docProgramacion.getElementsByTag(
		"a").select("a");
	Element e = null;
	boolean encontrado = false;
	for (Iterator it = calificacionesProgramacion.iterator(); !encontrado
		&& it.hasNext();) {
	    e = (Element) it.next();
	    if (e.text().equals("Calificaciones")) {
		encontrado = true;
	    }
	}

	String absUrlCalificaciones = e.absUrl("href");

	Document docCalificaciones = Jsoup.connect(absUrlCalificaciones)
		.cookies(cookies).post();

	Elements filasTabla = docCalificaciones.getElementsByTag("tr");
	for (Element e1 : filasTabla) {
	    Elements columnasPorFila = e1.children();
	    if (columnasPorFila.size() > 2) {
		String nombre = columnasPorFila.get(0).text();
		String comentarioProfesor = columnasPorFila.get(4).text();
		String urlTarea = columnasPorFila.get(0).select("a")
			.attr("href");
		String nota = columnasPorFila.get(1).text();
		Tarea t = new Tarea(nombre, comentarioProfesor, urlTarea, nota);
		if (comentarioProfesor.length() > 2) {
		    tareas.add(t);
		}

	    }

	}

	return tareas;

    }

}

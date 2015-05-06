/**
 *
 */
package gui;

import informacion.Informacion;
import informacion.Tarea;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import scrapper.BuscadorDeTareas;

/**
 * @author Ikk
 * @date 6/5/2015
 *
 */
public class VentanaTareas extends JFrame {
    private ArrayList<Tarea> tareas;
    private JLabel calificacion, titulo;
    private JList<Tarea> listaTareas;
    private JTextArea comentario;
    private JButton descargar;
    private BuscadorDeTareas bt;

    /**
     * Constructor de la clase VentanaTareas
     *
     * @throws IOException
     */
    public VentanaTareas(String titulo, Informacion info) throws IOException {
	super(titulo);
	String usuario = info.getUsuario();
	String contrasenia = info.getContrasenia();
	bt = new BuscadorDeTareas(usuario, contrasenia);
	tareas = bt.obtenerTareas();
	if (tareas == null) {
	    JOptionPane.showMessageDialog(getContentPane(), "LOGIN INCORRECTO",
		    "ALERTA", JOptionPane.ERROR_MESSAGE);
	} else {
	    iniciar();
	}

    }

    public void iniciar() {
	instanciar();
	configurar();
	aniadir();
	oyentes();
	propiedades();
    }

    public void instanciar() {
	titulo = new JLabel("TAREAS");
	calificacion = new JLabel("CALIFICACION: ");
	DefaultListModel<Tarea> dlm = new DefaultListModel<Tarea>();
	for (Tarea t : tareas) {
	    dlm.addElement(t);
	}
	listaTareas = new JList<Tarea>(dlm);
	comentario = new JTextArea();
	descargar = new JButton("DESCARGAR");
    }

    public void configurar() {
	setSize(new Dimension(1000, 1000));
	setLayout(null);
	titulo.setFont(new Font("Arial", Font.BOLD, 20));
	calificacion.setFont(new Font("Arial", Font.BOLD, 20));
	listaTareas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	comentario.setEditable(false);
    }

    public void aniadir() {
	titulo.setBounds(10, 10, 250, 50);
	add(titulo);
	JScrollPane jsp = new JScrollPane(listaTareas,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	jsp.setBounds(10, 55, 350, 300);
	add(jsp);
	calificacion.setBounds(380, 10, 250, 50);
	add(calificacion);
	JScrollPane jsp2 = new JScrollPane(comentario,
		JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
		JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	jsp2.setBounds(380, 55, 400, 300);
	add(jsp2);
	descargar.setBounds(255, 360, 150, 50);
	add(descargar);

    }

    public void oyentes() {
	listaTareas.addListSelectionListener(new ListSelectionListener() {

	    @Override
	    public void valueChanged(ListSelectionEvent e) {
		JList<Tarea> j = (JList<Tarea>) e.getSource();
		Tarea t = j.getSelectedValue();
		calificacion.setText("CALIFICACION: " + t.getNota());
		String comentarioFormateado = "";
		String comentarioCadena = t.getComentarioProfesor();
		for (int i = 0; i < comentarioCadena.length(); i++) {
		    if (i % 50 == 0) {
			comentarioFormateado += "\n";
		    }
		    comentarioFormateado += comentarioCadena.charAt(i);
		}
		comentario.setText(comentarioFormateado);
	    }
	});

	descargar.addActionListener(new ActionListener() {

	    @Override
	    public void actionPerformed(ActionEvent e) {
		Tarea t = listaTareas.getSelectedValue();
		String url = t.getUrl();
		try {
		    bt.descargarFichero(url);
		} catch (IOException e1) {
		    JOptionPane.showMessageDialog(getContentPane(),
			    "ERROR DE ENTRADA SALIDA", "ALERTA",
			    JOptionPane.ERROR_MESSAGE);
		}
	    }
	});

    }

    public void propiedades() {
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setResizable(false);
	setVisible(true);

    }

}

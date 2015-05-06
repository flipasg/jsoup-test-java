/**
 *
 */
package gui;

import informacion.Informacion;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * @author Ikk
 * @date 6/5/2015
 *
 */
public class DialogoInicioSesion extends JDialog implements ActionListener {
    private JLabel eUsuario, eContrasenia;
    private JPasswordField cContrasenia;
    private JTextField cUsuario;
    private JButton loguear, cancelar;

    /**
     * Constructor de la clase DialogoInicioSesion
     */
    public DialogoInicioSesion() {
	iniciar();
    }

    public void iniciar() {
	instanciar();
	configurar();
	aniadir();
	oyentes();
	propiedades();
    }

    public void instanciar() {
	eUsuario = new JLabel("USUARIO: ");
	eContrasenia = new JLabel("CONTRASEÑA: ");
	cUsuario = new JTextField(10);
	cContrasenia = new JPasswordField(10);
	loguear = new JButton("LOGUEAR");
	cancelar = new JButton("CANCELAR");
    }

    public void configurar() {
	setTitle("INICIO DE SESION");
	setLayout(new GridBagLayout());
	setSize(new Dimension(250, 150));
    }

    public void aniadir() {
	JPanel panelBotones = new JPanel();
	panelBotones.add(loguear);
	panelBotones.add(cancelar);
	panelBotones.setLayout(new FlowLayout(FlowLayout.RIGHT));
	GridBagConstraints gbc = new GridBagConstraints();
	gbc.fill = GridBagConstraints.HORIZONTAL;
	gbc.insets = new Insets(10, 0, 0, 0);
	gbc.gridx = 0;
	gbc.gridy = 0;
	add(eUsuario, gbc);
	gbc.gridx = 1;
	add(cUsuario, gbc);
	gbc.insets = new Insets(5, 0, 0, 0);
	gbc.gridx = 0;
	gbc.gridy = 1;
	add(eContrasenia, gbc);
	gbc.gridx = 1;
	add(cContrasenia, gbc);
	gbc.gridx = 0;
	gbc.gridy = 2;
	gbc.insets = new Insets(20, 0, 0, 0);
	gbc.fill = GridBagConstraints.NONE;
	add(loguear, gbc);
	gbc.gridx = 1;
	add(cancelar, gbc);
    }

    public void oyentes() {
	loguear.addActionListener(this);
	cancelar.addActionListener(this);
    }

    public void propiedades() {
	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	setResizable(false);
	setVisible(true);
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
	if (loguear == e.getSource()) {
	    String usuario = cUsuario.getText();
	    String contrasenia = cContrasenia.getText();
	    if (usuario.equals("") || contrasenia.equals("")) {
		JOptionPane.showMessageDialog(getContentPane(),
			"DEBE INTRODUCIR EL USUARIO Y LA CONTRASEÑA", "ALERTA",
			JOptionPane.ERROR_MESSAGE);
	    } else {
		Informacion info = new Informacion(usuario, contrasenia);
		try {
		    new VentanaTareas("TAREAS", info);
		} catch (IOException e1) {
		    JOptionPane.showMessageDialog(getContentPane(),
			    "ERROR DE ENTRADA SALIDA", "ALERTA",
			    JOptionPane.ERROR_MESSAGE);
		}
	    }
	} else {
	    dispose();
	}
    }

}

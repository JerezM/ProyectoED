package GUI;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import Controlador.Logica;
import TDAGrafo.InvalidEdgeException;
import TDAGrafo.InvalidVertexException;
import TDAMapeo.InvalidKeyException;
import javax.swing.JTextArea;

/**
 * GUI del Proyecto para examen libre(23-08-2019)
 * @author Jerez, M.
 */

public class App {

	private JFrame frame;
	private Logica logica;
	private JTextArea textAreaMapeo;
	private JTextArea textAreaGrafo;
	private JTextArea textAreaAcciones;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textAreaMapeo = new JTextArea();
		textAreaMapeo.setEnabled(false);
		textAreaMapeo.setEditable(false);
		textAreaMapeo.setBounds(270, 45, 304, 57);
		JScrollPane scrollMapeo= new JScrollPane(textAreaMapeo);
		scrollMapeo.setEnabled(false);
		scrollMapeo.setLocation(270, 45);
		scrollMapeo.setSize(304, 91);
        scrollMapeo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scrollMapeo); 
		
		textAreaGrafo = new JTextArea();
		textAreaGrafo.setEnabled(false);
		textAreaGrafo.setEditable(false);
		textAreaGrafo.setBounds(280, 221, 294, 57);
		JScrollPane scrollGrafo= new JScrollPane(textAreaGrafo);
		scrollGrafo.setEnabled(false);
		scrollGrafo.setLocation(280, 221);
		scrollGrafo.setSize(294, 91);
		scrollGrafo.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scrollGrafo);
		
		textAreaAcciones = new JTextArea();
		textAreaAcciones.setEnabled(false);
		textAreaAcciones.setEditable(false);
		textAreaAcciones.setBounds(147, 20, 103, 292);
		JScrollPane scrollAcciones= new JScrollPane(textAreaAcciones);
		scrollAcciones.setEnabled(false);
		scrollAcciones.setLocation(10, 21);
		scrollAcciones.setSize(103, 291);
		scrollAcciones.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        frame.getContentPane().add(scrollAcciones);
		
		logica= new Logica();
		
		JButton btnInsertarEntrada = new JButton("Insertar entrada");
		btnInsertarEntrada.setBounds(131, 47, 129, 23);
		frame.getContentPane().add(btnInsertarEntrada);
		btnInsertarEntrada.setEnabled(false);
		btnInsertarEntrada.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				try {
					String clave= JOptionPane.showInputDialog("Ingrese la clave: ");
					Integer valor= null;
					if(clave!=null) {
						valor= Integer.parseInt( JOptionPane.showInputDialog("Ingrese el valor: ") );
					}	
					
					if(valor!=null) {
						logica.insertarEntrada(clave, valor);
						actualizarPantallaAcciones("M.put("+clave+", "+valor+");\n");
					}
					
				} 
				catch(InvalidKeyException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}	
				catch(NumberFormatException f) {
					JOptionPane.showMessageDialog(null, "Error: El valor ingresado es invaldo.");
				}
			}
		});
		
		JButton btnEliminarEntrada = new JButton("Eliminar entrada");
		btnEliminarEntrada.setBounds(131, 73, 129, 23);
		frame.getContentPane().add(btnEliminarEntrada);
		btnEliminarEntrada.setEnabled(false);
		btnEliminarEntrada.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				String clave= JOptionPane.showInputDialog("Ingrese la clave: ");
				if(clave!=null) {
					try {
						logica.eliminarEntrada(clave);
					} 
					catch(InvalidKeyException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				
					actualizarPantallaAcciones("M.remove("+clave+");\n");
				}
			}
		});
		
		JButton btnBuscarClave = new JButton("Buscar clave");
		btnBuscarClave.setBounds(131, 99, 129, 23);
		frame.getContentPane().add(btnBuscarClave);
		btnBuscarClave.setEnabled(false);
		btnBuscarClave.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				String clave= JOptionPane.showInputDialog("Ingrese la clave a buscar: ");
				if(clave!=null) {
					try {
						String valor= logica.buscarClave(clave);
						JOptionPane.showMessageDialog(null, valor);
					} catch(InvalidKeyException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				
					actualizarPantallaAcciones("M.get("+clave+");\n");
				}
			}
		});
		
		JButton btnListarEntradas = new JButton("Listar entradas");
		btnListarEntradas.setBounds(437, 141, 137, 23);
		frame.getContentPane().add(btnListarEntradas);
		btnListarEntradas.setEnabled(false);
		btnListarEntradas.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				String entradas= logica.listarEntradas();
				actualizarPantallaAcciones("M.entries();\n");
				actualizarPantallaMapeo(entradas);
			}
		});
		
		JButton btnEntradasPorBucket = new JButton("Entradas por bucket");
		btnEntradasPorBucket.setBounds(270, 141, 167, 23);
		frame.getContentPane().add(btnEntradasPorBucket);
		btnEntradasPorBucket.setEnabled(false);
		btnEntradasPorBucket.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				String cantPorBucket= logica.entradasBucket();
				actualizarPantallaAcciones("M.entriesBucket();\n");
				actualizarPantallaMapeo(cantPorBucket);
			}
		});
		
		JButton btnGenerarMapeo = new JButton("Generar Mapeo");
		btnGenerarMapeo.setBounds(270, 21, 137, 23);
		frame.getContentPane().add(btnGenerarMapeo);
		btnGenerarMapeo.setToolTipText("Genera un mapeo de cadena de caracteres en entero.");
		btnGenerarMapeo.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				logica.generarMapeo();
				
				btnGenerarMapeo.setEnabled(false);
				textAreaMapeo.setEnabled(true);
				textAreaAcciones.setEnabled(true);
				scrollMapeo.setEnabled(true);
				scrollAcciones.setEnabled(true);
				btnInsertarEntrada.setEnabled(true);
				btnEliminarEntrada.setEnabled(true);
				btnBuscarClave.setEnabled(true);
				btnBuscarClave.setToolTipText("Busca el valor asociado a una entrada a partir de su clave.");
				btnListarEntradas.setEnabled(true);
				btnEntradasPorBucket.setEnabled(true);
				btnEntradasPorBucket.setToolTipText("Lista cuantas entradas hay en cada bucket.");
				
			}
		});
		
		JButton btnInsertarVertice = new JButton("Insertar vertice");
		btnInsertarVertice.setBounds(141, 223, 129, 23);
		frame.getContentPane().add(btnInsertarVertice);
		btnInsertarVertice.setEnabled(false);
		btnInsertarVertice.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				String rotulo= JOptionPane.showInputDialog("Ingrese el rotulo del nuevo vertice: ");
				if(rotulo!=null) {
					logica.insertarVertice(rotulo);
					actualizarPantallaAcciones("G.insertVertex("+rotulo+");\n");
				}
			}
		});
		
		JButton btnInsertarArco = new JButton("Insertar arco");
		btnInsertarArco.setBounds(141, 249, 129, 23);
		frame.getContentPane().add(btnInsertarArco);
		btnInsertarArco.setEnabled(false);
		btnInsertarArco.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				try {
					
					String rotulo1= JOptionPane.showInputDialog("Ingrese el rotulo de uno de los vertices: ");
					String rotulo2= null;
					if(rotulo1!=null) {
						rotulo2= JOptionPane.showInputDialog("Ingrese el rotulo del otro vertice que unira el arco: ");
					}
					Double rotuloArco= null;
					if(rotulo2!=null) {
						rotuloArco= Double.parseDouble( JOptionPane.showInputDialog("Ingrese el rotulo del nuevo arco: ") );
					}
					if(rotuloArco!=null) {
						logica.insertarArco(rotulo1, rotulo2, rotuloArco);
						actualizarPantallaAcciones("G.insertEdge("+rotulo1+","+rotulo2+","+rotuloArco+");\n");
					}
				} 
				catch (InvalidVertexException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
				catch (NullPointerException e1) {
					
				}
				
			}
		});
		
		JButton btnListarVerticesY = new JButton("Listar vertices y arcos");
		btnListarVerticesY.setBounds(407, 316, 167, 23);
		frame.getContentPane().add(btnListarVerticesY);
		btnListarVerticesY.setEnabled(false);
		btnListarVerticesY.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				String vertexEdge= null;
				try {
					vertexEdge= logica.listarVerticesYArcos();
				} catch (InvalidEdgeException e) {
					e.printStackTrace();
				}
				
				actualizarPantallaAcciones("G.toString();\n");
				actualizarPantallaGrafo(vertexEdge);
			}
		});
		
		JButton btnHallarCamino = new JButton("Hallar camino");
		btnHallarCamino.setBounds(141, 275, 129, 23);
		frame.getContentPane().add(btnHallarCamino);
		btnHallarCamino.setEnabled(false);
		btnHallarCamino.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				String rotulo1= JOptionPane.showInputDialog("Ingrese el rotulo del vertice origen: ");
				String rotulo2= null;
				if(rotulo1!=null) {
					rotulo2= JOptionPane.showInputDialog("Ingrese el rotulo del vertice destino: ");
				}
				if(rotulo2!=null) {
					try {
						
						String camino= logica.hallarCamino(rotulo1, rotulo2);
						actualizarPantallaAcciones("G.hallarCamino"+rotulo1+","+rotulo2+");\n");
						JOptionPane.showMessageDialog(null, camino);
						
					} catch (InvalidVertexException e) {
						JOptionPane.showMessageDialog(null, e.getMessage());
					}
				}
			}
		});
		
		JButton btnGenerarGrafo = new JButton("Generar Grafo");
		btnGenerarGrafo.setBounds(280, 199, 137, 23);
		frame.getContentPane().add(btnGenerarGrafo);
		btnGenerarGrafo.addActionListener(new ActionListener (){
			public void actionPerformed(ActionEvent arg) {
				logica.generarGrafo();
				
				btnGenerarGrafo.setEnabled(false);
				textAreaGrafo.setEnabled(true);
				textAreaAcciones.setEnabled(true);
				scrollGrafo.setEnabled(true);
				scrollAcciones.setEnabled(true);
				btnInsertarVertice.setEnabled(true);
				btnInsertarArco.setEnabled(true);
				btnListarVerticesY.setEnabled(true);
				btnHallarCamino.setToolTipText("Busca el camino minimo entre dos vertices.");
				btnHallarCamino.setEnabled(true);
			}
		});
		
	}
	
	private void actualizarPantallaAcciones(String actualizacion) {
		String estadoActual= textAreaAcciones.getText();
		estadoActual += actualizacion;
		textAreaAcciones.setText(estadoActual);
	}
	
	private void actualizarPantallaMapeo(String actualizacion) {
		textAreaMapeo.setText(actualizacion);
	}
	
	private void actualizarPantallaGrafo(String actualizacion) {
		textAreaGrafo.setText(actualizacion);
	}
	
}

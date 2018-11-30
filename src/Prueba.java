import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.Box;

import java.awt.BorderLayout;

import javax.swing.JLabel;

import java.awt.Font;

import javax.swing.SwingConstants;
import javax.swing.JInternalFrame;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;

import java.awt.Window.Type;

import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JCheckBox;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;


public class Prueba {

	private JFrame frmConnorBonber;
	private JTextField txtArchivo;
	private JTextField txtDirectorio;
	private JTextField txtOinicial;
	private JTextField txtOfinal;
	private JTextField txtBytes;
	private JTextField txtRellenarCon;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Prueba window = new Prueba();
					window.frmConnorBonber.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void iniciarOffsetLocator(JComboBox mostrarOffsets, JTextField txtArchivo,JTextField txtDirectorio, JTextField offsetinical, JTextField offsetfinal, JTextField cantidadbytes, JTextField rellenarcon, Boolean rellenarTrueOrFalse, JComboBox extension) throws IOException{
		
		// GUARDAMOS TODOS LOS PARAMETROS EN VARIABLES
		
		String archivo = txtArchivo.getText(); 								// Nombre del archivo
		String directorio = txtDirectorio.getText(); 						// Directorio para guardar
		long offsetInicial = Long.parseLong(offsetinical.getText());			// Offset Inicial
		//long offsetFinal = Long.parseLong(offsetfinal.getText());				// Offset Final
		long cantidadBytes = Long.parseLong(cantidadbytes.getText());			// Cantidad de Bytes
		String rellenarCon = rellenarcon.getText();							// Rellenar con estos datos
		Boolean rellenarAleatorio = rellenarTrueOrFalse;					// Rellenar con datos aleatorios o no?
		String extensionArchivo = extension.getSelectedItem().toString();	// Extension elegida
		
		long posicionEmpezarAMeter = 0;
		
		File arch = new File(txtArchivo.getText());
		long offsetFinal = arch.length();
		offsetfinal.setText(Long.toString(offsetFinal));
		
		if(mostrarOffsets.isEnabled() && mostrarOffsets.getSelectedItem().toString()!=""){
			offsetInicial = Long.parseLong(mostrarOffsets.getSelectedItem().toString());
			offsetFinal = offsetInicial + cantidadBytes*10;
			offsetinical.setText(Long.toString(offsetInicial));
			offsetfinal.setText(Long.toString(offsetFinal));
			posicionEmpezarAMeter = (int) offsetInicial;
		}
		
		long offsetActual = offsetInicial;									// Inicializamos la variable del Offset Actual al Offset Inicial establecido por el usuario
		long cantidadRecorrerBucle = (offsetFinal-offsetInicial)/cantidadBytes;		
		cantidadRecorrerBucle++;
																			/* 
		
																			   Vamos a ver cuantas veces hará falta recorrer el bucle para crear X archivos
		
																			   Si por ejemplo son 350KB de archivo, son 350.000 Bytes, por lo que si ponemos 1000 Bytes,
																			   serian 350 archivos los que deberiamos crear.
																			   
																			   Por eso hacemos esta division, para crear un bucle de 1 hasta 350
																			   
																			*/
		String nombreNuevoExportar = "";									// De principio no le ponemos nombre al nuevo archivo
		
		
		// EMPEZAMOS A CODEAR

		long count;														// Variable para guardar la cantidad de archivos generados
		for(long i = 1; i <= cantidadRecorrerBucle; i++){					// Bucle para crear cantidad de archivos correspondientes entre los offsets y los Bytes
			
			File archivoReal = new File(archivo);
			FileInputStream fis = new FileInputStream(archivoReal);		// Abrimos el archivo a encryptar
			byte[] buffer = new byte[(int) cantidadBytes];						// Array de bytes para guardar los datos para pasar del archivo original al nuevo
			count = 0L;														/* Creamos una variable para saber cuantas veces hemos pasado por los archivo
																			   de esta forma compararemos si el count es igual a la i del for, y en caso
																			   de serlo, ese array ira lleno del valor del campo rellenarCon */
			
			int n = 0;														// A n le damos valor 0 que sera donde se recibira cada dato del archivo
			nombreNuevoExportar = offsetActual + "_" + cantidadBytes + extensionArchivo; 		// Cada vez que pase por este for, se generara un nombre. Ejemplo: 1000_1000 / 4500_100 / etc.
			FileOutputStream fos = new FileOutputStream(new File(directorio + nombreNuevoExportar)); // Creamos un archivo en el directorio indicado con el nombre generado
			
			System.out.println("Creando " + directorio + nombreNuevoExportar);
			System.out.println(buffer.length);
			System.out.println((int)cantidadBytes);
			int num = 0;
			
			/*
			 * 
			 * Haria falta tener varias variables que guarden los siguientes datos:
			 * 
			 * -Primera posicion del archivo
			 * -Cantidad de bytes que entran en el primer cacho de codigo
			 * 
			 * -Segunda posicion del archivo
			 * -Cantidad de bytes a rellenar con 0
			 * 
			 * -Tercera posicion del archivo
			 * -Cantidad de bytes que faltan para completar el archivo
			 * 
			 */
			
			int primerOffset = 0;
			int bytesBuenosInicio = 0;
			int primerOffsetCodear = (int) offsetActual;
			int bytesMalos = (int) cantidadBytes;
			int ultimoOffset = 0;
			int bytesBuenosFinal = 0;
			int pesoDelArchivoTotal = (int) (new File(archivo)).length();
			
			int tenemosQueRepetirlo = (int) ((offsetFinal-offsetInicial)/cantidadBytes);
			
			for(int x = 0; x < tenemosQueRepetirlo; x++){
				bytesBuenosInicio = primerOffsetCodear-1;									// Guardamos la cantidad de datos buenos
				ultimoOffset = primerOffsetCodear + bytesMalos;
				bytesBuenosFinal = pesoDelArchivoTotal - ultimoOffset;
				
				
				
				primerOffsetCodear += bytesMalos;
			}
			
			
			/*
			 * 
			 * 
			 * Pronbando variables aqui entre medias
			 * 
			 * 
			 */
			
			
			fis.read(buffer, 0, (int)cantidadBytes);
			fos.write(buffer, 0, n);
			
			while ((n = fis.read(buffer, 0, (int)cantidadBytes)) != -1) {		// Vamos recogiendo datos del archivo y mientras que sea diferente a -1 (final de arhivo) haremos lo siguiente
				if((int)count==num){
					byte[] bufferRellenado = rellenarBufferDe0((int)cantidadBytes);
					fos.write(bufferRellenado, 0, n);							// Escribiremos en el archivo nuevo lo que se guarda en el bufer
					System.out.println("Datos falsos en posicion del count " + count);
					count++;													// Sumamos al count el valor de n
				} else {
					fos.write(buffer, 0, n);									// Escribiremos en el archivo nuevo lo que se guarda en el bufer
					System.out.println("Rellenando con datos reales en posicion del count " + count);
					count++;													// Sumamos al count el valor de n
				}												
			}
			
			fos.close();													// Cerramos el archivo
			offsetActual+=cantidadBytes;
			fis.close();														// Cerramos el archivo original
		}
		/*
		 * 
		 * 	FINAL DEL PROGRAMA O METODO
		 * 
		 */
		mostrarOffsets.setEnabled(true);
	}
	
	private byte[] rellenarBufferDe0(int cantidadBytes) {
		byte[] bufferRellenado = new byte[cantidadBytes];
		/*for(int i = 0; i < bufferRellenado.length; i++){
			bufferRellenado[i] = 0;
		}*/
		return bufferRellenado;
	}

	/**
	 * Create the application.
	 */
	public Prueba() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmConnorBonber = new JFrame();
		frmConnorBonber.setResizable(false);
		frmConnorBonber.setTitle("Connor & Bonber - Offset Locator");
		frmConnorBonber.setBounds(100, 100, 530, 354);
		frmConnorBonber.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmConnorBonber.getContentPane().setLayout(null);
		
		JLabel lblTitulo = new JLabel("ConnorBonber - Offset Locator");
		lblTitulo.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblTitulo.setBounds(27, 11, 497, 56);
		frmConnorBonber.getContentPane().add(lblTitulo);
		
		txtArchivo = new JTextField("C:/test.exe");
		txtArchivo.setBounds(80, 77, 340, 20);
		frmConnorBonber.getContentPane().add(txtArchivo);
		txtArchivo.setColumns(10);
		
		txtDirectorio = new JTextField("C:/test/");
		txtDirectorio.setBounds(80, 105, 340, 20);
		frmConnorBonber.getContentPane().add(txtDirectorio);
		txtDirectorio.setColumns(10);
		
		JButton btnArchivo = new JButton("...");
		btnArchivo.setBounds(430, 76, 53, 23);
		frmConnorBonber.getContentPane().add(btnArchivo);
		
		JButton btnDirectorio = new JButton("...");
		btnDirectorio.setBounds(430, 104, 53, 23);
		frmConnorBonber.getContentPane().add(btnDirectorio);
		
		txtOinicial = new JTextField();
		txtOinicial.setHorizontalAlignment(SwingConstants.CENTER);
		txtOinicial.setText("1000");
		txtOinicial.setBounds(27, 159, 86, 20);
		frmConnorBonber.getContentPane().add(txtOinicial);
		txtOinicial.setColumns(10);
		
		JLabel lblOinical = new JLabel("Offset Inicial");
		lblOinical.setHorizontalAlignment(SwingConstants.CENTER);
		lblOinical.setBounds(27, 140, 86, 14);
		frmConnorBonber.getContentPane().add(lblOinical);
		
		JLabel lblOfinal = new JLabel("Offset Final");
		lblOfinal.setHorizontalAlignment(SwingConstants.CENTER);
		lblOfinal.setBounds(123, 140, 86, 14);
		frmConnorBonber.getContentPane().add(lblOfinal);
		
		txtOfinal = new JTextField();
		txtOfinal.setHorizontalAlignment(SwingConstants.CENTER);
		txtOfinal.setColumns(10);
		txtOfinal.setBounds(123, 159, 86, 20);
		frmConnorBonber.getContentPane().add(txtOfinal);
		
		JLabel lblBytes = new JLabel("Bytes");
		lblBytes.setHorizontalAlignment(SwingConstants.CENTER);
		lblBytes.setBounds(219, 140, 86, 14);
		frmConnorBonber.getContentPane().add(lblBytes);
		
		txtBytes = new JTextField();
		txtBytes.setHorizontalAlignment(SwingConstants.CENTER);
		txtBytes.setText("1000");
		txtBytes.setColumns(10);
		txtBytes.setBounds(219, 159, 86, 20);
		frmConnorBonber.getContentPane().add(txtBytes);
		
		JComboBox extension = new JComboBox();
		extension.setModel(new DefaultComboBoxModel(new String[] {".exe", ".dll"}));
		extension.setSelectedIndex(0);
		extension.setBounds(411, 159, 68, 20);
		frmConnorBonber.getContentPane().add(extension);
		
		JLabel lblExtensión = new JLabel("Extensi\u00F3n");
		lblExtensión.setHorizontalAlignment(SwingConstants.CENTER);
		lblExtensión.setBounds(411, 140, 68, 14);
		frmConnorBonber.getContentPane().add(lblExtensión);
		
		txtRellenarCon = new JTextField();
		txtRellenarCon.setText("00");
		txtRellenarCon.setHorizontalAlignment(SwingConstants.CENTER);
		txtRellenarCon.setColumns(10);
		txtRellenarCon.setBounds(315, 159, 86, 20);
		frmConnorBonber.getContentPane().add(txtRellenarCon);
		
		JLabel lblRellenarCon = new JLabel("Rellenar con: ");
		lblRellenarCon.setHorizontalAlignment(SwingConstants.CENTER);
		lblRellenarCon.setBounds(315, 140, 86, 14);
		frmConnorBonber.getContentPane().add(lblRellenarCon);
		
		
		JLabel lblArchivo = new JLabel("Archivo");
		lblArchivo.setHorizontalAlignment(SwingConstants.RIGHT);
		lblArchivo.setBounds(10, 80, 60, 14);
		frmConnorBonber.getContentPane().add(lblArchivo);
		
		JLabel lblDirectorio = new JLabel("Directorio");
		lblDirectorio.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDirectorio.setBounds(10, 108, 60, 14);
		frmConnorBonber.getContentPane().add(lblDirectorio);
		
		JLabel lblEstado = new JLabel("Esperando ordenes...");
		lblEstado.setHorizontalAlignment(SwingConstants.CENTER);
		lblEstado.setBounds(91, 306, 362, 14);
		frmConnorBonber.getContentPane().add(lblEstado);
		
		JCheckBox chkRellenar = new JCheckBox("Rellenar con caracteres aleatorios");
		chkRellenar.setHorizontalAlignment(SwingConstants.CENTER);
		chkRellenar.setBounds(27, 186, 446, 23);
		frmConnorBonber.getContentPane().add(chkRellenar);
		
		JComboBox mostrarOffsets = new JComboBox();
		mostrarOffsets.setEnabled(false);
		mostrarOffsets.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
            	
            	
                JComboBox comboBox = (JComboBox) event.getSource();
                Object selected = comboBox.getSelectedItem();
                if(selected.toString().equals("item1")){
                	// ACCION
                }
            }
        });
		mostrarOffsets.setBounds(150, 216, 221, 30);
		frmConnorBonber.getContentPane().add(mostrarOffsets);
		
		JButton btnActualizar = new JButton("Actualizar");
		btnActualizar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				File dir = new File(txtDirectorio.getText());
            	String[] ficheros = dir.list();
            	mostrarOffsets.removeAllItems();
            	if (ficheros == null)
            		  System.out.println("No hay ficheros en el directorio especificado");
            	else { 
            		for (int x=0; x<ficheros.length; x++){
            		    System.out.println("Se ha selecionado el archivo: " + ficheros[x]);
            		    mostrarOffsets.addItem(ficheros[x].split("_")[0]);
            		}
            	}
            	if(Integer.parseInt(txtBytes.getText())>1)
            		txtBytes.setText(Integer.toString(Integer.parseInt(txtBytes.getText())/10));
			}
		});
		btnActualizar.setBounds(381, 216, 102, 30);
		frmConnorBonber.getContentPane().add(btnActualizar);
		

		/*
		 * 
		 * 
		 * AQUI ESTA EL BOTON INICIAR
		 * PASA COMO PARAMETRO MUCHOS VALORES
		 * 
		 * 
		 */
		JButton btnNewButton = new JButton("Iniciar");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					iniciarOffsetLocator(mostrarOffsets,txtArchivo,txtDirectorio,txtOinicial,txtOfinal,txtBytes,txtRellenarCon, false, extension); // POR DEFECTO EN FALSE... NO SE COMO COGER EL VALOR CD chkRellenar
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 24));
		btnNewButton.setBounds(194, 257, 142, 38);
		frmConnorBonber.getContentPane().add(btnNewButton);
		
	}
}

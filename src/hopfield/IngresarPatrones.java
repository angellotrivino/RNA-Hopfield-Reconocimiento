/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hopfield;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 *
 * @author II SNH31D3R II
 */
public class IngresarPatrones extends javax.swing.JFrame {

    File myDir = new File(".");
    String myDirStr = "";
    
    String Ruta;
    String RutaMemoria;
    
    Color c;
    BufferedImage bi,ImageEnt;
    File img,mem;
    ImageIcon foto;
     Graphics2D g2,g3;
    
    int matA[][];
    int VA[][];
    int matWA[][];
    
    int matEnt[][];
    int VEnt[];
   
    int VS[];
    
    int matW[][];
    int I[][];
    int cont;
    
    String nombreI[];
    int  numEnt,pat=0;
     ///variables pintar
    int X=-1,Y=-1,x1=-1,y1=-1;
    boolean nnw=false;
    double rred;
    int boton;
    int WidthImagen,HeightImagen;
    /**
     * Creates new form IngresarPatrones
     */
    public IngresarPatrones() {
        initComponents(); crearBuferImagen();
        
        try {
            myDirStr = myDir.getCanonicalPath(); ///Guarda la ruta del equipo
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ///Inicializa ruta
        
     Ruta = myDirStr+"/imagenes_entrenamiento/";
     RutaMemoria = myDirStr+"/memoria/";
        
         canvas1.setBackground(Color.white);
        
       canvas1.addMouseListener(new MouseListener() {
            
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getButton()==3){
                    boton=3;
                    X=e.getX();
                    Y=e.getY();
                   
                    paintCanvas(canvas1.getGraphics());
                }
                if(e.getButton()==1){
                    boton=1;
                    X=e.getX();
                    Y=e.getY();
                    
                    paintCanvas(canvas1.getGraphics());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                X=-1;
                x1=-1;
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        canvas1.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseDragged(MouseEvent e) {
                if(boton==3){
                x1=e.getX();
                y1=e.getY();               
                paintCanvas(canvas1.getGraphics());
                X=x1;
                Y=y1;  
              }
              if(boton==1){
                  
                x1=e.getX();
                y1=e.getY();                
                paintCanvas(canvas1.getGraphics());
                X=x1;
                Y=y1;  
              }
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                
            }
        });
    }
    
    public void crearBuferImagen(){
    
        WidthImagen=canvas1.getWidth()/4;
        HeightImagen=canvas1.getHeight()/4;
        
        // Creamos una imagen con ese tamaño y con su correspondiente formato de
        // color
        ImageEnt = new BufferedImage(WidthImagen, HeightImagen, BufferedImage.TYPE_INT_RGB);
        g3 = (Graphics2D) ImageEnt.getGraphics();
         // rellenamos el fondo
        g3.setColor(Color.white);
        g3.fillRect(0, 0, WidthImagen, WidthImagen);
        
        g3.setStroke(new BasicStroke(13));
        g3.setColor(Color.BLACK);
        
    }
    
    public  void paintCanvas(Graphics g){
        //B_Analizar.setEnabled(true);
        g2=(Graphics2D) g;
        
        
        g2.setStroke(new BasicStroke(52));
        if(boton==1){
           // g1.setColor(Color.black);
            g2.setColor(Color.black);
        }
        else if(boton==3){
        g2.setColor(Color.white);
        }
        if(x1!=-1 && X!=-1){
            g2.drawLine(X, Y, x1,y1);
         //   g1.drawLine(X, Y, x1,y1);
        }
        
        if(nnw){
           g2.setBackground(Color.white);
            g2.dispose();
            //g1.dispose();
            nnw=false;
        }
      
        if(x1!=-1 && X!=-1){
            g3.drawLine(X/4, Y/4, x1/4,y1/4);
        }
        
        
    }
    
   public void entrenamiento(String nombreImagen){
       
		//Crea una copia del mismo tamaño que la imagen
		//BufferedImage biDestino = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration()
		//	   .createCompatibleImage(bi.getWidth(), bi.getHeight(), Transparency.OPAQUE);
		//Recorre las imagenes y obtiene el color de la imagen original y la almacena en el destino
       //////////////////////////////////////////////////////////////////////////////////
       
       
       
       img = new File(Ruta+nombreImagen);
       
        try {
            bi = ImageIO.read(img);
        } catch (IOException ex) {
            Logger.getLogger(Interfaz.class.getName()).log(Level.SEVERE, null, ex);
        }
          //////Identidad
        
        I= new int[bi.getWidth()*bi.getHeight()][bi.getWidth()*bi.getHeight()];
         
         for (int y=0; y < bi.getWidth()*bi.getHeight();y++){
			for (int x=0; x < bi.getWidth()*bi.getHeight();x++){
                         
                         if(x==y){
                         I[y][x]=1;
                         //System.out.print(I[y][x]);
                         }else{
                         I[y][x]=0;
                         //System.out.print(I[y][x]);
                         }
                            
                        }
                       // System.out.println();
                }
         
         //////Vector de entrada
              
 cont=0;
                    for (int y=0; y < bi.getHeight();y++){
			for (int x1=0; x1 < bi.getWidth();x1++){
				//Obtiene el color
			        c=new Color(bi.getRGB(x1, y));
                                
                                if(c.getRed()==255 && c.getGreen()==255 && c.getBlue()==255)
                                {
                                    //System.out.print(" ");
                                    VA[pat][cont]=-1;
                                    //System.out.print(mat[y][x]);
                                }else{
                               
                                    //System.out.print("1");
                                    VA[pat][cont]=1;
                                    
                                    //System.out.print(mat[y][x]);
                                
                                }
                                cont++;
			}
                        //System.out.println();
                    }                   
                    
                     /////valor de los pesos para el error/////////////////////////////////////////////////

      
              
                ///////// matWA = VA'*VA -I
                    
                   
               
                for (int y=0; y < bi.getWidth()*bi.getHeight();y++){
                    for (int x=0; x < bi.getWidth()*bi.getHeight();x++){
                        
                        matWA[y][x]=VA[pat][x]*VA[pat][y] - I[y][x];
                        //System.out.print(matWA[y][x]);
                    }
                    //System.out.println();
                }

                //////////////sumatoria W
               
                for (int y=0; y < bi.getWidth()*bi.getHeight();y++){
                    for (int x=0; x < bi.getWidth()*bi.getHeight();x++){
                    
                         matW[y][x] = matW[y][x]+matWA[y][x];
                  
                       //System.out.print(matW[y][x]);
                    
                    }
                   // System.out.println();
                }
                              
	}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        canvas1 = new java.awt.Canvas();
        L_Mensaje3 = new javax.swing.JLabel();
        L_Mensaje1 = new javax.swing.JLabel();
        B_AgregarPatron = new javax.swing.JButton();
        B_Atras = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("AGREGAR PATRON");

        L_Mensaje3.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        L_Mensaje3.setText("PATRON A EVALUAR");

        L_Mensaje1.setFont(new java.awt.Font("Comic Sans MS", 0, 18)); // NOI18N
        L_Mensaje1.setForeground(new java.awt.Color(0, 0, 255));
        L_Mensaje1.setText("AGREGAR PATRON DE APRENDIZAJE");

        B_AgregarPatron.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        B_AgregarPatron.setText("Agregar Patron y Entrenar");
        B_AgregarPatron.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_AgregarPatronActionPerformed(evt);
            }
        });

        B_Atras.setFont(new java.awt.Font("Comic Sans MS", 0, 14)); // NOI18N
        B_Atras.setText("Atras");
        B_Atras.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                B_AtrasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(L_Mensaje3)
                        .addGap(257, 257, 257))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(B_AgregarPatron)
                            .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(223, 223, 223))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(182, 182, 182)
                        .addComponent(L_Mensaje1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(B_Atras)))
                .addGap(0, 137, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(B_Atras)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(L_Mensaje1)
                .addGap(52, 52, 52)
                .addComponent(L_Mensaje3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19)
                .addComponent(B_AgregarPatron)
                .addContainerGap(73, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void B_AgregarPatronActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_AgregarPatronActionPerformed
        // TODO add your handling code here:
        cont=0;
        File directorio = new File(Ruta);
        
          ////////////Guardar imagen en el disco

        // Ejecutamos el metodo Dispose para finalizar
            g3.dispose();
        // Se crea un flujo de datos, en este caso será FileOutPutStream, aunque
        // podés utilizar cualquier otro.
            if (directorio.isDirectory()) {
			
			// obtenemos su contenido
			File[] ficheros = directorio.listFiles();
                        
			// y lo sacamos por pantalla
                        for (File fichero : ficheros){
                            if(fichero.getName().equals(cont+".png")){
                            cont++;
                            }
                        }
            }

        FileOutputStream out;
        try {
            out = new FileOutputStream(new File(Ruta+cont+".png"));

            // Se decodifica la imagen y se envía al flujo.
         
            ImageIO.write(ImageEnt, "jpeg", out);
            
            out.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        crearBuferImagen();
        
         nnw=true;
        //B_Analizar.setEnabled(false);
        canvas1.repaint();
        
        crearBuferImagen();

        cont=0;
           pat=0;
            
                
		
		// Si es un directorio
		if (directorio.isDirectory()) {
			
			// obtenemos su contenido
			File[] ficheros = directorio.listFiles();
                        
			// y lo sacamos por pantalla
                        for (File fichero : ficheros){
                            cont++;
                        }
                        
                         VA = new int[cont][50*50];
                         nombreI = new String[cont];
                        matWA=  new int[50*50][50*50];
                         matW = new int[50*50][50*50];
                         
                         
			for (File fichero : ficheros){
                            entrenamiento(fichero.getName());
                            nombreI[pat]=fichero.getName();
                            pat++;
                        }
				
		}   
    
                    try
{
    
//Crear un objeto File se encarga de crear o abrir acceso a un archivo que se especifica en su constructor
File archivo=new File(RutaMemoria+"aprendizaje.txt");
if(archivo.exists()){
archivo.delete();
}
//Crear objeto FileWriter que sera el que nos ayude a escribir sobre archivo
FileWriter escribir=new FileWriter(archivo,true);
//Escribimos en el archivo con el metodo write 
 for (int y=0; y < bi.getWidth()*bi.getHeight();y++){
                    for (int x=0; x < bi.getWidth()*bi.getHeight();x++){
                        
                  escribir.write(String.valueOf(matW[x][y]));
                  
                    }
                }


//Cerramos la conexion
escribir.close();
}

//Si existe un problema al escribir cae aqui
catch(Exception e)
{
System.out.println("Error al escribir");
}
    
    }//GEN-LAST:event_B_AgregarPatronActionPerformed

    private void B_AtrasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_B_AtrasActionPerformed
        // TODO add your handling code here:
         Interfaz ver = new Interfaz();
         ver.setLocation(300, 0);
         ver.setVisible(true);
         this.dispose();
    }//GEN-LAST:event_B_AtrasActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(IngresarPatrones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IngresarPatrones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IngresarPatrones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IngresarPatrones.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IngresarPatrones().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton B_AgregarPatron;
    private javax.swing.JButton B_Atras;
    private javax.swing.JLabel L_Mensaje1;
    private javax.swing.JLabel L_Mensaje3;
    private java.awt.Canvas canvas1;
    // End of variables declaration//GEN-END:variables
}

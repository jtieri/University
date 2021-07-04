/*

 */

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JFrame;

/**

 */
public class Hw3 implements KeyListener, ComponentListener,
        MouseListener, MouseMotionListener
{
   private boolean doDebug = true;       
   public FrameBufferPanel fbp;
   public Scene scene;



   /**
    This constructor instantiates the Scene object
    and initializes it with appropriate geometry.
    */
   public Hw3() {
      // Create the Scene object that we shall render
      scene = new Scene();

      // Create several Model objects.
      scene.addModel(new Square(1));
      scene.addModel(new Square(2));
      scene.addModel(new Square(3));
      scene.addModel(new Circle(3, 4));
      scene.addModel(new Circle(3, 64));

      // Give each model a useful name.
      scene.modelList.get(0).name = "Square_1";
      scene.modelList.get(1).name = "Square_2";
      scene.modelList.get(2).name = "Square_3";
      scene.modelList.get(3).name = "Diamond";
      scene.modelList.get(4).name = "Circle";

      // Push the models away from where the camera is.
      for (Model m : scene.modelList) {
         moveModel(m, 0, 0, -10);
      }

      // Give each model an initial position in the scene.
      moveModel(scene.modelList.get(0),  0,  0, 0);
      moveModel(scene.modelList.get(1), -5, -5, 0);
      moveModel(scene.modelList.get(2), +5, +5, 0);
      moveModel(scene.modelList.get(3), +5, -5, 0);
      moveModel(scene.modelList.get(4), -5, +5, 0);

      // Define initial dimensions for a FrameBuffer.
      final int fbWidth  = 1000;
      final int fbHeight = 1000;

      // Create a FrameBufferPanel that holds a FrameBuffer.
      fbp = new FrameBufferPanel(fbWidth, fbHeight);

      // Create a JFrame that will hold the FrameBufferPanel.
      final JFrame jf = new JFrame("Hw3");
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      //jf.setLocationByPlatform(true);

      // Place the FrameBufferPanel in the JFrame.
      jf.getContentPane().add(fbp, BorderLayout.CENTER);
      jf.pack();
      jf.setVisible(true);

      // Register the event listeners
      fbp.addMouseListener(this);            
      fbp.addMouseMotionListener(this);      
      jf.addKeyListener(this);               
      jf.addComponentListener(this);     

      // Render.
      FrameBuffer fb = fbp.getFrameBuffer();
      fb.clearFB();
      Pipeline.render(scene, fb);
      fbp.update();
   }

   // -----------------------------------------------------------------------------------------------------------------

   // Implement the KeyListener interface.
   @Override public void keyPressed(KeyEvent e){}
   @Override public void keyReleased(KeyEvent e){}
   @Override public void keyTyped(KeyEvent e) {
      if (doDebug) {
         System.out.printf("You have typed the key: %d \n The keys char value is: %c", e.getKeyCode(), e.getKeyChar());
         System.out.println();
      }

      final char c = e.getKeyChar();
      if ('h' == c) {
         print_help_message();
         return;
      }
      if ('d' == c) {
         Pipeline.debug = !Pipeline.debug;
      } else if ('i' == c) {
         doDebug = !doDebug;
      } else if ('c' == c) {
         Rasterize_Clip.doClipping = !Rasterize_Clip.doClipping;
         System.out.printf("Clipping is turned on: %s \n ", Rasterize_Clip.doClipping);
      } else if ('r' == c || 'R' == c) {
         int i, j = fbp.getWidth();
         int k = fbp.getHeight();

         if ('r' == c) {
            i = Math.min(j, k);
         } else {
            i = Math.max(j, k);
         }

         FrameBuffer fb = new FrameBuffer(i, i);
         fbp.setFrameBuffer(fb);
         ((JFrame)fbp.getTopLevelAncestor()).pack();
      }

      // Render
      FrameBuffer fb = fbp.getFrameBuffer();
      fb.clearFB();
      Pipeline.render(scene, fb);
      fbp.update();
   }

   // Implement the MouseListener interface.
   @Override public void mouseEntered(MouseEvent e){}
   @Override public void mouseExited(MouseEvent e) {
      if (doDebug) {
         System.out.printf("The mouse has exited at point: %s", e.getPoint());
         System.out.println();
      }

      
   }
   @Override public void mousePressed(MouseEvent e) {
      if(doDebug) {
         System.out.printf("Mouse Pressed at Screen Coordinates: (%d, %d) \n", e.getX(), e.getY());
      }
      int x = e.getX();
      int y = e.getY();

      int width = fbp.getWidth();
      int height = fbp.getHeight();
      double d1 = 20.0 / width;
      double d2 = 20.0 / height;

   }
   @Override public void mouseReleased(MouseEvent e) {
      if (doDebug) {
         System.out.printf("The mouse was released at point: %s", e.getPoint());
         System.out.println();
      }


   }
   @Override public void mouseClicked(MouseEvent e){}

   // Implement the MouseMotionListener interface.
   @Override public void mouseMoved(MouseEvent e){}
   @Override public void mouseDragged(MouseEvent e) {
      // Make sure the mouse isn't being dragged outside the window
      if (e.getX() > 0 && e.getX() < fbp.getWidth() && e.getY() > 0 && e.getY() < fbp.getHeight()) {

         // Render again.
         FrameBuffer fb = fbp.getFrameBuffer();
         fb.clearFB();
         Pipeline.render(scene, fb);
         fbp.update();


         if(doDebug) {
            //System.out.println("Mouse Dragged Coordinates: ("+e.getX()+","+e.getY()+") "+"Amount Moved: ("+deltaX+","+deltaY+")");
         }
      }
   }

   // Implement the ComponentListener interface.
   @Override public void componentMoved(ComponentEvent e){}
   @Override public void componentHidden(ComponentEvent e){}
   @Override public void componentShown(ComponentEvent e){}
   @Override public void componentResized(ComponentEvent e) {
      final int width = fbp.getWidth();
      final int height = fbp.getHeight();

      FrameBuffer frameBuffer = new FrameBuffer(width, height);
      fbp.setFrameBuffer(frameBuffer);
      Pipeline.render(scene, frameBuffer);
      fbp.update();
   }


   // -----------------------------------------------------------------------------------------------------------------

   /**
    Create an instance of this class which has
    the affect of creating the GUI application.
    */
   public static void main(String[] args) {
      print_help_message();

      // We need to call the program's constructor in the
      // Java GUI Event Dispatch Thread, otherwise we get a
      // race condition between the constructor (running in
      // the main() thread) and the very first ComponentEvent
      // (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
              () -> new Hw3()   // a lambda expression
      );
   }


   private static void moveModel(Model m, double deltaX,
                                 double deltaY,
                                 double deltaZ) {
      for (int i = 0; i < m.vertexList.size(); ++i) {
         final Vertex v = m.vertexList.get(i);
         m.vertexList.set(i, new Vertex(v.x + deltaX,
                 v.y + deltaY,
                 v.z + deltaZ));
      }
   }


   private static void print_help_message() {
      System.out.println("Use the 'd' key to toggle renderer debugging information on and off.");
      System.out.println("Use the 'i' key to toggle mouse debugging information on and off.");
      System.out.println("Use the 'c' key to toggle line clipping on and off.");
      System.out.println("Use the 'r/R' keys to reset the window's aspect ratio.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}

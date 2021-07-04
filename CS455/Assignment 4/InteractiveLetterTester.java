/*

*/

import renderer.scene.*;
import renderer.models.Axes2D;
import renderer.framebuffer.FrameBufferPanel;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.BorderLayout;

/**

*/
public class InteractiveLetterTester extends InteractiveAbstractClient_R7
{
   /**
      This constructor instantiates the Scene object
      and initializes it with appropriate geometry.
      Then this constructor instantiates the GUI.
   */
   public InteractiveLetterTester()
   {
      // Create the Scene object that we shall render.
      scene = new Scene();

      // Create a set of x and y axes.
      Model axes = new Axes2D(-2, +2, -2, +2, 8, 8);
      ModelShading.setColor(axes, Color.red);
      Position axes_p = new Position( axes );
      scene.addPosition( axes_p );
      // Push the axes away from where the camera is.
      axes_p.matrix = Matrix.translate(0, 0, -near);

      // Add a letter to the Scene.
      final Model model = new P(); // Use any Model you want here.
      modelArray.add( model );

      // Create a Position for the Model.
      final Position position = new Position(model);

      // Add the Position (and its Model) to the Scene.
      scene.addPosition(position);

      // Push the Position away from where the camera is.
      position.matrix = Matrix.translate(0, 0, pushback);

      // Give the letter a random color.
      ModelShading.setRandomColor( model );

      System.out.println(model);

      // Define initial dimensions for a FrameBuffer.
      final int width  = 1024;
      final int height = 1024;

      // Create a FrameBufferPanel that will hold a FrameBuffer.
      fbp = new FrameBufferPanel(width, height);
      fbp.getFrameBuffer().setBackgroundColorFB(Color.darkGray);
      fbp.getFrameBuffer().getViewport().setBackgroundColorVP(Color.black);

      // Create a JFrame that will hold the FrameBufferPanel.
      jf = new JFrame("Renderer 7");
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    //jf.setLocationByPlatform(true);
      // Place the FrameBufferPanel in the JFrame.
      jf.getContentPane().add(fbp, BorderLayout.CENTER);
      jf.pack();
      jf.setVisible(true);

      // Create event handler objects for events from the JFrame.
      jf.addKeyListener(this);
      jf.addComponentListener(this);
   }


   /**
      Create an instance of this class which has
      the affect of creating the GUI application.
   */
   public static void main(String[] args)
   {
      print_help_message();

      // We need to call the program's constructor in the
      // Java GUI Event Dispatch Thread, otherwise we get a
      // race condition between the constructor (running in
      // the main() thread) and the very first ComponentEvent
      // (running in the EDT).
      javax.swing.SwingUtilities.invokeLater(
         () -> new InteractiveLetterTester()
      );
   }
}

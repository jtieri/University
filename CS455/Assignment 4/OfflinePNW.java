 /*

*/

import renderer.scene.*;
import renderer.models.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;

/**

*/
public class OfflinePNW
{
   public static void main(String[] args)
   {
      // Create the Scene object that we shall render.
      final Scene scene = new Scene();

      final double right  = 4;
      final double left   = -right;
      final double top    = 3;
      final double bottom = -top;
      final double near = 3;
      scene.camera.projPerspective(left, right, bottom, top, near);

      // Create a set of x and y axes.
      Model axes = new Axes2D(-2, +2, -2, +4, 8, 12);
      ModelShading.setColor(axes, Color.red);
      Position axes_p = new Position( axes );
      scene.addPosition( axes_p );
      // Push the axes away from where the camera is.
      axes_p.matrix = Matrix.translate(0, 0, -near);

      // Add the letters to the Scene.
      scene.addPosition(new Position( new P() ),
                        new Position( new N() ),
                        new Position( new W() ));

      // Give the letters random colors.
      ModelShading.setRandomColor(scene.getPosition(1).model); // P
      ModelShading.setRandomColor(scene.getPosition(2).model); // N
      ModelShading.setRandomColor(scene.getPosition(3).model); // W

      // Create a FrameBuffer to render our scene into.
      int width  = 1200;
      int height =  900;
      FrameBuffer fb = new FrameBuffer(width, height);

      // Create the animation frames.
      for (int i = 0; i < 360; i++)
      {
         // Push the letters away from the camera.
         scene.getPosition(1).matrix = Matrix.translate(0, 0, -near); // P
         scene.getPosition(2).matrix = Matrix.translate(0, 0, -near); // N
         scene.getPosition(3).matrix = Matrix.translate(0, 0, -near); // W

         // do P





         // do N





         // do W





         // Render again.
         fb.clearFB(Color.black);
         Pipeline.render(scene, fb);
         fb.dumpFB2File(String.format("PPM_PNW_Frame%03d.ppm", i));
      }
   }
}

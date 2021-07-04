/*

*/

import renderer.scene.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import java.awt.Color;
import javax.swing.JFrame;
import java.awt.event.KeyListener;
import java.awt.event.KeyEvent;
import java.awt.event.ComponentListener;
import java.awt.event.ComponentEvent;
import java.util.List;
import java.util.ArrayList;

/**
   This class holds all the client state information
   and implementations of the client's event handlers.
*/
public abstract class InteractiveAbstractClient_R7 implements KeyListener, ComponentListener
{
   protected boolean letterbox = false;
   protected double aspectRatio = 1.0;
   protected double near   =  1.0;
   protected double left   = -1.0;
   protected double right  =  1.0;
   protected double bottom = -1.0;
   protected double top    =  1.0;
   protected boolean showCamera = false;
   protected boolean cameraChanged = false;
   protected boolean showFBaspectRatio = false;

   protected boolean showMatrix = false;
   protected double pushback = -2.0;
   protected double[] xTranslation = {0.0};
   protected double[] yTranslation = {0.0};
   protected double[] zTranslation = {0.0};
   protected double[] xRotation = {0.0};
   protected double[] yRotation = {0.0};
   protected double[] zRotation = {0.0};
   protected double[] scale = {1.0};

   protected Scene scene = null;
   protected final List<Model> modelArray = new ArrayList<>();
   protected int currentModel = 0;

   protected JFrame jf = null;
   protected FrameBufferPanel fbp = null;


   // Implement the KeyListener interface.
   @Override public void keyPressed(KeyEvent e){}
   @Override public void keyReleased(KeyEvent e){}
   @Override public void keyTyped(KeyEvent e)
   {
      //System.out.println( e );

      final char c = e.getKeyChar();
      if ('h' == c)
      {
         print_help_message();
         return;
      }
      else if ('d' == c)
      {
         modelArray.get(currentModel).debug = ! modelArray.get(currentModel).debug;
         Clip.debug = ! Clip.debug;
      }
      else if ('D' == c)
      {
         RasterizeAntialias.debug = ! RasterizeAntialias.debug;
      }
      else if ('a' == c)
      {
         RasterizeAntialias.doAntialiasing = ! RasterizeAntialias.doAntialiasing;
         System.out.print("Anti-aliasing is turned ");
         System.out.println(RasterizeAntialias.doAntialiasing ? "On" : "Off");
      }
      else if ('g' == c)
      {
         RasterizeAntialias.doGamma = ! RasterizeAntialias.doGamma;
         System.out.print("Gamma correction is turned ");
         System.out.println(RasterizeAntialias.doGamma ? "On" : "Off");
      }
      else if ('p' == c)
      {
         scene.camera.perspective = ! scene.camera.perspective;
         final String p = scene.camera.perspective ? "perspective" : "orthographic";
         System.out.println("Using " + p + " projection");
         cameraChanged = true;
      }
      else if ('l' == c)
      {
         letterbox = ! letterbox;
         System.out.print("Letter boxing is turned ");
         System.out.println(letterbox ? "On" : "Off");
      }
      else if ('n' == c || 'N' == c)
      {
         // Move the camera's near plane.
         if ('n' == c)
         {
            near -= 0.01;
         }
         else
         {
            near += 0.01;
         }
         cameraChanged = true;
      }
      else if ('r' == c || 'R' == c)
      {
         // Change the aspect ratio of the camera's view rectangle.
         if ('r' == c)
         {
            aspectRatio -= 0.1;
         }
         else
         {
            aspectRatio += 0.1;
         }

         // Adjust right and left.
         // (Keep the vertical field-of-view fixed.)
         right =  top * aspectRatio;
         left  = -right;
         System.out.printf("Aspect ratio (of camera's image rectangle) = %.2f\n", aspectRatio);
         cameraChanged = true;
      }
      else if ('o' == c || 'O' == c)
      {
         // Change left, right, bottom, and top.
         // (Keep the aspect ratio fixed.)
         if ('o' == c)
         {
            left   += 0.1 * aspectRatio;
            right  -= 0.1 * aspectRatio;
            bottom += 0.1;
            top    -= 0.1;
         }
         else
         {
            left   -= 0.1 * aspectRatio;
            right  += 0.1 * aspectRatio;
            bottom -= 0.1;
            top    += 0.1;
         }
         cameraChanged = true;
      }
      else if ('f' == c)
      {
         showFBaspectRatio = ! showFBaspectRatio;
         if (showFBaspectRatio)
         {
            // Get the new size of the FrameBufferPanel.
            final int w = fbp.getWidth();
            final int h = fbp.getHeight();
            System.out.printf("Aspect ratio (of framebuffer) = %.2f\n", (double)w/(double)h);
         }
      }
      else if ('c' == c)
      {
         // Change the solid random color of the current model.
         ModelShading.setRandomColor(scene.getPosition(1).model);
      }
      else if ('C' == c)
      {
         // Change each color in the current model to a random color.
         ModelShading.setRandomColors(scene.getPosition(1).model);
      }
      else if ('e' == c && e.isAltDown())
      {
         // Change the random color of each vertex of the current model.
         ModelShading.setRandomVertexColors(scene.getPosition(1).model);
      }
      else if ('e' == c)
      {
         // Change the solid random color of each edge of the current model.
         ModelShading.setRandomLineSegmentColors(scene.getPosition(1).model);
      }
      else if ('E' == c)
      {
         // Change the random color of each end of each edge of the current model.
         ModelShading.setRainbowLineSegmentColors(scene.getPosition(1).model);
      }
      else if ('M' == c)
      {
         showCamera = ! showCamera;
         if (showCamera) cameraChanged = true;
      }
      else if ('m' == c)
      {
         showMatrix = ! showMatrix;
      }

      if ('='==c||'/'==c||'?'==c
        ||'s'==c||'x'==c||'y'==c||'z'==c||'u'==c||'v'==c||'w'==c
        ||'S'==c||'X'==c||'Y'==c||'Z'==c||'U'==c||'V'==c||'W'==c)
      {
         setTransformations(c);
      }

      if (showMatrix)
      {
         displayMatrix(c);
      }

      // Render again.
      setupViewing();
   }


   // A client program can override how transformations are preformed.
   protected void setTransformations(final char c)
   {
      if ('=' == c)
      {
         scale[0] = 1.0;
         xTranslation[0] = 0.0;
         yTranslation[0] = 0.0;
         zTranslation[0] = 0.0;
         xRotation[0] = 0.0;
         yRotation[0] = 0.0;
         zRotation[0] = 0.0;
      }
      else if ('s' == c) // Scale the model 10% smaller.
      {
         scale[0] /= 1.1;
      }
      else if ('S' == c) // Scale the model 10% larger.
      {
         scale[0] *= 1.1;
      }
      else if ('x' == c)
      {
         xTranslation[0] -= 0.1;
      }
      else if ('X' == c)
      {
         xTranslation[0] += 0.1;
      }
      else if ('y' == c)
      {
         yTranslation[0] -= 0.1;
      }
      else if ('Y' == c)
      {
         yTranslation[0] += 0.1;
      }
      else if ('z' == c)
      {
         zTranslation[0] -= 0.1;
      }
      else if ('Z' == c)
      {
         zTranslation[0] += 0.1;
      }
      else if ('u' == c)
      {
         xRotation[0] -= 2.0;
      }
      else if ('U' == c)
      {
         xRotation[0] += 2.0;
      }
      else if ('v' == c)
      {
         yRotation[0] -= 2.0;
      }
      else if ('V' == c)
      {
         yRotation[0] += 2.0;
      }
      else if ('w' == c)
      {
         zRotation[0] -= 2.0;
      }
      else if ('W' == c)
      {
         zRotation[0] += 2.0;
      }

      // Set the model-to-view transformation matrix.
      // The order of the transformations is very important!
      final Position model_p = scene.getPosition(1);
      // Push the model away from where the camera is.
      model_p.matrix = Matrix.translate(0, 0, pushback);
      // Move the model relative to its new position.
      model_p.matrix.mult( Matrix.translate(xTranslation[0],
                                            yTranslation[0],
                                            zTranslation[0]) );
      model_p.matrix.mult( Matrix.rotateX(xRotation[0]) );
      model_p.matrix.mult( Matrix.rotateY(yRotation[0]) );
      model_p.matrix.mult( Matrix.rotateZ(zRotation[0]) );
      model_p.matrix.mult( Matrix.scale(scale[0]) );
   }


   // A client program can override the printing of transformation information.
   protected void displayMatrix(final char c)
   {
      if ('m'==c||'='==c
        ||'s'==c||'x'==c||'y'==c||'z'==c||'u'==c||'v'==c||'w'==c
        ||'S'==c||'X'==c||'Y'==c||'Z'==c||'U'==c||'V'==c||'W'==c)
      {
         System.out.println("xRot = " + xRotation[0]
                        + ", yRot = " + yRotation[0]
                        + ", zRot = " + zRotation[0]);
         System.out.print( scene.getPosition(0).matrix );
      }
   }


   // Implement the ComponentListener interface.
   @Override public void componentMoved(ComponentEvent e){}
   @Override public void componentHidden(ComponentEvent e){}
   @Override public void componentShown(ComponentEvent e){}
   @Override public void componentResized(ComponentEvent e)
   {
      //System.out.println( e );
      /*
      System.out.printf("JFrame [w = %d, h = %d]: " +
                        "FrameBufferPanel [w = %d, h = %d].\n",
                        jf.getWidth(), jf.getHeight(),
                        fbp.getWidth(), fbp.getHeight());
      */
      // Get the new size of the FrameBufferPanel.
      final int w = fbp.getWidth();
      final int h = fbp.getHeight();

      // Create a new FrameBuffer that fits the FrameBufferPanel.
      final Color bg1 = fbp.getFrameBuffer().getBackgroundColorFB();
      final Color bg2 = fbp.getFrameBuffer().getViewport().getBackgroundColorVP();
      final FrameBuffer fb = new FrameBuffer(w, h, bg1);
      fb.vp.setBackgroundColorVP(bg2);
      fbp.setFrameBuffer(fb);

      if (showFBaspectRatio)
         System.out.printf("Aspect ratio (of framebuffer) = %.2f\n", (double)w/(double)h);

      // Render again.
      setupViewing();
   }


   // Get in one place the code to set up the viewport and the view volume.
   protected void setupViewing()
   {
      // Set up the camera's view volume.
      if (scene.camera.perspective)
      {
         scene.camera.projPerspective(left, right, bottom, top, near);
      }
      else
      {
         scene.camera.projOrtho(left, right, bottom, top);
      }

      if (showCamera && cameraChanged)
      {
         System.out.print( scene.camera );
         cameraChanged = false;
      }

      // Get the size of the FrameBuffer.
      final FrameBuffer fb = fbp.getFrameBuffer();
      final int w = fb.width;
      final int h = fb.height;
      // Create a viewport with the correct aspect ratio.
      if ( letterbox )
      {
         if ( aspectRatio <= w/(double)h )
         {
            final int width = (int)(h*aspectRatio);
            final int xOffset = (w - width)/2;
            fb.setViewport(xOffset, 0, width, h);
         }
         else
         {
            final int height = (int)(w/aspectRatio);
            final int yOffset = (h - height)/2;
            fb.setViewport(0, yOffset, w, height);
         }
         fb.clearFB();
         fb.vp.clearVP();
      }
      else // the viewport is the whole framebuffer
      {
         fb.setViewport();
         fb.vp.clearVP();
      }
      // Render again.
      Pipeline.render(scene, fb.vp);
      fbp.update();
   }


   protected static void print_help_message()
   {
      System.out.println("Use the 'd' key to toggle debugging information on and off for the current model.");
      System.out.println("Use the 'p' key to toggle between parallel and orthographic projection.");
      System.out.println("Use the x/X, y/Y, z/Z, keys to translate the model along the x, y, z axes.");
      System.out.println("Use the u/U, v/V, w/W, keys to rotate the model around the x, y, z axes.");
      System.out.println("Use the s/S keys to scale the size of the model.");
      System.out.println("Use the 'c' key to change the random solid model color.");
      System.out.println("Use the 'C' key to randomly change model's colors.");
    //System.out.println("Use the 'e' key to change the random vertex colors.");
      System.out.println("Use the 'e' key to change the random solid edge colors.");
      System.out.println("Use the 'E' key to change the random edge colors.");
      System.out.println("Use the 'a' key to toggle antialiasing on and off.");
      System.out.println("Use the 'g' key to toggle gamma correction on and off.");
      System.out.println("Use the n/N keys to move the camera's near plane.");
      System.out.println("Use the o/O keys to change the size of the camera's view rectangle.");
      System.out.println("Use the r/R keys to change the aspect ratio of the camera's view rectangle.");
      System.out.println("Use the 'f' key to toggle showing framebufer aspect ratio.");
      System.out.println("Use the 'l' key to toggle letterboxing on and off.");
      System.out.println("Use the 'M' key to toggle showing the Camera normalization matrix.");
      System.out.println("Use the 'm' key to toggle showing the Model transformation matrix.");
      System.out.println("Use the '=' key to reset the Model transformation matrix to the identity.");
      System.out.println("Use the 'h' key to redisplay this help message.");
   }
}

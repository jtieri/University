/*

*/

import renderer.scene.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

import javax.swing.plaf.synth.SynthIcon;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

/**

*/
public class Hw2_v2
{
   final static int WIDTH  = 800;
   final static int HEIGHT = 800;
   final static int LENGTH = 30;

   public static void main(String[] args)
   {
      // Turn on clipping in the rasterizer.
      Rasterize_Clip.doClipping = true;

      Scene scene = new Scene();

      scene.addModel( new P(), new N(), new W() );

      // Push the models away from where the camera is.
      for (Model m : scene.modelList)
      {
         for (int i = 0; i < m.vertexList.size(); ++i)
         {
            final Vertex v = m.vertexList.get(i);
            m.vertexList.set(i, new Vertex(v.x,
                                           v.y,
                                           v.z - 2));
         }
      }

      // Give each model an initial location.
      moveModel(scene.getModel(0), -1.6, 0); // P
      moveModel(scene.getModel(1), -0.5, 0); // N
      moveModel(scene.getModel(2),  0.6, 0); // W

      // Create an empty List of FrameBuffers.
      final LinkedList<FrameBuffer> fbList = new LinkedList<>();

      // Initialize the list of FrameBuffers and create the initial segment of frames.
      for (int i = 0; i < LENGTH; ++i)
      {
         // create a new FrameBuffer
         FrameBuffer tmpFB = new FrameBuffer(WIDTH, HEIGHT);

         // render the current Scene into the new FrameBuffer
         Pipeline.render(scene, tmpFB.vp);

         // add the new FrameBuffer at the front to the list
         fbList.addFirst(tmpFB);

         // save a post processed frame
         postProcess(fbList).dumpFB2File(String.format("PPM_Hw2_v2_Frame%03d.ppm", i));

         // update the Scene
         updateScene(scene, i);
      }

      // Now cycle through the list of FrameBuffers, keeping its length constant.
      for (int i = LENGTH; i <= 450; ++i)
      {
         // remove the oldest FrameBuffer from the tail of the list
         FrameBuffer oldFB = fbList.removeLast();
         oldFB.clearFB();

         // render the current Scene into the recycled FrameBuffer
         Pipeline.render(scene, oldFB.vp);

         // add the recycled FrameBuffer at the front of the list
         fbList.addFirst(oldFB);

         // save a post processed frame
         postProcess(fbList).dumpFB2File(String.format("PPM_Hw2_v2_Frame%03d.ppm", i));

         // update the Scene
         updateScene(scene, i);
      }

      // Empty out the list and create the last segment of frames.
      for (int i = 451; i < 450 + LENGTH; ++i)
      {
         // remove the oldest FrameBuffer from the list
         fbList.removeLast();

         // save a post processed frame
         postProcess(fbList).dumpFB2File(String.format("PPM_Hw2_v2_Frame%03d.ppm", i));
      }
   }


   private static void updateScene(Scene scene, int frameNumber)
   {
      // Move models up
      if (frameNumber < 50) {
         moveModels(scene, 0, .02);
      }

      // Move models right and down
      if (frameNumber >= 50 && frameNumber < 200) {
         moveModels(scene, .02, -.02);
      }

      // Move models left
      if (frameNumber >= 200 && frameNumber < 250) {
         moveModels(scene, -.02, 0);
      }

      // Move models left and up
      if (frameNumber >= 250 && frameNumber < 300) {
         moveModels(scene, -.02, .02);
      }

      // Move models left
      if (frameNumber >= 300 && frameNumber < 400) {
         moveModels(scene, -.02, 0);
      }

      // Move models right and up to be back in starting position
      if (frameNumber >= 400 && frameNumber < 450) {
         moveModels(scene, .02, .02);
      }
   }


   private static void moveModels(Scene scene, double deltaX, double deltaY)
   {
      for (Model model : scene.modelList)
      {
         moveModel(model, deltaX, deltaY);
      }
   }


   private static void moveModel(Model model, double deltaX, double deltaY)
   {
      for (int i = 0; i < model.vertexList.size(); ++i)
      {
         final Vertex v = model.vertexList.get(i);
         model.vertexList.set(i, new Vertex(v.x + deltaX,
                 v.y + deltaY,
                 v.z));
      }
   }


   /**
      Use the List of FrameBuffer objects to compute, and return,
      a new FrameBuffer object.
   */
   private static FrameBuffer postProcess(final LinkedList<FrameBuffer> fbList)
   {
      final FrameBuffer resultFB = new FrameBuffer(WIDTH, HEIGHT);

      // Iterate through the list of source framebuffers and copy
      // every white pixel from a source framebuffer into resultFB,
      // but reduce the brightness of the white pixel by an amount
      // proportional to how "old" the source frame is. Don't let any
      // "older" pixel overwrite a "newer" pixel already in resultFB.
      Iterator<FrameBuffer> listIterator = fbList.descendingIterator();
      float rVal = 0.0f;
      float gVal = 0.0f;
      float bVal = 0.0f;
      final int fadeFactor = fbList.size();

      // Iterate through the list of FrameBuffers
      while (listIterator.hasNext()) {
         FrameBuffer fb = listIterator.next();

         // Scan the selected FrameBuffer and when a non-black pixel is found, set that pixel in the result FrameBuffer
         for (int i = 0; i < fb.getHeightFB(); ++i) {
            for (int j = 0; j < fb.getWidthFB(); ++j) {
               Color testColor = fb.vp.getPixelVP(j, i);

               if (! testColor.equals(new Color(0, 0, 0))) {
                  resultFB.vp.setPixelVP(j, i, new Color(rVal, gVal, bVal));
               }
            }
         }

         rVal = (float) (rVal + (1.0/fadeFactor));
         gVal = (float) (gVal + ((1.0/fadeFactor)));
         bVal = (float) (bVal + ((1.0/fadeFactor)));
      }

      return resultFB;
   }
}

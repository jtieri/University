/*

*/

import renderer.scene.*;
import renderer.pipeline.*;
import renderer.framebuffer.*;

/**

*/
public class Hw2_v1
{
   final static int WIDTH  = 800;
   final static int HEIGHT = 800;

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

      final FrameBuffer fb = new FrameBuffer(WIDTH, HEIGHT);

      // Create the frames of an animation.
      for (int i = 0; i < 450; ++i)
      {
         fb.clearFB();
         Pipeline.render(scene, fb.vp);
         fb.dumpFB2File(String.format("PPM_Hw2_v1_Frame%03d.ppm", i));
         updateScene(scene, i);
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
}

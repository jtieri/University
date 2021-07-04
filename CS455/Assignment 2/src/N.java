/*

*/

import renderer.scene.*;

/**
   A two-dimensional model of the letter N.
*/
public class N extends Model
{
   /**
      The letter N.
   */
   public N()
   {
      super("N");

      addVertex(new Vertex(0.00, 0.00, 0.0),
                new Vertex(0.00, 1.00, 0.0),
                new Vertex(0.25, 1.00, 0.0),
                new Vertex(0.75, 0.50, 0.0),
                new Vertex(0.75, 1.00, 0.0),
                new Vertex(1.00, 1.00, 0.0),
                new Vertex(1.00, 0.00, 0.0),
                new Vertex(0.75, 0.00, 0.0),
                new Vertex(0.25, 0.50, 0.0),
                new Vertex(0.25, 0.00, 0.0));

      addLineSegment(new LineSegment(0, 1),
                     new LineSegment(1, 2),
                     new LineSegment(2, 3),
                     new LineSegment(3, 4),
                     new LineSegment(4, 5),
                     new LineSegment(5, 6),
                     new LineSegment(6, 7),
                     new LineSegment(7, 8),
                     new LineSegment(8, 9),
                     new LineSegment(9, 0));
   }
}

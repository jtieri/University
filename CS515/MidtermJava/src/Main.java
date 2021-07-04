class X {
    private int regularIntEnclosing;
    private static int staticIntEnclosing = 300;

    public class Y{
        private int m;
        private int n;
        public Y() {
            m = regularIntEnclosing;
            n = staticIntEnclosing;
        }
    };

    public X( int n ) { regularIntEnclosing = n; }
};

class Test {
    public static void main( String[] args ) {
        X x = new X( 100 );
        X.Y y = new X(10).new Y();
    }
};
class X {
    public void foo() {
        System.out.println("foo in X");
    }
    public X() {
        foo();
    }
}
    class Y extends X {
        public void foo(){
            System.out.println("foo in Y");
        }
        public Y() {}
        public static void  main(String[] args)
        {
            Y yobj = new Y();
        }
    }
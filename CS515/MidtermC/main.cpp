#include <iostream>

using namespace std;

class X {
    class Y{
        int m;
    public:
        Y( int mm ) { m = mm;
        }

        void printY(){ cout << "m of nested class object: "
                            << m << endl; };
    };

    Y* yptr;

public:
    X() { yptr = new Y( 100 ); }
    Y* get_yptr(){ return yptr; }
};

int main() {
    X x;
    x.get_yptr()->printY();
    X::Y y(2);
    return 0;
}

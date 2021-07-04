#include <iostream>

using namespace std;

class X {
    int* p;  // pointer to an array of integers
    int size;  // the size of the array
public:
    X() { p = nullptr; size = 0; }

    // dynamic allocate an array of size sz
    X( int* ptr, int sz ){
        size = sz;
        p = new int[sz];
        for (int i = 0; i < size; i++) {
            p[i] = ptr[i];
        }
    }

    // destructor
    ~X() {
        delete p;
    }

    //copy constructor
    X(X const &x) {
        size = x.size;
        p = new int[x.size];
        for (int i = 0; i < size; i++) {
            p[i] = x.p[i];
        }
    }

    //assignment operator
    X& operator = (const X &x) {
        size = x.size;
        for (int i = 0; i < size; i++) {
            p[i] = x.p[i];
        }
        return *this;
    }
};

class Y : public X {
    int n;
public:
    Y() {};
    Y( int* ptr, int sz, int nn ) : X( ptr, sz ), n( nn ) {}

    // copy constructor
    Y(Y const &y)  : X(y) {
        n = y.n;
    }

    // assignment operator
    Y& operator = (const Y &y) {
        X::operator=(y);
        n = y.n;
        return *this;
    }
};

int main() {
    int* ptr = new int[4];
    ptr[0] = 1;
    ptr[1] = 2;
    ptr[2] = 3;
    ptr[3] = 4;
    X x = X(ptr, sizeof(ptr));


}
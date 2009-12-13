/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package transformers;

/**
 *
 * @author szymon
 */
public class Matrix {

    public float a, b, c, d;

    public Matrix() {
        a = 1;
        b = 0;
        c = 0;
        d = 1;
    }

    public Matrix(float a, float b, float c, float d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    Matrix multiply(Matrix m) {
        return new Matrix(
                a * m.a + b * m.c,
                a * m.b + b * m.d,
                c * m.a + d * m.c,
                c * m.b + d * m.d);
    }
}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package transformers;

/**
 *
 * @author szymon
 */
public interface ToolModifier {
    Matrix getMatrix();
    void setNotifiedObject(ImageTransformer it);
}

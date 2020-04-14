package util;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;

import java.util.Objects;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/13 10:24
 */
public class PsiClassUtil {

    public static PsiField[] getAllFields(PsiClass psiClass){
        if(Objects.nonNull(psiClass)){
            return psiClass.getAllFields();
        }
        return new PsiField[0];
    }

}

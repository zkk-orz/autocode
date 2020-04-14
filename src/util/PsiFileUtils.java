package util;

import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiJavaFile;

import java.util.Objects;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/13 9:45
 */
public class PsiFileUtils {

    public static PsiJavaFile getJavaFile(PsiFile psiFile){
        if(Objects.nonNull(psiFile) && psiFile instanceof PsiJavaFile){
            return (PsiJavaFile)psiFile;
        }
        return null;
    }

    public static PsiClass getSinglePsiClass(PsiFile psiFile){
        PsiJavaFile psiJavaFile = PsiFileUtils.getJavaFile(psiFile);
        if(Objects.isNull(psiJavaFile)){
            return null;
        }
        PsiClass[] psiClasses = psiJavaFile.getClasses();
        if(psiClasses.length == 0){
            return null;
        }
        return psiClasses[0];
    }

}

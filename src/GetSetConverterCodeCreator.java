import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;

import java.util.Objects;

public class GetSetConverterCodeCreator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if(Objects.isNull(project)){
            return;
        }
        PsiElement psiElement = e.getData(CommonDataKeys.PSI_ELEMENT);

    }
}

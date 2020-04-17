import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import cu.DomainDialogForCSM;
import util.PsiFileUtils;

import java.util.Objects;

public class DomainToCSMCreator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if(Objects.isNull(project)){
            return;
        }
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        DomainDialogForCSM domainDialogForCSM = new DomainDialogForCSM(psiFile, project);
        domainDialogForCSM.show();
    }
}

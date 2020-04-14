import com.intellij.ide.util.TreeClassChooser;
import com.intellij.ide.util.TreeClassChooserFactory;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiFile;
import com.intellij.psi.search.GlobalSearchScope;
import gsc.GetSetConvertWrapper;
import util.PsiFileUtils;

import java.util.Objects;

public class GetSetConverterCodeCreator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if(Objects.isNull(project)){
            return;
        }
        GetSetConvertWrapper getSetConvertWrapper = createGetSetConvertWrapper(e);
        if(getSetConvertWrapper.isValid()){
            Messages.showMessageDialog(project, getSetConvertWrapper.createStatement(), "Test", null);
        }
    }

    private GetSetConvertWrapper createGetSetConvertWrapper(AnActionEvent e) {
        GetSetConvertWrapper getSetConvertWrapper = new GetSetConvertWrapper();
        PsiFile psiFile = e.getData(CommonDataKeys.PSI_FILE);
        PsiClass psiClass = PsiFileUtils.getSinglePsiClass(psiFile);
        getSetConvertWrapper.setSourceClass(psiClass);
        PsiClass chooseClass = getTargetClass(e);
        getSetConvertWrapper.setTargetClass(chooseClass);
        return getSetConvertWrapper;
    }

    private PsiClass getTargetClass(AnActionEvent e) {
        String title = "Select Target Class";
        Project project = e.getProject();
        if(Objects.isNull(project)){
            return null;
        }
        TreeClassChooserFactory factory = TreeClassChooserFactory.getInstance(project);
        GlobalSearchScope scope = GlobalSearchScope.allScope(project);;
        TreeClassChooser chooser = factory.createInheritanceClassChooser(title, scope, null, null);
        chooser.showDialog();
        return chooser.getSelected();
    }

}

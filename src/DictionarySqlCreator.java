import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import cu.DictionaryDialog;

import java.util.Objects;

public class DictionarySqlCreator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if(Objects.nonNull(project)){
            DictionaryDialog exceptionDialog = new DictionaryDialog(project);
            exceptionDialog.show();
        }
    }
}

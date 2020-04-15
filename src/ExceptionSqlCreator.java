import cf.ExceptionDialog;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.util.Objects;

public class ExceptionSqlCreator extends AnAction {

    @Override
    public void actionPerformed(AnActionEvent e) {
        Project project = e.getProject();
        if(Objects.nonNull(project)){
            ExceptionDialog exceptionDialog = new ExceptionDialog(project);
            exceptionDialog.show();
        }
    }
}

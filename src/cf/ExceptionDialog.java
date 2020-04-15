package cf;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/15 19:01
 */
public class ExceptionDialog extends DialogWrapper {

    public ExceptionDialog(@Nullable Project project) {
        super(project);
        init();
    }

    private String LINE_SEPARATOR = System.getProperty("line.separator");

    private final String sql = "INSERT INTO " + LINE_SEPARATOR +
            "`user_db`.`sys_exception`" + LINE_SEPARATOR +
            "(`code`, " + LINE_SEPARATOR +
            " `createtime`, " + LINE_SEPARATOR +
            "`modifytime`, " + LINE_SEPARATOR +
            "`fk_user_create`, " + LINE_SEPARATOR +
            "`fk_user_modify`, " + LINE_SEPARATOR +
            "`isvalid`) " + LINE_SEPARATOR +
            "VALUES " + LINE_SEPARATOR +
            "('##code##', " + LINE_SEPARATOR +
            "NOW(), " + LINE_SEPARATOR +
            "NOW()," + LINE_SEPARATOR +
            " 1, " + LINE_SEPARATOR +
            "1, " + LINE_SEPARATOR +
            "1);" + LINE_SEPARATOR +
            "INSERT INTO" + LINE_SEPARATOR +
            " `user_db`.`sys_exception_lang`" + LINE_SEPARATOR +
            "(`fk_sys_exception`, " + LINE_SEPARATOR +
            "`lang`, " + LINE_SEPARATOR +
            "`content`, " + LINE_SEPARATOR +
            "`createtime`, " + LINE_SEPARATOR +
            "`modifytime`, " + LINE_SEPARATOR +
            "`fk_user_create`, " + LINE_SEPARATOR +
            "`fk_user_modify`, " + LINE_SEPARATOR +
            "`isvalid`, " + LINE_SEPARATOR +
            "`remark`) " + LINE_SEPARATOR +
            "VALUES" + LINE_SEPARATOR +
            " ('##code##'," + LINE_SEPARATOR +
            " 'CHS', " + LINE_SEPARATOR +
            "'##msg##', " + LINE_SEPARATOR +
            "NOW(), " + LINE_SEPARATOR +
            "NOW(), " + LINE_SEPARATOR +
            "1, " + LINE_SEPARATOR +
            "1," + LINE_SEPARATOR +
            " 1," + LINE_SEPARATOR +
            " NULL);";

    private final JTextField code = new JTextField();
    private final JTextField msg = new JTextField();
    private final JTextArea textArea = new JTextArea();

    @Nullable
    @Override
    protected JComponent createNorthPanel() {
        JPanel north = new JPanel();
        north.add(textArea);
        return north;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JLabel codeLabel = new JLabel("Exception Code：");
        JLabel msgLabel = new JLabel("Exception Message：");
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(3, 2));
        center.add(codeLabel);
        center.add(code);
        center.add(msgLabel);
        center.add(msg);
        return center;
    }

    @Override
    protected JComponent createSouthPanel() {
        JPanel south = new JPanel();
        JButton submit = new JButton("提交");
        submit.setHorizontalAlignment(SwingConstants.CENTER);
        submit.setVerticalAlignment(SwingConstants.CENTER);
        south.add(submit);
        submit.addActionListener(e -> {
            String exceptionCode = code.getText();
            String exceptionMessage = msg.getText();
            textArea.setText(sql.replace("##code##", exceptionCode).replace("##msg##", exceptionMessage));
        });
        return south;
    }


}

package cu;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/16 9:51
 */
public class DictionaryDialog extends DialogWrapper {

    private String LINE_SEPARATOR = System.getProperty("line.separator");

    private final String sql = "\n" +
            "INSERT INTO `common_db`.`dictionarysys` (\n" +
            "\t`name`,\n" +
            "\t`key`,\n" +
            "\t`createtime`,\n" +
            "\t`modifytime`,\n" +
            "\t`fk_user_create`,\n" +
            "\t`fk_user_modify`,\n" +
            "\t`isvalid`\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t'##name##',\n" +
            "\t\t'##key##',\n" +
            "\t\tNOW(),\n" +
            "\t\tNOW(),\n" +
            "\t\t1,\n" +
            "\t\t1,\n" +
            "\t\t1\n" +
            "\t);\n" +
            "\n" +
            "\n" +
            "\n" +
            "INSERT INTO `common_db`.`dictionarysys_lang` (\n" +
            "\t`fk_dictionarysys`,\n" +
            "\t`lang`,\n" +
            "\t`value`,\n" +
            "\t`createtime`,\n" +
            "\t`modifytime`,\n" +
            "\t`fk_user_create`,\n" +
            "\t`fk_user_modify`,\n" +
            "\t`isvalid`\n" +
            ")\n" +
            "VALUES\n" +
            "\t(\n" +
            "\t\t(SELECT id FROM `common_db`.`dictionarysys` WHERE `name` = '##name##' AND `key` = '##key##'),\n" +
            "\t\t'CHS',\n" +
            "\t\t'##value##',\n" +
            "\t\tNOW(),\n" +
            "\t\tNOW(),\n" +
            "\t\t1,\n" +
            "\t\t1,\n" +
            "\t\t1\n" +
            "\t);";

    private final String nameReplace = "##name##";
    private final String keyReplace = "##key##";
    private final String valueReplace = "##value##";

    private final JTextField name = new JTextField();
    private final JTextField keyValue = new JTextField();
    private final JTextArea textArea = new JTextArea();

    public DictionaryDialog(@Nullable Project project) {
        super(project);
        init();
    }

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
        JLabel nameLabel = new JLabel("name：");
        JLabel keyValueLabel = new JLabel("key-value：");
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(2, 2));
        center.add(nameLabel);
        center.add(name);
        center.add(keyValueLabel);
        keyValue.setText("1-待确认;2-已确认");
        center.add(keyValue);
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
            String nameStr = name.getText();
            String keyValueStr = keyValue.getText();
            if(Objects.nonNull(keyValueStr) && Objects.nonNull(nameStr)
                && keyValueStr.length() > 0 && nameStr.length() > 0){
                String[] kvs = keyValueStr.split(";");
                StringBuilder sb = new StringBuilder();
                for(String kv : kvs){
                    String keyStr = kv.split("-")[0];
                    String valueStr = kv.split("-")[1];
                    sb.append(sql.replace(nameReplace, nameStr).replace(keyReplace, keyStr).replace(valueReplace, valueStr));
                    sb.append(LINE_SEPARATOR);
                }
                textArea.setText(sb.toString());
            }
        });
        return south;
    }

}

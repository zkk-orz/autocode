package dtcsm;

import javax.swing.*;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/16 15:02
 */
public class DomainToCSMJComponentRecord {

    private JLabel label = new JLabel();

    private JTextField textField = new JTextField();

    private JCheckBox query = new JCheckBox("查询");

    private JCheckBox create = new JCheckBox("创建");

    private JCheckBox delete = new JCheckBox("删除");

    private JCheckBox modify = new JCheckBox("修改");

    private JCheckBox statusChange = new JCheckBox("状态修改");

    public JLabel getLabel() {
        return label;
    }

    public JTextField getTextField() {
        return textField;
    }

    public JCheckBox getQuery() {
        return query;
    }

    public JCheckBox getCreate() {
        return create;
    }

    public JCheckBox getDelete() {
        return delete;
    }

    public JCheckBox getModify() {
        return modify;
    }

    public JCheckBox getStatusChange() {
        return statusChange;
    }

    public void addToComponent(JPanel center) {
        center.add(label);
        center.add(textField);
        center.add(query);
        center.add(create);
        center.add(delete);
        center.add(modify);
        center.add(statusChange);
    }
}

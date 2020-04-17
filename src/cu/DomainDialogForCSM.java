package cu;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ModuleRootManager;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VfsUtil;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.pom.java.LanguageLevel;
import com.intellij.psi.JavaDirectoryService;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiField;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiFileFactory;
import com.intellij.psi.PsiJavaFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.impl.PsiManagerImpl;
import com.intellij.psi.impl.file.JavaDirectoryServiceImpl;
import com.intellij.psi.impl.file.PsiJavaDirectoryImpl;
import com.intellij.psi.javadoc.PsiDocComment;
import com.intellij.refactoring.PackageWrapper;
import com.intellij.refactoring.move.moveClassesOrPackages.MoveClassesOrPackagesUtil;
import com.intellij.util.IncorrectOperationException;
import com.jetbrains.rd.util.Result;
import dtcsm.ControllerContentCreator;
import dtcsm.DomainToCSMJComponentRecord;
import dtcsm.FileContentResult;
import dtcsm.FileCreator;
import dtcsm.RequestCreateCondition;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.jps.model.java.JavaModuleSourceRootTypes;
import util.PsiFileUtils;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * TODO
 *
 * @author zkk
 * @since 2020/4/16 14:53
 */
public class DomainDialogForCSM extends DialogWrapper {

    private String LINE_SEPARATOR = System.getProperty("line.separator");

    private String FILE_SEPARATOR = System.getProperty("file.separator");

    private PsiFile psiFile;

    private Project project;

    private final Map<PsiField, DomainToCSMJComponentRecord> map = new HashMap<>();

    private JLabel packagePathLabel = new JLabel("包路径");
    private JTextField packagePathText = new JTextField();
    private JLabel classNameLabel = new JLabel("类名");
    private JTextField classNameText = new JTextField();
    private JLabel descriptionLabel = new JLabel();
    private JTextField descriptionText = new JTextField();
    private JLabel reLabel3 = new JLabel();

    public DomainDialogForCSM(PsiFile psiFile, @Nullable Project project) {
        this(project);
        this.project = project;
        this.psiFile = psiFile;
        init();
    }

    private DomainDialogForCSM(@Nullable Project project) {
        super(project);
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        JPanel center = new JPanel();
        PsiClass psiClass = PsiFileUtils.getSinglePsiClass(psiFile);
        if(Objects.nonNull(psiClass)){
            PsiField[] psiFields = psiClass.getFields();
            if(psiFields.length > 0){
                center.setLayout(new GridLayout(psiFields.length + 1, 7));
                center.add(packagePathLabel);
                center.add(packagePathText);
                center.add(classNameLabel);
                center.add(classNameText);
                center.add(descriptionLabel);
                center.add(descriptionText);
                center.add(reLabel3);
                for(PsiField psiField : psiFields){
                    DomainToCSMJComponentRecord record = new DomainToCSMJComponentRecord();
                    record.getLabel().setText(psiField.getName());
                    PsiDocComment psiDocComment = psiField.getDocComment();
                    if(Objects.nonNull(psiDocComment)){
                        String comment = psiDocComment.getText();
                        if(Objects.nonNull(comment) && comment.length() > 0) {
                            comment = comment.replace("/", "")
                                    .replace("*", "")
                                    .replace(" ", "")
                                    .replace(LINE_SEPARATOR, "");
                            record.getTextField().setText(comment);
                        }
                    }
                    map.put(psiField, record);
                    record.addToComponent(center);
                }
            }
        }
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
                createCSMFiles();
        });
        return south;
    }

    private void createCSMFiles(){
        if(Objects.nonNull(project)){
            String className = classNameText.getText();
            String description = descriptionText.getText();
            if(Objects.nonNull(className) && Objects.nonNull(description)
                && className.length() > 0 && description.length() > 0){
                PsiDirectory psiDirectory = createPsiDirectory();
                if(Objects.nonNull(psiDirectory)){
                    FileCreator fileCreator = new FileCreator(map);
                    List<FileContentResult> fileContentResults = fileCreator.getRequestContent(description, className);
                    if(Objects.nonNull(fileContentResults) && fileContentResults.size() > 0){
                        for(FileContentResult result : fileContentResults){
                            Messages.showMessageDialog(result.getContent(), result.getName(), null);
                            createFileInWriteCommandAction(psiDirectory, result.getContent(), result.getName());
                        }
                    }
                    //createClassOrInterface(psiDirectory, ControllerContentCreator.getContent(), className);
                }
            } else {
                Messages.showMessageDialog("名称与描述信息不能为空", "信息提示", null);
            }
        }
    }

    private void createFileInWriteCommandAction(PsiDirectory directory, String content, String fileName) {
        final String name = fileName + "." + StdFileTypes.JAVA.getDefaultExtension();
        PsiJavaFile psiJavaFile = (PsiJavaFile)PsiFileFactory.getInstance(project).createFileFromText(name, StdFileTypes.JAVA, content);
        PsiClass createdClass = psiJavaFile.getClasses()[0];
        String className = createdClass.getName();
        CodeStyleManager.getInstance(project).reformat(psiJavaFile);
        JavaDirectoryServiceImpl.checkCreateClassOrInterface(directory, className);
        final LanguageLevel ll = JavaDirectoryService.getInstance().getLanguageLevel(directory);
        final PsiJavaFile finalPsiJavaFile = (PsiJavaFile)psiJavaFile.setName(className + "." + StdFileTypes.JAVA.getDefaultExtension());
        Messages.showMessageDialog("添加文件", "Test", null);
        new WriteCommandAction.Simple(project, finalPsiJavaFile) {
            @Override
            protected void run() throws Throwable {
                directory.add(finalPsiJavaFile);
            }
        }.execute();
    }

    private PsiDirectory createPsiDirectory() {
        try {
            String basePath = psiFile.getVirtualFile().getPath();
            basePath = basePath.replace(".java", "/").toLowerCase();
            Messages.showMessageDialog(basePath, "路径", null);
            PsiManagerImpl psiManager = (PsiManagerImpl) PsiManagerImpl.getInstance(project);
            VirtualFile virtualFile1 = VfsUtil.createDirectoryIfMissing(basePath);
            return new PsiJavaDirectoryImpl(psiManager, virtualFile1);
        } catch (Exception e) {
            Messages.showMessageDialog(e.toString(), e.getMessage(), null);
            return null;
        }
    }

}

package cu;

import com.intellij.openapi.application.ReadAction;
import com.intellij.openapi.application.RunResult;
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
import java.io.File;
import java.io.FileWriter;
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

    private JLabel reLabel1 = new JLabel();
    private JLabel reLabel2 = new JLabel();
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
                center.add(reLabel1);
                center.add(reLabel2);
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
                    FileCreator fileCreator = new FileCreator(map, getPackagePath());
                    PsiClass psiClass = PsiFileUtils.getSinglePsiClass(psiFile);
                    if(Objects.nonNull(psiClass)){
                        fileCreator.setDomainClass(psiClass.getName());
                    }
                    fileCreator.setDomainImport(getDomainImport());
                    List<FileContentResult> fileContentResults = fileCreator.getFileContent(description, className);
                    if(Objects.nonNull(fileContentResults) && fileContentResults.size() > 0){
                        Messages.showMessageDialog("共生成文件" + fileContentResults.size() + "个" , "文件总数量", null);
                        for(FileContentResult result : fileContentResults){
                            createFileInWriteCommandAction(psiDirectory, result.getContent(), result.getName());
                            //createFileInWriteCommandActionByTwo(result.getContent(), result.getName());
                        }
                    }
                    //createClassOrInterface(psiDirectory, ControllerContentCreator.getContent(), className);
                }
            } else {
                Messages.showMessageDialog("名称与描述信息不能为空", "信息提示", null);
            }
        }
    }

    PsiDirectory psiDirectory = null;

    private void createFileInWriteCommandActionByTwo(String content, String fileName) {
        final String name = fileName + "." + StdFileTypes.JAVA.getDefaultExtension();
        PsiJavaFile psiJavaFile = (PsiJavaFile)PsiFileFactory.getInstance(project).createFileFromText(name, StdFileTypes.JAVA, content);
        PsiClass createdClass = psiJavaFile.getClasses()[0];
        String className = createdClass.getName();
        CodeStyleManager.getInstance(project).reformat(psiJavaFile);
        final PsiJavaFile finalPsiJavaFile = (PsiJavaFile)psiJavaFile.setName(className + "." + StdFileTypes.JAVA.getDefaultExtension());
        String basePath = psiFile.getVirtualFile().getPath();
        basePath = basePath.replace(".java", "/").toLowerCase();
//        try {
//            File dir = new File(basePath);
//            if(dir.exists()){
//                Messages.showMessageDialog(basePath, "创建路径", null);
//                dir.mkdir();
//            }
//            File file = new File(basePath + fileName + ".java");
//            if(file.exists()) {
//                Messages.showMessageDialog(file.getParent(), "创建路径", null);
//                file.getParentFile().mkdir();
//            }
//            if(file.createNewFile()){
//                Messages.showMessageDialog(content, "创建文件" + fileName, null);
//                try (FileWriter fw = new FileWriter(basePath + fileName + ".java")){
//                    fw.write(content);
//                    fw.flush();
//                }
//            }
//        } catch (Exception e){
//            Messages.showMessageDialog(e.getMessage(), fileName, null);
//        }
        Messages.showMessageDialog(finalPsiJavaFile.getText(), fileName, null);
        RunResult s = new WriteCommandAction.Simple(project, finalPsiJavaFile) {
            @Override
            protected void run() throws Throwable {
                if(Objects.isNull(psiDirectory)){
                    psiDirectory = createPsiDirectory();
                }
                if(Objects.nonNull(psiDirectory)){
                    JavaDirectoryServiceImpl.checkCreateClassOrInterface(psiDirectory, className);
                    psiDirectory.add(finalPsiJavaFile);
                }
            }
        }.execute();
        Messages.showMessageDialog(s.getResultObject().toString(), fileName, null);
    }

    private void createFileInWriteCommandAction(PsiDirectory directory, String content, String fileName) {
        Messages.showMessageDialog(content, fileName, null);
        final String name = fileName + "." + StdFileTypes.JAVA.getDefaultExtension();
        PsiJavaFile psiJavaFile = (PsiJavaFile)PsiFileFactory.getInstance(project).createFileFromText(name, StdFileTypes.JAVA, content);
        PsiClass createdClass = psiJavaFile.getClasses()[0];
        String className = createdClass.getName();
        CodeStyleManager.getInstance(project).reformat(psiJavaFile);
        //JavaDirectoryServiceImpl.checkCreateClassOrInterface(directory, className);
        final LanguageLevel ll = JavaDirectoryService.getInstance().getLanguageLevel(directory);
        final PsiJavaFile finalPsiJavaFile = (PsiJavaFile)psiJavaFile.setName(className + "." + StdFileTypes.JAVA.getDefaultExtension());
        new WriteCommandAction.Simple(project, finalPsiJavaFile) {
            @Override
            protected void run() throws Throwable {
                directory.add(finalPsiJavaFile);
            }
        }.execute();
    }

    private String getDomainImport(){
        String packagePath = psiFile.getVirtualFile().getPath();
        packagePath = packagePath.replace(".java", ";");
        packagePath = packagePath.substring(packagePath.indexOf("src.") + 4);
        return packagePath;
    }

    private String getPackagePath(){
        String packagePath = psiFile.getVirtualFile().getPath();
        packagePath = packagePath.replace(".java", "").toLowerCase();
        packagePath = packagePath.substring(packagePath.indexOf("src.") + 4);
        return packagePath;
    }

    private PsiDirectory createPsiDirectory() {
        try {
            String basePath = psiFile.getVirtualFile().getPath();
            basePath = basePath.replace(".java", "/").toLowerCase();
            PsiManagerImpl psiManager = (PsiManagerImpl) PsiManagerImpl.getInstance(project);
            VirtualFile virtualFile1 = VfsUtil.createDirectoryIfMissing(basePath);
            return new PsiJavaDirectoryImpl(psiManager, virtualFile1);
        } catch (Exception e) {
            Messages.showMessageDialog(e.toString(), e.getMessage(), null);
            return null;
        }
    }

}

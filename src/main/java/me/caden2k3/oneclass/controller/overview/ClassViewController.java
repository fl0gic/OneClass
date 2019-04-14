package me.caden2k3.oneclass.controller.overview;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.TreeTableColumn;
import lombok.Getter;
import lombok.Setter;
import me.caden2k3.infinitecampusapi.Student;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.FXMLChild;
import me.caden2k3.oneclass.model.DataManager;
import me.caden2k3.oneclass.model.Properties;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Caden Kriese
 *
 * Created on 2019-04-05.
 */
@FXMLChild(path = "overview/class-view.fxml")
public class ClassViewController extends Controller {
    @Getter @Setter ClassViewController instance;

    @FXML JFXTreeTableView<ClassEntry> classTable;
    @FXML JFXTreeTableColumn<ClassEntry, String> nameColumn;
    @FXML JFXTreeTableColumn<ClassEntry, String> teacherColumn;
    @FXML JFXTreeTableColumn<ClassEntry, String> gradeColumn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        instance = this;

        minHeight = 375;
        minWidth = 300;
        usePreviousSizes = false;
        title = "Class Overview";
        windowIcon = Properties.APPLICATION_ICON;

        setupCellValueFactory(nameColumn, ClassEntry::nameProperty);
        setupCellValueFactory(teacherColumn, ClassEntry::teacherProperty);
        setupCellValueFactory(gradeColumn, ClassEntry::gradeProperty);

        Student student = DataManager.getInstance().getCurrentStudent();

        ObservableList<ClassEntry> classes = FXCollections.observableArrayList();
        classes.addAll(student.getClassbooks().stream()
                .map(book ->
                        new ClassEntry(book.getCourseName(), book.getTeacherDisplay(), book.getGradePercentage() + "%"))
                .collect(Collectors.toList()));

        classTable.setRoot(new RecursiveTreeItem<>(classes, RecursiveTreeObject::getChildren));
    }

    @Override
    public void apply(Parent root) {
        super.apply(root);
    }

    private <T> void setupCellValueFactory(JFXTreeTableColumn<ClassEntry, T> column, Function<ClassEntry, ObservableValue<T>> mapper) {
        column.setCellValueFactory((TreeTableColumn.CellDataFeatures<ClassEntry, T> param) -> {
            if (column.validateValue(param))
                return mapper.apply(param.getValue().getValue());
            else
                return column.getComputedValue(param);
        });
    }

    static final class ClassEntry extends RecursiveTreeObject<ClassEntry> {
        final StringProperty name;
        final StringProperty teacher;
        final StringProperty grade;

        ClassEntry(String name, String teacher, String grade) {
            this.name = new SimpleStringProperty(name);
            this.teacher = new SimpleStringProperty(teacher);
            this.grade = new SimpleStringProperty(grade);
        }

        StringProperty nameProperty() {
            return name;
        }

        StringProperty teacherProperty() {
            return teacher;
        }

        StringProperty gradeProperty() {
            return grade;
        }
    }
}

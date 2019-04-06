package me.caden2k3.oneclass.controller.overview;

import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import lombok.Getter;
import lombok.Setter;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.controller.FXMLChild;
import me.caden2k3.oneclass.model.Properties;

import java.net.URL;
import java.util.ResourceBundle;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

        instance = this;

        minHeight = 375;
        minWidth = 300;
        usePreviousSizes = false;
        title = "Class Overview";
        windowIcon = Properties.APPLICATION_ICON;
    }

    @Override
    public void apply(Parent root) {
        super.apply(root);
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

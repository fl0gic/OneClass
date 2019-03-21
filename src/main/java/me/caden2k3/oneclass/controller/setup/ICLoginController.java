package me.caden2k3.oneclass.controller.setup;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import lombok.Getter;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.model.Properties;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Caden Kriese
 *
 * Created on 2019-02-12.
 */
public class ICLoginController extends Controller {
    @Getter private static ICLoginController instance;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        instance = this;

        //Configure window/stage settings.
        minHeight = 375;
        minWidth = 300;
        usePreviousSizes = false;
        title = "Login to Infinite Campus";
        windowIcon = new Image(OneClass.class.getResourceAsStream(Properties.IMAGE_PATH+"infinite-campus.png"));
    }

    @Override
    public void apply(Parent root) {
        super.apply(root);

    }
}

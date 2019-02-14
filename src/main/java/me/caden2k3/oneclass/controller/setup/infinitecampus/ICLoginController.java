package me.caden2k3.oneclass.controller.setup.infinitecampus;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
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
    @FXML private WebView webView;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);

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

        webView.getEngine().load("https://campus.dcsdk12.org/icprod/portal/icprod.jsp?status=login");

        //Platform.runLater(() -> webView.getEngine().load("https://campus.dcsdk12.org/icprod/portal/icprod.jsp?status=login"));
    }
}

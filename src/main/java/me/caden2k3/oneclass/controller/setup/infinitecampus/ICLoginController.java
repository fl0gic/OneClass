package me.caden2k3.oneclass.controller.setup.infinitecampus;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.classroom.ClassroomScopes;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.web.WebView;
import me.caden2k3.oneclass.OneClass;
import me.caden2k3.oneclass.controller.Controller;
import me.caden2k3.oneclass.model.Properties;
import me.caden2k3.oneclass.model.util.UtilLog;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @author Caden Kriese
 *
 * Created on 2019-02-12.
 */
public class ICLoginController extends Controller {
    private static final List<String> SCOPES = Collections.singletonList(ClassroomScopes.CLASSROOM_COURSES_READONLY);
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String APPLICATION_NAME = "Google Classroom API Java Quickstart";
    @FXML private WebView view;

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
        System.setProperty("sun.net.http.allowRestrictedHeaders", "true");

        try {
            final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            OneClass.getInstance().getFixedThreadPool().submit(() -> {
                try {
                    Credential credential = getCredentials(HTTP_TRANSPORT);
                    System.out.println("CREDENTIAL TOKEN = "+credential.getAccessToken());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

//            Classroom service = new Classroom.Builder(HTTP_TRANSPORT, JSON_FACTORY, credential)
//                    .setApplicationName(APPLICATION_NAME)
//                    .build();
//
//            UtilLog.debug("COURSES SIZE = " + service.courses().list().size());

//            Platform.runLater(() -> view.getEngine().load("https://campus.dcsdk12.org/icprod/portal/icprod.jsp"));
//            view.getEngine().setOnStatusChanged(event -> System.out.println("STATUS CHANGED : " + event.toString()));
        } catch (Exception ex) {
            UtilLog.error(ex);
        }
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = getClass().getResourceAsStream("/resources/config/google-creds.json");
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new File("tokens")))
                .setAccessType("offline")
                .build();

        Credential credential = flow.loadCredential("user");
        if (credential != null
                && (credential.getRefreshToken() != null ||
                credential.getExpiresInSeconds() == null ||
                credential.getExpiresInSeconds() > 60)) {
            return credential;
        }

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        String url = flow.newAuthorizationUrl().setRedirectUri(receiver.getRedirectUri()).build();

        //OneClass.getInstance().getFixedThreadPool().submit(() ->
        Platform.runLater(() -> view.getEngine().load(url));

        // receive authorization code and exchange it for an access token
        String code = receiver.waitForCode();
        TokenResponse response = flow.newTokenRequest(code).setRedirectUri(receiver.getRedirectUri()).execute();
        // store credential and return it
        return flow.createAndStoreCredential(response, "user");
    }
}

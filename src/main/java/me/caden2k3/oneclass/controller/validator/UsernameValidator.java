package me.caden2k3.oneclass.controller.validator;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextField;
import me.caden2k3.oneclass.model.DataManager;
import me.caden2k3.oneclass.model.user.User;

import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * @author Caden Kriese
 *
 * Created on 2019-02-09.
 */
public class UsernameValidator extends ValidatorBase {
    @Override
    protected void eval() {
        //Fixes messages not updating.
        if (srcControl.get() instanceof IFXValidatableControl)
            ((IFXValidatableControl) srcControl.get()).resetValidation();

        if (srcControl.get() instanceof TextField) {
            String username = ((TextField) srcControl.get()).getText();
            if (username.length() > 0) {
                if (!DataManager.getInstance().getUserList().stream().filter(Objects::nonNull)
                        .map(User::getUsername).collect(toList()).contains(username)) {
                    //TODO verify in DB that username is not taken.
                    hasErrors.set(false);
                    return;
                } else
                    setMessage("Username already taken!");
            } else
                setMessage("Please enter an username.");
        }

        hasErrors.set(true);
    }
}

package me.caden2k3.oneclass.controller.validator;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextField;

/**
 * @author Caden Kriese
 *
 * Created on 2019-02-09.
 */
public class EmailValidator extends ValidatorBase {
    @Override
    protected void eval() {
        //Fixes messages not updating.
        if (srcControl.get() instanceof IFXValidatableControl)
            ((IFXValidatableControl) srcControl.get()).resetValidation();

        if (srcControl.get() instanceof TextField) {
            String email = ((TextField) srcControl.get()).getText();
            if (email.length() > 0) {
                if (org.apache.commons.validator.routines.EmailValidator.getInstance().isValid((email))) {
                    hasErrors.set(false);
                    return;
                } else
                    setMessage("Invalid email address!");
            } else
                setMessage("Please enter a valid email address.");
        } else
            setMessage("Internal error occurred.");

        hasErrors.set(true);
    }
}

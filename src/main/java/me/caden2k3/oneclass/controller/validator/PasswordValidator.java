package me.caden2k3.oneclass.controller.validator;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.PasswordField;

/**
 * @author Caden Kriese
 *
 * Created on 2019-02-09.
 */
public class PasswordValidator extends ValidatorBase {
    @Override
    protected void eval() {
        //Fixes messages not updating.
        if (srcControl.get() instanceof IFXValidatableControl)
            ((IFXValidatableControl) srcControl.get()).resetValidation();

        //TODO Put back when v11.0 comes out
//        setIcon(GlyphsBuilder.create(FontAwesomeIconView.class)
//            .glyph(FontAwesomeIcon.WARNING)
//            .styleClass("error")
//            .build());

        if (srcControl.get() instanceof PasswordField) {
            String password = ((PasswordField) srcControl.get()).getText();
            if (password.length() >= 8) {
                if (!password.equals(password.toLowerCase())) {
                    if (!password.matches("[A-Za-z]*")) {
                        hasErrors.set(false);
                        return;
                    } else
                        setMessage("Must contain a number/symbol.");
                } else
                    setMessage("Must contain an uppercase character.");
            } else
                setMessage("Must be at least 8 characters.");
        }

        hasErrors.set(true);
    }
}

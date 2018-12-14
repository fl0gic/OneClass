package me.caden2k3.oneclass.controller.util;

import com.jfoenix.validation.base.ValidatorBase;
import javafx.scene.control.TextInputControl;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-14.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class PasswordValidator extends ValidatorBase {
  @Override protected void eval() {
    if (srcControl.get() instanceof TextInputControl) {
      String password = ((TextInputControl) srcControl.get()).getText();
      if (password.length() >= 8) {
        if (!password.equals(password.toLowerCase())) {
          if (!password.matches("[A-Za-z]*")) {
              hasErrors.set(false);
          } else
            invalid("Password must contain a number or symbol.");
        } else
          invalid("Password must contain an uppercase character.");
      } else
        invalid("Password must be at least 8 characters.");
    }
  }

  private void invalid(String string) {
    hasErrors.set(true);
    setMessage(string);

  }
}

package me.caden2k3.oneclass.controller.util;

import com.jfoenix.controls.base.IFXValidatableControl;
import com.jfoenix.validation.base.ValidatorBase;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Caden Kriese
 *
 * Created on 2018-12-14.
 *
 * This code is copyright © Caden Kriese 2018
 */
public class CustomValidator extends ValidatorBase {
  @Getter @Setter private ValidatorRunnable runnable;

  public CustomValidator(ValidatorRunnable runnable) {
    this.runnable = runnable;
  }

  @Override protected void eval() {
    if (srcControl.get() instanceof IFXValidatableControl)
      ((IFXValidatableControl) srcControl.get()).resetValidation();

    hasErrors.set(runnable.eval(this));
  }

  public static abstract class ValidatorRunnable {
    public abstract boolean eval(CustomValidator validator);
  }
}

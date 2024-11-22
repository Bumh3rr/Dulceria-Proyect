package modal;

import raven.modal.ModalDialog;
import raven.modal.option.BorderOption;
import raven.modal.option.Location;
import raven.modal.option.Option;

public class ConfigModal {

    public static Option getModelShowRigth() {
        Option createOption = ModalDialog.createOption();
        createOption.getLayoutOption().setSize(-1, 1f).setLocation(Location.TRAILING, Location.TOP)
                .setAnimateDistance(0.7f, 0);
        createOption
                .setCloseOnPressedEscape(true)
                .setBackgroundClickType(Option.BackgroundClickType.BLOCK)
                .setAnimationEnabled(true)
                .setOpacity(0.2f)
                .getBorderOption().setShadow(BorderOption.Shadow.DOUBLE_EXTRA_LARGE);
        return createOption;
    }

    public static Option getModelShowDefault() {
        return ModalDialog.createOption()
                .setCloseOnPressedEscape(true)
                .setBackgroundClickType(Option.BackgroundClickType.BLOCK)
                .setAnimationEnabled(true)
                .setOpacity(0.2f);
    }
    public static Option getModelShowModalPush() {
        return ModalDialog.createOption()
                .setCloseOnPressedEscape(false)
                .setBackgroundClickType(Option.BackgroundClickType.BLOCK)
                .setAnimationEnabled(false)
                .setOpacity(0.6f);
    }

}
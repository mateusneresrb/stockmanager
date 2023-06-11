package dev.mateusneres.stockmanager.utils;

import java.awt.*;
import java.util.Arrays;

public class IconUtil {

    public static void setIcon(Window window) {
        Image iconImage16x = Toolkit.getDefaultToolkit().getImage(IconUtil.class.getResource("/icons/icon_16.png"));
        Image iconImage24x = Toolkit.getDefaultToolkit().getImage(IconUtil.class.getResource("/icons/icon_24.png"));
        Image iconImage32x = Toolkit.getDefaultToolkit().getImage(IconUtil.class.getResource("/icons/icon_32.png"));
        Image iconImage64x = Toolkit.getDefaultToolkit().getImage(IconUtil.class.getResource("/icons/icon_64.png"));
        Image iconImage128x = Toolkit.getDefaultToolkit().getImage(IconUtil.class.getResource("/icons/icon_128.png"));
        Image iconImage256x = Toolkit.getDefaultToolkit().getImage(IconUtil.class.getResource("/icons/icon_256.png"));
        window.setIconImages(Arrays.asList(iconImage16x, iconImage24x, iconImage32x, iconImage64x, iconImage128x, iconImage256x));
    }

}

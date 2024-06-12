
package beans.vistas_resumen.util;

import java.text.DecimalFormat;

public abstract class UtilsResumenes {
    public static String formatDecimal(double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number);
    }
}

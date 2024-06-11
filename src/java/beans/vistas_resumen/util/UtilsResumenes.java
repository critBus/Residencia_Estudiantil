/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans.vistas_resumen.util;

import java.text.DecimalFormat;

/**
 *
 * @author Rene
 */
public abstract class UtilsResumenes {
    public static String formatDecimal(double number) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(number);
    }
}

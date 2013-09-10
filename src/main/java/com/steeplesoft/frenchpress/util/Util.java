/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.steeplesoft.frenchpress.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author jdlee
 */
public class Util {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss a");

    public static String formatDate(Date date) {
        return sdf.format(date);
    }

}

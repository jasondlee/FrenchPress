package com.steeplesoft.frenchpress.view.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FrenchPressUtil {
    public static String splitCamelCasedString(String original) {
        StringBuilder sb = new StringBuilder();

        // Find any leading word that begins with a lower case
        Pattern firstWord = Pattern.compile("^\\p{Lower}{1,}");
        Matcher m1 = firstWord.matcher(original);
        if (m1.find()) {
            String word = m1.group();
            sb.append(word.substring(0,1).toUpperCase()+word.substring(1)).append(" ");
        }

        // Find the rest of the words
        Pattern p = Pattern.compile("\\p{Upper}{1}\\p{Lower}{1,}");
        Matcher m = p.matcher(original);

        while (m.find()) {
            sb.append (m.group()).append(" ");
        }

        return sb.toString().trim();
    }
}
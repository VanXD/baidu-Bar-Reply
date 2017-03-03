package com.vanxd.baidubar.util;

import java.io.*;

/**
 * Created by 37343 on 2017-03-03.
 */
public class FileUtil {

    public static String textInLine(String filePath) {
        File f = new File(filePath);
        System.out.println(f.getAbsolutePath());
        StringBuilder str = new StringBuilder("");
        String strTmp = "";
        try {
            Reader in = new FileReader(f);
            BufferedReader bufr = new BufferedReader(in);
            while ((strTmp = bufr.readLine()) != null) {
                System.out.println(strTmp);
                str.append(strTmp);
            }
            bufr.close();
            in.close();
            Writer out = new FileWriter(f);
            BufferedWriter bufw = new BufferedWriter(out);
            bufw.write(str.toString());
            bufw.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return str.toString();
    }

}

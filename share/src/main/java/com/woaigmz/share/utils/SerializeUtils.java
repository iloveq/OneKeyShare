package com.woaigmz.share.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Created by haoran on 2018/8/3.
 */

public class SerializeUtils {
    private SerializeUtils() {
        throw new AssertionError();
    }

    public static Object deserialization(String filePath) {
        ObjectInputStream in = null;

        Object var3;
        try {
            in = new ObjectInputStream(new FileInputStream(filePath));
            Object o = in.readObject();
            in.close();
            var3 = o;
        } catch (FileNotFoundException var14) {
            throw new RuntimeException("FileNotFoundException occurred. ", var14);
        } catch (ClassNotFoundException var15) {
            throw new RuntimeException("ClassNotFoundException occurred. ", var15);
        } catch (IOException var16) {
            throw new RuntimeException("IOException occurred. ", var16);
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException var13) {
                    throw new RuntimeException("IOException occurred. ", var13);
                }
            }

        }

        return var3;
    }

    public static void serialization(String filePath, Object obj) {
        ObjectOutputStream out = null;

        try {
            out = new ObjectOutputStream(new FileOutputStream(filePath));
            out.writeObject(obj);
            out.close();
        } catch (FileNotFoundException var12) {
            throw new RuntimeException("FileNotFoundException occurred. ", var12);
        } catch (IOException var13) {
            throw new RuntimeException("IOException occurred. ", var13);
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException var11) {
                    throw new RuntimeException("IOException occurred. ", var11);
                }
            }

        }

    }
}

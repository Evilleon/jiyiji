package com.example.lenovo.jiyiji.Utils;

import java.util.Random;

public class Colors {
    public static String RandTone(){//随机色号
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;


        return R+G+B;
    }

    public static int RandColors(){

        Random myRandom=new Random();

        int ranColor = 0xff000000 | myRandom.nextInt(0x00ffffff);

        return ranColor;
    }
}

package com.msf.fuxi.xdiamond;

import com.msf.fuxi.xdiamond.init.XDiamondValueInit;


public class XDiamondUtil {

    public static String getResources(String key) {
        return XDiamondValueInit.map.get(key);
    }
}

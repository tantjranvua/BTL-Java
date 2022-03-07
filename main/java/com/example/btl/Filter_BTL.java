package com.example.btl_kien;
import java.io.File;
import java.io.FilenameFilter;

public class Filter_BTL implements FilenameFilter {
    String st;
    public Filter_BTL(String st){
        this.st = st;
    }
    public boolean accept (File Dir, String name){
        int ind = name.lastIndexOf(".");
        String ans;
        if(ind<=0||ind>=name.length()-1) {
            ans = name;
        }
        else {
            ans = name.substring(0,ind);
        }
        return ans.equals(st);
    }


}

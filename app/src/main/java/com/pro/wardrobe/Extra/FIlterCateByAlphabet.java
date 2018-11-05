package com.pro.wardrobe.Extra;

public class FIlterCateByAlphabet {

    String txt;
    String txt_id;
    int type;

    public FIlterCateByAlphabet(String txt, String txt_id, int type) {
        this.txt = txt;
        this.txt_id = txt_id;
        this.type = type;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getTxt_id() {
        return txt_id;
    }

    public void setTxt_id(String txt_id) {
        this.txt_id = txt_id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}

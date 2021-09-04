package com.baro.utils.parsing.MenuExtraToMenu;

import java.util.ArrayList;

public class MenuExtraToMenuList {
    public boolean result;
    public ArrayList<MenuExtraToMenu> extraByMenus;

    public MenuExtraToMenuList(boolean result, ArrayList<MenuExtraToMenu> extraByMenus) {
        this.result = result;
        this.extraByMenus = extraByMenus;
    }

    @Override
    public String toString() {
        return "MenuExtraToMenuList{" +
                "result=" + result +
                ", extraByMenus=" + extraByMenus +
                '}';
    }

    public boolean isResult() {
        return result;
    }

    public void setResult(boolean result) {
        this.result = result;
    }

    public ArrayList<MenuExtraToMenu> getExtraByMenus() {
        return extraByMenus;
    }

    public void setExtraByMenus(ArrayList<MenuExtraToMenu> extraByMenus) {
        this.extraByMenus = extraByMenus;
    }
}

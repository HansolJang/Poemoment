package com.jamsi.poemoment.http.json;

/**
 * Created by zipdoc on 2017. 5. 21..
 */

public abstract class JSBased {

    private boolean isExpand = false;
    private boolean isDisplay = false;
    private boolean isSelected = false;

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setDisplay(boolean isDisplay) {
        this.isDisplay = isDisplay;
    }

    public boolean isDisplay() {
        return isDisplay;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }
}

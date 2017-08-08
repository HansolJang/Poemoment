package com.jamsi.poemoment.http.json;

import java.util.List;

/**
 * Created by zipdoc on 2017. 5. 21..
 */

public class JSEmotionList {

    protected Integer total_count;
    protected List<JSEmotion> items;

    public Integer getTotal_count() {
        return total_count;
    }

    public List<JSEmotion> getItems() {
        return items;
    }

    public void setTotal_count(Integer total_count) {
        this.total_count = total_count;
    }

    public void setItems(List<JSEmotion> items) {
        this.items = items;
    }
}

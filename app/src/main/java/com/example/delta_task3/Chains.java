
package com.example.delta_task3;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Chains {

    @SerializedName("baby_trigger_item")
    @Expose
    private Object babyTriggerItem;
    @SerializedName("chain")
    @Expose
    private Chain chain;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Object getBabyTriggerItem() {
        return babyTriggerItem;
    }

    public void setBabyTriggerItem(Object babyTriggerItem) {
        this.babyTriggerItem = babyTriggerItem;
    }

    public Chain getChain() {
        return chain;
    }

    public void setChain(Chain chain) {
        this.chain = chain;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}


package com.example.delta_task3;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Types {

    @SerializedName("count")
    @Expose(deserialize = false)
    private Integer count;
    @SerializedName("next")
    @Expose(deserialize = false)
    private Object next;
    @SerializedName("previous")
    @Expose(deserialize = false)
    private Object previous;
    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Object getNext() {
        return next;
    }

    public void setNext(Object next) {
        this.next = next;
    }

    public Object getPrevious() {
        return previous;
    }

    public void setPrevious(Object previous) {
        this.previous = previous;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

}

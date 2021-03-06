package edu.njupt.feng.web.entity.common;


import edu.njupt.feng.web.entity.service.NodeServiceListItem;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResultInfoWithoutContent implements Serializable {

    private List<NodeServiceListItem> result;

    private Long costTime;

    public ResultInfoWithoutContent(){
        result = new ArrayList<>();
        costTime = 0L;
    }

    public List<NodeServiceListItem> getResult() {
        return result;
    }

    public void setResult(List<NodeServiceListItem> result) {
        this.result = result;
    }

    public Long getCostTime() {
        return costTime;
    }

    public void setCostTime(Long costTime) {
        this.costTime = costTime;
    }

    public void add(ResultInfoWithoutContent b){
        if(result == null){
            result = new ArrayList<>();
        }
        if (costTime == null){
            costTime = 0L;
        }
        if(b.getResult() != null){
            result.addAll(b.getResult());
            if (result != null) {
                result.sort(new Comparator<NodeServiceListItem>() {
                    @Override
                    public int compare(NodeServiceListItem o1, NodeServiceListItem o2) {
                        if (Float.valueOf(o1.getAttributes().get("search_sim")) < Float.valueOf(o2.getAttributes().get("search_sim")))
                            return 1;
                        else if (Float.valueOf(o1.getAttributes().get("search_sim")) > Float.valueOf(o2.getAttributes().get("search_sim")))
                            return -1;
                        else
                            return 0;
                    }
                });
                result=result.subList(0, Math.min(10,result.size()));
            }
        }
        if(b.getCostTime() != null){
            costTime += b.getCostTime();
        }
    }
}

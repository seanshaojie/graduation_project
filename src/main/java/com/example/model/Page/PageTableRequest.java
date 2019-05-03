package com.example.model.Page;

import java.io.Serializable;
import java.util.Map;

/**@ClassName
 *@Description:  分页用的实体类
 *@Data 2019/3/27
 *Author censhaojie
 */
public class PageTableRequest implements Serializable {
    private static final long serialVersionUID = -7576684356934488332L;
    private Integer offset;
    private Integer limit;
    private Map<String, Object> params;

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }
}

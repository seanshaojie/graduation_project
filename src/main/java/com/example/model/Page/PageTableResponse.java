package com.example.model.Page;

import java.io.Serializable;
import java.util.List;
/**@ClassName
 *@Description:  分页查询返回的实体
 *@Data 2019/3/27
 *Author censhaojie
 */
public class PageTableResponse implements Serializable {
    private static final long serialVersionUID = 6871420754858971790L;
    private Integer recordsTotal;
    private Integer recordsFiltered;
    private List<?> data;

    public PageTableResponse(Integer recordsTotal, Integer recordsFiltered, List<?> data) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public Integer getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(Integer recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public Integer getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(Integer recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}

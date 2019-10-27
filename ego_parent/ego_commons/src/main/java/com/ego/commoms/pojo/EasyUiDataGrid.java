package com.ego.commoms.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/6
 * @Description: com.ego.commoms.pojo
 * @version: 1.0
 */
public class EasyUiDataGrid implements Serializable {

    private List<?> rows;
    private long total;


    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }
}

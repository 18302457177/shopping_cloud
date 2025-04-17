package api.common;

import lombok.Data;

import java.util.List;

@Data
public class PageResult<T>{
    private Integer total;

    private Integer pageSize;

    private Integer pageNo;

    private List<T> data;
}

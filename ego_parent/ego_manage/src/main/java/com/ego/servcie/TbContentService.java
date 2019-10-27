package com.ego.servcie;

import com.ego.commoms.pojo.EasyUiDataGrid;
import com.ego.commoms.pojo.EgoResult;
import com.ego.pojo.TbContent;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/9
 * @Description: com.ego.servcie
 * @version: 1.0
 */
public interface TbContentService {

    EasyUiDataGrid findByCategoryid(Long categoryId, int page , int rows);

    EgoResult save(TbContent tbContent);

    EgoResult change(TbContent tbContent);

    EgoResult delete(Long[] ids);

}

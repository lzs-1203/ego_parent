package com.ego.servcie;

import com.ego.commoms.pojo.EasyUITree;
import com.ego.commoms.pojo.EgoResult;

import java.util.List;

/**
 * @Auther: Constant.Wang
 * @Date: 2019/8/8
 * @Description: com.ego.servcie
 * @version: 1.0
 */
public interface TbContentCategoryService {

    List<EasyUITree>  findAllByPid(Long pid);

    EgoResult saveContentCategory(Long parentId, String name);

    EgoResult updateContentCategory( Long id, String name);

    EgoResult deleteContentCategory(Long id);

}

package com.ego.manage.service;

import com.ego.commons.pojo.EasyUITreeNode;

import java.util.List;

public interface TbItemCatService {
    List<EasyUITreeNode> selectByPid(Long pid);
}

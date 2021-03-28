package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.vo.DepartmentVO;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/16 10:38
 */
public interface IDepartmentTreeService {

    public List<DepartmentVO> buildTree(List<DepartmentVO> departmentVOList);

    public void buildIdList(Long deptId, List<Long> result);


}

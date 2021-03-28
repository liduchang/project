package cn.javaboy.admin.dao.sys;

import cn.javaboy.admin.common.domain.sys.entity.DepartmentEntity;
import cn.javaboy.admin.common.domain.sys.vo.DepartmentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 14:47
 */
public interface DepartmentDao extends BaseMapper<DepartmentEntity> {

    /**
     * 根据部门id，查询此部门直接子部门的数量
     *
     * @param deptId
     * @return int 子部门的数量
     */
    Integer countSubDepartment(@Param("deptId") Long deptId);

    /**
     * 获取全部部门列表
     *
     * @return List<DepartmentVO>
     */
    List<DepartmentVO> listAll();

    /**
     * 功能描述: 根据父部门id查询
     *
     * @param
     * @return
     * @auther yandanyang
     * @date 2018/8/25 0025 上午 9:46
     */
    List<DepartmentVO> selectByParentId(@Param("departmentId") Long departmentId);
}

package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.dto.DepartmentCreateDTO;
import cn.javaboy.admin.common.domain.sys.dto.DepartmentUpdateDTO;
import cn.javaboy.admin.common.domain.sys.vo.DepartmentVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/16 10:36
 */
public interface IDepartmentService {

    public ResponseDTO<List<DepartmentVO>> listDepartment();

    public ResponseDTO<List<DepartmentVO>> listAllDepartmentUser(String departmentName);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> addDepartment(DepartmentCreateDTO departmentCreateDTO);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> updateDepartment(DepartmentUpdateDTO updateDTO);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> delDepartment(Long deptId);

    public ResponseDTO<DepartmentVO> getDepartmentById(Long departmentId);

    public ResponseDTO<List<DepartmentVO>> listAll();

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> upOrDown(Long departmentId, Long swapId);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> upgrade(Long departmentId);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> downgrade(Long departmentId, Long preId);


}

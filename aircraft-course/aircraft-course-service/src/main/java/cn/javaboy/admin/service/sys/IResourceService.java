package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.dto.ResourceFunctionDTO;
import cn.javaboy.admin.common.domain.sys.dto.ResourceMenuDTO;
import cn.javaboy.admin.common.domain.sys.dto.ValidateList;
import cn.javaboy.admin.common.domain.sys.vo.ResourceFunctionVO;
import cn.javaboy.admin.common.domain.sys.vo.ResourceMenuVO;
import cn.javaboy.admin.common.domain.sys.vo.ResourceRequestUrlVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 13:54
 */
public interface IResourceService {

    public ResponseDTO<List<ResourceRequestUrlVO>> getPrivilegeUrlDTOList();

    @Transactional(rollbackFor = Throwable.class)
    public ResponseDTO<String> menuBatchSave(List<ResourceMenuDTO> menuList);

    public ResponseDTO<List<ResourceMenuVO>> menuQueryAll();

    public ResponseDTO<String> functionSaveOrUpdate(ResourceFunctionDTO privilegeFunctionDTO);

    public ResponseDTO<List<ResourceFunctionVO>> functionQuery(String menuKey);

    @Transactional(rollbackFor = Exception.class)
    public ResponseDTO<String> batchSaveFunctionList(ValidateList<ResourceFunctionDTO> functionList);



}

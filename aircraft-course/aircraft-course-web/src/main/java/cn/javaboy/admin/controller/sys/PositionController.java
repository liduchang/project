package cn.javaboy.admin.controller.sys;

import cn.javaboy.admin.common.anno.OperateLog;
import cn.javaboy.admin.common.domain.constant.SwaggerTagConst;
import cn.javaboy.admin.common.domain.sys.dto.PageResultDTO;
import cn.javaboy.admin.common.domain.sys.dto.PositionAddDTO;
import cn.javaboy.admin.common.domain.sys.dto.PositionQueryDTO;
import cn.javaboy.admin.common.domain.sys.dto.PositionUpdateDTO;
import cn.javaboy.admin.common.domain.sys.vo.PositionResultVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.service.sys.IPositionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(tags = {SwaggerTagConst.Admin.MANAGER_JOB})
@RestController
@RequestMapping("/api/position")
public class PositionController {

    @Autowired
    private IPositionService positionService;

    @ApiOperation(value = "分页查询所有岗位", notes = "分页查询所有岗位 @author liduchang")
    @PostMapping("/getListPage")
    @OperateLog
    public ResponseDTO<PageResultDTO<PositionResultVO>> getJobPage(@RequestBody @Valid PositionQueryDTO queryDTO) {
        return positionService.queryPositionByPage(queryDTO);
    }

    @ApiOperation(value = "添加岗位", notes = "添加岗位 @author liduchang")
    @PostMapping("/add")
    @OperateLog
    public ResponseDTO<String> addJob(@RequestBody @Valid PositionAddDTO addDTO) {
        return positionService.addPosition(addDTO);
    }

    @ApiOperation(value = "更新岗位", notes = "更新岗位 @author liduchang")
    @PostMapping("/update")
    @OperateLog
    public ResponseDTO<String> updateJob(@RequestBody @Valid PositionUpdateDTO updateDTO) {
        return positionService.updatePosition(updateDTO);
    }

    @ApiOperation(value = "根据ID查询岗位", notes = "根据ID查询岗位 @author liduchang")
    @GetMapping("/queryById/{id}")
    @OperateLog
    public ResponseDTO<PositionResultVO> queryJobById(@PathVariable Long id) {
        return positionService.queryPositionById(id);
    }

    @ApiOperation(value = "根据ID删除岗位", notes = "根据ID删除岗位 @author liduchang")
    @GetMapping("/remove/{id}")
    @OperateLog
    public ResponseDTO<String> removeJob(@PathVariable Long id) {
        return positionService.removePosition(id);
    }

}

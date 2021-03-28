package cn.javaboy.admin.service.sys.impl;

import cn.javaboy.admin.common.anno.SmartReload;
import cn.javaboy.admin.common.domain.constant.JudgeEnum;
import cn.javaboy.admin.common.domain.constant.ReloadTagConst;
import cn.javaboy.admin.common.domain.constant.SysParamDataType;
import cn.javaboy.admin.common.domain.constant.SysParamEnum;
import cn.javaboy.admin.common.domain.sys.dto.*;
import cn.javaboy.admin.common.domain.sys.entity.SysParamEntity;
import cn.javaboy.admin.common.domain.sys.vo.SysParamVO;
import cn.javaboy.admin.common.resp.ResponseDTO;
import cn.javaboy.admin.common.resp.constant.ResponseCodeConst;
import cn.javaboy.admin.common.resp.constant.SysParamResponseCodeConst;
import cn.javaboy.admin.common.utils.JBBeanUtil;
import cn.javaboy.admin.common.utils.JBPageUtil;
import cn.javaboy.admin.dao.sys.SysParamDao;
import cn.javaboy.admin.service.sys.ISmartReloadService;
import cn.javaboy.admin.service.sys.ISysParamService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

/**
 * @Author: liduchang
 * @Date: 2021/3/11 14:00
 */
@Slf4j
@Service
public class SysParamService implements ISysParamService {

    /**
     * 系统配置缓存
     */
    private ConcurrentHashMap<String, SysParamEntity> systemConfigMap = new ConcurrentHashMap<>();

    @Autowired
    private SysParamDao systemConfigDao;

    @Autowired
    private ISmartReloadService smartReloadService;

    /**
     * 初始化系统设置缓存
     */
    @PostConstruct
    private void initSystemConfigCache() {
        List<SysParamEntity> entityList = systemConfigDao.selectAll();
        if (CollectionUtils.isEmpty(entityList)) {
            return;
        }

        systemConfigMap.clear();
        entityList.forEach(entity -> this.systemConfigMap.put(entity.getConfigKey().toLowerCase(), entity));
        log.info("系统设置缓存初始化完毕:{}", systemConfigMap.size());

        smartReloadService.register(this);
    }

    @SmartReload(ReloadTagConst.SYSTEM_CONFIG)
    public boolean reload(String args) {
        this.initSystemConfigCache();
        log.warn("<<SmartReload>> <<{}>> , args {} reload success ", ReloadTagConst.SYSTEM_CONFIG, args);
        return true;
    }

    /**
     * 分页获取系统配置
     *
     * @param queryDTO
     * @return
     */
    public ResponseDTO<PageResultDTO<SysParamVO>> getSysParamPage(SysParamQueryDTO queryDTO) {
        Page page = JBPageUtil.convert2QueryPage(queryDTO);
        if(queryDTO.getKey() != null){
            queryDTO.setKey(queryDTO.getKey().toLowerCase());
        }
        List<SysParamEntity> entityList = systemConfigDao.selectSystemSettingList(page, queryDTO);
        PageResultDTO<SysParamVO> pageResultDTO = JBPageUtil.convert2PageResult(page, entityList, SysParamVO.class);
        return ResponseDTO.succData(pageResultDTO);
    }

    /**
     * 根据参数key获得一条数据（数据库）
     *
     * @param configKey
     * @return
     */
    public ResponseDTO<SysParamVO> selectByKey(String configKey) {
        if(configKey != null){
            configKey = configKey.toLowerCase();
        }
        SysParamEntity entity = systemConfigDao.getByKey(configKey);
        if (entity == null) {
            return ResponseDTO.wrap(SysParamResponseCodeConst.NOT_EXIST);
        }
        SysParamVO configDTO = JBBeanUtil.copy(entity, SysParamVO.class);
        return ResponseDTO.succData(configDTO);
    }

    /**
     * 根据参数key获得一条数据 并转换为 对象
     *
     * @param configKey
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T selectByKey2Obj(String configKey, Class<T> clazz) {
        if(configKey != null){
            configKey = configKey.toLowerCase();
        }
        SysParamEntity entity = systemConfigDao.getByKey(configKey);
        if (entity == null) {
            return null;
        }
        SysParamDTO configDTO = JBBeanUtil.copy(entity, SysParamDTO.class);
        String configValue = configDTO.getConfigValue();
        if (StringUtils.isEmpty(configValue)) {
            return null;
        }
        T obj = JSON.parseObject(configValue, clazz);
        return obj;
    }

    public SysParamDTO getCacheByKey(SysParamEnum.Key key) {
        SysParamEntity entity = this.systemConfigMap.get(key.name().toLowerCase());
        if (entity == null) {
            return null;
        }
        return JBBeanUtil.copy(entity, SysParamDTO.class);
    }

    /**
     * 添加系统配置
     *
     * @param configAddDTO
     * @return
     */
    public ResponseDTO<String> addSysParam(SysParamAddDTO configAddDTO) {
        configAddDTO.setConfigKey(configAddDTO.getConfigKey().toLowerCase());
        SysParamEntity entity = systemConfigDao.getByKey(configAddDTO.getConfigKey());
        if (entity != null) {
            return ResponseDTO.wrap(SysParamResponseCodeConst.ALREADY_EXIST);
        }
        ResponseDTO valueValid = this.configValueValid(configAddDTO.getConfigKey(),configAddDTO.getConfigValue());
        if(!valueValid.isSuccess()){
            return valueValid;
        }
        configAddDTO.setConfigKey(configAddDTO.getConfigKey().toLowerCase());
        SysParamEntity addEntity = JBBeanUtil.copy(configAddDTO, SysParamEntity.class);
        addEntity.setIsUsing(JudgeEnum.YES.getValue());
        systemConfigDao.insert(addEntity);
        //刷新缓存
        this.initSystemConfigCache();
        return ResponseDTO.succ();
    }

    /**
     * 更新系统配置
     *
     * @param updateDTO
     * @return
     */
    public ResponseDTO<String> updateSysParam(SysParamUpdateDTO updateDTO) {
        updateDTO.setConfigKey(updateDTO.getConfigKey().toLowerCase());
        SysParamEntity entity = systemConfigDao.selectById(updateDTO.getId());
        //系统配置不存在
        if (entity == null) {
            return ResponseDTO.wrap(SysParamResponseCodeConst.NOT_EXIST);
        }
        SysParamEntity alreadyEntity = systemConfigDao.getByKeyExcludeId(updateDTO.getConfigKey().toLowerCase(), updateDTO.getId());
        if (alreadyEntity != null) {
            return ResponseDTO.wrap(SysParamResponseCodeConst.ALREADY_EXIST);
        }
        ResponseDTO valueValid = this.configValueValid(updateDTO.getConfigKey(),updateDTO.getConfigValue());
        if(!valueValid.isSuccess()){
            return valueValid;
        }
        entity = JBBeanUtil.copy(updateDTO, SysParamEntity.class);
        updateDTO.setConfigKey(updateDTO.getConfigKey().toLowerCase());
        systemConfigDao.updateById(entity);

        //刷新缓存
        this.initSystemConfigCache();
        return ResponseDTO.succ();
    }


    private ResponseDTO<String> configValueValid(String configKey , String configValue){
        SysParamEnum.Key configKeyEnum = SysParamEnum.Key.selectByKey(configKey);
        if(configKeyEnum == null){
            return ResponseDTO.succ();
        }
        SysParamDataType dataType = configKeyEnum.getDataType();
        if(dataType == null){
            return ResponseDTO.succ();
        }
        if(dataType.name().equals(SysParamDataType.TEXT.name())){
            return ResponseDTO.succ();
        }
        if(dataType.name().equals(SysParamDataType.JSON.name())){
            try {
                JSONObject jsonStr = JSONObject.parseObject(configValue);
                return ResponseDTO.succ();
            } catch (Exception e) {
                return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM,"数据格式不是JSON,请修改后提交。");
            }
        }
        if(StringUtils.isNotEmpty(dataType.getValid())){
            Boolean valid = Pattern.matches(dataType.getValid(), configValue);
            if(valid){
                return ResponseDTO.succ();
            }
            return ResponseDTO.wrap(ResponseCodeConst.ERROR_PARAM,"数据格式不是"+dataType.name().toLowerCase()+",请修改后提交。");
        }

        return ResponseDTO.succ();
    }

    /**
     * 根据分组名称 获取获取系统设置
     *
     * @param group
     * @return
     */
    public ResponseDTO<List<SysParamVO>> getListByGroup(String group) {

        List<SysParamEntity> entityList = systemConfigDao.getListByGroup(group);
        if (CollectionUtils.isEmpty(entityList)) {
            return ResponseDTO.succData(Lists.newArrayList());
        }
        List<SysParamVO> systemConfigList = JBBeanUtil.copyList(entityList, SysParamVO.class);
        return ResponseDTO.succData(systemConfigList);
    }

    /**
     * 根据分组名称 获取获取系统设置
     *
     * @param group
     * @return
     */
    public List<SysParamDTO> getListByGroup(SysParamEnum.Group group) {
        List<SysParamEntity> entityList = systemConfigDao.getListByGroup(group.name());
        if (CollectionUtils.isEmpty(entityList)) {
            return Lists.newArrayList();
        }
        List<SysParamDTO> systemConfigList = JBBeanUtil.copyList(entityList, SysParamDTO.class);
        return systemConfigList;
    }
}

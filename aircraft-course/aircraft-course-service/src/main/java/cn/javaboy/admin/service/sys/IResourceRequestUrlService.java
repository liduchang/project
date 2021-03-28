package cn.javaboy.admin.service.sys;

import cn.javaboy.admin.common.domain.sys.vo.ResourceRequestUrlVO;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @Author: liduchang
 * @Date: 2021/3/15 9:54
 */
public interface IResourceRequestUrlService {

    @PostConstruct
    public void initAllUrl();

    public List<ResourceRequestUrlVO> getPrivilegeList();
}

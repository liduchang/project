package cn.javaboy.admin.common.domain.constant;

import java.util.Arrays;
import java.util.Optional;

public class SysParamEnum {

    public enum Group {
        BACK,
        GIT_LOG
    }

    public enum Key {
        /**
         * 超管id
         */
        USER_SUPERMAN(SysParamDataType.TEXT),
        /**
         * 阿里云OSS配置项
         */
        ALI_OSS(SysParamDataType.JSON),
        /**
         * 七牛云OSS配置项
         */
        QI_NIU_OSS(SysParamDataType.JSON),
        /**
         * 本地文件上传url前缀
         */
        LOCAL_UPLOAD_URL_PREFIX(SysParamDataType.URL),
        /**
         * 邮件配置
         */
        EMAIL_CONFIG(SysParamDataType.JSON),
        /**
         * git-log 插件
         */
        GIT_LOG_PLUGIN(SysParamDataType.JSON);

        private SysParamDataType dataType;

        Key(SysParamDataType dataType) {
            this.dataType = dataType;
        }


        public SysParamDataType getDataType() {
            return dataType;
        }

        public static Key selectByKey(String key) {
            Key[] values = Key.values();
            Optional<Key> first = Arrays.stream(values).filter(e -> e.name().equalsIgnoreCase(key)).findFirst();
            return !first.isPresent() ? null : first.get();
        }
    }

}

### 项目结构
#### 公共模块（iab-admin-common）
```text
java...
   └──aircraft-course-common
        ├── anno        通用注解
        ├── context     上下文相关
        ├── db          数据源
        ├── domain      实体类
        ├── exception   统一管理项目中的异常 
        ├── mybaties    mybatis相关的插件
        ├── page        分页相关
        ├── resp        统一返回格式
        ├── utils       工具类
        └── validator   自定义校验相关
```

#### dao模块（aircraft-course-dao）
```text
aircraft-course-dao
└──  dao                    dao层
```

#### service模块（aircraft-course-service）
```text
aircraft-course-service
└──  service                service层
```

#### sql文件（aircraft-course-sql）
```text
aircraft-course-sql
└──  sql                    通用sql文件
```

#### web模块（iab-admin-web）
```text
aircraft-course-web
├── support        支撑模块
    └── aspect             切面
    └── config             配置
    └── interceptor        拦截器
└── controller             controller层
```

### 快速启动
####后端
1. 先执行aircraft-course-sql下的sql文件
2. 本机安装redis，并配置redis的host信息为自己的ip以及密码
3. 启动aircraft-course-web下的AircraftCourseApplication启动类

####前端
1. 执行npm install
2. 使用admin/123456进行登录
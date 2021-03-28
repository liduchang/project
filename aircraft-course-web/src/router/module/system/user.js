import Main from '@/components/main';
// 基础设置
export const user = [
  {
    path: '/user',
    component: Main,
    name: 'User',
    meta: {
      title: '权限管理',
      icon: 'icon iconfont iconrenyuanguanli'
    },
    children: [
      //  角色管理页面路由
      {
        path: '/user/role',
        name: 'RoleManager',
        meta: {
          title: '角色管理',
          privilege: [
            {
              title: '添加角色',
              name: 'add-role'
            },
            {
              title: '删除角色',
              name: 'delete-role'
            },
            {
              title: '编辑角色',
              name: 'update-role'
            },
            {
              title: '修改角色权限',
              name: 'update-role-privilege'
            },
            {
              title: '添加成员',
              name: 'add-user-role'
            },
            {
              title: '移除成员',
              name: 'delete-user-role'
            },
            {
              title: '批量移除',
              name: 'delete-user-role-batch'
            },
            {
              title: '查询成员',
              name: 'search-user-list'
            },
            {
              title: '查询数据范围',
              name: 'query-data-scope'
            },
            {
              title: '更新数据范围',
              name: 'update-data-scope'
            }
          ]
        },
        component: () => import('@/views/system/user/role/role-manage.vue')
      },
      // 岗位管理页面路由 新
      {
        path: '/user/position',
        name: 'PositionList',
        meta: {
          title: '岗位管理',
          privilege: [
            {
              title: '查询',
              name: 'search-position'
            },
            {
              title: '添加',
              name: 'add-position'
            },
            {
              title: '修改',
              name: 'update-position'
            },
            {
              title: '删除',
              name: 'delete-position'
            }
          ]
        },
        component: () => import('@/views/system/user/position/position-list.vue')
      },
      // 人员管理页面路由
      {
        path: '/user/role-user-manage',
        name: 'RoleUserManager',
        meta: {
          title: '用户管理',
          privilege: [
            {
              title: '添加部门',
              name: 'add-department'
            },
            {
              title: '编辑部门',
              name: 'update-department'
            },
            {
              title: '删除部门',
              name: 'delete-department'
            },
            {
              title: '查询',
              name: 'search-department'
            },
            {
              title: '添加成员',
              name: 'add-user'
            },
            {
              title: '编辑成员',
              name: 'update-user'
            },
            {
              title: '禁用',
              name: 'disabled-user'
            },
            {
              title: '批量操作',
              name: 'disabled-user-batch'
            },
            {
              title: '员工角色编辑',
              name: 'update-user-role'
            },
            {
              title: '删除员工',
              name: 'delete-user'
            },
            {
              title: '重置密码',
              name: 'reset-user-password'
            }
          ]
        },
        component: () =>
          import('@/views/system/user/role-user/role-user-manage.vue')
      }
    ]
  }
];

import Main from '@/components/main';
// 基础设置
export const systemSetting = [
  {
    path: '/system-setting',
    name: 'SystemSetting',
    component: Main,
    meta: {
      title: '系统设置',
      icon: 'icon iconfont iconxitongshezhi'
    },

    children: [
      {
        path: '/system-setting/system-param',
        name: 'SysParam',
        meta: {
          title: '系统参数',
          privilege: [
            {
              title: '查询系统参数',
              name: 'system-params-search'
            },
            {
              title: '添加系统参数',
              name: 'system-params-add'
            },
            {
              title: '修改系统参数',
              name: 'system-params-update'
            },
            {
              title: '搜索系统参数',
              name: 'system-config-search'
            }
          ]
        },
        component: () =>
          import('@/views/system/system-setting/system-param/system-param.vue')
      },
      {
        path: '/system-setting/system-resource',
        name: 'SystemResource',
        meta: {
          title: '菜单设置',
          privilege: [
            {
              title: '编辑',
              name: 'resource-main-update'
            },
            {
              title: '批量保存功能点',
              name: 'resource-batch-save-points'
            },
            {
              title: '查询',
              name: 'resource-main-search'
            }
          ]
        },
        component: () => import('@/views/system/system-setting/system-resource/system-resource.vue')
      }
    ]
  }
];

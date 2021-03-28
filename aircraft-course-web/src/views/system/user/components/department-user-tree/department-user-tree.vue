<template>
  <div style="height: 330px;">
    <Row>
      <Col span="24">
        <Input
          @on-change="filterUser"
          placeholder="请输入员工名称"
          style="max-width: 300px;position:fixed;z-index:9999"
          v-if="!isDepartment"
          v-model="searchKeywords"
        />
        <Tree style="margin-top:40px" :data="treeData"></Tree>
      </Col>
    </Row>
  </div>
</template>

<script>
import { departmentApi } from '@/api/department';
import DepartmentUserTreeItem from '../department-user-tree-item/department-user-tree-item.vue';
import Vue from 'vue';
export default {
  name: 'DepartmentUserTree',
  components: {
    DepartmentUserTreeItem
  },
  props: {
    // true 查部门
    isDepartment: {
      type: Boolean,
      default: false
    }
  },
  data () {
    return {
      // 查询部门及员工列表
      originalData: [],
      // 搜索内容
      searchKeywords: '',
      eventbus: new Vue(),
      // 当前树形选中 部门或人员
      currentSelect: null,
      // 生成适配的树形数据
      treeData: []
    };
  },
  mounted () {
    this.getDepartmentUserList();
  },
  methods: {
    // 重置搜索内容
    resetSearch () {
      this.searchKeywords = '';
      this.getDepartmentUserList();
    },
    // 获取当前选择项
    getSelect () {
      return this.currentSelect;
    },
    // 输入框输入后 搜索
    filterUser () {
      this.originalData.forEach(department => {
        this.recursionFilterUser(department);
      });
      this.generateTreeData();
    },
    // 根据searchKeywords搜索成员和部门
    recursionFilterUser (department) {
      if (department.users) {
        department.users.forEach(e => {
          if (
            this.searchKeywords === '' ||
            e.actualName.indexOf(this.searchKeywords) > -1
          ) {
            e.show = true;
          } else {
            e.show = false;
          }
        });
      }

      if (department.children) {
        department.children.forEach(item => {
          this.recursionFilterUser(item);
        });
      }
    },
    // 让所有部门展开所有子项
    filterDepartment () {
      this.originalData.forEach(department => {
        this.recursionFilterDepartment(department);
      });
      this.generateTreeData();
    },
    // 展开部门所有子项
    recursionFilterDepartment (department) {
      if (department.children) {
        department.children.forEach(e => {
          e.show = true;
          this.recursionFilterDepartment(e);
        });
      }
    },
    // 查询部门及员工列表
    async getDepartmentUserList () {
      this.$Spin.show();
      let res = await departmentApi.getDepartmentUserList();
      this.$Spin.hide();
      this.originalData = res.data;
      if (!this.isDepartment) {
        this.filterUser();
      } else {
        this.filterDepartment();
      }
    },
    // 生成树形图数据
    generateTreeData () {
      let tree = [];
      this.originalData.forEach(department => {
        let icon = department.type == 1 ? 'md-cube' : 'md-menu';
        let obj = Object.assign(
          {},
          {
            title: department.name,
            expand: true,
            children: this.constractTree(department),
            render: (h, { root, node, data }) => {
              return h(DepartmentUserTreeItem, {
                props: {
                  isDepartment: this.isDepartment,
                  itemData: {
                    title: department.name,
                    icon: icon,
                    isUser: false,
                    id: department.id,
                    selectFunction: obj => {
                      if (this.isDepartment) {
                        this.currentSelect = obj;
                        this.eventbus.$emit('select', obj);
                        this.$emit('on-select', obj);
                      }
                    }
                  }
                },
                style: {
                  cursor: 'pointer'
                }
              });
            }
          }
        );
        tree.push(obj);
      });
      this.treeData = tree;
    },
    // 根据部门构建树形
    constractTree (department) {
      let children = [];
      if (department.children) {
        department.children.forEach(departmentChild => {
          if (this.isDepartment && !departmentChild.show) {
            return;
          }
          let icon = departmentChild.type == 1 ? 'md-cube' : 'md-menu';
          let obj = Object.assign(
            {},
            {
              title: departmentChild.name,
              expand: true,
              disabled: false,
              children: this.constractTree(departmentChild),
              render: (h, { root, node, data }) => {
                return h(DepartmentUserTreeItem, {
                  props: {
                    isDepartment: this.isDepartment,
                    itemData: {
                      title: departmentChild.name,
                      icon: icon,
                      isUser: false,
                      id: departmentChild.id,
                      selectFunction: obj => {
                        this.currentSelect = obj;
                        this.eventbus.$emit('select', obj);
                        this.$emit('on-select', obj);
                      }
                    }
                  },
                  style: {
                    cursor: 'pointer'
                  }
                });
              }
            }
          );
          children.push(obj);
        });
      }
      if (!this.isDepartment && department.users) {
        department.users.forEach(userItem => {
          if (userItem.show) {
            let obj = Object.assign(
              {},
              {
                title: userItem.actualName,
                render: (h, { root, node, data }) => {
                  return h(DepartmentUserTreeItem, {
                    props: {
                      isDepartment: this.isDepartment,
                      itemData: {
                        title: userItem.actualName,
                        icon: 'md-person',
                        isUser: true,
                        selected: false,
                        id: userItem.id,
                        eventbus: this.eventbus,
                        selectFunction: obj => {
                          this.currentSelect = obj;
                          this.eventbus.$emit('select', obj);
                          this.$emit('on-select', obj);
                        }
                      }
                    },
                    style: {
                      cursor: 'pointer'
                    }
                  });
                }
              }
            );
            children.push(obj);
          }
        });
      }
      return children;
    }
  }
};
</script>
<style lang="less" scoped>
</style>

<template>
  <div>
    <Col :lg="19" :md="16">
      <Card class="warp-card" dis-hover>
        <!--Row 顶部操作区 start-->
        <Row justify="space-between" type="flex">
          <Col>
            <Input
              @on-change="searchUsers"
              icon="md-search"
              placeholder="请输入姓名搜搜..."
              style="width: 200px"
              v-model="searchUserName"
            />
            <Button
              @click="showAddUserModal"
              icon="md-add"
              style="margin-left: 5px;"
              type="primary"
              v-privilege="'add-user'"
            >添加成员</Button>
          </Col>
          <Col>
            <Button
              @click="queryUserListByIsDisabled(1)"
              type="default"
              v-privilege="'search-department'"
              v-show="!currentDisabled"
            >查看已禁用用户</Button>
            <Button
              @click="queryUserListByIsDisabled(0)"
              type="default"
              v-privilege="'search-department'"
              v-show="currentDisabled"
            >查看已启用用户</Button>
            <Button
              @click="GroupDisable(1)"
              style="margin-left:5px"
              type="error"
              v-privilege="'disabled-user-batch'"
              v-show="!currentDisabled"
            >批量禁用</Button>
            <Button
              @click="GroupDisable(0)"
              style="margin-left:5px"
              type="success"
              v-privilege="'disabled-user-batch'"
              v-show="currentDisabled"
            >批量启用</Button>
          </Col>
        </Row>
        <!--Row 顶部操作区 end-->
        <!--Row 表格区 start-->
        <Row style="padding-top:10px; ">
          <Table
            :columns="columns"
            :data="userTable"
            :loading="isShowTablesLoading"
            @on-selection-change="singleSelect"
            border
          ></Table>
        </Row>
        <!--Row 表格区 end-->
        <!--Row 底部操作区 start-->
        <Row class="page" justify="end" style="position: relative;margin-top: 10px;" type="flex">
          <Page
            :current="pageNum"
            :page-size="pageSize"
            :total="totalPage"
            @on-change="changePage"
            show-elevator
          ></Page>
        </Row>
        <!--Row 底部操作区 end-->
      </Card>
    </Col>
    <!--UserTableAdd 添加成员弹窗 start-->
    <UserTableAdd
      :selectDepartment="selectDepartment"
      @addSuccess="getUserList"
      ref="userTableAdd"
    ></UserTableAdd>
    <!--UserTableAdd 添加成员弹窗 end-->
    <!--Modal 角色管理弹窗 start-->
    <Modal
      @on-ok="confirmAddRole"
      class-name="vertical-center-modal"
      title="角色管理"
      v-model="isShowManageRoleModal"
    >
      <CheckboxGroup v-model="userRole">
        <Checkbox
          :key="roleItem.id"
          :label="roleItem.id"
          style="width:104px;line-height:40px;"
          v-for="roleItem in roleList"
        >{{roleItem.roleName}}</Checkbox>
      </CheckboxGroup>
    </Modal>
    <!--Modal 角色管理弹窗 end-->
    <!--UserTableDetail 角色详情 start-->
    <UserTableDetail ref="userTableDetail"></UserTableDetail>
    <!--UserTableDetail 角色详情 end-->
  </div>
</template>
<script>
import { departmentApi } from '@/api/department';
import { userApi } from '@/api/user';
import { roleApi } from '@/api/role';
import { utils } from '@/lib/util';
import UserTableAdd from '../user-table-add/user-table-add.vue';
import UserTableDetail from '../user-table-detail/user-table-detail.vue';
export default {
  name: 'UserTable',
  components: {
    UserTableAdd,
    UserTableDetail
  },
  props: {
    // 选中的部门
    selectDepartment: {
      type: Object,
      required: true
    },
    // 表格样式 按钮功能
    departments: {
      type: Array,
      required: true
    }
  },
  data () {
    return {
      userRole: [],
      // 员工id
      mid: '',
      totalPage: 0,
      pageSize: 20,
      pageNum: 1,
      isShowManageRoleModal: false,
      isShowTablesLoading: false,
      // 搜索框内容
      searchUserName: '',
      columns: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: 'ID',
          width: 60,
          key: 'id'
        },
        {
          key: 'actualName',
          width: 200,
          title: '名称',
          align: 'left'
        },
        {
          key: 'loginName',
          title: '登录名',
          width: 200,
          align: 'left'
        },
        {
          key: 'isDisabled',
          width: 80,
          title: '状态',
          align: 'left',
          render: (h, params) => {
            let disabled = params.row.isDisabled;
            return h('span', disabled ? '禁用' : '启用');
          }
        },
        {
          key: 'phone',
          width: 120,
          title: '手机',
          align: 'left'
        },
        {
          key: 'departmentName',
          width: 180,
          title: '所属部门',
          align: 'left'
        },
        {
          key: 'positionName',
          width: 180,
          title: '岗位',
          align: 'left',
          ellipsis: true,
          tooltip: true
        },
        {
          key: 'email',
          width: 180,
          title: '电子邮箱',
          align: 'left'
        },

        {
          key: 'createTime',
          width: 180,
          title: '创建时间',
          align: 'left'
        },
        {
          title: '操作',
          key: 'action',
          width: 160,
          fixed: 'right',
          align: 'center',
          className: 'action-hide',
          render: (h, params) => {
            let btnGroup = [];
            let isSuper = params.row.super;
            if (!isSuper) {
              btnGroup.push({
                title: '角色设置',
                directives: [
                  {
                    name: 'privilege',
                    value: 'update-user-role'
                  }
                ],
                action: () => {
                  this.showManageRoleModal(h, params);
                }
              });
            }
            btnGroup.push({
              title: '详情',
              action: () => {
                this.$refs['userTableDetail'].showModal(params.row);
              }
            });
            if (params.row.isDisabled == 0 && !isSuper) {
              btnGroup.push({
                title: '编辑',
                directives: [
                  {
                    name: 'privilege',
                    value: 'update-user'
                  }
                ],
                action: () => {
                  this.updataUser(params.row);
                }
              });
            }
            if (!isSuper) {
              let isDisableBtn;
              if (params.row.isDisabled == 0) {
                isDisableBtn = {
                  title: '禁用',
                  directives: [
                    {
                      name: 'privilege',
                      value: 'disabled-user'
                    }
                  ],
                  action: () => {
                    this.updateUserStatus(h, params);
                  }
                };
              } else {
                isDisableBtn = {
                  title: '启用',
                  directives: [
                    {
                      name: 'privilege',
                      value: 'disabled-user'
                    }
                  ],
                  action: () => {
                    this.updateUserStatus(h, params);
                  }
                };
              }
              btnGroup.push(isDisableBtn);
            }
            if (!isSuper) {
              btnGroup.push({
                title: '密码重置',
                directives: [
                  {
                    name: 'privilege',
                    value: 'reset-user-password'
                  }
                ],
                action: () => {
                  this.$Modal.confirm({
                    title: '重置密码',
                    content: '是否将密码重置为123456',
                    onOk: () => {
                      this.resetPassword(params.row.id);
                    }
                  });
                }
              });
            }
            if (params.row.isDisabled == 1 && !isSuper) {
              btnGroup.push({
                title: '删除',
                directives: [
                  {
                    name: 'privilege',
                    value: 'delete-user'
                  }
                ],
                action: () => {
                  this.$Modal.confirm({
                    title: '删除',
                    content: '确认删除？',
                    onOk: () => {
                      this.deleteUser(params.row.id);
                    }
                  });
                }
              });
            }
            // return h('div', btnGroup)
            return this.$tableAction(h, btnGroup);
          }
        }
      ],
      // 表格数据
      userTable: [],
      disableId: [],
      currentDisabled: 0,
      roleList: []
    };
  },
  watch: {
    selectDepartment (newData) {
      this.selectDepartment = newData;
      this.pageNum = 1;
      this.getUserList();
    }
  },
  mounted () {
    this.getUserList();
  },
  methods: {
    // 翻页
    changePage (pageNum) {
      this.pageNum = pageNum;
      this.getUserList();
    },
    // 根据部门ID查询部门员工
    async getUserList () {
      let param = {
        isDisabled: this.currentDisabled,
        pageSize: this.pageSize,
        pageNum: this.pageNum
      };
      param.departmentId = this.selectDepartment.id;
      try {
        this.isShowTablesLoading = true;
        param.keyword = this.searchUserName;
        let result = await userApi.getUserList(param);
        this.totalPage = result.data.total;
        this.userTable = result.data.list;
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
      } finally {
        this.isShowTablesLoading = false;
      }
    },
    // 打开子组件的模态框
    showAddUserModal () {
      this.$refs.userTableAdd.showModal();
    },
    // 根据是否启用查询成员
    queryUserListByIsDisabled (type) {
      this.currentDisabled = type;
      this.pageNum = 1;
      this.getUserList();
    },
    // 确定添加角色
    async confirmAddRole () {
      let roleList = this.userRole;
      try {
        this.isShowTablesLoading = true;
        let result = await userApi.updateRoles(
          Object.assign({
            userId: this.mid,
            roleIds: roleList
          })
        );
        this.isShowTablesLoading = false;
        this.$Message.success('授权成功');
        this.getUserList();
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
        this.isShowTablesLoading = false;
      }
    },
    // 确认添加角色的后台数据接口
    async getRoles (id) {
      try {
        this.isShowTablesLoading = true;
        let result = await roleApi.getRoles(id);
        this.isShowTablesLoading = false;
        this.roleList = result.data;
        this.userRole = [];
        this.roleList.forEach(item => {
          if (item.selected) {
            this.userRole.push(item.id);
          }
        });
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
        this.isShowTablesLoading = false;
      }
    },
    // 点击打开员工角色选择框
    showManageRoleModal (h, param) {
      this.mid = param.row.id;
      this.isShowManageRoleModal = true;
      this.getRoles(param.row.id);
    },
    // 修改员工信息
    updataUser (detail) {
      if (this.departments[0].id == detail.departmentId) {
        detail.departmentName = this.departments[0].name;
      } else {
        this.departments[0].children.forEach(item => {
          if (item.id == detail.departmentId) {
            detail.departmentName = item.name;
          }
        });
      }
      if (detail.birthday) {
        detail.birthday = utils.getDate(new Date(detail.birthday), 'YMD');
      }
      this.$refs.userTableAdd.showModal(detail);
    },
    // 点击搜索
    searchUsers () {
      this.pageNum = 1;
      this.queryUserListByIsDisabled(0);
    },
    // 表格左侧复选框，点击保存选中栏信息
    singleSelect (row) {
      this.disableId = row;
    },
    // 批量禁用
    GroupDisable (type) {
      if (this.disableId.length == 0) {
        this.$Message.error('请最少选择一项');
        return false;
      } else {
        this.currentDisabled = type;
        let disableIds = this.disableId.map(e => e.id);
        this.updateStatusBatch(
          Object.assign({ status: type, userIds: disableIds })
        );
      }
    },
    // 禁用/启用单个用户
    async updateUserStatus (h, params) {
      try {
        this.isShowTablesLoading = true;
        let userId = params.row.id;
        let status = 0;
        if (params.row.isDisabled == 0) {
          status = 1;
          this.queryUserListByIsDisabled(0);
        } else {
          status = 0;
          this.queryUserListByIsDisabled(1);
        }
        let result = await userApi.updateStatus(userId, status);
        this.isShowTablesLoading = false;
        if (status) {
          this.$Message.success('禁用成功');
          this.getUserList();
        } else {
          this.$Message.success('启用成功');
          this.queryUserListByIsDisabled(1);
        }
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
        this.isShowTablesLoading = false;
      }
    },
    // 批量禁用多个用户
    async updateStatusBatch (param) {
      try {
        this.isShowTablesLoading = true;
        let result = await userApi.updateStatusBatch(param);
        this.isShowTablesLoading = false;
        this.$Message.success('操作成功');
        this.disableId = [];
        this.queryUserListByIsDisabled(this.currentDisabled);
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
        this.isShowTablesLoading = false;
      }
    },
    // 重置密码
    async resetPassword (id) {
      try {
        this.isShowTablesLoading = true;
        let result = await userApi.resetPassword(id);
        this.isShowTablesLoading = false;
        this.$Message.success('操作成功');

        this.queryUserListByIsDisabled(this.currentDisabled);
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
        this.isShowTablesLoading = false;
      }
    },
    // 删除成员
    async deleteUser (id) {
      try {
        this.isShowTablesLoading = true;
        let result = await userApi.deleteUser(id);
        this.isShowTablesLoading = false;
        this.$Message.success('操作成功');
        this.queryUserListByIsDisabled(this.currentDisabled);
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
        this.isShowTablesLoading = false;
      }
    }
  }
};
</script>
<style lang="less" scoped>
.ivu-tree-children {
  cursor: pointer;
}

.option-department {
  font-size: 14px;
  padding: 5px;
  cursor: pointer;
}

.searchable-table-con1 {
  min-height: 350px !important;
}

.option-department:hover {
  background-color: rgba(5, 170, 250, 0.2);
}
</style>

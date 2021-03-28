<template>
  <!--div tab切换成员列表部分 start-->
  <div>
    <Row>
      <Col span="16" style="margin:20px 0;font-size: 15px;color: #95a5a6;">管理拥有当前角色权限的人员列表</Col>
      <Col span="8" style="margin:20px 0; text-align: right;">
        <Button
          @click="addUser"
          type="primary"
          v-privilege="'add-user-role'"
        >添加成员</Button>

             <Button
          @click="deleteUsers()"
          style="margin-left:10px"
          type="error"
          v-privilege="'delete-user-role-batch'"
        >批量移除</Button>
      </Col>
    </Row>
    <!--Table 表格列表 start-->
    <Table
      :columns="columns"
      :data="tableData"
      :loading="isShowTablesLoading"
      @on-selection-change="selectChange"
      border
      ref="selection"
    ></Table>
    <!--Table 表格列表 end-->
    <Row class="page" justify="end" type="flex">
      <Col order="2" span="24" style="text-align: right;margin-top:20px;">
        <Page
          :current="currentPage"
          :page-size="pageSize"
          :total="total"
          @on-change="changePage"
          show-elevator
        ></Page>
      </Col>
    </Row>
    <!--modal 添加成员 start-->
    <Modal
      @on-ok="confirmPrepAddUsers()"
      style="min-width:1800px"
      title="添加成员"
      v-model="isShowUserModal"
      width="700"
    >
      <Row class="shuttle-box">
        <!--Col 左侧员工列表 start-->
        <Col class="box" span="11">
          <Row>
            <Col class="title" span="24">员工列表</Col>
          </Row>
          <Row>
            <Col
              @dblclick.native="getCharge"
              class="no-scrollbar"
              id="goRight"
              span="24"
              style="height: 290px;overflow-y:scroll"
            >
              <DepartmentUserTree ref="departmentUserTree"></DepartmentUserTree>
            </Col>
          </Row>
        </Col>
        <!--Col 左侧员工列表 end-->
        <!--Col 转移按钮 start-->
        <Col span="2" style="text-align: center;">
          <Icon
            @click.native="addPrepUsers"
            size="30"
            style="line-height: 350px"
            type="md-arrow-round-forward"
          ></Icon>
        </Col>
        <!--Col 转移按钮 end-->
        <!--Col 右侧预添加列表 start-->
        <Col class="box" span="11">
          <Row>
            <Col class="title" span="24"> 成员列表 </Col>
            <Col
              class="no-scrollbar"
              span="24"
              style="overflow-y:scroll;height: 290px;text-align: center"
            >
              <Row :key="index" v-for="(item,index) in prepAddUsers">
                <Col span="24" style="font-size: 15px;text-align: left;">
                  <icon type="ios-people"></icon>
                  {{item.manageName}}
                  <Button @click.native="deletePrepUser(index)" icon="md-close" type="text"></Button>
                </Col>
              </Row>
            </Col>
          </Row>
        </Col>
        <!--Col 右侧预添加列表 end-->
      </Row>
    </Modal>
    <!--modal 添加成员 end-->
  </div>
  <!--div tab切换成员列表部分 end-->
</template>
<script>
import DepartmentUserTree from '../../../components/department-user-tree/department-user-tree';
import { roleApi } from '@/api/role';

export default {
  name: 'RoleList',
  components: {
    DepartmentUserTree
  },
  props: {
    // 角色id
    roleId: {
      type: Number,
      required: true,
      validator: value => {
        return value >= 0;
      }
    }
  },
  // 数据
  data () {
    return {
      // 是否显示添加成员弹窗
      isShowUserModal: false,
      currentPage: 1,
      isShowTablesLoading: false,
      columns: [
        {
          type: 'selection',
          width: 60,
          align: 'center'
        },
        {
          title: '登录名',
          key: 'loginName'
        },
        {
          title: '姓名',
          key: 'actualName'
        },
        {
          title: '手机号',
          key: 'phone'
        },
        {
          title: '邮箱',
          key: 'email'
        },
        {
          title: '部门',
          key: 'departmentName'
        },
        {
          title: '操作',
          key: 'operation ',
          width: 100,
          align: 'center',
          className: 'action-hide',
          render: (h, params) => {
            return this.$tableAction(h, [
              {
                title: '移除',
                directives: [
                  {
                    name: 'privilege',
                    value: 'delete-user-role'
                  }
                ],
                action: () => {
                  this.deleteUser(params.index);
                }
              }
            ]);
          }
        }
      ],
      userData: {
        // orderby:'',
        roleId: '',
        pageNum: 1,
        pageSize: 10,
        sort: ''
      },
      // 表格数据
      tableData: [],
      // 待添加的成员列表
      prepAddUsers: [],
      // 提交添加成员数据
      submitPrepAddUsersData: {
        // 提交成员id列表
        userIds: [],
        // 当前roleId
        roleId: 0
      },
      // 批量删除的id列表
      deleteIds: [],
      total: 0,
      pageSize: 0
    };
  },
  watch: {
    roleId (val) {
      if (val) {
        this.userData.roleId = this.roleId;
        this.getListUser(this.userData);
      }
    }
  },
  mounted () {
    this.userData.roleId = this.roleId;
    this.getListUser(this.userData);
  },
  methods: {
    // 批量删除
    deleteUsers () {
      let object = {};
      object.userIds = this.deleteIds.slice(0, this.deleteIds.length);
      object.roleId = this.roleId;
      if (object.userIds.length <= 0) {
        this.$Message.error('请先选择要移除的成员');
      } else {
        this.deleteUserList(object); // 删除
      }
    },
    // 删除待添加列表中 人员
    deletePrepUser (index) {
      this.prepAddUsers.splice(index, 1);
    },
    // 批量删除方法
    async deleteUserList (param) {
      this.isShowTablesLoading = true;
      try {
        await roleApi.deleteUserList(param);
        await this.getListUser(this.userData); // 刷新数据
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
      } finally {
        this.isShowTablesLoading = false;
      }
    },
    // 选项框多选移除
    selectChange (selection) {
      this.deleteIds = [];
      for (let i = 0; i < selection.length; i++) {
        this.deleteIds.push(selection[i].id);
      }
      console.log(this.deleteIds);
    },
    // 移除当前项
    deleteUser (index) {
      let object = {};
      object.userId = this.tableData[index].id;
      object.roleId = this.roleId;
      this.deleteUserRole(object);
    },
    // 删除角色成员方法
    async deleteUserRole (param) {
      this.isShowTablesLoading = true;
      try {
        await roleApi.deleteUserRole(param);
        this.$Message.success('移除成功');
        await this.getListUser(this.userData);
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
      } finally {
        this.isShowTablesLoading = false;
      }
    },
    // 分页改变获取数据方法
    // 分页器
    changePage (number) {
      let object = {};
      object.roleId = this.roleId;
      this.currentPage = number;
      object.pageNum = number;
      object.pageSize = this.pageSize;
      object.sort = '';
      this.getListUser(object);
    },
    // 确定添加角色成员
    confirmPrepAddUsers () {
      this.submitPrepAddUsersData.userIds = [];
      this.prepAddUsers.forEach(e => {
        console.log(e);
        this.submitPrepAddUsersData.userIds.push(e.manageId);
      });
      console.log(this.prepAddUsers);
      this.submitPrepAddUsersData.roleId = this.roleId;
      this.addUserListRole(this.submitPrepAddUsersData);
      this.getListUser(this.userData); // 刷新表格
    },
    // 添加角色成员方法
    async addUserListRole (param) {
      this.isShowTablesLoading = true;
      try {
        await roleApi.addUserListRole(param);
        this.$Message.success('添加成功');
        this.userData.roleId = this.roleId;
        await this.getListUser(this.userData);
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
      } finally {
        this.isShowTablesLoading = false;
      }
    },
    // 穿梭框穿梭方法
    addPrepUsers () {
      let obj = this.$refs.departmentUserTree.getSelect();
      let Obj = {};
      let notHave = true;
      Obj.manageName = obj.name;
      Obj.manageId = obj.id;
      for (let i = 0; i < this.prepAddUsers.length; i++) {
        if (this.prepAddUsers[i].manageId === Obj.manageId) {
          notHave = false;
          break;
        }
      }
      if (notHave === true) {
        notHave = false;
        this.submitPrepAddUsersData.userIds.push(Obj.manageId);
        this.prepAddUsers.push(Obj);
      }
    },
    // 获取角色id对应的成员列表方法
    async getListUser (param) {
      this.isShowTablesLoading = true;
      try {
        let response = await roleApi.getListUser(param);
        this.roleList = response.data;
        this.total = response.data.total;
        this.pageSize = response.data.pageSize;
        this.tableData = this.roleList.list;
        await this.getListAllUser();
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
      } finally {
        this.isShowTablesLoading = false;
      }
    },
    // 获取角色id对应的全部成员列表方法
    async getListAllUser () {
      this.$isShowTablesLoading = true;
      try {
        let response = await roleApi.getAllListUser(this.roleId);
        let list = response.data;
        this.prepAddUsers = [];
        for (let i = 0; i < list.length; i++) {
          let object = {};
          object.manageName = list[i].actualName;
          object.manageId = list[i].id;
          this.prepAddUsers.push(object);
        }
      } catch (e) {
        // TODO zhuoda sentry
        console.error(e);
      } finally {
        this.isShowTablesLoading = false;
      }
    },
    // 添加成员方法，
    addUser () {
      this.isShowUserModal = true;
      this.getListAllUser();
      this.submitPrepAddUsersData.UserIds = [];
    }
  }
};
</script>

<style lang="less" scoped >
.shuttle-box {
  position: relative;
  .box {
    border: 1px solid #f0f0f0;
    border-radius: 10px;
    height: 330px;
  }

  .title {
    padding: 10px 0;
    background: #426783;
    font-size: 14px;
    color: #fff;
    text-align: center;
  }
}
</style>

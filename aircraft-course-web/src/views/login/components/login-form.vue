<template>
  <div>
    <Form :model="formData" :rules="rules" @keydown.enter.native="login" ref="loginForm">
      <FormItem prop="loginName">
        <Input placeholder="请输入用户名" v-model="formData.loginName"></Input>
      </FormItem>
      <FormItem prop="loginPwd">
        <Input placeholder="请输入密码" type="password" v-model="formData.loginPwd"></Input>
      </FormItem>
      <FormItem prop="code">
        <Input class="code-input" placeholder="请输入验证码" v-model="formData.code"></Input>
        <img :src="codeUrl" @click="verificationCode" class="codeUrl" v-if="codeUrl" />
      </FormItem>
      <FormItem class="remember">
        <Checkbox v-model="remember">记住密码</Checkbox>
      </FormItem>
      <FormItem>
        <Button :loading="btnLoading" @click="login" long type="primary">登录</Button>
      </FormItem>
    </Form>
  </div>
</template>
<script>
import { loginApi } from '@/api/login';
import { mapGetters } from 'vuex';
import { PRIVILEGE_TYPE } from '@/constants/login';
import sha1 from '@/lib/sha1';
export default {
  name: 'LoginForm',
  props: {
    loginNameRules: {
      type: Array,
      default: () => {
        return [{ required: true, message: '账号不能为空', trigger: 'blur' }];
      }
    },
    loginPwdRules: {
      type: Array,
      default: () => {
        return [{ required: true, message: '密码不能为空', trigger: 'blur' }];
      }
    },
    codedRules: {
      type: Array,
      default: () => {
        return [{ required: true, message: '验证码不能为空', trigger: 'blur' }];
      }
    }
  },
  data () {
    return {
      // 防止重复提交 按钮加载状态
      remember: false,
      btnLoading: false,
      formData: {
        code: '',
        codeUuid: '',
        loginName: 'admin',
        loginPwd: 'admin'
      },
      codeUrl: ''
    };
  },
  computed: {
    rules () {
      return {
        loginName: this.loginNameRules,
        loginPwd: this.loginPwdRules,
        code: this.codedRules
      };
    },
    ...mapGetters(['rememberUser'])
  },
  mounted () {
    this.verificationCode();
    // 判断是否记住我
    this.rememberMe();
  },
  methods: {
    // 获取验证码
    async verificationCode () {
      let result = await loginApi.getVerificationCode();
      let datas = result.data;
      this.formData.codeUuid = datas.uuid;
      this.codeUrl = datas.code;
    },
    rememberMe () {
      if (this.rememberUser) { // 勾选了记住我
        let demo = JSON.parse(this.rememberUser);
        this.formData.loginName = demo.loginName;
        this.formData.loginPwd = demo.loginPwd;
        this.remember = true;
      } else {
        this.remember = false;
        this.formData = {};
      }
    },
    // 登录
    login () {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loginSuccess();
        }
      });
    },
    // 登录 - 异步
    async loginSuccess () {
      try {
        let formData = {
          id: null,
          loginPwd: null,
          loginName: null,
          nickName: null,
          actualName: null,
          phone: null,
          isSuperMan: null,
          departmentId: null,
          departmentName: null
        };
        let rememberUserInfo;
        if (this.rememberUser) {
          rememberUserInfo = JSON.parse(this.rememberUser);
        }
        this.btnLoading = true;
        // 加密
        let pwdAfterSha1 = sha1(this.formData.loginPwd);
        if (!this.remember) { // 第一次没有勾选了记住我或者之前勾选了记住我，然后取消了记住我
          if (rememberUserInfo) { // 之前勾选了记住我，然后取消了记住我。什么也不用做
          } else { // 第一次没有勾选了记住我
            this.formData.loginPwd = pwdAfterSha1;
          }
        } else { // 勾选记住我
          if (rememberUserInfo) { // 有数据了,在mounted函数里面已经赋值了
            formData.loginPwd = rememberUserInfo.loginPwd;
          } else { // 第一次还没有存进数据
            this.formData.loginPwd = pwdAfterSha1;
            formData.loginPwd = pwdAfterSha1;
          }
        }
        let loginResult = await loginApi.login(this.formData);
        let loginInfo = loginResult.data;
        localStorage.clear();
        this.$store.commit('setToken', loginInfo.xaccessToken);
        formData.id = loginInfo.id;
        formData.loginName = loginInfo.loginName;
        formData.nickName = loginInfo.nickName;
        formData.actualName = loginInfo.actualName;
        formData.phone = loginInfo.phone;
        formData.isSuperMan = loginInfo.isSuperMan;
        formData.departmentId = loginInfo.departmentId;
        formData.departmentName = loginInfo.departmentName;

        // 保存用户登录，保存在localStorage
        this.$store.commit('setUserLoginInfo', formData);
        if (this.remember) {
          // 勾选了记住我，保存用户登录，保存在localStorage
          this.$store.commit('setRememberUserInfo', formData);
        }
        // 设置权限
        this.$store.commit('setUserPrivilege', loginInfo.privilegeList);
        this.btnLoading = false;
        // 跳转到首页
        this.$router.push({
          name: this.$config.homeName
        });
      } catch (e) {
        console.error(e);
        this.btnLoading = false;
        this.verificationCode();
      }
    }
  }
};
</script>

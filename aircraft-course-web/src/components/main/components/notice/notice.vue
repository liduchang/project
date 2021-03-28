<template>
  <div class="notice">
    <Tooltip content="消息" placement="bottom">
      <Badge class-name="demo-badge-alone">
        <Icon @click="openNotice" class="demo-badge" size="18" type="icon iconfont iconnews"></Icon>
      </Badge>
    </Tooltip>
  </div>
</template>

<script>
import InfiniteLoading from 'vue-infinite-loading';
export default {
  name: 'Notice',
  components: {
    InfiniteLoading
  },
  data () {
    return {
      loading: false,
      // 是否展示消息列表
      modalOpen: false,
      // 是否展示消息详情
      detailModalOpen: false,
      noticeDetail: {
        title: '',
        content: '',
        updateTime: ''
      }
    };
  },
  mounted () {
  },
  computed: {
  },
  methods: {
    openNotice () {
      console.log('打开消息提示了');
    }
  }
};
</script>

<style lang='less'>
.notice {
  &:hover {
    background: #f7f7f8;
    cursor: pointer;
  }
}
.demo-badge {
  margin: 0 10px;
  border-radius: 6px;
  display: inline-block;
  color: #909ca4;
  font-size: 18px;
  cursor: pointer;
}
.demo-badge-alone {
  background: #f47f92 !important;
  top: 5px !important;
  right: 15px !important;
}

</style>

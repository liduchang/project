<template>
  <Select clearable filterable :multiple="multiple" :style="selectorStyle" v-model="selectValue">
    <Option :key="item.id" :value="item.id" v-for="item in dataList">{{ item.actualName }}</Option>
  </Select>
</template>
<script>
import { userApi } from '@/api/user';
export default {
  name: 'UserSelector',
  props: {
    // 选中的员工
    value: null,
    multiple: {
      type: Boolean,
      default: false
    },
    selectorStyle: {
      type: String,
      default: 'width:180px'
    }
  },
  data () {
    return {
      dataList: [],
      selectValue: this.value
    };
  },
  mounted () {
    this.reset();
    this.query();
  },
  methods: {
    reset () {
      if (this.multiple) {
        this.selectValue = [];
      } else {
        this.selectValue = -1;
      }
    },
    updateSelect (value) {
      this.selectValue = value;
    },
    getSelectValue () {
      return this.selectValue;
    },
    query () {
      (async () => {
        let res = await userApi.getAllUser();
        this.dataList = res.data;
      })();
    }
  }
};
</script>

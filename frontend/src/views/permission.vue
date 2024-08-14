<template>
  <div class="container">

    <div class="mgb20">
      <span class="label">役割：</span>
      <el-select v-model="role" @change="handleChange">
        <el-option label="スーパーアドミニストレーター" value="admin"></el-option>
        <el-option label="一般ユーザー" value="user"></el-option>
      </el-select>
    </div>
    <div class="mgb20 tree-wrapper">
      <el-tree
        ref="tree"
        :data="data"
        node-key="id"
        default-expand-all
        show-checkbox
        :default-checked-keys="checkedKeys"
      />
    </div>
    <el-button type="primary" @click="onSubmit">権限を保存</el-button>
  </div>
</template>

<script setup lang="ts" name="permission">
import { ref } from "vue";
import { ElTree } from "element-plus";
import { usePermissStore } from "../store/permiss";

const role = ref<string>("admin");

interface Tree {
  id: string;
  label: string;
  children?: Tree[];
}

const data: Tree[] = [
  {
    id: "1",
    label: "システムホーム",
  },
  {
    id: "2",
    label: "基本テーブル",
    children: [
      {
        id: "15",
        label: "編集",
      },
      {
        id: "16",
        label: "削除",
      },
    ],
  },
  {
    id: "3",
    label: "タブ選択卡",
  },
  {
    id: "4",
    label: "フォーム関連",
    children: [
      {
        id: "5",
        label: "基本フォーム",
      },
      {
        id: "6",
        label: "ファイルアップロード",
      },
      {
        id: "7",
        label: "三階層メニュー",
        children: [
          {
            id: "8",
            label: "リッチテキストエディター",
          },
          {
            id: "9",
            label: "Markdownエディター",
          },
        ],
      },
    ],
  },
  {
    id: "10",
    label: "カスタムアイコン",
  },
  {
    id: "11",
    label: "Schartチャート",
  },

  {
    id: "13",
    label: "権限管理",
  },
  // {
  // 	id: '14',
  // 	label: '作者サポート'
  // }
];

const permiss = usePermissStore();

// 現在の権限を取得
const checkedKeys = ref<string[]>([]);
const getPremission = () => {
  // インターフェースからのリクエストに基づいて権限を取得
  checkedKeys.value = permiss.defaultList[role.value];
};
getPremission();

// 権限を保存
const tree = ref<InstanceType<typeof ElTree>>();
const onSubmit = () => {
  // 選択された権限を取得
  console.log(tree.value!.getCheckedKeys(false));
};

const handleChange = (val: string[]) => {
  tree.value!.setCheckedKeys(permiss.defaultList[role.value]);
};
</script>

<style scoped>
.tree-wrapper {
  max-width: 500px;
}
.label {
  font-size: 14px;
}
</style>

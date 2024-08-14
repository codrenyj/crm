<!-- 
    メニュー
-->
<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      :default-active="onRoutes"
      :collapse="sidebar.collapse"
      background-color="#324157"
      text-color="#bfcbd9"
      active-text-color="#20a0ff"
      unique-opened
      router
    >
      <template v-for="item in items">
        <template v-if="item.subs">
          <el-sub-menu
            :index="item.index"
            :key="item.index"
            v-permiss="item.permiss"
          >
            <template #title>
              <el-icon>
                <component :is="item.icon"></component>
              </el-icon>
              <span>{{ item.title }}</span>
            </template>
            <template v-for="subItem in item.subs" :key="subItem.index">
              <el-sub-menu
                v-if="subItem.subs"
                :index="subItem.index"
                :key="subItem.index"
                v-permiss="item.permiss"
              >
                <template #title>{{ subItem.title }}</template>
                <el-menu-item
                  v-for="(threeItem, i) in subItem.subs"
                  :key="i"
                  :index="threeItem.index"
                >
                  {{ threeItem.title }}
                </el-menu-item>
              </el-sub-menu>
              <el-menu-item
                v-else
                :index="subItem.index"
                v-permiss="item.permiss"
              >
                {{ subItem.title }}
              </el-menu-item>
            </template>
          </el-sub-menu>
        </template>

        <template v-else>
          <el-menu-item
            :index="item.index"
            :key="item.index"
            v-permiss="item.permiss"
          >
            <el-icon>
              <component :is="item.icon"></component>
            </el-icon>
            <template #title>{{ item.title }}</template>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script setup lang="ts">
import { computed } from "vue";
import { useSidebarStore } from "../store/sidebar";
import { useRoute } from "vue-router";

const items = [
  {
    icon: "Odometer",
    index: "/dashboard",
    title: "システムホーム",
    permiss: "1", //権限の設定と確認
  },
  {
    icon: "Setting",
    index: "/tabs",
    title: "システム設定",
    permiss: "10",
    subs: [
      {
        icon: "Warning",
        index: "/permission",
        title: "権限管理",
        permiss: "13",
      },
    ],
  },
  {
    icon: "Calendar",
    index: "1",
    title: "顧客管理",
    permiss: "2",
    subs: [
      {
        index: "/table",
        title: "見込み顧客管理",
        permiss: "2",
      },
      {
        index: "/import",
        title: "顧客リスト",
        permiss: "2",
      },
    ],
    
  }
];

const route = useRoute();

/*
計算属性 onRoutes を作成し、route.path が変更された場合、onRoutes の値もそれに応じて更新されます。
これは、ルートの変更に応じてレスポンスを更新するためのもので、useRoute() と組み合わせて使用されます。
*/
const onRoutes = computed(() => {
  return route.path;
});

const sidebar = useSidebarStore();
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}

.sidebar::-webkit-scrollbar {
  width: 0;
}

.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}

.sidebar > ul {
  height: 100%;
}
</style>

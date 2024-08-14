<template>
	<el-config-provider :locale="ja">
		<!-- 現在のルートに基づいて、動的にマッチするコンポーネントの内容をレンダリングする -->
		<router-view />  
	</el-config-provider>
</template>

<script setup lang="ts">
import { ElConfigProvider } from 'element-plus';
import ja from 'element-plus/es/locale/lang/ja';
</script>
<style>
@import './assets/css/main.css';
@import './assets/css/color-dark.scss';
</style>

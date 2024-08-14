<template>
	<el-descriptions title="" :column="2" border>
		<el-descriptions-item>
			<template #label> ID </template>
			{{ data.id }}
		</el-descriptions-item>
		<el-descriptions-item>
			<template #label> 名前 </template>
			{{ data.name }}
		</el-descriptions-item>
		<el-descriptions-item>
			<template #label> 性別 </template>
			{{ data.sex }}
		</el-descriptions-item>
		<el-descriptions-item>
			<template #label> 状態 </template>
			{{ data.status ? '正常' : '异常' }}
		</el-descriptions-item>
		<el-descriptions-item :span="2">
			<template #label> 電話番号 </template>
			{{ data.mobile }}
		</el-descriptions-item>
		<el-descriptions-item>
			<template #label> 作成時間 </template>
			{{ data.createdAt }}
		</el-descriptions-item>
		<el-descriptions-item>
			<template #label> 更新時間 </template>
			{{ data.updatedAt }}
		</el-descriptions-item>
		<!-- <el-descriptions-item>
			<template #label> 头像 </template>
			<img :src="data.thumb" style="width: 120px" alt="" />
		</el-descriptions-item> -->
	</el-descriptions>
</template>

<script lang="ts" setup>
const props = defineProps({
	data: {
		type: Object,
		required: true
	}
});
</script>

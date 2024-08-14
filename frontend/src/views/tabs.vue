<template>
	<div class="container">
		<el-tabs v-model="message">
			<el-tab-pane :label="`未読メッセージ(${state.unread.length})`" name="first">
				<el-table :data="state.unread" :show-header="false" style="width: 100%">
					<el-table-column>
						<template #default="scope">
							<span class="message-title">{{ scope.row.title }}</span>
						</template>
					</el-table-column>
					<el-table-column prop="date" width="180"></el-table-column>
					<el-table-column width="120">
						<template #default="scope">
							<el-button size="small" @click="handleRead(scope.$index)">既読にする</el-button>
						</template>
					</el-table-column>
				</el-table>
				<div class="handle-row">
					<el-button type="primary">すべて既読としてマークする</el-button>
				</div>
			</el-tab-pane>
			<el-tab-pane :label="`既読メッセージ(${state.read.length})`" name="second">
				<template v-if="message === 'second'">
					<el-table :data="state.read" :show-header="false" style="width: 100%">
						<el-table-column>
							<template #default="scope">
								<span class="message-title">{{ scope.row.title }}</span>
							</template>
						</el-table-column>
						<el-table-column prop="date" width="180"></el-table-column>
						<el-table-column width="120">
							<template #default="scope">
								<el-button type="danger" size="small" @click="handleDel(scope.$index)">削除</el-button>
							</template>
						</el-table-column>
					</el-table>
					<div class="handle-row">
						<el-button type="danger">すべて削除</el-button>
					</div>
				</template>
			</el-tab-pane>
			<el-tab-pane :label="`ゴミ箱(${state.recycle.length})`" name="third">
				<template v-if="message === 'third'">
					<el-table :data="state.recycle" :show-header="false" style="width: 100%">
						<el-table-column>
							<template #default="scope">
								<span class="message-title">{{ scope.row.title }}</span>
							</template>
						</el-table-column>
						<el-table-column prop="date" width="180"></el-table-column>
						<el-table-column width="120">
							<template #default="scope">
								<el-button size="small" @click="handleRestore(scope.$index)">元に戻す</el-button>
							</template>
						</el-table-column>
					</el-table>
					<div class="handle-row">
						<el-button type="danger">ごみ箱を空にする</el-button>
					</div>
				</template>
			</el-tab-pane>
		</el-tabs>
	</div>
</template>

<script setup lang="ts" name="tabs">
import { ref, reactive } from 'vue';

const message = ref('first');
const state = reactive({
	unread: [
		{
			date: '2018-04-19 20:00:00',
			title: '【システムに関するお知らせ】 今夜2:00～5:00の間、システム改修及びメンテナンスを実施いたします。'
		},
		{
			date: '2018-04-19 21:00:00',
			title: '大きな赤い封筒は今夜12時に先着順に配布されます'
		}
	],
	read: [
		{
			date: '2018-04-19 20:00:00',
			title: '【システム通知】このシステムは今夜の午前2時から午前5時までアップグレードとメンテナンスを行います。'
		}
	],
	recycle: [
		{
			date: '2018-04-19 20:00:00',
			title: '【システム通知】このシステムは今夜の午前2時から午前5時までアップグレードとメンテナンスを行います。'
		}
	]
});

const handleRead = (index: number) => {
	const item = state.unread.splice(index, 1);
	state.read = item.concat(state.read);
};
const handleDel = (index: number) => {
	const item = state.read.splice(index, 1);
	state.recycle = item.concat(state.recycle);
};
const handleRestore = (index: number) => {
	const item = state.recycle.splice(index, 1);
	state.read = item.concat(state.read);
};
</script>

<style>
.message-title {
	cursor: pointer;
}
.handle-row {
	margin-top: 30px;
}
</style>

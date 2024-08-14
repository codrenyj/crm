<template>
	<div>
		<div class="container">
			<div class="search-box">
				<el-input v-model="query.name" placeholder="名前/ID" class="search-input mr10" clearable></el-input>
				<el-button type="primary" :icon="Search" @click="handleSearch">検索</el-button>
				<el-button type="warning" :icon="CirclePlusFilled" @click="visible = true">新規</el-button>
			</div>
			<el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
				<el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
				<el-table-column prop="name" label="名前" align="center"></el-table-column>
				<el-table-column label="性別" align="center">
					<template #default="scope">{{ scope.row.sex }}</template>
				</el-table-column>
				<el-table-column prop="mobile" label="電話番号" align="center"></el-table-column>
				<el-table-column label="状態" align="center">
					<template #default="scope">
						<el-tag :type="scope.row.status ? 'success' : 'danger'">
							{{ scope.row.status ? '正常' : '異常' }}
						</el-tag>
					</template>
				</el-table-column>

				<el-table-column prop="creatDate" label="作成時間" align="center"></el-table-column>
				<el-table-column label="操作" width="280" align="center">
					<template #default="scope">
						<el-button type="warning" size="small" :icon="View" @click="handleView(scope.row)">
							参照
						</el-button>
						<el-button type="primary" size="small" :icon="Edit" @click="handleEdit(scope.$index, scope.row)"
							v-permiss="15">
							修正
						</el-button>
						<el-button type="danger" size="small" :icon="Delete" @click="handleDelete(scope.row.id)" v-permiss="16">
							削除
						</el-button>
					</template>
				</el-table-column>
			</el-table>
			<div class="pagination">
				<el-pagination background layout="total, prev, pager, next" :current-page="query.pageIndex"
					:page-size="query.pageSize" :total="pageTotal" @current-change="handlePageChange"></el-pagination>
			</div>
		</div>
		<el-dialog :title="idEdit ? '情報の修正' : '新規顧客の新規'" v-model="visible" width="500px" destroy-on-close
			:close-on-click-modal="false" @close="closeDialog">
			<TableEdit :data="rowData" :edit="idEdit" :update="updateData" />
		</el-dialog>
		<el-dialog title="顧客情報の詳細" v-model="visible1" width="700px" destroy-on-close>
			<TableDetail :data="rowData" />
		</el-dialog>
	</div>
</template>

<script setup lang="ts" name="basetable">
import { ref, reactive } from 'vue';
import { ElMessage, ElMessageBox } from 'element-plus';
import { Delete, Edit, Search, CirclePlusFilled, View } from '@element-plus/icons-vue';
import Api from '@/api/index'
// import { fetchData } from '../api/index';
// import TableEdit from '../components/table-edit.vue';
// import TableDetail from '../components/table-detail.vue';

interface TableItem {
	id: number;
	name: string;
	thumb: string;
	sex: string;
	status: string;
	mobile: string;
}

const query = reactive({
	creatDate: '',
	name: '',
	pageIndex: 1,
	pageSize: 10
});
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
// テーブルデータの取得
const getData = async () => {

	// const res = await fetchData();
	/*
	const res = {
		"list": [{
			"id": 1,
			"name": "鈴木",
			"gender": "男",
			"tel": "08055554444",
			"state": "成功",
			"creatDate": "2023-11-01",
			"thumb": "https://lin-xin.gitee.io/images/post/wms.png"
		},
		{
			"id": 2,
			"name": "松子",
			"gender": "女",
			"tel": "030599954444",
			"state": "成功",
			"creatDate": "2023-10-11",
			"thumb": "https://lin-xin.gitee.io/images/post/node3.png"
		},
		{
			"id": 3,
			"name": "宫本",
			"gender": "男",
			"tel": "05065558444",
			"state": "失敗",
			"creatDate": "2023-11-11",
			"thumb": "https://lin-xin.gitee.io/images/post/parcel.png"
		},
		{
			"id": 4,
			"name": "丰田",
			"gender": "男",
			"tel": "060587654444",
			"state": "成功",
			"creatDate": "2019-23-20",
			"thumb": "https://lin-xin.gitee.io/images/post/notice.png"
		}
		],
		"pageTotal": 4
	}
		*/

	Api.get("/api/custom/list",query).then(function(r){
		console.log(r)

		tableData.value = r.data.list;
		pageTotal.value = r.data.total ;
	});

};
getData();

// 検索
const handleSearch = () => {
	query.pageIndex = 1;
	getData();

	
};
// ページネーションナビゲーション
const handlePageChange = (val: number) => {
	query.pageIndex = val;
	getData();
};

// 削除
const handleDelete = (id: number) => {
	// 削除の二次確認
	ElMessageBox.confirm('削除してもよろしいですか？', 'Tip', {
		type: 'warning',
		confirmButtonText: '確認',
		cancelButtonText: 'キャンセル',
	})
		.then(() => {

			Api.post("/api/custom/delete",{"id":id}).then(function(r){

				ElMessage.success('削除成功');

				getData();

			});

			//tableData.value.splice(index, 1);
		})
		.catch(() => { });
};

const visible = ref(false);
let idx: number = -1;
const idEdit = ref(false);
const rowData = ref({});
const handleEdit = (index: number, row: TableItem) => {
	idx = index;
	rowData.value = row;
	idEdit.value = true;
	visible.value = true;
};
const updateData = (row: TableItem) => {
	idEdit.value ? (tableData.value[idx] = row) : tableData.value.unshift(row);
	console.log(tableData.value);
	closeDialog();
};

const closeDialog = () => {
	visible.value = false;
	idEdit.value = false;
};

const visible1 = ref(false);
const handleView = (row: TableItem) => {
	rowData.value = row;
	visible1.value = true;
};
</script>

<style scoped>
.search-box {
	margin-bottom: 20px;
}

.search-input {
	width: 200px;
}

.mr10 {
	margin-right: 10px;
}

.table-td-thumb {
	display: block;
	margin: auto;
	width: 40px;
	height: 40px;
}
</style>

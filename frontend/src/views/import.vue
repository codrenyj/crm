<template>
    <div>
        <div class="container">
            <div class="handle-box">
                <el-upload
                    action="#"
                    :limit="1"
                    accept=".xlsx, .xls"
                    :show-file-list="false"
                    :before-upload="beforeUpload"
                    :http-request="handleMany"
                >
                    <el-button class="mr10" type="success">一括インポート</el-button>
                </el-upload>
                <el-link href="/template.xlsx" target="_blank">テンプレートをダウンロード</el-link>
            </div>
            <el-table :data="tableData" border class="table" header-cell-class-name="table-header">
                <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
                <el-table-column prop="name" label="名前"></el-table-column>
                <el-table-column prop="sex" label="性别"></el-table-column>
                <el-table-column prop="sno" label="電話番号"></el-table-column>
                <el-table-column prop="class" label="状態"></el-table-column>
                <el-table-column prop="age" label="作成時間"></el-table-column>
            </el-table>
        </div>
    </div>
</template>

<script setup lang="ts" name="import">
import { UploadProps } from 'element-plus';
import { ref, reactive } from 'vue';
import * as XLSX from 'xlsx';

interface TableItem {
    id: number;
    name: string;
    sno: string;
    class: string;
    age: string;
    sex: string;
}

const tableData = ref<TableItem[]>([]);
// テーブルデータを取得する
const getData = () => {
    tableData.value = [
        {
            id: 1,
            name: '成田',
            sno: '08054512266',
            class: '正常',
            age: '2023-01-22',
            sex: '男',
        },
        {
            id: 2,
            name: '长泽',
            sno: '07099665847',
            class: '正常',
            age: '2023-12-22',
            sex: '女',
        },
    ];
};
getData();

const importList = ref<any>([]);
const beforeUpload: UploadProps['beforeUpload'] = async (rawFile) => {
    importList.value = await analysisExcel(rawFile);
    return true;
};
const analysisExcel = (file: any) => {
    return new Promise(function (resolve, reject) {
        const reader = new FileReader();
        reader.onload = function (e: any) {
            const data = e.target.result;
            let datajson = XLSX.read(data, {
                type: 'binary',
            });

            const sheetName = datajson.SheetNames[0];
            const result = XLSX.utils.sheet_to_json(datajson.Sheets[sheetName]);
            resolve(result);
        };
        reader.readAsBinaryString(file);
    });
};

const handleMany = async () => {
    // データをサーバーに送信した後、最新のリストを取得します。これは単なる例であり、リクエストは行いません
    const list = importList.value.map((item: any, index: number) => {
        return {
            id: index,
            name: item['氏名'],
            sno: item['学生番号'],
            class: item['クラス'],
            age: item['年齢'],
            sex: item['性別'],
        };
    });
    tableData.value.push(...list);
};
</script>

<style scoped>
.handle-box {
    display: flex;
    margin-bottom: 20px;
}

.table {
    width: 100%;
    font-size: 14px;
}
.mr10 {
    margin-right: 10px;
}
</style>

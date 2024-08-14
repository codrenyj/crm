<template>
    <div class="container">
        <div class="form-box">
            <el-form ref="formRef" :rules="rules" :model="form" label-width="80px">
                <el-form-item label="フォーム名" prop="name">
                    <el-input v-model="form.name"></el-input>
                </el-form-item>
                <el-form-item label="セレクタ" prop="region">
                    <el-select v-model="form.region" placeholder="選択してください">
                        <el-option key="小明" label="小明" value="小明"></el-option>
                        <el-option key="小红" label="小红" value="小红"></el-option>
                        <el-option key="小白" label="小白" value="小白"></el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="日付時間">
                    <el-col :span="11">
                        <el-form-item prop="date1">
                            <el-date-picker
                                type="date"
                                placeholder="日付を選択"
                                v-model="form.date1"
                                style="width: 100%"
                            ></el-date-picker>
                        </el-form-item>
                    </el-col>
                    <el-col class="line" :span="2">-</el-col>
                    <el-col :span="11">
                        <el-form-item prop="date2">
                            <el-time-picker placeholder="時間を選択" v-model="form.date2" style="width: 100%">
                            </el-time-picker>
                        </el-form-item>
                    </el-col>
                </el-form-item>
                <el-form-item label="都市カスケード" prop="options">
                    <el-cascader :options="options" v-model="form.options"></el-cascader>
                </el-form-item>
                <el-form-item label="スイッチ選択" prop="delivery">
                    <el-switch v-model="form.delivery"></el-switch>
                </el-form-item>
                <el-form-item label="チェックボックス" prop="type">
                    <el-checkbox-group v-model="form.type">
                        <el-checkbox label="小明" name="type"></el-checkbox>
                        <el-checkbox label="小红" name="type"></el-checkbox>
                        <el-checkbox label="小白" name="type"></el-checkbox>
                    </el-checkbox-group>
                </el-form-item>
                <el-form-item label="ラジオボタン" prop="resource">
                    <el-radio-group v-model="form.resource">
                        <el-radio label="小明"></el-radio>
                        <el-radio label="小红"></el-radio>
                        <el-radio label="小白"></el-radio>
                    </el-radio-group>
                </el-form-item>
                <el-form-item label="テキストエリア" prop="desc">
                    <el-input type="textarea" rows="5" v-model="form.desc"></el-input>
                </el-form-item>
                <el-form-item>
                    <el-button type="primary" @click="onSubmit(formRef)">フォームを提出する</el-button>
                    <el-button @click="onReset(formRef)">フォームをリセットする</el-button>
                </el-form-item>
            </el-form>
        </div>
    </div>
</template>

<script setup lang="ts" name="baseform">
import { reactive, ref } from 'vue';
import { ElMessage } from 'element-plus';
import type { FormInstance, FormRules } from 'element-plus';

const options = [
    {
        value: 'guangdong',
        label: '広東省',
        children: [
            {
                value: 'guangzhou',
                label: '広州市',
                children: [
                    {
                        value: 'tianhe',
                        label: '天河区',
                    },
                    {
                        value: 'haizhu',
                        label: '海珠区',
                    },
                ],
            },
            {
                value: 'dongguan',
                label: '東莞市',
                children: [
                    {
                        value: 'changan',
                        label: '長安鎮',
                    },
                    {
                        value: 'humen',
                        label: '虎門鎮',
                    },
                ],
            },
        ],
    },
    {
        value: 'hunan',
        label: '湖南省',
        children: [
            {
                value: 'changsha',
                label: '長沙市',
                children: [
                    {
                        value: 'yuelu',
                        label: '岳麓区',
                    },
                ],
            },
        ],
    },
];
const rules: FormRules = {
    name: [{ required: true, message: 'フォーム名を入力してください', trigger: 'blur' }],
};
const formRef = ref<FormInstance>();
const form = reactive({
    name: '',
    region: '',
    date1: '',
    date2: '',
    delivery: true,
    type: ['小明'],
    resource: '小红',
    desc: '',
    options: [],
});
// 提出
const onSubmit = (formEl: FormInstance | undefined) => {
    // フォーム検証
    if (!formEl) return;
    formEl.validate((valid) => {
        if (valid) {
            console.log(form);
            ElMessage.success('提出成功！');
        } else {
            return false;
        }
    });
};
// リセット
const onReset = (formEl: FormInstance | undefined) => {
    if (!formEl) return;
    formEl.resetFields();
};
</script>

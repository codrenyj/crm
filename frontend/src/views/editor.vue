<template>
    <div class="container">
        <div class="plugins-tips">
            wangEditor：軽量なwebリッチテキストエディター、設定が便利で、使用が簡単です。 アクセスアドレス：
            <a href="https://www.wangeditor.com/doc/" target="_blank">wangEditor</a>
        </div>
        <div style="border: 1px solid #ccc; margin-bottom: 10px">
            <Toolbar style="border-bottom: 1px solid #ccc" :editor="editorRef" :defaultConfig="toolbarConfig" />
            <Editor
                style="height: 500px; overflow-y: hidden"
                v-model="valueHtml"
                :defaultConfig="editorConfig"
                @onCreated="handleCreated"
            />
        </div>
        <el-button type="primary" @click="syncHTML">送信</el-button>
    </div>
</template>

<script setup lang="ts" name="editor">
import '@wangeditor/editor/dist/css/style.css'; // CSSをインポート
import { onBeforeUnmount, ref, reactive, shallowRef, onMounted } from 'vue';
import { Editor, Toolbar } from '@wangeditor/editor-for-vue';
// エディターインスタンスはshallowRefを使う必要があります
const editorRef = shallowRef();

//  HTMLコンテンツ
const valueHtml = ref('<p>hello</p>');

// 模擬ajax非同期でコンテンツを取得
onMounted(() => {
    setTimeout(() => {
        valueHtml.value = '<p>模擬Ajax非同期でコンテンツを設定</p>';
    }, 1500);
});

const toolbarConfig = {};
const editorConfig = { placeholder: '内容を入力してください...' };

// コンポーネントの破棄時にエディターも同時に破棄
onBeforeUnmount(() => {
    const editor = editorRef.value;
    if (editor == null) return;
    editor.destroy();
});

const handleCreated = (editor: any) => {
    editorRef.value = editor; // エディターインスタンスを記録、重要！
};
const syncHTML = () => {
    console.log(valueHtml.value);
};
</script>

<style></style>

<template>
    <div class="container">
        <div class="content-title">ドラッグ＆ドロップ対応</div>
        <el-upload
            class="upload-demo"
            drag
            action="http://jsonplaceholder.typicode.com/api/posts/"
            multiple
            :on-change="handle"
        >
            <el-icon class="el-icon--upload"><upload-filled /></el-icon>
            <div class="el-upload__text">
                ファイルをここにドラッグするか、
                <em>クリックしてアップロード</em>
            </div>
        </el-upload>

        <div class="content-title">クロッピング対応（拡張機能）</div>
        <div class="plugins-tips">
            vue-cropperjs：cropperjsを包んだVueコンポーネント。 訪問アドレス：
            <a href="https://github.com/Agontuk/vue-cropperjs" target="_blank">vue-cropperjs</a>。 サンプルは
            <router-link to="/user">個人センター</router-link>
        </div>
    </div>
</template>

<script setup lang="ts">
const handle = (rawFile: any) => {
    console.log(rawFile);
};
</script>

<style scoped>
.content-title {
    font-weight: 400;
    line-height: 50px;
    margin: 10px 0;
    font-size: 22px;
    color: #1f2f3d;
}
.upload-demo {
    width: 360px;
}
</style>

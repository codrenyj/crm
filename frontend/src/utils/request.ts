import axios, { AxiosInstance, AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

const service: AxiosInstance = axios.create({
    timeout: 5000  //リクエストが5秒以内に応答されない場合、タイムアウトエラーが発生します
});

//リクエストインターセプター
service.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        // リクエストが送信される前の処理ロジック
        return config;
    },
    (error: AxiosError) => {
        // リクエストエラー発生時の処理ロジック
        console.log(error);
        return Promise.reject();
    }
);

//レスポンスインターセプター
service.interceptors.response.use(
    (response: AxiosResponse) => {
        // レスポンス受信後の処理ロジック
        if (response.status === 200) { //200通常表示为请求成功
            return response;
        } else {
            Promise.reject();
        }
    },
    (error: AxiosError) => {
        // レスポンスエラー発生時の処理ロジック
        console.log(error);
        return Promise.reject();
    }
);

export default service;

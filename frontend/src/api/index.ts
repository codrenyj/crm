import axios, { AxiosInstance, AxiosError, AxiosResponse, InternalAxiosRequestConfig } from 'axios';

import { useUserStore } from '@/store/module/user' ;


const api: AxiosInstance = axios.create({
    timeout: 5000  //リクエストが5秒以内に応答されない場合、タイムアウトエラーが発生します
});

//リクエストインターセプター
api.interceptors.request.use(
    (config: InternalAxiosRequestConfig) => {
        // リクエストが送信される前の処理ロジック
      
        const userStore = useUserStore();

        console.log(userStore.token);

        var token = userStore.token;

        //config.headers["token"]=token;
        config.headers['Authorization'] = token;

        return config;


    },
    (error: AxiosError) => {
        // リクエストエラー発生時の処理ロジック
        console.log(error);
        return Promise.reject();
    }

    


);

//レスポンスインターセプター
api.interceptors.response.use(
    (response: AxiosResponse) => {
        // レスポンス受信後の処理ロジック
        if (response.status === 200) { //200通常表示为请求成功
            return response.data;
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


/*
export interface ApiResult<T> {
    code: number;
    message: string;
    data: T;
}
export async function GET<T>(url: string, params?: any): Promise<ApiResult<T>> {
    const response = await request.get<ApiResult<T>>(url, { params });
    return response.data;
}
export async function POST<T>(url: string, data?: any): Promise<ApiResult<T>> {
    const response = await request.post<ApiResult<T>>(url, data);
    return response.data;
}
*/



var Api = {
  // 发起GET请求
  get:function(url,params) {
    return api.get(url, { params: params });
  },

  // 发起POST请求
  post:function(url,params) {
    var config = {
      headers: {
        'Content-Type': 'multipart/form-data'
      }
    };

    return api.post(url, params,config);
  },


  // 发起POST JSON 请求
   postJson:function(url,params) 
  {

    return api.post(url, params);
  }

}

export default Api;
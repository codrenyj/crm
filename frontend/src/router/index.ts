import { createRouter, createWebHashHistory, RouteRecordRaw } from 'vue-router';
import { usePermissStore } from '../store/permiss';
import Home from '../views/home.vue';
import { useUserStore } from '@/store/module/user' ;
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'

/*
    createRouter: これはルーターインスタンスを作成するためのファクトリ関数です。
    これを呼び出すことで、アプリケーションのルーティングを管理するオブジェクトを取得できます。
*/

/*
    createWebHashHistory: これはハッシュモードを基にしたルーティング履歴管理インスタンスを作成するためのものです。
    シングルページアプリケーション (SPA) では、これは通常、フロントエンドのルーティングを処理するために使用されます。
*/

/*
    RouteRecordRaw: これは、ルートの設定情報を記述するためのインターフェースです。
    通常、このインターフェースを使用して、パス、コンポーネントなどのルートの設定を定義します。
*/

const routes: RouteRecordRaw[] = [
    {
        path: '/', 
        redirect: '/dashboard', //リダイレクト（pathパスにアクセスした場合、xxxパスにリダイレクトする）
    },
    {
        path: '/',
        name: 'Home',
        component: Home,
        children: [
            {
                path: '/dashboard',
                name: 'dashboard',
                meta: {
                    title: 'ホームページ',
                    permiss: '1',
                },
                component: () => import(/* webpackChunkName: "dashboard" */ '../views/dashboard.vue'),
            },
            {
                path: '/table',
                name: 'basetable',
                meta: {
                    title: '表',
                    permiss: '2',
                },
                component: () => import(/* webpackChunkName: "table" */ '../views/table.vue'),
            },
            // {
            //     path: '/charts',
            //     name: 'basecharts',
            //     meta: {
            //         title: 'チャート',
            //         permiss: '11',
            //     },
            //     component: () => import(/* webpackChunkName: "charts" */ '../views/charts.vue'),
            // },
            {
                path: '/tabs',
                name: 'tabs',
                meta: {
                    title: 'tabタグ',
                    permiss: '3',
                },
                component: () => import(/* webpackChunkName: "tabs" */ '../views/tabs.vue'),
            },
            {
                path: '/form',
                name: 'baseform',
                meta: {
                    title: 'フォーム',
                    permiss: '5',
                },
                component: () => import(/* webpackChunkName: "form" */ '../views/form.vue'),
            },
            {
                path: '/permission',
                name: 'permission',
                meta: {
                    title: '権限管理',
                    permiss: '13',
                },
                component: () => import(/* webpackChunkName: "permission" */ '../views/permission.vue'),
            },
            {
                path: '/upload',
                name: 'upload',
                meta: {
                    title: 'アップロードプラグイン',
                    permiss: '6',
                },
                component: () => import(/* webpackChunkName: "upload" */ '../views/upload.vue'),
            },
            {
                path: '/icon',
                name: 'icon',
                meta: {
                    title: 'アイコン',
                    permiss: '10',
                },
                component: () => import(/* webpackChunkName: "icon" */ '../views/icon.vue'),
            },
            {
                path: '/user',
                name: 'user',
                meta: {
                    title: 'ユーザセンター',
                },
                component: () => import(/* webpackChunkName: "user" */ '../views/user.vue'),
            },
            {
                path: '/editor',
                name: 'editor',
                meta: {
                    title: 'リッチテキストエディタ',
                    permiss: '8',
                },
                component: () => import(/* webpackChunkName: "editor" */ '../views/editor.vue'),
            },
            // {
            //     path: '/markdown',
            //     name: 'markdown',
            //     meta: {
            //         title: 'Markdownエディタ',
            //         permiss: '9',
            //     },
            //     component: () => import(/* webpackChunkName: "markdown" */ '../views/markdown.vue'),
            // },
            // {
            //     path: '/export',
            //     name: 'export',
            //     meta: {
            //         title: 'Excelエクスポート',
            //         permiss: '2',
            //     },
            //     component: () => import(/* webpackChunkName: "export" */ '../views/export.vue'),
            // },
            {
                path: '/import',
                name: 'import',
                meta: {
                    title: 'Excelインポート',
                    permiss: '2',
                },
                component: () => import(/* webpackChunkName: "import" */ '../views/import.vue'),
            },
        ],
    },
    {
        path: '/login',
        name: 'Login',
        meta: {
            title: 'ログイン',
        },
        component: () => import(/* webpackChunkName: "login" */ '../views/login.vue'),
    },
    {
        path: '/403',
        name: '403',
        meta: {
            title: 'アクセス権がない',
        },
        component: () => import(/* webpackChunkName: "403" */ '../views/403.vue'),
    },
];

const router = createRouter({
    history: createWebHashHistory(),
    routes,
});


/*
    to: 目的ルートオブジェクトに進むことを示します。
    from: 現在ナビゲートして離れようとしているルートオブジェクトを示します。
    next: 関数であり、次のフックに進むために使用されます。next()を呼び出すと、ナビゲーションが続行されます；
    next(false)を呼び出すと、ナビゲーションが中断されます；
    next('/path')を呼び出すと、指定されたパスにリダイレクトされます。
*/


//グローバル前置ルーター
router.beforeEach((to, from, next) => {
    NProgress.start();

    const userStore = useUserStore()
    const token = userStore.token;

    const whiteList = ['/login'];  // no redirect whitelist



    if (whiteList.indexOf(to.path) !== -1) {
        // in the free login whitelist, go directly
        next() ;
    }
    else 
    {
        if (token) {
            // other pages that do not have permission to access are redirec    ted to the login page.
            next();
        }
        if (token) {
            // other pages that do not have permission to access are redirec    ted to the login page.
            next();
        }
        else {
            next(`/login?redirect=${to.path}`);
        }

        }
        
        //NProgress.done()
});

//後置ルーター
router.afterEach(() => {
    NProgress.done() //コンポーネントの読み込み完了後、プログレスバーの表示をキャンセルする
})

export default router;

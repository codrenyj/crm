import { defineStore } from 'pinia';

interface ObjectList {
	[key: string]: string[];
}

export const usePermissStore = defineStore('permiss', {
	state: () => { //状態を初期化する
		const keys = localStorage.getItem('ms_keys'); //Id
		return {
			key: keys ? JSON.parse(keys) : <string[]>[],
			defaultList: <ObjectList>{
				admin: ['1', '2', '3', '4', '5', '6', '7', '8', '9', '10', '11', '12', '13', '14', '15', '16'],
				user: ['1', '2', '3', '11', '13', '14', '15']
			}
		};
	},
	actions: { //状態を更新する
		handleSet(val: string[]) {
			this.key = val;
		}
	}
});

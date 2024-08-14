import { defineStore } from 'pinia'

export const useUserStore = defineStore('user', {
    persist: true,

    state: () => ({
        
            id:"",
            username:"",
            token:"",
    }),


    actions: {
      setUser(id,username,token) {
        this.id = id
        this.username = username
        this.token = token
      },
    },
  })

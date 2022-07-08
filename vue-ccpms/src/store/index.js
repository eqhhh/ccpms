import { createStore } from 'vuex'

export default createStore({
  state: {
    user: {}
  },
  getters: {
    getUser: (state) => state.user
  },
  mutations: {
    SET_USER(state, user) {
      state.user = user
    }
  },
  actions: {
    SET_USER({commit}, user) {
      this.state.user = user
    }
  },
  modules: {
  }
})

import Vue from 'vue'
import App from './App.vue'
import Vuex from 'vuex'
import VueRouter from 'vue-router'
import {routes} from './routes'
import {BootstrapVue, IconsPlugin} from 'bootstrap-vue'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import {authorizationStorage} from "./App";
import cors from 'cors';
import VueApexCharts from 'vue-apexcharts';

Vue.config.productionTip = false;

Vue.use(VueRouter);
Vue.use(BootstrapVue);
Vue.use(IconsPlugin);
Vue.use(Vuex);
Vue.use(cors);
Vue.component('apex-chart', VueApexCharts);

const router = new VueRouter({routes});

const store = new Vuex.Store({
    state: {
        user: null,
        token: null
    },
    mutations: {
        login(state, payload) {
            state.user = payload.user;
            state.token = payload.token;
            authorizationStorage.setAuthorization(payload.token, payload.user);
        },
        logout(state) {
            state.user = null;
            state.token = null;
            authorizationStorage.clear();
        },
        updateUser(state, payload) {
            state.user = payload.user;
            authorizationStorage.setUserData(payload.user);
        }
    },
    getters: {
        isAuthorized: state => {
            return state.token != null;
        }
    }
});

router.beforeEach((to, from, next) => {
    if (to.matched.some(record => record.meta.requiredAuth)) {
        if (!authorizationStorage.isAuthorized()) {
            next({path: '/login'});
        } else {
            next();
        }
    } else if (to.matched.some(record => record.meta.requiredNotAuth)) {
        if (authorizationStorage.isAuthorized()) {
            next({path: '/profile'});
        } else {
            next();
        }
    } else {
        next();
    }
});

new Vue({
    render: h => h(App),
    router,
    store
}).$mount('#app');

import Home from './components/Home'
import Schedule from "./components/Schedule";
import Match from "./components/Match";
import LoginView from "./components/LoginView";
import Profile from "./components/Profile";
import CreateAccount from "./components/CreateAccount";
import PageNotFound from "./components/PageNotFound";
import UsersRanking from "./components/UsersRanking";
import User from "./components/User";

export const routes = [
    {path: '/', name: 'home', component: Home},
    {path: '/schedule', name: 'schedule', component: Schedule},
    {path: '/match/:id', name: 'match', component: Match},
    {path: '/login', name: 'login', component: LoginView, meta: {requiredNotAuth: true}},
    {path: '/profile', name: 'profile', component: Profile, meta: {requiredAuth: true}},
    {path: '/create-account', name: 'create-account', component: CreateAccount, meta: {requiredNotAuth: true}},
    {path: '/users-ranking', name: 'users-ranking', component: UsersRanking},
    {path: '/user/:id', name: 'user', component: User},
    {path: '*', name: 'not-found', component: PageNotFound}
];

import Home from './components/Home'
import Schedule from "./components/Schedule";
import Match from "./components/Match";
import LoginView from "./components/LoginView";
import Profile from "./components/Profile";

export const routes = [
    {path: '/', name: 'home', component: Home},
    {path: '/schedule', name: 'schedule', component: Schedule},
    {path: '/match/:id', name: 'match', component: Match},
    {path: '/login', name: 'login', component: LoginView},
    {path: '/profile', name: 'profile', component: Profile}
];

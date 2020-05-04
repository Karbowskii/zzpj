import Home from './components/Home'
import Schedule from "./components/Schedule";
import Match from "./components/Match";

export const routes = [
    {path: '/', name: 'home', component: Home},
    {path: '/schedule', name: 'schedule', component: Schedule},
    {path: '/match/:id', name: 'match', component: Match}
];

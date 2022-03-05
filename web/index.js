import {Home} from './pages/Home.js';
import {Join} from './pages/Join.js';
import {Login} from './pages/Login.js';
import {Nav} from './components/Nav.js';
import {OAuthJoin} from "./pages/OAuthJoin.js";

const root = document.getElementById('body');
const navigation = document.getElementById('navigation');

const pages = [
    {path: contextPath + "/", component: Home, id: "body"},
    {path: contextPath + "/home", component: Home, id: "body"},
    {path: contextPath + "/join", component: Join, id: "body"},
    {path: contextPath + '/login', component: Login, id: "body"},
    {path: contextPath + '/kakaojoin', component: OAuthJoin, id: "body", param: "kakao"},
];

const render = async path => {
    try {
        const p = pages.find(page => page.path === path);
        let component;
        if (!p) {
            window.location.href = contextPath + "/error";
        } else {
            component = p.component;
        }
        if (p.param !== undefined) {
            component(root, p.param);
        } else {
            component(root);
        }
    } catch (err) {
        console.error(err);
    }
};

Nav(render);

window.addEventListener('popstate', () => {
    console.log('[Pop State]', window.location.pathname);
    render(window.location.pathname);
});

render(window.location.pathname);
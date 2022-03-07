import {Home} from './pages/Home.js';
import {Join} from './pages/Join.js';
import {Login} from './pages/Login.js';
import {Nav} from './components/Nav.js';
import {OAuthJoin} from "./pages/OAuthJoin.js";
import {Mypage} from "./pages/Mypage.js";
import {BookSubmit} from "./pages/BookSubmit.js";

const root = document.getElementById('body');
const navigation = document.getElementById('navigation');

const pages = [
    {path: contextPath + "/", component: Home, id: "body"},
    {path: contextPath + "/home", component: Home, id: "body"},
    {path: contextPath + "/join", component: Join, id: "body"},
    {path: contextPath + '/login', component: Login, id: "body"},
    {path: contextPath + '/kakaojoin', component: OAuthJoin, id: "body", param: "kakao"},
    {path: contextPath + '/mypage', component: Mypage, id: "body"},
    {path: contextPath + '/booksubmit', component: BookSubmit, id: "body"},
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
            component(document.getElementById(p.id), p.param);
        } else {
            component(document.getElementById(p.id));
        }
    } catch (err) {
        console.error(err);
    }
};

Nav(render);

const preventPopStatePages = [
    {name: 'kakaojoin', path: contextPath + "/kakaojoin"}
]

window.addEventListener('popstate', () => {
    if (preventPopStatePages.find(page => page.path === window.location.pathname)) {
        location.reload();
    }
    console.log('[Pop State]', window.location.pathname);
    render(window.location.pathname);
});

render(window.location.pathname);
import {Home} from './pages/Home.js';
import {Join} from './pages/Join.js';
import {Nav} from './components/Nav.js';

const root = document.getElementById('body');
const navigation = document.getElementById('navigation');

const pages = [
    {path: contextPath + "/", component: Home},
    {path: contextPath + "/join", component: Join},
    // {path: contextPath + '/service', component: Login},
];

const render = async path => {
    try {
        const component = pages.find(page => page.path === path)?.component || NotFound;
        // root.replaceChildren(await component());
        component(root);
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
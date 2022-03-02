import {createElement} from '../utils/component.js';

export const Home = async (target) => {
    const render = () => target.replaceChildren(createElement('<section class="section"><div>메인페이지</div></section>'));
    render();
};
import {createElement} from "../../utils/component.js";

export const Search = (target) => {
    const render = () => {
        target.replaceChildren(createElement(`
    <section class="hero is-medium">
    <div class="hero-body has-text-centered">
      <h1 class="title is-2">검색을 위한 섹션</h1>
      <div id="hero-input-group" class="field has-addons has-addons-centered">
        <div class="control">
          <input class="input is-medium" type="text" placeholder="Search...">
        </div>
        <div class="control">
          <a id="id-search" class="button is-medium">
            <i class="fa-solid fa-magnifying-glass"></i>
          </a>
        </div>
      </div>
    </div>
  </section>
`));
        setEvent();
    }
    const setEvent = () => {

    }
    render();
};
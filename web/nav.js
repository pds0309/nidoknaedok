import Navigation from './pages/Navigation.js';

const nav = document.getElementById("navigation");
nav.replaceChildren(await Navigation());

(function () {
    var burger = document.querySelector('.burger');
    var menu = document.querySelector('#' + burger.dataset.target);
    burger.addEventListener('click', function () {
        burger.classList.toggle('is-active');
        menu.classList.toggle('is-active');
    });
})();
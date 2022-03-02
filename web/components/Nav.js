export function Nav(render) {
    navigation.addEventListener('click', ev => {
        if (!ev.target.matches('#navigation > div > div > div > a')) {
            return;
        }
        const path = ev.target.getAttribute('href');
        console.log(path);
        ev.preventDefault();
        if (path !== window.location.pathname) {
            window.history.pushState({}, null, path);
            render(path);
        } else {
            console.log("[Prevent Re-render] same request!");
        }
    });
}

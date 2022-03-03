export function Nav(render) {
    document.addEventListener('click', ev => {
        if (!ev.target.matches('a')) {
            return;
        }
        const path = ev.target.getAttribute('href');
        ev.preventDefault();
        if (path !== window.location.pathname) {
            let previusHistoryURL;
            if (window.history.state === null) {
                previusHistoryURL = "/";
            } else {
                previusHistoryURL = window.history.state.current;
            }
            console.log("[Your Prev Page was]: " + previusHistoryURL);
            window.history.pushState({current: path, prev: previusHistoryURL}, null, path);
            render(path);
        } else {
            console.log("[Prevent Re-render] same request!");
        }
    });
}

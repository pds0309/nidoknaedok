import {CompareSession} from "./CompareSession.js";
import {OpenDataSyncGET} from "./FetchData.js";

export function Nav(render) {

    const doNotPreventEventPages = [
        {name: "logout", path: contextPath + "/members/logout"},
    ]
    const sessionTrace = CompareSession(Nav);

    document.addEventListener('click', ev => {
        if (!ev.target.matches('a')) {
            return;
        }

        const path = ev.target.getAttribute('href');
        if (doNotPreventEventPages.find(page => page.path === path)) {
            return;
        }

        const currentSession = JSON.parse(OpenDataSyncGET("session")).data.result;
        if (sessionTrace.get !== currentSession) {
            location.replace(contextPath + "/");
        }
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

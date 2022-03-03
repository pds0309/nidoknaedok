import {useState} from "../utils/state.js";

export const CompareSession = (component) => {
    const hasSession = currentUserName === "" ? "NO" : "YES";
    const [session, setSession] = useState(hasSession, component);
    return {
        get: session,
    }
}
import {useState, useEffect} from "../utils/state.js";

export function OAuthInfo(component, name) {
    const [info, setInfo] = useState({}, component);
    useEffect(() => {
        console.log(name + "연동 페이지로 이동합니다");
    }, component, []);
    return {
        set: (info) => {
            setInfo(info);
        },
        get: info,
    }
}
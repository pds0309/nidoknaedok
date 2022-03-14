import {useEffect, useState} from "../utils/state.js";

export function PageState(component) {
    const [pageInfo, setPageInfo] = useState([], component);
    useEffect(() => {
        //
    }, component, []);
    return {
        setPage: (value) => {
            pageInfo[0].page = value;
            setPageInfo(pageInfo);
        },
        setPrev: (value) => {
            pageInfo[0].prev = value;
            setPageInfo(pageInfo);
        },
        setNext: (value) => {
            pageInfo[0].next = value;
            setPageInfo(pageInfo);
        },
        setAll: (object) => {
            pageInfo[0] = object;
            setPageInfo(pageInfo);
        },
        get: pageInfo,
    }
}
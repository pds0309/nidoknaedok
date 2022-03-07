import {useEffect, useState} from "../utils/state.js";

export function Submission(component) {
    const [submit, setSubmit] = useState([], component);
    useEffect(() => {

    }, component, []);
    return {
        set: (value) => {
            submit[0] = value;
            setSubmit(submit);
        },
        get: submit,
    }
}
import {useState} from "../utils/state.js";

export function Validation(component) {
    const [valid, setValid] = useState([], component);
    return {
        push: (val) => {
            if (valid.indexOf(val) === -1) {
                setValid(valid.push(val));
            }
        },
        pop: (val) => {
            if (valid.indexOf(val) !== -1) {
                setValid(valid.pop(val));
            }
        },
        popContains: (val) => {
            const value = valid.filter(v => v.indexOf(val) !== -1);
            if (value.length !== 0) {
                setValid(valid.pop(value[0]));
            }
        },
        get: valid,
    }
}
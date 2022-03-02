export function state() {
    const indexOfComponents = new Map();
    const statesOfComponents = new Map();

    function useState(initState, component, $target) {
        if (!indexOfComponents.get(component)) {
            indexOfComponents.set(component, 0);
        }
        if (!statesOfComponents.get(component)) {
            statesOfComponents.set(component, []);
        }
        const index = indexOfComponents.get(component);
        const states = statesOfComponents.get(component);

        indexOfComponents.set(component, index + 1);

        const state = states[index] || initState;

        const setState = (newState) => {
            states[index] = newState;
            indexOfComponents.set(component, 0);
            if ($target !== undefined) {
                component($target);
            }
        }

        return [state, setState];
    }

    function useEffect(cb, component, depArr) {
        const index = indexOfComponents.get(component);
        const states = statesOfComponents.get(component);
        const oldDeps = states[index];
        let hasChanged = true;
        if (oldDeps) {
            hasChanged = depArr.some((dep, i) => !Object.is(dep, oldDeps[i]));
        }
        if (hasChanged) cb();
        states[index] = depArr;
        indexOfComponents.set(component, index + 1);
    }

    return {useState, useEffect};
}

export const {useState, useEffect} = state();
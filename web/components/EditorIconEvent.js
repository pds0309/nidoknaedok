export function EditorIconEvent(target) {
    document.querySelectorAll(target)
        .forEach(value => value.addEventListener('click', (ev) => {
            ev.preventDefault();
            const currentBox = ev.target.parentElement.parentElement.nextElementSibling;
            if (currentBox.readOnly) {
                currentBox.style.backgroundColor = 'white';
                currentBox.readOnly = false;
                return;
            }
            currentBox.style.backgroundColor = '#efecec';
            currentBox.readOnly = true;
        }));
}
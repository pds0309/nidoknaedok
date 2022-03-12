export function QuillEditor(target) {

    let toolbarOptions = [
        ['bold', 'italic', 'underline', 'strike'],
        ['blockquote', 'code-block'],
        [{ 'align': [] }],
        [{'list': 'ordered'}, {'list': 'bullet'}],
        [{'script': 'sub'}, {'script': 'super'}],
        [{'indent': '-1'}, {'indent': '+1'}],
        [{'size': ['small', false, 'large', 'huge']}],
        [{'color': []},
            {'background': []}],
        ['link'],
        ['clean']
    ];

    const options = {
        placeholder: '글을 입력해주세요',
        theme: 'snow',
        modules: {
            toolbar: toolbarOptions
        }
    };
    const editor = new Quill(target, options);
}
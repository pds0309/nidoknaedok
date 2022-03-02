export const createElement = string => {
    const $temp = document.createElement('template');
    $temp.innerHTML = string;
    return $temp.content;
};
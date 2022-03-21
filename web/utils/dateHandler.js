export const dateToChar = (date, divider) => {
    let year = date.getFullYear();
    let month = (1 + date.getMonth());
    month = month >= 10 ? month : '0' + month;
    let day = date.getDate();
    day = day >= 10 ? day : '0' + day;
    return year + divider + month + divider + day;
}

export const getDday = (date, target, errorComment) => {
    const todayTime = new Date();
    const diff = date - todayTime;
    if (diff < 0) {
        target.style.color = 'red';
        target.innerText = errorComment;
        return;
    }
    const diffDay = Math.floor(diff / (1000 * 60 * 60 * 24));
    const diffHour = Math.floor((diff / (1000 * 60 * 60)) % 24);
    const diffMin = Math.floor((diff / (1000 * 60)) % 60);
    const diffSec = Math.floor(diff / 1000 % 60);
    target.innerText = `${diffDay}일 ${diffHour}시간`;
}
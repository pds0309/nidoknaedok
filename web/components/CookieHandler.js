export function SetCookie( strName, strValue, iSecond )
{
    let strCookie = strName + "=" + encodeURIComponent(strValue);
    if( typeof iSecond === "number" )
    {
        strCookie += "; max-age=" + iSecond;
    }
    document.cookie = strCookie;
}
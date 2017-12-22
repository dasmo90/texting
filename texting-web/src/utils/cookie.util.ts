export class CookieUtil {

    public static setCookie(key: string, value: string) {
        document.cookie = key + "=" + value + "; ;path=/;";
    }

    public static deleteCookie(key: string) {
        document.cookie = key + "=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;";
    }

    public static getCookie(key: string): string {
        const name = key + "=";
        const decodedCookie = decodeURIComponent(document.cookie);
        const ca = decodedCookie.split(";");
        let returnValue = "";
        decodedCookie.split(";").forEach((cookiePart) => {
            while (cookiePart.charAt(0) === " ") {
                cookiePart = cookiePart.substring(1);
            }
            if (cookiePart.indexOf(key) === 0) {
                returnValue = cookiePart.substring(name.length, cookiePart.length);
            }
        });
        return returnValue;
    }
}

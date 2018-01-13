export class CookieUtil {

    public static setCookie(key: string, value: string, exdays: number = 1) {
        const d = new Date();
        d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
        const expires = "expires=" + d.toUTCString();
        document.cookie = key + "=" + value + ";" + expires + ";path=/";
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
            if (cookiePart.indexOf(name) === 0) {
                returnValue = cookiePart.substring(name.length, cookiePart.length);
            }
        });
        return returnValue;
    }
}

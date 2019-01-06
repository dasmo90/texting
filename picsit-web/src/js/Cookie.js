export default class Cookie {
    static set(key, value) {
        document.cookie = key + "=" + value;
    }

    static get(key) {
        var name = key + "=";
        var decodedCookie = decodeURIComponent(document.cookie);
        var ca = decodedCookie.split(';');
        for (var i = 0; i < ca.length; i++) {
            var c = ca[i];
            while (c.charAt(0) === ' ') {
                c = c.substring(1);
            }
            if (c.indexOf(name) === 0) {
                return c.substring(name.length, c.length);
            }
        }
        return "";
    }

    static clear(key) {
        Cookie.set(key, '');
    }
}
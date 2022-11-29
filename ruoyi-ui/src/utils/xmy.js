export function uuid(len, radix) {
    var chars = '0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz'.split('');
    var uuid = [], i;
    radix = radix || chars.length;

    if (len) {
        // Compact form
        for (i = 0; i < len; i++) uuid[i] = chars[0 | Math.random()*radix];
    } else {
        // rfc4122, version 4 form
        var r;
        uuid[8] = uuid[13] = uuid[18] = uuid[23] = '-';
        uuid[14] = '4';

        // Fill in random data.  At i==19 set the high bits of clock sequence as
        // per rfc4122, sec. 4.1.5
        for (i = 0; i < 36; i++) {
            if (!uuid[i]) {
                r = 0 | Math.random()*16;
                uuid[i] = chars[(i == 19) ? (r & 0x3) | 0x8 : r];
            }
        }
    }

    return uuid.join('').toLowerCase();
}

// 获得年月日时分秒
// 输入：2020-10-27T14:36:23
// 输出：2020-10-27 14:36:23
export function converTDateToDate(tDate) {
    var d = tDate ? new Date(tDate) : new Date();
    var year = d.getFullYear();
    var month = d.getMonth() + 1;
    var day = d.getDate();
    var hours = d.getHours();
    var min = d.getMinutes();
    // var seconds = d.getSeconds();

    if (month < 10) month = '0' + month;
    if (day < 10) day = '0' + day;
    if (hours < 0) hours = '0' + hours;
    if (min < 10) min = '0' + min;
    // if (seconds < 10) seconds = '0' + seconds;

    // return (year + '-' + month + '-' + day + ' ' + hours + ':' + min + ':' + seconds);
    return (year + '-' + month + '-' + day + ' ' + hours + ':' + min);
}
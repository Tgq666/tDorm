/**
 * Created by Schon on 2018/9/13 0013.
 */
//设置cookie
export function setCookie(key,value,time) {
  var exdate = new Date(); //获取时间
  exdate.setTime(exdate.getTime() + time); //保存的天数，我这里写的是1年
  //字符串拼接cookie
  window.document.cookie = key + "=" + value + ";path=/;expires=" + exdate.toGMTString();
};
//读取cookie
export function getCookie(cname) {
  var name = cname + "=";
  var ca = document.cookie.split(';');
  for (var i = 0; i < ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) === ' ') c = c.substring(1);
    if (c.indexOf(name) !== -1){
      return c.substring(name.length, c.length);
    }
  }
  return "";
};
function padLeftZero (str) {
  return ('00' + str).substr(str.length);
};

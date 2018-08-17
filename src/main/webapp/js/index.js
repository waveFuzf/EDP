webpackJsonp([1], [
/* 0 */,
/* 1 */,
/* 2 */,
/* 3 */,
/* 4 */,
/* 5 */
/***/ (function (module, __webpack_exports__, __webpack_require__) {

    "use strict";
    class global { }
/* harmony export (immutable) */ __webpack_exports__["a"] = global;

    global.color_red = "#E23352"; //rgb(226,51,82)	
    global.color_orange = "#FFAC66"; //rgb(225,172,102)
    global.color_yellow = "#EACF36"; //rgb(234,207,54)
    global.color_green = "#BAC64D"; //rgb(186,200,77)	
    global.color_gray = "#E6D8BE"; //rgb(230,216,190)

    /***/
}),
/* 6 */,
/* 7 */,
/* 8 */
/***/ (function (module, __webpack_exports__, __webpack_require__) {

    "use strict";
    Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* WEBPACK VAR INJECTION */(function ($) {/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jQuery__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jQuery___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_jQuery__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__ui_index_data__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__ui_index_animate__ = __webpack_require__(10);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__ui_global_constant__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_semantic__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4_semantic___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_4_semantic__);

      if (localStorage.getItem("tel")) {
        if (new Date() - new Date(localStorage.getItem("time")) <   20 * 1000) {
          $.post('./login', {
            "tel": localStorage.getItem("tel"),
            "password": localStorage.getItem("password")
          }, function (data, textStatus, xhr) {
            if (textStatus == "success") {
              if (data.isLogin == "falseOne") {
                alert("自动登陆失败！");
              } else if (data.isLogin == "falseTwo") {
                alert("自动登陆失败！");
              } else if (data.isLogin) {
                console.log(JSON.stringify(data));
                //使用sessionStorage
                sessionStorage.setItem("myInf", JSON.stringify(data));
                //跳转
                location.href = './home.html';
              }
            } else {
              alert("自动登陆失败！");
            }
          });
        }
      }

      $(document).ready(function () {
        //动画效果
        var dotline = new __WEBPACK_IMPORTED_MODULE_2__ui_index_animate__["a" /* default */]({
          dom: 'J_dotLine', //画布id
          cw: $(window).width(), //画布宽
          ch: $(window).height(), //画布高
          ds: 20, //点的个数
          r: 20, //圆点半径
          dis: 500 //触发连线的距离
        });

        dotline.start();
        $(".loginAndRegist").css('top', `${$(window).height() * 0.12}px`);
        $(window).resize(function (event) {
          console.log('test');
          //调整登录注册框
          $(".loginAndRegist").css('top', `${$(window).height() * 0.12}px`);
          //调整canvas大小
          dotline.setHW($(window).height() < 650 ? 650 : $(window).height(), $(window).width() < 600 ? 600 : $(window).width());
        });

        //切换登录注册框
        $("h4.title a").each(function (index, el) {
          $(this).on('click', function (event) {
            event.preventDefault();
            $(".title b").css('color', __WEBPACK_IMPORTED_MODULE_3__ui_global_constant__["a" /* default */].color_red);
            setTimeout(() => {
              $(".title b").css('color', 'rgba(0,0,0,.5)');
            }, 300);
            if (index == 1) {
              $("h4.title a").eq(1).addClass('change-color');
              $("h4.title a").eq(0).removeClass('change-color');
              $("span.title-line").addClass("move");
              $(".login").hide();
              $(".regist").show();
              //点击注册请求验证码
              $(".regist form img").attr('src', `./code?${Math.random()}`);
              //添加验证码点击事件
              $(".regist form img").on('click', function (event) {
                event.preventDefault();
                $(this).attr('src', `./code?${Math.random()}`);
              });
            } else {
              $("h4.title a").eq(0).addClass('change-color');
              $("h4.title a").eq(1).removeClass('change-color');
              $("span.title-line").removeClass("move");
              $(".regist").hide();
              $(".login").show();
            }
          });
        });

        $("h4.title a").eq(0).click();

        //登陆验证
        $(".login button").click(function (event) {
          /* Act on the event */
          event.preventDefault();
        });
        $(".login form").form({
          // on: 'blur',
          // fields: {
          //   tel: {
          //     identifier: 'tel',
          //     rules: [{
          //       type: 'regExp',
          //       value: /^1[3|4|5|7|8][0-9]{9}$/,
          //       prompt: '请输入11位手机号'

          //     }]
          //   },
          //   password: {
          //     identifier: 'password',
          //     rules: [{
          //       type: 'regExp',
          //       value: /^[a-zA-Z0-9_-]{4,16}$/,
          //       prompt: '请输入4到16位中英文密码'
          //     }]
          //   }
          // },
          onSuccess: function (event) {
            event.preventDefault();
            let tel = $(".login input").eq(0).val();
            let password = $(".login input").eq(1).val();
            $(".login .button").addClass('loading');
            $.post('./login', {
              "tel": tel,
              "password": password
            }, function (data, textStatus, xhr) {
              $(".login .button").removeClass('loading');
              if (textStatus == "success") {
                if (data.isLogin == "falseOne") {
                  $(".login .button").text("密码错误");
                } else if (data.isLogin == "falseTwo") {
                  $(".login .button").text("用户不存在");
                } else if (data.isLogin) {
                  console.log(JSON.stringify(data));

                  //使用sessionStorage
                  sessionStorage.setItem("myInf", JSON.stringify(data));
                  //记住我
                  if ($(".do-not-forget input").is(':checked')) {
                    doNotForget(tel, password);
                  }
                  //跳转
                  location.href = './home.html';
                }
              } else {
                $(".login .button").text("登陆失败,请稍后再试");
              }
            });
            return false;
          }
        });

        //记住我
        function doNotForget(tel, password) {
          localStorage.setItem("tel", tel);
          localStorage.setItem("password", password);
          alert(new Date().toString());
          localStorage.setItem("time", new Date().toString());
        }

        //注册
        $(".regist button").click(function (event) {
          /* Act on the event */
          event.preventDefault();
        });
        $(".regist form").form({
          // on: 'blur',
          // fields: {
          //   tel: {
          //     identifier: 'tel',
          //     rules: [{
          //       type: 'regExp',
          //       value: /^1[3|4|5|7|8][0-9]{9}$/,
          //       prompt: '请输入11位手机号'
          //     }]
          //   },
          //   password: {
          //     identifier: 'password',
          //     rules: [{
          //       type: 'regExp',
          //       value: /^[a-zA-Z0-9_-]{4,16}$/,
          //       prompt: '请输入4到16位中英文密码'
          //     }]
          //   },
          //   repassword: {
          //     identifier: 'repassword',
          //     rules: [{
          //       type: 'match[password]',
          //       prompt: '确认重复密码'
          //     }]
          //   },
          //   code: {
          //     identifier: 'code',
          //     rules: [{
          //       type: 'regExp',
          //       value: /^\d{4}$/,
          //       prompt: '请输入4位数字验证码'
          //     }]
          //   }
          // },
          onSuccess: function (event) {
            event.preventDefault();
            let tel = $(".regist input").eq(0).val();
            let password = $(".regist input").eq(1).val();
            let code = $(".regist input").eq(2).val();
            $(".regist .button").addClass('loading');
            $.post('./register', {
              "tel": tel,
              "password": password,
              "code": code
            }, function (data, textStatus, xhr) {
              $(".regist .button").removeClass('loading');
              if (textStatus == "success") {
                if (data.isRegister == "falseOne") {
                  $(".regist .button").text("此手机号已被注册");
                } else if (data.isRegister == "falseTwo") {
                  $(".regist .button").text("验证码错误");
                } else if (data.isRegister) {
                  $(".regist input").eq(0).val("");
                  $(".regist input").eq(1).val("");
                  $(".regist input").eq(2).val("");
                  $(".login input").eq(0).val(tel);
                  $("h4.title a").eq(0).click();
                }
              } else {
                $(".regist .button").text("注册失败,请稍后再试");
              }
            });
          }
        });
      });
      /* WEBPACK VAR INJECTION */
}.call(__webpack_exports__, __webpack_require__(0)))

    /***/
}),
/* 9 */
/***/ (function (module, __webpack_exports__, __webpack_require__) {

    "use strict";
    // import jqery from 'jquery';
    class dataGet {

      constructor(url, method, message) {
        this.url = radius;
        this.method = method;
        this.message = message;
      }
      getXhr() {
        var xhr = null;
        if (window.XMLHttpRequest) {
          //new browsers
          xhr = new XMLHttpRequest();
        } else if (window.ActiveXObject) {
          //IE5 and IE6
          xhr = new ActiveXObject("Microsoft.XMLHTTP");
        }
        return xhr;
      }
      // 
      translate() {
        var that = this; //传递this
        var xhr = this.getXhr();
        if (xhr) {
          var promise = new Promise(function (resolve, reject) {
            xhr.onreadystatechange = function () {
              if (xhr.readyState === 4) {
                if (xhr.status === 200) {
                  try {
                    if (xhr.responseText) {
                      var data = JSON.parse(xhr.responseText);
                      resolve(data);
                    } else {
                      reject();
                      console.log("error:responseText is null!");
                    }
                  } catch (e) {
                    // statements
                    console.log(e);
                  }
                }
              } else {
                reject();
              }
            };
            xmlhttp.open(that.mothod, that.url, true);
            xmlhttp.send(message);
          });
          return promise;
        } else {
          console.log('error:xhr is null!');
          return null;
        }
      }

    }
    /* unused harmony export default */


    /***/
}),
/* 10 */
/***/ (function (module, __webpack_exports__, __webpack_require__) {

    "use strict";
    class Dotline {

      constructor(option) {
        this.opt = Object.assign({
          dom: 'J_dotLine', //画布id
          cw: 1000, //画布宽
          ch: 500, //画布高
          ds: 20, //点的个数
          r: 0.5, //圆点半径
          dis: 100 //触发连线的距离
        }, option);

        this.c = document.getElementById(this.opt.dom); //canvas元素id
        this.ctx = this.c.getContext('2d');
        this.c.width = this.opt.cw; //canvas宽
        this.c.height = this.opt.ch; //canvas高
        this.dotSum = this.opt.ds; //点的数量
        this.radius = this.opt.r; //圆点的半径
        this.disMax = this.opt.dis * this.opt.dis; //点与点触发连线的间距     
        this.dots = [];

        //requestAnimationFrame控制canvas动画
        var RAF = window.requestAnimationFrame || window.webkitRequestAnimationFrame || window.mozRequestAnimationFrame || window.oRequestAnimationFrame || window.msRequestAnimationFrame || function (callback) {
          window.setTimeout(callback, 1000 / 60);
        };

        var _self = this;

        // 增加鼠标效果
        var mousedot = {
          x: null,
          y: null,
          label: 'mouse'
        };
        this.c.onmousemove = function (ev) {
          //鼠标移进
          var e = ev || window.event;
          mousedot.x = e.clientX - _self.c.offsetLeft; //相对父元素位置 clientX 鼠标在页面的x坐标
          mousedot.y = e.clientY - _self.c.offsetTop;
        };
        this.c.onmouseout = function (ev) {
          mousedot.x = null;
          mousedot.y = null;
        };

        //控制动画
        this.animate = function () {
          _self.ctx.clearRect(0, 0, _self.c.width, _self.c.height); //清空canvas

          _self.drawLine(_self.dots);

          RAF(_self.animate);
        };
      }

      setHW(height, width) {
        this.c.height = height;
        this.c.width = width;
      }
      //画点
      addDots() {
        var dot;
        var color = ["rgba(226,51,82,.2)", "rgba(225,172,102,.2)", "rgba(234,207,54,.2)", "rgba(186,200,77,.2)", "rgba(230,216,190,.2)"];
        for (var i = 0; i < this.dotSum; i++) {
          //参数
          dot = {
            x: Math.floor(Math.random() * this.c.width) - this.radius,
            y: Math.floor(Math.random() * this.c.height) - this.radius,
            ax: (Math.random() * 2 - 1) / 3,
            ay: (Math.random() * 2 - 1) / 3,
            r: 10 + Math.floor(Math.random() * 15),
            color: color[Math.floor(Math.random() * 5)]
          };
          this.dots.push(dot);
        }
      }

      //点运动
      move(dot) {
        dot.x += dot.ax;
        dot.y += dot.ay;
        //点碰到边缘返回
        dot.ax *= dot.x > this.c.width - dot.r || dot.x < dot.r ? -1 : 1;
        dot.ay *= dot.y > this.c.height - dot.r || dot.y < dot.r ? -1 : 1;
        //绘制点

        this.ctx.beginPath();
        this.ctx.arc(dot.x, dot.y, dot.r, 0, Math.PI * 2, false);
        this.ctx.fillStyle = dot.color;
        this.ctx.fill();
        this.ctx.stroke();
        // this.cxt.closePath();
      }

      //画线
      drawLine(dots) {
        var nowDot;
        var _that = this;
        var color = ["rgba(226,51,82,", "rgba(225,172,102,", "rgba(234,207,54,", "rgba(186,200,77,", "rgb(230,216,190,"];
        this.dots.forEach(function (dot) {
          _that.move(dot);
          for (var j = 0; j < dots.length; j++) {
            nowDot = dots[j];
            if (nowDot === dot || nowDot.x === null || nowDot.y === null) continue; //continue跳出当前循环开始新的循环
            var dx = dot.x - nowDot.x,
              //别的点坐标减当前点坐标
              dy = dot.y - nowDot.y;
            var dc = dx * dx + dy * dy;
            if (Math.sqrt(dc) > Math.sqrt(_that.disMax)) continue;

            // 如果是鼠标，则让粒子向鼠标的位置移动
            if (nowDot.label && Math.sqrt(dc) > Math.sqrt(_that.disMax) / 2) {
              dot.x -= dx * 0.02;
              dot.y -= dy * 0.02;
            }

            var ratio;
            ratio = (_that.disMax - dc) / _that.disMax;
            _that.ctx.beginPath();
            _that.ctx.lineWidth = ratio / 2;
            _that.ctx.strokeStyle = 'rgba(234,207,54,' + (ratio + 0.2) + ')';
            _that.ctx.moveTo(dot.x, dot.y);
            _that.ctx.lineTo(nowDot.x, nowDot.y);
            _that.ctx.stroke(); //不描边看不出效果

            //dots.splice(dots.indexOf(dot), 1);
          }
        });
      }

      start() {
        var _that = this;
        this.addDots();
        setTimeout(function () {
          _that.animate();
        }, 100);
      }
    }
/* harmony export (immutable) */ __webpack_exports__["a"] = Dotline;


    /***/
})
], [8]);
//# sourceMappingURL=index.js.map
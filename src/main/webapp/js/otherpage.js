webpackJsonp([4], {

/***/ 17:
/***/ (function (module, __webpack_exports__, __webpack_require__) {

			"use strict";
			Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* WEBPACK VAR INJECTION */(function ($) {/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jQuery__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jQuery___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_jQuery__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_semantic__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1_semantic___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_1_semantic__);



				$(document).ready(function () {

					var myInf = JSON.parse(sessionStorage.getItem("myInf")); //用户信息

					(function () {
						$("#avatar img").attr('src', myInf.userMessage.image);
						$("#avatar span").text(myInf.userMessage.name);
						$(".myinfdropdown .menu b").text(myInf.userMessage.integral);
					})();

					$.post('./seeOtherSpace', {
						"personId": GetQueryString("personId")
					}, function (data, textStatus, xhr) {
						console.log(data);
						((data, $box) => {
							$box.find(".other_avatar img").attr('src', data.userMessage.image);
							$box.find(".other_inf h1").text(data.userMessage.name);
							$box.find(".other_inf h3 span").text(data.userMessage.count);
							$box.find(".other_inf .xinyu").text("信誉" + data.userMessage.creditLevel);
							data.sex == "0" ? $box.find(".other_inf i").addClass("man") : $box.find(".other_inf i").addClass("woman");
							if (data.evaluates.length > 0) {
								$("#other_evl").empty();
								for (var i = 0; i < data.evaluates.length; i++) {
									addEvl(data.evaluates[i], $("#other_evl"));
								}
								console.log(data.evaluates.length);
							} else {
								$("#other_evl").text("该用户暂时没有评价");
							}
						})(data, $("#other_content"));
						$('.ui.rating').rating({
							interactive: false
						});
					});

					function GetQueryString(name) {
						var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
						var r = window.location.search.substr(1).match(reg);
						if (r != null) return unescape(r[2]);
						return null;
					}

					$("button.writeent").on('click', function (event) {
						event.preventDefault();
						window.location.href = "../task1/writing";
					});

					$("button.backmain").on('click', function (event) {
						event.preventDefault();
						window.location.href = "./home";
					});

					$('.ui.dropdown').dropdown();

					function addEvl(data, box) {
						var eva = `
		 	<div class="ui card evl">
              <div class="content">
                <div class="right floated meta">
                  <i class="icon write"></i>${data.evaluateDate}
                </div>
                <img class="ui avatar image" src="${data.evaluatedImage}"><span>${data.evaluatedName}</span>
              </div>
              <div class="content">
                <div class="header" style="font-weight: 100 !important;color:rgb(122, 133, 153);">
                  ${data.evaluate}
                </div>
              </div>
              <div class="extra content">
                <span class="date"><div class="ui heart huge rating" data-rating="${data.evaluateLevel}" data-max-rating="5"></div></span>
              </div>
          </div>
      </div>`;
						box.append(eva);
					}
				});
				/* WEBPACK VAR INJECTION */
}.call(__webpack_exports__, __webpack_require__(0)))

			/***/
})

}, [17]);
//# sourceMappingURL=otherpage.js.map
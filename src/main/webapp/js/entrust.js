webpackJsonp([2], [
/* 0 */,
/* 1 */,
/* 2 */
/***/ (function (module, __webpack_exports__, __webpack_require__) {

		"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_AMap__ = __webpack_require__(3);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_AMap___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_AMap__);
		/**
		 **地图功能**
		 **包括**
		 **1.定位功能**
		 **2.根据经纬度获取具体位置**
		 **3.选择地图点返回经纬度**
		 **4.地图绘制**
		 **inf**
		 **1.api:http://webapi.amap.com/maps?v=1.4.2&key=e92318f288333abcc2892dfdff52d410**
		 **2.样式：amap://styles/b564256e2748553ccc3df159787fac2c**
		 **/


		// import AMapUI from 'AMapUI';

		class Position {
			constructor(option) {
				this.opt = Object.assign({
					height: 0,
					width: 0,
					vessel: 'Map'
				}, option);
				this.vessel = document.getElementById(this.opt.vessel); //获取地图容器
				this.vessel.height = this.opt.height;
				this.vessel.width = this.opt.width;
				this.map = null;

				this.placeSearch = null;
			}

			//http://ditu.amap.com/?p=B023B13L9M,30.220671,120.108478,%E6%9D%AD%E5%B7%9E%E8%A5%BF%E6%B9%96%E9%A3%8E%E6%99%AF%E5%90%8D%E8%83%9C%E5%8C%BA,%E6%9D%AD%E5%B7%9E%E5%B8%82%E8%A5%BF%E6%B9%96%E5%8C%BA%E9%BE%99%E4%BA%95%E8%B7%AF1%E5%8F%B7,330100
			drawMap(longitude = 120.108478, latitude = 30.220671, callback) {
				if (arguments.length === 1) {
					callback = arguments[0];
					longitude = 120.108478;
					latitude = 30.220671;
				}

				var map = new __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.Map(this.opt.vessel, {
					resizeEnable: true,
					zoom: 10,
					center: [longitude, latitude],
					mapStyle: 'amap://styles/b564256e2748553ccc3df159787fac2c'
				});
				this.map = map;

				if (this.map) {
					callback && callback();
				} else {
					alert("地图绘制失败！");
				}
			}

			addPicker(center, success, falied) {
				var that = this;
				AMapUI.loadUI(['misc/PositionPicker'], function (PositionPicker) {
					var map = that.map;

					var positionPicker = new PositionPicker({
						mode: 'dragMarker',
						map: map
					});

					positionPicker.on('success', function (positionResult) {
						success(positionResult);
					});
					positionPicker.on('fail', function (positionResult) {
						falied(positionResult);
					});

					positionPicker.start(center);
					map.panBy(0, 1);
				});
			}

			getMyPosition(callback) {
				var that = this;
				this.map.plugin('AMap.Geolocation', () => {
					var geolocation = new __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.Geolocation({
						enableHighAccuracy: true, //是否使用高精度定位，默认:true
						timeout: 10000, //超过10秒后停止定位，默认：无穷大
						buttonOffset: new __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.Pixel(10, 20), //定位按钮与设置的停靠位置的偏移量，默认：Pixel(10, 20)
						zoomToAccuracy: true, //定位成功后调整地图视野范围使定位位置及精度范围视野内可见，默认：false
						buttonPosition: 'RB'
					});
					this.map.addControl(geolocation);
					geolocation.getCurrentPosition();

					//返回定位信息
					__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.event.addListener(geolocation, 'complete', data => {
						// console.log(JSON.stringify(data));
						callback && callback(data);
					});

					//返回定位出错信息
					__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.event.addListener(geolocation, 'error', data => {
						console.error("Geolocation Error!");
					});
				});
			}

			//点击获得经纬度
			addMark(buttonName, callback) {
				var that = this;
				var amapEvent = null;
				var lnglat = null;
				__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.event.addDomListener(document.getElementById(buttonName), 'click', () => {
					__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.event.removeListener(amapEvent);
					amapEvent = __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.event.addListener(that.map, 'click', e => {
						new __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.Marker({
							position: e.lnglat,
							map: that.map
						});
						// console.log(e.lnglat.toString());
						// callback(e.lnglat);
					});
				});

				this.vessel.onclick = e => {
					// alert("adsada");
					__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.event.removeListener(amapEvent);
				};
			}

			//根据经纬度获得地址
			getPositionByLonLat(lnglatXY = [120.108478, 30.220671]) {
				var promise = new Promise((resolve, reject) => {
					__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.service('AMap.Geocoder', () => {
						//回调函数
						//实例化Geocoder
						var geocoder = new __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.Geocoder({
							// city: "010" //城市，默认：“全国”
						});
						geocoder.getAddress(lnglatXY, (status, result) => {
							console.log("getPositionByLonLat", JSON.stringify(result));
							if (status === "complete" && result.info === "OK") {
								console.log(`根据经纬度获得地址：${JSON.stringify(result.regeocode.addressComponent)}`);
								resolve(result.regeocode);
							} else {
								console.error("get address error!");
								reject(status);
							}
						});
					});
				});
				return promise;
			}

			//根据地址获取经纬度
			getPositionByAddress(address) {
				var promise = new Promise((resolve, reject) => {
					__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.service('AMap.Geocoder', () => {
						//回调函数
						//实例化Geocoder
						var geocoder = new __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.Geocoder({});
						geocoder.getLocation(address, (status, result) => {
							// console.log(JSON.stringify(result));
							if (status === "complete" && result.info === "OK") {
								// address = result.regeocode.formattedAddress
								//console.log(JSON.stringify(result));
								//console.log(result.geocodes[0].location); //格式：{N: 39.90403, L: 116.40752600000002, lng: 116.407526, lat: 39.90403}
								resolve(result.geocodes[0].location);
							} else {
								console.error("get address error!");
								reject(status);
							}
						});
					});
				});
				return promise;
			}

			clickToGetPosition(callback) {
				let placeSearch = null;
				if (!this.placeSearch) {
					__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.service('AMap.PlaceSearch', function () {
						//回调函数
						placeSearch = new __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.PlaceSearch();
					});
				} else {
					placeSearch = this.placeSearch;
				}

				this.map.on('hotspotclick', function (resultx) {
					var name = resultx.name;
					console.log(name);
					placeSearch.getDetails(resultx.id, function (status, result) {
						console.log(JSON.stringify(result));
						if (status === 'complete' && result.info === 'OK') {
							callback && callback(result.poiList.pois[0], result, name);
						}
					});
				});
			}

			/*
		 
		 var map = new AMap.Map('container', {
				resizeEnable: true,
				center: [116.397428, 39.90923],
				zoom: 13,
				isHotspot: true
			});
			var placeSearch = new AMap.PlaceSearch();  //构造地点查询类
			var infoWindow=new AMap.AdvancedInfoWindow({});
			map.on('hotspotclick', function(result) {
				placeSearch.getDetails(result.id, function(status, result) {
					if (status === 'complete' && result.info === 'OK') {
						placeSearch_CallBack(result);
					}
				});
			});
			//回调函数
			function placeSearch_CallBack(data) { //infoWindow.open(map, result.lnglat);
				var poiArr = data.poiList.pois;
				var location = poiArr[0].location;
				infoWindow.setContent(createContent(poiArr[0]));
				infoWindow.open(map,location);
			}
			function createContent(poi) {  //信息窗体内容
				var s = [];
				s.push('<div class="info-title">'+poi.name+'</div><div class="info-content">'+"地址：" + poi.address);
				s.push("电话：" + poi.tel);
				s.push("类型：" + poi.type);
				s.push('<div>');
				return s.join("<br>");
			}
		 
		 
		 */

		}
/* harmony export (immutable) */ __webpack_exports__["a"] = Position;


		/***/
}),
/* 3 */
/***/ (function (module, exports) {

		module.exports = window.AMap;

		/***/
}),
/* 4 */,
/* 5 */,
/* 6 */
/***/ (function (module, __webpack_exports__, __webpack_require__) {

		"use strict";
		Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* WEBPACK VAR INJECTION */(function ($) {/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jQuery__ = __webpack_require__(1);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0_jQuery___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_jQuery__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__ui_position__ = __webpack_require__(2);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_semantic__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_semantic___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_semantic__);




			$(document).ready(function () {

				//全局变量
				var lngAndlat = {};
				var formType = 1; //1为委托，2为代取
				var submitType = 1; //1为发布，2为暂存 
				var myInf = JSON.parse(sessionStorage.getItem("myInf")); //用户信息
				var pos = new __WEBPACK_IMPORTED_MODULE_1__ui_position__["a" /* default */]({
					'vessel': 'map'
				});
				pos.drawMap(() => { });

				$('.ui.fluid.selection.dropdown').dropdown({
					maxSelections: 3
				});
				$("#write_container .kinds").popup();
				$("#write_container form .location").popup();

				//预填地址
				pos.getMyPosition(data => {
					var inf = data.addressComponent;
					$("#write_content .form1 .address input").eq(0).val(inf.province);
					$("#write_content .form1 .address input").eq(1).val(inf.city);
					$("#write_content .form1 .address input").eq(2).val(inf.district);
					$("#write_content .form2 .address input").eq(0).val(inf.province);
					$("#write_content .form2 .address input").eq(1).val(inf.city);
					$("#write_content .form2 .address input").eq(2).val(inf.district);
				});

				//添加点击热点事件
				pos.clickToGetPosition((data, datax, name) => {
					//确认经纬度	
					console.log("点击热点获得的经纬度：", data.location.lng, data.location.lat);
					lngAndlat.lng = data.location.lng;
					lngAndlat.lat = data.location.lat;
					pos.getPositionByLonLat([data.location.lng, data.location.lat]).then(datax => {
						let inf = datax.addressComponent;
						if (formType === 1) {
							$("#write_content .form1 .address input").eq(0).val(inf.province);
							$("#write_content .form1 .address input").eq(1).val(inf.city);
							$("#write_content .form1 .address input").eq(2).val(inf.district);
							$("#detail_add").val(`${inf.township}${inf.street}${inf.streetNumber}${name}`);
							$("#modal1").modal("hide");
						} else if (formType === 2) {
							$("#write_content .form2 .address input").eq(0).val(inf.province);
							$("#write_content .form2 .address input").eq(1).val(inf.city);
							$("#write_content .form2 .address input").eq(2).val(inf.district);
							$("#detail_add1").val(`${inf.township}${inf.street}${inf.streetNumber}${name}`);
							$("#modal1").modal("hide");
						}
					}, err => {
						alert(err);
					}).catch();
				});

				// pos.getPositionByAddress("浙江省杭州市西湖区留下街道小和山新苑八区国通快递代理点天天快递代理点");

				//切换填表
				$("#write_container .kinds").click(function (event) {
					if (formType === 1) {
						$(this).text("写代取");
						$("nav .func button").eq(1).toggle();
						formType = 2;
						$("#form1").fadeOut('fast', function () {
							$("#form2").show();
						});
					} else if (formType === 2) {
						$(this).text("写委托");
						$("nav .func button").eq(1).toggle();
						formType = 1;
						$("#form2").fadeOut('fast', function () {
							$("#form1").show();
						});
					}
				});

				$("#write_container form .location").click(function (event) {
					pos.getMyPosition();
					$("#modal1").modal('show');
				});

				$("#write_container .func button:first").click(function (event) {

					submitType = 1;
					$("#modal2").find("input").val("");
					if (formType === 1) {
						$("#form1").submit();
						console.log("发布1");
					} else {
						$("#form2").submit();
						console.log("发布2");
					}
				});

				$("#write_container .func button:last").click(function (event) {

					submitType = 2;
					if (formType === 1) {
						console.log("暂存");
						$("#form1").submit();
					}
				});

				function showMessage(mes) {
					$("#modal3 .content").text(mes);
					$("#modal3").modal('show');
					$("#modal3 .button").click(function (event) {
						$("#modal3").modal('hide');
					});
				}

				$("#form1").form({
					on: 'blur',
					inline: true,
					fields: {
						type: {
							identifier: 'type',
							rules: [{
								type: 'empty',
								prompt: '类型不能为空'
							}]
						},
						province: {
							identifier: 'province',
							rules: [{
								type: 'empty',
								prompt: '省份不能为空'
							}]
						},
						district: {
							identifier: 'district',
							rules: [{
								type: 'empty',
								prompt: '区县不能为空'
							}]
						},
						detailAdd: {
							identifier: 'detailAdd',
							rules: [{
								type: 'empty',
								prompt: '详细地址不能为空'
							}]
						},
						fetchcode: {
							identifier: 'fetchcode',
							rules: [{
								type: 'empty',
								prompt: '取货码不能为空'
							}]
						},
						dateline: {
							identifier: 'dateline',
							rules: [{
								type: 'empty',
								prompt: '截止时间不能为空'
							}]
						}
					},
					onSuccess: function (event) {
						event.preventDefault();
						var address = $("#form1 .express_add");
						var add = '';
						address.each(function (index, el) {
							add += $(this).val();
						});
						//未点击地址,验证地址存在情况
						if (!lngAndlat.lng) {
							pos.getPositionByAddress(add).then(data => {
								console.log(JSON.stringify(data));
								//写入经纬度
								lngAndlat.lng = data.lng;
								lngAndlat.lat = data.lat;
							}, data => {
								console.log(data, "地址获取失败");
								$("#form1 .express_add").parent("div").addClass('error');
								return false;
							}).catch(() => {
								console.log('test11');
								alert("获取经纬度失败！！！");
								return false;
							});
						}

						//已经有经纬度且表单验证通过,此为直接发布
						if (lngAndlat.lng && formType === 1 && submitType === 1) {
							//添加积分
							$("#modal2").modal({
								onApprove: function () {
									let reward = $(this).find("input").val();
									if (!/^[1-9]\d*$/.test(reward)) {
										if (myInf.userMessage.integral < reward) {
											showMessage("积分不够！");
											$(this).find("input").parent("div").addClass('error');
											return false;
										}
										$(this).find("input").parent("div").addClass('error');
										return false;
									} else {

										if ((new Date($("#form1 .takeDate").val()) - new Date()) < 0) {
											showMessage("请填入正确的时间范围！");
											return;
										}
										//开始提交,表单
										$.post('../task1/appointment', {
											"type": $("#form1 .type").val(),
											"size": $("#form1 .size").val(),
											"address": add,
											"company": $("#form1 .company").val(),
											"code": $("#form1 .code").val(),
											"tip": $("#form1 .tip").val(),
											"longitude": lngAndlat.lng,
											"latitude": lngAndlat.lat,
											"requirement": $("#form1 .requirement").val(),
											"takeDate": $("#form1 .takeDate").val(),
											"integral": $("#modal2 .integral").val()
										}, function (data, textStatus, xhr) {
											lngAndlat = {};
											if (data.message === "true") {
												showMessage("发布成功！");
												$("#modal2 .integral").val("");
												$("#form1 input").val('');
												$("#form1 textarea").val('');
											} else if (data.message === "falseOne") {
												showMessage("此快件已经存在！");
											} else if (data.message === "falseTwo") {
												showMessage("您的积分不够！");
											}
										});
									}
								}
							}).modal('show');
						}

						//此为暂存
						if (lngAndlat.lng && formType === 1 && submitType === 2) {
							if ((new Date($("#form1 .takeDate").val()) - new Date()) < 0) {
								showMessage("请填入正确的时间范围！");
								return;
							}
							$.post('../task/temporary', {
								"type": $("#form1 .type").val(),
								"size": $("#form1 .size").val(),
								"address": add,
								"company": $("#form1 .company").val(),
								"code": $("#form1 .code").val(),
								"tip": $("#form1 .tip").val(),
								"longitude": lngAndlat.lng,
								"latitude": lngAndlat.lat,
								"requirement": $("#form1 .requirement").val(),
								"takeDate": $("#form1 .takeDate").val()
							}, function (data, textStatus, xhr) {
								lngAndlat = {};
								if (data.message === "true") {
									showMessage("暂存成功！");
									$("#form1 input").val('');
									$("#form1 textarea").val('');
								} else {
									showMessage("此快件已经存在！");
								}
							});
						}
					}
				});

				$("#form2").form({
					on: 'blur',
					inline: true,
					fields: {
						introduce: {
							identifier: 'freedate',
							rules: [{
								type: 'empty',
								prompt: '时间不能为空'
							}]
						},
						province: {
							identifier: 'province',
							rules: [{
								type: 'empty',
								prompt: '省份不能为空'
							}]
						},
						district: {
							identifier: 'district',
							rules: [{
								type: 'empty',
								prompt: '区县不能为空'
							}]
						},
						detailAdd: {
							identifier: 'detailAdd',
							rules: [{
								type: 'empty',
								prompt: '详细地址不能为空'
							}]
						}
					},
					onSuccess: function (event) {
						event.preventDefault();
						let address = $("#form2 .express_add");
						let add = '';
						address.each(function (index, el) {
							add += $(this).val();
						});
						//未点击地址,验证地址存在情况
						if (!lngAndlat.lng) {

							pos.getPositionByAddress(add).then(data => {
								console.log(JSON.stringify(data));
								//写入经纬度
								lngAndlat.lng = data.lng;
								lngAndlat.lat = data.lat;
								//开始提交
								if ((new Date($("#form2 .freeDate").val()) - new Date()) < 0) {
									showMessage("请填入正确的时间范围！");
									return;
								}
								$.post('../task/issueReceipt', {
									"freeDate": $("#form2 .freeDate").val(),
									"address": add,
									"introduce": $("#form2 .introduce").val()
								}, function (data, textStatus, xhr) {
									/*optional stuff to do after success */
									lngAndlat = {};

									if (data.message === "true") {
										showMessage("发布成功！");
										$("#form2 input").val('');
										$("#form2 textarea").val('');
									} else {
										showMessage("发布失败！");
									}
								});
							}, data => {
								console.log(data, "地址获取失败");
								$("#form2 .express_add").parent("div").addClass('error');
							}).catch(() => {
								console.log('test11');
								alert("获取经纬度失败！！！");
							});
						} else {
							if ((new Date($("#form2 .freeDate").val()) - new Date()) < 0) {
								showMessage("请填入正确的时间范围！");
								return;
							}
							//开始提交
							$.post('../task/issueReceipt', {
								"freeDate": $("#form2 .freeDate").val(),
								"address": add,
								"introduce": $("#form2 .introduce").val()
							}, function (data, textStatus, xhr) {
								/*optional stuff to do after success */
								lngAndlat = {};

								if (data.message === "true") {
									showMessage("发布成功！");
									$("#form2 input").val('');
									$("#form2 textarea").val('');
								} else {
									showMessage("发布失败！");
								}
							});
						}
					}
				});
				if (sessionStorage.getItem("writing") === "fetch") {
					$("nav .kinds").click();
					sessionStorage.setItem("writing", "");
				}
			});
			/* WEBPACK VAR INJECTION */
}.call(__webpack_exports__, __webpack_require__(0)))

		/***/
})
], [6]);
//# sourceMappingURL=entrust.js.map
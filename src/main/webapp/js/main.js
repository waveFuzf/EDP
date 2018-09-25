webpackJsonp([0], [
	/* 0 */
	,
	/* 1 */
	,
	/* 2 */
	/***/
	(function(module, __webpack_exports__, __webpack_require__) {

		"use strict";
		/* harmony import */
		var __WEBPACK_IMPORTED_MODULE_0_AMap__ = __webpack_require__(3);
		/* harmony import */
		var __WEBPACK_IMPORTED_MODULE_0_AMap___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_AMap__);
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
					zoom: 13,
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
				AMapUI.loadUI(['misc/PositionPicker'], function(PositionPicker) {
					var map = that.map;

					var positionPicker = new PositionPicker({
						mode: 'dragMarker',
						map: map
					});

					positionPicker.on('success', function(positionResult) {
						success(positionResult);
					});
					positionPicker.on('fail', function(positionResult) {
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
					__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.service('AMap.PlaceSearch', function() {
						//回调函数
						placeSearch = new __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.PlaceSearch();
					});
				} else {
					placeSearch = this.placeSearch;
				}

				this.map.on('hotspotclick', function(resultx) {
					var name = resultx.name;
					console.log(name);
					placeSearch.getDetails(resultx.id, function(status, result) {
						console.log(JSON.stringify(result));
						if (status === 'complete' && result.info === 'OK') {
							callback && callback(result.poiList.pois[0], result, name);
						}
					});
				});
			}

			route(start, end, callback) {
				var that = this;
				__WEBPACK_IMPORTED_MODULE_0_AMap___default.a.service('AMap.Walking', function() {
					//回调函数
					var walking = new __WEBPACK_IMPORTED_MODULE_0_AMap___default.a.Walking({
						map: that.map,
						panel: "mypag_des"
					});
					//根据起终点坐标规划步行路线
					console.log("路线规划");
					walking.search(start, end, function(status, result) {
						//TODO 解析返回结果，自己生成操作界面和地图展示界面
						console.log(JSON.stringify(result));
						callback && callback();
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
		/* harmony export (immutable) */
		__webpack_exports__["a"] = Position;


		/***/
	}),
	/* 3 */
	/***/
	(function(module, exports) {

		module.exports = window.AMap;

		/***/
	}),
	/* 4 */
	,
	/* 5 */
	/***/
	(function(module, __webpack_exports__, __webpack_require__) {

		"use strict";
		class global {}
		/* harmony export (immutable) */
		__webpack_exports__["a"] = global;

		global.color_red = "#E23352"; //rgb(226,51,82)	
		global.color_orange = "#FFAC66"; //rgb(225,172,102)
		global.color_yellow = "#EACF36"; //rgb(234,207,54)
		global.color_green = "#BAC64D"; //rgb(186,200,77)	
		global.color_gray = "#E6D8BE"; //rgb(230,216,190)

		/***/
	}),
	/* 6 */
	,
	/* 7 */
	,
	/* 8 */
	,
	/* 9 */
	,
	/* 10 */
	,
	/* 11 */
	/***/
	(function(module, __webpack_exports__, __webpack_require__) {

		"use strict";
		Object.defineProperty(__webpack_exports__, "__esModule", {
			value: true
		});
		/* WEBPACK VAR INJECTION */
		(function($) { /* harmony import */
			var __WEBPACK_IMPORTED_MODULE_0_jQuery__ = __webpack_require__(1);
			/* harmony import */
			var __WEBPACK_IMPORTED_MODULE_0_jQuery___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_jQuery__);
			/* harmony import */
			var __WEBPACK_IMPORTED_MODULE_1__ui_global_constant__ = __webpack_require__(5);
			/* harmony import */
			var __WEBPACK_IMPORTED_MODULE_2__ui_position__ = __webpack_require__(2);
			/* harmony import */
			var __WEBPACK_IMPORTED_MODULE_3__ui_socket__ = __webpack_require__(12);
			/* harmony import */
			var __WEBPACK_IMPORTED_MODULE_4__ui_main_pageUpdate__ = __webpack_require__(13);
			/* harmony import */
			var __WEBPACK_IMPORTED_MODULE_5_semantic__ = __webpack_require__(4);
			/* harmony import */
			var __WEBPACK_IMPORTED_MODULE_5_semantic___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_5_semantic__);



			//待添加
			//单号被接受后 remove此单
			//第二种请求
			$(document).ready(function() {
				console.log('aaaa')
				if (sessionStorage.getItem("longitude")) {
					firstLoading(sessionStorage.getItem("longitude"), sessionStorage.getItem("latitude"));
				}


				//全局变量声明
				var clickFlag = 0;
				var chatHeight = 0; //聊天窗口
				var chatId = 0;
				var chatImage = "../images/defaultavatar.jpg";
				var lngAndLat = [120, 30]; //经纬度
				var searchAddress = ''; //地址
				var searchCredit = 5; //
				var pageTimeOne = 2;
				var pageTimeTwo = 2;
				var supportId = 0; //辅助使用Id
				var ifGet = true;
				var time = null;
				var unreadMap = {
					"userId": [],
					"record": [],
					"time": []
				};
				var tenRecord;
				var publishOdd = "";
				//我的信息
				var myInf = JSON.parse(sessionStorage.getItem("myInf")); //用户信息
				//websocket使用类
				var mysocket = new __WEBPACK_IMPORTED_MODULE_3__ui_socket__["a" /* default */ ]("ws://localhost:8080/websocket");
				//地图操作类
				var position = new __WEBPACK_IMPORTED_MODULE_2__ui_position__["a" /* default */ ]({
					height: 500,
					width: 500,
					vessel: "map"
				});
				//页面更新类
				var update = new __WEBPACK_IMPORTED_MODULE_4__ui_main_pageUpdate__["a" /* default */ ]();

				//	页面更新
				(function() {
					$("#avatar img").attr('src', myInf.userMessage.image);
					$("#avatar span").text(myInf.userMessage.name);
					$(".myinfdropdown .menu b").text(myInf.userMessage.integral);
				})();
				//地图中心默认 为西湖
				position.drawMap(120.108478, 30.220671, () => {});

				//定位
				if (!sessionStorage.getItem("longitude")) {
					position.getMyPosition(data => {
						position.addPicker([data.position.lng, data.position.lat], datax => {
							$("#now_position").text(datax.address);
							$("#nearest").text(datax.nearestPOI);
							console.log("移动标记获得的经纬度：", datax.position.lng, datax.position.lat);
							lngAndLat[0] = datax.position.lng;
							lngAndLat[1] = datax.position.lat;

							$('#modal1').modal({
								closable: false,
								onDeny: function() {
									$('#wait_loading')[0].style.display = "none";
								},
								onApprove: function() {
									//获取页面更新数据,并更新数据
									firstLoading(lngAndLat[0], lngAndLat[1]);
								}
							}).modal('show');
						}, () => {
							alert("自选位置失败！");
						});
					});
				}

				//添加点击热点事件
				position.clickToGetPosition(data => {
					// console.log(JSON.stringify(data), [data.location.lng, data.location.lat]);
					console.log("点击热点获得的经纬度：", data.location.lng, data.location.lat);
					lngAndLat[0] = data.location.lng;
					lngAndLat[1] = data.location.lat;
					position.getPositionByLonLat([data.location.lng, data.location.lat]).then(datax => {
						// alert(JSON.stringify(datax));
						$("#now_position").text(datax.formattedAddress);
						$("#nearest").text(data.name);
					}, err => {
						alert(err);
					}).catch();
				});

				//此方法用以拖拽聊天框
				function addChatBoxMove(boxx) {
					var box = boxx.find('.header');
					// console.log(box);
					box.mousedown(function(event) {
						var rex = event.clientX - boxx[0].offsetLeft;
						var rey = event.clientY - boxx[0].offsetTop;
						console.log(box.offset().top);
						box.mousemove(function(event) {
							boxx.css({
								left: event.clientX - rex + 'px',
								top: event.clientY - rey + 'px'
							});
						});
						$(document).mouseup(function(event) {
							box.unbind('mousemove');
							$(document).unbind('mouseup');
						});
					});
				}

				//此socket连接使用
				function useWebsocket() {
					//连接服务器
					mysocket.connect();
					console.log("connect success!");
					//监听数据返回
					mysocket.listenMessageBack(data => {
						var datax = JSON.parse(data);
						console.log("websocketBackMes:", data);
						switch (datax.type) {
							case "1":
								update.InfTypeOne(datax, $(".msg_content"));
								break;
							case "2":
								console.log(datax.company);
								if (datax.company)(datax.choose == "true") ? update.InfTypeTwoWang(datax, $(".msg_content")) : update.InfTypeThreeWang(datax, $(".msg_content"));
								if (!datax.company)(datax.choose == "true") ? update.InfTypeFiveWang(datax, $(".msg_content")) : update.InfTypeSixWang(datax, $(".msg_content"));
								break;
							case "3":

								break;
							case "4":
								update.InfTypeFour(datax, $(".msg_content"));
								break;
							case "5":
								//添加最后聊天时间
								chatLastTime(datax.time);
								//添加聊天记录
								var cla;
								var cccimg;
								$.each(datax.records, function(index, el) {
									console.log(el.record);
									cla = el.fromId == myInf.userMessage.id ? "me" : "others";
									cccimg = el.fromId == myInf.userMessage.id ? myInf.userMessage.image : chatImage;
									chatUpdata(cla, cccimg, el.record);
								});
								break;
							case "6":
								addMsg("您的信誉等级不够！");
								break;
							case "7":
								addMsg("对不起，您的积分不够！");
								break;
							default:
								console.log('$("#chat_box").is(":visible"):' + $("#chat_box").is(':visible'));
								console.log('inArray(datax.senderId, unreadMap.userId)：' + inArray(datax.senderId, unreadMap.userId));
								if (datax.senderId == chatId && $("#chat_box").is(':visible')) {
									chatUpdata("others", chatImage, datax.body);
								} else if (inArray(datax.senderId, unreadMap.userId) >= 0) {
									unreadMap.record[inArray(datax.senderId, unreadMap.userId)].push(datax.body);
								} else {
									unreadMap.userId.push(datax.senderId);
									console.log(unreadMap.userId);
									unreadMap.record[unreadMap.userId.length - 1] = [];
									unreadMap.record[unreadMap.userId.length - 1].push(datax.body);
									unreadMap.time.push(datax.time);
									update.unreadChatMsgWang(datax, $("#main_menu"));
								}
						}
					});
					window.onbeforeunload = function() {
						mysocket.getSocket().close();
					};
				}

				console.log(sessionStorage.getItem("longitude"));

				function firstLoading(longitude, latitude) {
					$.post('./loading', {
						"longitude": longitude,
						"latitude": latitude
					}, function(data, textStatus, xhr) {
						console.log(JSON.stringify(data));
						if (!sessionStorage.getItem("longitude")) {
							sessionStorage.setItem("longitude", longitude);
							sessionStorage.setItem("latitude", latitude);
						}
						(function(data) {
							//top
							for (var i = 0; i < data.user.length; i++) {
								update.topUpdate(data.user[i], $(".top_list ul"));
							}
							//entrust
							for (var i = 0; i < data.publish.length; i++) {
								update.entrustUpdate(data.publish[i], $(".entrust_content"));
							}
							//fetch
							for (var i = 0; i < data.request.length; i++) {
								update.fetchUpdate(data.request[i], $(".fetch_content"));
							}
							//message
							for (var i = 0; i < data.message.length; i++) {
								if (data.message[i].msgType == 2) {
									update.InfTypeTwo(data.message[i], $(".msg_content"));
								} else if (data.message[i].msgType == 3) {
									update.InfTypeThree(data.message[i], $(".msg_content"));
								} else if (data.message[i].msgType == 1) {
									update.InfTypeOneLai(data.message[i], $(".msg_content"));
								} else if (data.message[i].msgType == 4) {
									update.InfTypeFourLai(data.message[i], $(".msg_content"));
								} else if (data.message[i].msgType == 5) {
									update.InfTypeFiveLai(data.message[i], $(".msg_content"));
								} else if (data.message[i].msgType == 6) {
									update.InfTypeSixLai(data.message[i], $(".msg_content"));
								}
							}

							//unreadMsg
							for (var i = 0; i < data.unread.length; i++) {
								console.log('data.Unread.fromuserId:' + data.unread[i].fromuserId);
								unreadMap.userId.push(data.unread[i].fromuserId);
								console.log('unreadMap.userId:' + unreadMap.userId[i]);
								unreadMap.record.push(data.unread[i].record);
								console.log('data.Unread.record' + data.unread[i].record[0]);
								unreadMap.time.push(data.unread[i].time);
								update.unreadChatMsg(data.unread[i], $("#main_menu"));
							}
							console.log(JSON.stringify(unreadMap));
							$(".wait_loading").hide('fast', function() {
								$("#main_content").show();
							});
						})(data);
						$("#wait_loading").remove();
						useWebsocket();
						getMoreData();
					});
				}

				//加载器
				function addLoading(box) {
					let loading = '<div class="ui segment"><div class="ui active inverted dimmer"><div class="ui text loader"></div></div><p><img src="../images/para.png" alt=""></p></div>';
					box.append(loading);
				}

				//添加返回顶部单击事件
				$("#backtop").click(function(event) {
					$(".segment").remove();
					$('html,body').animate({
						scrollTop: 0
					}, 400);
				});

				function getMoreData() {
					//滚动监听，到底部获取数据
					$(window).scroll(function(event) {
						var nowHeight = $(window).scrollTop();
						var msgContent = $(".inf_content .msg_content").offset().top;
						var nowBox = clickFlag === 0 ? $(".entrust_content") : $(".fetch_content");
						var nowNum = nowBox.find(".entrust").length;
						var nowOffsetTop = nowBox.find(".entrust").eq(nowNum - 1).offset().top;
						//控制返回顶部
						if (nowHeight > 250) {
							$("#backtop").fadeIn();
						} else {
							$("#backtop").fadeOut();
						}
						if (nowHeight > msgContent) {}
						//到达最后一个

						if (ifGet) {
							if (nowHeight + $(window).height() > nowOffsetTop) {
								//加载框
								//addLoading(nowBox);
								if ($(".segment").length === 0) {
									addLoading(nowBox);
								}
								//请求数据, 
								if (clickFlag === 0) {
									$.post('../task1/putdown', {
										"page": pageTimeOne,
										"select": searchAddress,
										"limit": searchCredit
									}, function(data, textStatus, xhr) {
										if (data.length == 0) ifGet = false;
										(function(data) {
											console.log("请求获得的数据：", JSON.stringify(data));
											pageTimeOne++;
											if (textStatus === "success") {
												//移除加载框
												$(".segment").remove();
												for (var i = 0; i < data.length; i++) {
													update.entrustUpdate(data[i], nowBox);
												}
											} else {
												//error
												throw new Error('Data Request Failed!');
											}
										})(data);
									});
								} else {
									$.post('../task/putdown', {
										"page": pageTimeTwo,
										"select": searchAddress,
										"limit": searchCredit
									}, function(data, textStatus, xhr) {
										if (data.length == 0) ifGet = false;
										(function(data) {
											pageTimeTwo++;
											if (textStatus === "success") {
												//移除加载框
												$(".segment").remove();
												//更新页面
												for (var i = 0; i < data.length; i++) {
													update.fetchUpdate(data[i], nowBox);
												}
											} else {
												//error
												throw new Error('Data Request Failed!');
											}
										})(data);
									});
								} //else
							} //if (>)
						} //ifget
					});
				} //function getMoreData()

				//搜索
				function mainSearch() {
					searchCredit = $(".main_search").eq(0).val();
					if (!searchCredit) searchCredit = 5;
					searchAddress = $(".main_search").eq(1).val();
					if (!searchAddress) searchAddress = " ";
					console.log('searchCredit：' + searchCredit);
					console.log('searchAddress：' + searchAddress);
					if (searchAddress) {
						if (clickFlag === 0) {
							$.post('../task1/selectAddress', {
								"select": searchAddress,
								"limit": searchCredit
							}, function(data, textStatus, xhr) {
								/*optional stuff to do after success */
								$(".entrust_content").empty();
								(function(data) {
									for (var i = 0; i < data.length; i++) {
										update.entrustUpdate(data[i], $(".entrust_content"));
									}
								})(data);
							});
						} else if (searchAddress && clickFlag === 1) {
							$.post('../task/selectAddress', {
								"select": searchAddress,
								"limit": searchCredit
							}, function(data, textStatus, xhr) {
								/*optional stuff to do after success */
								$(".fetch_content").empty()(function(data) {
									for (var i = 0; i < data.length; i++) {
										update.fetchUpdate(data[i], $(".fetch_content"));
									}
								})();
							});
						}
					}
				}

				//以下为全局事件
				$("body").on('click', 'button', function(event) {
					event.preventDefault();
				});
				//切换entrust and fetch
				$("#func_nav button").each(function(index, el) {
					$(this).click(function(event) {
						event.preventDefault();
						if (index === 0) {
							clickFlag = index;
							$(".fetch_content").fadeOut("fast", () => {
								$(".entrust_content").fadeIn("fast");
							});
						} else if (index === 1) {
							clickFlag = index;
							$(".entrust_content").fadeOut("fast", () => {
								$(".fetch_content").fadeIn("fast");
							});
						} else if (index === 3) {
							if (!myInf.isCertification) {
								addMsg('您尚未进行实名认证 ，是否进行实名认证')
								return
							}
							location.href = "../task1/writing";
						} else if (index === 2) {
							if (!myInf.isCertification) {
								addMsg('您尚未进行实名认证 ，是否进行实名认证')
								return
							}
							sessionStorage.setItem("writing", "fetch");
							location.href = "../task1/writing";
						}
					});
				});

				//搜索
				$(".prompt.main_search").focus(function(event) {
					event.preventDefault();
					$(".prompt.main_search").keypress(function(event) {
						event.preventDefault();
						if (event.which === 13) {
							mainSearch();
							$(".main_search").eq(1).val("");
							$(".prompt.main_search").unbind("keypress");
							return;
						}
					});
				});


				//未读消息
				$(document).on('click', '.unreadChatMsg', function(event) {
					event.preventDefault();
					// $(this).remove();
					$(this).find('.avatar').dblclick();
					$("#chat_box .header .name").text($(this).find('span').text());
				});

				//双击以聊天
				$(document).on('dblclick', '.top_list .avatar', function(event) {
					clearTimeout(time);
					chatId = $(this).parent().find(".hide").text();
					if (JSON.parse(sessionStorage.getItem("myInf")).userMessage.id != chatId) {
						//消息清空历史
						clearTimeout(time);
						$("#chat_box .header .name").text($(this).parent().find('span').text());
						$("#chatrecorde").empty();

						console.log("与id为" + chatId + "的用户聊天");
						let unreadset = inArray(chatId, unreadMap.userId);
						console.log("chatID:" + chatId);
						chatImage = $(this).attr('src');
						console.log("chatImage:" + chatImage);
						console.log("unreadset:" + unreadset);

						console.log("unreadMap.time[unreadset]" + unreadMap.time[unreadset]);
						unreadset >= 0 ? initChatBox(unreadMap.record[unreadset], unreadMap.time[unreadset]) : mysocket.sendFetchMessTypeFive(myInf.userMessage.id, chatId, () => {
							console.log("get history chat record");
						});
						$("#chat_box .header").unbind();
						$("#chat_box").css({
							// top: (event.clientY - $("#chat_box").height() / 2) + 'px',
							// left: (event.clientX - $("#chat_box").width() / 2) + 'px'
							bottom: 0,
							right: 0
						});
						$("#chat_box").removeClass('hidden');
						$("#chat_box").show(300);
						addChatBoxMove($("#chat_box"));
					} else {
						console.log("点击了自己的头像！");
					}
				});


				$(document).on('click', '.top_list .avatarc', function(event) {
					clearTimeout(time);
					var that = this;
					time = setTimeout(function() {
						window.location.href = "./otherspace.html?personId=" + $(that).parent().find(".hide").text();
					}, 250);
				});

				function inArray(num, arr) {
					for (var i = 0; i < arr.length; i++) {
						if (num == arr[i]) {
							return i;
						}
					}
					return -1;
				}

				//更新未读消息
				function initChatBox(data, time) {
					console.log("Get Unread Record.");
					chatLastTime(time);
					$.each(data, function(index, el) {
						chatUpdata("others", chatImage, el);
					});
				}

				//聊天
				$("#chat_box .edit_box button").on('click', function(event) {
					event.preventDefault();
					let mes = $("#chat_box .edit_box input");

					//聊天 与服务器通信
					mes.val() === '' ? null : mysocket.chat(chatId, mes.val(), () => {
						chatUpdata("me", myInf.userMessage.image, mes.val());
						mes.val('');
					});
				});

				$("#chat_box .edit_box input").bind('keyup', function(event) {
					event.preventDefault();
					if (event.keyCode == '13') {
						let mes = $("#chat_box .edit_box input");
						//聊天 与服务器通信
						mes.val() === '' ? null : mysocket.chat(chatId, mes.val(), () => {
							chatUpdata("me", myInf.userMessage.image, mes.val());
							mes.val('');
						});
					}

				});

				//接受委托
				$(document).on('click', '.entrust .header', function(event) {
					event.preventDefault();
					/* Act on the event */
					var odd = $(this).find('.odd').text();
					if (clickFlag === 0) {
						$('#modal2').modal({
							closable: false,
							onDeny: function() {},
							onApprove: function() {
								//确认发出
								(function(odd) {
									mysocket.sendFetchMessTypeOne(odd);
								})(odd);

								addMsg("请求已发出，请等待确认。");
							}
						}).modal('show');
					} else {
						$("#modal4 .content_4").empty();
						$.post('../task/clickrequest', {}, function(data, textStatus, xhr) {
							(function() {
								if (data.length === 0) {
									$("#modal4 .content_4").text("您还没有暂存！！");
								} else {
									for (var i = 0; i < data.length; i++) {
										update.extrustxUpdate(data[i], $("#modal4 .content_4"));
									}
								}
								$("#modal4 .entrustx").each(function() {
									$(this).click(function() {
										$("#modal4 .entrustx").each(function() {
											$(this).removeClass('isclick');
										});
										$(this).addClass('isclick');
										publishOdd = $(this).find('b').text();
										supportId = $(this).find('.publish_id').text();
										console.log("publishOdd:" + publishOdd + "||supportId:" + supportId);
									})
								});
							})();
							$('#modal4').modal({
								closable: false,
								onDeny: function() {},
								onApprove: function() {
									$("#modal5").modal({
										onApprove: function() {
											let reward = $(this).find("input").val();
											if (!/^[1-9]\d*$/.test(reward)) {
												$(this).find("input").parent("div").addClass('error');
												return false;
											} else {
												//开始提交
												mysocket.sendFetchMessTypeFour(publishOdd, odd, reward, supportId, () => {});
											}
										}
									}).modal('show');
								}
							}).modal('show');
						});
					}
				});

				//聊天窗口关闭
				$("#chat_box .header i:last").click(function(event) {
					/* Act on the event */
					console.log(event.target);
					$("#chat_box").transition('fade left');
				});

				//委托 请求 接受
				$(".msg_content").on('click', ".msg1 button:last", function(event) {
					event.preventDefault();
					var that = $(this);
					mysocket.sendFetchMessTypeTwo($(this).parent('div').find('.hide').text(), -1, true, () => {
						console.log("订单确认消息已发送！");
						that.parent('div').parent('div').remove();
					});
				});
				//委托 请求 拒绝
				$(".msg_content").on('click', ".msg1 .extra button:first", function(event) {
					event.preventDefault();
					var that = $(this);
					mysocket.sendFetchMessTypeTwo($(this).parent('div').find('.hide').text(), -1, false, () => {
						console.log("订单拒绝消息已发送！");
						that.parent('div').parent('div').remove();
					});
				});
				// $(".msg_content").on('click', '.msg .yes', function (event) {
				// 	event.preventDefault();
				// 	$.post('../MM/changeState', {
				// 		"messageId": $(this).parent().parent().find(".message_id").text(),
				// 		"State": 2,
				// 	}, function (data, textStatus, xhr) {
				// 		$(this).parent().parent().remove();
				// 	});
				// 	$(this).parent().parent().remove();

				// });

				//代取 拒绝
				$(".msg_content").on('click', ".msg4 .extra button:last", function(event) {
					event.preventDefault();
					var that = $(this);
					$.post('../MM/changeState', {
						"messageId": $(this).parent().find(".hide").text(),
						"State": 1,
					}, function(data, textStatus, xhr) {
						$(this).parent().parent().remove();
					});
					mysocket.sendFetchMessTypeTwo($(this).parent('div').find('.hide').text(), $(this).parent('div').find('.support_id').text(), false, () => {
						console.log("订单拒绝消息已发送！");
						that.parent('div').parent('div').remove();
					});
				});

				//代取 接受
				$(".msg_content").on('click', ".msg4 .extra button:first", function(event) {
					event.preventDefault();
					var that = $(this);
					$.post('../MM/changeState', {
						"messageId": $(this).parent().find(".hide").text(),
						"State": 2,
					}, function(data, textStatus, xhr) {
						$(this).parent().parent().remove();
					});
					mysocket.sendFetchMessTypeTwo($(this).parent('div').find('.hide').text(), $(this).parent('div').find('.support_id').text(), true, () => {
						console.log("订单确认消息已发送！");
						that.parent('div').parent('div').remove();
					});
				});



				$(".msg_content").on('click', '.msg i', function(event) {
					event.preventDefault();
					$.post('../MM/message', {
						"messageId": $(this).parent().find(".message_id").text()
					}, function(data, textStatus, xhr) {

						$(this).parent().parent().remove();
					});
					$(this).parent().parent().remove();

				});

				$(".msg_content").on('click', '.msg .sure', function(event) {
					$(this).parent().parent().remove();
					history.go(0);
				});

				$(".msg_content").on('click', '.msg .yes', function(event) {
					event.preventDefault();
					$.post('../MM/changeState', {
						"messageId": $(this).parent().parent().find(".message_id").text(),
						"State": 2,
					}, function(data, textStatus, xhr) {
						$(this).parent().parent().remove();
					});
					$(this).parent().parent().remove();

				});
				$(".msg_content").on('click', '.msg .nop', function(event) {
					event.preventDefault();
					$.post('../MM/changeState', {
						"messageId": $(this).parent().parent().find(".message_id").text(),
						"State": 1,
					}, function(data, textStatus, xhr) {

						$(this).parent().parent().remove();
					});
					$(this).parent().parent().remove();

				});

				//聊天更新
				chatHeight = $("#chatrecorde").height();

				function chatUpdata(flag, avatarSrc, mes) {
					var data = `<div class="${flag}">
            <img class="avator" src="${avatarSrc}"></img>
            <br><span>${mes}</span>
        </div>`;
					$("#chatrecorde").append(data);
					chatHeight += $("#chatrecorde").children().last().height();
					$("#chatrecorde").scrollTop(chatHeight);
				}

				function chatLastTime(time) {
					var d = `<div class="time">
          ${time}
        </div>`;
					$("#chatrecorde").append(d);
				}

				//头
				$('.ui.dropdown').dropdown();

				$("#mian_write").click(function(event) {
					/* Act on the event */
					if (!myInf.isCertification) {
						addMsg('您尚未进行实名认证 ，是否进行实名认证')
						return
					}
					location.href = "../task1/writing";
				});

				function addMsg(msg) {
					$("#modal3 .content").text(msg);
					$("#modal3").modal('show');
				}
			});
			/* WEBPACK VAR INJECTION */
		}.call(__webpack_exports__, __webpack_require__(0)))

		/***/
	}),
	/* 12 */
	/***/
	(function(module, __webpack_exports__, __webpack_require__) {

		"use strict";
		class mySocket {

			constructor(address = "ws://localhost:8080/websocket") {
				this.address = address;
				if ('WebSocket' in window) {
					this.websocket = new WebSocket(address);
				} else {
					alert('当前浏览器不支持websocket,请升级浏览器');
				}
			}

			connect() {
				//连接成功建立的回调方法
				if (this.websocket) {
					this.websocket.onopen = function() {
						console.log("WebSocket连接成功");
					};
				} else {
					console.log("weosocket==null");
				}

				this.websocket.onerror = function() {
					console.log("WebSocket连接发生错误");
				};

				//监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
				window.onbeforeunload = function() {
					this.websocket.close();
				};
			}

			getSocket() {
				return this.websocket();
			}

			//接单
			sendFetchMessTypeOne(publishId) {
				console.log("sendMesType1");
				this.websocket.send(`{"type": "1","publishId": "${publishId}"}`);
			}

			sendFetchMessTypeTwo(messageId, supportId, choose, callback) {
				console.log("sendMesType2");
				var c = choose ? "true" : "false";
				this.websocket.send(`{"type": "2","messageId": "${messageId}","supportId":${supportId},"choose": "${c}"}`);
				console.log(`{"type": "2","messageId": "${messageId}","choose": "${c}"}`);
				callback && callback();
			}

			sendFetchMessTypeFour(publishId, publisherId, integral, supportId, callback) {
				console.log("sendMesType4");
				this.websocket.send(`{"type": "4","publishId": "${publishId}","publisherId": "${publisherId}","integral": "${integral}","supportId":"${supportId}"}`);
				callback && callback();
			}

			sendFetchMessTypeFive(fromId, toId, callback) {
				console.log("sendMesType2");
				this.websocket.send(`{"type": "5","fromId": "${fromId}","toId": "${toId}"}`);
				callback && callback();
			}

			chat(id, message, callback) {
				console.log("chatMes");
				this.websocket.send(`{"id": "${id}","body":"${message}"}`);
				callback && callback();
			}

			listenMessageBack(callback) {
				this.websocket.onmessage = function(event) {
					console.log("backData:", event.data);
					callback && callback(event.data);
				};
			}

			close() {
				this.websocket.close();
				this.websocket.onclose = function() {
					console.log("WebSocket连接关闭");
				};
			}

		}
		/* harmony export (immutable) */
		__webpack_exports__["a"] = mySocket;


		/***/
	}),
	/* 13 */
	/***/
	(function(module, __webpack_exports__, __webpack_require__) {

		"use strict";
		/* harmony import */
		var __WEBPACK_IMPORTED_MODULE_0_jQuery__ = __webpack_require__(1);
		/* harmony import */
		var __WEBPACK_IMPORTED_MODULE_0_jQuery___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_0_jQuery__);


		class pageUpdate {
			constructor() {}

			InfTypeOne(data, box) {
				var mes = `<div class="msg1 msg">
              <div class="header">
                From:
                <img class="ui avatar avatarc image" src="${data.image}"><span>${data.name}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class=" hide user_id">${data.passiveId}</p>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您编号为<b>${data.fromNum}</b>的委托已经被接受,是否确认?
              </div>
              <div class="extra">
                <p hidden="hidden" class="hide user_id">${data.messageId}</p>
                <button class="mini ui button basic red nop" >拒绝</button>
                <button class="mini ui button basic red yes" >接受</button>
              </div>
            </div>`;
				box.append(mes);
			}

			InfTypeOneLai(data, box) {
				var mes = `<div class="msg1 msg">
              <div class="header">
                From:
                <img class="ui avatar avatarc image" src="${data.pubuserimage}"><span>${data.pubusername}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class=" hide user_id">${data.pubuserid}</p>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您编号为<b>${data.fromNum}</b>的委托已经被接受,是否确认？
              </div>
              <div class="extra">
                <p hidden="hidden" class="hide">${data.messageId}</p>
                <button class="mini ui button basic red nop" >拒绝</button>
                <button class="mini ui button basic red yes" >接受</button>
              </div>
            </div>`;
				box.append(mes);
			}

			InfTypeTwo(data, box) {
				var mes = `<div class="msg2 msg">
              <div class="header">
              <p hidden="hidden" class="hide user_id">${data.pubuserid}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.pubuserimage}"><span>${data.pubusername}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您对<b>${data.company}</b>的接单请求已经被接受。
              </div>
              <div class="extra">
                <button class="mini ui button basic red" >确定</button>
              </div>
            </div>`;

				box.append(mes);
			}

			InfTypeTwoWang(data, box) {
				var mes = `<div class="msg2 msg">
              <div class="header">
                <p hidden="hidden" class="hide">${data.passiveId}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.image}"><span>${data.passiveName}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您对<b>${data.company}</b>的接单请求已经被接受。
              </div>
              <div class="extra">
                <button class="mini ui button basic red sure" >确定</button>
              </div>
            </div>`;

				box.append(mes);
			}

			InfTypeThree(data, box) {
				var mes = `<div class="msg3 msg">
              <div class="header">
                <p hidden="hidden" class="hide">${data.pubuserid}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.pubuserimage}"><span>${data.pubusername}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您对<b>${data.company}</b>的接单请求已经被拒绝。
              </div>
              <div class="extra">
                <button class="mini ui button basic red sure" >确定</button>
              </div>
            </div>`;

				box.append(mes);
			}

			InfTypeThreeWang(data, box) {
				var mes = `<div class="msg3 msg">
              <div class="header">
                <p hidden="hidden" class="hide">${data.passaveid}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.image}"><span>${data.passiveName}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您对<b>${data.company}</b>的接单请求已经被拒绝。
              </div>
              <div class="extra">
                <button class="mini ui button basic red sure" >确定</button>
              </div>
            </div>`;

				box.append(mes);
			}

			//*//*待修改*/*/
			InfTypeFour(data, box) {
				var mes = `   <div class="msg4 msg">
              <div class="header">
                <p hidden="hidden" class="hide">${data.passiveId}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.image}"><span>${data.name}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                Msg:类型：${data.expressType}<br>
                大小：${data.expressSize}<br>
                取件地址：${data.expressAddress}
              </div>
              <div class="extra">               
                <button class="mini ui button basic red" >接受</button>
                <button class="mini ui button basic red" >拒绝</button>
                <p hidden="hidden" class="hide">${data.messageId}</p>
                <p hidden="hidden" class="support_id">${data.supportId}</p>
              </div>
            </div>`;

				box.append(mes);
			}
			InfTypeFourLai(data, box) {
				var mes = `   <div class="msg4 msg">
              <div class="header">
                <p hidden="hidden" class="hide">${data.pubuserid}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.pubuserimage}"><span>${data.pubusername}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                Msg:类型：${data.type}<br>
                大小：${data.size}<br>
                取件地址：${data.address}
              </div>
              <div class="extra">               
                <button class="mini ui button basic red" >接受</button>
                <button class="mini ui button basic red" >拒绝</button>
                <p hidden="hidden" class="hide">${data.messageId}</p>
              </div>
            </div>`;
				box.append(mes);
			}
			InfTypeFiveLai(data, box) {
				var mes = `<div class="msg2 msg">
              <div class="header">
              <p hidden="hidden" class="hide">${data.pubuserid}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.pubuserimage}"><span>${data.pubusername}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您的单号为<b>${data.efromNum}</b>的代取请求已经被接受。
              </div>
              <div class="extra">
                <button class="mini ui button basic red" >确定</button>
              </div>
            </div>`;

				box.append(mes);
			}
			InfTypeFiveWang(data, box) {
				var mes = `<div class="msg2 msg">
              <div class="header">
                <p hidden="hidden" class="hide">${data.passiveId}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.image}"><span>${data.passiveName}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您的单号为<b>${data.fromNum}</b>的代取请求已经被接受。
              </div>
              <div class="extra">
                <button class="mini ui button basic red sure" >确定</button>
              </div>
            </div>`;

				box.append(mes);
			}
			InfTypeSixLai(data, box) {
				var mes = `<div class="msg3 msg">
              <div class="header">
              <p hidden="hidden" class="hide">${data.pubuserid}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.pubuserimage}"><span>${data.pubusername}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您的单号为<b>${data.efromNum}</b>的代取请求已经被接受。
              </div>
              <div class="extra">
                <button class="mini ui button basic red" >确定</button>
              </div>
            </div>`;

				box.append(mes);
			}
			InfTypeSixWang(data, box) {
				var mes = `<div class="msg3 msg">
              <div class="header">
                <p hidden="hidden" class="hide">${data.passiveId}</p>
                From:
                <img class="ui avatar avatarc image" src="${data.image}"><span>${data.passiveName}</span>
                <i class="close icon" style="float: right;"></i>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                您的单号为<b>${data.fromNum}</b>的代取请求已经被拒绝。
              </div>
              <div class="extra">
                <button class="mini ui button basic red sure" >确定</button>
              </div>
            </div>`;

				box.append(mes);
			}

			entrustUpdate(data, box) {
				var entrust = `
          <div class="ui card entrust">
              <div class="content">
                <div class="right floated meta">
                  <i class="icon write"></i>${data.publishDate}
                </div>
                <img class="ui avatar avatarc image" src="${data.publisheruserimage}"><span>${data.publisherusername}</span>
                <p hidden="hidden" class="id hide">${data.publisheruserId}</p>
                <p hidden="hidden" class="message_id">${data.messageId}</p>
              </div>
              <div class="content">
                <div class="header ">
                  <p hidden="hidden" class="odd">${data.fromNum}</p>
                  <span style="margin-right:20px;" class="type">类型:${data.type}</span>
                  <span style="margin-right:20px;" class="size">大小:${data.size}</span>
                  <span>信誉限制:${data.requirement}</span>
                  <span class="right floated"><i class="trophy icon red"></i>${data.integral}</span>
                </div>
                <div class="meta">
                    <span class="date">${data.address}</span>
                </div>
                <div class="description">
                   备注：${data.tip}
                </div>
              </div>
              <div class="extra content">
                <a href="" title="" class="express_type">${data.company}</a>
                <span>截止时间:${data.takeDate}</span>
                <span class="right floated"><i class="location arrow icon"></i>${data.distance}米</span>
              </div>
          </div>`;

				box.append(entrust);
			}

			extrustxUpdate(data, box) {
				var extrustx = `<div class="ui card entrustx" >
        <p hidden="hidden" class="publish_id hide">${data.publishId}</p>
        <div class="content">
          <div class="right floated meta">
            <i class="icon write"></i>${data.publishDate}
            </div>
            <span>单号：<b class="odd">${data.fromNum}</b></span>
          </div>
          <div class="content">
            <div class="header ">
              <span style="margin-right:20px;" class="type">类型:${data.type}</span>
              <span style="margin-right:20px;" class="size">大小:${data.size}</span>
            </div>
            <div class="meta">
              <span class="date">${data.address}</span>
            </div>
          </div>
          <div class="extra content">
			<a href="" title="" class="express_type">${data.company}</a>
              <span>截止时间:${data.takeDate}</span>
          </div>
      </div>`;
				box.append(extrustx);
			}

			fetchUpdate(data, box) {
				var fetch = `<div class="ui card entrust">
              <div class="content">
                <div class="right floated meta">
                  <i class="icon write"></i>${data.requestDate}
                </div>
                <img class="ui avatar avatarc image" src="${data.requesteruserImage}"><span>${data.requesterusername}</span>
                <p hidden="hidden" class="id hide">${data.requesteruserId}</p>
              </div>
              <div class="content">
                <div class="header ">
                  <span>信誉：${data.creditLevel}</span>
                  <p hidden="hidden" class="odd">${data.fromNum}</p>
                </div>
                <div class="meta">
                    <span class="date">${data.address}</span>
                </div>
                <div class="description">
                   备注：${data.introduce}
                </div>
              </div>
              <div class="extra content">

                <span>截止时间:${data.freeDate}</span>
              </div>
          </div>
        </div>`;

				box.append(fetch);
			}

			topUpdate(data, box) {
				var top = `<li style="padding-right: -200px;position: relative;height: 40px; margin-top: 10px;">
          <p hidden="hidden" class="chat_id hide">${data.userId}</p>
          <img class="ui avatar avatarc image" src="${data.image}" style="position: absolute;top:0;left: 0;width: 40px;height: 40px;">
            <div style="margin-left: 50px;height: 40px;width: 150px;">
              <div style="height: 20px;"><span>${data.name}</sapn></div>
              <div style="height: 20px;">本周传递了 ${data.count}个快件</div>
            </div>
       </li>`;

				box.append(top);
			}

			unreadChatMsg(data, box) {
				var msg = `<div class="item unreadChatMsg">
        <div>
            <p hidden="hidden" class="chat_id">${data.fromuserId}</p>
            <img class="ui avatar avatarc image mini" src="${data.fromuserimage}">
            <span>${data.fromusername}</span>  
        </div>
    </div>`;
				box.append(msg);
			}

			unreadChatMsgWang(data, box) {
				var msg = `<div class="item unreadChatMsg">
        <div>
            <p hidden="hidden" class="chat_id hide">${data.senderId}</p>
            <img class="ui avatar avatarc image mini" src="${data.image}">
            <span>${data.name}</span>  
        </div>
    </div>`;
				box.append(msg);
			}

		}
		/* harmony export (immutable) */
		__webpack_exports__["a"] = pageUpdate;


		/***/
	})
], [11]);
//# sourceMappingURL=main.js.map
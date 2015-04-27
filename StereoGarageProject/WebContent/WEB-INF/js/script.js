//********************************* some const  *********************************//
/*
 * collection of url
 */
var Urls = {
    url_all_user: "http://localhost:8080/StereoGarageProject/User/AllUser",

    url_storey_lot: "http://localhost:8080/StereoGarageProject/Lot/StoreyLot",

    url_lot_operation: "http://localhost:8080/StereoGarageProject/Lot/Operation",

    url_one_user : "http://localhost:8080/StereoGarageProject/User/OneUser"
}

/*
 * postAction that tell callback which action have been done
 */
var PostAction = {
    post_all_user: 0,

    post_storey_lot: 1,

    post_lot_operation: 2,

    post_one_user : 3,

    post_lot_operation : 4
}

var OperationType = {
    reserve : 0,

    park : 1,

    leave : 2,

    contract : 3
}

var ResponseStatus = {
	success : 0,

	failed : 1
}

var LotStatus = {
    empty: 0,

    using: 1,

    reservation: 2
}

var Gender = {
    male : 1,
    female : 0
}

//*********************************  entiity  *********************************//
+function($){
    function LotInfo (lotInfo){
        var lotInfo = lotInfo;

        //public method
        this.showControllerInfo = function(){
            this.clearControlInfo();
            var status;
            $(".lotInfo .lotId-text").text(lotInfo.lotId);
            $(".lotInfo .lotStorey-text").text(lotInfo.storey);
            switch(lotInfo.status){
            case LotStatus.empty:
                status = "空车位";
                $("#contract-btn").removeClass("disabled");
                $("#leave-btn").removeClass("disabled");
                $("#reserve-btn").removeClass("disabled");
                $("#parking-btn").removeClass("disabled");
                break;
            case LotStatus.using:
                status = "正在使用中";
                $("#leave-btn").removeClass("disabled");
                var startTime = new Date(lotInfo.parkingStartTime).Format("yyyy-MM-dd hh:mm:ss");
                var endTime = new Date(lotInfo.parkingEndTime).Format("yyyy-MM-dd hh:mm:ss");
                $(".lotInfo .startTime-text").text(startTime);
                $(".lotInfo .endTime-text").text(endTime);
                break;
            case LotStatus.reservation:
                status = "预约中";
                $("#parking-btn").removeClass("disabled");
                var time = new Date(lotInfo.reservationTime).Format("yyyy-MM-dd hh:mm:ss");
                $(".lotInfo .reserveTime-text").text(time);
                break;
            }
            $(".lotInfo .lotStatus-text").text(status);
            if(lotInfo.isContracted){
                $(".lotInfo .isContracted-text").text("是");
            }else{
                $(".lotInfo .isContracted-text").text("否");
            }
            if(lotInfo.userInfo != null){
                showUserInfo(lotInfo.userInfo);
            }
        };

        var showUserInfo = function(userInfo){
            $(".userInfo .displayName-text").text(userInfo.displayName);
            $(".userInfo .identityId-text").text(userInfo.identityId);
            if(userInfo.company != "" && userInfo.company != null && typeof(userInfo.company) != "undefined"){
                $(".userInfo .company-text").text(userInfo.company);
            }
        };

        this.initButtons = function(){
            $("#reserve-btn").unbind("click");
            $("#parking-btn").unbind("click");
            $("#leave-btn").unbind("click");
            $("#contract-btn").unbind("click");
            $("#general-modal .modal-footer button:last").unbind("click");
            switch(lotInfo.status){
            case LotStatus.empty:
                $("#reserve-btn").click(function(){
                    initGeneralModal(OperationType.reserve);
                    $("#general-modal").modal({keyboard:true});
                    $("#general-modal .modal-footer button:last").click(function(){
                        if(invalidGeneralForm(OperationType.reserve)){
                            var postJson = reserveFormToJson();
                            alert(postJson);
                            var postData = PostData.createNew(PostAction.post_lot_operation, postJson);
                            postDataFromServer(postData);
                        }
                    });
                });
                $("#parking-btn").click(function(){
                    initGeneralModal(OperationType.park);
                    $("#general-modal").modal({keyboard:true});
                    $("#general-modal .modal-footer button:last").click(function(){
                        if(invalidGeneralForm(OperationType.park)){
                            var postJson = parkingFormToJson();
                            alert(postJson);
                            var postData = PostData.createNew(PostAction.post_lot_operation, postJson);
                            postDataFromServer(postData);
                        }
                    });
                });

                $("#leave-btn").click(function(){
                    $("#leave-modal").modal();
                });

                $("#contract-btn").click(function(){
                    $("#contract-modal").modal();
                });
                break;
            case LotStatus.using:
                $("#leave-btn").click(function(){
                    $("#leave-modal").modal();
                });
                break;
            case LotStatus.reservation:
                $("#parking-btn").click(function(){
                    initParkingModal(OperationType.park);
                    $("#parking-modal").modal({keyboard:true});
                });
                break;
            }
        }

        var initGeneralModal = function(operationType){
            switch(operationType){
            case OperationType.reserve:
                $("#generalModalLabel").text("预约车位");
                $("#general-modal").find(".lotId").text(lotInfo.lotId);
                $("#general-modal").find(".operationText").text("预约");
                $("#reservationTime").appendDtpicker({"locale":"cn", "futureOnly" : true});
                $("#general-modal").find(".parkingEndTime").css("display", "none");
                $("#general-modal").find(".reservationTime").css("display", "inline");
                break;
            case OperationType.park:
                $("#generalModalLabel").text("停车");
                $("#general-modal").find(".lotId").text(lotInfo.lotId);
                $("#general-modal").find(".operationText").text("停车");
                $("#parkingEndTime").appendDtpicker({"locale":"cn", "futureOnly" : true});
                $("#general-modal").find(".parkingEndTime").css("display", "inline");
                $("#general-modal").find(".reservationTime").css("display", "none");
                break;
            case OperationType.leave:
                break;
            case OperationType.contract:
                break;
            }
        };

        var initParkingModal = function(){

        };

        var initLeaveModal = function(){

        };

        var initContractModal = function(){

        };

        function invalidGeneralForm(operationType){
            if ($("#displayName").val()==""){
                alert("用户名不能为空");
                return false;
            }

            if($("#identityId").val()==""){
                alert("身份证号不能为空");
                return false;
            }

            if($("#phoneNumber").val()==""){
                alert("电话号码不能为空");
                return false;
            }

            if(operationType == OperationType.reserve){
                var reservationTime = datepickerToTime($("#reservationTime").val());
                var now = new Date();
                if((reservationTime - now.getTime()) < 2*60*60*1000){
                    alert("预约需提前至少两小时");
                    return false;
                }
            }else if(operationType == OperationType.park){
                var parkingEndTime = datepickerToTime($("#parkingEndTime").val());
                var now = new Date();
                if((parkingEndTime - now.getTime()) < 2*60*60*1000){
                    alert("至少停车两小时");
                    return false;
                }
            }
            return true;
        }

        function invalidParkingForm(){

        }

        function invalidLeaveForm(){

        }

        function invalidContractForm(){
            
        }
    };

    LotInfo.prototype.clearControlInfo = function(){
        $(".lotInfo .lotId-text").text("--");
        $(".lotInfo .lotStorey-text").text("--");
        $(".lotInfo .lotStatus-text").text("--");
        $(".lotInfo .isContracted-text").text("--");
        $(".lotInfo .reserveTime-text").text("--");
        $(".lotInfo .startTime-text").text("--");
        $(".lotInfo .endTime-text").text("--");

        $(".userInfo .displayName-text").text("--");
        $(".userInfo .identityId-text").text("--");
        $(".userInfo .company-text").text("--");
        
        if(!$("#reserve-btn").hasClass("disabled")){
            $("#reserve-btn").addClass("disabled");
        }

        if(!$("#parking-btn").hasClass("disabled")){
            $("#parking-btn").addClass("disabled");
        }

        if(!$("#leave-btn").hasClass("disabled")){
            $("#leave-btn").addClass("disabled");
        }

        if(!$("#contract-btn").hasClass("disabled")){
            $("#contract-btn").addClass("disabled");
        }
    };

    $.LotInfo = LotInfo;

}(jQuery);

//*********************************  post data  *********************************//

var PostData = {
    createNew: function(postAction, data) {
        var postData = {};
        postData.postAction = postAction;
        postData.data = data;
        return postData;
    }
}

var StoreyRequest = {
    createNew : function(storey){
        var storeyRequest = {};
        storeyRequest.storey = storey;
        return JSON.stringify(storeyRequest);
    }
}

var ReservationRequest = {
    createNew : function(lotId, operationType, reservationTime, userInfo){
        var reservationRequest = {};
        reservationRequest.lotId = lotId;
        reservationRequest.operationType = operationType;
        reservationRequest.reservationTime = reservationTime;
        reservationRequest.userInfo = userInfo;
        return JSON.stringify(reservationRequest);
    }
}

var ParkingRequest = {
    createNew : function(lotId, operationType, parkingEndTime, userInfo){
        var parkingRequest = {};
        parkingRequest.lotId = lotId;
        parkingRequest.operationType = operationType;
        parkingRequest.parkingEndTime = parkingEndTime;
        parkingRequest.userInfo = userInfo;
        return JSON.stringify(parkingRequest);
    }
}

//*********************************  global vars  *********************************//

/*
 * when the page is scrolling disable the ta button
 */
var isScrolling = false;

var currentLotsObj = null;

var currentStorey = 1;

var pageSize = 20;

var storeyCount = 6;

//*********************************  global utils  *********************************//

/*
 * convert  form to json
 */
$.fn.serializeObject = function(){
    var o = {};
    var array = this.serializeArray();
    $.each(array, function(){
        if(o[this.name]){
            if(!o[this.name].push){
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        }else{
            o[this.name] = this.value || '';
        }
    });

    return o;
};

// 对Date的扩展，将 Date 转化为指定格式的String
// 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符， 
// 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字) 
// 例子： 
// (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423 
// (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18 
Date.prototype.Format = function (fmt) { //author: meizz 
    var o = {
        "M+": this.getMonth() + 1, //月份 
        "d+": this.getDate(), //日 
        "h+": this.getHours(), //小时 
        "m+": this.getMinutes(), //分 
        "s+": this.getSeconds(), //秒 
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度 
        "S": this.getMilliseconds() //毫秒 
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
    if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
}


/*
 * convert datepicker to timestamp(long)
 */
function datepickerToTime(datepicker){
    var date = datepicker.split(/\s+/)[0].split("-");
    var time = datepicker.split(/\s+/)[1].split(":");
    var year = date[0];
    var month = date[1];
    var day = date[2];
    var hours = time[0];
    var minute = time[1];
    return new Date(year, month-1, day, hours, minute).getTime();
}


function reserveFormToJson(){
    var userInfo = $("#general-form").serializeObject();
    var lotId = $("#general-form").find(".lotId").text();
    var operationType = OperationType.reserve;
    var datepicker = $("#reservationTime").val();
    var reservationTime = datepickerToTime(datepicker);
    var jsonText = ReservationRequest.createNew(lotId, operationType, reservationTime, userInfo);
    return jsonText;
}

function parkingFormToJson(){
    var userInfo = $("#general-form").serializeObject();
    var lotId = $("#general-form").find(".lotId").text();
    var operationType = OperationType.park;
    var datepicker = $("#parkingEndTime").val();
    var parkingEndTime = datepickerToTime(datepicker);
    var jsonText = ParkingRequest.createNew(lotId, operationType, parkingEndTime, userInfo);
    return jsonText;
}

//*********************************  ajax main function  *********************************//

/*
 * response callback to handle the data from server
 */
function httpResponse(responseTxt, postAction) {
	var responseStatus = JSON.parse(responseTxt).status;
	var responseContent = JSON.parse(responseTxt).content;
    switch (postAction) {
        case PostAction.post_all_user:
            console.log("all User");
            $.each(responseContent, function(i, item) {

            });
            break;
        case PostAction.post_one_user:
        	break;
        case PostAction.post_storey_lot:
            currentLotsObj = responseContent;
            var lots;
            switch (currentStorey) {
                case 1:
                    lots = $("#storeyOne .col-md-3");
                    break;
                case 2:
                    lots = $("#storeyTwo .col-md-3");
                    break;
                case 3:
                    lots = $("#storeyThree .col-md-3");
                    break;
                case 4:
                    lots = $("#storeyFour .col-md-3");
                    break;
                case 5:
                    lots = $("#storeyFive .col-md-3");
                    break;
                case 6:
                    lots = $("#storeySix .col-md-3");
                    break;
            }

            $.each(currentLotsObj, function(i, lotInfo) {
                var storey = lotInfo.storey;
                var lotId = lotInfo.lotId;
                if (i % 20 <= 3) {
                    lots.eq(i).tooltip({
                        title: "当前楼层:" + storey + "  " + "车位号:" + lotId,
                        placement: "bottom"
                    });
                } else {
                    lots.eq(i).tooltip({
                        title: "当前楼层:" + storey + "  " + "车位号:" + lotId,
                        placement: "top"
                    });
                }
                updateDetailLotView(lots.eq(i), lotInfo);
            });
            break;

    }
}

/*
 * post data from server
 */
function getServerData(event) {
    console.log("getServerData");
    var url = "";

    var postAction;
    var data;

    postAction = event.data.postAction;
    data = event.data.data;
    if (postAction == PostAction.post_all_user) {
        url = Urls.url_all_user;
    } else if (postAction == PostAction.post_storey_lot) {
        url = Urls.url_storey_lot;
    }

    var callback = function(responseTxt, statusTxt) {
        if (statusTxt == "success") {
            httpResponse(responseTxt, postAction);
        } else {
            alert("网络错误");
        }
    }

    $.post(url, data, callback);
}

function postDataFromServer(data) {
    console.log("connect server...");
    var url = "";

    var postAction;
    var data;

    postAction = data.postAction;
    data = data.data;
    switch(postAction){
    case PostAction.post_all_user:
        url = Urls.url_all_user;
        break;
    case PostAction.post_one_user:
        url = Urls.url_one_user;
        break;
    case PostAction.post_storey_lot:
        url = Urls.url_storey_lot;
        break;
    case PostAction.post_lot_operation:
        url = Urls.url_lot_operation;
        break;
    }

    var callback = function(responseTxt, statusTxt) {
        if (statusTxt == "success") {
            console.log("PostAction: " + postAction + " success");
            httpResponse(responseTxt, postAction);
        } else {
            alert("网络错误");
        }
    }

    $.post(url, data, callback);
}

//*********************************  view control function  *********************************//

/*
 * function that use to change the tabs
 */
function changeTab(tabContent, offset, callback) {
    tabContent.animate({
        left: "+=" + offset
    }, 500, callback);
}

function initDetailLotView(){
	$(".lotView").each(function(i, item){
		$(this).children(".col-md-3").each(function(i, item){
		  $(this).append('<div class="container"></div>');
		  $(this).find(".container").append('<img src="image/logo.png" alt="" class="img-rounded">').append('<span><strong><strong></span>');
        });
	});
}

function updateDetailLotView(item, lotInfo){
	if (lotInfo.status == LotStatus.empty) {
		item.css("background-color", "white");
		item.find("img").attr("src", "");
    } else if (lotInfo.status == LotStatus.using) {
    	item.css("background-color", "red");
    	item.find("img").attr("src", "image/using.png");
    } else if (lotInfo.status == LotStatus.reservation) {
        item.css("background-color", "yellow");
        item.find("img").attr("src", "image/reservation.png");
    }
}

function clearControlInfo(){
    $(".lotInfo .lotId-text").text("--");
    $(".lotInfo .lotStorey-text").text("--");
    $(".lotInfo .lotStatus-text").text("--");
    $(".lotInfo .isContracted-text").text("--");
    $(".lotInfo .reserveTime-text").text("--");
    $(".lotInfo .startTime-text").text("--");
    $(".lotInfo .endTime-text").text("--");

    $(".userInfo .displayName-text").text("--");
    $(".userInfo .identityId-text").text("--");
    $(".userInfo .company-text").text("--");
    
    if(!$("#reserve-btn").hasClass("disabled")){
        $("#reserve-btn").addClass("disabled");
    }

    if(!$("#parking-btn").hasClass("disabled")){
        $("#parking-btn").addClass("disabled");
    }

    if(!$("#leave-btn").hasClass("disabled")){
        $("#leave-btn").addClass("disabled");
    }

    if(!$("#contract-btn").hasClass("disabled")){
        $("#contract-btn").addClass("disabled");
    }  
}


//*********************************  main  *********************************//

/*
 * execute when page load complete
 */
$(document).ready(function() {
    for (i = 0; i < 20; i++) {
        $(".container .lotView").append('<div class="col-md-3" data-toggle="tooltip" data-placement="top"></div>');
    }

    initDetailLotView();
    clearControlInfo();
    $("#storeyOne .col-md-3, #storeyTwo .col-md-3, #storeyThree .col-md-3, #storeyFour .col-md-3, #storeyFive .col-md-3, #storeySix .col-md-3").mouseover(function() {
        $(this).css("opacity", 0.5);
    }).mouseout(function() {
        $(this).css("opacity", 1);
    }).each(function(i, item) {
        $(this).click(function(event) {
        	//handle with the info responsed from server
            // showControllerInfo(currentLotsObj[i%20]);
            var lotInfo = new $.LotInfo(currentLotsObj[i%20]);
            lotInfo.showControllerInfo();
            lotInfo.initButtons();
            event.stopPropagation();
        });
    });

    var postData = PostData.createNew(PostAction.post_storey_lot, JSON.stringify({
        storey: currentStorey
    }));
    postDataFromServer(postData);

    $("#myTab li").each(function(i, item) {

        $(this).click(function() {
        	if(!isScrolling){
        		isScrolling = true;
        		var offset = (currentStorey - i - 1) * 800;
            	changeTab($(".tab-content"), offset, function() {
            		clearControlInfo();
                	currentStorey = i + 1;
                    var postJson = StoreyRequest.createNew(currentStorey);
                	var postData = PostData.createNew(PostAction.post_storey_lot, postJson);
                	postDataFromServer(postData);
                	isScrolling = false;
            	});
            }
        });
    });

    $("#general-modal").on('hide.bs.modal', function(){
        $("#reservationTime").handleDtpicker('hide');
        $("#reservationTime").handleDtpicker('destroy');
        $("#parkingEndTime").handleDtpicker('hide');
        $("#parkingEndTime").handleDtpicker('destroy');
    });
});

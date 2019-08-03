var service_management_standardization_filter;
var service_management_standardization_desc = "asc";
var service_management_standardization_orderBy = "id";

var selected_service_list = new Array();
/**
 * 服务标准化
 */
function serviceManagementStandardization() {
    service_management_standardization_filter = null;
    service_management_standardization_desc = "asc";
    service_management_standardization_orderBy = "id";
    selected_service_list = new Array();

    $("#main_content").html('');
    $("#main_content").html('<div class="row div-row">' +
            '<label for="standardization_rule_select">标准化准则：</label>' +
            '<select id="standardization_rule_select">' +
                '<option value="1">标准化准则1</option>' +
                '<option value="2">标准化准则2</option>' +
                '<option value="3">标准化准则3</option>' +
        '</select>' +
        '</div>' +
        '<div class="row div-row">' +
        '<div class="checkbox">' +
            '<label>' +
                '<input id="delete_original_data_checkbox" type="checkbox">标准化完成后删除原始服务表中的数据' +
            '</label>' +
        '</div>' +
        '</div>' +
        '<h4>请选择需要标准化的服务：</h4>' +
            '<div class="row div-row">' +
                '<table id="service_management_standardization_table" class="table-responsive table table-bordered table-hover">' +
                    '<tr>' +
                        '<th>ID</th>' +
                        '<th>名称</th>' +
                        '<th>内容</th>' +
                        '<th>创建时间</th>' +
                        '<th>修改时间</th>' +
                        '<th>是否要标准化</th>' +
                    '</tr>' +
                '</table>' +
            '</div>' +
        '<div class="row div-row">' +
            '<label for="standardization_filter_label">当前筛选条件：</label>' +
            '<label id="standardization_filter_label">无</label>' +
        '</div>' +
        '<div class="row text-center div-row">' +
            '<nav aria-label="Page navigation">' +
                '<ul id="service_management_standardization_pagination" class="pagination"></ul>' +
            '</nav>' +
        '</div>' +
        '<div class="row div-row">' +
            '<div class="col-md-2 col-md-offset-2">' +
                '<button class="btn btn-default btn-sm" onclick="serviceManagementStandardizationRefreshButton()">刷新</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" onclick="startStandardization()">开始标准化</button>' +
            '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='cluster_management_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='service_management_standardization_filter_input'/>").html("约束表达式：")).append($("<input id='service_management_standardization_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='serviceManagementStandardizationFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));

    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='serviceManagementStandardization()'/>").html(">> 服务标准化"));

    serviceManagementStandardizationRefresh(1);
}

function serviceManagementStandardizationRefreshButton() {

    selected_service_list = new Array();
    service_management_standardization_filter = null;
    service_management_standardization_desc = "asc";
    service_management_standardization_orderBy = "id";

    $("#standardization_filter_label").html("无");

    serviceManagementStandardizationRefresh(1);
}

/**
 * 获取服务信息
 * @param pageNum
 */
function serviceManagementStandardizationRefresh(pageNum) {
    $.ajax({
        type : "GET",
        url  : "/api/service/service_list_all",
        data : {"pageNum" : pageNum,"filter" : service_management_standardization_filter,"desc" : service_management_standardization_desc,"orderBy" : service_management_standardization_orderBy},
        dataType : "json",
        success : function (data) {
            $("#service_management_standardization_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                if(selected_service_list.indexOf(String(data.data.list[i].id)) > -1){
                    $("#service_management_standardization_table").append($("<tr/>")
                        .append($("<td/>").html(data.data.list[i].id))
                        .append($("<td/>").html(data.data.list[i].name))
                        .append($("<td/>").html(data.data.list[i].content))
                        .append($("<td/>").html(data.data.list[i].createTime))
                        .append($("<td/>").html(data.data.list[i].modifyTime))
                        .append($("<td/>").append($("<input type='checkbox' name='standardization_checkbox'onclick='standardizationCheckboxChoose(this)' checked='checked'>").attr("value",data.data.list[i].id))).attr("service_id",data.data.list[i].id));
                }else {
                    $("#service_management_standardization_table").append($("<tr/>")
                        .append($("<td/>").html(data.data.list[i].id))
                        .append($("<td/>").html(data.data.list[i].name))
                        .append($("<td/>").html(data.data.list[i].content))
                        .append($("<td/>").html(data.data.list[i].createTime))
                        .append($("<td/>").html(data.data.list[i].modifyTime))
                        .append($("<td/>").append(($("<input type='checkbox' name='standardization_checkbox'onclick='standardizationCheckboxChoose(this)'>").attr("value",data.data.list[i].id)))).attr("service_id",data.data.list[i].id));
                }

            }

            $("#service_management_standardization_pagination").html("");
            if(data.data.isFirstPage){
                $("#service_management_standardization_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#service_management_standardization_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='serviceManagementStandardizationRefresh(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#service_management_standardization_pagination").append($("<li/>").append($("<span onclick='serviceManagementStandardizationRefresh("  + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#service_management_standardization_pagination").append($("<li class='active'/>").append($("<span onclick='serviceManagementStandardizationRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#service_management_standardization_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#service_management_standardization_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='serviceManagementStandardizationRefresh(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }
        }
    });
}

function standardizationCheckboxChoose(obj) {
    if($(obj).is(":checked")){
        selected_service_list.push($(obj).val());
    }else {
        selected_service_list.splice(selected_service_list.indexOf($(obj).val()),1);
    }
}

/**
 * 服务标准化
 */
function startStandardization() {
    $.ajax({
        type : "GET",
        url  : "/api/service/standardization",
        data : {"serviceList" : JSON.stringify(selected_service_list),"delete" : $("#delete_original_data_checkbox").is(":checked"),"strategy" : $("#standardization_rule_select option:selected").val()},
        dataType : "json",
        success : function (data) {
            alert("该功能尚未实现\n" + data.msg);
        }
    });
}

/**
 * 服务过滤
 */
function serviceManagementStandardizationFilter() {
    service_management_standardization_filter = $("#service_management_standardization_filter_input").val();
    $("#standardization_filter_label").html(service_management_standardization_filter);
    serviceManagementStandardizationRefresh(1);
}
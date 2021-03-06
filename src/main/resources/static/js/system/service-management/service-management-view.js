var service_management_view_filter;
var service_management_view_desc = "asc";
var service_management_view_orderBy = "id";
/**
 * 服务浏览
 */
function serviceManagementView(){

    service_management_view_filter = null;
    service_management_view_desc = "asc";
    service_management_view_orderBy = "id";

    $("#main_content").html('');
    $("#main_content").html('<h3>现有服务列表</h3> ' +
        '<div>' +
            '<label>排序关键字：<select id="service_management_view_sort_keyword"><option value="id">id</option><option value="name">name</option><option value="attributes">attributes</option><option value="node">node</option><option value="cluster">cluster</option><option value="content">content</option><option value="create_time">create_time</option><option value="modify_time">modify_time</option></select></label>' +
            '<label>升降序<input type="checkbox" id="service_management_view_node_sort_asc"></label>' +
            '<button onclick="serviceManagementViewNodeSort()">排序</button>' +
        '</div>' +
        '<div class="row div-row">' +
            '<table id="service_management_view_table" class="table table-bordered table-hover table-responsive">' +
                '<tr></tr>' +
            '</table>' +
        '</div>' +
        '<div class="row">' +
            '<label>当前筛选条件：</label>' +
            '<label id="service_management_view_filter_label">无</label>' +
        '</div>' +
        '<div class="row text-center div-row">' +
            '<nav aria-label="Page navigation">' +
                '<ul id="service_management_view_pagination" class="pagination"></ul>' +
            '</nav>' +
        '</div>' +
        '<div class="row div-row">' +
            '<div class="col-md-2 col-md-offset-2">' +
                '<button class="btn btn-default btn-sm" onclick="serviceManagementViewRefreshButton()">刷新</button>' +
            '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#service_management_view_filter_modal">筛选</button>' +
        '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#service_management_view_export_modal">导出</button>' +
            '</div>' +
        '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='service_management_view_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='service_management_view_filter_input'/>").html("约束表达式：")).append($("<input id='service_management_view_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='serviceManagementViewFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='service_management_view_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='serviceManagementViewExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='service_management_view_detail_modal' tabindex='-1' role='dialog' aria-labelledby='modal_detail_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_detail_title'/>").html("详细信息")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row' id='service_management_view_detail_modal_detail_content'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    serviceManagementViewRefresh(1);

    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='serviceManagementView()'/>").html(">> 服务浏览"));
}

/**
 * 排序
 */
function serviceManagementViewNodeSort() {
    service_management_view_orderBy= $("#service_management_view_sort_keyword option:selected").val();

    if($("#service_management_view_node_sort_asc").is(":checked")){
        service_management_view_desc = "asc";
    }else {
        service_management_view_desc = "desc";
    }

    serviceManagementViewRefresh(1);
}

function serviceManagementViewDBClick(obj){
    $.ajax({
        type : "GET",
        data : {"serviceID" : $(obj).attr("service_id")},
        url  : "/api/service/service_info",
        dataType : "json",
        success : function (data) {
            $("#service_management_view_detail_modal_detail_content").html(
                "服务id：" + data.data.id +"<br/>"
                + "服务名称：" + data.data.name + "<br/>"
                + "服务属性：" + parseAttribute(data.data.attributes) + "<br/>"
                + "服务内容： " + data.data.content + "<br/>"
                + "服务所属节点： " + data.data.node + "<br/>"
                + "服务所属集群：" + data.data.cluster + "<br/>"
                + "服务创建时间：" + new Date(data.data.createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                + "服务修改时间：" + new Date(data.data.modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
            );
            $("#service_management_view_detail_modal").modal();
        }
    });

}

/**
 * 服务浏览界面的刷新
 */
function serviceManagementViewRefresh(pageNum) {
    $.ajax({
        type : "GET",
        url  : "/api/service/service_list_all",
        data : {"pageNum" : pageNum,"filter" : service_management_view_filter,"desc" : service_management_view_desc,"orderBy" : service_management_view_orderBy},
        dataType : "json",
        success : function (data) {
            $("#service_management_view_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#service_management_view_table").append($("<tr onclick='serviceManagementViewTRClick(this)' ondblclick='serviceManagementViewDBClick(this)'>").html(
                    "服务id：" + data.data.list[i].id +"<br/>"
                    + "服务名称：" + data.data.list[i].name + "<br/>"
                    + "服务属性：" + parseAttribute(data.data.list[i].attributes) + "<br/>"
                    + "服务内容： " + simplifyServiceContent(data.data.list[i].content) + "<br/>"
                    + "服务所属节点： " + data.data.list[i].node + "<br/>"
                    + "服务所属集群：" + data.data.list[i].cluster + "<br/>"
                    + "服务创建时间：" + new Date(data.data.list[i].createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                    + "服务修改时间：" + new Date(data.data.list[i].modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                ).append($("<hr/>")).attr("service_id",data.data.list[i].id));
            }

            $("#service_management_view_pagination").html("");
            if(data.data.isFirstPage){
                $("#service_management_view_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#service_management_view_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='serviceManagementViewRefresh(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#service_management_view_pagination").append($("<li/>").append($("<span onclick='serviceManagementViewRefresh("  + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#service_management_view_pagination").append($("<li class='active'/>").append($("<span onclick='serviceManagementViewRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#service_management_view_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#service_management_view_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='serviceManagementViewRefresh(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }
        }
    });
}

/**
 * 排序
 * @param obj
 */
function serviceManagementViewOrder(obj) {
    service_management_view_orderBy = $(obj).attr("order");
    if(service_management_view_desc == "asc"){
        service_management_view_desc = "desc";
    }else {
        service_management_view_desc = "asc";
    }
    serviceManagementViewRefresh(1);
}

/**
 * 刷新按钮
 */
function serviceManagementViewRefreshButton() {
    service_management_view_filter = null;
    service_management_view_desc = "asc";
    service_management_view_orderBy = "id";

    $("#service_management_view_filter_label").html("无");

    serviceManagementViewRefresh(1);
}

/**
 * 每行选中事件
 * @param obj
 */
function serviceManagementViewTRClick(obj) {
    var trs = $("#service_management_view_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 筛选按钮
 */
function serviceManagementViewFilter() {
    service_management_view_filter = $("#service_management_view_filter_input").val();
    $("#service_management_view_filter_label").html(service_management_view_filter);
    serviceManagementViewRefresh(1);
}

/**
 * 数据导出
 */
function serviceManagementViewExport() {
    alert("该功能还未实现，您选中的导出数据的格式为：" + $("input[name='export_way']:checked").val());
}
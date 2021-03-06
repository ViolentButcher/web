var service_management_maintain_filter;
var service_management_maintain_desc = "asc";
var service_management_maintain_orderBy = "id";
/**
 * 服务维护界面加载
 */
function serviceManagementMaintain() {

    service_management_maintain_filter = null;
    service_management_maintain_desc = "asc";
    service_management_maintain_orderBy = "id";

    $("#main_content").html('');
    $("#main_content").html('<h4>现有服务列表：</h4>' +
        '<div>' +
            '<label>排序关键字：<select id="service_management_maintain_sort_keyword"><option value="id">id</option><option value="name">name</option><option value="attributes">attributes</option><option value="node">node</option><option value="cluster">cluster</option><option value="content">content</option><option value="create_time">create_time</option><option value="modify_time">modify_time</option></select></label>' +
            '<label>升降序<input type="checkbox" id="service_management_maintain_sort_asc"></label>' +
            '<button onclick="serviceManagementMaintainSort()">排序</button>' +
        '</div>' +
        '<div class="row div-row">' +
            '<table id="service_management_maintain_table" class="table-responsive table table-bordered table-hover">' +
            '</table>' +
        '</div>' +
        '<div class="row div-row">' +
            '<label for="service_management_maintain_filter_label">当前筛选条件：</label>' +
            '<label id="service_management_maintain_filter_label">无</label>' +
        '</div>' +
        '<div class="row text-center div-row">' +
            '<nav aria-label="Page navigation">' +
                '<ul id="service_management_maintain_pagination" class="pagination"></ul>' +
            '</nav>' +
        '</div>' +
        '<div class="row div-row">' +
            '<div class="col-md-1 col-md-offset-2">' +
                '<button class="btn btn-default btn-sm" onclick="serviceManagementMantainRefreshButton()">刷新</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_export_modal">导出</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_add_modal">添加</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_change_modal">修改</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" onclick="serviceManagementMaintainDelete()">删除</button>' +
            '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='cluster_management_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='service_management_maintain_filter_input'/>").html("约束表达式：")).append($("<input id='service_management_maintain_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='serviceManagementMaintainFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='serviceManagementMaintainExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_add_modal' tabindex='-1' role='dialog' aria-labelledby='modal_add_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_add_title'/>").html("添加数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("服务名称：").append($("<input type='text' id='service_management_maintain_add_name'>")))
                    .append($("<div class='row'/>").append("<label/>").html("服务属性：").append($("<input type='text' id='service_management_maintain_add_attributes'>")))
                    .append($("<div class='row'/>").append("<label/>").html("服务内容：").append($("<input type='text' id='service_management_maintain_add_content'>")))
                    .append($("<div class='row'/>").append("<label/>").html("服务所属节点：").append($("<input type='text' id='service_management_maintain_add_node'>"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal' onclick='serviceManagementMaintainAdd()'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_change_modal' tabindex='-1' role='dialog' aria-labelledby='modal_change_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_change_title'/>").html("修改数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("服务名称：").append($("<input type='text' id='service_management_maintain_modify_name'>")))
                    .append($("<div class='row'/>").append("<label/>").html("服务属性：").append($("<input type='text' id='service_management_maintain_modify_attributes'>")))
                    .append($("<div class='row'/>").append("<label/>").html("服务内容：").append($("<input type='text' id='service_management_maintain_modify_content'>")))
                    .append($("<div class='row'/>").append("<label/>").html("服务所属节点：").append($("<input type='text' id='service_management_maintain_modify_node'>"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'onclick='serviceManagementMaintainModify()'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_delete_modal' tabindex='-1' role='dialog' aria-labelledby='modal_delete_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_delete_title'/>").html("删除数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("字段1")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='service_management_maintain_detail_modal' tabindex='-1' role='dialog' aria-labelledby='modal_detail_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_detail_title'/>").html("详细信息")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row' id='service_management_maintain_detail_modal_detail_content'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    serviceManagementMaintainRefresh(1);

    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='serviceManagementMaintain()'/>").html(">> 服务维护"));
}

function serviceManagementMaintainSort() {
    service_management_maintain_orderBy= $("#service_management_maintain_sort_keyword option:selected").val();

    if($("#service_management_view_maintain_sort_asc").is(":checked")){
        service_management_maintain_desc = "asc";
    }else {
        service_management_maintain_desc = "desc";
    }

    serviceManagementMaintainRefresh(1);
}

/**
 * 排序
 * @param obj
 */
function serviceManagementMaintainOrder(obj) {
    service_management_maintain_orderBy = $(obj).attr("order");
    if(service_management_maintain_desc == "asc"){
        service_management_maintain_desc = "desc";
    }else {
        service_management_maintain_desc = "asc";
    }
    serviceManagementMaintainRefresh(1);
}

function serviceManagementMaintainDBClick(obj){
    $.ajax({
        type : "GET",
        data : {"serviceID" : $(obj).attr("service_id")},
        url  : "/api/service/service_info",
        dataType : "json",
        success : function (data) {
            $("#service_management_maintain_detail_modal_detail_content").html(
                "服务id：" + data.data.id +"<br/>"
                + "服务名称：" + data.data.name + "<br/>"
                + "服务属性：" + parseAttribute(data.data.attributes) + "<br/>"
                + "服务内容： " + data.data.content + "<br/>"
                + "服务所属节点： " + data.data.node + "<br/>"
                + "服务所属集群：" + data.data.cluster + "<br/>"
                + "服务创建时间：" + new Date(data.data.createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                + "服务修改时间：" + new Date(data.data.modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
            );
            $("#service_management_maintain_detail_modal").modal();
        }
    });

}

/**
 * 界面刷新
 * @param pageNum
 */
function serviceManagementMaintainRefresh(pageNum) {
    $.ajax({
        type: "GET",
        url: "/api/service/service_list_all",
        data: {
            "pageNum": pageNum,
            "filter": service_management_maintain_filter,
            "desc": service_management_maintain_desc,
            "orderBy": service_management_maintain_orderBy
        },
        dataType: "json",
        success: function (data) {
            $("#service_management_maintain_table tr").nextAll().remove();
            for (var i = 0; i < data.data.list.length; i++) {
                $("#service_management_maintain_table").append($("<tr onclick='serviceManagementMaintainTRClick(this)' ondblclick='serviceManagementMaintainDBClick(this)'/>").html(
                    "服务id：" + data.data.list[i].id +"<br/>"
                    + "服务名称：" + data.data.list[i].name + "<br/>"
                    + "服务属性：" + parseAttribute(data.data.list[i].attributes) + "<br/>"
                    + "服务内容： " + simplifyServiceContent(data.data.list[i].content) + "<br/>"
                    + "服务所属节点： " + data.data.list[i].node + "<br/>"
                    + "服务所属集群：" + data.data.list[i].cluster + "<br/>"
                    + "服务创建时间：" + new Date(data.data.list[i].createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                    + "服务修改时间：" + new Date(data.data.list[i].modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                ).append($("<hr/>")).attr("service_id", data.data.list[i].id));
            }

            $("#service_management_maintain_pagination").html("");
            if (data.data.isFirstPage) {
                $("#service_management_maintain_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            } else {
                $("#service_management_maintain_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='serviceManagementMaintainRefresh(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for (var index = 0; index < data.data.navigatepageNums.length; index++) {
                if (data.data.navigatepageNums[index] != data.data.pageNum) {
                    $("#service_management_maintain_pagination").append($("<li/>").append($("<span onclick='serviceManagementMaintainRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                } else {
                    $("#service_management_maintain_pagination").append($("<li class='active'/>").append($("<span onclick='serviceManagementMaintainRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if (data.data.isLastPage) {
                $("#service_management_maintain_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            } else {
                $("#service_management_maintain_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='serviceManagementMaintainRefresh(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }
        }
    });
}

/**
 * 每行选中事件
 * @param obj
 */
function serviceManagementMaintainTRClick(obj) {
    var trs = $("#service_management_maintain_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 修改服务信息
 */
function serviceManagementMaintainModify() {
    var on_tr = $("#service_management_maintain_table").find("tr.tr-on");
    if(on_tr != null){
        $.ajax({
            type : "GET",
            url : "/api/service/modify",
            data : {"serviceID" : on_tr.attr("service_id"),"name" : $("#service_management_maintain_modify_name").val(),"attributes" : $("#service_management_maintain_modify_attributes").val(),"content" : $("#service_management_maintain_modify_content").val(),"node" : $("#service_management_maintain_modify_node").val()},
            dataType : "json",
            success : function (data) {
                alert(data.msg);
            }
        });
    }else{
        alert("请选中对应服务！")
    }
}

/**
 * 筛选按钮
 */
function serviceManagementMaintainFilter() {
    service_management_maintain_filter = $("#service_management_maintain_filter_input").val();
    $("#service_management_maintain_filter_label").html(service_management_view_filter);
    serviceManagementMaintainRefresh(1);
}

/**
 *
 */
function serviceManagementMantainRefreshButton() {
    service_management_maintain_filter = null;
    service_management_maintain_desc = "asc";
    service_management_maintain_orderBy = "id";

    serviceManagementMaintainRefresh(1);

    $("#service_management_maintain_filter_label").html("无");
}

/**
 * 数据导出
 */
function serviceManagementMaintainExport() {
    alert("该功能还未实现，您选中的导出数据的格式为：" + $("input[name='export_way']:checked").val());
}

/**
 * 数据删除
 */
function serviceManagementMaintainDelete() {
    var on_tr = $("#service_management_maintain_table").find("tr.tr-on");
    if(on_tr != null){
        $.ajax({
            type : "GET",
            url : "/api/service/delete",
            data : {"serviceID" : on_tr.attr("service_id")},
            dataType : "json",
            success : function (data) {
                alert(data.msg);
            }
        });
    }else{
        alert("请选中对应服务！")
    }
}

/**
 * 添加
 */
function serviceManagementMaintainAdd() {
    if($("#service_management_maintain_add_name").val() == null || $("#service_management_maintain_add_name").val().length==0 ){
        alert("请输入服务名称！");
    }else {
        $.ajax({
            type : "GET",
            url : "/api/service/add",
            data : {"name" : $("#service_management_maintain_add_name").val(),"attributes" : $("#service_management_maintain_add_attributes").val(),"content" : $("#service_management_maintain_add_content").val(),"node" : $("#service_management_maintain_add_node").val()},
            dataType : "json",
            success : function (data) {
                alert(data.msg);
            }
        });
    }
}
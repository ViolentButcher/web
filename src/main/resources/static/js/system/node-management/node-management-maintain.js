var node_management_maintain_node_filter;
var node_management_maintain_node_desc = "asc";
var node_management_maintain_node_orderBy = "id";

var node_management_maintain_current_node;
/**
 * 节点维护
 */
function nodeManagementMaintain() {
    $("#main_content").html('');
    $("#main_content").html('<h4>现有节点列表：</h4>' +
        '<div class="row div-row">' +
            '<table id="node_maintenance_table" class="table table-bordered table-hover table-responsive">' +
                '<tr>' +
                    '<th>ID</th>' +
                    '<th>名称</th>' +
                    '<th>属性</th>' +
                    '<th>服务个数</th>' +
                    '<th>坐标</th>' +
                    '<th>所属集群</th>' +
                    '<th>关联节点</th>' +
                    '<th>等级</th>' +
                    '<th>创建时间</th>' +
                    '<th>修改时间</th>' +
                '</tr>' +
            '</table>' +
        '</div>' +
        '<div class="row div-row">' +
            '<label for="node_maintenance_filter_label">当前筛选条件：</label>' +
            '<label id="node_maintenance_filter_label">无</label>' +
        '</div>' +
        '<div class="row div-row">' +
            '<div class="col-md-1 col-md-offset-1">' +
                '<button class="btn btn-default btn-sm" id="node_maintenance_refresh">刷新</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" id="node_maintenance_filter" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" id="node_maintenance_import" data-toggle="modal" data-target="#cluster_management_export_modal">导入</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" id="node_maintenance_add" data-toggle="modal" data-target="#cluster_management_add_modal">添加</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" id="node_maintenance_change" data-toggle="modal" data-target="#cluster_management_change_modal">修改</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" id="node_maintenance_delete" data-target="#cluster_management_delete_modal">删除</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" id="node_maintenance_detail" onclick="nodeMaintenanceInfo()">查看服务信息</button>' +
            '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='cluster_management_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_filter_label'/>").html("约束表达式：")).append($("<input id='cluster_management_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_add_modal' tabindex='-1' role='dialog' aria-labelledby='modal_add_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_add_title'/>").html("添加数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("字段1").append($("<input type='text'>"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_change_modal' tabindex='-1' role='dialog' aria-labelledby='modal_change_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_change_title'/>").html("修改数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("字段1").append($("<input type='text'>"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
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
}

/**
 * 查看节点信息
 */
function nodeManagementMaintainNodeDetailRequest(cluster_id,pageNum) {
    $.ajax({
        type : "GET",
        data : {"pageNum":pageNum,"filter" : cluster_management_view_node_filter,"desc" : cluster_management_view_node_desc,"orderBy" : cluster_management_view_node_orderBy},
        url  : "/api/node/node_list",
        dataType : "json",
        success : function (data) {
            $("#cluster_management_cluster_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#cluster_management_cluster_table").append($("<tr onclick='clusterManagementViewClusterTRClick(this)'/>")
                    .append($("<td/>").html(data.data.list[i].id))
                    .append($("<td/>").html(data.data.list[i].name))
                    .append($("<td/>").html(data.data.list[i].attrs))
                    .append($("<td/>").html(data.data.list[i].serviceNumber))
                    .append($("<td/>").html(data.data.list[i].position))
                    .append($("<td/>").html(data.data.list[i].associatedNode))
                    .append($("<td/>").html(data.data.list[i].level))
                    .append($("<td/>").html(data.data.list[i].createTime))
                    .append($("<td/>").html(data.data.list[i].modifyTime)));
            }
            $("#cluster_management_view_node_pagination").html("");
            if(data.data.isFirstPage){
                $("#cluster_management_view_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#cluster_management_view_node_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='clusterManagementViewClusterDetailRequest(" + cluster_management_view_current_cluster + "," + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#cluster_management_view_node_pagination").append($("<li/>").append($("<span onclick='clusterManagementViewClusterDetailRequest(" + cluster_management_view_current_cluster + "," + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#cluster_management_view_node_pagination").append($("<li class='active'/>").append($("<span onclick='clusterManagementViewClusterDetailRequest(" + cluster_management_view_current_cluster + "," + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#cluster_management_view_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#cluster_management_view_node_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='clusterManagementViewClusterDetailRequest(" + cluster_management_view_current_cluster + "," + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }

        }
    });
}

/**
 * 每行选中事件
 * @param obj
 */
function nodeManagementMaintainNodeTRClick(obj) {
    var trs = $("#node_management_view_node_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 查看节点详情
 */
function nodeManagementMaintainNodeDetail() {
    var on_tr = $("#node_management_view_node_table").find("tr.tr-on");
    if(on_tr != null){
        node_management_view_current_node = on_tr.attr("node_id");
        nodeManagementViewNodeDetailLoad();
    }else{
        alert("请选中对应节点！")
    }
}

function nodeManagementViewNodeDetailLoad() {
    $("#main_content").html('');
    $("#main_content").html('<h3>现有服务列表</h3> ' +
        '<div class="row div-row">' +
        '<table id="node_management_view_service_table" class="table table-bordered table-hover table-responsive">' +
        '<tr>' +
        '<th>ID</th>' +
        '<th>名称</th>' +
        '<th>属性</th>' +
        '<th>所属集群</th>' +
        '<th>所属节点</th>' +
        '<th>内容</th>' +
        '<th>创建时间</th>' +
        '<th>修改时间</th>' +
        '</tr>' +
        '</table>' +
        '</div>' +
        '<div class="row">' +
        '<label>当前筛选条件：</label>' +
        '<label id="node_management_view_service_filter_label">无</label>' +
        '</div>' +
        '<div class="row text-center div-row">' +
        '<nav aria-label="Page navigation">' +
        '<ul id="node_management_view_service_pagination" class="pagination"></ul>' +
        '</nav>' +
        '</div>' +
        '<div class="row div-row">' +
        '<div class="col-md-2 col-md-offset-2">' +
        '<button class="btn btn-default btn-sm" onclick="nodeManagementViewServiceRefresh(1)">刷新</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#service_management_view_filter_modal">筛选</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#service_management_view_export_modal">导出</button>' +
        '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='service_management_view_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='service_management_view_filter_input'/>").html("约束表达式：")).append($("<input id='service_management_view_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='nodeManagementViewServiceFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='service_management_view_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='nodeManagementViewServiceExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));

    nodeManagementViewServiceRefresh(1);
}

/**
 * 服务节点的刷新
 */
function nodeManagementViewServiceRefresh(pageNum) {
    $.ajax({
        type : "GET",
        url  : "/api/service/service_list",
        data : {"nodeID":node_management_view_current_node,"pageNum" : pageNum,"filter" : service_management_view_filter,"desc" : service_management_view_desc,"orderBy" : service_management_view_orderBy},
        dataType : "json",
        success : function (data) {
            $("#node_management_view_service_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#node_management_view_service_table").append($("<tr onclick='nodeManagementServiceViewTRClick(this)'/>")
                    .append($("<td/>").html(data.data.list[i].id))
                    .append($("<td/>").html(data.data.list[i].name))
                    .append($("<td/>").html(data.data.list[i].attributes))
                    .append($("<td/>").html(data.data.list[i].content))
                    .append($("<td/>").html(data.data.list[i].createTime))
                    .append($("<td/>").html(data.data.list[i].modifyTime)).attr("service_id",data.data.list[i].id));
            }

            $("#node_management_view_service_pagination").html("");
            if(data.data.isFirstPage){
                $("#node_management_view_service_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#node_management_view_service_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='nodeManagementViewServiceRefresh(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#node_management_view_service_pagination").append($("<li/>").append($("<span onclick='nodeManagementViewServiceRefresh("  + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#node_management_view_service_pagination").append($("<li class='active'/>").append($("<span onclick='nodeManagementViewServiceRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#node_management_view_service_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#node_management_view_service_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='nodeManagementViewServiceRefresh(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }
        }
    });
}

/**
 * 每行选中事件
 * @param obj
 */
function nodeManagementServiceViewTRClick(obj) {
    var trs = $("#node_management_view_service_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 筛选按钮
 */
function nodeManagementMaintainServiceFilter() {

}

/**
 * 服务导出按钮
 */
function nodeManagementMaintainServiceExport() {

}
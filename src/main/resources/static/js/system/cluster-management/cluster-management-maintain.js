var cluster_management_maintain_cluster_filter;
var cluster_management_maintain_cluster_desc = "asc";
var cluster_management_maintain_cluster_orderBy = "id";

var cluster_management_maintain_configure_cluster;

var cluster_management_maintain_node_filter;
var cluster_management_maintain_node_desc = "asc";
var cluster_management_maintain_node_orderBy = "id";
var cluster_management_maintain_node_configure_node;

/**
 * 加载集群维护页面
 */
function clusterManagementMaintain() {
    $("#main_content").html('');
    $("#location").html('');
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementMaintain()'/>").html(">> 集群维护"));
    $("#main_content").append($("<div class='row div-row'/>").append($("<table id='cluster_management_maintain_cluster_table' class='table table-bordered table-responsive'/>")
        .append($("<tr/>").append($("<th/>").html("ID")).append($("<th/>").html("名称")).append($("<th/>").html("属性")).append($("<th/>").html("节点个数")).append($("<th/>").html("节点关系配置")).append($("<th/>").html("创建时间")).append($("<th/>").html("修改时间")).append($("<th/>").html("节点加载状态")))));

    $("#main_content").append($("<div class='row div-row'/>")
        .html('<label for="cluster_management_maintain_cluster_filter_label">当前筛选条件：</label>' +
            '<label id="cluster_management_maintain_cluster_filter_label">无</label>'));

    $("#main_content").append($("<div class='div-row row text-center'/>")
        .append($("<nav aria-label='Page navigation'/>")
            .append($("<ul id='cluster_management_maintain_cluster_pagination' class='pagination'/>"))));

    var cluster_button_row = $("<div class='row div-row'/>");
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_maintain_refresh' onclick='clusterManagementMaintainClusterRefresh()'/>").html("刷新")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_maintain_filter' data-toggle='modal' data-target='#cluster_management_maintain_filter_modal'/>").html("筛选")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_maintain_add' data-toggle='modal' data-target='#cluster_management_maintain_add_modal'/>").html("添加")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_maintain_modify' data-toggle='modal' data-target='#cluster_management_maintain_modify_modal'/>").html("修改")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' onclick='clusterManagementMaintainDel()'/>").html("删除")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' onclick='clusterManagementMaintainConfigure()'/>").html("配置节点关系")));
    $("#main_content").append(cluster_button_row);
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_filter_label'/>").html("约束表达式：")).append($("<input id='cluster_management_maintain_cluster_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementMaintainClusterFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_add_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("添加集群")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("集群名称：").append("<input type='text' id='cluster_maintain_cluster_add_name'>"))
                    .append($("<div class='row'/>").append("<label/>").html("集群结构：").append("<input type='text' id='cluster_maintain_cluster_add_attributes'>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementMaintainClusterAdd()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_modify_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_filter_label'/>").html("约束表达式：")).append($("<input id='cluster_management_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_configure_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_filter_label'/>").html("约束表达式：")).append($("<input id='cluster_management_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    clusterManagementMaintainClusterRefresh(1);
}

/**
 * 集群列表刷新
 */
function clusterManagementMaintainClusterRefresh(pageNum) {
    $.ajax({
        type : "GET",
        url  : "/api/cluster/cluster_list",
        data : {"pageNum" : pageNum,"filter" : cluster_management_maintain_cluster_filter,"desc" : cluster_management_maintain_cluster_desc,"orderBy" : cluster_management_maintain_cluster_orderBy},
        dataType : "json",
        success : function (data) {
            $("#cluster_management_maintain_cluster_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                var state = "";
                if(data.data.list[i].state == 1){
                    state = "已加载";
                }else if (data.data.list[i].state == 0) {
                    state = "未加载";
                }
                $("#cluster_management_maintain_cluster_table").append($("<tr onclick='clusterManagementMaintainClusterTRClick(this)'/>")
                    .append($("<td/>").html(data.data.list[i].id))
                    .append($("<td/>").html(data.data.list[i].name))
                    .append($("<td/>").html(data.data.list[i].attribute))
                    .append($("<td/>").html(data.data.list[i].nodeNumber))
                    .append($("<td/>").html(data.data.list[i].configuration))
                    .append($("<td/>").html(data.data.list[i].createTime))
                    .append($("<td/>").html(data.data.list[i].modifyTime))
                    .append($("<td/>").html(data.data.list[i].state)).attr("cluster_id",data.data.list[i].id));
            }

            $("#cluster_management_maintain_cluster_pagination").html("");
            if(data.data.isFirstPage){
                $("#cluster_management_maintain_cluster_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#cluster_management_maintain_cluster_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='clusterManagementViewClusterDetailRequest(" + cluster_management_view_current_cluster + "," + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#cluster_management_maintain_cluster_pagination").append($("<li/>").append($("<span onclick='clusterManagementViewClusterDetailRequest(" + cluster_management_view_current_cluster + "," + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#cluster_management_maintain_cluster_pagination").append($("<li class='active'/>").append($("<span onclick='clusterManagementViewClusterDetailRequest(" + cluster_management_view_current_cluster + "," + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#cluster_management_maintain_cluster_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#cluster_management_maintain_cluster_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='clusterManagementViewClusterDetailRequest(" + cluster_management_view_current_cluster + "," + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }
        }
    });
}

/**
 * tr的选中
 * @param obj
 */
function clusterManagementMaintainClusterTRClick(obj) {
    var trs = $("#cluster_management_maintain_cluster_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 集群的删除
 */
function clusterManagementMaintainDel() {
    var on_tr = $("#cluster_management_maintain_cluster_table").find("tr.tr-on");
    if(on_tr != null){
        clusterManagementMaintainDelRequest(on_tr.attr("cluster_id"));
    }else{
        alert("请选中对应集群！")
    }
}

/**
 * 集群删除的请求
 * @param clusterID
 */
function clusterManagementMaintainDelRequest(clusterID) {
    $.ajax({
        type : "GET",
        url : "/api/cluster/delete",
        data : {"clusterID" : clusterID},
        dataType : "json",
        success : function (data) {
            alert(data.msg);
        }
    });
}

/**
 * 集群筛选
 */
function clusterManagementMaintainClusterFilter() {
    cluster_management_maintain_cluster_filter = $("#cluster_management_maintain_cluster_filter_input").val();
    $("#cluster_management_maintain_cluster_filter_label").html(cluster_management_maintain_cluster_filter);
    clusterManagementMaintainClusterRefresh(1);
}

/**
 * 添加集群
 */
function clusterManagementMaintainClusterAdd() {
    $.ajax({
        type : "GET",
        url : "/api/cluster/add",
        data : {"name":$("#cluster_maintain_cluster_add_name").val(),"attributes" : $("#cluster_maintain_cluster_add_name").val()},
        dataType : "json",
        success : function (data) {
            alert(data.msg);
        }
    });
    clusterManagementMaintainClusterRefresh(1);
}

/**
 * 配置节点关系
 */
function clusterManagementMaintainConfigure() {
    var on_tr = $("#cluster_management_maintain_cluster_table").find("tr.tr-on");
    if(on_tr != null){
        cluster_management_maintain_configure_cluster = on_tr.attr("cluster_id");
        clusterManagementMaintainConfigureLoad();
    }else{
        alert("请选中对应集群！")
    }
}

/**
 * 节点配置界面加载
 */
function clusterManagementMaintainConfigureLoad(){
    $("#main_content").html('');
    $("#location").html('');
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementMaintain()'/>").html(">> 集群维护")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementMaintainConfigureLoad()'/>").html(">> 节点配置")).append($("<br/>"))
        .append($("<div class='div-row row'/>").append($("<label/>").html("当前集群名称：")).append($("<label id='cluster_management_maintain_node_cluster_name'/>").html("无")))
        .append($("<div class='div-row row'/>").append($("<label/>").html("当前集群属性：")).append($("<label id='cluster_management_maintain_node_cluster_attr'/>").html("无")))
        .append($("<div class='div-row row'/>").append($("<label/>").html("当前集群配置规则：")).append($("<label id='cluster_management_maintain_node_cluster_rule'/>").html("无")));

    $("#main_content").append($("<div class='row div-row'/>").append($("<table id='cluster_management_maintain_configuration_node_table' class='table table-bordered table-responsive'/>")
        .append($("<tr/>").append($("<th/>").html("ID")).append($("<th/>").html("名称")).append($("<th/>").html("属性")).append($("<th/>").html("服务个数")).append($("<th/>").html("坐标")).append($("<th/>").html("关联节点")).append($("<th/>").html("等级")).append($("<th/>").html("创建时间")).append($("<th/>").html("修改时间")))));

    $("#main_content").append($("<div class='row div-row'/>").append($("<label/>").html("当前筛选条件：")).append("<label id='cluster_management_maintain_node_filter_show'>"));

    $("#main_content").append($("<div class='div-row row text-center'/>")
        .append($("<nav aria-label='Page navigation'/>")
            .append($("<ul id='cluster_management_maintain_node_pagination' class='pagination'/>"))));

    var cluster_button_row = $("<div class='row div-row'/>");
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' onclick='clusterManagementMaintainNodeRefresh(1)'/>").html("刷新")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' data-toggle='modal' data-target='#cluster_management_maintain_filter_modal'/>").html("筛选")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' onclick='clusterManagementMaintainNodeDetail()'/>").html("查看服务信息")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' data-toggle='modal' data-target='#cluster_management_maintain_configure_manual_modal'/>").html("手动配置关联节点")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' data-toggle='modal' data-target='#cluster_management_maintain_configure_auto_modal'/>").html("自动配置关联节点")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' onclick='clusterManagementMaintain()'/>").html("返回")));
    $("#main_content").append(cluster_button_row);

    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_filter_modal' tabindex='-1' role='dialog' aria-labelledby='cluster_management_maintain_node_modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='cluster_management_maintain_node_modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_maintain_node_filter_input'/>").html("约束表达式：")).append($("<input id='cluster_management_maintain_node_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementMaintainNodeFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_configure_manual_modal' tabindex='-1' role='dialog' aria-labelledby='cluster_management_maintain_configure_manual_modal_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='cluster_management_maintain_configure_manual_modal_title'/>").html("手动配置关联节点" + cluster_management_maintain_node_configure_node)))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("节点等级：").append("<input type='text' id='cluster_management_maintain_configure_manual_level_input'>"))
                    .append($("<div class='row'/>").append("<label/>").html("节点关联类型：").append("<input type='text' id='cluster_management_maintain_configure_manual_associate_type_input'>"))
                    .append($("<div class='row'/>").append("<label/>").html("关联节点(节点id，以\",\"分割)：").append("<input type='textarea' id='cluster_management_maintain_configure_manual_associated_input'>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementMaintainNodeManualConfigureAssociatedNode()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_configure_auto_modal' tabindex='-1' role='dialog' aria-labelledby='cluster_management_maintain_configure_auto_modal_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='cluster_management_maintain_configure_auto_modal_title'/>").html("自动配置关联节点")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_filter_label'/>").html("约束表达式：")).append($("<input id='cluster_management_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementMaintainNodeAutoConfigureAssociatedNode()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    clusterManagementMaintainNodeRefresh(1);
}

/**
 * 节点刷新
 * @param pageNum
 */
function clusterManagementMaintainNodeRefresh(pageNum) {
    $.ajax({
        type : "GET",
        data : {"clusterID" : cluster_management_maintain_configure_cluster,"pageNum":pageNum,"filter" : cluster_management_maintain_node_filter,"desc" : cluster_management_maintain_node_desc,"orderBy" : cluster_management_maintain_node_orderBy},
        url  : "/api/node/node_list",
        dataType : "json",
        success : function (data) {
            $("#cluster_management_maintain_configuration_node_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#cluster_management_maintain_configuration_node_table").append($("<tr onclick='clusterManagementMaintainNodeTRClick(this)'/>")
                    .append($("<td/>").html(data.data.list[i].id))
                    .append($("<td/>").html(data.data.list[i].name))
                    .append($("<td/>").html(data.data.list[i].attrs))
                    .append($("<td/>").html(data.data.list[i].serviceNumber))
                    .append($("<td/>").html(data.data.list[i].position))
                    .append($("<td/>").html(data.data.list[i].associatedNodes))
                    .append($("<td/>").html(data.data.list[i].level))
                    .append($("<td/>").html(data.data.list[i].createTime))
                    .append($("<td/>").html(data.data.list[i].modifyTime)).attr("node_id",data.data.list[i].id));
            }
            $("#cluster_management_maintain_node_pagination").html("");
            if(data.data.isFirstPage){
                $("#cluster_management_maintain_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#cluster_management_maintain_node_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='clusterManagementMaintainNodeRefresh(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#cluster_management_maintain_node_pagination").append($("<li/>").append($("<span onclick='clusterManagementMaintainNodeRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#cluster_management_maintain_node_pagination").append($("<li class='active'/>").append($("<span onclick='clusterManagementMaintainNodeRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#cluster_management_maintain_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#cluster_management_maintain_node_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='clusterManagementMaintainNodeRefresh(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }

        }
    });
}


/**
 * 每行选中事件
 * @param obj
 */
function clusterManagementMaintainNodeTRClick(obj) {
    var trs = $("#cluster_management_maintain_configuration_node_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
    cluster_management_maintain_node_configure_node = $(obj).attr("node_id");
}

/**
 * 查看服务详细信息
 */
function clusterManagementMaintainNodeDetail() {
    var on_tr = $("#cluster_management_maintain_configuration_node_table").find("tr.tr-on");
    if(on_tr != null){

    }else{
        alert("请选中对应节点！")
    }
}

/**
 *节点的筛选
 */
function clusterManagementMaintainNodeFilter() {
    cluster_management_maintain_node_filter = $("#cluster_management_maintain_node_filter_input").val();
    $("#cluster_management_maintain_node_filter_show").html(cluster_management_maintain_node_filter);
    clusterManagementMaintainNodeRefresh(1);
}

/**
 * 手动配置节点关联关系
 */
function clusterManagementMaintainNodeManualConfigureAssociatedNode() {
    if(cluster_management_maintain_node_configure_node == null){
        alert("请选中对应节点！！！");
    }else {
        $.ajax({
            type : "GET",
            url : "/api/node/configure/manual",
            data : {"nodeID":cluster_management_maintain_node_configure_node,"level":$("#cluster_management_maintain_configure_manual_level_input").val(),"associatedNode":$("#cluster_management_maintain_configure_manual_associated_input").val(),"associatedType" : $("#cluster_management_maintain_configure_manual_associate_type_input").val()},
            dataType : "json",
            success : function (data) {
                alert(data.msg);
            }
        });
    }
}

/**
 * 自动配置节点关联关系
 */
function clusterManagementMaintainNodeAutoConfigureAssociatedNode() {

}

function clusterManagementMaintainNodeDetailLoad() {
    $("#main_content").html('');
    $("#main_content").html('<h3>现有服务列表</h3> ' +
        '<div class="row div-row">' +
            '<table id="cluster_management_maintain_service_table" class="table table-bordered table-hover table-responsive">' +
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
            '<label id="cluster_management_maintain_service_filter_label">无</label>' +
        '</div>' +
        '<div class="row text-center div-row">' +
            '<nav aria-label="Page navigation">' +
                '<ul id="cluster_management_maintain_service_pagination" class="pagination"></ul>' +
            '</nav>' +
        '</div>' +
        '<div class="row div-row">' +
            '<div class="col-md-2 col-md-offset-2">' +
                '<button class="btn btn-default btn-sm" onclick="clusterManagementMaintainServiceRefresh(1)">刷新</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_maintain_service_filter_modal">筛选</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_maintain_service_export_modal">导出</button>' +
            '</div>' +
        '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_service_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='service_management_view_filter_input'/>").html("约束表达式：")).append($("<input id='service_management_view_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementMaintainServiceFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_service_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementMaintainServiceExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
}

/**
 *
 * @param pageNum
 */
function clusterManagementMaintainServiceRefresh(pageNum) {
    $.ajax({
        type : "GET",
        url  : "/api/service/service_list_all",
        data : {"pageNum" : pageNum,"filter" : service_management_view_filter,"desc" : service_management_view_desc,"orderBy" : service_management_view_orderBy},
        dataType : "json",
        success : function (data) {
            $("#service_management_view_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#service_management_view_table").append($("<tr onclick='serviceManagementViewTRClick(this)'/>")
                    .append($("<td/>").html(data.data.list[i].id))
                    .append($("<td/>").html(data.data.list[i].name))
                    .append($("<td/>").html(data.data.list[i].attributes))
                    .append($("<td/>").html(data.data.list[i].cluster))
                    .append($("<td/>").html(data.data.list[i].node))
                    .append($("<td/>").html(data.data.list[i].content))
                    .append($("<td/>").html(data.data.list[i].createTime))
                    .append($("<td/>").html(data.data.list[i].modifyTime)).attr("service_id",data.data.list[i].id));
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
 * 服务过滤
 */
function clusterManagementMaintainServiceFilter() {
    
}

/**
 * 服务导出
 */
function clusterManagementMaintainServiceExport() {
    
}
var cluster_management_maintain_cluster_filter;
var cluster_management_maintain_cluster_desc;
var cluster_management_maintain_cluster_orderBy;

var cluster_management_maintain_configure_cluster;
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
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementMaintaince()'/>").html(">> 集群维护"));
    $("#main_content").append($("<div class='row div-row'/>").append($("<table id='cluster_table' class='table table-bordered table-responsive'/>")
        .append($("<tr/>").append($("<th/>").html("ID")).append($("<th/>").html("名称")).append($("<th/>").html("属性")).append($("<th/>").html("节点个数")).append($("<th/>").html("节点关系配置")).append($("<th/>").html("创建时间")).append($("<th/>").html("修改时间")).append($("<th/>").html("节点加载状态")))));
    var cluster_button_row = $("<div class='row div-row'/>");
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_maintain_refresh' onclick='clusterManagementRefresh()'/>").html("刷新")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_maintain_filter' data-toggle='modal' data-target='#cluster_management_maintain_filter_modal'/>").html("筛选")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_maintain_add' data-toggle='modal' data-target='#cluster_management_maintain_add_modal'/>").html("添加")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_maintain_modify' data-toggle='modal' data-target='#cluster_management_maintain_modify_modal'/>").html("修改")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' onclick='clusterManagementMaintainDel()'/>").html("删除")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_maintain_configure' data-toggle='modal' data-target='#cluster_management_maintain_configure_modal'/>").html("配置节点关系")));
    $("#main_content").append(cluster_button_row);
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_filter_label'/>").html("约束表达式：")).append($("<input id='cluster_management_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_maintain_add_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("添加集群")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("集群名称：").append("<input type='text' id='add_cluster_name'>"))
                    .append($("<div class='row'/>").append("<label/>").html("集群结构：").append($("<label/>").append($("<input type='radio' name='cluster_structure_type' value=1 checked/>")).append("集中式集群")).append($("<label/>").append($("<input type='radio' value=2 name='cluster_structure_type' >")).append("层次式集群")).append($("<label/>").append($("<input type='radio' value=3 name='cluster_structure_type' >")).append("无中心集群"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementMaintainAdd()' data-dismiss='modal'/>").html("确定"))
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
    clusterManagementMaintainRefresh();
}


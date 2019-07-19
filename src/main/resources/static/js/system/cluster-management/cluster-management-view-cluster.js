var cluster_management_view_cluster_list_size;
var cluster_management_view_node_list_size;

/**
 * 加载集群管理页面
 */
function clusterManagementViewCluster() {
    //清空页面内容
    $("#main_content").html('');
    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementViewCluster()'/>").html(">> 集群管理"));

    //集群表格
    $("#main_content").append($("<div class='row div-row'/>").append($("<table id='cluster_table' class='table table-bordered table-responsive'/>")
        .append($("<tr/>").append($("<th/>").html("ID")).append($("<th/>").html("名称")).append($("<th/>").html("属性")).append($("<th/>").html("节点个数")).append($("<th/>").html("节点关系配置")).append($("<th/>").html("创建时间")).append($("<th/>").html("修改时间")).append($("<th/>").html("节点加载状态")))));

    //分页
    $("#main_content").append($("<div class='row div-row text-center'/>").append($("<div id='cluster_management_pagination' class='pagination'/>")));

    //按钮
    var cluster_button_row = $("<div class='row div-row'/>");
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_refresh' onclick='clusterManagementViewClusterRefresh()'/>").html("刷新")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_filter' data-toggle='modal' data-target='#cluster_management_filter_modal'/>").html("筛选")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_export' data-toggle='modal' data-target='#cluster_management_export_modal'/>").html("导出")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_detail' onclick='clusterManagementViewClusterDetail()'/>").html("查看节点信息")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_load' onclick='clusterManagementLoad()'/>").html("加载/卸载节点")));
    $("#main_content").append(cluster_button_row);

    //筛选部分的拟态框
    $("#main_content").append($("<div class='modal fade' id='cluster_management_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_filter_label'/>").html("约束表达式：")).append($("<input id='cluster_management_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));

    //导出数据框
    $("#main_content").append($("<div class='modal fade' id='cluster_management_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));

    //获取列表信息
    clusterManagementViewClusterRefresh();

    //设置分页
    $("#cluster_management_pagination").pagination(cluster_management_view_cluster_list_size, {
        num_edge_entries: 1, //边缘页数
        num_display_entries: 4, //主体页数
        callback: clusterManagementViewClusterPagination,
        items_per_page: 10, //每页显示个数
        prev_text: "前一页",
        next_text: "后一页"
    });
}

/**
 * 集群列表的刷新
 */
function clusterManagementViewClusterRefresh(pageIndex) {
    $.ajax({
        type : "GET",
        url  : "/api/cluster/cluster_list",
        data : {"pageNum" : pageIndex},
        dataType : "json",
        success : function (data) {
            cluster_management_view_cluster_list_size = data.data.size;
            $("#cluster_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                var state = "";
                if(data.data.list[i].state == 1){
                    state = "已加载";
                }else if (data.data.list[i].state == 0) {
                    state = "未加载";
                }
                $("#cluster_table").append($("<tr onclick='clusterManagementViewClusterTRClick(this)'/>")
                    .append($("<td/>").html(data.data.list[i].id))
                    .append($("<td/>").html(data.data.list[i].name))
                    .append($("<td/>").html(data.data.list[i].attribute))
                    .append($("<td/>").html(data.data.list[i].nodeNumber))
                    .append($("<td/>").html(data.data.list[i].configuration))
                    .append($("<td/>").html(data.data.list[i].createTime))
                    .append($("<td/>").html(data.data.list[i].modifyTime))
                    .append($("<td/>").html(data.data.list[i].state)).attr("cluster_id",data.data.list[i].id));
            }
        }
    })
}

/**
 * 集群列表的分页回调函数
 * @param page
 * @param jq
 */
function clusterManagementViewClusterPagination(page,jq) {
    clusterManagementViewClusterRefresh(page);
}


/**
 * 集群每行单击函数，选中框变色
 * @param obj
 */
function clusterManagementViewClusterTRClick(obj) {
    var trs = $("#cluster_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 查看集群详细信息
 */
function clusterManagementViewClusterDetail() {
    var on_tr = $("#cluster_table").find("tr.tr-on");
    if(on_tr != null){
        clusterManagementViewClusterDetailLoad(on_tr.attr("cluster_id"));
    }else{
        alert("请选中对应集群！")
    }
}

/**
 * 根据id加载集群列表
 * @param cluster_id
 */
function clusterManagementViewClusterDetailLoad(cluster_id) {
    cluster_management_current_node = cluster_id;
    $("#main_content").html('');
    $("#main_content").html('<div class="row div-row">' +
        '集群<span id="cluster_management_cluster_span">"集群名"</span>节点列表：' +
        '</div>' +
        '<div class="row div-row">' +
        '<table id="cluster_management_cluster_table" class="table table-bordered table-hover table-responsive">' +
        '<tr>' +
        '<th>ID</th>' +
        '<th>名称</th>' +
        '<th>服务个数</th>' +
        '<th>坐标</th>' +
        '<th>关联节点</th>' +
        '<th>等级</th>' +
        '<th>创建时间</th>' +
        '<th>修改时间</th>' +
        '</tr>' +
        '</table>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label for="cluster_management_cluster_filter_label">当前筛选条件：</label>' +
        '<label id="cluster_management_cluster_filter_label">无</label>' +
        '</div>' +
        '<div class="row"><div id="cluster_management_cluster_node_pagination" class="pagination"></div> </div> ' +
        '<div class="row div-row">' +
        '<div class="col-md-2 col-md-offset-1">' +
        '<button class="btn btn-default btn-sm" id="cluster_management_cluster_refresh">刷新</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="cluster_management_cluster_filter" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="cluster_management_cluster_export"  data-toggle="modal" data-target="#cluster_management_export_modal">导出</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="cluster_management_cluster_detail" onclick="clusterManagementClusterService()">查看服务信息</button>' +
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
    clusterManagementViewClusterDetailRequest(cluster_id);
    $("#cluster_management_cluster_node_pagination").pagination(cluster_management_view_node_list_size, {
        num_edge_entries: 1, //边缘页数
        num_display_entries: 4, //主体页数
        callback: clusterManagementNodePagintion,
        items_per_page: 10, //每页显示个数
        prev_text: "前一页",
        next_text: "后一页"
    });

}

/**
 * 查看节点信息
 */
function clusterManagementViewClusterDetailRequest(cluster_id,pageNum) {
    $.ajax({
        type : "GET",
        data : {"clusterID" : cluster_id, "pageNum":pageNum},
        url  : "/api/cluster/node_list",
        dataType : "json",
        success : function (data) {
            $("#cluster_management_cluster_table tr").nextAll().remove();
            cluster_management_view_node_list_size = data.data.size;
            for(var i=0;i<data.data.list.length;i++){
                $("#cluster_management_cluster_table").append($("<tr/>")
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
        }
    });
}
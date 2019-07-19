
var cluster_management_node_list_size;
var cluster_management_current_node;



function clusterManagementNodePagintion(page,jq){
    clusterManagementDetailRequest(cluster_management_current_node,page)
}





/**
 * 加载服务信息
 */
function clusterManagementClusterService() {
    $("#main_content").html('');
    $("#main_content").html('<h3>现有服务列表</h3> ' +
        '<table class="table table-bordered table-hover table-responsive">' +
        ' <tr>' +
        '<th>ID</th>' +
        '<th>名称</th>' +
        '<th>属性</th>' +
        '<th>内容</th>' +
        '<th>创建时间</th>' +
        '<th>修改时间</th>' +
        '</tr>' +
        '</table>' +
        '<div class="row div-row">' +
        '<label>当前筛选条件：</label>' +
        '<label id="service_view_filter_label">无</label>' +
        '</div>' +
        '<div class="row div-row text-center"><div id="cluster_management_cluster_service"></div> </div> ' +
        '<div class="row div-row">' +
        '<div class="col-md-2 col-md-offset-2">' +
        '<button class="btn btn-default btn-sm" id="service_view_refresh">刷新</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="service_view_filter" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="service_view_export" data-toggle="modal" data-target="#cluster_management_export_modal">导出</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="service_view_back" onclick="clusterManagementDetail()">返回</button>' +
        '</div>' +
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
}

/**
 * 集群管理-集群浏览的筛选
 */
function clusterManagementFilter() {

}

/**
 * 集群管理-集群浏览的导出
 */

function clusterManagementExport() {

}

/**
 * 加载对应集群
 */
function clusterManagementLoad(){
    var cluster_id = $("#cluster_table").find("tr.tr-on").attr("cluster_id");
    if(cluster_id != null){
        $.ajax({
            type : "GET",
            data : {"id" : cluster_id},
            url  : "/api/cluster/start_cluster",
            dataType : "json",
            success : function (data) {
            }
        })
    }else{
        alert("请选中对应集群！")
    }
}



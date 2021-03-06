var cluster_management_view_cluster_filter;
var cluster_management_view_cluster_desc = "asc";
var cluster_management_view_cluster_orderBy = "id";

var cluster_management_view_node_filter;
var cluster_management_view_node_desc = "asc";
var cluster_management_view_node_orderBy = "id";

var cluster_management_view_current_cluster;

var cluster_management_view_service_filter;
var cluster_management_view_service_desc = "asc";
var cluster_management_view_service_orderBy = "id";

var cluster_management_view_current_service;

/**
 * 加载集群管理页面
 */
function clusterManagementViewCluster() {

    cluster_management_view_cluster_filter = null;
    cluster_management_view_cluster_desc = "asc";
    cluster_management_view_cluster_orderBy = "id";

    //清空页面内容
    $("#main_content").html('');
    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementViewCluster()'/>").html(">> 集群管理"));

    //集群表格
    $("#main_content").append($("<div/>").html(
        '<label>排序关键字：<select id="cluster_management_view_cluster_sort_keyword"><option value="id">id</option><option value="name">name</option><option value="attribute">attribute</option><option value="configuration">configuration</option><option value="type">type</option><option value="state">state</option><option value="node_number">node_number</option><option value="create_time">create_time</option><option value="modify_time">modify_time</option></select></label>' +
        '<label>升降序<input type="checkbox" id="cluster_management_view_cluster_sort_asc"></label>' +
        '<button onclick="clusterManagementViewClusterSort()">排序</button>'
    )).append($("<div class='row div-row'/>").append($("<table id='cluster_table' class='table table-bordered table-responsive'/>").append($("<tr/>"))));

    $("#main_content").append($("<div class='row div-row'/>").html('<label>当前筛选条件：</label>' +
        '<label id="cluster_management_view_cluster_filter_label">无</label>' ));

    //分页
    $("#main_content").append($("<div class='row text-center div-row'/>").html('<nav aria-label="Page navigation">' +
            '<ul id="cluster_management_view_cluster_pagination" class="pagination"></ul>' +
        '</nav>'));

    //按钮
    var cluster_button_row = $("<div class='row div-row'/>");
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_refresh' onclick='clusterManagementViewClusterRefreshButton()'/>").html("刷新")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_filter' data-toggle='modal' data-target='#cluster_management_filter_modal'/>").html("筛选")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_export' data-toggle='modal' data-target='#cluster_management_export_modal'/>").html("导出")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_detail' onclick='clusterManagementViewClusterDetail()'/>").html("查看节点信息")));
    cluster_button_row.append($("<div class='col-md-2'/>").append($("<button class='btn btn-default btn-sm' id='cluster_management_load' onclick='clusterManagementViewLoad()'/>").html("加载/卸载节点")));
    $("#main_content").append(cluster_button_row);

    //筛选部分的拟态框
    $("#main_content").append($("<div class='modal fade' id='cluster_management_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_filter_label'/>").html("约束表达式：")).append($("<input id='cluster_management_view_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementViewFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));

    //导出数据框
    $("#main_content").append($("<div class='modal fade' id='cluster_management_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementViewExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));

    $("#main_content").append($("<div class='modal fade' id='cluster_management_view_cluster_detail_modal' tabindex='-1' role='dialog' aria-labelledby='modal_detail_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_detail_title'/>").html("详细信息")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row' id='cluster_management_view_cluster_detail_modal_content'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));

    //获取列表信息
    clusterManagementViewClusterRefresh(1);
}

/**
 * 排序
 * @param obj
 */
function clusterManagementViewClusterOrder(obj) {
    cluster_management_view_cluster_orderBy = $(obj).attr("order");
    if(cluster_management_view_cluster_desc == "asc"){
        cluster_management_view_cluster_desc = "desc";
    }else {
        cluster_management_view_cluster_desc = "asc";
    }
    clusterManagementViewClusterRefresh(1);
}

function clusterManagementViewClusterDBClick(obj) {
    $.ajax({
        type : "GET",
        data : {"clusterID" : $(obj).attr("cluster_id")},
        url  : "/api/cluster/cluster_info",
        dataType : "json",
        success : function (data) {
            $("#cluster_management_view_cluster_detail_modal_content").html(
                "集群id：" + data.data.id +"<br/>"
                + "节点名称：" + data.data.name + "<br/>"
                + "节点属性：" + parseAttribute(data.data.attribute) + "<br/>"
                + "集群配置：" +data.data.configuration + "<br/>"
                + "集群状态：" + data.data.state + "<br/>"
                + "节点数量：" + data.data.nodeNumber + "<br/>"
                + "集群创建时间：" + new Date(data.data.createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                + "集群修改时间：" + new Date(data.data.modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
            );
            $("#cluster_management_view_cluster_detail_modal").modal();
        }
    });
}

function clusterManagementViewClusterSort() {
    cluster_management_view_cluster_orderBy = $("#cluster_management_maintain_cluster_sort_keyword option:selected").val();

    if($("#cluster_management_view_cluster_sort_asc").is(":checked")){
        cluster_management_view_cluster_desc = "asc";
    }else {
        cluster_management_view_cluster_desc = "desc";
    }
    clusterManagementViewClusterRefresh(1);
}

/**
 * 集群列表的刷新
 */
function clusterManagementViewClusterRefresh(pageIndex) {
    $.ajax({
        type : "GET",
        url  : "/api/cluster/cluster_list",
        data : {"pageNum" : pageIndex,"filter" : cluster_management_view_cluster_filter,"desc" : cluster_management_view_cluster_desc,"orderBy" : cluster_management_view_cluster_orderBy},
        dataType : "json",
        success : function (data) {
            $("#cluster_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                var state = "";
                if(data.data.list[i].state == 1){
                    state = "已加载";
                }else if (data.data.list[i].state == 0) {
                    state = "未加载";
                }
                $("#cluster_table").append($("<tr onclick='clusterManagementViewClusterTRClick(this)' ondblclick='clusterManagementViewClusterDBClick(this)'/>").html(
                    "集群id：" + data.data.list[i].id +"<br/>"
                    + "节点名称：" + data.data.list[i].name + "<br/>"
                    + "节点属性：" + parseAttribute(data.data.list[i].attribute) + "<br/>"
                    + "集群配置：" +data.data.list[i].configuration + "<br/>"
                    + "集群状态：" + state + "<br/>"
                    + "节点数量：" + data.data.list[i].nodeNumber + "<br/>"
                    + "集群创建时间：" + new Date(data.data.list[i].createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                    + "集群修改时间：" + new Date(data.data.list[i].modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                ).append($("<hr/>")).attr("cluster_id",data.data.list[i].id));
            }
            $("#cluster_management_view_cluster_pagination").html("");
            if(data.data.isFirstPage){
                $("#cluster_management_view_cluster_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#cluster_management_view_cluster_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='clusterManagementViewClusterRefresh(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#cluster_management_view_cluster_pagination").append($("<li/>").append($("<span onclick='clusterManagementViewClusterRefresh("  + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#cluster_management_view_cluster_pagination").append($("<li class='active'/>").append($("<span onclick='clusterManagementViewClusterRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#cluster_management_view_cluster_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#cluster_management_view_cluster_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='clusterManagementViewClusterRefresh(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }
        }
    });
}

/**
 * 刷新按钮
 */
function clusterManagementViewClusterRefreshButton() {
    cluster_management_view_cluster_filter = null;
    cluster_management_view_cluster_desc = "asc";
    cluster_management_view_cluster_orderBy = "id";

    $("#cluster_management_view_cluster_filter_label").html("无");

    clusterManagementViewClusterRefresh(1);
}

function clusterManagementViewLoad(){
    var on_tr = $("#cluster_table").find("tr.tr-on");
    if(on_tr != null){
        cluster_management_view_current_cluster = on_tr.attr("cluster_id");
        $.ajax({
            type : "GET",
            url  : "/api/cluster/load",
            data : {"clusterID":cluster_management_view_current_cluster},
            dataType : "json",
            success : function (data) {
                alert(data.msg);
            }
        });
    }else{
        alert("请选中对应集群！")
    }
}

/**
 * 集群导出
 */
function clusterManagementViewExport() {
    alert("该功能还未实现，您选中的导出数据的格式为：" + $("input[name='export_way']:checked").val());
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
 * 节点筛选
 */
function clusterManagementViewFilter(){
    cluster_management_view_cluster_filter = $("#cluster_management_view_filter_input").val();
    $("#cluster_management_view_cluster_filter_label").html(cluster_management_view_cluster_filter);
    clusterManagementViewClusterRefresh(1);
}

/**
 * 查看集群详细信息
 */
function clusterManagementViewClusterDetail() {
    var on_tr = $("#cluster_table").find("tr.tr-on");
    if(on_tr != null){
        cluster_management_view_current_cluster = on_tr.attr("cluster_id");
        clusterManagementViewClusterDetailLoad();
    }else{
        alert("请选中对应集群！")
    }
}

/**
 * 根据id加载集群列表
 * @param cluster_id
 */
function clusterManagementViewClusterDetailLoad() {

    cluster_management_view_node_filter = null;
    cluster_management_view_node_desc = "asc";
    cluster_management_view_node_orderBy = "id";

    $("#main_content").html('');
    $("#main_content").html('<div class="row div-row">' +
                 '<label>集群 <label id="cluster_management_cluster_label"></label>' + '  节点列表：</label>' +
            '</div>' +
            '<div>' +
                '<label>排序关键字：<select id="cluster_management_view_node_sort_keyword"><option value="id">id</option><option value="name">name</option><option value="attributes">attributes</option><option value="service_number">service_number</option><option value="position">position</option><option value="cluster">cluster</option><option value="associated_nodes">associated_nodes</option><option value="level">level</option><option value="create_time">create_time</option><option value="modify_time">modify_time</option></select></label>' +
                '<label>升降序<input type="checkbox" id="cluster_management_view_node_sort_asc"></label>' +
                '<button onclick="clusterManagementViewNodeSort()">排序</button>' +
            '</div>' +
            '<div class="row div-row">' +
                '<table id="cluster_management_view_node_table" class="table table-bordered table-hover table-responsive">' +
                    '<tr>' +
                    '</tr>' +
                '</table>' +
            '</div>' +
            '<div class="row div-row">' +
                '<label for="cluster_management_view_node_filter_label">当前筛选条件：</label>' +
                '<label id="cluster_management_view_node_filter_label">无</label>' +
            '</div>' +
            '<div class="row text-center div-row">' +
                '<nav aria-label="Page navigation">' +
                    '<ul id="cluster_management_view_node_pagination" class="pagination"></ul>' +
                '</nav>' +
            '</div>' +
            '<div class="row div-row">' +
                '<div class="col-md-2 col-md-offset-1">' +
                    '<button class="btn btn-default btn-sm" onclick="clusterManagementViewNodeRefreshButton()">刷新</button>' +
                '</div>' +
                '<div class="col-md-2">' +
                    '<button class="btn btn-default btn-sm" id="cluster_management_cluster_filter" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
                '</div>' +
                '<div class="col-md-2">' +
                    '<button class="btn btn-default btn-sm" id="cluster_management_cluster_export"  data-toggle="modal" data-target="#cluster_management_export_modal">导出</button>' +
                '</div>' +
                '<div class="col-md-2">' +
                    '<button class="btn btn-default btn-sm" id="cluster_management_cluster_detail" onclick="clusterManagementViewNodeDetail()">查看服务信息</button>' +
                '</div>' +
                '<div class="col-md-2">' +
                    '<button class="btn btn-default btn-sm" id="cluster_management_cluster_detail" onclick="clusterManagementViewCluster()">返回</button>' +
                '</div>' +
            '</div>');
    $("#main_content").append($("<div class='modal fade' id='cluster_management_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_view_node_filter_input'/>").html("约束表达式：")).append($("<input id='cluster_management_view_node_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementViewNodeFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementViewNodeExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_view_node_detail_modal' tabindex='-1' role='dialog' aria-labelledby='modal_detail_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_detail_title'/>").html("详细信息")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row' id='cluster_management_view_node_detail_modal_content'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementViewCluster()'/>").html(">> 集群管理")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementViewClusterDetailLoad()'/>").html(">> 节点信息"));

    $.ajax({
            type: "GET",
            data: {
                "clusterID": cluster_management_view_current_cluster,
            },
            url: "/api/cluster/cluster_info",
            dataType: "json",
            success: function (data) {
                $("#cluster_management_cluster_label").html(data.data.name);
            }
        });

    clusterManagementViewClusterDetailRequest(1);

}

function clusterManagementViewNodeSort() {
    cluster_management_view_node_orderBy = $("#cluster_management_view_node_sort_keyword option:selected").val();

    if($("#cluster_management_view_node_sort_asc").is(":checked")){
        cluster_management_view_node_desc = "asc";
    }else {
        cluster_management_view_node_desc = "desc";
    }
    clusterManagementViewClusterDetailRequest(1);
}

function clusterManagementViewNodeDBClick(obj) {
    $.ajax({
        type : "GET",
        data : {"nodeID" : $(obj).attr("node_id")},
        url  : "/api/node/node_info",
        dataType : "json",
        success : function (data) {
            $("#cluster_management_view_node_detail_modal_content").html(
                "节点id：" + data.data.id +"<br/>"
                + "节点名称：" + data.data.name + "<br/>"
                + "节点属性：" + parseAttribute(data.data.attributes) + "<br/>"
                + "节点服务数量：" +data.data.serviceNumber + "<br/>"
                + "节点位置：" + data.data.position + "<br/>"
                + "节点关联节点：" + parseAssociatedNode(data.data.associatedNodeServiceInfos) + "<br/>"
                + "节点等级：" + data.data.level + "<br/>"
                + "节点创建时间：" + new Date(data.data.createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                + "节点修改时间：" + new Date(data.data.modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
            );
            $("#cluster_management_view_node_detail_modal").modal();
        }
    });
}

/**
 * 排序
 * @param obj
 */
function clusterManagementViewNodeOrder(obj) {
    cluster_management_view_node_orderBy = $(obj).attr("order");
    if(cluster_management_view_node_desc == "asc"){
        cluster_management_view_node_desc = "desc";
    }else {
        cluster_management_view_node_desc = "asc";
    }
    clusterManagementViewClusterDetailRequest(1);
}

/**
 * 查看节点信息
 */
function clusterManagementViewClusterDetailRequest(pageNum) {
    $.ajax({
        type : "GET",
        data : {"clusterID":cluster_management_view_current_cluster,"pageNum":pageNum,"filter" : cluster_management_view_node_filter,"desc" : cluster_management_view_node_desc,"orderBy" : cluster_management_view_node_orderBy},
        url  : "/api/node/node_list",
        dataType : "json",
        success : function (data) {
            $("#cluster_management_view_node_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#cluster_management_view_node_table").append($("<tr onclick='clusterManagementViewClusterTRClick(this)' ondblclick='clusterManagementViewNodeDBClick(this)'/>").html(
                    "节点id：" + data.data.list[i].id +"<br/>"
                    + "节点名称：" + data.data.list[i].name + "<br/>"
                    + "节点属性：" + parseSimpleAttribute(data.data.list[i].attributes) + "<br/>"
                    + "节点服务数量：" +data.data.list[i].serviceNumber + "<br/>"
                    + "节点位置：" + data.data.list[i].position + "<br/>"
                    + "节点关联节点：" + parseSimpleAssociatedNode(data.data.list[i].associatedNodes) + "<br/>"
                    + "节点等级：" + data.data.list[i].level + "<br/>"
                    + "节点创建时间：" + new Date(data.data.list[i].createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                    + "节点修改时间：" + new Date(data.data.list[i].modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                ).append($("<hr/>")).attr("node_id",data.data.list[i].id));
            }
            $("#cluster_management_view_node_pagination").html("");
            if(data.data.isFirstPage){
                $("#cluster_management_view_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#cluster_management_view_node_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='clusterManagementViewClusterDetailRequest(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#cluster_management_view_node_pagination").append($("<li/>").append($("<span onclick='clusterManagementViewClusterDetailRequest(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#cluster_management_view_node_pagination").append($("<li class='active'/>").append($("<span onclick='clusterManagementViewClusterDetailRequest(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#cluster_management_view_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#cluster_management_view_node_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='clusterManagementViewClusterDetailRequest(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }

        }
    });
}


/**
 * 节点列表刷新
 */
function clusterManagementViewNodeRefreshButton(){
    cluster_management_view_node_filter = null;
    cluster_management_view_node_desc = "asc";
    cluster_management_view_node_orderBy = "id";

    $("#cluster_management_view_node_filter_label").html("无");

    clusterManagementViewClusterDetailRequest(1);
}

/**
 * 节点筛选
 */
function clusterManagementViewNodeFilter() {
    cluster_management_view_node_filter = $("#cluster_management_view_node_filter_input").val();
    $("#cluster_management_view_node_filter_label").html(cluster_management_view_node_filter);
    clusterManagementViewClusterDetailRequest(1);
}

/**
 * 节点导出
 */
function clusterManagementViewNodeExport(){
    alert("该功能还未实现，您选中的导出数据的格式为：" + $("input[name='export_way']:checked").val());
}

function clusterManagementViewNodeDetail() {
    var on_tr = $("#cluster_management_view_node_table").find("tr.tr-on");
    if(on_tr != null){
        cluster_management_view_current_service =  on_tr.attr("node_id");
        clusterManagementViewClusterService();
    }else{
        alert("请选中对应集群！")
    }
}

/**
 * 服务详情
 */
function clusterManagementViewClusterService(){

    cluster_management_view_service_filter = null;
    cluster_management_view_service_desc = "asc";
    cluster_management_view_service_orderBy = "id";

    $("#main_content").html('');
    $("#main_content").html('<h3>现有服务列表</h3> ' +
        '<div class="div-row row">' +
            '<label>排序关键字：<select id="cluster_management_view_service_sort_keyword"><option value="id">id</option><option value="name">name</option><option value="attributes">attributes</option><option value="node">node</option><option value="cluster">cluster</option><option value="content">content</option><option value="create_time">create_time</option><option value="modify_time">modify_time</option></select></label>' +
            '<label>升降序<input type="checkbox" id="cluster_management_view_service_sort_asc"></label>' +
            '<button onclick="clusterManagementViewServiceSort()">排序</button>' +
        '</div>' +
        '<div class="row div-row">' +
        '<table id="cluster_management_view_service_table" class="table table-bordered table-hover table-responsive">' +
            '<tr>' +
                
            '</tr>' +
        '</table>' +
        '</div>' +
        '<div class="row">' +
            '<label>当前筛选条件：</label>' +
            '<label id="cluster_management_view_service_filter_label">无</label>' +
        '</div>' +
        '<div class="row text-center div-row">' +
            '<nav aria-label="Page navigation">' +
                '<ul id="cluster_management_view_service_pagination" class="pagination"></ul>' +
            '</nav>' +
        '</div>' +
        '<div class="row div-row">' +
            '<div class="col-md-2 col-md-offset-2">' +
                '<button class="btn btn-default btn-sm" onclick="clusterManagementViewClusterServiceRefresh()">刷新</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#service_management_view_filter_modal">筛选</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#service_management_view_export_modal">导出</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" onclick="clusterManagementViewClusterDetailLoad()">返回</button>' +
            '</div>' +
        '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='service_management_view_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='cluster_management_view_service_filter_input'/>").html("约束表达式：")).append($("<input id='cluster_management_view_service_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementViewServiceFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='service_management_view_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementViewServiceExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='cluster_management_view_service_detail_modal' tabindex='-1' role='dialog' aria-labelledby='modal_detail_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_detail_title'/>").html("详细信息")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row' id='cluster_management_view_service_detail_modal_detail_content'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    clusterManagementViewClusterServiceRequest(1);

    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementViewCluster()'/>").html(">> 集群浏览")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementViewClusterDetailLoad()'/>").html(">> 节点浏览")).append($("<button class='btn btn-link btn-xs' onclick='clusterManagementViewClusterService()'/>").html(">> 服务浏览"));
}

function clusterManagementViewServiceDB(obj) {
    $.ajax({
        type : "GET",
        data : {"serviceID" : $(obj).attr("service_id")},
        url  : "/api/service/service_info",
        dataType : "json",
        success : function (data) {
            $("#cluster_management_view_service_detail_modal_detail_content").html(
                "服务id：" + data.data.id +"<br/>"
                + "服务名称：" + data.data.name + "<br/>"
                + "服务属性：" + parseAttribute(data.data.attributes) + "<br/>"
                + "服务内容： " + data.data.content + "<br/>"
                + "服务所属节点： " + data.data.node + "<br/>"
                + "服务所属集群：" + data.data.cluster + "<br/>"
                + "服务创建时间：" + new Date(data.data.createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                + "服务修改时间：" + new Date(data.data.modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
            );
            $("#cluster_management_view_service_detail_modal").modal();
        }
    });
}

function clusterManagementViewServiceSort() {
    cluster_management_view_service_orderBy = $("#cluster_management_view_service_sort_keyword option:selected").val();

    if($("#cluster_management_view_service_sort_asc").is(":checked")){
        cluster_management_view_service_desc = "asc";
    }else {
        cluster_management_view_service_desc = "desc";
    }

    clusterManagementViewClusterServiceRequest(1);
}

/**
 * 排序
 * @param obj
 */
function clusterManagementViewClusterServiceOrder(obj) {
    cluster_management_view_service_orderBy = $(obj).attr("order");
    if(cluster_management_view_service_desc == "asc"){
        cluster_management_view_service_desc = "desc";
    }else {
        cluster_management_view_service_desc = "asc";
    }
    clusterManagementViewClusterServiceRequest(1);
}

/**
 * 服务浏览界面的刷新
 */
function clusterManagementViewClusterServiceRequest(pageNum) {
    $.ajax({
        type : "GET",
        url  : "/api/service/service_list",
        data : {"nodeID" : cluster_management_view_current_service,"pageNum" : pageNum,"filter" : cluster_management_view_service_filter,"desc" : cluster_management_view_service_desc,"orderBy" : cluster_management_view_service_orderBy},
        dataType : "json",
        success : function (data) {
            $("#cluster_management_view_service_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#cluster_management_view_service_table").append($("<tr onclick='clusterManagementViewServiceTRClick(this)' ondblclick='clusterManagementViewServiceDB(this)'/>").html(
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

            $("#cluster_management_view_service_pagination").html("");
            if(data.data.isFirstPage){
                $("#cluster_management_view_service_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#cluster_management_view_service_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='clusterManagementViewClusterServiceRequest(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#cluster_management_view_service_pagination").append($("<li/>").append($("<span onclick='clusterManagementViewClusterServiceRequest("  + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#cluster_management_view_service_pagination").append($("<li class='active'/>").append($("<span onclick='clusterManagementViewClusterServiceRequest(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#cluster_management_view_service_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#cluster_management_view_service_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='clusterManagementViewClusterServiceRequest(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }
        }
    });
}

function clusterManagementViewClusterServiceRefresh() {
    cluster_management_view_service_filter = null;
    cluster_management_view_service_desc = "asc";
    cluster_management_view_service_orderBy = "id";

    $("#cluster_management_view_service_filter_label").html("无");

    clusterManagementViewClusterServiceRequest(1);
}

/**
 * 每行选中事件
 * @param obj
 */
function clusterManagementViewServiceTRClick(obj) {
    var trs = $("#cluster_management_view_service_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}


/**
 * 筛选按钮
 */
function clusterManagementViewServiceFilter() {
    cluster_management_view_service_filter = $("#cluster_management_view_service_filter_input").val();
    $("#cluster_management_view_service_filter_label").html(service_management_view_filter);
    clusterManagementViewClusterServiceRequest(1);
}

/**
 * 数据导出
 */
function clusterManagementViewServiceExport() {
    alert("该功能还未实现，您选中的导出数据的格式为：" + $("input[name='export_way']:checked").val());
}
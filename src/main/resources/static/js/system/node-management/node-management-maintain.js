var node_management_maintain_node_filter;
var node_management_maintain_node_desc = "asc";
var node_management_maintain_node_orderBy = "id";

var node_management_maintain_current_node;

var node_management_maintain_service_filter;
var node_management_maintain_service_desc = "asc";
var node_management_maintain_service_orderBy = "id";
/**
 * 节点维护
 */
function nodeManagementMaintain() {

    node_management_maintain_node_filter = null;
    node_management_maintain_node_desc = "asc";
    node_management_maintain_node_orderBy = "id";

    $("#main_content").html('');
    $("#main_content").html('<h4>现有节点列表：</h4>' +
        '<div>' +
            '<label>排序关键字：<select id="node_management_maintain_node_sort_keyword"><option value="id">id</option><option value="name">name</option><option value="attributes">attributes</option><option value="service_number">service_number</option><option value="position">position</option><option value="cluster">cluster</option><option value="associated_nodes">associated_nodes</option><option value="level">level</option><option value="create_time">create_time</option><option value="modify_time">modify_time</option></select></label>' +
            '<label>升降序<input type="checkbox" id="node_management_maintain_node_sort_asc"></label>' +
            '<button onclick="nodeManagementMaitainNodeSort()">排序</button>' +
        '</div>' +
        '<div class="row div-row">' +
            '<table id="node_management_maintain_node_table" class="table table-bordered table-hover table-responsive">' +
                '<tr></tr>' +
            '</table>' +
        '</div>' +
        '<div class="row div-row">' +
            '<label for="node_management_maintain_node_filter_label">当前筛选条件：</label>' +
            '<label id="node_management_maintain_node_filter_label">无</label>' +
        '</div>' +
        '<div class="row text-center div-row">' +
            '<nav aria-label="Page navigation">' +
                '<ul id="node_management_maintain_node_pagination" class="pagination"></ul>' +
            '</nav>' +
        '</div> ' +
        '<div class="row div-row">' +
            '<div class="col-md-1 col-md-offset-1">' +
                '<button class="btn btn-default btn-sm" onclick="nodeManagementMaintainRefreshButton()">刷新</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" id="node_maintenance_filter" data-toggle="modal" data-target="#node_management_maintain_node_filter_modal">筛选</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#node_management_maintain_node_export_modal">导入</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#node_management_maintain_node_add_modal">添加</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#node_management_maintain_node_change_modal">修改</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" onclick="nodeManagementMaintainNodeDelete()">删除</button>' +
            '</div>' +
            '<div class="col-md-1">' +
                '<button class="btn btn-default btn-sm" id="node_maintenance_detail" onclick="nodeManagementMaintainNodeDetail()">查看服务信息</button>' +
            '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='node_management_maintain_node_filter_modal' tabindex='-1' role='dialog' aria-labelledby='node_management_maintain_node_modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='node_management_maintain_node_modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='node_management_maintain_node_filter_label'/>").html("约束表达式：")).append($("<input id='node_management_maintain_node_filter_label' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='nodeManagementMaintainNodeFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='node_management_maintain_node_export_modal' tabindex='-1' role='dialog' aria-labelledby='node_management_maintain_node_modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='node_management_maintain_node_modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='clusterManagementExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='node_management_maintain_node_add_modal' tabindex='-1' role='dialog' aria-labelledby='node_management_maintain_node_modal_add_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='node_management_maintain_node_modal_add_title'/>").html("添加节点")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("名称：").append($("<input type='text' id='node_management_maintain_node_add_name'>")))
                    .append($("<div class='row'/>").append("<label/>").html("属性：").append($("<input type='text'id='node_management_maintain_node_add_attributes'>")))
                    .append($("<div class='row'/>").append("<label/>").html("坐标：").append($("<input type='text'id='node_management_maintain_node_add_position'>")))
                    .append($("<div class='row'/>").append("<label/>").html("所属集群：").append($("<input type='text' id='node_management_maintain_node_add_cluster'>"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal' onclick='nodeManagementMaintainNodeAdd()'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='node_management_maintain_node_change_modal' tabindex='-1' role='dialog' aria-labelledby='node_management_maintain_node_modal_change_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='node_management_maintain_node_modal_change_title'/>").html("修改节点")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("名称：").append($("<input type='text' id='node_management_maintain_node_change_name'>")))
                    .append($("<div class='row'/>").append("<label/>").html("属性：").append($("<input type='text'id='node_management_maintain_node_change_attributes'>")))
                    .append($("<div class='row'/>").append("<label/>").html("坐标：").append($("<input type='text'id='node_management_maintain_node_change_position'>")))
                    .append($("<div class='row'/>").append("<label/>").html("所属集群：").append($("<input type='text' id='node_management_maintain_node_change_cluster'>"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal' onclick='nodeManagementMaintainNodeModify()'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='node_management_maintain_node_detail_modal' tabindex='-1' role='dialog' aria-labelledby='modal_detail_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_detail_title'/>").html("详细信息")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row' id='node_management_maintain_node_detail_modal_detail_content'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='nodeManagementMaintain()'/>").html(">> 节点维护"));

    nodeManagementMaintainNodeRequest(1);
}

function nodeManagementMaitainNodeSort() {
    node_management_maintain_node_orderBy = $("#node_management_maintain_node_sort_keyword option:selected").val();

    if($("#node_management_maintain_node_sort_asc").is(":checked")){
        node_management_maintain_node_desc = "asc";
    }else {
        node_management_maintain_node_desc = "desc";
    }
    nodeManagementMaintainNodeRequest(1);
}

/**
 * 排序
 * @param obj
 */
function nodeManagementMaintainNodeOrder(obj) {
    node_management_maintain_node_orderBy = $(obj).attr("order");
    if(node_management_maintain_node_desc == "asc"){
        node_management_maintain_node_desc = "desc";
    }else {
        node_management_maintain_node_desc = "asc";
    }
    nodeManagementMaintainNodeRequest(1);
}

/**
 * 节点添加
 */
function nodeManagementMaintainNodeAdd() {
    $.ajax({
        type : "GET",
        url : "/api/node/add",
        data : {"name":$("#node_management_maintain_node_add_name").val(),"attributes" : $("#node_management_maintain_node_add_attributes").val(),"position":$("#node_management_maintain_node_add_position").val(),"cluster":$("#node_management_maintain_node_add_cluster").val()},
        dataType : "json",
        success : function (data) {
            alert(data.msg);
        }
    });
}

/**
 * 节点修改
 */
function nodeManagementMaintainNodeModify() {
    var on_tr = $("#node_management_maintain_node_table").find("tr.tr-on");
    if(on_tr != null){
        $.ajax({
            type : "GET",
            url : "/api/node/modify",
            data : {"nodeID":on_tr.attr("node_id"),"name":$("#node_management_maintain_node_change_name").val(),"attributes" : $("#node_management_maintain_node_change_attributes").val(),"position":$("#node_management_maintain_node_change_position").val(),"cluster":$("#node_management_maintain_node_add_cluster").val()},
            dataType : "json",
            success : function (data) {
                alert(data.msg);
            }
        });
    }else{
        alert("请选中对应节点！")
    }

}

function nodeManagementMaintainNodeDBClick(obj) {
    $.ajax({
        type : "GET",
        data : {"nodeID" : $(obj).attr("node_id")},
        url  : "/api/node/node_info",
        dataType : "json",
        success : function (data) {
            $("#node_management_maintain_node_detail_modal_detail_content").html(
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
            $("#node_management_maintain_node_detail_modal").modal();
        }
    });
}

/**
 * 查看节点信息
 */
function nodeManagementMaintainNodeRequest(pageNum) {
    $.ajax({
        type : "GET",
        data : { "pageNum":pageNum,"filter" : node_management_maintain_node_filter,"desc" : node_management_maintain_node_desc,"orderBy" : node_management_maintain_node_orderBy},
        url  : "/api/node/node_list_all",
        dataType : "json",
        success : function (data) {
            $("#node_management_maintain_node_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#node_management_maintain_node_table").append($("<tr onclick='nodeManagementMaintainNodeTRClick(this)' ondblclick='nodeManagementMaintainNodeDBClick(this)'/>").html(
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
            $("#node_management_maintain_node_pagination").html("");
            if(data.data.isFirstPage){
                $("#node_management_maintain_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#node_management_maintain_node_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='nodeManagementMaintainNodeRequest(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#node_management_maintain_node_pagination").append($("<li/>").append($("<span onclick='nodeManagementMaintainNodeRequest(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#node_management_maintain_node_pagination").append($("<li class='active'/>").append($("<span onclick='nodeManagementMaintainNodeRequest(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#node_management_maintain_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#node_management_maintain_node_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='nodeManagementMaintainNodeRequest(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }

        }
    });
}

/**
 * 节点刷新按钮
 */
function nodeManagementMaintainRefreshButton() {
    node_management_maintain_node_filter = null;
    node_management_maintain_node_desc = "asc";
    node_management_maintain_node_orderBy = "id";

    $("#node_management_maintain_node_filter_label").html("无");
    nodeManagementMaintainNodeRequest(1);
}

/**
 * 删除节点
 */
function nodeManagementMaintainNodeDelete(){
    var on_tr = $("#node_management_view_node_table").find("tr.tr-on");
    if(on_tr != null){
        node_management_view_current_node = on_tr.attr("node_id");
        alert("删除节点");
    }else{
        alert("请选中对应节点！")
    }
}

/**
 * 每行选中事件
 * @param obj
 */
function nodeManagementMaintainNodeTRClick(obj) {
    var trs = $("#node_management_maintain_node_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 节点筛选
 */
function nodeManagementMaintainNodeFilter() {
    node_management_maintain_node_filter = $("#node_management_maintain_node_filter_label").val();
    $("#node_management_maintain_node_filter_label").html(node_management_maintain_node_filter);
    nodeManagementMaintainNodeRequest(1);
}

/**
 * 查看节点详情
 */
function nodeManagementMaintainNodeDetail() {
    var on_tr = $("#node_management_maintain_node_table").find("tr.tr-on");
    if(on_tr != null){
        node_management_maintain_current_node = on_tr.attr("node_id");
        nodeManagementMaintainNodeDetailLoad();
    }else{
        alert("请选中对应节点！")
    }
}

/**
 * 服务详情页面加载
 */
function nodeManagementMaintainNodeDetailLoad() {

    node_management_maintain_service_filter = null;
    node_management_maintain_service_desc = "asc";
    node_management_maintain_service_orderBy = "id";

    $("#main_content").html('');
    $("#main_content").html('<h3>现有服务列表</h3> ' +
        '<div class="div-row row">' +
            '<label>排序关键字：<select id="node_management_maintain_service_sort_keyword"><option value="id">id</option><option value="name">name</option><option value="attributes">attributes</option><option value="node">node</option><option value="cluster">cluster</option><option value="content">content</option><option value="create_time">create_time</option><option value="modify_time">modify_time</option></select></label>' +
            '<label>升降序<input type="checkbox" id="node_management_maintain_service_sort_asc"></label>' +
            '<button onclick="nodeManagementMaintainServiceSort()">排序</button>' +
        '</div>' +
        '<div class="row div-row">' +
            '<table id="node_management_maintain_service_table" class="table table-bordered table-hover table-responsive">' +
                '<tr>' +
                '</tr>' +
            '</table>' +
        '</div>' +
        '<div class="row">' +
            '<label>当前筛选条件：</label>' +
            '<label id="node_management_maintain_service_filter_label">无</label>' +
        '</div>' +
        '<div class="row text-center div-row">' +
            '<nav aria-label="Page navigation">' +
                '<ul id="node_management_maintain_service_pagination" class="pagination"></ul>' +
            '</nav>' +
        '</div>' +
        '<div class="row div-row">' +
            '<div class="col-md-2 col-md-offset-2">' +
                '<button class="btn btn-default btn-sm" onclick="nodeManagementMaintainServiceRefreshButton()">刷新</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#service_management_maintain_filter_modal">筛选</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#service_management_maintain_export_modal">导出</button>' +
            '</div>' +
            '<div class="col-md-2 col-md-offset-2">' +
                '<button class="btn btn-default btn-sm" onclick="nodeManagementMaintain()">返回</button>' +
            '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='service_management_maintain_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='service_management_maintain_filter_input'/>").html("约束表达式：")).append($("<input id='service_management_maintain_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='nodeManagementMaintainServiceFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='service_management_maintain_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='nodeManagementMaintainServiceExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='node_management_maintain_service_detail_modal' tabindex='-1' role='dialog' aria-labelledby='modal_detail_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_detail_title'/>").html("详细信息")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row' id='node_management_maintain_service_detail_modal_detail_content'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    nodeManagementMaintainServiceRefresh(1);
}

function nodeManagementMaintainServiceDBClick(obj) {
    $.ajax({
        type : "GET",
        data : {"serviceID" : $(obj).attr("service_id")},
        url  : "/api/service/service_info",
        dataType : "json",
        success : function (data) {
            $("#node_management_maintain_service_detail_modal_detail_content").html(
                "服务id：" + data.data.id +"<br/>"
                + "服务名称：" + data.data.name + "<br/>"
                + "服务属性：" + parseAttribute(data.data.attributes) + "<br/>"
                + "服务内容： " + data.data.content + "<br/>"
                + "服务所属节点： " + data.data.node + "<br/>"
                + "服务所属集群：" + data.data.cluster + "<br/>"
                + "服务创建时间：" + new Date(data.data.createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                + "服务修改时间：" + new Date(data.data.modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
            );
            $("#node_management_maintain_service_detail_modal").modal();
        }
    });
}

function nodeManagementMaintainServiceSort() {
    node_management_maintain_service_orderBy = $("#node_management_maintain_service_sort_keyword option:selected").val();

    if($("#node_management_maintain_service_sort_asc").is(":checked")){
        node_management_maintain_service_desc = "asc";
    }else {
        node_management_maintain_service_desc = "desc";
    }

    nodeManagementMaintainServiceRefresh(1);
}

/**
 * 排序
 * @param obj
 */
function nodeManagementMaintainServiceOrder(obj) {
    node_management_maintain_service_orderBy = $(obj).attr("order");
    if(node_management_maintain_service_desc == "asc"){
        node_management_maintain_service_desc = "desc";
    }else {
        node_management_maintain_service_desc = "asc";
    }
    nodeManagementMaintainServiceRefresh(1);
}

/**
 * 服务节点的刷新
 */
function nodeManagementMaintainServiceRefresh(pageNum) {
    $.ajax({
        type : "GET",
        url  : "/api/service/service_list",
        data : {"nodeID":node_management_maintain_current_node,"pageNum" : pageNum,"filter" : node_management_maintain_service_filter,"desc" : node_management_maintain_service_desc,"orderBy" : node_management_maintain_service_orderBy},
        dataType : "json",
        success : function (data) {
            $("#node_management_maintain_service_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#node_management_maintain_service_table").append($("<tr onclick='nodeManagementMaintainServiceTRClick(this)' ondblclick='nodeManagementMaintainServiceDBClick(this)'/>").html(
                    "服务id：" + data.data.list[i].id +"<br/>"
                    + "服务名称：" + data.data.list[i].name + "<br/>"
                    + "服务属性：" + parseAttribute(data.data.list[i].attributes) + "<br/>"
                    + "服务内容： " + simplifyServiceContent(data.data.list[i].content) + "<br/>"
                    + "服务所属节点： " + data.data.list[i].node + "<br/>"
                    + "服务所属集群：" + data.data.list[i].cluster + "<br/>"
                    + "服务创建时间：" + new Date(data.data.list[i].createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                    + "服务修改时间：" + new Date(data.data.list[i].modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                ).attr("service_id",data.data.list[i].id));
            }

            $("#node_management_maintain_service_pagination").html("");
            if(data.data.isFirstPage){
                $("#node_management_maintain_service_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#node_management_maintain_service_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='nodeManagementViewServiceRefresh(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#node_management_maintain_service_pagination").append($("<li/>").append($("<span onclick='nodeManagementMaintainServiceRefresh("  + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#node_management_maintain_service_pagination").append($("<li class='active'/>").append($("<span onclick='nodeManagementMaintainServiceRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#node_management_maintain_service_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#node_management_maintain_service_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='nodeManagementMaintainServiceRefresh(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }
        }
    });
}

/**
 * 服务刷新
 */
function nodeManagementMaintainServiceRefreshButton() {
    node_management_maintain_service_filter = null;
    node_management_maintain_service_desc = "asc";
    node_management_maintain_service_orderBy = "id";

    $("#node_management_maintain_service_filter_label").html("无");

    nodeManagementMaintainServiceRefresh(1);
}

/**
 * 每行选中事件
 * @param obj
 */
function nodeManagementMaintainServiceTRClick(obj) {
    var trs = $("#node_management_maintain_service_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 筛选按钮
 */
function nodeManagementMaintainServiceFilter() {
    node_management_maintain_service_filter = $("#service_management_maintain_filter_input").val();
    $("#node_management_maintain_service_filter_label").html(node_management_maintain_service_filter);
    nodeManagementMaintainServiceRefresh(1);
}

/**
 * 服务导出按钮
 */
function nodeManagementMaintainServiceExport() {
    alert("该功能还未实现，您选中的导出数据的格式为：" + $("input[name='export_way']:checked").val());
}
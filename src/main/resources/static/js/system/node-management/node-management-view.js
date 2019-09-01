var node_management_view_node_filter;
var node_management_view_node_desc = "asc";
var node_management_view_node_orderBy = "id";

var node_management_view_current_node;

var node_management_view_service_filter;
var node_management_view_service_desc = "asc";
var node_management_view_service_orderBy = "id";
/**
 * 节点浏览
 */
function nodeManagementViewNode() {

    node_management_view_node_filter = null;
    node_management_view_node_desc = "asc";
    node_management_view_node_orderBy = "id";

    $("#main_content").html('');
    $("#main_content").html('<h4>现有节点列表：</h4>' +
        '<div class="row div-row">' +
            '<table id="node_management_view_node_table" class="table table-hover system-table">' +
                '<tr>' +
                    '<div>' +
                        '<label>排序关键字：<select id="node_management_view_node_sort_keyword"><option value="id">id</option><option value="name">name</option><option value="attributes">attributes</option><option value="service_number">service_number</option><option value="position">position</option><option value="cluster">cluster</option><option value="associated_nodes">associated_nodes</option><option value="level">level</option><option value="create_time">create_time</option><option value="modify_time">modify_time</option></select></label>' +
                        '<label>升降序<input type="checkbox" id="node_management_view_node_sort_asc"></label>' +
                        '<button onclick="nodeManagementViewNodeSort()">排序</button>' +
                    '</div>' +
                '</tr>' +
            '</table>' +
        '</div>' +
        '<div class="row div-row">' +
            '<label for="node_management_view_node_filter_label">当前筛选条件：</label>' +
            '<label id="node_management_view_node_filter_label">无</label>' +
        '</div>' +
        '<div class="row text-center div-row">' +
            '<nav aria-label="Page navigation">' +
                '<ul id="node_management_view_node_pagination" class="pagination"></ul>' +
            '</nav>' +
        '</div> ' +
        '<div class="row div-row">' +
            '<div class="col-md-2 col-md-offset-1">' +
                '<button class="btn btn-default btn-sm" onclick="nodeManagementViewNodeRefreshButton()">刷新</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" id="node_view_filter" data-toggle="modal" data-target="#node_management_view_node_filter_modal">筛选</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" id="node_view_export" data-toggle="modal" data-target="#node_management_view_node_export_modal">导出</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" onclick="nodeManagementViewNodeDetail()">查看服务信息</button>' +
            '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='node_management_view_node_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='node_management_view_node_filter_input'/>").html("约束表达式：")).append($("<input id='node_management_view_node_filter_input' class='form-control'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='nodeManagementViewNodeFilter()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='node_management_view_node_export_modal' tabindex='-1' role='dialog' aria-labelledby='modal_export_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_export_title'/>").html("导出数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("导出数据格式").append($("<label/>").append($("<input type='radio' name='export_way' value='txt'/>")).append("txt")).append($("<label/>").append($("<input type='radio' name='export_way' value='xls'/>")).append("xls")).append($("<label/>").append($("<input type='radio' name='export_way' value='xml'/>")).append("xml"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' onclick='nodeManagementViewExport()' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    $("#main_content").append($("<div class='modal fade' id='node_management_view_node_detail_modal' tabindex='-1' role='dialog' aria-labelledby='modal_detail_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_detail_title'/>").html("详细信息")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row' id='node_management_view_node_detail_modal_detail_content'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
    nodeManagementViewRefresh(1);
    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='nodeManagementViewNode()'/>").html(">> 节点浏览"));
}

function nodeManagementViewNodeSort() {
    node_management_view_node_orderBy = $("#node_management_view_node_sort_keyword option:selected").val();

    if($("#node_management_view_node_sort_asc").is(":checked")){
        node_management_view_node_desc = "asc";
    }else {
        node_management_view_node_desc = "desc";
    }
    nodeManagementViewRefresh(1);
}

/**
 * 排序
 * @param obj
 */
function nodeManagementViewNodeOrder(obj) {
    node_management_view_node_orderBy = $(obj).attr("order");
    if(node_management_view_node_desc == "asc"){
        node_management_view_node_desc = "desc";
    }else {
        node_management_view_node_desc = "asc";
    }
    nodeManagementViewRefresh(1);
}

/**
 * 节点筛选
 */
function nodeManagementViewNodeFilter() {
    node_management_view_node_filter = $("#node_management_view_node_filter_input").val();
    $("#node_management_view_node_filter_label").html(node_management_view_node_filter);
    nodeManagementViewRefresh(1);
}

/**
 * 数据导出
 */
function nodeManagementViewExport() {
    alert("该功能还未实现，您选中的导出数据的格式为：" + $("input[name='export_way']:checked").val());
}

/**
 * 节点列表的刷新
 */
function nodeManagementViewRefresh(pageNum) {
    $.ajax({
        type : "GET",
        data : { "pageNum":pageNum,"filter" : node_management_view_node_filter,"desc" : node_management_view_node_desc,"orderBy" : node_management_view_node_orderBy},
        url  : "/api/node/node_list_all",
        dataType : "json",
        success : function (data) {
            $("#node_management_view_node_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#node_management_view_node_table").append($("<tr onclick='nodeManagementViewNodeTRClick(this)' ondblclick='nodeManagementViewNodeDBClick(this)'/>")
                    .append($("<td/>").html(
                        "节点id：" + data.data.list[i].id +"<br/>"
                        + "节点名称：" + data.data.list[i].name + "<br/>"
                        + "节点属性：" + parseSimpleAttribute(data.data.list[i].attributes) + "<br/>"
                        + "节点服务数量：" +data.data.list[i].serviceNumber + "<br/>"
                        + "节点位置：" + data.data.list[i].position + "<br/>"
                        + "节点关联节点：" + parseSimpleAssociatedNode(data.data.list[i].associatedNodes) + "<br/>"
                        + "节点等级：" + data.data.list[i].level + "<br/>"
                        + "节点创建时间：" + new Date(data.data.list[i].createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                        + "节点修改时间：" + new Date(data.data.list[i].modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"))
                    .append($("<hr/>")).attr("node_id",data.data.list[i].id));
            }
            $("#node_management_view_node_pagination").html("");
            if(data.data.isFirstPage){
                $("#node_management_view_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Previous'/>").append($("<span/>").html("&laquo;"))));
            }else {
                $("#node_management_view_node_pagination").append($("<li/>").append($("<span aria-label='Previous' onclick='nodeManagementViewRefresh(" + data.data.prePage + ")'/>").append($("<span/>").html("&laquo;"))));
            }

            for(var index=0;index < data.data.navigatepageNums.length;index++){
                if(data.data.navigatepageNums[index] != data.data.pageNum){
                    $("#node_management_view_node_pagination").append($("<li/>").append($("<span onclick='nodeManagementViewRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }else {
                    $("#node_management_view_node_pagination").append($("<li class='active'/>").append($("<span onclick='nodeManagementViewRefresh(" + data.data.navigatepageNums[index] + ")'/>").append($("<span/>").html(data.data.navigatepageNums[index]))));
                }
            }

            if(data.data.isLastPage){
                $("#node_management_view_node_pagination").append($("<li class='disabled'/>").append($("<span aria-label='Next'/>").append($("<span/>").html("&raquo;"))));
            }else {
                $("#node_management_view_node_pagination").append($("<li/>").append($("<span aria-label='Next' onclick='nodeManagementViewRefresh(" + data.data.nextPage + ")'/>").append($("<span/>").html("&raquo;"))));
            }

        }
    });
}

/**
 * 节点刷新按钮
 */
function nodeManagementViewNodeRefreshButton() {
    node_management_view_node_filter = null;
    node_management_view_node_desc = "asc";
    node_management_view_node_orderBy = "id";

    $("#node_management_view_node_filter_label").html("无");
    nodeManagementViewRefresh(1);
}

function nodeManagementViewNodeDBClick(obj){
    $.ajax({
        type : "GET",
        data : {"nodeID" : $(obj).attr("node_id")},
        url  : "/api/node/node_info",
        dataType : "json",
        success : function (data) {
            $("#node_management_view_node_detail_modal_detail_content").html(
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
            $("#node_management_view_node_detail_modal").modal();
        }
    });

}

/**
 * 每行选中事件
 * @param obj
 */
function nodeManagementViewNodeTRClick(obj) {
    var trs = $("#node_management_view_node_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 查看节点详情
 */
function nodeManagementViewNodeDetail() {
    var on_tr = $("#node_management_view_node_table").find("tr.tr-on");
    if(on_tr != null){
        node_management_view_current_node = on_tr.attr("node_id");
        nodeManagementViewNodeDetailLoad();
    }else{
        alert("请选中对应节点！")
    }
}

/**
 * 详情页面加载
 */
function nodeManagementViewNodeDetailLoad() {

    node_management_view_service_filter = null;
    node_management_view_service_desc = "asc";
    node_management_view_service_orderBy = "id";

    $("#main_content").html('');
    $("#main_content").html('<h3>现有服务列表</h3> ' +
        '<div class="div-row row">' +
            '<label>排序关键字：<select id="node_management_view_service_sort_keyword"><option value="id">id</option><option value="name">name</option><option value="attributes">attributes</option><option value="node">node</option><option value="cluster">cluster</option><option value="content">content</option><option value="create_time">create_time</option><option value="modify_time">modify_time</option></select></label>' +
            '<label>升降序<input type="checkbox" id="node_management_view_service_sort_asc"></label>' +
            '<button onclick="nodeManagementViewServiceSort()">排序</button>' +
        '</div>' +
        '<div class="row div-row">' +
            '<table id="node_management_view_service_table" class="table table-bordered table-hover table-responsive">' +
                '<tr>' +

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
                '<button class="btn btn-default btn-sm" onclick="nodeManagementViewServiceRefreshButton()">刷新</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#node_management_view_service_filter_modal">筛选</button>' +
            '</div>' +
            '<div class="col-md-2">' +
                '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#service_management_view_export_modal">导出</button>' +
            '</div>' +
        '</div>');
    $("#main_content").append($("<div class='modal fade' id='node_management_view_service_filter_modal' tabindex='-1' role='dialog' aria-labelledby='modal_filter_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_filter_title'/>").html("筛选记录")))
                .append($("<div class='modal-body'/>").append($("<label for='node_management_view_service_filter_input'/>").html("约束表达式：")).append($("<input id='node_management_view_service_filter_input' class='form-control'/>")))
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

    $("#main_content").append($("<div class='modal fade' id='node_management_view_service_detail_modal' tabindex='-1' role='dialog' aria-labelledby='modal_detail_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_detail_title'/>").html("详细信息")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row' id='node_management_view_service_detail_modal_detail_content'/>")))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));

    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='nodeManagementViewNode()'/>").html(">> 节点浏览")).append($("<button class='btn btn-link btn-xs' onclick='nodeManagementViewNodeDetailLoad()'/>").html(">> 服务浏览"));

    nodeManagementViewServiceRefresh(1);
}

function nodeManagementViewServiceDBClick(obj) {
    $.ajax({
        type : "GET",
        data : {"serviceID" : $(obj).attr("service_id")},
        url  : "/api/service/service_info",
        dataType : "json",
        success : function (data) {
            $("#node_management_view_service_detail_modal_detail_content").html(
                "服务id：" + data.data.id +"<br/>"
                + "服务名称：" + data.data.name + "<br/>"
                + "服务属性：" + parseAttribute(data.data.attributes) + "<br/>"
                + "服务内容： " + data.data.content + "<br/>"
                + "服务所属节点： " + data.data.node + "<br/>"
                + "服务所属集群：" + data.data.cluster + "<br/>"
                + "服务创建时间：" + new Date(data.data.createTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
                + "服务修改时间：" + new Date(data.data.modifyTime).Format("yyyy-MM-dd hh:mm:ss") + "<br/>"
            );
            $("#node_management_view_service_detail_modal").modal();
        }
    });
}

function nodeManagementViewServiceSort() {
    node_management_view_service_orderBy = $("#node_management_view_service_sort_keyword option:selected").val();

    if($("#node_management_view_service_sort_asc").is(":checked")){
        node_management_view_service_desc = "asc";
    }else {
        node_management_view_service_desc = "desc";
    }

    nodeManagementViewServiceRefresh(1);
}

/**
 * 排序
 * @param obj
 */
function nodeManagementViewServiceOrder(obj) {
    node_management_view_service_orderBy = $(obj).attr("order");

    if(node_management_view_service_desc == "asc"){
        node_management_view_service_desc = "desc";
    }else {
        node_management_view_service_desc = "asc";
    }
    nodeManagementViewServiceRefresh(1);
}

/**
 * 服务节点的刷新
 */
function nodeManagementViewServiceRefresh(pageNum) {
    $.ajax({
        type : "GET",
        url  : "/api/service/service_list",
        data : {"nodeID":node_management_view_current_node,"pageNum" : pageNum,"filter" : node_management_view_service_filter,"desc" : node_management_view_service_desc,"orderBy" : node_management_view_service_orderBy},
        dataType : "json",
        success : function (data) {
            $("#node_management_view_service_table tr").nextAll().remove();
            for(var i=0;i<data.data.list.length;i++){
                $("#node_management_view_service_table").append($("<tr onclick='nodeManagementServiceViewTRClick(this)' ondblclick='nodeManagementViewServiceDBClick(this)'/>").html(
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
 * 服务刷新按钮
 */
function nodeManagementViewServiceRefreshButton() {
    node_management_view_service_filter = null;
    node_management_view_service_desc = "asc";
    node_management_view_service_orderBy = "id";

    $("#node_management_view_service_filter_label").html("无");
    nodeManagementViewServiceRefresh(1);
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
function nodeManagementViewServiceFilter() {
    node_management_view_service_filter = $("#node_management_view_service_filter_input").val();
    $("#node_management_view_service_filter_label").html(node_management_view_service_filter);
    nodeManagementViewServiceRefresh(1);
}

/**
 * 服务导出按钮
 */
function nodeManagementViewServiceExport() {
    alert("该功能还未实现，您选中的导出数据的格式为：" + $("input[name='export_way']:checked").val());
}
//
function clusterManagementMaintanceDetailLoad(cluster_id) {
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
}

function clusterManagementViewClusterDetailRequest(cluster_id) {
    $.ajax({
        type : "GET",
        data : {"cluster" : cluster_id},
        url  : "/api/cluster/cluster_info",
        dataType : "json",
        success : function (data) {
            $("#cluster_management_cluster_table tr").nextAll().remove();
            for(var i=0;i<data.data.length;i++){
                $("#cluster_management_cluster_table").append($("<tr/>")
                    .append($("<td/>").html(data.data[i].id))
                    .append($("<td/>").html(data.data[i].name))
                    .append($("<td/>").html(data.data[i].attrs))
                    .append($("<td/>").html(data.data[i].serviceNumber))
                    .append($("<td/>").html(data.data[i].position))
                    .append($("<td/>").html(data.data[i].associatedNode))
                    .append($("<td/>").html(data.data[i].level))
                    .append($("<td/>").html(data.data[i].createTime))
                    .append($("<td/>").html(data.data[i].modifyTime)));
            }
        }
    });
}

function clusterManagementDetail() {
    var on_tr = $("#cluster_table").find("tr.tr-on");
    if(on_tr != null){
        clusterManagementDetailLoad(on_tr.attr("cluster_id"));
    }else{
        alert("请选中对应集群！")
    }
}

function clusterManagementMaintainRefresh() {
    $.ajax({
        type : "GET",
        url  : "/api/cluster/cluster_list",
        dataType : "json",
        success : function (data) {
            $("#cluster_table tr").nextAll().remove();
            for(var i=0;i<data.data.length;i++){
                var state = "";
                if(data.data[i].state == 1){
                    state = "已加载";
                }else if (data.data[i].state == 0) {
                    state = "未加载";
                }
                $("#cluster_table").append($("<tr onclick='clusterManagementTRClick(this)'/>")
                    .append($("<td/>").html(data.data[i].id))
                    .append($("<td/>").html(data.data[i].name))
                    .append($("<td/>").html(data.data[i].attribute))
                    .append($("<td/>").html(data.data[i].nodeNumber))
                    .append($("<td/>").html(data.data[i].configuration))
                    .append($("<td/>").html(data.data[i].createTime))
                    .append($("<td/>").html(data.data[i].modifyTime))
                    .append($("<td/>").html(data.data[i].state)).attr("cluster_id",data.data[i].id));
            }
        }
    })
}

//加载集群维护页面
function clusterManagementMaintaince() {
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

//添加集群
function clusterManagementMaintainAdd() {
    $.ajax({
        type : "GET",
        url  : "/api/cluster/add_cluster",
        data : {"name":$("#add_cluster_name").val(),"type":$('input[name="cluster_structure_type"]:checked').val()},
        dataType : "json",
        success : function () {
            clusterManagementMaintainRefresh();
        }
    })
}

//删除集群
function clusterManagementMaintainDel() {
    var on_tr = $("#cluster_table").find("tr.tr-on");
    if(on_tr != null){
        $.ajax({
            type : "GET",
            data : {"id" : on_tr.attr("cluster_id")},
            url  : "/api/cluster/del_cluster",
            dataType : "json",
            success : function () {
                clusterManagementMaintainRefresh();
            }
        })
    }else{
        alert("请选中对应集群！")
    }
}

function clusterManagementTRClick(obj) {
    var trs = $("#cluster_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}


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
        '<div class="row">' +
        '<label>当前筛选条件：</label>' +
        '<label id="service_view_filter_label">无</label>' +
        '</div>' +
        '<div class="row">' +
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

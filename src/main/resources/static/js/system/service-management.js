var service_management_view_size;
//服务浏览界面
function servicesViewer() {
    $("#main_content").html('');
    $("#main_content").html('<h3>现有服务列表</h3> ' +
        '<table id="service_view_table" class="table table-bordered table-hover table-responsive">' +
        ' <tr>' +
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
        '<div class="row">' +
        '<label>当前筛选条件：</label>' +
        '<label id="service_view_filter_label">无</label>' +
        '</div>' +
        '<div class="row div-row text-center"><div id="service_management_view_pagition" class="pagina"></div> </div> ' +
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
    serviceViewRefresh();
    alert(service_management_view_size);
    $("#service_management_view_pagition").pagination(service_management_view_size, {
        num_edge_entries: 1, //边缘页数
        num_display_entries: 4, //主体页数
        callback: serviceViewPagintion,
        items_per_page: 10, //每页显示个数
        prev_text: "前一页",
        next_text: "后一页"
    });
}

/**
 * 服务列表的刷新
 */
function serviceViewRefresh(pageIndex) {
    $.ajax({
        type : "GET",
        async : false,
        url  : "/api/service/service_list",
        data : {"pageNum" : pageIndex},
        dataType : "json",
        success : function (data) {
            service_management_view_size = data.data.sum;
            $("#service_view_table tr").nextAll().remove();
            for(var i=0;i<data.data.infoList.length;i++){
                $("#service_view_table").append($("<tr onclick='serviceViewTRClick(this)'/>")
                    .append($("<td/>").html(data.data.infoList[i].id))
                    .append($("<td/>").html(data.data.infoList[i].name))
                    .append($("<td/>").html(data.data.infoList[i].attribute))
                    .append($("<td/>").html(data.data.infoList[i].cluster))
                    .append($("<td/>").html(data.data.infoList[i].node))
                    .append($("<td/>").html(data.data.infoList[i].content))
                    .append($("<td/>").html(data.data.infoList[i].createTime))
                    .append($("<td/>").html(data.data.infoList[i].modifyTime)).attr("service_id",data.data.infoList[i].id));
            }
        }
    })
}

/**
 * 服务分页回调函数
 * @param page
 * @param jq
 */
function serviceViewPagintion(page,jq) {
    serviceViewRefresh(page);
}

/**
 * 服务列表每行单击函数
 * @param obj
 */
function serviceViewTRClick(obj) {
    var trs = $("#service_view_table").find("tr");
    if(trs.hasClass("tr-on")){
        trs.removeClass("tr-on");
    }
    $(obj).addClass("tr-on");
}

/**
 * 服务标准化加载
 */
function serviceStandardization() {
    $("#main_content").html('');
    $("#main_content").html('<div class="row div-row">' +
        '<label for="standardization_rule_select">标准化准则：</label>' +
        '<select id="standardization_rule_select">' +
        '<option>标准化准则1</option>' +
        '<option>标准化准则2</option>' +
        '<option>标准化准则3</option>' +
        '</select>' +
        '</div>' +
        '<div class="row div-row">' +
        '<div class="checkbox">' +
        '<label>' +
        '<input id="delete_original_data_checkbox" type="checkbox">标准化完成后删除原始服务表中的数据' +
        '</label>' +
        '</div>' +
        '</div>' +
        '<h4>请选择需要标准化的服务：</h4>' +
        '<div class="row div-row">' +
        '<table id="standardized_services_table" class="table-responsive table table-bordered table-hover">' +
        '<tr>' +
        '<th>ID</th>' +
        '<th>名称</th>' +
        '<th>内容</th>' +
        '<th>创建时间</th>' +
        '<th>修改时间</th>' +
        '<th>是否要标准化</th>' +
        '</tr>' +
        '</table>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label for="standardization_filter_label">当前筛选条件：</label>' +
        '<label id="standardization_filter_label">无</label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<div class="col-md-2 col-md-offset-2">' +
        '<button class="btn btn-default btn-sm" id="service_view_refresh" onclick="serviceViewRefresh()">刷新</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="service_view_filter" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="service_view_export" >开始标准化</button>' +
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
}

/**
 * 服务维护加载
 */
function servicesReserved() {
    $("#main_content").html('');
    $("#main_content").html('<h4>现有服务列表：</h4>' +
        '<div class="row div-row">' +
        '<table id="reserved_services_table" class="table-responsive table table-bordered table-hover">' +
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
        '<div class="row div-row">' +
        '<label for="reserved_filter_label">当前筛选条件：</label>' +
        '<label id="reserved_filter_label">无</label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<div class="col-md-1 col-md-offset-2">' +
        '<button class="btn btn-default btn-sm">刷新</button>' +
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
        '<button class="btn btn-default btn-sm">删除</button>' +
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
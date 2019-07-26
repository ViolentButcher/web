/**
 * 服务标准化
 */
function serviceManagementStandardization() {
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
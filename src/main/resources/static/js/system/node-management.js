function nodeView() {
    $("#main_content").html('');
    $("#main_content").html('<h4>现有节点列表：</h4>' +
        '<div class="row div-row">' +
        '<table id="node_view_table" class="table table-bordered table-hover table-responsive">' +
        '<tr>' +
        '<th>ID</th>' +
        '<th>名称</th>' +
        '<th>服务个数</th>' +
        '<th>坐标</th>' +
        '<th>所属集群</th>' +
        '<th>关联节点</th>' +
        '<th>等级</th>' +
        '<th>创建时间</th>' +
        '<th>修改时间</th>' +
        '</tr>' +
        '</table>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label for="node_view_filter_label">当前筛选条件：</label>' +
        '<label id="node_view_filter_label">无</label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<div class="col-md-2 col-md-offset-1">' +
        '<button class="btn btn-default btn-sm" id="node_view_refresh">刷新</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="node_view_filter" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="node_view_export" data-toggle="modal" data-target="#cluster_management_export_modal">导出</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" id="node_view_detail" onclick="nodeViewInfo()">查看服务信息</button>' +
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

function nodeMaintenance() {
    $("#main_content").html('');
    $("#main_content").html('<h4>现有节点列表：</h4>' +
        '<div class="row div-row">' +
        '<table id="node_maintenance_table" class="table table-bordered table-hover table-responsive">' +
        '<tr>' +
        '<th>ID</th>' +
        '<th>名称</th>' +
        '<th>属性</th>' +
        '<th>服务个数</th>' +
        '<th>坐标</th>' +
        '<th>所属集群</th>' +
        '<th>关联节点</th>' +
        '<th>等级</th>' +
        '<th>创建时间</th>' +
        '<th>修改时间</th>' +
        '</tr>' +
        '</table>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label for="node_maintenance_filter_label">当前筛选条件：</label>' +
        '<label id="node_maintenance_filter_label">无</label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<div class="col-md-1 col-md-offset-1">' +
        '<button class="btn btn-default btn-sm" id="node_maintenance_refresh">刷新</button>' +
        '</div>' +
        '<div class="col-md-1">' +
        '<button class="btn btn-default btn-sm" id="node_maintenance_filter" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
        '</div>' +
        '<div class="col-md-1">' +
        '<button class="btn btn-default btn-sm" id="node_maintenance_import" data-toggle="modal" data-target="#cluster_management_export_modal">导入</button>' +
        '</div>' +
        '<div class="col-md-1">' +
        '<button class="btn btn-default btn-sm" id="node_maintenance_add" data-toggle="modal" data-target="#cluster_management_add_modal">添加</button>' +
        '</div>' +
        '<div class="col-md-1">' +
        '<button class="btn btn-default btn-sm" id="node_maintenance_change" data-toggle="modal" data-target="#cluster_management_change_modal">修改</button>' +
        '</div>' +
        '<div class="col-md-1">' +
        '<button class="btn btn-default btn-sm" id="node_maintenance_delete" data-target="#cluster_management_delete_modal">删除</button>' +
        '</div>' +
        '<div class="col-md-1">' +
        '<button class="btn btn-default btn-sm" id="node_maintenance_detail" onclick="nodeMaintenanceInfo()">查看服务信息</button>' +
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

function nodeViewInfo() {
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
        '<button class="btn btn-default btn-sm" id="service_view_back" onclick="nodeView()">返回</button>' +
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

function nodeMaintenanceInfo() {
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
        '<button class="btn btn-default btn-sm" id="service_view_back" onclick="nodeMaintenance()">返回</button>' +
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
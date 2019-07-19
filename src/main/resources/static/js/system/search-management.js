function serviceSearch() {
    $("#main_content").html('');
    $("#main_content").html('<div class="row div-row">' +
        '<label for="search_cluster_name">集群名称:</label>' +
        '<select id="search_cluster_name">' +
        '<option>1</option>' +
        '<option>2</option>' +
        '<option>3</option>' +
        '</select>' +
        '</div>' +
        '<h4>请求内容：</h4>' +
        '<div class="row div-row">' +
        '<div class="radio">' +
        '<label class="col-md-2">' +
        '<input type="radio" name="search_config_radio" id="search_config_radio_manual" onclick="searchConfigurationRadioManual()" value="1">' +
        '手动设置' +
        '</label>' +
        '<label class="col-md-2">' +
        '<input type="radio" name="search_config_radio" id="search_config_radio_batch_export" value="2" onclick="searchConfigurationRadioBatch()">' +
        '批量导入' +
        '</label>' +
        '<label class="col-md-2">' +
        '<input type="radio" name="search_config_radio" id="search_config_radio_auto" value="3" onclick="searchConfigurationRadioAuto()">' +
        '自动设置' +
        '</label>' +
        '</div>' +
        '</div>' +
        '<div id="search_config_content" class="row div-row">' +
        '</div>' +
        '<h4>搜索算法：</h4>' +
        '<div class="row div-row">' +
        '<label for="search_algorithm">算法名称:</label>' +
        '<select id="search_algorithm">' +
        '<option>1</option>' +
        '<option>2</option>' +
        '<option>3</option>' +
        '</select>' +
        '</div>' +
        '<div class="row div-row">' +
        '<button class="btn btn-default" id="search_config_next_button" onclick="searchSecondStep()">下一步</button>' +
        '</div>');
}

function searchConfigurationRadioManual() {
    $("#search_config_content").html('');
    $("#search_config_content").html('<div class="row div-row">' +
        '<label>请求节点：</label>' +
        '<select>' +
        '<option>1</option>' +
        '<option>2</option>' +
        '<option>3</option>' +
        '</select>' +
        '</div>' +
        '<div class="div-row row">' +
        '<label>请求内容：<input type="text"></label>' +
        '</div>');
}

function searchConfigurationRadioBatch() {
    $("#search_config_content").html('');
    $("#search_config_content").html('<div class="row div-row">' +
        '<label>文件格式：</label>' +
        '<label>' +
        '<input type="radio" name="import_radio">' +
        'txt' +
        '</label>' +
        '<label>' +
        '<input type="radio" name="import_radio">' +
        'xsl' +
        '</label>' +
        '<label>' +
        '<input type="radio" name="import_radio">' +
        'xml' +
        '</label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>选择文件：<input type="file"></label>' +
        '</div>');
}

function searchConfigurationRadioAuto() {
    $("#search_config_content").html('');
    $("#search_config_content").html('<div class="row div-row">' +
        '<label>请求生成规则：</label>' +
        '<select>' +
        '<option>1</option>' +
        '<option>2</option>' +
        '<option>3</option>' +
        '</select>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>参数设置：<input type="text"></label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>请求生成个数：</label>' +
        '<select>' +
        '<option>1</option>' +
        '<option>2</option>' +
        '<option>3</option>' +
        '</select>' +
        '</div>');
}

function searchSecondStep() {
    $("#main_content").html('');
    $("#main_content").html('<div class="row div-row">' +
        '<label>集群：</label>' +
        '<label id="search_cluster"></label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>算法：</label>' +
        '<label id="search_algorithm"></label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>参数：</label>' +
        '<label id="search_args"></label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>请求个数：</label>' +
        '<label id="search_requests_num"></label>' +
        '</div>' +
        '<div class="div div-row">' +
        '<label>' +
        '<input type="checkbox" id="nodes_format_input">' +
        '节点空间格式化：' +
        '</label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<canvas id="my_canvas" width="800" height="800" style="border:1px solid #000000;"></canvas>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>日志：</label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<div class="well well-sm col-md-8">' +
        '第一条日志....<br/>' +
        '第二条日志....<br/>' +
        '</div>' +
        '</div>' +
        '<div class="div div-row">' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm">开始</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm">停止</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" onclick="serviceSearch()">上一步</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" onclick="searchThirdStep()">下一步</button>' +
        '</div>' +
        '</div>');
}

function searchThirdStep() {
    $("#main_content").html('');
    $("#main_content").html('<h3>搜索结果显示：</h3>' +
        '<div class="row div-row">' +
        '<label>请求序号：<input type="text"></label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>请求节点名称：<input type="text"></label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>请求内容：<input type="text"></label>' +
        '</div>' +
        '<h4>搜索结果：</h4>' +
        '<div class="row div-row">' +
        '<table class="table table-hover table-responsive table-bordered">' +
        '<tr>' +
        '<th>ID</th>' +
        '<th>名称</th>' +
        '<th>属性</th>' +
        '<th>所属节点</th>' +
        '<th>内容</th>' +
        '<th>创建时间</th>' +
        '<th>修改时间</th>' +
        '</tr>' +
        '</table>' +
        '</div>' +
        '<div class="div-row div">' +
        '<label for="search_start_time_label">搜索开始时间：</label>' +
        '<label id="search_start_time_label"></label>' +
        '</div>' +
        '<div class="div-row div">' +
        '<label for="search_cost_time_label">搜索耗时：</label>' +
        '<label id="search_cost_time_label"></label>' +
        '</div>' +
        '<div class="div-row div">' +
        '<label for="search_filter_label">当前筛选条件：</label>' +
        '<label id="search_filter_label"></label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<div class="col-md-2 col-md-offset-2">' +
        '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_filter_modal">筛选</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_change_modal">修改属性</button>' +
        '</div>' +
        '<div class="col-md-2">' +
        '<button class="btn btn-default btn-sm" data-toggle="modal" data-target="#cluster_management_export_modal">导出</button>' +
        '</div>' +
        '</div>' +
        '<h3>搜索结果保存：</h3>' +
        '<div class="row">' +
        '<label class="col-md-2">搜索文件格式：</label>' +
        '<label class="col-md-1">' +
        '<input type="radio" name="search_file" value="txt">' +
        'txt' +
        '</label>' +
        '<label class="col-md-1">' +
        '<input type="radio" name="search_file" value="xls">' +
        'xls' +
        '</label>' +
        '<label class="col-md-1">' +
        '<input type="radio" name="search_file" value="xml">' +
        'xml' +
        '</label>' +
        '</div>'
    );
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
    $("#main_content").append($("<div class='modal fade' id='cluster_management_change_modal' tabindex='-1' role='dialog' aria-labelledby='modal_change_title' aria-hidden='true'/>")
        .append($("<div class='modal-dialog'/>")
            .append($("<div class='modal-content'/>")
                .append($("<div class='modal-header'/>").append($("<h4 class='modal-title' id='modal_change_title'/>").html("修改数据")))
                .append($("<div class='modal-body'/>")
                    .append($("<div class='row'/>").append("<label/>").html("字段1").append($("<input type='text'>"))))
                .append($("<div class='modal-footer'/>")
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("确定"))
                    .append($("<button class='btn btn-default' data-dismiss='modal'/>").html("取消"))))));
}
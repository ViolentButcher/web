/**
 * 服务分配
 */
function serviceManagementDistribution() {
    $("#main_content").html('');
    $("#main_content").html('<div class="row div-row">' +
            '<label for="distribution_cluster_select">分配集群选择：</label>' +
            '<select id="distribution_cluster_select">' +
            '</select>' +
        '</div>' +
        '<div class="row div-row">' +
            '<label for="distribution_rule_select">分配规则：</label>' +
                '<select id="distribution_rule_select">' +
                '<option value="1">分配规则1</option>' +
                '<option value="2">分配规则2</option>' +
                '<option value="3">分配规则3</option>' +
            '</select>' +
        '</div>' +
        '<div class="row div-row">' +
            '<label><input type="checkbox" id="distribution_checkbox">已经分配的不再选择</label>' +
        '</div>' +
        '<div class="row div-row">' +
            '<button class="btn btn-default btn-sm" onclick="startDistribution()">开始分配</button>' +
        '</div>');

    $("#location").html('');

    //展示位置信息
    $("#location").append($("<label/>").html("位置：")).append($("<button class='btn btn-link btn-xs' onclick='serviceManagementDistribution()'/>").html(">> 服务分配"));

    serviceManagementDistributionLoad();
}

/**
 * 界面加载
 */
function serviceManagementDistributionLoad() {
    $.ajax({
        type : "GET",
        url  : "/api/cluster/distribution/cluster_list",
        dataType : "json",
        success : function (data) {
            for(var i=0;i<data.data.length;i++){
                $("#distribution_cluster_select").append($("<option/>").html(data.data[i].name).attr("value",data.data[i].id));
            }
        }
    });
}

/**
 * 开始分配
 */
function startDistribution() {
    $.ajax({
        type : "GET",
        url  : "/api/service/distribution",
        data : {"cluster":$("#distribution_cluster_select option:selected").val(),"strategy" : $("#distribution_rule_select option:selected").val(),"reserved":$("#distribution_checkbox").is(":checked")},
        dataType : "json",
        success : function (data) {
            alert("目前该功能还未完成\n"+ data.msg);
        }
    });

}
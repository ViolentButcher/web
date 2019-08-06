function testSearchManagement() {
    $("#main_content").html('');
    $("#main_content").html('<div class="row div-row">' +
        '<label for="search_cluster_name">集群名称:</label>' +
            '<select id="search_cluster_name" onchange="searchManagementSelectCluster()">' +
            '</select>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>请求节点：</label>' +
            '<select id="search_request_node">' +
                '<option value=-1>无</option>' +
            '</select>' +
        '</div>' +
        '<div class="row div-row">' +
            '<label>请求算法：</label>' +
            '<select id="search_algorithm_type">' +
                '<option value=1>算法1</option>' +
                '<option value=2>算法2</option>' +
                '<option value=3>算法3</option>' +
            '</select>' +
        '</div>' +
        '<div class="div-row row">' +
            '<label>请求内容：<input id="search_input" type="text"></label>' +
        '</div>' +
        '<div class="div-row row">' +
            '<button class="btn btn-default btn-sm" onclick="testSearch()">开始搜索</button>' +
        '</div>' +
        '<div class="div-row row">' +
            '<label>推荐耗时：<label id="search_cost"></label></label>' +
        '</div>' +
        '<div class="row div-row">' +
            '<table id="search_result" class="table-responsive table table-bordered table-striped"></table>' +
        '</div>'

    );

    testSearchManagementRequest();
}

function testSearchManagementRequest() {
    $("#search_cluster_name").html('<option value=-1>无</option>');
    $.ajax({
        type : "GET",
        url  : "/api/cluster/distribution/cluster_list",
        dataType : "json",
        success : function (data) {
            for(var i=0;i<data.data.length;i++){
                $("#search_cluster_name").append($("<option/>").html(data.data[i].name).attr("value",data.data[i].id));
            }
        }
    });
}

function searchManagementSelectCluster() {
    if($("#search_cluster_name option:selected").val() != null && $("#search_cluster_name option:selected").val()!=-1){
        $("#search_request_node").html('<option value="-1">无</option>');
        $.ajax({
            type : "GET",
            url  : "/api/node/node_list/cluster",
            data : {"clusterID" : $("#search_cluster_name option:selected").val()},
            dataType : "json",
            success : function (data) {
                for(var i=0;i<data.data.length;i++){
                    $("#search_request_node").append($("<option/>").html(data.data[i].name).attr("value",data.data[i].id));
                }
            }
        });
    }
}

function testSearch() {
    $("#search_cost").html("");
    if($("#search_request_node option:selected").val() != null && $("#search_request_node option:selected").val()!=-1){
        if($("#search_input").val() != null){

            $.ajax({
                type : "GET",
                url  : "/test/api/search/test",
                data : {"nodeId" : $("#search_request_node option:selected").val(),"keyword":$("#search_input").val(),"type":$("#search_algorithm_type option:selected").val()},
                dataType : "json",
                success : function (data) {
                    $("#search_result").html("");
                    for (var i=0;i<data.data.result.length;i++){
                        $("#search_result").append($("<tr/>")
                            .append($("<td/>").html("id:" + data.data.result[i].id
                                                + "<br>attributes:" + data.data.result[i].attributes
                                                + "<br>node:" + data.data.result[i].node
                                                + "<br>cluster:" + data.data.result[i].cluster
                                                + "<br>content:" + data.data.result[i].content
                                                + "<br>createTime:" + new Date(data.data.result[i].createTime).Format("yyyy-MM-dd hh:mm:ss")
                                                + "<br>modifyTime:" + new Date(data.data.result[i].modifyTime).Format("yyyy-MM-dd hh:mm:ss"))));
                    }
                    $("#search_cost").html(data.data.costTime);
                }
            });

        }else {
            alert("请输入请求内容");
        }
    }else {
        alert("请选中请求节点！");
    }
}
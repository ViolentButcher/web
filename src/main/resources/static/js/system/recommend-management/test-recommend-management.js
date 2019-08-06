function testRecommendManagement() {
    $("#main_content").html('');
    $("#main_content").html('<div class="row div-row">' +
        '<label for="recommend_cluster_name">集群名称:</label>' +
        '<select id="recommend_cluster_name" onchange="recommendManagementSelectCluster()">' +
        '</select>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>请求节点：</label>' +
        '<select id="recommend_request_node">' +
        '<option value=-1>无</option>' +
        '</select>' +
        '</div>' +
        '<div class="row div-row">' +
        '<label>请求算法：</label>' +
        '<select id="recommend_algorithm_type">' +
        '<option value=1>算法1</option>' +
        '<option value=2>算法2</option>' +
        '<option value=3>算法3</option>' +
        '</select>' +
        '</div>' +
        '<div class="div-row row">' +
        '<label>请求内容：<input id="recommend_input" type="text"></label>' +
        '</div>' +
        '<div class="div-row row">' +
        '<button class="btn btn-default btn-sm" onclick="testRecommend()">开始搜索</button>' +
        '</div>' +
        '<div class="div-row row">' +
        '<label>推荐耗时：<label id="recommend_cost"></label></label>' +
        '</div>' +
        '<div class="row div-row">' +
        '<table id="recommend_result" class="table-responsive table table-bordered table-striped"></table>' +
        '</div>'

    );

    testRecommendManagementRequest();
}

function testRecommendManagementRequest() {
    $("#recommend_cluster_name").html('<option value=-1>无</option>');
    $.ajax({
        type : "GET",
        url  : "/api/cluster/distribution/cluster_list",
        dataType : "json",
        success : function (data) {
            for(var i=0;i<data.data.length;i++){
                $("#recommend_cluster_name").append($("<option/>").html(data.data[i].name).attr("value",data.data[i].id));
            }
        }
    });
}

function recommendManagementSelectCluster() {
    if($("#recommend_cluster_name option:selected").val() != null && $("#recommend_cluster_name option:selected").val()!=-1){
        $("#recommend_request_node").html('<option value="-1">无</option>');
        $.ajax({
            type : "GET",
            url  : "/api/node/node_list/cluster",
            data : {"clusterID" : $("#recommend_cluster_name option:selected").val()},
            dataType : "json",
            success : function (data) {
                for(var i=0;i<data.data.length;i++){
                    $("#recommend_request_node").append($("<option/>").html(data.data[i].name).attr("value",data.data[i].id));
                }
            }
        });
    }
}

function testRecommend() {
    $("#recommend_cost").html("");
    if($("#recommend_request_node option:selected").val() != null && $("#recommend_request_node option:selected").val()!=-1){
        if($("#recommend_input").val() != null){

            $.ajax({
                type : "GET",
                url  : "/test/api/recommend/test",
                data : {"nodeId" : $("#recommend_request_node option:selected").val(),"keyword":$("#recommend_input").val(),"type":$("#recommend_algorithm_type option:selected").val()},
                dataType : "json",
                success : function (data) {
                    $("#recommend_result").html("");
                    for (var i=0;i<data.data.result.length;i++){
                        $("#recommend_result").append($("<tr/>")
                            .append($("<td/>").html("id:" + data.data.result[i].id
                                + "<br>attributes:" + data.data.result[i].attributes
                                + "<br>node:" + data.data.result[i].node
                                + "<br>cluster:" + data.data.result[i].cluster
                                + "<br>content:" + data.data.result[i].content
                                + "<br>createTime:" + new Date(data.data.result[i].createTime).Format("yyyy-MM-dd hh:mm:ss")
                                + "<br>modifyTime:" + new Date(data.data.result[i].modifyTime).Format("yyyy-MM-dd hh:mm:ss"))));
                    }
                    $("#recommend_cost").html(data.data.costTime);
                }
            });

        }else {
            alert("请输入请求内容");
        }
    }else {
        alert("请选中请求节点！");
    }
}
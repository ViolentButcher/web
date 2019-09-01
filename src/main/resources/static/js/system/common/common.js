
Date.prototype.Format = function(fmt) {
    var o = {
        "M+" : this.getMonth() + 1, // 月份
        "d+" : this.getDate(), // 日
        "h+" : this.getHours(), // 小时
        "m+" : this.getMinutes(), // 分
        "s+" : this.getSeconds(), // 秒
        "q+" : Math.floor((this.getMonth() + 3) / 3), // 季度
        "S" : this.getMilliseconds()
        // 毫秒
    };
    if (/(y+)/.test(fmt))
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
            .substr(4 - RegExp.$1.length));
    for ( var k in o)
        if (new RegExp("(" + k + ")").test(fmt))
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
                : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;

};

String.prototype.replaceAll = function (FindText, RepText) {
    return this.replace(new RegExp(FindText, "g"), RepText);
};

function parseSimpleAttribute(attributes) {

    if (attributes != null && attributes.length > 0) {
        var result = new Array();

        var start = 0;
        var end = 0;
        var count = 0;

        for (var i=0;i<attributes.length;i++){
            if(attributes[i] == "{"){
                start = i;

                while (end <= start || i <= attributes.length ){
                    if (attributes[i] == "}"){
                        end = i;
                        result[count++] = attributes.slice(start+1,end).replaceAll("\"","");
                        break;
                    }
                    i++;
                }
                if(count >= 3){
                    result[count++] = "...";
                    break;
                }
            }
        }
        if (result.length != 0){
            return "<br/>" + result.join("<br/>");
        }
        return "无"
    }

    return "无";

};

function parseSimpleAssociatedNode(associatedNodes) {
    if (associatedNodes != null && associatedNodes.length > 0) {
        var result = new Array();

        var start = 0;
        var end = 0;
        var count = 0;

        for (var i=0;i<associatedNodes.length;i++){
            if(associatedNodes[i] == "{"){
                start = i;

                while (end < start || i <= associatedNodes.length ){
                    if (associatedNodes[i] == "}"){
                        end = i;
                        result[count++] = associatedNodes.slice(start+1,end).replaceAll("\"","");
                        break;
                    }
                    i++;
                }
                if(count >= 3){
                    result[count++] = "...";
                    break;
                }
            }
        }
        if (result.length != 0){
            return "<br/>" + result.join("<br/>");
        }
        return "无"
    }

    return "无";
};

function parseAttribute(attributes) {

    if (attributes != null && attributes.length > 0) {
        var result = new Array();

        var start = 0;
        var end = 0;
        var count = 0;

        for (var i=0;i<attributes.length;i++){
            if(attributes[i] == "{"){
                start = i;

                while (end <= start || i <= attributes.length ){
                    if (attributes[i] == "}"){
                        end = i;
                        result[count++] = attributes.slice(start+1,end).replaceAll("\"","");
                        break;
                    }
                    i++;
                }
            }
        }
        if (result.length != 0){
            return "<br/>" + result.join("<br/>");
        }
        return "无"
    }

    return "无";

};

function parseAssociatedNode(associatedNodes) {
    var result = new Array();

    if (associatedNodes != null) {
        for (var i=0;i<associatedNodes.length;i++) {
            result[i] = "&nbsp&nbsp id:" + associatedNodes[i].id + ",associatedType:" + associatedNodes[i].associatedType + ",serviceAddress:" + associatedNodes[i].serviceAddress;
        }
        return "<br/>" + result.join("<br/>");
    }
    return "无";
};

function simplifyServiceContent(content) {
    if(content != null && content.length > 100){
        return content.slice(0,100) + "...";
    }
    return content;
}
function getExplorer() {
    if (window.navigator.userAgent.indexOf("MSIE") >= 0) {
        return 1;
    } else if (window.navigator.userAgent.indexOf("Firefox") >= 0) {
        return 0;
    } else if (window.navigator.userAgent.indexOf("Chrome") >= 0) {
        return 0;
    } else if (window.navigator.userAgent.indexOf("Opera") >= 0) {
        return 0;
    } else if (window.navigator.userAgent.indexOf("Safari") >= 0) {
        return 0;
    } else {
        return 1;
    }
}

function Msie(id) {
    var tabID= document.getElementById(id);
    var aXO = new ActiveXObject("Excel.Application");
    var oWB = oXL.Workbooks.Add();
    var oSheet = oWB.ActiveSheet;
    var len= tabID.rows.length;   //取得表格行数
    for (i = 0; i < len; i++){
        var Lenc = tabID.rows(i).cells.length;  //取得每行的列数
        for (j = 0; j < Lenc; j++)
        {
            oSheet.Cells(i + 1, j + 1).value = tabID.rows(i).cells(j).innerText;
            //赋值
        }
    }
    aXO.Visible = true;
}

function other(mytalbe){
    var table = document.getElementById(mytalbe);
    // 克隆（复制）此table元素，这样对复制品进行修改（如添加或改变table的标题等），导出复制品，而不影响原table在浏览器中的展示。
    table = table.cloneNode(true);
    var uri = 'data:application/vnd.ms-excel;base64,',
        template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><?xml version="1.0" encoding="UTF-8" standalone="yes"?><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table style="vnd.ms-excel.numberformat:@">{table}</table></body></html>',
        base64 = function(s) {
            return window.btoa(unescape(encodeURIComponent(s)));
        },
        format = function(s, c) {
            return s.replace(/{(\w+)}/g, function(m, p) {
                return c[p];
            });
        };
    if(!table.nodeType) table = document.getElementById(table);
    var ctx = {
        worksheet: name || 'Worksheet',
        table: table.innerHTML
    };
    window.location.href = uri + base64(format(template, ctx));
}

function tabletoExcel(tableID) {
    if(getExplorer() == 1){
        Msie(tableID);
    }else{
        other(tableID);
    }
}
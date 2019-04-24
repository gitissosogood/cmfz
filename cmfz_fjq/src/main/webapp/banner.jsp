<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/icon.css">
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/themes/default/easyui.css">
    <%--<link rel="stylesheet" type="text/css" href="../themes/IconExtension.css">--%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/easyui-lang-zh_CN.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.edatagrid.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#datagrid").edatagrid({
                url: "${pageContext.request.contextPath}/banner/selectAll",
                method: "post",
                fit: true,
                fitColumns: true,
                onEndEdit: function (index, row, changes) {
                    //console.log(changes);
                    if (changes.status != "yes" && changes.status != "no" && changes.status != null) {
                        $.messager.alert("提示框", "状态只能修改为yes或者no", "warning", function () {
                            $("#datagrid").edatagrid("rejectChanges");
                        });
                    } else {
                        if (changes.status == "yes") {
                            $.ajax({
                                url: "${pageContext.request.contextPath}/banner/selectYesCount",
                                type: "post",
                                dataType: "json",
                                success: function (data) {
                                    if (data == 5) {
                                        $.messager.alert("提示框", "yes状态只能有5个,请先设置无用的为no", "warning", function () {
                                            $("#datagrid").edatagrid("rejectChanges");
                                        });
                                    }
                                }
                            });
                        }
                    }
                },
                updateUrl: "${pageContext.request.contextPath}/banner/update",
                toolbar: [{
                    iconCls: 'icon-add',
                    text: "添加",
                    handler: function () {
                        $("#bannerName").textbox("setValue", "");
                        $("[name=status][value=yes]").prop("checked", true);
                        $("#bannerImgPath").filebox("setValue", "");
                        $("#addDialog").dialog("open");
                    }
                }, '-', {
                    iconCls: "icon-redo",
                    text: "导出Excel",
                    handler: function () {
                        window.location.href = "${pageContext.request.contextPath}/banner/exportExcel";
                    }
                }],
                pagination: true,
                columns: [[
                    {field: "title", title: "名字", width: 200, editor: "text"},
                    {field: "status", title: "状态", width: 200, editor: "text"},
                    {field: "imgPath", title: "路径", width: 200},
                    {field: "createDate", title: "时间", width: 200}
                ]],
                onLoadSuccess: function (data) {
                    $("#datagrid").edatagrid("fixRowHeight");
                },
                view: detailview,
                detailFormatter: function (rowIndex, rowData) {
                    return "<table><tr>" +
                        "<td rowspan=3 style='border:0'><img src='${pageContext.request.contextPath}/img/shouye/" + rowData.imgPath + "' style='height:200px;'></td>" +
                        "<td style='border:0'>" +
                        "<p>date: " + rowData.createDate + "</p>" +
                        "<p>description: " + rowData.title + "</p>" +
                        "<p>path: " + rowData.imgPath + "</p>" +
                        "</td>" +
                        "</tr></table>";
                }
            });
            $("#addDialog").dialog({
                title: "添加",
                width: 300,
                height: 200,
                closed: true,
                modal: true,
                buttons: [{
                    iconCls: "icon-ok",
                    text: "确认",
                    handler: function () {
                        $("#addForm").form("submit", {
                            url: "${pageContext.request.contextPath}/banner/addBanner",
                            onSubmit: function () {
                                return $("#addForm").form("validate");
                            },
                            success: function (data) {
                                data = JSON.parse(data);
                                if (data.flag == 1) {
                                    $("#addDialog").dialog("close");
                                    $.messager.alert("提示框", "添加成功！", "info");
                                    $("#datagrid").edatagrid("reload");
                                } else {
                                    $.messager.alert("提示框", "添加失败！", "error");
                                }
                            }
                        });
                    }
                }]
            });
        })
    </script>

</head>
<body>
<table id="datagrid">
</table>
<div id="addDialog">
    <br>
    <form id="addForm" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td>名字:</td>
                <td><input id="bannerName" name="title" class="easyui-textbox" data-options="missingMessage:'名字不能为空'"
                           required="required"></td>
            </tr>
            <tr>
                <td>路径:</td>
                <td><input id="bannerImgPath" name="file" class="easyui-filebox" data-options="missingMessage:'路径不能为空'"
                           required="required"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
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
    <script type="text/javascript">
        $(function () {
            $("#user").datagrid({
                url: "${pageContext.request.contextPath}/user/selectAll",
                fit: true,
                fitColumns: true,
                toolbar: [{
                    iconCls: 'icon-add',
                    text: "添加用户",
                    handler: function () {
                        $("#addUserForm").form("reset");
                        $("#addUserDialog").dialog("open");
                    }
                }],
                columns: [[
                    {field: "name", title: "名字", width: 150},
                    {
                        field: "sex", title: "性别", width: 150, formatter: function (value, row, index) {
                            if (value == 1) {
                                return "男";
                            } else {
                                return "女";
                            }
                        }
                    },
                    {field: "province", title: "省份", width: 150},
                    {field: "createDate", title: "注册时间", width: 300}
                ]],
            });
            $("#addUserDialog").dialog({
                title: "注册用户",
                width: 300,
                height: 200,
                closed: true,
                modal: true,
                buttons: [{
                    text: "确认",
                    iconCls: "icon-ok",
                    handler: function () {
                        $("#addUserForm").form("submit", {
                            url: "${pageContext.request.contextPath}/user/addUser",
                            onSubmit: function () {
                                return $("#addUserForm").form("validate");
                            },
                            success: function (data) {
                                data = JSON.parse(data);
                                if (data.flag == 1) {
                                    $("#addUserDialog").dialog("close");
                                    $.messager.alert("提示框", "添加成功", "info", function () {
                                        $("#user").datagrid("reload");
                                    });
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
<table id="user"></table>
<div id="addUserDialog">
    <br>
    <form id="addUserForm" method="post">
        <table>
            <tr>
                <td>名字：</td>
                <td><input name="name" class="easyui-textbox" data-options="required:true"></td>
            </tr>
            <tr>
                <td>性别：</td>
                <td><input type="radio" name="sex" value="1" checked>男<input type="radio" name="sex" value="0">女</td>
            </tr>
            <tr>
                <td>省份：</td>
                <td><input name="province" class="easyui-textbox" data-options="required:true"></td>
            </tr>
        </table>
    </form>
</div>
</body>
</html>
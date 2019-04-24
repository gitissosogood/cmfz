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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/datagrid-cellediting.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#tt").datagrid({
                width: 200,
                height: 200,
                columns: [[
                    {field: 'bh', title: '编号', width: 100},
                    {field: 'xing', title: '姓', width: 100, editor: 'text'},
                ]],
                dblclickToEdit: true
            }).datagrid('enableCellEditing');
        })
    </script>
</head>
<body>
<audio src="music/meiguihua.mp3" controls="controls">
    您的浏览器不支持audio
</audio>
<table id="tt">
    <tr>
        <td>1</td>
        <td>zhang</td>
    </tr>
    <tr>
        <td>2</td>
        <td>li</td>
    </tr>
</table>
</body>
</html>
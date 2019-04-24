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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/datagrid-detailview.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.edatagrid.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#album").treegrid({
                url: "${pageContext.request.contextPath}/album/queryAll",
                idField: "id",
                treeField: "title",
                fit: true,
                //fitColumns:true,
                toolbar: [{
                    iconCls: 'icon-tip',
                    text: "专辑详情",
                    handler: function () {
                        var select = $("#album").treegrid("getSelected");
                        if (select == null) {
                            $.messager.alert("提示框", "请先选择再查看", "info");
                        } else {
                            var parent = $("#album").treegrid("getParent", select.id);
                            if (parent != null) {
                                select = parent;
                            }
                            $("#titleAlbum").val(select.title);
                            $("#amountAlbum").val(select.amount);
                            $("#scoreAlbum").val(select.score);
                            $("#authorAlbum").val(select.author);
                            $("#boardcastAlbum").val(select.boardcast);
                            $("#publishDateAlbum").val(select.publishDate);
                            $("#briefAlbum").val(select.brief);
                            $("#albumImgPath").prop("src", "${pageContext.request.contextPath}/img/audioCollection/" + select.imgPath);
                            $("#albumDetail").dialog("open");
                        }
                    }
                }, '-', {
                    iconCls: 'icon-save',
                    text: "添加专辑",
                    handler: function () {
                        $("#addAlbumTitle").textbox("setText", "");
                        $("#addAlbumAuthor").textbox("setText", "");
                        $("#addAlbumBoardcast").textbox("setText", "");
                        $("#addAlbumBrief").textbox("setText", "");
                        $("#addAlbum").dialog("open");
                    }
                }, '-', {
                    iconCls: 'icon-save',
                    text: "添加章节",
                    handler: function () {
                        var select = $("#album").treegrid("getSelected");
                        if (select == null) {
                            $.messager.alert("提示框", "请选择一个专辑进行添加", "info");
                        } else {
                            var parent = $("#album").treegrid("getParent", select.id);
                            if (parent != null) {
                                $.messager.alert("提示框", "请选择一个专辑进行添加", "info");
                            } else {
                                $("#albumId").val(select.id);
                                $("#chapterTitle").textbox("setValue", "");
                                $("#chapterFile").filebox("setValue", "");
                                $("#addChapter").dialog("open");
                            }
                        }
                    }
                }, '-', {
                    iconCls: 'icon-undo',
                    text: "下载音频",
                    handler: function () {
                        var select = $("#album").treegrid("getSelected");
                        if (select == null) {
                            $.messager.alert("提示框", "请选择一个章节进行下载", "info");
                        } else {
                            var parent = $("#album").treegrid("getParent", select.id);
                            if (parent == null) {
                                $.messager.alert("提示框", "请选择一个章节进行下载", "info");
                            } else {
                                window.location.href = "${pageContext.request.contextPath}/chapter/download?id=" + select.id;
                            }
                        }
                    }
                }, '-', {
                    iconCls: 'icon-redo',
                    text: "导出Excel",
                    handler: function () {
                        window.location.href = "${pageContext.request.contextPath}/album/exportAlbum"
                    }
                }],
                columns: [[
                    {field: "title", title: "名字", width: 300},
                    {field: "size", title: "章节大小", width: 300},
                    {field: 'duration', title: "章节时长", width: 300}
                ]],
                onDblClickRow: function (row) {
                    console.log(row)
                    var parent = $("#album").treegrid("getParent", row.id);
                    if (parent == null) {
                        $.messager.alert("提示框", "专辑不能播放", "info");
                    } else {
                        $("#music").prop("src", "${pageContext.request.contextPath}/music/" + row.newName);
                        $("#musicDialog").dialog("open");
                    }
                }
            });
            $("#albumDetail").dialog({
                title: "专辑详情",
                width: 300,
                height: 250,
                closed: true,
                modal: true
            });
            $("#addAlbum").dialog({
                title: "添加专辑",
                width: 300,
                height: 200,
                buttons: [{
                    iconCls: "icon-ok",
                    text: "确认",
                    handler: function () {
                        $("#albumForm").form("submit", {
                            url: "${pageContext.request.contextPath}/album/addAlbum",
                            onSubmit: function () {
                                return $("#albumForm").form("validate");
                            },
                            success: function (data) {
                                data = JSON.parse(data);
                                if (data.flag == 1) {
                                    $("#addAlbum").dialog("close");
                                    $.messager.alert("提示框", "添加成功", "info");
                                    $("#album").treegrid("reload");
                                }
                            }
                        });
                    }
                }],
                closed: true,
                modal: true
            });
            $("#addChapter").dialog({
                title: "添加章节",
                width: 300,
                height: 200,
                buttons: [{
                    iconCls: "icon-ok",
                    text: "确认",
                    handler: function () {
                        $("#chapterForm").form("submit", {
                            url: "${pageContext.request.contextPath}/chapter/addChapter",
                            onSubmit: function () {
                                return $("#chapterForm").form("validate");
                            },
                            success: function (data) {
                                data = JSON.parse(data);
                                if (data.flag == 1) {
                                    $("#addChapter").dialog("close");
                                    $.messager.alert("提示框", "添加成功", "info");
                                    $("#album").treegrid("reload");
                                }
                            }
                        });
                    }
                }],
                closed: true,
                modal: true
            });
            $("#musicDialog").dialog({
                title: "音频播放",
                width: 300,
                height: 200,
                closed: true,
                modal: true,
                onClose: function () {
                    $("#music").trigger("pause");
                }
            })
        })
    </script>
</head>
<body>
<table id="album"></table>
<div id="albumDetail">
    <div>
        <table>
            <tr>
                <td>名字:</td>
                <td><input id="titleAlbum" readonly/></td>
            </tr>
            <tr>
                <td>集数:</td>
                <td><input id="amountAlbum" readonly/>
                </td>
            </tr>
            <tr>
                <td>评分:</td>
                <td><input id="scoreAlbum" readonly/>
                </td>
            </tr>
            <tr>
                <td>作者:</td>
                <td><input id="authorAlbum" readonly/></td>
            </tr>
            <tr>
                <td>播音:</td>
                <td><input id="boardcastAlbum" readonly/></td>
            </tr>
            <tr>
                <td>时间:</td>
                <td><input id="publishDateAlbum" readonly/></td>
            </tr>
            <tr>
                <td>简介:</td>
                <td><input id="briefAlbum" readonly/></td>
            </tr>
            <tr>
                <td>图片:</td>
                <td><img width="150px" height="140px" id="albumImgPath" src="" readonly/></td>
            </tr>
        </table>
    </div>
</div>
<div id="addAlbum">
    <form id="albumForm" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <td>名字:</td>
                <td><input id="addAlbumTitle" type="text" name="title" class="easyui-textbox"
                           data-options="required:true,missingMessage:'名字不能为空'"/></td>
            </tr>
            <tr>
                <td>作者:</td>
                <td><input id="addAlbumAuthor" type="text" name="author" class="easyui-textbox"
                           data-options="required:true,missingMessage:'作者不能为空'"/></td>
            </tr>
            <tr>
                <td>播音:</td>
                <td><input id="addAlbumBoardcast" type="text" name="boardcast" class="easyui-textbox"
                           data-options="required:true,missingMessage:'播音不能为空'"/></td>
            </tr>
            <tr>
                <td>简介:</td>
                <td><input id="addAlbumBrief" type="text" name="brief" class="easyui-textbox"
                           data-options="required:true,missingMessage:'简介不能为空'"/></td>
            </tr>
            <tr>
                <td>图片:</td>
                <td><input id="addAlbumImgPath" name="file" class="easyui-filebox"
                           data-options="missingMessage:'路径不能为空'" required="required"></td>
            </tr>
        </table>
    </form>
</div>
<div id="addChapter">
    <form id="chapterForm" method="post" enctype="multipart/form-data">
        <table>
            <tr>
                <input type="hidden" id="albumId" name="albumId">
            </tr>
            <tr>
                <td>名字:</td>
                <td><input id="chapterTitle" type="text" name="title" class="easyui-textbox"
                           data-options="required:true,missingMessage:'名字不能为空'"/></td>
            </tr>
            <tr>
                <td>音频:</td>
                <td><input id="chapterFile" name="file" class="easyui-filebox"
                           data-options="required:true,missingMessage:'音频能为空'"></td>
            </tr>
        </table>
    </form>
</div>
<div id="musicDialog">
    <audio id="music" src="" controls="controls">
    </audio>
</div>
</body>
</html>
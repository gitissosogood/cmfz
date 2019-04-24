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
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/echarts.min.js"></script>
    <script type="text/javascript" src="http://cdn-hangzhou.goeasy.io/goeasy.js"></script>
    <script type="text/javascript">
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));

            $.ajax({
                url: "${pageContext.request.contextPath}/user/count",
                type: "post",
                dataType: "json",
                success: function (data) {
                    // 指定图表的配置项和数据
                    var option = {
                        title: {
                            text: '持名法洲App用户\n近三周用户注册数量'
                        },
                        tooltip: {},
                        legend: {
                            data: ['人数']
                        },
                        xAxis: {
                            data: data.xAxisData,
                        },
                        yAxis: {},
                        series: [{
                            name: '人数',
                            type: 'bar',
                            data: data.seriesData,
                        }]
                    };
                    // 使用刚指定的配置项和数据显示图表。
                    myChart.setOption(option);
                }
            })

        });
        var goEasy = new GoEasy({
            appkey: "BC-21fc0feadbbc4494ba8b0f630be6c016"
        });
        goEasy.subscribe({
            channel: "count",
            onMessage: function (message) {
                //console.log(message.content);
                var myChart = echarts.init(document.getElementById('main'));
                content = JSON.parse(message.content);
                myChart.setOption({
                    xAxis: {
                        data: content.xAxisData
                    },
                    series: [{
                        // 根据名字对应到相应的系列
                        name: '人数',
                        data: content.seriesData
                    }]
                });
            }
        })
    </script>
</head>
<body>
<div id="main" style="width: 600px;height:400px;"></div>
</body>
</html>
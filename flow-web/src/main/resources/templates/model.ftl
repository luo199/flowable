<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache,must-revalidate">  
    <meta http-equiv="expires" content="0">
	<meta http-equiv = "X-UA-Compatible" content ="IE=edge,chrome=1" />
    <meta name="renderer" content="webkit|ie-comp|ie-stand"/> <!--优先级：极速模式，兼容模式，IE模式-->
    <meta content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=0" name="viewport" />
	<title></title>
	<#--<link rel="shortcut icon" type="image/ico" href="${request.contextPath}/static/img/favicon.ico"/>
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/common.css" />
	<link rel="stylesheet" type="text/css" href="${request.contextPath}/static/css/original/index.css" />
	<script src="${request.contextPath}/static/lib/jquery/jquery-2.2.4.min.js"></script>-->
	<#--<script>
	    var contextPath = '${request.contextPath}';
		var cas_host = "http://192.168.50.5:7066";
		var api_host = "http://192.168.50.48:8091";
		var userName = '${Session._const_cas_assertion_.principal.attributes.name}';
		var userId = '${_const_cas_assertion_.principal.attributes.ID}';
		var bureauId = '${_const_cas_assertion_.principal.attributes.bureauID}';
		var bureauName = '${_const_cas_assertion_.principal.attributes.bureauName}';
		var deptId = '${_const_cas_assertion_.principal.attributes.deptID}';
		var userSex = "男";
 	    var cas_prefix_logout = cas_host+"/sso/logout?service=" +api_host + contextPath;
	</script>-->
</head>
<script>
    location.href='${request.contextPath}/models/index.html';
</script>



</html>

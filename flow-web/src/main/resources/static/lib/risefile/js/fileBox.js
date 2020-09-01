//var where = ""; // 会议中的议题条件（议题材料/展现材料）
//var topicType = ""; // 会议中的1＝议题材料，2＝展现材料，3＝会议加载议题材料
//var isArchManager = ""; // 资料中心的权限控制(true=管理权限，false=无管理权限)
//var meetSort = "";
//var fileType = "";// 限制文件类型
var hasInit = false;//是否初始化
//var checkMeeting="";
//var meetingNoticeFileNum;
var isCanDelete=false;

String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}

String.prototype.booleanValue = function() {
	if (this == "true") {
		return true;
	} else {
		return false;
	}
}

function initFileBox(fileboxObject, objInfo) {
	// 重载控件对象
	var boxID = fileboxObject.FILEBOXNAME;
	var divID = fileboxObject.FILEBOXNAME + "DIV";
	var rsID = fileboxObject.FILEBOXNAME + "XML";
	document.all(divID).innerHTML = "";
	var fileOjectHTML = '<object classid="clsid:A99B0DE5-282B-472c-A0C7-53E8182CD474" id="'+boxID+'"'
				+' width="100%" height="100%" CODEBASE="'+webContextPath+'/static/lib/risefile/commons/ocx/ntkofman.4.0.1.2.cab#version=4,0,1,2">\n'
				+'<param name="MakerCaption" value="北京有生博大软件技术有限公司">\n'
				+'<param name="MakerKey" value="C33CA5D43F86E65E3FA1748E7484FF89C1D6FCF5">\n'
				//+'<param name="ProductCaption" value="risesoft">\n'
				//+'<param name="ProductKey" value="98E2DE4FC7794888A15741C8E91FABCC1BC8827E">\n'
				+'<param name="ProductCaption" value="risesoft">  <param name="ProductKey" value="98E2DE4FC7794888A15741C8E91FABCC1BC8827E">\n'
				+'<param name="MaxUploadSize" value="10000000000">\n'
				+'<param name="ViewType" value="0">\n'
				+'<param name="IsPermitAddDelFiles" value="-1">\n'
				+'<param name="DelFileField" value="deletedFileName">\n'
				+'<param name="ScannerPicFileName" value="NTKO扫描文件"><!--默认扫描仪添加文件的文件名前缀 --> '
				+'<param name="ScannerPicType" value="1">   <!--默认扫描仪添加文件的类型  0,TIFF, 1:JPEG,  2:GIF, 3:BMP --> '
				+'<param name="IsAllowSelScannerPicType" value="-1">  <!--是否允许选择文件类型 --> '
				+'<param name="IsCloseScannerUI" value="-1"> <!--扫描完毕之后是否关闭扫描仪--> '
				+'<param name="IsSelScannerIfOnlyOne" value="0"> <!--如果只有一个扫描仪，是否提示选择。--> '
				+'<SPAN STYLE="color:red">不能装载附件管理控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>\n'
			 +'</object>\n';
	document.all(divID).innerHTML = fileOjectHTML;
	filebox = document.all(fileboxObject.FILEBOXNAME);
	filebox.FILEBOXNAME = fileboxObject.FILEBOXNAME;
	filebox.APPNAME = fileboxObject.APPNAME;
	filebox.FILEROOT = fileboxObject.FILEROOT;
	filebox.SAVEMODE = fileboxObject.SAVEMODE;
	filebox.MAJORVERSION = fileboxObject.MAJORVERSION;
	filebox.MAJORVERSIONNAME = fileboxObject.MAJORVERSIONNAME;
	filebox.KEEPMINIMALVERSION = fileboxObject.KEEPMINIMALVERSION;
	filebox.FILTERS = fileboxObject.FILTERS;
	filebox.ISFILTEREXTENDS = fileboxObject.ISFILTEREXTENDS;
	filebox.STREAMHANDLES = fileboxObject.STREAMHANDLES;
	filebox.ISHANDLEEXTENDS = fileboxObject.ISHANDLEEXTENDS;
	filebox.APPINSTGUID = fileboxObject.APPINSTGUID;
	filebox.CONTEXT = fileboxObject.CONTEXT;
	// tag的配置
	var config = "appname=" + filebox.APPNAME;// 使用applicationname，没有用统一的appName,因为与控件有冲突。
	config += "&fileboxname=" + filebox.FILEBOXNAME;
	config += "&fileroot=" + filebox.FILEROOT;
	config += "&savemode=" + filebox.SAVEMODE;
	config += "&majorversion=" + filebox.MAJORVERSION;
	config += "&majorversionname=" + filebox.MAJORVERSIONNAME;
	config += "&isminimalversion=" + filebox.ISMINIMALVERSION;
	config += "&filehandles=" + filebox.FILEHANDLES;
	config += "&streamhandles=" + filebox.STREAMHANDLES;
	config += "&ishandleextends=" + filebox.ISHANDLEEXTENDS;
	config += "&keepminimalversion=" + filebox.KEEPMINIMALVERSION;
	config += "&appInstGUID=" + filebox.APPINSTGUID;
	// app传入context的内容
	var context = "context=" + filebox.CONTEXT;
	filebox.config = config;
	// 文件上传或修改的url
	filebox.commonUrlParams = config + "&" + context;;
	//filebox.url = webContextPath + "/api/instanceAttachment/listByInstanceIdAndAttachementType?instanceId="+objInfo.instanceId+"&attachementType="+objInfo.attachementType;
	/*var jsonObj= JSON.parse(json);
	var attachFile;
	var guid;
	var fileName;
	var fileExtName;
	var fileSize;
	var fileModifiedTimeStr;
	var isAllowEdit;
	var fileVersion;
	var fileCreator;
	var fileCreateDept;
	var fileCreatedTimeStr;
	var url;
	if (jsonObj.length > 0) {
		for ( var i = 0; i < jsonObj.length; i++) {	
			guid = jsonObj[i].guid;
			fileName = jsonObj[i].fileName;
			fileExtName = jsonObj[i].fileExtName;
			fileSize = jsonObj[i].fileSize;
			fileModifiedTimeStr = jsonObj[i].fileModifiedTimeStr;
			isAllowEdit = jsonObj[i].isAllowEdit;
			fileVersion = jsonObj[i].fileVersion;
			fileCreator = jsonObj[i].fileCreator;
			fileCreateDept = jsonObj[i].fileCreateDept;
			fileCreatedTimeStr = jsonObj[i].fileCreatedTimeStr;
			// url=webContextPath+"/components/risefile/default/fileboxAction.jsp?action=download"
			// +"&fileName="+fileName+"&fileGUID="+guid+"&"+config;
			url = "/servlet/RisefileViewData1?fileName=" + fileName
					+ "&fileGUID=" + guid + "&appname=" + filebox.APPNAME;
			attachFile = filebox.AddServerFile(url  + "&isArchManager=" + isArchManager, fileName,parseInt(fileSize), fileCreatedTimeStr, isAllowEdit.booleanValue());
			attachFile.SetCustomData(0, fileVersion);
			attachFile.SetCustomData(1, fileCreator);
			attachFile.SetCustomData(2, fileCreateDept);
			attachFile.SetCustomData(3, fileModifiedTimeStr);
			attachFile.SetCustomData(4, fileExtName);
			filebox.SetColumnVisible(10, false);
		}
	}*/

	// 根据页面编码设置控件属性
	var useUTF8 = (document.charset == "utf-8");
	filebox.IsUseUTF8Data = useUTF8;
	// filebox.ViewType = fileboxObject.VIEWTYPE;
	filebox.ViewType = 2;
	filebox.Statusbar = false;
	filebox.MaxUploadSize = fileboxObject.MAXUPLOADSIZE;// 一次上传不能超过xxM
	filebox.IsConfirmSaveModified = "false";
	// filebox1.DefaultAddFileTypes="*.doc;*.gif;";

	filebox.SetColumnVisible(1, false);
	filebox.SetColumnVisible(2, true);
	filebox.SetColumnVisible(3, false);
	filebox.SetColumnVisible(4, false);
	filebox.SetColumnVisible(5, false);
//	filebox.SetCustomColumnCaption(0, "版本");
	filebox.SetCustomColumnCaption(1, "上传人");
//	filebox.SetCustomColumnCaption(2, "部门");
	filebox.SetCustomColumnCaption(3, "时间");
	filebox.SetColumnVisible(6, false);
	filebox.SetColumnVisible(7, true);
	filebox.SetColumnVisible(8, false);
	filebox.SetColumnVisible(9, true);
	filebox.IsPermitAddDelFiles = fileboxObject.ISPERMITADDDELFILES;
	if (fileboxObject.ISPERMITADDDELFILES == 0) {
		// filebox.Toolbar=false;
		filebox.IsReadOnlyMode = true;  
	}
	hasInit = true;
}
function deleteRiseFile(filebox, attachfile) {
	if (isArchManager == "true") {
		// lianhuiyang:过滤文件名中的%,+,&符号
		var creator = attachfile.GetCustomData(1);//获取上传人
		if(!isCanDelete){
			if(employer!=creator){
				alert("您不能删除别人上传的附件。");
				return;
			}
		}
		var fileName = (attachfile.FileName).replace(/\%/, "%25").replace(/\+/,
				"%2B").replace(/\&/, "%26");
		var str = attachfile.fileURL;
		str = str.substring(str.indexOf("fileGUID={") + 9,
				str.indexOf("}&") + 1);
//		var url = "/api/instanceAttachment/delete" + "&action=delete" + "&fileName=" + fileName
//				+ "&fileGuid=" + str  + "&isArchManager=" + isArchManager;
		var url = webContextPath + window.portPath.deleteAttachementById + "?"+filebox.commonUrlParams+"&attachmentId="+attachfile.CustomFileID;
		var xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
		var post = "";
		xmlhttp.open('POST', url, false);// 使用POST方法打开服务器连接
		xmlhttp.setrequestheader('content-length', post.length);
		xmlhttp.setrequestheader('content-type', 'application/x-www-form-urlencoded');
		xmlhttp.send(post);
		var res = xmlhttp.responseText;
	} else {
		alert("您没有操作权限。");
		return;
	}
}

function addRiseFile(filebox, searchParams) {//上传方法
	if (isArchManager == "true") {
		//var url = filebox.url + "&action=save&isArchManager=" + isArchManager;
		var url = webContextPath + window.portPath.saveOrUpdateAttachement + "?instanceId="+searchParams.instanceId+"&attachementType="+searchParams.attachementType + "&" + filebox.commonUrlParams;
		commitFileBox(filebox, url);
	} else {
		alert("您没有操作权限。");
		return;
	}
}
function updateRiseFiles(filebox) {
	if (!filebox.IsNeedSaveToServer) {
		alert("您没有更改任何文件！");
		return;
	}
	if (isArchManager == "true") {
		var url = filebox.url + "&action=update&isArchManager=" + isArchManager;
		commitFileBox(filebox, url);
	} else {
		alert("您没有操作权限。");
		return;
	}
}

function updateRiseFileszx(filebox) {
	if (!filebox.IsNeedSaveToServer) {
		return;
	}
	if (isArchManager == "true") {
		var url = filebox.url + "&action=update&isArchManager=" + isArchManager;
		commitFileBox(filebox, url);
	} else {
		return;
	}
}
function isReadOnly(fileboxID) {
	var filebox = document.all(fileboxID);
	filebox.IsReadOnlyMode = true;
}
function updateRiseFilesByID(fileboxID) {
	var filebox = document.all(fileboxID);
	updateRiseFiles(filebox);
}

function updateRiseFilesByIDzx(fileboxID) {
	var filebox = document.all(fileboxID);
	updateRiseFileszx(filebox);
}
function fileboxSelectChange(filebox, count) {
	filebox.SELECTCOUNT = count;
	zaixiansave();
}
function doFileCommand(cmdType, IsServerFile, FilePathOrURL, AttachFile,
		filebox) {
	var commandstr = "";
	switch (cmdType) {
	case 0:
		commandstr = "ntkoCmdEdit";
		break;
	case 1:
		commandstr = "ntkoCmdOpen";
		break;
	case 2:
		commandstr = "ntkoCmdPrint";
		break;
	case 3:
		commandstr = "ntkoCmdDelete";
		deleteRiseFile(filebox, AttachFile);
		break;
	case 4:
		commandstr = "ntkoCmdSave";
		break;
	case 5:
		commandstr = "ntkoCmdSaveAll";
		break;
	case 6:
		commandstr = "ntkoCmdOpenFolder";
		break;
	case 7:
		commandstr = "ntkoCmdViewProp";
		break;
	}
}

function beforeFileOpened(cmdType,AttachFile){
	var commandstr = "";
	switch(cmdType)
	{
	case 0:
		commandstr = "ntkoCmdEdit";
		break;
	case 1:
		commandstr = "ntkoCmdOpen";
		break;
	case 2:
		commandstr = "ntkoCmdPrint";
		break;
	case 3:
		commandstr = "ntkoCmdDelete";
		break;
	case 4:
		commandstr = "ntkoCmdSave";
		break;
	case 5:
		commandstr = "ntkoCmdSaveAll";
		break;
	case 6:
		commandstr = "ntkoCmdRename";
		break;	
	case 7:
		commandstr = "ntkoCmdOpenFolder";
		break;
	case 8:
		commandstr = "ntkoCmdViewProp";
		break;										
	}
	//alert("commandstr=" + commandstr +" AttachFile.IsServerFile="+AttachFile.IsServerFile + "  AttachFile.FilePath=" + AttachFile.FilePath);
	//alert(AttachFile.CustomFileID);
	window.open("/riseoffice/pdf/readpdfFile.jsp?fileGuid="+AttachFile.CustomFileID);
	CancelLastCommand = true;
	return false;
}

function afterCommitFileBox(retStr, ErrCode) {
	if (ErrCode == 0) {
		var fileboxID = retStr.substring(0, retStr.indexOf(";"));
		fileboxID = fileboxID.trim();
		var msg = retStr.substring(retStr.indexOf(";") + 1, retStr.length);

		var fileboxObjectName = fileboxID + "Object";
		var rsID = fileboxID + "XML";
		msg = JSON.parse(msg);
		if (msg.errorStatus == 1) {
			showError(fileboxID, JSON.parse(msg).message);
		} else {//成功
			json=msg.data;
			if(hasInit){
				eval("refreshFileBox(" + fileboxObjectName + ")");
			}else{
				eval("initFileBox(" + fileboxObjectName + ")");
			}
		}
	} else if (ErrCode == 10){ // 用户取消
		alert(retStr);
	} else if (ErrCode == 11){ // 超时终止
		alert(retStr);
	} else {
		newwin = window
				.open(
						"",
						"_blank",
						"left=200,top=200,width=400,height=200,status=0,toolbar=0,menubar=0,location=0,scrollbars=0,resizable=0",
						false);
		newdoc = newwin.document;
		newdoc.open();
		newdoc
				.write("<center><hr>"
						+ retStr
						+ "<hr><input type=button VALUE='关闭窗口' onclick='window.close()'></center>");
		// newdoc.close();
	}
}

function showVersion(fileboxID, searchParams) {
	var filebox = document.all(fileboxID);
	eval("var fileboxObject=" + fileboxID + "Object");
	if (filebox.IsNeedSaveToServer) {// 如果需要更新，更新
		//var url = filebox.url;// + "&action=update" + "&isArchManager=" + isArchManager;
		var url = webContextPath + window.portPath.getAttachementByInstanceIdAndTypeList + "?instanceId="+searchParams.instanceId+"&attachementType="+searchParams.attachementType+"&"+filebox.commonUrlParams;
		if (fileboxObject.ALLVERSION) {
			url += "&isAllVersion=false";
			fileboxObject.ALLVERSION = false;
			fileboxObject.ISPERMITADDDELFILES = -1;
		} else {
			url += "&isAllVersion=true";
			fileboxObject.ALLVERSION = true;
			fileboxObject.ISPERMITADDDELFILES = 0;
			url += "&isAllVersion=false";
			fileboxObject.ALLVERSION = false;
			fileboxObject.ISPERMITADDDELFILES = -1;
		}
		commitFileBox(filebox, url);
	} else {// 不需要更新，直接显示版本
		var url = webContextPath + window.portPath.getAttachementByInstanceIdAndTypeList + "?instanceId="+searchParams.instanceId+"&attachementType="+searchParams.attachementType+"&"+filebox.commonUrlParams;//+ "&action=showVersion" + "&isArchManager=" + isArchManager;
		/*if (fileboxObject.ALLVERSION) {
			// url+="&isAllVersion=false";
			// fileboxObject.ALLVERSION=false;
			// fileboxObject.ISPERMITADDDELFILES=-1;
		} else {
			// url+="&isAllVersion=true";
			// fileboxObject.ALLVERSION=true;
			// fileboxObject.ISPERMITADDDELFILES=0;
		}*/
		// var xmlhttp = new ActiveXObject('Microsoft.XMLHTTP');
		var xmlhttp = null;
		if (!!window.ActiveXObject || "ActiveXObject" in window) { // 判断是否支持ActiveX控件
			xmlhttp = new ActiveXObject('Microsoft.XMLHTTP'); // 通过实例化ActiveXObject的一个新实例来创建XMLHTTPRequest对象
		} else if (window.XMLHttpRequest) { // 判断是否把XMLHTTPRequest实现为一个本地javascript对象
			xmlhttp = new XMLHttpRequest(); // 创建XMLHTTPRequest的一个实例（本地javascript对象）
		}
		var post = "";
		xmlhttp.open('POST', url, false);// 使用POST方法打开服务器连接
		xmlhttp.setRequestHeader('content-length', post.length);
		xmlhttp.setRequestHeader('content-type', 'application/x-www-form-urlencoded');
		xmlhttp.send(post);
		var res = xmlhttp.responseText;
		res = fileboxID + ";" + res;
		afterCommitFileBox(res, 0);
	}

}

function commitFileBox(filebox, url) {
	var a = filebox.BeginSaveToURL(url, "attachFile", // 文件输入域名称,可任选,不与其他<input type=file name=..>的name部分重复即可
	"", // 可选的其他自定义数据－值对，以&分隔。如：myname=tanger&hisname=tom,一般为空
	filebox.FILEBOXNAME, // 控件的智能提交功能可以允许同时提交选定的表单的所有数据.此处可使用id或者序号
	0);
}

function showError(fileboxid, error) {
	var errorHTML = "<div align='center' style='font-size: 12px;color:red'><img src='"
			+ webContextPath
			+ "/static/lib/risefile/commons/image/error.gif' border=0 alt='文件框错误！请与系统管理员联系！'><br>"
			+ "文件框报错：" + error + "<div>"
	document.all(fileboxid + "DIV").innerHTML = errorHTML;
}

function addFileCmd(fileboxID) {
	var filebox = document.all(fileboxID);
	filebox.AddLocalFile("", false, false);
}

function refreshFileBox(fileboxObject){
	var filebox = document.all(fileboxObject.FILEBOXNAME);
	filebox.Reset();
	var jsonObj= json || null; //不再使用xml数据，改用json传递,2014-2-26日修改  
	var attachFile;
	var guid;
	var fileName;
	var fileExtName;
	var fileSize;
	var fileModifiedTimeStr;
	var isAllowEdit;
	var fileVersion;
	var fileCreator;
	var fileCreateDept;
	var fileCreatedTimeStr;
	var url;
	if (jsonObj && jsonObj.length > 0) {
		for ( var i = 0; i < jsonObj.length; i++) {
			guid = jsonObj[i].attachmentId;
			fileName = jsonObj[i].fileName;
			fileExtName = jsonObj[i].attachmentExtName;
			fileSize = jsonObj[i].attachmentSize;
			fileModifiedTimeStr = window.common.dateFormat(jsonObj[i].updateDatetime, 'yyyy-MM-dd');
			isAllowEdit = 'true';
			fileVersion = jsonObj[i].step;
			fileCreator = jsonObj[i].creatorName;
			fileCreateDept = jsonObj[i].deptName;
			fileCreatedTimeStr = jsonObj[i].createDatetime;
			url = webContextPath + window.portPath.downloadAttachementById + "?fileId=" + jsonObj[i].primaryId;
			attachFile = filebox.AddServerFile(url, fileName,fileSize, fileModifiedTimeStr, true);
			if(attachFile != null){
			attachFile.SetCustomData(0, jsonObj[i].primaryId);
			attachFile.SetCustomData(1, fileCreator);
			attachFile.SetCustomData(2, fileCreateDept);
			attachFile.SetCustomData(3, fileModifiedTimeStr);
			attachFile.SetCustomData(4, jsonObj[i].instanceId);
			attachFile.CustomFileID=guid;
			filebox.SetColumnVisible(10, false);
			}
		}
		//添加附件后刷新数量
		if(filebox.APPNAME == 'repository'){//附件
			$("#attachment .data-num", window.parent.document).text(jsonObj.length);
		}else if(filebox.APPNAME == 'wenjianbeijing'){
			$("#beijing .data-num", window.parent.document).text(jsonObj.length);
		}
	}
}
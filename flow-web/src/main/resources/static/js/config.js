/*常量*/
window.CONST = {
	orgName: '深圳市光明区',
	orgDeptId: '{AC1063FB-FFFF-FFFF-B416-42B400000006}',//区委区政府 办文科ID
	orgBureauId: '{B0FF7BE0-B4C0-E940-A1FA-9C27F19D884B}',//区委区政府ID
};
/*
**请求地址集合
*/
/*请求头类型*/
window.conType = {
    conTyForm: 'application/x-www-form-urlencoded;charset=UTF-8',
    conTyMultipart: 'multipart/form-data',
    conTyJson: 'application/json;charset=UTF-8'
};
/*请求地址*/
window.portPath = {
	getNavBarList: '/api/index/listResourceFromCommonApp',//头部菜单
	getLeftMenuList: '/api/index/listResource',//左侧菜单
	getAllBureausList: '/api/org/department/listAllBureaus',//获取局办
	getAllDeptList: '/api/org/department/listAllDept',//获取部门
	getDeptListByPersonId: '/api/org/department/listDeptByPersonId',//获取当前人所有岗位所在的局办
    saveBusinessDefine: '/api/admin/businessDefine/save',//保存业务定义
    updateBusinessDefine: '/api/admin/businessDefine/modify',//修改业务定义
    removeBusinessDefine: '/api/admin/businessDefine/remove',//删除业务定义
    getBusinessDefineDetatils: '/api/admin/businessDefine/get',//获取业务定义详情
    queryBusinessDefineList: '/api/admin/businessDefine/query',//查询业务定义列表
    saveFormDefine: '/api/admin/formDefine/save',//保存表单定义
    updateFormDefine: '/api/admin/formDefine/modify',//修改表单定义
    removeFormDefine: '/api/admin/formDefine/remove',//删除表单定义
    getFormDefineDetatils: '/api/admin/formDefine/get',//获取表单定义详情
    queryFormDefineList: '/api/admin/formDefine/query',//查询表单定义列表
    queryFormList: '/api/formDefine/listForm',//通过实例id获取需要打开的表单
    uploadFormSource: '/api/admin/formDefine/uploadFormSource',//上传表单HTML
    getFormSource: '/api/formDefine/getFormSource',//获取表单HTML
    queryTableNamesList: '/api/admin/formDefine/tableNames',//查询数据库表名
    getFormElementsByFormId: '/api/admin/formElements/listByFormId',//通过表单ID倒序获取表单元素列表
    getFormElementsByFormIdAndEleEgName: '/api/admin/formElements/getByFormIdAndEleEgName',//通过表单ID和元素英文名获取表单元素列表
    saveFormElements: '/api/admin/formElements/save',//保存表单元素
    updateFormElements: '/api/admin/formElements/modify',//修改表单元素
    removeFormElements: '/api/admin/formElements/remove',//删除表单元素
    getFormElementsDetails: '/api/admin/formElements/get',//获取表单元素详情
    queryJdbcTypesList: '/api/admin/formElements/jdbcTypes',//查询数据库字段类别
    saveTextTemplate: '/api/admin/textTemplate/save',//保存模板
    updateTextTemplate: '/api/admin/textTemplate/modify',//修改模板
    getTextTemplateByBusinessId: '/api/textTemplate/listByBusinessId',//通过业务ID获取模板表
    getTemplateByBusinessIdAndType: '/api/textTemplate/listByBusinessIdAndTemplateType',//通过业务ID和模板类型模板表
    queryTemplateList: '/api/admin/textTemplate/query',//查询模板表
    getTextTemplateDetatils: '/api/admin/textTemplate/get',//获取模板详情
    removeTextTemplateById: '/api/admin/textTemplate/remove',//删除模板
    saveTextTemplateContent: '/api/admin/textTemplateContent/save',//上传模板文件
    getTextTemplateContent: '/api/admin/textTemplateContent/get',//获取模板内容
    saveRedHeader: '/api/admin/redHeader/save',//保存红头模板
    updateRedHeader: '/api/admin/redHeader/modify',//修改红头模板
    getRedHeaderDetatils: '/api/admin/redHeader/get',//获取红头模板详情
    removeRedHeaderById: '/api/admin/redHeader/remove',//删除红头模板
    getRedHeaderByBureauGuidAndDeptGuid: '/api/redHeader/listByBureauGuidAndDeptGuid',//通过局办GUID和科室GUID获取红头模板
    queryAllRedHeaderList: '/api/redHeader/queryAll',//获取红头模板
    getTemplateBookmarkByTemplateId: '/api/admin/textTemplateBookmark/getTemplateBookmarkByTemplateID',//通过模板ID获取模板文件书签
    getBookmarkByTemplateId: '/api/textTemplateBookmark/listByTemplateId',//通过模板id获取书签列表
    updateBookmark: '/api/admin/textTemplateBookmark/modify',//修改书签设置
    getBookmarkDetails: '/api/admin/textTemplateBookmark/get',//通过书签id获取书签详情
    queryAllFormList: '/api/formDefine/listAll',//查询所有表单定义表
    getFiledsByTablename: '/api/admin/formDefine/tableFileds',//根据表名查询表字段
    saveCommentTemplate: '/api/admin/commentType/save',//保存模板
    updateCommentTemplate: '/api/admin/commentType/modify',//修改模板
    queryCommentTemplateList: '/api/admin/commentType/query',//查询模板表
    getCommentTemplateDetatils: '/api/admin/commentType/get',//获取模板详情
    removeCommentTemplateById: '/api/admin/commentType/remove',//删除模板
    getMenuResourceIds: '/api/admin/buttonDefine/getMenuResourceIDs',//获取按钮资源ID 
    saveMenuResourceId: '/api/admin/businessDefine/saveMenuResourceID',//确定选择按钮资源ID 
    saveButtonFilterBeanName: '/api/admin/businessDefine/saveButtonFilterBeanName',//保存选择按钮过滤
    getCustomButtonFilterNames: '/api/admin/buttonDefine/getCustomButtonFilterNames',//获取所有的自定义按钮过滤名称
    getButtonResource: '/api/admin/buttonDefine/getButtonResource',//获取所有的自定义按钮资源
    getButtonOperator: '/api/admin/buttonDefine/getButtonOperator',//获取获取按钮定义配置
    saveButtonOperator: '/api/admin/buttonDefine/saveButtonOperator',//保存按钮定义配置
    getopinionListByBusinessId:'/api/admin/commentType/listByBusinessId',//通过业务id  查询意见列表
    setFormIdByCommentTypeIds:'/api/admin/commentType/setFormIdByCommentTypeIds',//把commentTypeIds的对象设置formId
    getEleValidListByElementId:'/api/admin/formEleValid/listByElementId', //根据ElementId 获取验证列表
    saveEleValidForm:'/api/admin/formEleValid/save',//插入 元素验证表
    getPermissionByElementId:'/api/admin/formElePerm/listByElementId',//获取元素ID获取字段许可记录的集合
    savePermission:'/api/admin/formElePerm/save',//插入元素许可记录
    updatePermission:'/api/admin/formElePerm/modify',//修改元素许可记录
    getPermissionByPermissionId:'/api/admin/formElePerm/get',//通过 permissionId 获取详情 
    removePermissionByPermissionId:'/api/admin/formElePerm/remove',//通过 permissionId 删除数据
    getlistAllRoles:'/api/index/listAllRoles',// 根据登录人权限获取左侧资源菜单

    getListUserParentNodes:'/api/platform/user/parentNodes',//获取人员树全部机构节点
    getListUserFindByPid:'/api/platform/user/findByPid',//点击父节点查找子节点

    /*--------------------流程设计(自定义)-------------------------------*/
    processDesignList: '/api/process/page',//流程定义列表
    checkModelKeyExist:'/api/process/findByModelKey',//检验流程key值是否存在
    findModelById:'/api/process/findById',//根据id查询model
    /*--------------------流程管理(自定义)-------------------------------*/
    processManageList: '/api/process/manage/definition/page',//流程定义列表
    processManageActivate: '/api/process/manage/activate',//激活流程
    processManageSuspend: '/api/process/manage/suspend',//挂起流程
    processManageXmlDeploy: '/api/process/manage/xmlDeploy',//导入流程并部署
    processManageImage: '/api/draw/process/png/base64',//流程图
    processManageDelete: '/api/process/manage/delete',//删除流程部署
    ActivateProcessList:'/api/process/manage/listActivateProcess',//查询所有已部署并且激活的流程
    
    /*--------------------流程设计(引用flowable)-------------------------------*/
    creatProcessDesign: '/app/rest/models',//新增流程
    processDesignPage:'/static/models/index.html#/editor/',//流程设计页面
    deployProcessDesign:'/api/process/deploy',//部署流程
    deleteProcessDesign:'/api/process/deleteById',//删除流程
    cloneProcessDesign:'/app/rest/models/',//复制流程（后面拼接  id/clone）

    /*--------------------业务定义-------------------------------*/
    businessBaseList: '/api/business/page',//业务定义列表
    findBusinessBaseByCode:'/api/business/findByCode',//根据code查询业务
    updateBusinessStatus:'/api/business/updateStatus',//删除业务-伪删除(修改状态)
    checkBusinessCodeExist:'/api/business/findByBusinessCode',//校验code是否存在
    createbusinessBase:'/api/business/createBusiness',//新增业务定义
    updatebusinessBase:'/api/business/updateBusiness',//修改业务定义
    cataLogList:'/api/businessCatalog/childListCataLog',//业务类别列表
    querycataLogList:'/api/businessCatalog/page',//分页查询业务类别列表
    checkCataLogCodeExist:'/api/businessCatalog/findByCataLogCode',//校验code是否存在
    checkCataLogPcodeExist:'/api/businessCatalog/findByCataLogPcode',//校验pcode是否存在
    createCataLog:'/api/businessCatalog/createBusinessCatalog',//新增业务类别
    findCataLogByCode:'/api/businessCatalog/findByCode',//根据code查询分类
    findChildCataLogList:'/api/businessCatalog/childListCataLog',//查询该节点下的子节点列表
    updateCataLog:'/api/businessCatalog/updateCataLog',//修改分类
    checkBusinessByCatacode:'/api/business/checkBusinessByCatacode',//查询该分类下是否有业务
    updateCataLogStatus:'/api/businessCatalog/updateStatus',//删除分类-伪删除(修改状态)
    saveChildCataLogList:'/api/businessCatalog/saveChildCataLogList',//保存子节点列表
    findPcodeByCode:'/api/businessCatalog/findPcodeByCode',//根据编码查询父节点编码
    
    /*--------------------业务配置-------------------------------*/
    commonViewsPage: '/api/business/commPage/page', //通用页面--分页查询
    commonViewsSelect: '/api/business/commPage/selectList', //通用页面--不分页查询
    commonViewsFindOne: '/api/business/commPage/findOne', //通用页面--按主键查询
    commonViewsSave: '/api/business/commPage/save', //通用页面--保存
    commonViewsDelete: '/api/business/commPage/deleteById', //通用页面--按主键删除

    commonActionsPage: '/api/business/commAction/page',//通用按钮--分页查询
    commonActionsList:'/api/business/commAction/actionsList',//通用按钮--用在选择按钮时下拉框获取全部按钮名
    commonActionsSave: '/api/business/commAction/save',//通用按钮--保存按钮
    commonActionsFindOne: '/api/business/commAction/findOne', //通用按钮--查找单个按钮
    commonActionsDelete: '/api/business/commAction/deleteById', //通用按钮--删除按钮

    /*--------------------人员配置-------------------------------*/
    processExecutorPage: '/api/business/manage/executors/', //通用页面--分页查询
    //commonViewsSelect: '/api/business/manage/selectList', //通用页面--不分页查询
    processExecutorFindOne: '/api/business/manage/executor', //通用页面--按主键查询
    processExecutorSave: '/api/business/manage/executor/save', //通用页面--保存
    cprocessExecutorDelete: '/api/business/manage/executor/delete/', //通用页面--按主键删除


    /*--------------------时限配置-------------------------------*/
    processLimitPage:'/api/business/manage/timeLimit/',//流程时限查询
    processLimitSave:'/api/business/manage/limit/save',//保存流程时限
    processLimitDelete:'/api/business/manage/limit/deleteByTaskId',//按流程id删除时限
    processLimitByprocessId:'/api/business/manage/limit/',//根据流程id获取信息



    /*--------------------按钮配置-------------------------------*/
    processActionsPage:'/api/business/manage/actions/',
    processActionsSave:'/api/business/manage/actions/save',
    processActionsDelete:'/api/business/manage/actions/deleteById',

    /*--------------------表单定义-------------------------------*/
    formDefPage: '/api/form/page', //表单定义页面--分页查询
    formDefSave:'/api/form/save',//保存基础表单信息
    formDefFindOne:'/api/form/getById',//根据主键查询
    formDefDelete:'/api/form/delete',//根据主键删除
    formDefFindAll:'/api/form/findAll',

    /*--------------------视图配置-------------------------------*/
    processViewsPage:'/api/business/manage/views/',
    processViewsSave:'/api/business/manage/views/save',
    processViewsDelete:'/api/business/manage/views/deleteByTaskId',

    /*--------------------请休假表单-------------------------------*/
    holidayManageList:'/api/holiday/manage',
    startHolidayProcess:'/api/holiday/manage/startHolidayProcess',
    holidayApproveProcess:'/api/holiday/manage/holidayApproveProcess',
    holidayFinishProcess:'/api/holiday/manage/holidayFinish',
    holidayRejectProcess:'/api/holiday/manage/holidayReject',


    holidayFormSave:'/api/business/manage/HolidayForm/save',
    holidayFormPage:'/api/business/manage/HolidayForm/page',
    holidayFormApprovePage:'/api/business/manage/HolidayForm/approvePage',
    holidayFormFindOne:'/api/business/manage/HolidayForm/findOne',
    holidayFormDeleteById:'/api/business/manage/HolidayForm/deleteById',
    getNextExecutor:'/api/business/manage/HolidayForm/getNextExecutor',
    saveExecutorDetail:'/api/business/manage/executorDetail/save',







};

<!DOCTYPE html>
<#escape x as x?html><html>
<head>
<title><#if task.new>${action.getText('create')}<#else>${action.getText('edit')}</#if>${action.getText('task')}</title>
</head>
<body>
<@s.form action="${actionBaseUrl}/save" method="post" cssClass="ajax form-horizontal sequential_create">
	<#if !task.new>
		<@s.hidden name="task.id" />
	</#if>
	<@s.textfield label="%{getText('taskName')}" name="task.taskName" cssClass="required checkavailable" cssStyle="width:350px;"/>
	<@s.textfield label="%{getText('host')}" name="task.host" cssClass="required checkavailable" cssStyle="width:350px;"/>
	<@s.checkboxlist label="%{getText('serverMonitorType')}(多选)" name="task.serverMonitors" list="@com.wao.itil.model.enums.ServerMonitorType@values()" listKey="name" listValue="displayName" cssClass="required custom"/>
	<@s.radio label="%{getText('monitorFrequenceType')}(单选)" name="task.monitorFrequenceType" cssClass="required custom" list="@com.wao.itil.model.enums.MonitorFrequenceType@values()" listKey="name" listValue="displayName"/>
    <@s.radio label="%{getText('sequenceAlarmType')}(单选)" name="task.sequenceAlarmType" cssClass="required custom" list="@com.wao.itil.model.enums.SequenceAlarmType@values()" listKey="name" listValue="displayName"/>
    <@s.radio label="%{getText('alarmNoticeType')}(单选)" name="task.alarmNoticeType" cssClass="required custom" list="@com.wao.itil.model.enums.AlarmNoticeType@values()" listKey="name" listValue="displayName"/>
    <@s.checkbox label="%{getText('templatable')}" name="task.templatable" cssClass="custom"/>
    <@s.textfield label="%{getText('templateName')}" name="task.templateName" cssClass="custom"/>    
    <@s.checkbox label="%{getText('successExecuted')}" name="task.successExecuted" cssClass="custom"/>
    
    <div class="control-group listpick" data-options="{'url':'<@url value="/user/pick?columns=name,username"/>','cache':false}">
    <@s.hidden cssClass="listpick-id" name="task.user.id" id="task-user" class="listpick-id required excludeIfNotEdited"/>
    <label class="control-label" for="task">用户</label>
    <div class="controls">
    <span class="listpick-name"><#if task.user??>${task.user.name}</#if></span>
    </div>
    </div>
    
	<@s.submit value="%{getText('save')}" />
</@s.form>
</body>
</html></#escape>
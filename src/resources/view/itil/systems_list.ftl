<!DOCTYPE html>
<#escape x as x?html><html>
<head>
<title>${action.getText('systems')}${action.getText('list')}</title>
</head>
<body>
<#assign columns={"task.host":{},"osName":{},"linuxDistro":{},"platform":{},"osVersion":{},"createDate":{}}>

<#assign actionColumnButtons='
<button type="button" class="btn" data-view="view" data-windowoptions="{\'width\':\'950px\'}">${action.getText("view")}</button>
'>

<#assign bottomButtons='
<button type="button" class="btn reload">${action.getText("reload")}</button>
<button type="button" class="btn more">${action.getText("more")}</button>
'>

<@richtable entityName="systems" columns=columns actionColumnButtons=actionColumnButtons bottomButtons=bottomButtons celleditable=false deletable=false searchable=true />

</body>
</html></#escape>
<table cellpadding="0" cellspacing="0" border="0" class="comment-tb" id="${commentType.commentTypeId}">
		<tr>
		    <td height="36"><span class="span-th">${commentType.commentTypeChName}</span></td>
		</tr>
		<tr>
		    <td>
		      <#if (commentList?? && commentList?size > 0)>
		      <#list commentList! as comment> 
		        <TABLE class="w100">
				   <TR> 
				     <TD>${comment.commentContent!}
				     	<#if comment.editable>
				         <div class="edit-comment" onclick="openCommentEditDialog('update', '${isFirstCommentType!?string ("true","false")}', '${comment.commentId}')"><img title="修改意见" src="${contextName}/static/img/original/comment-edit.png"><span class="edit-text">修改</span></div>
				     	</#if>
				     </TD>
				  </TR>  
				  <TR>
				     <TD align="right">${comment.deptName}&nbsp;${comment.personName}&nbsp;${comment.commentDate?string('yyyy-MM-dd HH:mm:ss')}
				     </TD>
				  </TR>
				</TABLE>
			   </#list>
			   </#if>
			 </td>
		</tr>
		<tr>
			<td>
				<#if isWriteable>
				  <div id="bitou" class="bitou" onclick="openCommentEditDialog('add', '${isFirstCommentType!?string ("true","false")}', '${instance.instanceId}','${commentType.commentTypeId}')">意见填写</div>
				</#if>
			</td>
		</tr>
</table>
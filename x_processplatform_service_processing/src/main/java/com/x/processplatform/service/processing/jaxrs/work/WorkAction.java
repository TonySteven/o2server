package com.x.processplatform.service.processing.jaxrs.work;

import com.google.gson.JsonElement;
import com.x.base.core.project.annotation.JaxrsDescribe;
import com.x.base.core.project.annotation.JaxrsMethodDescribe;
import com.x.base.core.project.annotation.JaxrsParameterDescribe;
import com.x.base.core.project.http.ActionResult;
import com.x.base.core.project.http.EffectivePerson;
import com.x.base.core.project.http.HttpMediaType;
import com.x.base.core.project.jaxrs.ResponseFactory;
import com.x.base.core.project.jaxrs.StandardJaxrsAction;
import com.x.base.core.project.logger.Logger;
import com.x.base.core.project.logger.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * 工作流相关接口
 *
 * @author steven
 * &#064;date  2023/10/16
 */
@Path("work")
@JaxrsDescribe("工作")
public class WorkAction extends StandardJaxrsAction {

	/**
	 * logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(WorkAction.class);

	/**
	 * 创建一个处于start状态的工作.
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param processId     process id
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "创建一个处于start状态的工作.", action = ActionCreate.class)
	@POST
	@Path("process/{processId}")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void create(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("流程标识") @PathParam("processId") String processId, JsonElement jsonElement) {
		ActionResult<ActionCreate.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionCreate().execute(effectivePerson, processId, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, jsonElement);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * 赋值创建一个处于start状态的work.
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "赋值创建一个处于start状态的work.", action = ActionAssignCreate.class)
	@POST
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void assignCreate(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			JsonElement jsonElement) {
		ActionResult<ActionAssignCreate.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionAssignCreate().execute(effectivePerson, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, jsonElement);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * 流转一个流程实例.
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "流转一个流程实例.", action = ActionProcessing.class)
	@PUT
	@Path("{id}/processing")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void processing(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id, JsonElement jsonElement) {
		ActionResult<ActionProcessing.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionProcessing().execute(effectivePerson, id, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, jsonElement);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * 删除单个工作.
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 */
	@JaxrsMethodDescribe(value = "删除单个工作.", action = ActionDelete.class)
	@DELETE
	@Path("{id}")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void delete(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id) {
		ActionResult<ActionDelete.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionDelete().execute(effectivePerson, id);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, null);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * 调度
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 * @param activityId    activity id
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "调度.", action = ActionReroute.class)
	@PUT
	@Path("{id}/reroute/activity/{activityId}/activitytype/{activityType}")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void reroute(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id,
			@JaxrsParameterDescribe("活动标识") @PathParam("activityId") String activityId, JsonElement jsonElement) {
		ActionResult<ActionReroute.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionReroute().execute(effectivePerson, id, activityId, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, null);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * 指定文件增加一个副本.
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "指定文件增加一个副本.", action = ActionAddSplit.class)
	@PUT
	@Path("{id}/add/split")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void addSplit(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id, JsonElement jsonElement) {
		ActionResult<List<ActionAddSplit.Wo>> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionAddSplit().execute(effectivePerson, id, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, null);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * 回滚指定的工作到指定的workLog.
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "回滚指定的工作到指定的workLog.", action = ActionRollback.class)
	@PUT
	@Path("{id}/rollback")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void rollback(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id, JsonElement jsonElement) {
		ActionResult<ActionRollback.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionRollback().execute(effectivePerson, id, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, null);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * 添加人工环节处理人,不会自动产生,需要processing之后才会执行.
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "添加人工环节处理人,不会自动产生,需要processing之后才会执行.", action = ActionManualAppendIdentity.class)
	@PUT
	@Path("{id}/manual/append/identity")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void manualAppendIdentity(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id, JsonElement jsonElement) {
		ActionResult<ActionManualAppendIdentity.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionManualAppendIdentity().execute(effectivePerson, id, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, null);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * 删除滞留的草稿工作.
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 */
	@JaxrsMethodDescribe(value = "删除滞留的草稿工作.", action = ActionDeleteDraft.class)
	@DELETE
	@Path("{id}/draft")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void deleteDraft(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id) {
		ActionResult<ActionDeleteDraft.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionDeleteDraft().execute(effectivePerson, id);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, null);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * 指定Work运行映射.
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 */
	@JaxrsMethodDescribe(value = "指定Work运行映射.", action = ActionProjection.class)
	@GET
	@Path("{id}/projection")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void projection(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id) {
		ActionResult<ActionProjection.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new ActionProjection().execute(effectivePerson, id);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, null);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * "V2_调度
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "V2_调度.", action = V2Reroute.class)
	@PUT
	@Path("v2/{id}/reroute")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void V2Reroute(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id, JsonElement jsonElement) {
		ActionResult<V2Reroute.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new V2Reroute().execute(effectivePerson, id, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, jsonElement);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * V2_撤回
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "V2_撤回.", action = V2Retract.class)
	@PUT
	@Path("v2/{id}/retract")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void V2Retract(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id, JsonElement jsonElement) {
		ActionResult<V2Retract.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new V2Retract().execute(effectivePerson, id, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, jsonElement);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}

	/**
	 * V2_回滚
	 *
	 * @param asyncResponse async response
	 * @param request       request
	 * @param id            id
	 * @param jsonElement   json element
	 */
	@JaxrsMethodDescribe(value = "V2_回滚.", action = V2Rollback.class)
	@PUT
	@Path("v2/{id}/rollback")
	@Produces(HttpMediaType.APPLICATION_JSON_UTF_8)
	@Consumes(MediaType.APPLICATION_JSON)
	public void V2Rollback(@Suspended final AsyncResponse asyncResponse, @Context HttpServletRequest request,
			@JaxrsParameterDescribe("工作标识") @PathParam("id") String id, JsonElement jsonElement) {
		ActionResult<V2Rollback.Wo> result = new ActionResult<>();
		EffectivePerson effectivePerson = this.effectivePerson(request);
		try {
			result = new V2Rollback().execute(effectivePerson, id, jsonElement);
		} catch (Exception e) {
			logger.error(e, effectivePerson, request, jsonElement);
			result.error(e);
		}
		asyncResponse.resume(ResponseFactory.getEntityTagActionResultResponse(request, result));
	}
}
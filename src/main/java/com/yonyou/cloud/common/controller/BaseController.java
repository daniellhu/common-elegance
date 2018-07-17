package com.yonyou.cloud.common.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.cloud.common.annotation.YcApi;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.service.BaseService;
import com.yonyou.cloud.common.service.utils.PageQuery;

/**
 * 基础controller
 * 
 * 默认实现增删改查
 * 
 * @author BENJAMIN
 * 
 */
public class BaseController<Service extends BaseService,Entity> {
    @Autowired
    protected HttpServletRequest request;
    
    @Autowired
    protected Service baseService;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    @YcApi
    public RestResultResponse<Entity> add(@RequestBody Entity entity){
        baseService.insertSelective(entity);
        return new RestResultResponse<Entity>().success(true);
    }
    
    
    
    @RequestMapping(value = "query",method = RequestMethod.GET)
    @ResponseBody
    @YcApi
    public RestResultResponse<Entity> getByEntity(@RequestParam Map<String, Object> params){
    	
        return new RestResultResponse<Entity>().success(true).data(baseService.selectByQuery(params));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    @YcApi
    public RestResultResponse<Entity> get(@PathVariable(value="id") int id){
        return new RestResultResponse<Entity>().success(true).data(baseService.selectById(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    @YcApi
    public RestResultResponse<Entity> update(@RequestBody Entity entity){
        baseService.updateSelectiveById(entity);
        return new RestResultResponse<Entity>().success(true);
    }
    
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    @YcApi
    public RestResultResponse<Entity> remove(@PathVariable int id){
        baseService.deleteById(id);
        return new RestResultResponse<Entity>().success(true);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    @YcApi
    public RestResultResponse<List<Entity>> all(){
        return new RestResultResponse<Entity>().success(true).data(baseService.selectListAll());
    }
    
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    @YcApi
    public PageResultResponse<Entity> list(@RequestParam(required=false) Map<String, Object> params){
        //查询列表数据
        PageQuery query = new PageQuery(params);
        return baseService.selectByQuery(query);
    }
    
    public String getCurrentUserName(){
        String authorization = request.getHeader("Authorization");
        return new String(Base64Utils.decodeFromString(authorization));
    }
}

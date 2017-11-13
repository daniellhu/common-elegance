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

import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.beans.RestResultResponse;
import com.yonyou.cloud.common.service.BaseService;
import com.yonyou.cloud.common.service.utils.PageQuery;

/**
 * 基础controller
 * 
 * 默认实现增删改查
 * 
 */
public class BaseController<Service extends BaseService,Entity> {
    @Autowired
    protected HttpServletRequest request;
    
    @Autowired
    protected Service baseBiz;

    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public RestResultResponse<Entity> add(@RequestBody Entity entity){
        baseBiz.insertSelective(entity);
        return new RestResultResponse<Entity>().success(true);
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    @ResponseBody
    public RestResultResponse<Entity> get(@PathVariable int id){
        return new RestResultResponse<Entity>().success(true).data(baseBiz.selectById(id));
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.PUT)
    @ResponseBody
    public RestResultResponse<Entity> update(@RequestBody Entity entity){
        baseBiz.updateSelectiveById(entity);
        return new RestResultResponse<Entity>().success(true);
    }
    @RequestMapping(value = "/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public RestResultResponse<Entity> remove(@PathVariable int id){
        baseBiz.deleteById(id);
        return new RestResultResponse<Entity>().success(true);
    }

    @RequestMapping(value = "/all",method = RequestMethod.GET)
    @ResponseBody
    public RestResultResponse<List<Entity>> all(){
        return new RestResultResponse<Entity>().success(true).data(baseBiz.selectListAll());
    }
    
    
    @RequestMapping(value = "/page",method = RequestMethod.GET)
    @ResponseBody
    public PageResultResponse<Entity> list(@RequestParam Map<String, Object> params){
        //查询列表数据
        PageQuery query = new PageQuery(params);
        return baseBiz.selectByQuery(query);
    }
    
    
    public String getCurrentUserName(){
        String authorization = request.getHeader("Authorization");
        return new String(Base64Utils.decodeFromString(authorization));
    }
}

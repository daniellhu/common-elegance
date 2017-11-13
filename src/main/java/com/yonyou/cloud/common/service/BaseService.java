package com.yonyou.cloud.common.service;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.service.utils.EntityUtils;
import com.yonyou.cloud.common.service.utils.PageQuery;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

/**
 * 基础业务处理类
 * 
 * @author BENJAMIN
 *
 * @param <M>
 * @param <T>
 */
public abstract class BaseService<M extends Mapper<T>, T> {
    @Autowired
    protected M mapper;

    public void setMapper(M mapper) {
        this.mapper = mapper;
    }

    public T selectOne(T entity) {
        return mapper.selectOne(entity);
    }


    public T selectById(Object id) {
        return mapper.selectByPrimaryKey(id);
    }


    public List<T> selectList(T entity) {
        return mapper.select(entity);
    }


    public List<T> selectListAll() {
        return mapper.selectAll();
    }


    public Long selectCount(T entity) {
        return new Long(mapper.selectCount(entity));
    }


    public void insert(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
        mapper.insert(entity);
    }


    public void insertSelective(T entity) {
        EntityUtils.setCreatAndUpdatInfo(entity);
        mapper.insertSelective(entity);
    }


    public void delete(T entity) {
        mapper.delete(entity);
    }


    public void deleteById(Object id) {
        mapper.deleteByPrimaryKey(id);
    }


    public void updateById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKey(entity);
    }


    public void updateSelectiveById(T entity) {
        EntityUtils.setUpdatedInfo(entity);
        mapper.updateByPrimaryKeySelective(entity);

    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }

    public int selectCountByExample(Object example) {
        return mapper.selectCountByExample(example);
    }

    public PageResultResponse<T> selectByQuery(PageQuery query) {
        Class<T> clazz = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
        Example example = new Example(clazz);
        Example.Criteria criteria = example.createCriteria();
        for (Map.Entry<String, Object> entry : query.entrySet()) {
            criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() + "%");
        }
        Page<Object> result = PageHelper.startPage(query.getPage(), query.getLimit());
        List<T> list = mapper.selectByExample(example);
        return new PageResultResponse<T>(result.getTotal(), list);
    }

}

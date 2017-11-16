package com.yonyou.cloud.common.service;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.xiaoleilu.hutool.bean.BeanUtil;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.service.utils.ESPageQuery;

/**
 * ES相关基础业务处理类
 * 
 * @author BENJAMIN
 *
 * @param <M>
 * @param <T>
 */
public abstract class EsBaseService<T> {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Class<T> entityClass;

	@Autowired
	private TransportClient transportClient;

	public EsBaseService() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
		logger.debug("entityClass===="+entityClass.getSimpleName().toLowerCase());
	}

	/**
	 * 查一个 ，多个会报错
	 * 
	 * @param index
	 * @param query
	 * @return
	 */
	public T selectOne(String index, String query) {

		SearchResponse searchResponse = transportClient.prepareSearch(index).setTypes(entityClass.getSimpleName().toLowerCase())
				.setQuery(query == null || query.equals("") ? QueryBuilders.matchAllQuery()
						: QueryBuilders.queryStringQuery(query))
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(0).setSize(1)// 分页
				.get();

		SearchHit hits = searchResponse.getHits().getAt(0);
		Map m = hits.getSource();
		return BeanUtil.mapToBean(m, entityClass, true);

	}

	public PageResultResponse<T> pageQuery(ESPageQuery query, String index) {

		String queryString = query.getQueryString();

		SearchResponse searchResponse = transportClient.prepareSearch(index).setTypes(entityClass.getSimpleName().toLowerCase())
				.setQuery(queryString == null || queryString.equals("") ? QueryBuilders.matchAllQuery()
						: QueryBuilders.queryStringQuery(queryString))
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(query.getLimit() * (query.getPage() - 1))
				.setSize(query.getLimit())// 分页
				.addSort(
						query.getOrderBy() == null || query.getOrderBy().equals("") ? "@timestamp" : query.getOrderBy(),
						query.getOrderType() == null || query.getOrderType().equals("desc") ? SortOrder.DESC
								: SortOrder.ASC)
				.get();

		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();
		SearchHit[] searchHits = hits.getHits();

		List<T> l = new ArrayList<T>();
		for (int i = 0; i < searchHits.length; i++) {
			SearchHit s = searchHits[i];

			Map m = s.getSource();
			T t=BeanUtil.mapToBean(m, entityClass, true);
			l.add(t);

		}
		return new PageResultResponse<T>(total, l);
	}

	/**
	 * 查list
	 * 
	 * @param dbName
	 * @param queryString
	 * @return
	 */
	public List<T> selectList(String index, String queryString) {

		SearchResponse searchResponse = transportClient.prepareSearch(index).setTypes(entityClass.getSimpleName().toLowerCase())
				.setQuery(queryString == null || queryString.equals("") ? QueryBuilders.matchAllQuery()
						: QueryBuilders.queryStringQuery(queryString))
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(0).addSort("@timestamp", SortOrder.DESC)// 排序
				.get();

		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();

		List<T> l = new ArrayList<T>();
		for (int i = 0; i < searchHits.length; i++) {
			SearchHit s = searchHits[i];
			s.getSource().get("properties");

			Map m = s.getSource();
			T t=BeanUtil.mapToBean(m, entityClass, true);
			l.add(t);
		}
		return l;
	}

	// public T selectById(Object id) {
	// return mapper.selectByPrimaryKey(id);
	// }
	//
	//
	// public List<T> selectList(T entity) {
	// return mapper.select(entity);
	// }
	//
	//
	// public List<T> selectListAll() {
	// return mapper.selectAll();
	// }
	//
	//
	// public Long selectCount(T entity) {
	// return new Long(mapper.selectCount(entity));
	// }
	//
	//
	// public void insert(T entity) {
	// EntityUtils.setCreatAndUpdatInfo(entity);
	// mapper.insert(entity);
	// }
	//
	//
	// public void insertSelective(T entity) {
	// EntityUtils.setCreatAndUpdatInfo(entity);
	// mapper.insertSelective(entity);
	// }
	//
	//
	// public void delete(T entity) {
	// mapper.delete(entity);
	// }
	//
	//
	// public void deleteById(Object id) {
	// mapper.deleteByPrimaryKey(id);
	// }
	//
	//
	// public void updateById(T entity) {
	// EntityUtils.setUpdatedInfo(entity);
	// mapper.updateByPrimaryKey(entity);
	// }
	//
	//
	// public void updateSelectiveById(T entity) {
	// EntityUtils.setUpdatedInfo(entity);
	// mapper.updateByPrimaryKeySelective(entity);
	//
	// }
	//
	// public List<T> selectByExample(Object example) {
	// return mapper.selectByExample(example);
	// }
	//
	// public int selectCountByExample(Object example) {
	// return mapper.selectCountByExample(example);
	// }
	//
	// public PageResultResponse<T> selectByQuery(PageQuery query) {
	// Class<T> clazz = (Class<T>) ((ParameterizedType)
	// getClass().getGenericSuperclass()).getActualTypeArguments()[1];
	// Example example = new Example(clazz);
	// Example.Criteria criteria = example.createCriteria();
	// for (Map.Entry<String, Object> entry : query.entrySet()) {
	// criteria.andLike(entry.getKey(), "%" + entry.getValue().toString() +
	// "%");
	// }
	// Page<Object> result = PageHelper.startPage(query.getPage(),
	// query.getLimit());
	// List<T> list = mapper.selectByExample(example);
	// return new PageResultResponse<T>(result.getTotal(), list);
	// }

}

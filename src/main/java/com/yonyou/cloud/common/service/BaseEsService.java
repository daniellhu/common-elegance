package com.yonyou.cloud.common.service;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.elasticsearch.index.query.QueryBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaoleilu.hutool.bean.BeanUtil;
import com.yonyou.cloud.common.beans.PageResultResponse;
import com.yonyou.cloud.common.service.utils.EsPageQuery;

/**
 * ES相关基础业务处理类
 * 
 * @author BENJAMIN
 *
 * @param <M>
 * @param <T>
 */
public abstract class BaseEsService<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	

	private Class<T> entityClass;

	@Autowired
	private TransportClient transportClient;

	public BaseEsService() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
		logger.debug("entityClass====" + entityClass.getSimpleName().toLowerCase());
	}

	/**
	 * 查一个
	 * 
	 * @param index
	 * @param query
	 * @return
	 */
	public T selectOne(String index, String query) {

		SearchResponse searchResponse = transportClient.prepareSearch(index)
				.setTypes(entityClass.getSimpleName().toLowerCase())
				.setQuery(query == null || "".equals(query) ? QueryBuilders.matchAllQuery()
						: QueryBuilders.queryStringQuery(query))
				// 分页
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(0).setSize(1)
				.get();

		if(searchResponse!=null){
			if(searchResponse.getHits().getHits().length>0){
				SearchHit hits = searchResponse.getHits().getAt(0);
				
				Map m = hits.getSource();
				m.put("_id", hits.getId());
				return BeanUtil.mapToBean(m, entityClass, true);
			}else{
				return null;
			}
			
		}else{
			return null;
		}
		

	}
	
	
	/**
	 * 查询单个
	 * 
	 * @param index
	 * @param queryBuilder
	 * @return
	 */
	public T selectOne(String index, QueryBuilder queryBuilder) {

		SearchResponse searchResponse = transportClient.prepareSearch(index)
				.setTypes(entityClass.getSimpleName().toLowerCase())
				.setQuery(queryBuilder)
				// 分页
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(0).setSize(1)
				.get();

		if(searchResponse!=null){
			if(searchResponse.getHits().getHits().length>0){
				SearchHit hits = searchResponse.getHits().getAt(0);
				Map m = hits.getSource();
				return BeanUtil.mapToBean(m, entityClass, true);
			}else{
				return null;
			}
			
		}else{
			return null;
		}
		

	}
	

	/**
	 * 分页查询
	 * 
	 * @param query
	 * @param index
	 * @return
	 */
	public PageResultResponse<T> pageQuery(EsPageQuery query, String index) {

		String queryString = query.getQueryString();

		SearchResponse searchResponse = transportClient.prepareSearch(index)
				.setTypes(entityClass.getSimpleName().toLowerCase())
				.setQuery(queryString == null || "".equals(queryString) ? QueryBuilders.matchAllQuery()
						: QueryBuilders.queryStringQuery(queryString))
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(query.getLimit() * (query.getPage() - 1))
				// 分页
				.setSize(query.getLimit())
				.addSort(
						query.getOrderBy() == null || "".equals(query.getOrderBy()) ? "_id" : query.getOrderBy(),
						query.getOrderType() == null || "desc".equals(query.getOrderType()) ? SortOrder.DESC
								: SortOrder.ASC)
				.get();

		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();
		SearchHit[] searchHits = hits.getHits();

		List<T> l = new ArrayList<T>();
		for (int i = 0; i < searchHits.length; i++) {
			SearchHit s = searchHits[i];

			Map m = s.getSource();
			T t = BeanUtil.mapToBean(m, entityClass, true);
			l.add(t);

		}
		return new PageResultResponse<T>(total, l);
	}
	
	/**
	 * 分页查询
	 * 
	 * @param query
	 * @param index
	 * @param filter
	 * @return
	 */
	public PageResultResponse<T> pageQuery(EsPageQuery query, String index, QueryBuilder filter) {

		String queryString = query.getQueryString();

		SearchResponse searchResponse = transportClient.prepareSearch(index)
				.setTypes(entityClass.getSimpleName().toLowerCase())
				.setQuery(queryString == null || "".equals(queryString) ? QueryBuilders.matchAllQuery()
						: QueryBuilders.queryStringQuery(queryString))
				.setPostFilter(filter)
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(query.getLimit() * (query.getPage() - 1))
				// 分页
				.setSize(query.getLimit())
				.addSort(
						query.getOrderBy() == null || "".equals(query.getOrderBy()) ? "_id" : query.getOrderBy(),
						query.getOrderType() == null || "desc".equals(query.getOrderType()) ? SortOrder.DESC
								: SortOrder.ASC)
				.get();

		SearchHits hits = searchResponse.getHits();
		long total = hits.getTotalHits();
		SearchHit[] searchHits = hits.getHits();

		List<T> l = new ArrayList<T>();
		for (int i = 0; i < searchHits.length; i++) {
			SearchHit s = searchHits[i];

			Map m = s.getSource();
			T t = BeanUtil.mapToBean(m, entityClass, true);
			l.add(t);

		}
		return new PageResultResponse<T>(total, l);
	}
	
	

	/**
	 * 查询list
	 * 
	 * @param index
	 * @param queryString
	 * @return
	 */
	public List<T> selectList(String index, String queryString) {
		
		SearchResponse searchResponse = transportClient.prepareSearch(index).setTypes(entityClass.getSimpleName().toLowerCase())  
	            .setQuery(queryString == null || "".equals(queryString) ? QueryBuilders.matchAllQuery()
						: QueryBuilders.queryStringQuery(queryString))  
	            .setSearchType(SearchType.QUERY_THEN_FETCH)  
	            .setTimeout(TimeValue.timeValueSeconds(300))
	            .get();  

		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();

		List<T> l = new ArrayList<T>();
		for (int i = 0; i < searchHits.length; i++) {
			SearchHit s = searchHits[i];
			Map m = s.getSource();
			T t = BeanUtil.mapToBean(m, entityClass, true);
			l.add(t);
		}
		return l;
	}
	
	
	/**
	 * 分页查询
	 * 
	 * @param index
	 * @param queryBuilder
	 * @return
	 */
	public List<T> selectList(String index, QueryBuilder queryBuilder) {
		
		SearchResponse searchResponse = transportClient.prepareSearch(index).setTypes(entityClass.getSimpleName().toLowerCase())  
	            .setQuery(queryBuilder)  
	            .setSearchType(SearchType.QUERY_THEN_FETCH)  
	            .setTimeout(TimeValue.timeValueSeconds(300))
	            .get();  

		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();

		List<T> l = new ArrayList<T>();
		for (int i = 0; i < searchHits.length; i++) {
			SearchHit s = searchHits[i];
			Map m = s.getSource();
			T t = BeanUtil.mapToBean(m, entityClass, true);
			l.add(t);
		}
		return l;
	}
	
	

	/**
	 * 新增
	 * 
	 * @param index
	 * @param t
	 * @throws JsonProcessingException
	 */
	public void insert(String index, T t) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();

		IndexResponse indexResponse = transportClient.prepareIndex(index, entityClass.getSimpleName().toLowerCase())
				.setSource(mapper.writeValueAsString(t),XContentType.JSON).get();

	}
	
	

	/**
	 * 修改
	 * 
	 * @param index 索引
	 * @param t 更新后的doc
	 * @param id 更新的条件
	 * @throws IOException
	 */
	public void update(String index, T t,String id) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		UpdateResponse updateResponse = transportClient.prepareUpdate(index, entityClass.getSimpleName().toLowerCase(), id)
				.setDoc(mapper.writeValueAsString(t),XContentType.JSON).get();

		System.out.println(updateResponse.getVersion());

	}
}

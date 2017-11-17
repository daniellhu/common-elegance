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
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder.EntityManagerFactoryBeanCallback;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
public abstract class EsBaseServiceMuti<T> {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private Class<T> entityClass;

	@Autowired
	private TransportClient transportClient;

	public EsBaseServiceMuti() {
		Type genType = getClass().getGenericSuperclass();
		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
		entityClass = (Class) params[0];
		logger.debug("entityClass====" + entityClass.getSimpleName().toLowerCase());
	}

	/**
	 * 单个查询
	 * 根据entity查找type
	 * 
	 * @param index
	 * @param query
	 * @return
	 */
	public T selectOne(String index, String query) {
		return selectOne(index, query,entityClass);
	}
	
	
	
	/**
	 * 单个查询
	 * 
	 * @param index 索引
	 * @param query 查询语句
	 * @param type 指定type
	 * @return
	 */
	public <clazz> clazz selectOne(String index, String query,Class<clazz> type) {
		SearchResponse searchResponse = transportClient.prepareSearch(index)
				.setTypes(type.getSimpleName().toLowerCase())
				.setQuery(query == null || query.equals("") ? QueryBuilders.matchAllQuery()
						: QueryBuilders.queryStringQuery(query))
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(0).setSize(1)// 分页
				.get();

		//取出单个
		SearchHit hits = searchResponse.getHits().getAt(0);
		Map m = hits.getSource();
		//封装出bean
		return BeanUtil.mapToBean(m, type, true);

	}

	/**
	 * 分页查询 
	 * 条件封装到query中
	 * 根据entity查找type
	 * 
	 * @param query
	 * @param index
	 * @return
	 */
	public PageResultResponse<T> pageQuery(ESPageQuery query, String index) {

		return pageQuery(query, index,entityClass);
	}
	
	
	/**
	 * 分页查询 
	 * 条件封装到query中
	 * 根据classz查找type
	 * 
	 * @param <clazz>
	 * @param query
	 * @param index
	 * @return
	 */
	public <clazz> PageResultResponse<clazz>   pageQuery(ESPageQuery query, String index,Class<clazz> classz) {

		String queryString = query.getQueryString();

		SearchResponse searchResponse = transportClient.prepareSearch(index)
				.setTypes(classz.getSimpleName().toLowerCase())
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

		List<clazz> l = new ArrayList<clazz>();
		for (int i = 0; i < searchHits.length; i++) {
			SearchHit s = searchHits[i];

			Map m = s.getSource();
			
			l.add(BeanUtil.mapToBean(m, classz, true));

		}
		return new PageResultResponse<clazz>(total, l);
	}

	/**
	 * 查询list
	 * 默认返回T的类型
	 * 
	 * @param dbName
	 * @param queryString
	 * @return
	 */
	public List<T> selectList(String index, String queryString) {

//		SearchResponse searchResponse = transportClient.prepareSearch(index)
//				.setTypes(entityClass.getSimpleName().toLowerCase())
//				.setQuery(queryString == null || queryString.equals("") ? QueryBuilders.matchAllQuery()
//						: QueryBuilders.queryStringQuery(queryString))
//				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(0).addSort("@timestamp", SortOrder.DESC)// 排序
//				.get();
//
//		SearchHits hits = searchResponse.getHits();
//		SearchHit[] searchHits = hits.getHits();
//
//		List<T> l = new ArrayList<T>();
//		for (int i = 0; i < searchHits.length; i++) {
//			SearchHit s = searchHits[i];
//			s.getSource().get("properties");
//
//			Map m = s.getSource();
//			T t = BeanUtil.mapToBean(m, entityClass, true);
//			l.add(t);
//		}
//		return l;
		
		return selectList(index,queryString,entityClass);
	}
	
	
	/**
	 * 查询list
	 * 返回指定类型
	 * 
	 * @param <clazz>
	 * @param dbName
	 * @param queryString
	 * @return
	 */
	public <clazz> List<clazz> selectList(String index, String queryString,Class<clazz> clazz) {

		SearchResponse searchResponse = transportClient.prepareSearch(index)
				.setTypes(clazz.getSimpleName().toLowerCase())
				.setQuery(queryString == null || queryString.equals("") ? QueryBuilders.matchAllQuery()
						: QueryBuilders.queryStringQuery(queryString))
				.setSearchType(SearchType.QUERY_THEN_FETCH).setFrom(0).addSort("@timestamp", SortOrder.DESC)// 排序
				.get();

		SearchHits hits = searchResponse.getHits();
		SearchHit[] searchHits = hits.getHits();

		List<clazz> l = new ArrayList<clazz>();
		for (int i = 0; i < searchHits.length; i++) {
			SearchHit s = searchHits[i];
			s.getSource().get("properties");

			Map m = s.getSource();
			l.add(BeanUtil.mapToBean(m, clazz, true));
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
		insert(index,mapper.writeValueAsString(t),entityClass);
	}
	
	public <clazz> void insert(String index,Class<clazz> clazz) throws JsonProcessingException{
		ObjectMapper mapper = new ObjectMapper();
		insert(index,mapper.writeValueAsString(clazz),clazz);
	}
	
	public <clazz> void insert(String index,String doc,Class<clazz> clazz){
		IndexResponse indexResponse = transportClient.prepareIndex(index, clazz.getSimpleName().toLowerCase())
				.setSource(doc).get();
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
		update(index,entityClass,id,mapper.writeValueAsString(t));

	}
	
	public <clazz> void update(String index,Class<clazz> clazz,String id,String doc) throws JsonProcessingException {
		// XContentBuilder source = XContentFactory.jsonBuilder()
		// .startObject()
		// .field("name", "will")
		// .endObject();
		ObjectMapper mapper = new ObjectMapper();
		UpdateResponse updateResponse = transportClient.prepareUpdate(index, clazz.getSimpleName().toLowerCase(), id)
				.setDoc(doc).get();

		System.out.println(updateResponse.getVersion());

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

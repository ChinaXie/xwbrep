package com.xwb.utils;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import com.mongodb.MongoClientURI;

/**
 * 
 * @author xwb
 */
@Configuration
public class MongoConfig {
	
	@Value("${spring.data.mongodb.uri}")
	private String MONGO_URI;
	
	@Value("${spring.data.mongodb.wygluri}")
	private String MONGO_URI_WYGL;

	@Bean
	public MongoMappingContext mongoMappingContext() {
		MongoMappingContext mappingContext = new MongoMappingContext();
		return mappingContext;
	}

	// ==================== 连接�? mongodb1 服务�?
	// ======================================

	@Bean // 使用自定义的typeMapper去除写入mongodb时的“_class”字�?
	public MappingMongoConverter mappingMongoConverter() throws Exception {
		DefaultDbRefResolver dbRefResolver = new DefaultDbRefResolver(this.dbFactory());
		MappingMongoConverter converter = new MappingMongoConverter(dbRefResolver, this.mongoMappingContext());
		converter.setTypeMapper(new DefaultMongoTypeMapper(null));
		return converter;
	}

	@Bean
	@Primary
	public MongoDbFactory dbFactory() throws UnknownHostException {
		return new SimpleMongoDbFactory(new MongoClientURI(MONGO_URI));
	}

	@Bean
	@Primary
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(this.dbFactory(), this.mappingMongoConverter());
	}

	@Bean
	public MongoDbFactory wyglDbFactory() throws UnknownHostException {
		return new SimpleMongoDbFactory(new MongoClientURI(MONGO_URI_WYGL));
	}

	@Bean
	public MongoTemplate wyglMongoTemplate() throws Exception {
		return new MongoTemplate(wyglDbFactory(),mappingMongoConverter());
	}
}
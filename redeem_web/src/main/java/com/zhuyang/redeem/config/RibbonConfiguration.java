package com.zhuyang.redeem.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.IPing;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.PingUrl;
import com.netflix.loadbalancer.RandomRule;

/**
 * 
 * Here, we override the IPing and IRule used by the default load balancer. The
 * default IPing is a NoOpPing (which doesn’t actually ping server instances,
 * instead always reporting that they’re stable), and the default IRule is a
 * ZoneAvoidanceRule (which avoids the Amazon EC2 zone that has the most
 * malfunctioning servers, and might thus be a bit difficult to try out in our
 * local environment).
 * 
 */
public class RibbonConfiguration {
	@Autowired
	private IClientConfig ribbonClientConfig;

	/**
	 * Our IPing is a PingUrl, which will ping a URL to check the status of each
	 * server.provider has, as you’ll recall, a method mapped to the / path;
	 * that means that Ribbon will get an HTTP 200 response when it pings a
	 * running provider server.
	 * 
	 * server list defined in application.yml :listOfServers: localhost:8000,
	 * localhost:8002,localhost:8003
	 * 判断目标服务是否存活 对应不同的协议不同的方式去探测，得到后端服务是否存活。如有http的，还有对于微服务框架内的服务存活的NIWSDiscoveryPing是通过eureka client来获取的instanceinfo中的信息来获取。
	 */
	@Bean
	public IPing ribbonPing(IClientConfig config) {
		// ping url will try to access http://microservice-provider/provider/ to
		// see if reponse code is 200 . check PingUrl.isAlive()
		// param /provider/ is the context-path of provider service
		return new PingUrl(false, "/provider/");
	}

	/**
	 * The IRule we set up, the AvailabilityFilteringRule, will use Ribbon’s
	 * built-in circuit breaker functionality to filter out any servers in an
	 * “open-circuit” state: if a ping fails to connect to a given server, or if
	 * it gets a read failure for the server, Ribbon will consider that server
	 * “dead” until it begins to respond normally.
	 * 
	 * AvailabilityFilteringRule | 过滤掉那些因为一直连接失败的被标记为circuit tripped的后端server，并过滤掉那些高并发的的后端server（active connections 超过配置的阈值） | 使用一个AvailabilityPredicate来包含过滤server的逻辑，其实就就是检查status里记录的各个server的运行状态
	 * RandomRule  | 随机选择一个server
	 * BestAvailabl eRule | 选择一个最小的并发请求的server | 逐个考察Server，如果Server被tripped了，则忽略，在选择其中
	 * RoundRobinRule  |  roundRobin方式轮询选择  |  轮询index，选择index对应位置的server
	 * WeightedResponseTimeRule  |  根据响应时间分配一个weight，响应时间越长，weight越小，被选中的可能性越低。  |  一 个后台线程定期的从status里面读取评价响应时间，为每个server计算一个weight。Weight的计算也比较简单responsetime 减去每个server自己平均的responsetime是server的权重。当刚开始运行，没有形成statas时，使用roubine策略选择 server。
	 * RetryRule  |  对选定的负载均衡策略机上重试机制。 |  在一个配置时间段内当选择server不成功，则一直尝试使用subRule的方式选择一个可用的server
	 * ZoneAvoidanceRule  |  复合判断server所在区域的性能和server的可用性选择server  |  使 用ZoneAvoidancePredicate和AvailabilityPredicate来判断是否选择某个server，前一个判断判定一个 zone的运行性能是否可用，剔除不可用的zone（的所有server），AvailabilityPredicate用于过滤掉连接数过多的 Server。
	 * @param config
	 * @return
	 */
	@Bean
	public IRule ribbonRule(IClientConfig config) {
		// return new AvailabilityFilteringRule();
//		 return new RandomRule();//
		// return new BestAvailableRule();
		// return new RoundRobinRule();//轮询
		// return new WeightedResponseTimeRule();
		// return new RetryRule();
//		 return new ZoneAvoidanceRule();
		return new MyRule();
	}
}